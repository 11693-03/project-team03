package edu.cmu.lti.oaqa.pipeline;

import static java.util.stream.Collectors.toList;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.resource.ResourceProcessException;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import json.JsonCollectionReaderHelper;
import json.gson.ListAnswerSet;
import json.gson.QuestionType;
import json.gson.Snippet;
import json.gson.TestListQuestion;
import json.gson.TestQuestion;
import json.gson.TestSet;
import json.gson.Triple;

import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.util.FSCollectionFactory;

import util.MyUtils;
import util.PerformanceInfo;
import util.TypeUtil;
import edu.cmu.lti.oaqa.type.answer.Answer;
import edu.cmu.lti.oaqa.type.input.Question;
import edu.cmu.lti.oaqa.type.retrieval.ConceptSearchResult;
import edu.cmu.lti.oaqa.type.retrieval.Document;
import edu.cmu.lti.oaqa.type.retrieval.Passage;
import edu.cmu.lti.oaqa.type.retrieval.TripleSearchResult;

/**
 * This the consumer of the system, which will evaluate performance of each part and output the
 * result
 * 
 */
public class Consumer extends CasConsumer_ImplBase {
  static final String PUBPREFIX = "http://www.ncbi.nlm.nih.gov/pubmed/";

  // File golden_file;//refer to the file that has golden standard answer
  String outputPath;

  Map<String, List<String>> docMaps;

  Map<String, List<String>> conceptMaps;

  Map<String, List<Triple>> tripleMaps;

  Map<String, List<Snippet>> snippetMaps;

  Map<String, List<List<String>>> answerMap;

  List<TestQuestion> goldStandards;

  List<? extends TestQuestion> goldStandardsForExactAnswer;

  JsonCollectionReaderHelper jsHelper;

  List<TestListQuestion> answers;

  ListAnswerSet answerSet;

  PerformanceInfo metrics;

  String goldStandardPath;

  /**
   * Name of configuration parameter that must be set to the path of an input file.
   */
  public static final String PARAM_INPUTPATH = "InputFile";

  @Override
  public void initialize() throws ResourceInitializationException {

    // read goldStandard by JsonCollectionReader
    jsHelper = new JsonCollectionReaderHelper();
    goldStandards = jsHelper.testRun();
    goldStandardPath = ((String) getConfigParameterValue(PARAM_INPUTPATH)).trim();

    goldStandardsForExactAnswer = TestSet

    .load(getClass().getResourceAsStream(goldStandardPath)).stream().collect(toList());

    // for each question, we store the documents, concepts, triple info corresponding to each
    // question
    docMaps = new HashMap<String, List<String>>();
    conceptMaps = new HashMap<String, List<String>>();
    tripleMaps = new HashMap<String, List<Triple>>();
    snippetMaps = new HashMap<String, List<Snippet>>();
    answerMap = new HashMap<String, List<List<String>>>();
    metrics = new PerformanceInfo();
    for (int i = 0; i < goldStandards.size(); i++) {
      docMaps.put(goldStandards.get(i).getId(), goldStandards.get(i).getDocuments());
      conceptMaps.put(goldStandards.get(i).getId(), goldStandards.get(i).getConcepts());
      tripleMaps.put(goldStandards.get(i).getId(), goldStandards.get(i).getTriples());
      snippetMaps.put(goldStandards.get(i).getId(), goldStandards.get(i).getSnippets());

    }
    for (int i = 0; i < goldStandardsForExactAnswer.size(); i++) {

      answerMap.put(goldStandardsForExactAnswer.get(i).getId(),
              ((TestListQuestion) goldStandardsForExactAnswer.get(i)).getExactAnswer());

    }
    answers = new LinkedList<TestListQuestion>();

  }

  @Override
  public void processCas(CAS aCAS) throws ResourceProcessException {

    outputPath = "MyOutput.json";// the path of output file

    JCas jcas = null;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      e.printStackTrace();
    }
    Question question = TypeUtil.getQuestion(jcas);
    String curQId = question.getId();
    String body = question.getText();
    QuestionType type = JsonCollectionReaderHelper.convertQuestionType(question.getQuestionType());

    /** Compute Documents Performance **/
    Collection<Document> documents = TypeUtil.getRankedDocuments(jcas);
    LinkedList<Document> documentList = new LinkedList<Document>();
    documentList.addAll(documents);
    LinkedList<String> docUriList = new LinkedList<String>();
    for (Document doc : documentList)
      docUriList.add(doc.getUri());
    List<String> docResult = docMaps.get(curQId);
    // System.out.println(documentList.size());
    int docTotalPositive = 0;
    double totalPrecision = 0.0;
    double docPrecision = 0.0;
    double docRecall = 0.0;
    for (int i = 0; i < documentList.size(); i++) {
      if (docResult.contains(documentList.get(i).getUri())) {
        // System.out.println(documentList.get(i).getRank()+":"+documentList.get(i).getUri());
        docTotalPositive++;
        totalPrecision += (docTotalPositive * 1.0) / ((i + 1) * 1.0);
      }
    }
    if (docTotalPositive == 0) {
      docPrecision = 0.0;
    } else {
      docPrecision = totalPrecision / (docTotalPositive * 1.0);
    }
    docRecall = (double) docTotalPositive / (double) docResult.size();
    metrics.addDocAP(docPrecision);
    System.out.println("docPrecision:" + docPrecision);
    System.out.println("docRecall:" + docRecall);
    // For the Concepts:
    Collection<ConceptSearchResult> concepts = TypeUtil.getRankedConceptSearchResults(jcas);
    LinkedList<ConceptSearchResult> conceptList = new LinkedList<ConceptSearchResult>();
    conceptList.addAll(concepts);
    LinkedList<String> conceptUriList = new LinkedList<String>();
    for (ConceptSearchResult concept : conceptList)
      conceptUriList.add(concept.getUri());

    List<String> collectionResult = conceptMaps.get(curQId);
    // System.out.println("curID:" + curQId);
    int conceptTotalPositive = 0;
    double concepttotalPrecision = 0.0;
    double conceptPrecision = 0.0;
    double conceptRecall = 0.0;
    for (int i = 0; i < conceptList.size(); i++) {
      if (collectionResult.contains(conceptList.get(i).getUri())) {
        // System.out.println(i + ":" + conceptList.get(i).getUri());
        conceptTotalPositive++;
        concepttotalPrecision += (conceptTotalPositive * 1.0) / ((i + 1) * 1.0);
        conceptRecall = conceptTotalPositive / (collectionResult.size() * 1.0);
      }
    }
    if (conceptTotalPositive == 0) {
      conceptPrecision = 0.0;
    } else {
      conceptPrecision = concepttotalPrecision / (conceptTotalPositive * 1.0);
    }
    metrics.addConceptAP(conceptPrecision);
    System.out.println("ConceptPrecision:" + conceptPrecision);

    /** compute triple performance **/
    int tripleTotalPositive = 0;
    double tripletotalPrecision = 0.0;
    double triplePrecision = 0.0;
    double tripleRecall = 0.0;
    Collection<TripleSearchResult> triples = TypeUtil.getRankedTripleSearchResults(jcas);
    LinkedList<TripleSearchResult> tripleSRList = new LinkedList<TripleSearchResult>();
    tripleSRList.addAll(triples);
    // gain the tripList for this query
    List<Triple> tripleList = new ArrayList<Triple>();
    tripleList = tripleMaps.get(curQId);
    if (tripleList != null) {
      for (int i = 0; i < tripleSRList.size(); i++) {
        String oresult = tripleSRList.get(i).getTriple().getObject();
        String presult = tripleSRList.get(i).getTriple().getPredicate();
        String sresult = tripleSRList.get(i).getTriple().getSubject();
        for (int j = 0; j < tripleList.size(); j++) {
          if (tripleList.get(j).getO().equals(oresult) && tripleList.get(j).getP().equals(presult)
                  && tripleList.get(j).getS().equals(sresult)) {
            tripleTotalPositive++;
            tripletotalPrecision += (tripleTotalPositive * 1.0) / ((i + 1) * 1.0);
            tripleRecall = tripleTotalPositive / (tripleList.size() * 1.0);
          }
        }
      }
      if (tripleTotalPositive == 0) {
        triplePrecision = 0.0;
      } else {
        triplePrecision = tripletotalPrecision / (tripleTotalPositive * 1.0);
      }
    }
    triplePrecision = 0;
    metrics.addTripleAP(triplePrecision);
    System.out.println("triplePrecision:" + triplePrecision);

    // System.out.println("--------------I'm a hualiliful segmentation line-------------");
    Collection<Passage> snippets = TypeUtil.getRankedPassages(jcas);
    LinkedList<Passage> snippetList = new LinkedList<Passage>();
    snippetList.addAll(snippets);
    LinkedList<Snippet> test = new LinkedList<Snippet>();
    for (Passage p : snippetList)
      test.add(new Snippet(p.getUri(), p.getText(), p.getOffsetInBeginSection(), p
              .getOffsetInEndSection(), p.getBeginSection(), p.getEndSection(), p.getTitle(), p
              .getScore()));
    List<Snippet> gold = snippetMaps.get(curQId);
    //System.out.println("snippets golden standard size:" + gold.size());
    double precision = MyUtils.calcSnippetPrecision(gold, test);
    double recall = MyUtils.calcSnippetRecall(gold, test);
    System.out.println("snippet precision:" + precision);
    System.out.println("snippet recall:" + recall);

    System.out.println("Done");
    List<List<String>> exactAnswer = new LinkedList<List<String>>();

    Collection<Answer> answersFromCAS = TypeUtil.getAnswersByRank(jcas);
    LinkedList<Answer> answerList = new LinkedList<Answer>(answersFromCAS);
    for (Answer a : answerList) {
      LinkedList<String> listString = new LinkedList<String>();
      listString.add(a.getText());
      Collection<String> variants = FSCollectionFactory.create(a.getVariants());
      listString.addAll(variants);
      exactAnswer.add(listString);
      //System.err.println("exact answer " + listString);
    }
    List<List<String>> goldAnswerList = answerMap.get(curQId);
    double answerPrecision = 0.0;
    double answerRecall = 0.0;
    int goldAnswerSize = 0;
    if (goldAnswerList != null && exactAnswer.size()!=0){
      answerPrecision = PerformanceInfo.computeAnswerPrecision(exactAnswer, goldAnswerList);
      answerRecall = PerformanceInfo.computeAnswerRecall(exactAnswer, goldAnswerList);
      goldAnswerSize = goldAnswerList.size();
    }
    else
      answerPrecision = 0;
    
    double fMeasure = PerformanceInfo.getFMeasure(answerPrecision, answerRecall);
    metrics.addAnswerPrecision(answerPrecision);
    metrics.addAnswerRecalList(answerRecall);
    String idealAnswer = "";
    if(snippetList!=null && snippetList.size()!=0)
      idealAnswer = snippetList.get(0).getText();    
    metrics.addAnswerFMeasure(fMeasure);
    TestListQuestion answer = new TestListQuestion(curQId, body, type, docUriList, test,
            conceptUriList, tripleList, idealAnswer, exactAnswer);
    answers.add(answer);

    System.out.println("current doc MAP = " + metrics.getDocMAP());
    System.out.println("current concept MAP = " + metrics.getConceptMAP());

//    System.err.println("gold answer size " + goldAnswerSize + " TEST size"
//            + exactAnswer.size() + " answer precision = " + answerPrecision);
    System.out.println("exact answer recall:" + answerRecall);
  }

  @Override
  public void destroy() {
    // output results
    answerSet = new ListAnswerSet(answers);
    String output = answerSet.dump();
    System.out.println("final doc MAP = " + metrics.getDocMAP());
    System.out.println("final concept MAP = " + metrics.getConceptMAP());
    System.out.println("final answer MAP = " + metrics.getAnswerMAP());
    System.out.println("final answer MAR = "+metrics.getAnswerMAR());
    System.out.println("final answer MAF = "+metrics.getAnswerFMAP());
    try {
      PrintWriter out = new PrintWriter(outputPath);
      out.println(output);
      out.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}

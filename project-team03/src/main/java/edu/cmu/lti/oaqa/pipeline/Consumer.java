package edu.cmu.lti.oaqa.pipeline;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.resource.ResourceProcessException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.util.FSCollectionFactory;

import util.MyUtils;
import util.TypeUtil;
import edu.cmu.lti.oaqa.type.answer.Answer;
import edu.cmu.lti.oaqa.type.input.Question;
import edu.cmu.lti.oaqa.type.kb.Concept;
import edu.cmu.lti.oaqa.type.retrieval.ConceptSearchResult;
import edu.cmu.lti.oaqa.type.retrieval.Document;
import edu.cmu.lti.oaqa.type.retrieval.Passage;
import edu.cmu.lti.oaqa.type.retrieval.TripleSearchResult;

public class Consumer extends CasConsumer_ImplBase {
  static final String PUBPREFIX = "http://www.ncbi.nlm.nih.gov/pubmed/";

  // File golden_file;//refer to the file that has golden standard answer
  String goldPath, outputPath;

  Map<String, List<String>> docMaps;

  Map<String, List<String>> conceptMaps;

  Map<String, List<Triple>> tripleMaps;
  
  Map<String, List<Snippet>> snippetMaps;

  List<TestQuestion> goldStandards;

  JsonCollectionReaderHelper jsHelper;
  
  List<TestListQuestion> answers;
  
  ListAnswerSet answerSet;

  @Override
  public void initialize() throws ResourceInitializationException {

    // read goldStandard by JsonCollectionReader
    jsHelper = new JsonCollectionReaderHelper();
    goldStandards = jsHelper.testRun();

    // for each question, we store the documents, concepts, triple info corresponding to each
    // question
    docMaps = new HashMap<String, List<String>>();
    conceptMaps = new HashMap<String, List<String>>();
    tripleMaps = new HashMap<String, List<Triple>>();
    snippetMaps = new HashMap<String, List<Snippet>>();

    for (int i = 0; i < goldStandards.size(); i++) {
      docMaps.put(goldStandards.get(i).getId(), goldStandards.get(i).getDocuments());
      conceptMaps.put(goldStandards.get(i).getId(), goldStandards.get(i).getConcepts());
      tripleMaps.put(goldStandards.get(i).getId(), goldStandards.get(i).getTriples());
      snippetMaps.put(goldStandards.get(i).getId(), goldStandards.get(i).getSnippets());
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
    

    // For the documents:
    Collection<Document> documents = TypeUtil.getRankedDocuments(jcas);
    LinkedList<Document> documentList = new LinkedList<Document>();
    documentList.addAll(documents);
    
    LinkedList<String> docUriList = new LinkedList<String>();
    for(Document doc :documentList)
      docUriList.add(doc.getUri());
    
    
    List<String> docResult = docMaps.get(curQId);
    //System.out.println(documentList.size());
    int docTotalPositive = 0;
    double totalPrecision = 0.0;
    double docPrecision = 0.0;
    for (int i = 0; i < documentList.size(); i++) {
      if (docResult.contains(documentList.get(i).getUri())) {
//        System.out.println(documentList.get(i).getRank()+":"+documentList.get(i).getUri());
        docTotalPositive++;
        totalPrecision += (docTotalPositive * 1.0) / ((i + 1) * 1.0);
      }
    }
    if (docTotalPositive == 0) {
      docPrecision = 0.0;
    } else {
      docPrecision = totalPrecision / (docTotalPositive * 1.0);
    }
    System.out.println("docPrecision:" + docPrecision);
    System.out.println("docRecall:"+(double)docTotalPositive/(double)docResult.size());
    // For the Concepts:
    Collection<ConceptSearchResult> concepts = TypeUtil.getRankedConceptSearchResults(jcas);

    LinkedList<ConceptSearchResult> conceptList = new LinkedList<ConceptSearchResult>();
    conceptList.addAll(concepts);
    LinkedList<String> conceptUriList = new LinkedList<String>();
    for(ConceptSearchResult concept :conceptList)
      conceptUriList.add(concept.getUri());
    

    List<String> collectionResult = conceptMaps.get(curQId);
    //System.out.println("curID:" + curQId);
    int conceptTotalPositive = 0;
    double concepttotalPrecision = 0.0;
    double conceptPrecision = 0.0;

    for (int i = 0; i < conceptList.size(); i++) {
      if (collectionResult.contains(conceptList.get(i).getUri())) {
        //System.out.println(i + ":" + conceptList.get(i).getUri());
        conceptTotalPositive++;
        concepttotalPrecision += (conceptTotalPositive * 1.0) / ((i + 1) * 1.0);
      }
    }
    if (conceptTotalPositive == 0) {
      conceptPrecision = 0.0;
    } else {
      conceptPrecision = concepttotalPrecision / (conceptTotalPositive * 1.0);
    }

    System.out.println("ConceptPrecision:" + conceptPrecision);

    // For collection
    Collection<TripleSearchResult> triples = TypeUtil.getRankedTripleSearchResults(jcas);
    LinkedList<TripleSearchResult> tripleSRList = new LinkedList<TripleSearchResult>();
    tripleSRList.addAll(triples);
    LinkedList<Triple> tripleList = new LinkedList<Triple>();
    for(TripleSearchResult tripleSR: tripleSRList )
      tripleList.add(new Triple(tripleSR.getTriple().getSubject(),tripleSR.getTriple().getPredicate(),tripleSR.getTriple().getObject()));
    

    //System.out.println("--------------I'm a hualiliful segmentation line-------------");
    Collection<Passage> snippets = TypeUtil.getRankedPassages(jcas);
    LinkedList<Passage> snippetList = new LinkedList<Passage>();
    snippetList.addAll(snippets);
    LinkedList<Snippet> test = new LinkedList<Snippet>();
    for(Passage p:snippetList)
      test.add(new Snippet(p.getUri(), p.getText(), p.getOffsetInBeginSection(), p.getOffsetInEndSection(),
              p.getBeginSection(), p.getEndSection(),p.getTitle(), p.getScore()));
    List<Snippet> gold = snippetMaps.get(curQId);
    System.out.println("snippets golden standard size:"+gold.size());
    double precision = MyUtils.calcSnippetPrecision(gold, test);
    double recall = MyUtils.calcSnippetRecall(gold, test);
    System.out.println("snippet precision:"+precision);
    System.out.println("snippet recall:"+recall);
    

    System.out.println("Done");
    List<List<String>> exactAnswer = new LinkedList<List<String>>();
   
    Collection<Answer>  answersFromCAS = TypeUtil.getAnswersByRank(jcas);
    LinkedList<Answer> answerList = new LinkedList<Answer>(answersFromCAS);
    for(Answer a :answerList){
      LinkedList<String> listString = new LinkedList<String>();
      listString.add(a.getText());
      Collection<String> variants = FSCollectionFactory.create(a.getVariants());
      listString.addAll(variants);
    }
    
    TestListQuestion answer = new TestListQuestion(curQId,body,type,docUriList,test,conceptUriList,tripleList,"pseudo ideal answer",exactAnswer);
    answers.add(answer);
  }
  
  @Override
  public void destroy(){
    answerSet = new ListAnswerSet(answers);
    String output = answerSet.dump();
    try {
      PrintWriter out = new PrintWriter(outputPath);
      out.println(output);
      out.close();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}

package edu.cmu.lti.oaqa.pipeline;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.resource.ResourceProcessException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import json.JsonCollectionReaderHelper;
import json.gson.TestQuestion;
import json.gson.Triple;

import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import util.TypeUtil;
import edu.cmu.lti.oaqa.type.input.Question;
import edu.cmu.lti.oaqa.type.kb.Concept;
import edu.cmu.lti.oaqa.type.retrieval.ConceptSearchResult;
import edu.cmu.lti.oaqa.type.retrieval.Document;
import edu.cmu.lti.oaqa.type.retrieval.TripleSearchResult;

public class Consumer extends CasConsumer_ImplBase {
  static final String PUBPREFIX = "http://www.ncbi.nlm.nih.gov/pubmed/";

  // File golden_file;//refer to the file that has golden standard answer
  String goldPath, outputPath;

  Map<String, List<String>> docMaps;

  Map<String, List<String>> conceptMaps;

  Map<String, List<Triple>> tripleMaps;

  List<TestQuestion> goldStandards;

  JsonCollectionReaderHelper jsHelper;

  // five kinds of evaluations
  List<Double[]> precisions;

  List<Double[]> recalls;

  List<Double[]> fmeasures;

  List<Double[]> maps;

  List<Double[]> gmaps;

  File goldenFile;

  @Override
  public void initialize() throws ResourceInitializationException {
    // precisions = new ArrayList<Double[]>();
    // recalls = new ArrayList<Double[]>();
    // fmeasures = new ArrayList<Double[]>();
    // maps = new ArrayList<Double[]>();
    // gmaps = new ArrayList<Double[]>();

    jsHelper = new JsonCollectionReaderHelper();
    goldStandards = jsHelper.testRun();

    docMaps = new HashMap<String, List<String>>();
    conceptMaps = new HashMap<String, List<String>>();
    tripleMaps = new HashMap<String, List<Triple>>();

    for (int i = 0; i < goldStandards.size(); i++) {
      docMaps.put(goldStandards.get(i).getId(), goldStandards.get(i).getDocuments());
      conceptMaps.put(goldStandards.get(i).getId(), goldStandards.get(i).getConcepts());
      tripleMaps.put(goldStandards.get(i).getId(), goldStandards.get(i).getTriples());
    }

  }

  @Override
  public void processCas(CAS aCAS) throws ResourceProcessException {

    // TODO Auto-generated method stub
    outputPath = "/MyOutput.json";// the path of output file

    JCas jcas = null;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    String curQId = TypeUtil.getQuestion(jcas).getId();

    Collection<ConceptSearchResult> concepts = TypeUtil.getRankedConceptSearchResults(jcas);
    LinkedList<ConceptSearchResult> conceptList = new LinkedList<ConceptSearchResult>();
    conceptList.addAll(concepts);

    Collection<Document> documents = TypeUtil.getRankedDocuments(jcas);
    LinkedList<Document> documentList = new LinkedList<Document>();
    documentList.addAll(documents);

    Collection<TripleSearchResult> triples = TypeUtil.getRankedTripleSearchResults(jcas);
    LinkedList<TripleSearchResult> tripleList = new LinkedList<TripleSearchResult>();
    tripleList.addAll(triples);

    List<String> docResult = docMaps.get(curQId);
    int tp = 0;
    int docTotalPositive = 0;
    double totalPrecision=0.0;
    double docPrecision=0.0;

    for (int i = 0; i < documentList.size(); i++) {
      System.out.println("**********"+documentList.get(i).getUri());
      if (docResult.contains(documentList.get(i).getUri())) {
        docTotalPositive++;
        totalPrecision+=(docTotalPositive*1.0)/((i+1)*1.0); 
      }  
    }
    if(docTotalPositive == 0){
      docPrecision = 0.0;
    }
    else{
      docPrecision=totalPrecision/(docTotalPositive*1.0);
    }
    System.out.println("totalPrecision"+totalPrecision);
    System.out.println("docPositive"+docTotalPositive);
    System.out.println("docPrecision"+docPrecision);
      
  }
}

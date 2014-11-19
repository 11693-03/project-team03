package edu.cmu.lti.oaqa.pipeline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.resource.ResourceInitializationException;

import util.MyUtils;
import util.TokenizerLingpipe;
import util.TypeFactory;
import util.TypeUtil;
import util.Utils;
import edu.cmu.lti.oaqa.bio.bioasq.services.GoPubMedService;
import edu.cmu.lti.oaqa.bio.bioasq.services.OntologyServiceResponse;
import edu.cmu.lti.oaqa.type.kb.Concept;
import edu.cmu.lti.oaqa.type.retrieval.AtomicQueryConcept;
import edu.cmu.lti.oaqa.type.retrieval.ConceptSearchResult;


/**
 *  This class retrieves concepts related to the query, 
 *  using apis provided by Mesh, Jochem, Uniprot, DiseaseOntology, etc
 *  @author Michael Zhuang
 *   
 * 
 ***/
public class ConceptRetrieve extends JCasAnnotator_ImplBase{
  GoPubMedService service;
  public static String Properties = "ProjectProperties";
  public void initialize(UimaContext aContext) throws ResourceInitializationException{
    super.initialize(aContext);
    String properties = (String) aContext.getConfigParameterValue((Properties)); 
    try {
      service = new GoPubMedService(properties);
    } catch (Exception e) {
    }
  }
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    FSIterator<TOP> iter = aJCas.getJFSIndexRepository().getAllIndexedFS(AtomicQueryConcept.type);

    TokenizerLingpipe tokenizer = TokenizerLingpipe.getInstance();
    MyUtils ins = MyUtils.getInstance();
    if(iter.isValid() && iter.hasNext()){
      AtomicQueryConcept query = (AtomicQueryConcept)iter.next();
      String text = query.getText();
      String[] tokens = text.split("\\s+");
      HashMap<String, Integer> qVector = new HashMap<String, Integer>();
      for(String t : tokens){
        if(!qVector.containsKey(t))
          qVector.put(t, 1);
        qVector.put(t, qVector.get(t)+1);
      }
      try {
        OntologyServiceResponse.Result uniprotResult = service.findUniprotEntitiesPaged(text, 0);
        Concept concept = new Concept(aJCas);
        String label = null;
        List<String>uris = new LinkedList<String>();
        for (OntologyServiceResponse.Finding finding : uniprotResult.getFindings()) {
          if(finding.getScore()<0.1)
            break;
          if(label==null)
            label = finding.getConcept().getLabel();
          String keyword = tokenizer.tokenize(finding.getConcept().getLabel());
          double score = ins.computeCosineSimilarity(qVector, keyword);
          
          ConceptSearchResult conceptSR = TypeFactory.createConceptSearchResult(
                  aJCas, concept, finding.getConcept().getUri(),score, 
                  finding.getConcept().getLabel(), 0, text, 
                  finding.getConcept().getTermId(),new ArrayList<>());
          uris.add(finding.getConcept().getUri());
          conceptSR.addToIndexes(aJCas);
          //System.err.println("In Annotator Concept "+rank+":"+conceptSR);
        }
        concept.setUris(Utils.createStringList(aJCas,uris));
        concept.setName(label);
        concept.addToIndexes(aJCas);

        concept = new Concept(aJCas);
        OntologyServiceResponse.Result diseaseOntologyResult = service
                .findDiseaseOntologyEntitiesPaged(text, 0);
        uris = new LinkedList<String>();
        for (OntologyServiceResponse.Finding finding : diseaseOntologyResult.getFindings()) {
          if(finding.getScore()<0.1)
            break;
          if(label==null)
            label = finding.getConcept().getLabel();
          String keyword = tokenizer.tokenize(finding.getConcept().getLabel());
          double score = ins.computeCosineSimilarity(qVector, keyword);
          
          ConceptSearchResult conceptSR = TypeFactory.createConceptSearchResult(
                  aJCas, concept, finding.getConcept().getUri(),score, 
                  finding.getConcept().getLabel(), 0, text, 
                  finding.getConcept().getTermId(),new ArrayList<>());
          uris.add(finding.getConcept().getUri());
          conceptSR.addToIndexes(aJCas);
          //System.err.println("In Annotator Concept "+rank+":"+conceptSR);
        }
        concept.setUris(Utils.createStringList(aJCas,uris));
        concept.setName(label);
        concept.addToIndexes(aJCas);

        uris = new LinkedList<String>();
        concept = new Concept(aJCas);
        OntologyServiceResponse.Result geneOntologyResult = service.findGeneOntologyEntitiesPaged(text,
                0, 10);
        //System.out.println("Gene ontology: " + geneOntologyResult.getFindings().size());
        for (OntologyServiceResponse.Finding finding : geneOntologyResult.getFindings()) {
          if(finding.getScore()<0.1)
            break;
          if(label==null)
            label = finding.getConcept().getLabel();
          String keyword = tokenizer.tokenize(finding.getConcept().getLabel());
          double score = ins.computeCosineSimilarity(qVector, keyword);
          
          ConceptSearchResult conceptSR = TypeFactory.createConceptSearchResult(
                  aJCas, concept, finding.getConcept().getUri(),score, 
                  finding.getConcept().getLabel(), 0, text, 
                  finding.getConcept().getTermId(),new ArrayList<>());
          uris.add(finding.getConcept().getUri());
          conceptSR.addToIndexes(aJCas);
          //System.err.println("In Annotator Concept "+rank+":"+conceptSR);
        }
        concept.setUris(Utils.createStringList(aJCas,uris));
        concept.setName(label);
        concept.addToIndexes(aJCas);

        uris = new LinkedList<String>();
        concept = new Concept(aJCas);
        OntologyServiceResponse.Result jochemResult = service.findJochemEntitiesPaged(text, 0);
        //System.out.println("Jochem: " + jochemResult.getFindings().size());
        for (OntologyServiceResponse.Finding finding : jochemResult.getFindings()) {
          if(finding.getScore()<0.1)
            break;
          if(label==null)
            label = finding.getConcept().getLabel();
          String keyword = tokenizer.tokenize(finding.getConcept().getLabel());
          double score = ins.computeCosineSimilarity(qVector, keyword);
          
          ConceptSearchResult conceptSR = TypeFactory.createConceptSearchResult(
                  aJCas, concept, finding.getConcept().getUri(),score, 
                  finding.getConcept().getLabel(), 0, text, 
                  finding.getConcept().getTermId(),new ArrayList<>());
          uris.add(finding.getConcept().getUri());
          conceptSR.addToIndexes(aJCas);
          //System.err.println("In Annotator Concept "+rank+":"+conceptSR);
        }
        concept.setUris(Utils.createStringList(aJCas,uris));
        concept.setName(label);
        concept.addToIndexes(aJCas);


        uris = new LinkedList<String>();
        concept = new Concept(aJCas);
        OntologyServiceResponse.Result meshResult = service.findMeshEntitiesPaged(text, 0);
        //System.out.println("MeSH: " + meshResult.getFindings().size());
        for (OntologyServiceResponse.Finding finding : meshResult.getFindings()) {
          if(finding.getScore()<0.1)
            break;
          if(label==null)
            label = finding.getConcept().getLabel();
          String keyword = tokenizer.tokenize(finding.getConcept().getLabel());
          double score = ins.computeCosineSimilarity(qVector, keyword);
          
          ConceptSearchResult conceptSR = TypeFactory.createConceptSearchResult(
                  aJCas, concept, finding.getConcept().getUri(),score, 
                  finding.getConcept().getLabel(), 0, text, 
                  finding.getConcept().getTermId(),new ArrayList<>());
          uris.add(finding.getConcept().getUri());
          conceptSR.addToIndexes(aJCas);
          //System.err.println("In Annotator Concept "+rank+":"+conceptSR);
          }
        concept.setUris(Utils.createStringList(aJCas,uris));
        concept.setName(label);
        concept.addToIndexes(aJCas);

        Collection<ConceptSearchResult> aa = TypeUtil.getRankedConceptSearchResults(aJCas);
        int rank = 1;
        Iterator<ConceptSearchResult> it = TypeUtil.getRankedSearchResultByScore(aJCas,aa.size()).iterator();
        while(it.hasNext()){
          ConceptSearchResult sr =  it.next();
          sr.removeFromIndexes(aJCas);
          sr.setRank(rank++);         
          sr.addToIndexes(aJCas);
        }
        //System.err.println("CAS size(in consumer):"+aa.size());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

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
import edu.cmu.lti.oaqa.type.retrieval.FinalQuery;


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
    FSIterator<TOP> iter = aJCas.getJFSIndexRepository().getAllIndexedFS(FinalQuery.type);
    
    if(iter.isValid() && iter.hasNext()){

      FinalQuery query = (FinalQuery)iter.next();
      //String queryText = query.getQueryWithoutOp();
      String queryText = query.getQueryWithOp();
      //String queryText = query.getOriginalQuery();
      System.out.println("query:"+queryText);

      List<OntologyServiceResponse.Finding> findings = new LinkedList<OntologyServiceResponse.Finding>();
      try {
        OntologyServiceResponse.Result uniprotResult = service.findUniprotEntitiesPaged(queryText, 0);
        for (OntologyServiceResponse.Finding finding : uniprotResult.getFindings()) {
          findings.add(finding);
          //System.err.println("In Annotator Concept "+rank+":"+conceptSR);
        }

        OntologyServiceResponse.Result diseaseOntologyResult = service
                .findDiseaseOntologyEntitiesPaged(queryText, 0);
        for (OntologyServiceResponse.Finding finding : diseaseOntologyResult.getFindings()) {
          findings.add(finding);
        }
        OntologyServiceResponse.Result geneOntologyResult = service.
                findGeneOntologyEntitiesPaged(queryText,0, 10);
        for (OntologyServiceResponse.Finding finding : geneOntologyResult.getFindings()) {
          findings.add(finding);
        }
        OntologyServiceResponse.Result jochemResult = service.findJochemEntitiesPaged(queryText, 0);
        for (OntologyServiceResponse.Finding finding : jochemResult.getFindings()) {
          findings.add(finding);
        }
        OntologyServiceResponse.Result meshResult = service.findMeshEntitiesPaged(queryText, 0);
        for (OntologyServiceResponse.Finding finding : meshResult.getFindings()) {
          findings.add(finding);
        }

        System.out.println("Processing concept retrieval");
        createConceptFromFindings(query.getOriginalQuery(), aJCas, findings);
        int rank = 1;
        Iterator<ConceptSearchResult> it = TypeUtil.getRankedSearchResultByScore(aJCas,findings.size()).iterator();
        while(it.hasNext()){
          ConceptSearchResult sr =  it.next();
          sr.removeFromIndexes(aJCas);
          sr.setRank(rank++);         
          sr.addToIndexes(aJCas);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  private void createConceptFromFindings(String query, JCas aJCas,List<OntologyServiceResponse.Finding>findings){
    TokenizerLingpipe tokenizer = TokenizerLingpipe.getInstance();
    MyUtils ins = MyUtils.getInstance();

    

    for(OntologyServiceResponse.Finding finding : findings){
      if(finding.getScore() < 0.1)
        continue;
//      System.out.println("Matched Lable:"+finding.getMatchedLabel());
//      System.out.println("concept.lable:"+finding.getConcept().getLabel());
      String keyword = tokenizer.tokenize(finding.getConcept().getLabel());
      double score = ins.computeCosineSimilarity(query, keyword);

      score += ins.computeCosineSimilarity(finding.getMatchedLabel(), query);
      score /= 2.0;
      Concept concept = new Concept(aJCas);
      concept.setName(finding.getConcept().getLabel());

      ConceptSearchResult conceptSR = TypeFactory.createConceptSearchResult(
              aJCas, concept, finding.getConcept().getUri().replace("2014", "2012"),score, 
              finding.getConcept().getLabel(), 0, query, 
              finding.getConcept().getTermId(),new ArrayList<>());
      
      conceptSR.addToIndexes(aJCas);
    }
    
  }
}

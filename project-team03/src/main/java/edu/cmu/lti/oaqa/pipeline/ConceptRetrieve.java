package edu.cmu.lti.oaqa.pipeline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.resource.ResourceInitializationException;

import util.TypeFactory;
import util.Utils;
import edu.cmu.lti.oaqa.bio.bioasq.services.GoPubMedService;
import edu.cmu.lti.oaqa.bio.bioasq.services.OntologyServiceResponse;
import edu.cmu.lti.oaqa.type.kb.Concept;
import edu.cmu.lti.oaqa.type.retrieval.AtomicQueryConcept;
import edu.cmu.lti.oaqa.type.retrieval.ConceptSearchResult;

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
    
    if(iter.isValid() && iter.hasNext()){
      AtomicQueryConcept query = (AtomicQueryConcept)iter.next();
      String text = query.getText();
      try {
        OntologyServiceResponse.Result uniprotResult = service.findUniprotEntitiesPaged(text, 0);
        //OntologyServiceResponse.Result meshResult = service.findMeshEntitiesPaged(text, 0);
        int rank = 0;
        Concept concept = new Concept(aJCas);
        String label = null;
        List<String>uris = new LinkedList<String>();
        for (OntologyServiceResponse.Finding finding : uniprotResult.getFindings()) {
          if(label==null)
            label = finding.getConcept().getLabel();
          ConceptSearchResult conceptSR = TypeFactory.createConceptSearchResult(
                  aJCas, concept, finding.getConcept().getUri(),finding.getScore(), 
                  finding.getConcept().getLabel(),rank++, text, 
                  finding.getConcept().getTermId(),new ArrayList<>());
          uris.add(finding.getConcept().getUri());
          conceptSR.addToIndexes(aJCas);
        }
        concept.setUris(Utils.createStringList(aJCas,uris));
        concept.setName(label);
        System.out.println(label);
        concept.addToIndexes(aJCas);
        
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}

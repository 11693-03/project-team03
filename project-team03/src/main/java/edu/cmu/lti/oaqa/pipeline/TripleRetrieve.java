package edu.cmu.lti.oaqa.pipeline;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.http.client.ClientProtocolException;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.resource.ResourceInitializationException;

import util.TypeFactory;
import edu.cmu.lti.oaqa.bio.bioasq.services.GoPubMedService;
import edu.cmu.lti.oaqa.bio.bioasq.services.LinkedLifeDataServiceResponse;
import edu.cmu.lti.oaqa.type.kb.Triple;
import edu.cmu.lti.oaqa.type.retrieval.AtomicQueryConcept;
import edu.cmu.lti.oaqa.type.retrieval.TripleSearchResult;
/**
 * 
 * @author handixu
 *
 */
public class TripleRetrieve extends JCasAnnotator_ImplBase{
  
  private GoPubMedService service;
  
  @Override
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);
    try {
      service = new GoPubMedService("project.properties");
    } catch (ConfigurationException e) {
      System.out.println("Unable to start GoPubMedService");
      e.printStackTrace();
    }
   
  }

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    FSIterator<TOP> interator = aJCas.getJFSIndexRepository().getAllIndexedFS(
            AtomicQueryConcept.type);
    AtomicQueryConcept query = (AtomicQueryConcept) interator.next();
    String keywords = query.getText();
    
    try {
      LinkedLifeDataServiceResponse.Result linkedLifeDataResult = service
              .findLinkedLifeDataEntitiesPaged(keywords, 0, 1);
      int rank = 1;
      for (LinkedLifeDataServiceResponse.Entity entity : linkedLifeDataResult.getEntities()) {
          LinkedLifeDataServiceResponse.Relation relation = entity.getRelations().get(0);
          Triple triple = TypeFactory.createTriple(aJCas, relation.getSubj(), relation.getPred(), relation.getObj());
          triple.addToIndexes();
          TripleSearchResult searchResult = new TripleSearchResult(aJCas);
          searchResult.setRank(rank++);
          searchResult.setTriple(triple);
          searchResult.addToIndexes();
      }
      
    } catch (ClientProtocolException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }

}

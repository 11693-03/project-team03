package edu.cmu.lti.oaqa.pipeline;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.resource.ResourceInitializationException;

import util.TypeFactory;
import util.TypeUtil;
import edu.cmu.lti.oaqa.bio.bioasq.services.GoPubMedService;
import edu.cmu.lti.oaqa.bio.bioasq.services.PubMedSearchServiceResponse;
import edu.cmu.lti.oaqa.type.retrieval.AtomicQueryConcept;
import edu.cmu.lti.oaqa.type.retrieval.ConceptSearchResult;
import edu.cmu.lti.oaqa.type.retrieval.Document;

public class DocumentRetrieve extends JCasAnnotator_ImplBase {
  GoPubMedService service;
  public static final String uriPrefix = "http://www.ncbi.nlm.nih.gov/pubmed/";
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
    if (iter.isValid() && iter.hasNext()) {
      AtomicQueryConcept query = (AtomicQueryConcept)iter.next();
      String text = query.getText();
      try {
        PubMedSearchServiceResponse.Result pubmedResult = service.findPubMedCitations(text, 0);
        int rank = 1;
        for(PubMedSearchServiceResponse.Document docs : pubmedResult.getDocuments()){
          Document doc = TypeFactory.createDocument(aJCas, uriPrefix+docs.getPmid(), text,
                  rank++, text, docs.getTitle(), docs.getPmid());
          doc.addToIndexes();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
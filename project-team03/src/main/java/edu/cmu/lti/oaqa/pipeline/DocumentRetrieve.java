package edu.cmu.lti.oaqa.pipeline;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.resource.ResourceInitializationException;

import util.MyLemmatizer;
import util.MyUtils;
import util.NERLingpipe;
import util.TokenizerLingpipe;
import util.TypeFactory;
import util.TypeUtil;
import edu.cmu.lti.oaqa.bio.bioasq.services.GoPubMedService;
import edu.cmu.lti.oaqa.bio.bioasq.services.PubMedSearchServiceResponse;
import edu.cmu.lti.oaqa.type.retrieval.AtomicQueryConcept;
import edu.cmu.lti.oaqa.type.retrieval.ConceptSearchResult;
import edu.cmu.lti.oaqa.type.retrieval.Document;

/**
 *  This class retrieves documents related to the query, 
 *  using apis provided by PubMed
 *  @author Michael Zhuang
 *
 ***/

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
      
      MyUtils ins = MyUtils.getInstance();
//      MyLemmatizer lem = MyLemmatizer.getInstance();
//      NERLingpipe ling = NERLingpipe.getInstance();
      TokenizerLingpipe tokenizer = TokenizerLingpipe.getInstance();
      //httpClient = HttpClients.createDefault();
      try {
        PubMedSearchServiceResponse.Result pubmedResult = service.findPubMedCitations(text, 0, 200);
        for(PubMedSearchServiceResponse.Document docs : pubmedResult.getDocuments()){
          String url = uriPrefix+docs.getPmid();
          String keywords = docs.getTitle();
          //keywords = lem.lemmatize(keywords);
          //keywords = ling.extractKeywords(keywords);
          keywords = tokenizer.tokenize(keywords);
          double score = ins.computeCosineSimilarity(text, keywords);
          Document doc = TypeFactory.createDocument(aJCas, url, text,
                  999, text, docs.getTitle(), docs.getPmid());
          if(score < 0)
             continue;
          //System.out.println(score);
          doc.setScore(score);
          doc.addToIndexes();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

      System.out.println("Processing document retrieval");
      Collection<Document> documents = TypeUtil.getRankedDocumentByScore(aJCas,300);
      LinkedList<Document> documentList = new LinkedList<Document>();
      documentList.addAll(documents);
      int rank = 1;
      for(Document d : documentList){
        d.removeFromIndexes();
        d.setRank(rank++);
        d.addToIndexes(aJCas);
      }
    }
  }
}
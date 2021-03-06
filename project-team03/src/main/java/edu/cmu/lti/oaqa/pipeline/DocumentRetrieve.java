package edu.cmu.lti.oaqa.pipeline;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

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
import edu.cmu.lti.oaqa.bio.bioasq.services.GoPubMedService;
import edu.cmu.lti.oaqa.bio.bioasq.services.PubMedSearchServiceResponse;
import edu.cmu.lti.oaqa.type.retrieval.AtomicQueryConcept;
import edu.cmu.lti.oaqa.type.retrieval.ConceptSearchResult;
import edu.cmu.lti.oaqa.type.retrieval.Document;
import edu.cmu.lti.oaqa.type.retrieval.FinalQuery;

/**
 * This class retrieves documents related to the query, 
 * using apis provided by PubMed.
 * It also computes the relevance between the documents we retrieved and 
 * the queries. The relevance score will be used for ranking.
 * 
 * @author Michael Zhuang
 *
 ***/

public class DocumentRetrieve extends JCasAnnotator_ImplBase {
  private static int docLimits = 151;

  GoPubMedService service;

  public static final String uriPrefix = "http://www.ncbi.nlm.nih.gov/pubmed/";

  public static String Properties = "ProjectProperties";

  public void initialize(UimaContext aContext) throws ResourceInitializationException {
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
    if (iter.isValid() && iter.hasNext()) {
      FinalQuery query = (FinalQuery) iter.next();
       String queryText = query.getQueryWithOp();
      //String queryText = query.getOriginalQuery();
      //String queryText = query.getQueryWithoutOp();
      MyUtils ins = MyUtils.getInstance();
      TokenizerLingpipe tokenizer = TokenizerLingpipe.getInstance();
      Collection<Document> documents = null;
      do {
        try {
          PubMedSearchServiceResponse.Result pubmedResult = service.findPubMedCitations(queryText,
                  0, 200);
          for (PubMedSearchServiceResponse.Document docs : pubmedResult.getDocuments()) {
            String url = uriPrefix + docs.getPmid();
            String keywords = docs.getTitle();
            keywords = tokenizer.tokenize(keywords);
            double score = 0;
            score += ins.computeCosineSimilarity(keywords, query.getKeyword());
            if (docs.getMeshHeading() != null) {
              score += ins
                      .computeCosineSimilarity(docs.getMeshHeading(), query.getKeyword());
              score /= 2.0;
            }
            Document doc = TypeFactory.createDocument(aJCas, url, docs.getDocumentAbstract(), 999,
                    query.getOriginalQuery(), docs.getTitle(), docs.getPmid());
            doc.setScore(score);
            doc.addToIndexes();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
        System.out.println("Processing document retrieval");
        documents = TypeUtil.getRankedDocumentByScore(aJCas, TypeUtil.getRankedDocuments(aJCas)
                .size());
        if(queryText.equals(query.getKeyword()))
          break;
        else
          queryText = query.getKeyword();
      } while (documents.size()==0);
      LinkedList<Document> documentList = new LinkedList<Document>();
      documentList.addAll(documents);
      int rank = 1;
      for (Document d : documentList) {
        d.removeFromIndexes();
        d.setRank(rank++);
        if (rank > docLimits)
          continue;
        d.addToIndexes(aJCas);
      }
    }
  }
}
package edu.cmu.lti.oaqa.pipeline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import edu.cmu.lti.oaqa.type.retrieval.Document;
import util.TypeUtil;

public class SnippetRetrieval extends JCasAnnotator_ImplBase {
  private static final String PREFIX = "http://metal.lti.cs.cmu.edu:30002/pmc/";

  private CloseableHttpClient httpClient;

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    Collection<Document> docList = TypeUtil.getRankedDocuments(aJCas);
    List<String> pmids = new LinkedList<String>();
    for (Document doc : docList) {
      pmids.add(doc.getDocId());
    }
    httpClient = HttpClients.createDefault();
    for (String pmid : pmids) {
      String url = PREFIX + pmid;
      HttpGet httpGet = new HttpGet(url);
      try {
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
          BufferedReader buffer = new BufferedReader(new InputStreamReader(entity.getContent()));
          String line;
          while ((line = buffer.readLine()) != null) {
           // System.out.println(line);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }
}

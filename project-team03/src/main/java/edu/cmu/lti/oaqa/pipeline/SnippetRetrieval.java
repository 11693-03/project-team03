package edu.cmu.lti.oaqa.pipeline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import json.gson.SectionSet;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import edu.cmu.lti.oaqa.type.retrieval.Document;
import edu.cmu.lti.oaqa.type.retrieval.Passage;
import util.SentenceChunker;
import util.TypeUtil;

public class SnippetRetrieval extends JCasAnnotator_ImplBase {
  private static final String PREFIX = "http://metal.lti.cs.cmu.edu:30002/pmc/";
  //private static final String PREFIX = "http://www.ncbi.nlm.nih.gov/pubmed/";

  private CloseableHttpClient httpClient;

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    Collection<Document> docList = TypeUtil.getRankedDocuments(aJCas);
    List<String> pmids = new LinkedList<String>();
    for (Document doc : docList) {
      pmids.add(doc.getDocId());
      //System.out.println("docID:"+doc.getDocId());
    }
    httpClient = HttpClients.createDefault();
    SentenceChunker ins = SentenceChunker.getInstance();
    for (String pmid : pmids) {
      String url = PREFIX + pmid;
      HttpGet httpGet = new HttpGet(url);
      try {
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
          BufferedReader buffer = new BufferedReader(new InputStreamReader(entity.getContent()));
          String json = "";
          String line;
          while ((line = buffer.readLine()) != null) {
            json += line;
          }
          //System.out.println("json:"+json);
          SectionSet sectionSet = SectionSet.load(json);
          List<String>sections = sectionSet.getSections();
          for(int i = 0; i < sections.size(); i++){
            HashMap<Integer, Integer> r = ins.chunk(sections.get(i));
            Iterator<Integer> iter = r.keySet().iterator();
            while(iter.hasNext()){
              int begin = iter.next();
              int end = r.get(begin);
              //System.out.println("zzzzzzzzzzzzzzzzz:"+sections.get(i).substring(begin, end));
              Passage snippet = new Passage(aJCas);
              snippet.setDocId(pmid);
              snippet.setTitle(sectionSet.getTitle());
              snippet.setOffsetInBeginSection(begin);
              snippet.setOffsetInEndSection(end);
              snippet.setBeginSection("sections."+String.valueOf(i));
              snippet.setEndSection("sections."+String.valueOf(i));
              snippet.setText(sections.get(i).substring(begin, end));
              snippet.addToIndexes(aJCas);
            }            
          }
          //System.out.println("Snippet:"+sectionSet);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }
}

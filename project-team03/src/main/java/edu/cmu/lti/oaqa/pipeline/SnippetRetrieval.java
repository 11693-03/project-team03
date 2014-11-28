package edu.cmu.lti.oaqa.pipeline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import json.gson.SectionSet;
import json.gson.Snippet;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;

import edu.cmu.lti.oaqa.type.retrieval.AtomicQueryConcept;
import edu.cmu.lti.oaqa.type.retrieval.Document;
import edu.cmu.lti.oaqa.type.retrieval.Passage;
import util.MyUtils;
import util.SentenceChunker;
import util.TokenizerLingpipe;
import util.TypeUtil;

public class SnippetRetrieval extends JCasAnnotator_ImplBase {
  private static final String PREFIX = "http://metal.lti.cs.cmu.edu:30002/pmc/";
  //private static final String PREFIX = "http://www.ncbi.nlm.nih.gov/pubmed/";

  private CloseableHttpClient httpClient;

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    FSIterator<TOP> qIter = aJCas.getJFSIndexRepository().getAllIndexedFS(AtomicQueryConcept.type);
    String qText = null;
    if (qIter.isValid() && qIter.hasNext()) {
      AtomicQueryConcept query = (AtomicQueryConcept)qIter.next();
     qText = query.getText();
    }
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
      List<Snippet> snippets = new LinkedList<Snippet>();
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
              double conf = evaluateSimilarity(qText, sections.get(i).substring(begin, end));
              if(conf < 0.1)
                continue;
              Snippet snippet = new Snippet(pmid,sections.get(i).substring(begin, end),
                      begin,end,"sections."+String.valueOf(i),"sections."+String.valueOf(i),
                      sectionSet.getTitle(), conf);
                snippets.add(snippet);
            }            
          }
          //System.out.println("Snippet:"+sectionSet);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      Collections.sort(snippets);
      for(Snippet snippet : snippets){
        Passage p = new Passage(aJCas);
        p.setDocId(snippet.getDocument());
        p.setTitle(snippet.getTitle());
        p.setOffsetInBeginSection(snippet.getOffsetInBeginSection());
        p.setOffsetInEndSection(snippet.getOffsetInEndSection());
        p.setBeginSection(snippet.getBeginSection());
        p.setEndSection(snippet.getEndSection());
        p.setText(snippet.getText());
        p.addToIndexes(aJCas);
      }
    }
  }
  private double evaluateSimilarity(String query, String snippet){
    TokenizerLingpipe ins = TokenizerLingpipe.getInstance();
    snippet = ins.tokenize(snippet);
    MyUtils cosEval = MyUtils.getInstance();
    return cosEval.computeCosineSimilarity(query, snippet);
  }
}

package edu.cmu.lti.oaqa.pipeline;

import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import util.AbnerAnnotator;
import util.MyLemmatizer;
import util.NERLingpipe;
import util.TokenizerLingpipe;
import util.TypeUtil;
import edu.cmu.lti.oaqa.type.input.Question;
import edu.cmu.lti.oaqa.type.retrieval.AtomicQueryConcept;
import edu.cmu.lti.oaqa.type.retrieval.ConceptSearchResult;

public class InitialAnnotator extends JCasAnnotator_ImplBase{
/**
 *  This class receives Question type in JCas, 
 *  remove the question mark, and create a new type AtomicQuestion, 
 *  containing the original query and the modficated query.
 *  @author Michael Zhuang 
 * 
 **/
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
      Question question = TypeUtil.getQuestion(aJCas);
      String originalQuery = question.getText();
      Vector<String>keyWords = AbnerAnnotator.getInstance().getGeneSpan(originalQuery);
      StringBuffer sb = new StringBuffer();
      for(String v : keyWords){
        sb.append(v+" ");
      }
      TokenizerLingpipe ins = TokenizerLingpipe.getInstance();
      String modificatedQuery = originalQuery.replace("?", "");
      modificatedQuery = ins.tokenize(modificatedQuery);
      MyLemmatizer mLem = MyLemmatizer.getInstance();
      modificatedQuery = mLem.lemmatize(modificatedQuery);
//      NERLingpipe ling = NERLingpipe.getInstance();
//      try {
//        modificatedQuery = ling.extractKeywords(modificatedQuery);
//      } catch (ClassNotFoundException | IOException e) {
//        e.printStackTrace();
//      }
      System.out.println(originalQuery+"->"+modificatedQuery);
      for(String token : modificatedQuery.split("\\s+")){
        if(token.length()<=1)
          continue;
        AtomicQueryConcept atomic = new AtomicQueryConcept(aJCas);
        atomic.setOriginalText(sb.toString().trim());
        atomic.setText(token);
        atomic.addToIndexes(aJCas);
//        if(!token.equals(mLem.lemmatize(token).trim())){
//          token = mLem.lemmatize(token).trim();
//          atomic = new AtomicQueryConcept(aJCas);
//          atomic.setOriginalText(originalQuery);
//          atomic.setText(token);
//          atomic.addToIndexes(aJCas);
//        }
      }        
  }  
}

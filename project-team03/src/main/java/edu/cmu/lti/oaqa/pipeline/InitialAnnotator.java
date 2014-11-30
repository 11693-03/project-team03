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
import util.StanfordNER;
import util.TokenizerLingpipe;
import util.TypeUtil;
import edu.cmu.lti.oaqa.type.input.Question;
import edu.cmu.lti.oaqa.type.retrieval.AtomicQueryConcept;
import edu.cmu.lti.oaqa.type.retrieval.ConceptSearchResult;

public class InitialAnnotator extends JCasAnnotator_ImplBase{
/**
 *  This class receives Question type in JCas, 
 *  remove the question mark, do lemmatization, remove stop words,
 *  extract keyword. 
 *  AtomicQueryConcept stores every token of the processed question and keyword.
 *  @author Michael Zhuang 
 * 
 **/
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
      Question question = TypeUtil.getQuestion(aJCas);
      String originalQuery = question.getText();
      
      TokenizerLingpipe ins = TokenizerLingpipe.getInstance();
      String modificatedQuery = originalQuery.replace("?", "");
      modificatedQuery = ins.tokenize(modificatedQuery);
      MyLemmatizer mLem = MyLemmatizer.getInstance();
      modificatedQuery = mLem.lemmatize(modificatedQuery);
      Vector<String>keyWords = StanfordNER.getInstance().getGeneSpans(modificatedQuery);
      StringBuffer sb = new StringBuffer();
      sb.append(keyWords.get(0));
      for(String v : keyWords){
        sb.append(" "+v);
      }
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

package edu.cmu.lti.oaqa.pipeline;

import java.util.Collection;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

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
      AtomicQueryConcept atomic = new AtomicQueryConcept(aJCas);
      atomic.setOriginalText(question.getText());
      atomic.setText(question.getText().replace("?", ""));
      atomic.addToIndexes(aJCas);        
  }  
}

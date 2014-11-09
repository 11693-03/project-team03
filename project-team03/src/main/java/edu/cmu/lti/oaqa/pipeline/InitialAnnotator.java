package edu.cmu.lti.oaqa.pipeline;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import util.TypeUtil;
import edu.cmu.lti.oaqa.type.input.Question;
import edu.cmu.lti.oaqa.type.retrieval.AtomicQueryConcept;

public class InitialAnnotator extends JCasAnnotator_ImplBase{

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
      Question question = TypeUtil.getQuestion(aJCas);
      AtomicQueryConcept atomic = new AtomicQueryConcept(aJCas);
      atomic.setOriginalText(question.getText());
      atomic.setText(question.getText().replace("?", ""));
      atomic.addToIndexes(aJCas);          
  }  
}

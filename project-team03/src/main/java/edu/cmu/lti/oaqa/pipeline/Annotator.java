package edu.cmu.lti.oaqa.pipeline;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import edu.cmu.lti.oaqa.type.input.Question;
import edu.cmu.lti.oaqa.type.retrieval.AtomicQueryConcept;

public class Annotator extends JCasAnnotator_ImplBase{

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    FSIterator<Annotation> iter = aJCas.getAnnotationIndex(Question.type).iterator();
    if(iter.isValid() && iter.hasNext()){
      iter.moveToNext();
      Question question = (Question)iter.get();
      AtomicQueryConcept atomic = new AtomicQueryConcept(aJCas);
      atomic.setOriginalText(question.getText());
      atomic.setText(question.getText().replace("?", ""));
      atomic.addToIndexes(aJCas);      
    }
  }
  
}

package edu.cmu.lti.oaqa.pipeline;

import org.apache.uima.jcas.JCas;

import edu.cmu.lti.oaqa.type.retrieval.ComplexQueryConcept;

public class ComplexQueryConstructor {
  public void process(JCas aJCas){
    ComplexQueryConcept cqc = new ComplexQueryConcept(aJCas);
    
  }
  public static void main(String[] args){
    
    
  }
}

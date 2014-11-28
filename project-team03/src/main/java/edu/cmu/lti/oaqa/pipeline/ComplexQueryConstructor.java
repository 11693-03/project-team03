package edu.cmu.lti.oaqa.pipeline;

import java.util.LinkedList;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;

import util.JAWSApi;
import util.Utils;
import edu.cmu.lti.oaqa.type.retrieval.AtomicQueryConcept;
import edu.cmu.lti.oaqa.type.retrieval.ComplexQueryConcept;
import edu.cmu.lti.oaqa.type.retrieval.QueryOperator;

public class ComplexQueryConstructor extends JCasAnnotator_ImplBase {
  @Override
  public void process(JCas aJCas) {
    FSIterator<TOP> iter = aJCas.getJFSIndexRepository().getAllIndexedFS(AtomicQueryConcept.type);
    while (iter.isValid() && iter.hasNext()) {
      AtomicQueryConcept query = (AtomicQueryConcept) iter.next();
      String queryToken = query.getText();
      ComplexQueryConcept cqc = new ComplexQueryConcept(aJCas);
      QueryOperator v = new QueryOperator(aJCas);
      List<String> syn = JAWSApi.getInstance().getSynonyms(queryToken, 1);
      v.setArgs(Utils.createStringList(aJCas, syn));
      v.setName("OR");
      cqc.setOperator(v);
      cqc.addToIndexes(aJCas);
    }
  }

  public static void main(String[] args) {

  }
}

package edu.cmu.lti.oaqa.pipeline;

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

/**
 *  This class gets each token of the query, then use wordNet to retrieve all synonyms 
 *  of the token, and store them in complexQueryConcept.
 *  
 *  @author Michael Zhuang
 *   
 ***/
public class ComplexQueryConstructor extends JCasAnnotator_ImplBase {
  private static int numOfSynonym = 1;
  @Override
  public void process(JCas aJCas) {
    FSIterator<TOP> iter = aJCas.getJFSIndexRepository().getAllIndexedFS(AtomicQueryConcept.type);
    while (iter.isValid() && iter.hasNext()) {
      AtomicQueryConcept query = (AtomicQueryConcept) iter.next();
      String queryToken = query.getText();
      ComplexQueryConcept cqc = new ComplexQueryConcept(aJCas);
      QueryOperator v = new QueryOperator(aJCas);
      List<String> syn = JAWSApi.getInstance().getSynonyms(queryToken, numOfSynonym);
      v.setArgs(Utils.createStringList(aJCas, syn));
      v.setName("SYNONYM");
      cqc.setOperator(v);
      cqc.addToIndexes(aJCas);
    }
  }
  public static void main(String[] args) {

  }
}

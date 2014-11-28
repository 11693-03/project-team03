package edu.cmu.lti.oaqa.pipeline;

import java.util.LinkedList;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.StringList;
import org.apache.uima.jcas.cas.TOP;

import util.Utils;
import edu.cmu.lti.oaqa.type.retrieval.AtomicQueryConcept;
import edu.cmu.lti.oaqa.type.retrieval.ComplexQueryConcept;
import edu.cmu.lti.oaqa.type.retrieval.FinalQuery;
import edu.cmu.lti.oaqa.type.retrieval.QueryOperator;

public class QueryCombiner extends JCasAnnotator_ImplBase{

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    FSIterator<TOP> iter = aJCas.getJFSIndexRepository().getAllIndexedFS(ComplexQueryConcept.type);
    StringBuffer queryWithOp = new StringBuffer();
    StringBuffer queryWithoutOp = new StringBuffer();
    while(iter.isValid() && iter.hasNext()){
      ComplexQueryConcept cqc = (ComplexQueryConcept)iter.next();
      QueryOperator opn = cqc.getOperator();
      StringList strlist = opn.getArgs();
      System.out.println(strlist.toString());
      if(queryWithOp.length()==0){
        queryWithOp.append(strlist.toString().replace(" ", "OR")+" ");
      }else{
        queryWithOp.append("AND"+strlist.toString()+" ");
      }
      queryWithoutOp.append(strlist.toString()+" ");
    }
    FinalQuery fquery = new FinalQuery(aJCas);
    fquery.setQueryWIthOp(queryWithOp.toString().trim());
    fquery.setQueryWithoutOp(queryWithoutOp.toString().trim());
    fquery.addToIndexes(aJCas);
  }
  public static void pro(JCas aJCas){
    List<String>test = new LinkedList<String>();
    test.add("abc");
    test.add("zcxv");
    test.add("bnm");
    StringList aa = Utils.createStringList(aJCas, test);
    System.out.println(aa.toString());
  }
}

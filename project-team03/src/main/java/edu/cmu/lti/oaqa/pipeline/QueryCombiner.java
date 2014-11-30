package edu.cmu.lti.oaqa.pipeline;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.StringList;
import org.apache.uima.jcas.cas.TOP;
import org.uimafit.util.FSCollectionFactory;

import util.Utils;
import edu.cmu.lti.oaqa.type.input.Question;
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
      Collection<String> syn = FSCollectionFactory.create(strlist);
      List<String>synList = new LinkedList<String>();
      synList.addAll(syn);
      
      StringBuffer syns = new StringBuffer();
      syns.append("("+synList.get(0));
      for(int i = 1; i < synList.size(); i++){
        syns.append(" OR "+synList.get(i));
      }
      syns.append(")");
      if(queryWithOp.length()==0){
        queryWithoutOp.append(synList.get(0)+" ");
        queryWithOp.append(syns.toString()+" ");
      }else{
        queryWithOp.append("AND "+syns.toString()+" ");
        queryWithoutOp.append(syns.toString().replaceAll(" OR ", " ").replace("(","").replace(")"," "));
      }
    }
    StringBuffer sb = new StringBuffer();
    String keyword = null;
    FSIterator<TOP> qit = aJCas.getJFSIndexRepository().getAllIndexedFS(AtomicQueryConcept.type);
    while (qit.isValid() && qit.hasNext()){
      AtomicQueryConcept aq = (AtomicQueryConcept)qit.next();
      sb.append(aq.getText()+" ");
      keyword = aq.getOriginalText();
    }
    qit = aJCas.getJFSIndexRepository().getAllIndexedFS(Question.type);
    String qID = null;
    if(qit.isValid() && qit.hasNext()){
      Question q = (Question)qit.next();
      qID = q.getId();
    }
    FinalQuery fquery = new FinalQuery(aJCas);
    fquery.setQueryWithOp(queryWithOp.toString().trim());
    fquery.setQueryWithoutOp(queryWithoutOp.toString().trim());
    fquery.setOriginalQuery(sb.toString().trim());
    fquery.setQueryID(qID);
    fquery.setKeyword(keyword);
//    System.out.println(queryWithOp.toString().trim());
//    System.out.println(queryWithoutOp.toString().trim());
    
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

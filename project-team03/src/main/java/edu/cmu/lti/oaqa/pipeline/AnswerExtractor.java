package edu.cmu.lti.oaqa.pipeline;

import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;

import util.AbnerAnnotator;
import util.JAWSApi;
import util.MyLemmatizer;
import util.Utils;
import edu.cmu.lti.oaqa.type.answer.Answer;
import edu.cmu.lti.oaqa.type.retrieval.FinalQuery;
import edu.cmu.lti.oaqa.type.retrieval.Passage;

/**
 *  This class use Abner named entity recognizer to extract 
 *  certain phrases from the snippets we retrieved as the candidate answer.
 *  Then we use wordNet again to find synonyms of each candidate answer 
 *  as its variants.
 *  
 *  @author Michael Zhuang
 * 
 **/
public class AnswerExtractor extends JCasAnnotator_ImplBase{
  private final static int numOfSyn = 3;
  private boolean isExistedInQuestion(String namedEntity, String query){
    MyLemmatizer mLem = MyLemmatizer.getInstance();
    namedEntity = mLem.lemmatize(namedEntity.toLowerCase());
    return query.contains(namedEntity);
  }
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    FSIterator<TOP> qIt = aJCas.getJFSIndexRepository().getAllIndexedFS(FinalQuery.type);
    String query = null;
    if(qIt.isValid() && qIt.hasNext()){
      FinalQuery fq = (FinalQuery) qIt.next();
      query = fq.getOriginalQuery();
//      query = fq.getQueryWithOp();
//      query = fq.getQueryWithoutOp();
    }    
    FSIterator<TOP> Iter = aJCas.getJFSIndexRepository().getAllIndexedFS(Passage.type);
    AbnerAnnotator ner = AbnerAnnotator.getInstance();
    JAWSApi syn = JAWSApi.getInstance();    
    HashSet<String> answerSoFar = new HashSet<String>();
    while(Iter.isValid() && Iter.hasNext()){
      Passage p = (Passage) Iter.next();
      String text = p.getText();
      Vector<String> namedEntities = ner.getGeneSpan(text);
      for(String namedEntity : namedEntities){
        if(answerSoFar.contains(namedEntity))
          continue;
        answerSoFar.add(namedEntity);
        Answer answer = new Answer(aJCas);
        answer.setRank(p.getRank());
        List<String>synonyms = syn.getSynonyms(namedEntity, numOfSyn);
        answer.setText(synonyms.get(0));
        synonyms.remove(0);
        for(int i = 0; i < synonyms.size(); i++){
          if(isExistedInQuestion(synonyms.get(i), query)||
                  synonyms.get(i).equals(answer.getText().toLowerCase())){
            synonyms.remove(i);
          }
        }
        answer.setVariants(Utils.createStringList(aJCas, synonyms));
        answer.addToIndexes(aJCas);
      }
    }
  }

}

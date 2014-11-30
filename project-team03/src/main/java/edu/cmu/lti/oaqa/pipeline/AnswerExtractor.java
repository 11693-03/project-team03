package edu.cmu.lti.oaqa.pipeline;

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
    
    while(Iter.isValid() && Iter.hasNext()){
      Passage p = (Passage) Iter.next();
      String text = p.getText();

      Vector<String> namedEntities = ner.getGeneSpan(text);
      for(String namedEntity : namedEntities){
        Answer answer = new Answer(aJCas);
        answer.setRank(p.getRank());
        List<String>synonyms = syn.getSynonyms(namedEntity, numOfSyn);
        answer.setText(synonyms.get(0));
        synonyms.remove(0); //The first element in the synonyms is the original text
        for(int i = 0; i < synonyms.size(); i++){
          if(isExistedInQuestion(synonyms.get(i), query)){
            synonyms.remove(i);
          }
        }
        answer.setVariants(Utils.createStringList(aJCas, namedEntities));
        answer.addToIndexes(aJCas);
      }
    }
  }

}

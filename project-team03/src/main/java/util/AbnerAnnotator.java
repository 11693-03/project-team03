package util;
import java.util.Iterator;
import java.util.Vector;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;

import abner.Tagger;


/**
 * This class annotates the input sentence by calling the Abner NLP.
 * @author Qiankun Zhuang
 */
public class AbnerAnnotator{
  private AbnerAnnotator(){
    try{
      tagger = new Tagger(Tagger.BIOCREATIVE);
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  private static AbnerAnnotator instance = null;
  public static AbnerAnnotator getInstance(){
    if(instance == null)
      instance = new AbnerAnnotator();
    return instance;
  }
  Tagger tagger;
  /**
   * Extract gene names from the input string
   * 
   * @param text
   *          The input string to be analyzed
   * @return Extracted gene names in the sentence
   * 
   */
  public Vector<String> getGeneSpan(String text){
    String[][] res = tagger.getEntities(text);
    Vector<String> Names = new Vector<String>();
    for(int i = 0; i < res[0].length; i++){
      Names.add(res[0][i]);
    }
    return Names;
  }
}
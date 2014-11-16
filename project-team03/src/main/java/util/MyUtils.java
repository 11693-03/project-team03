package util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyUtils {

  private static MyUtils instance = null;
  private MyUtils(){
    
  }
  public static MyUtils getInstance(){
    if(instance == null)
      instance = new MyUtils();
    return instance;
  }
  public double computeCosineSimilarity(Map<String, Integer> queryVector,
          String docTitle) {
    RemoveStopWords ins = RemoveStopWords.getInstance();
    String nTitle = ins.processStr(docTitle);
    String[] tokens = nTitle.split("\\s+");
    HashMap<String, Integer> docVector = new HashMap<String, Integer>();
    for(String t : tokens){
      if(!docVector.containsKey(t))
        docVector.put(t, 1);
      docVector.put(t, docVector.get(t)+1);
    }
    double cosine_similarity = 0.0;
    Iterator<String> wordInQuery = queryVector.keySet().iterator();
    int querySum = 0;
    while (wordInQuery.hasNext()) {
      String word = (String) wordInQuery.next();
      int freq = queryVector.get(word);
      querySum += freq * freq;
    }
    Iterator<String> wordInDoc = docVector.keySet().iterator();
    int docSum = 0;
    while (wordInDoc.hasNext()) {
      String word = (String) wordInDoc.next();
      int freq = docVector.get(word);
      docSum += freq * freq;
    }
    wordInQuery = queryVector.keySet().iterator();
    int up = 0;
    while (wordInQuery.hasNext()) {
      String word = (String) wordInQuery.next();
      if (docVector.containsKey(word)) {
        up += docVector.get(word) * queryVector.get(word);
      }
    }
    cosine_similarity = (double) up / (Math.sqrt((double) querySum) * Math.sqrt((double) docSum));
    return cosine_similarity;
  }
}

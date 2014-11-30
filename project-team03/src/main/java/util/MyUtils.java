package util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
  public double computeCosineSimilarity(String str1,
          String str2) {
    return computeCosineSimilarity(string2map(str1), string2map(str2));
  }
  private HashMap<String, Integer> string2map(String str){
    HashMap<String, Integer> Vector = new HashMap<String, Integer>();
    String[] tokens2 = str.split("\\s+");
    for(String t : tokens2){
      if(!Vector.containsKey(t))
        Vector.put(t, 1);
      Vector.put(t, Vector.get(t)+1);
    }
    return Vector;
  }
  public double computeCosineSimilarity(List<String>str1, String str2){
    HashMap<String, Integer> Vector1 = new HashMap<String, Integer>();
    for(String t : str1){
      if(!Vector1.containsKey(t))
        Vector1.put(t, 1);
      Vector1.put(t, Vector1.get(t)+1);
    }
    HashMap<String, Integer> Vector2 = string2map(str2);
    return computeCosineSimilarity(Vector1, Vector2);
  }
  public double computeCosineSimilarity(HashMap<String, Integer> Vector1, HashMap<String, Integer> Vector2){
    double cosine_similarity = 0.0;
    Iterator<String> wordInQuery = Vector1.keySet().iterator();
    int querySum = 0;
    while (wordInQuery.hasNext()) {
      String word = (String) wordInQuery.next();
      int freq = Vector1.get(word);
      querySum += freq * freq;
    }
    Iterator<String> wordInDoc = Vector2.keySet().iterator();
    int docSum = 0;
    while (wordInDoc.hasNext()) {
      String word = (String) wordInDoc.next();
      int freq = Vector2.get(word);
      docSum += freq * freq;
    }
    wordInQuery = Vector1.keySet().iterator();
    int up = 0;
    while (wordInQuery.hasNext()) {
      String word = (String) wordInQuery.next();
      if (Vector2.containsKey(word)) {
        up += Vector2.get(word) * Vector1.get(word);
      }
    }
    cosine_similarity = (double) up / (Math.sqrt((double) querySum) * Math.sqrt((double) docSum));
    return cosine_similarity;
  }
}

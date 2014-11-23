package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import json.gson.Snippet;

public class MyUtils {

  private static MyUtils instance = null;

  private MyUtils() {

  }

  public static MyUtils getInstance() {
    if (instance == null)
      instance = new MyUtils();
    return instance;
  }

  public double computeCosineSimilarity(Map<String, Integer> queryVector, String docTitle) {
    String[] tokens = docTitle.split("\\s+");
    HashMap<String, Integer> docVector = new HashMap<String, Integer>();
    for (String t : tokens) {
      if (!docVector.containsKey(t))
        docVector.put(t, 1);
      docVector.put(t, docVector.get(t) + 1);
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

  public static double calcSnippetPrecision(List<Snippet> gold, List<Snippet> test) {

    if (test.size() == 0)
      return 0;

    int overlap = computeCommonOfSnippets(gold, test);

    int testSize = 0;
    for (Snippet snip : test)
      testSize += (snip.getOffsetInEndSection() - snip.getOffsetInBeginSection());

    return ((double) overlap) / testSize;
  }

  public static double calcSnippetRecall(List<Snippet> gold, List<Snippet> test) {

    if (gold.size() == 0)
      return 0;

    int overlap = computeCommonOfSnippets(gold, test);

    int goldSize = 0;
    for (Snippet snip : gold)
      goldSize += (snip.getOffsetInEndSection() - snip.getOffsetInBeginSection());

    return ((double) overlap) / goldSize;
  }

  private static int computeCommonOfSnippets(List<Snippet> goldSnippets, List<Snippet> testSnippets) {

    int commonAmounts = 0;
    for (Snippet aTest : testSnippets) {
     // System.out.println("Test!"+aTest.getBeginSection()+aTest.getOffsetInBeginSection()+aTest.getEndSection()+aTest.getOffsetInEndSection());
      List<Snippet> hits = new ArrayList<Snippet>();
      for (Snippet aGold : goldSnippets) {
        if (aGold.getDocument().equals(aTest.getDocument())
                && aGold.getBeginSection().equals(aTest.getBeginSection())) {
          hits.add(aGold);
          //System.out.println("find!!!" + aGold.getBeginSection() + aGold.getOffsetInBeginSection()
          //        + aGold.getEndSection() + aGold.getOffsetInEndSection());
        }
      }
      // sum of common characters
      for (Snippet hit : hits) {
        int begin = Math.max(aTest.getOffsetInBeginSection(), hit.getOffsetInBeginSection());
        int end = Math.min(aTest.getOffsetInEndSection(), hit.getOffsetInEndSection());
        if (begin < end) {
          commonAmounts += (end - begin);
        }
      }
    }
    return commonAmounts;
  }
}

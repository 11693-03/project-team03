import java.util.HashMap;

import util.MyLemmatizer;
import util.MyUtils;
import util.RemoveStopWords;


public class Test {
  public static void main(String[] args){

    MyUtils ins = MyUtils.getInstance();
    MyLemmatizer lem = MyLemmatizer.getInstance();
    RemoveStopWords rem = RemoveStopWords.getInstance();
    String doc = "Gender and the treatment of immune-mediated chronic inflammatory diseases: rheumatoid arthritis, inflammatory bowel disease and psoriasis: an observational study.";
    String doc2 = "Is Rheumatoid Arthritis more common in men or women";
    doc = rem.processStr(doc);
    doc2 = rem.processStr(doc2);
    doc = lem.lemmatize(doc);
    doc2 = lem.lemmatize(doc2);
    
    String[] tokens = doc.split("\\s+");
    HashMap<String, Integer> qVector = new HashMap<String, Integer>();
    for(String t : tokens){
      if(!qVector.containsKey(t))
        qVector.put(t, 1);
      qVector.put(t, qVector.get(t)+1);
    }
    System.out.println(ins.computeCosineSimilarity(qVector, doc2));
  }
}

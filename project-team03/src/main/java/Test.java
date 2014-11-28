import java.util.HashMap;

import util.MyLemmatizer;
import util.MyUtils;


public class Test {
  public static void main(String[] args){

    MyUtils ins = MyUtils.getInstance();
    MyLemmatizer lem = MyLemmatizer.getInstance();
    String doc = "Gender and the treatment of immune-mediated chronic inflammatory diseases: rheumatoid arthritis, inflammatory bowel disease and psoriasis: an observational study.";
    String doc2 = "Is Rheumatoid Arthritis more common in men or women";
    doc = lem.lemmatize(doc);
    doc2 = lem.lemmatize(doc2);
    
    System.out.println(ins.computeCosineSimilarity(doc, doc2));
  }
}

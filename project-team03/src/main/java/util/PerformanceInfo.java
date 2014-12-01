package util;

import java.util.LinkedList;
import java.util.List;

public class PerformanceInfo {
  private List<Double> docPrecisionList;

  private List<Double> conceptPrecisionList;

  private List<Double> tripleList;
  
  private List<Double> answerPrecisionList;
  
  private List<Double> answerRecalList;
  
  
  private double docMAP;
  private double conceptMAP;
  private double tripleMAP;
  private double answerMAP;
  private double answerMAR;
  
  public PerformanceInfo() {
    docPrecisionList = new LinkedList<Double>();
    conceptPrecisionList = new LinkedList<Double>();
    tripleList = new LinkedList<Double>();
    answerPrecisionList = new LinkedList<Double>();
    answerRecalList = new LinkedList<Double>();
  }

  public void addDocAP(double precision) {
    docPrecisionList.add(precision);
  }

  public void addConceptAP(double precision) {
    conceptPrecisionList.add(precision);
  }

  public void addTripleAP(double precision) {
    docPrecisionList.add(precision);
  }

  private double computeMAP(List<Double> list) {
    System.err.println("listsize"+list.size());
    if (list.size() == 0)
      return 0;
    double total = 0;
    for (Double d : list)
      total += d;
    
    return total / list.size();

  }

  public void setDocMap() {
    docMAP = computeMAP(docPrecisionList);
  }

  public void setConceptMap() {
    conceptMAP = computeMAP(conceptPrecisionList);
  }

  public void setTripleMap() {
    tripleMAP = computeMAP(tripleList);
  }
  
  public double getDocMAP(){
    this.setDocMap();
    return docMAP;
  }
  
  public double getConceptMAP(){
    this.setConceptMap();
    return conceptMAP;
  }
  
  public double getTripleMAP(){
    this.setTripleMap();
    return tripleMAP;
  }
  
  public double getAnswerMAP(){
    return answerMAP;
  }
  
  public double getAnswerMAR(){
    return answerMAR;
  }
  
  public static double computeAnswerPrecision(List<List<String>> test, List<List<String>> gold){
    double count = 0;
    double size = 0;
    for(List<String> tl: test)
      for(String ts: tl)
        size++;
    
    for(List<String> l: gold){
      for(String s: l){
        for(List<String> tl: test)
          for(String ts: tl){
            System.err.println("test= "+ts+"-----gold ="+s );
            if(ts.contains(s))
              count++;
          }
      }
    }
    return count*1.0/size;
  }


}

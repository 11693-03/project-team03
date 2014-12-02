package util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class PerformanceInfo {
  private List<Double> docPrecisionList;

  private List<Double> conceptPrecisionList;

  private List<Double> tripleList;
  
  private List<Double> answerPrecisionList;
  
  private List<Double> answerRecalList;
  
  private List<Double> answerFMeasureList;
  
  
  private double docMAP;
  private double conceptMAP;
  private double tripleMAP;
  private double answerMAP;
  private double answerMAR;
  private double answerFMAP;
  
  public PerformanceInfo() {
    docPrecisionList = new LinkedList<Double>();
    conceptPrecisionList = new LinkedList<Double>();
    tripleList = new LinkedList<Double>();
    answerPrecisionList = new LinkedList<Double>();
    answerRecalList = new LinkedList<Double>();
    answerFMeasureList = new LinkedList<Double>();
  }

  public void addDocAP(double precision) {
    docPrecisionList.add(precision);
  }

  public void addConceptAP(double precision) {
    conceptPrecisionList.add(precision);
  }

  public void addTripleAP(double precision) {
    tripleList.add(precision);
  }
  
  public void addAnswerPrecision(double precision) {
    answerPrecisionList.add(precision);
  }
  
  public void addAnswerRecalList(double d){
    answerRecalList.add(d);
  }
  
  
  
  public void addAnswerFMeasure(double d){
    answerFMeasureList.add(d);
  }

  private double computeMAP(List<Double> list) {
    //System.err.println("listsize"+list.size());
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
  
  public void setAnswerMap(){
    answerMAP = computeMAP(answerPrecisionList);
  }
  
  public void setAnswerFMap(){
    answerFMAP = computeMAP(answerFMeasureList);
  }
  
  public void setAnswerMar(){
    answerMAR = computeMAP(answerRecalList);
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
    this.setAnswerMap();
    return answerMAP;
  }
  
  public double getAnswerMAR(){
    this.setAnswerMar();
    return answerMAR;
  }
  public double getAnswerFMAP(){
    this.setAnswerFMap();
    return answerFMAP;
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
            //System.err.println("test= "+ts+"-----gold ="+s );
            if(ts.contains(s))
              count++;
          }
      }
    }
    return count*1.0/size;
  }
  
  public static double getFMeasure(double a, double b){
    if((a+b)==0)
      return 0;
    return 2*a*b/(a+b);
  }
  
  public static double computeAnswerRecall(List<List<String>> test, List<List<String>> gold) {
    int count = 0;
    int size = 0;
    HashSet<String> testAnswer = new HashSet<String>();
    for (List<String> tests : test) {
      for (String t : tests) {
        testAnswer.add(t);
      }
    }
    for (List<String> goldens : gold){
      for (String golden : goldens)
        if (testAnswer.contains(golden))
          count++;
    }
    
    for (List<String> tl : gold)
      for (String ts : tl)
        size++;
    return count / (size * 1.0);
  }

}

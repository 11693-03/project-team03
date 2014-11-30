package util;

import java.util.LinkedList;
import java.util.List;

public class PerformanceInfo {
  private List<Double> docList;

  private List<Double> conceptList;

  private List<Double> tripleList;
  
  private List<Double> answerPrecisionList;
  
  private List<Double> answerRecalList;
  
  
  private double docMAP;
  private double conceptMAP;
  private double tripleMAP;
  private double answerMAP;
  private double answerMAR;
  
  public PerformanceInfo() {
    docList = new LinkedList<Double>();
    conceptList = new LinkedList<Double>();
    tripleList = new LinkedList<Double>();
  }

  public void addDocAP(double precision) {
    docList.add(precision);
  }

  public void addConceptAP(double precision) {
    docList.add(precision);
  }

  public void addTripleAP(double precision) {
    docList.add(precision);
  }

  private double computeMAP(List<Double> list) {
    if (list.size() == 0)
      return 0;
    double total = 0;
    for (Double d : list)
      total += d;

    return total / list.size();

  }

  public void setDocMap() {
    docMAP = computeMAP(docList);
  }

  public void setConceptMap() {
    conceptMAP = computeMAP(conceptList);
  }

  public void setTripleMap() {
    tripleMAP = computeMAP(tripleList);
  }
  
  public double getDocMAP(){
    return docMAP;
  }
  
  public double getConceptMAP(){
    return conceptMAP;
  }
  
  public double getTripleMAP(){
    return tripleMAP;
  }
  
  public double getAnswerMAP(){
    return answerMAP;
  }
  
  public double getAnswerMAR(){
    return answerMAR;
  }
  
  


}

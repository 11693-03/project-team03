package edu.cmu.lti.oaqa.pipeline;

import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.resource.ResourceProcessException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.oaqa.type.input.Question;

public class Consumer extends CasConsumer_ImplBase {
  // File golden_file;//refer to the file that has golden standard answer
  String goldPath, outputPath;

  List<Question> questions;

  // five kinds of evaluations
  List<Double[]> precisions;

  List<Double[]> recalls;

  List<Double[]> fmeasures;

  List<Double[]> maps;

  List<Double[]> gmaps;

  File goldenFile;
  @Override
  public void initialize() throws ResourceInitializationException {
    precisions = new ArrayList<Double[]>();
    recalls = new ArrayList<Double[]>();
    fmeasures = new ArrayList<Double[]>();
    maps = new ArrayList<Double[]>();
    gmaps = new ArrayList<Double[]>();

    goldPath = "/BioASQ-SampleData1B.json";
    goldenFile = new File((String) getConfigParameterValue("input"));

  }

  @Override
  public void processCas(CAS aCAS) throws ResourceProcessException {

    // TODO Auto-generated method stub
    outputPath = "/MyOutput.json";// the path of output file
    questions = new ArrayList<Question>();

    JCas jcas = null;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}

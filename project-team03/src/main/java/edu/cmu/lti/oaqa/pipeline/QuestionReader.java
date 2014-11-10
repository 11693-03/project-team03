package edu.cmu.lti.oaqa.pipeline;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.List;

import json.JsonCollectionReaderHelper;
import json.gson.Question;
import json.gson.TestSet;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;



public class QuestionReader extends CollectionReader_ImplBase {
  private static final String PARAM_INPUT = "InputFile";

  private List<Question> inputs;

  private int index, size;

  @Override
  public void initialize() throws ResourceInitializationException {
    String filePath = ((String) getConfigParameterValue(PARAM_INPUT)).trim();
    //System.out.println(filePath);
    inputs = TestSet.load(getClass().getResourceAsStream(filePath)).stream().collect(toList());
    
    size = inputs.size();
    // trim question texts
    inputs.stream().filter(input -> input.getBody() != null)
            .forEach(input -> input.setBody(input.getBody().trim().replaceAll("\\s+", " ")));
  }

  @Override
  public void getNext(CAS aCAS) throws IOException, CollectionException {
    JCas jCas = null;
    try {
      jCas = aCAS.getJCas();
    } catch (CASException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Question question = inputs.get(index++);
    JsonCollectionReaderHelper.addQuestionToIndex(question, "", jCas);

  }

  @Override
  public boolean hasNext() throws IOException, CollectionException {
    // TODO Auto-generated method stub
    return index < size;
  }

  @Override
  public Progress[] getProgress() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void close() throws IOException {
    // TODO Auto-generated method stub

  }

}

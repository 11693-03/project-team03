package json.gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import json.gson.TestSet.QuestionTypeSelector;

import com.github.julman99.gsonfire.GsonFireBuilder;
import com.google.gson.Gson;

public class ListAnswerSet {

  private List<TestListQuestion> answers;
  public ListAnswerSet(List<TestListQuestion> answers){
    this.answers = answers;
  }
  
  private static Gson gson = new GsonFireBuilder().createGson();


  public String dump() {
    ListAnswerSet output = new ListAnswerSet(answers);
    return gson.toJson(output);
  }

}

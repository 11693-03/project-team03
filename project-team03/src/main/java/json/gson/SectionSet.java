package json.gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;

/**
 * 
 * @author handixu
 *
 */
public class SectionSet {
  // private String id;
  private String pmid;

  private String title;

  private List<String> sections;

  public SectionSet(String id, String text, List<String> sections) {
    super();
    this.pmid = id;
    this.title = text;
    this.sections = sections;
  }
  public String getTitle(){
    return title;
  }
  public List<String> getSections(){
    return sections;
  }
  public static SectionSet load(String json){
    return new Gson().fromJson(json, SectionSet.class);
  }
  @Override
  public String toString(){
    String temp =  "pmid: "+this.pmid+"\ntitle: "+this.title;   
    for(String sec :sections)
      temp += "\n"+ sec;
    return temp;
  }
  public static void main(String... args) throws Exception {
    String value = "/Users/handixu/git/project-team03/project-team03/src/main/resources/test.json";
    String result = "";
    String temp = "";
    BufferedReader br = new BufferedReader(new FileReader(value));
    while ((temp = br.readLine()) != null)
      result += temp;
    // Now do the magic.
    SectionSet data = new Gson().fromJson(result, SectionSet.class);

    // Show it.
    for (int i = 0; i < data.sections.size(); i++)
      System.out.println(data.sections.get(i));

  }

}

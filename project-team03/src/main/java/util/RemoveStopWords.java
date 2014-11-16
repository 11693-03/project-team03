package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class RemoveStopWords {
  HashSet<String> stopWords;
  File storeFile;
  private static RemoveStopWords instance = null;
  private RemoveStopWords(){
    stopWords = new HashSet<String>();
    storeFile = new File("src/main/resources/stopwords.txt");
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(storeFile));
      String line = null;
      while((line=reader.readLine())!=null){
        if(line.charAt(0)=='#')
          continue;
        stopWords.add(line.trim());
      }
      reader.close();
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public static RemoveStopWords getInstance(){
    if(instance == null)
      instance = new RemoveStopWords();
    return instance;
  }
  public boolean checkExistance(String token){
    return stopWords.contains(token);
  }
  public String processStr(String str){
    String[] tokens = str.split("\\s+");
    StringBuffer res = new StringBuffer();
    for(String t : tokens){
      if(!checkExistance(t)){
        res.append(t+" ");
      }
    }
    return res.toString().trim();
  }
}

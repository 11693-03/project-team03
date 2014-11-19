package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.LowerCaseTokenizerFactory;
import com.aliasi.tokenizer.StopTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;

public class TokenizerLingpipe {
  private static TokenizerLingpipe instance = null;
  public static TokenizerLingpipe getInstance(){
    if(instance == null)
      instance = new TokenizerLingpipe();
    return instance;
  }
  private TokenizerFactory mTokenizerFactory;
  HashSet<String>stopWords;
  private TokenizerLingpipe(){
    stopWords = new HashSet<String>();
    File storeFile = new File("src/main/resources/stopwords.txt");
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
    mTokenizerFactory = IndoEuropeanTokenizerFactory.INSTANCE;
    mTokenizerFactory = new LowerCaseTokenizerFactory(mTokenizerFactory);
    mTokenizerFactory = new StopTokenizerFactory(mTokenizerFactory, stopWords);
  }
  public String tokenize(String query){
    Tokenizer tokenizer = mTokenizerFactory.tokenizer(query.toCharArray(), 0,
            query.length());

    List<String> tokens = new ArrayList<String>();
    List<String> others = new ArrayList<>();
    tokenizer.tokenize(tokens, others);
    String res = new String();
    for (String token : tokens) {
      res += token+" ";
    }
    return res;
  }
}

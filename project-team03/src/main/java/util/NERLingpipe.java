package util;
import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunker;
import com.aliasi.chunk.Chunking;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.util.AbstractExternalizable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *  Named Entity Recognizer from Lingpipe, utilizing a trained data set
 *  @author Michael Zhuang
 */
public class NERLingpipe {
  /**
   *  The path of the trained data set we are using
   */
  String ChunkerFile = "src/main/resources/ne-en-bio-genetag.HmmChunker";
  File modelFile;
  private static NERLingpipe instance;
  private Map<Integer, Integer> space;
  Chunker chunker;
  /**
   *  Constructor of NERLingpipe 
   */
  private NERLingpipe(){
    try {
      modelFile = new File(ChunkerFile);
      chunker = (Chunker) AbstractExternalizable.readObject(modelFile);
      space = new HashMap<Integer, Integer>();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  /**
   *  Singleton pattern to ensure only one instance of NERLingpipe is created. 
   */
  public static NERLingpipe getInstance(){
    if(instance == null)
      instance = new NERLingpipe();
    return instance;
  }
   /**
   *  Get the number of spaces before the position in the sentence
   *  specified by the parameter 
   *  @param begin
   */
  public int getSpaceNum(int begin){
    return space.get(begin);
  }
   /**
   *  Extract gene names from the input string
   *  @param text
   *      The input string to be analyzed
   *  @return Map
   *      Records the start and end position of each gene names in the sentence
   * 
   */
  private Map<Integer, Integer> getGeneSpans(String text) throws IOException, ClassNotFoundException{
    Map<Integer, Integer> begin2end = new TreeMap<Integer, Integer>();
    Chunking chunking = chunker.chunk(text);
    Iterator<Chunk>iter = chunking.chunkSet().iterator();
    while(iter.hasNext()){
      int numOfSpace = 0;
      Chunk c = (Chunk)iter.next();
      begin2end.put(c.start(), c.end());
      
      for(int i = 0; i < c.start(); i++){
        if(text.charAt(i)==' ')
          numOfSpace++;
      }
      space.put(c.start(), numOfSpace);
    }
    return begin2end;
  }
  
  public String extractKeywords(String text) throws ClassNotFoundException, IOException{
    //System.out.println(text);
    Map<Integer, Integer> begin2end = getGeneSpans(text);
    Iterator<Entry<Integer, Integer>>  iter = begin2end.entrySet().iterator();
    StringBuffer strBuf = new StringBuffer();
    while(iter.hasNext()){
      Entry<Integer, Integer> entry = (Entry<Integer, Integer>)(iter.next());
      strBuf.append(text.substring(entry.getKey(), entry.getValue())+" ");
    }
    return strBuf.toString().trim();
  }
  /**
   *  The main method is used to test Lingpipe NER
   *  @param args 
   * 
   */
  public static void main(String[] args) throws Exception {
    NERLingpipe test = new NERLingpipe();
    String text = "p53 regulates human insulin-like growth factor II gene expression through active P4 promoter in rhabdomyosarcoma cells.";
    System.out.println(test.extractKeywords(text));
  }
}
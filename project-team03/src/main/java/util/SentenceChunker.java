package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import json.gson.SectionSet;

import com.aliasi.sentences.MedlineSentenceModel;
import com.aliasi.sentences.SentenceModel;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;

public class SentenceChunker {
  static final TokenizerFactory TOKENIZER_FACTORY = IndoEuropeanTokenizerFactory.INSTANCE;
  static final SentenceModel SENTENCE_MODEL = new MedlineSentenceModel();
  static final SentenceChunker SENTENCE_CHUNKER = new SentenceChunker();
  private SentenceChunker() {
  }
  private static SentenceChunker instance = null;
  public static SentenceChunker getInstance(){
    if(instance == null)
      instance = new SentenceChunker();
    return instance;
  }
  HashMap<Integer, Integer> begin2end;
  public HashMap<Integer, Integer> chunk(String text){
    begin2end = new HashMap<Integer, Integer>();
    List<String> tokenList = new ArrayList<String>();
    List<String> whiteList = new ArrayList<String>();
    Tokenizer tokenizer
        = TOKENIZER_FACTORY.tokenizer(text.toCharArray(),
                                      0,text.length());
    tokenizer.tokenize(tokenList,whiteList);
    String[] tokens = new String[tokenList.size()];
    String[] whites = new String[whiteList.size()];
    tokenList.toArray(tokens);
    whiteList.toArray(whites);
    int[] sentenceBoundaries
        = SENTENCE_MODEL.boundaryIndices(tokens,whites);
    int sentStartTok = 0;
    int sentEndTok = 0;
    int sentencePosSt = 0;
    int sentencePosEnd = 0;
    List<String> sentences = new LinkedList<String>();
    for (int i = 0; i < sentenceBoundaries.length; ++i) {
        sentEndTok = sentenceBoundaries[i];
        //System.out.println("SENTENCE "+(i+1)+": ");
        StringBuffer sentence = new StringBuffer();
        for (int j=sentStartTok; j <= sentEndTok; j++) {
            //System.out.print(tokens[j]+whites[j+1]);
            //System.out.println(whites[j+1].length());
            sentence.append(tokens[j]+whites[j+1]);
        }
        sentences.add(sentence.toString());
        //System.out.println();
        sentencePosEnd = sentencePosSt+sentence.length()-1;

        begin2end.put(sentencePosSt,sentencePosEnd);
        sentencePosSt = sentencePosEnd+1;
        sentStartTok = sentEndTok+1;
    }
    return begin2end;
  }
  public static void main(String[] args){
    SentenceChunker test = new SentenceChunker();
    String text = "The induction of immediate-early (IE) response genes, such as egr-1,"
            + "c-fos, and c-jun, occurs rapidly after the activation of T"
            + " lymphocytes. The process of activation involves calcium mobilization,"
            + " activation of protein kinase C (PKC), and phosphorylation of tyrosine"
            + " kinases. p21(ras), a guanine nucleotide binding factor, mediates"
            + " T-cell signal transduction through PKC-dependent and PKC-independent"
            + " pathways. The involvement of p21(ras) in the regulation of"
            + " calcium-dependent signals has been suggested through analysis of its"
            + " role in the activation of NF-AT. We have investigated the inductions"
            + " of the IE genes in response to calcium signals in Jurkat cells (in"
            + " the presence of activated p21(ras)) and their correlated consequences.";
    HashMap<Integer, Integer> res = test.chunk(text);
    Iterator<Integer> iter = res.keySet().iterator();
    while(iter.hasNext()){
      int begin = iter.next();
      int end = res.get(begin);
      System.out.println(text.substring(begin, end));
    }
  }
}

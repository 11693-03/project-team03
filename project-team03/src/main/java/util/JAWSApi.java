package util;

import java.util.LinkedList;
import java.util.List;

import edu.smu.tspell.wordnet.*;

/**
 * Displays word forms and definitions for synsets containing the word form specified on the command
 * line. To use this application, specify the word form that you wish to view synsets for, as in the
 * following example which displays all synsets containing the word form "airplane": <br>
 * java TestJAWS airplane
 */
public class JAWSApi {
  private JAWSApi(){
    System.setProperty("wordnet.database.dir", "src/main/resources/dict");    
  }
  private static JAWSApi instance = null;
  public static JAWSApi getInstance(){
    if(instance==null)
      instance = new JAWSApi();
    return instance;
  }
  public List<String> getSynonyms(String text, int num) {
    WordNetDatabase database = WordNetDatabase.getFileInstance();
    Synset[] synsets = database.getSynsets(text);
    List<String> syn = new LinkedList<String>();
    if (synsets.length > 0) {
      for (int i = 0; i < synsets.length; i++) {
        System.out.println("");
        String[] wordForms = synsets[i].getWordForms();
        for (int j = 1; j < Math.min(wordForms.length, num + 1); j++) {
          syn.add(wordForms[j]);
        }
      }
      return syn;
    } else {
      return null;
    }
  }

  /**
   * Main entry point. The command-line arguments are concatenated together (separated by spaces)
   * and used as the word form to look up.
   */
  public static void main(String[] args) {
    System.setProperty("wordnet.database.dir", "src/main/resources/dict");
    args = new String[1];
    args[0] = "shit";
    if (args.length > 0) {
      // Concatenate the command-line arguments
      StringBuffer buffer = new StringBuffer();
      for (int i = 0; i < args.length; i++) {
        buffer.append((i > 0 ? " " : "") + args[i]);
      }
      String wordForm = buffer.toString();
      // Get the synsets containing the wrod form
      WordNetDatabase database = WordNetDatabase.getFileInstance();
      Synset[] synsets = database.getSynsets(wordForm);
      // Display the word forms and definitions for synsets retrieved
      if (synsets.length > 0) {
        System.out.println("The following synsets contain '" + wordForm
                + "' or a possible base form " + "of that text:");
        for (int i = 0; i < synsets.length; i++) {
          System.out.println("");
          String[] wordForms = synsets[i].getWordForms();
          for (int j = 0; j < wordForms.length; j++) {
            System.out.print((j > 0 ? ", " : "") + wordForms[j]);
          }
          System.out.println(": " + synsets[i].getDefinition());
        }
      } else {
        System.err.println("No synsets exist that contain " + "the word form '" + wordForm + "'");
      }
    } else {
      System.err.println("You must specify " + "a word form for which to retrieve synsets.");
    }
  }
}
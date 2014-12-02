

/* First created by JCasGen Sat Oct 18 19:40:19 EDT 2014 */
package edu.cmu.lti.oaqa.type.retrieval;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** A document search result.

 *  */
public class Document extends SearchResult {
  /** 
   *  
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Document.class);
  /** 
   *  
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** 
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   *  */
  protected Document() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * 
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Document(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** 
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Document(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   *  modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: title

  /** getter for title - gets The title of the document.
   * 
   * @return value of the feature 
   */
  public String getTitle() {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_title == null)
      jcasType.jcas.throwFeatMissing("title", "edu.cmu.lti.oaqa.type.retrieval.Document");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Document_Type)jcasType).casFeatCode_title);}
    
  /** setter for title - sets The title of the document. 
   * 
   * @param v value to set into the feature 
   */
  public void setTitle(String v) {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_title == null)
      jcasType.jcas.throwFeatMissing("title", "edu.cmu.lti.oaqa.type.retrieval.Document");
    jcasType.ll_cas.ll_setStringValue(addr, ((Document_Type)jcasType).casFeatCode_title, v);}    
   
    
  //*--------------*
  //* Feature: docId

  /** getter for docId - gets A unique identifier for this document.
   * 
   * @return value of the feature 
   */
  public String getDocId() {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_docId == null)
      jcasType.jcas.throwFeatMissing("docId", "edu.cmu.lti.oaqa.type.retrieval.Document");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Document_Type)jcasType).casFeatCode_docId);}
    
  /** setter for docId - sets A unique identifier for this document. 
   * 
   * @param v value to set into the feature 
   */
  public void setDocId(String v) {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_docId == null)
      jcasType.jcas.throwFeatMissing("docId", "edu.cmu.lti.oaqa.type.retrieval.Document");
    jcasType.ll_cas.ll_setStringValue(addr, ((Document_Type)jcasType).casFeatCode_docId, v);}    
  }

    
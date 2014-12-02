

/* First created by JCasGen Fri Nov 28 15:46:42 EST 2014 */
package edu.cmu.lti.oaqa.type.retrieval;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.TOP;


/** 
 *  */
public class FinalQuery extends TOP {
  /** 
   *  
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(FinalQuery.class);
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
  protected FinalQuery() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * 
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public FinalQuery(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** 
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public FinalQuery(JCas jcas) {
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
  //* Feature: queryWithOp

  /** getter for queryWithOp - gets 
   * 
   * @return value of the feature 
   */
  public String getQueryWithOp() {
    if (FinalQuery_Type.featOkTst && ((FinalQuery_Type)jcasType).casFeat_queryWithOp == null)
      jcasType.jcas.throwFeatMissing("queryWithOp", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    return jcasType.ll_cas.ll_getStringValue(addr, ((FinalQuery_Type)jcasType).casFeatCode_queryWithOp);}
    
  /** setter for queryWithOp - sets  
   * 
   * @param v value to set into the feature 
   */
  public void setQueryWithOp(String v) {
    if (FinalQuery_Type.featOkTst && ((FinalQuery_Type)jcasType).casFeat_queryWithOp == null)
      jcasType.jcas.throwFeatMissing("queryWithOp", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    jcasType.ll_cas.ll_setStringValue(addr, ((FinalQuery_Type)jcasType).casFeatCode_queryWithOp, v);}    
   
    
  //*--------------*
  //* Feature: queryWithoutOp

  /** getter for queryWithoutOp - gets 
   * 
   * @return value of the feature 
   */
  public String getQueryWithoutOp() {
    if (FinalQuery_Type.featOkTst && ((FinalQuery_Type)jcasType).casFeat_queryWithoutOp == null)
      jcasType.jcas.throwFeatMissing("queryWithoutOp", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    return jcasType.ll_cas.ll_getStringValue(addr, ((FinalQuery_Type)jcasType).casFeatCode_queryWithoutOp);}
    
  /** setter for queryWithoutOp - sets  
   * 
   * @param v value to set into the feature 
   */
  public void setQueryWithoutOp(String v) {
    if (FinalQuery_Type.featOkTst && ((FinalQuery_Type)jcasType).casFeat_queryWithoutOp == null)
      jcasType.jcas.throwFeatMissing("queryWithoutOp", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    jcasType.ll_cas.ll_setStringValue(addr, ((FinalQuery_Type)jcasType).casFeatCode_queryWithoutOp, v);}    
   
    
  //*--------------*
  //* Feature: originalQuery

  /** getter for originalQuery - gets 
   * 
   * @return value of the feature 
   */
  public String getOriginalQuery() {
    if (FinalQuery_Type.featOkTst && ((FinalQuery_Type)jcasType).casFeat_originalQuery == null)
      jcasType.jcas.throwFeatMissing("originalQuery", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    return jcasType.ll_cas.ll_getStringValue(addr, ((FinalQuery_Type)jcasType).casFeatCode_originalQuery);}
    
  /** setter for originalQuery - sets  
   * 
   * @param v value to set into the feature 
   */
  public void setOriginalQuery(String v) {
    if (FinalQuery_Type.featOkTst && ((FinalQuery_Type)jcasType).casFeat_originalQuery == null)
      jcasType.jcas.throwFeatMissing("originalQuery", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    jcasType.ll_cas.ll_setStringValue(addr, ((FinalQuery_Type)jcasType).casFeatCode_originalQuery, v);}    
   
    
  //*--------------*
  //* Feature: queryID

  /** getter for queryID - gets 
   * 
   * @return value of the feature 
   */
  public String getQueryID() {
    if (FinalQuery_Type.featOkTst && ((FinalQuery_Type)jcasType).casFeat_queryID == null)
      jcasType.jcas.throwFeatMissing("queryID", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    return jcasType.ll_cas.ll_getStringValue(addr, ((FinalQuery_Type)jcasType).casFeatCode_queryID);}
    
  /** setter for queryID - sets  
   * 
   * @param v value to set into the feature 
   */
  public void setQueryID(String v) {
    if (FinalQuery_Type.featOkTst && ((FinalQuery_Type)jcasType).casFeat_queryID == null)
      jcasType.jcas.throwFeatMissing("queryID", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    jcasType.ll_cas.ll_setStringValue(addr, ((FinalQuery_Type)jcasType).casFeatCode_queryID, v);}    
   
    
  //*--------------*
  //* Feature: keyword

  /** getter for keyword - gets 
   * 
   * @return value of the feature 
   */
  public String getKeyword() {
    if (FinalQuery_Type.featOkTst && ((FinalQuery_Type)jcasType).casFeat_keyword == null)
      jcasType.jcas.throwFeatMissing("keyword", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    return jcasType.ll_cas.ll_getStringValue(addr, ((FinalQuery_Type)jcasType).casFeatCode_keyword);}
    
  /** setter for keyword - sets  
   * 
   * @param v value to set into the feature 
   */
  public void setKeyword(String v) {
    if (FinalQuery_Type.featOkTst && ((FinalQuery_Type)jcasType).casFeat_keyword == null)
      jcasType.jcas.throwFeatMissing("keyword", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    jcasType.ll_cas.ll_setStringValue(addr, ((FinalQuery_Type)jcasType).casFeatCode_keyword, v);}    
  }

    


/* First created by JCasGen Fri Nov 28 15:46:42 EST 2014 */
package edu.cmu.lti.oaqa.type.retrieval;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.TOP;


/** 
 * Updated by JCasGen Fri Nov 28 15:46:42 EST 2014
 * XML source: /home/micz/project-team03/project-team03/src/main/resources/type/OAQATypes.xml
 * @generated */
public class FinalQuery extends TOP {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(FinalQuery.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected FinalQuery() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public FinalQuery(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
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
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: queryWIthOp

  /** getter for queryWIthOp - gets 
   * @generated
   * @return value of the feature 
   */
  public String getQueryWIthOp() {
    if (FinalQuery_Type.featOkTst && ((FinalQuery_Type)jcasType).casFeat_queryWIthOp == null)
      jcasType.jcas.throwFeatMissing("queryWIthOp", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    return jcasType.ll_cas.ll_getStringValue(addr, ((FinalQuery_Type)jcasType).casFeatCode_queryWIthOp);}
    
  /** setter for queryWIthOp - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setQueryWIthOp(String v) {
    if (FinalQuery_Type.featOkTst && ((FinalQuery_Type)jcasType).casFeat_queryWIthOp == null)
      jcasType.jcas.throwFeatMissing("queryWIthOp", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    jcasType.ll_cas.ll_setStringValue(addr, ((FinalQuery_Type)jcasType).casFeatCode_queryWIthOp, v);}    
   
    
  //*--------------*
  //* Feature: queryWithoutOp

  /** getter for queryWithoutOp - gets 
   * @generated
   * @return value of the feature 
   */
  public String getQueryWithoutOp() {
    if (FinalQuery_Type.featOkTst && ((FinalQuery_Type)jcasType).casFeat_queryWithoutOp == null)
      jcasType.jcas.throwFeatMissing("queryWithoutOp", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    return jcasType.ll_cas.ll_getStringValue(addr, ((FinalQuery_Type)jcasType).casFeatCode_queryWithoutOp);}
    
  /** setter for queryWithoutOp - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setQueryWithoutOp(String v) {
    if (FinalQuery_Type.featOkTst && ((FinalQuery_Type)jcasType).casFeat_queryWithoutOp == null)
      jcasType.jcas.throwFeatMissing("queryWithoutOp", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    jcasType.ll_cas.ll_setStringValue(addr, ((FinalQuery_Type)jcasType).casFeatCode_queryWithoutOp, v);}    
  }

    
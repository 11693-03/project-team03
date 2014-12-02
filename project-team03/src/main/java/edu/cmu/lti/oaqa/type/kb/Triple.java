

/* First created by JCasGen Sat Oct 18 19:40:19 EDT 2014 */
package edu.cmu.lti.oaqa.type.kb;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.TOP;


/** A triple, e.g., and RDF triple.
 *  */
public class Triple extends TOP {
  /** 
   *  
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Triple.class);
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
  protected Triple() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * 
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Triple(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** 
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Triple(JCas jcas) {
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
  //* Feature: subject

  /** getter for subject - gets The subject of the triple - always a URI.
   * 
   * @return value of the feature 
   */
  public String getSubject() {
    if (Triple_Type.featOkTst && ((Triple_Type)jcasType).casFeat_subject == null)
      jcasType.jcas.throwFeatMissing("subject", "edu.cmu.lti.oaqa.type.kb.Triple");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Triple_Type)jcasType).casFeatCode_subject);}
    
  /** setter for subject - sets The subject of the triple - always a URI. 
   * 
   * @param v value to set into the feature 
   */
  public void setSubject(String v) {
    if (Triple_Type.featOkTst && ((Triple_Type)jcasType).casFeat_subject == null)
      jcasType.jcas.throwFeatMissing("subject", "edu.cmu.lti.oaqa.type.kb.Triple");
    jcasType.ll_cas.ll_setStringValue(addr, ((Triple_Type)jcasType).casFeatCode_subject, v);}    
   
    
  //*--------------*
  //* Feature: predicate

  /** getter for predicate - gets The predicate of the triple - always a URI.
   * 
   * @return value of the feature 
   */
  public String getPredicate() {
    if (Triple_Type.featOkTst && ((Triple_Type)jcasType).casFeat_predicate == null)
      jcasType.jcas.throwFeatMissing("predicate", "edu.cmu.lti.oaqa.type.kb.Triple");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Triple_Type)jcasType).casFeatCode_predicate);}
    
  /** setter for predicate - sets The predicate of the triple - always a URI. 
   * 
   * @param v value to set into the feature 
   */
  public void setPredicate(String v) {
    if (Triple_Type.featOkTst && ((Triple_Type)jcasType).casFeat_predicate == null)
      jcasType.jcas.throwFeatMissing("predicate", "edu.cmu.lti.oaqa.type.kb.Triple");
    jcasType.ll_cas.ll_setStringValue(addr, ((Triple_Type)jcasType).casFeatCode_predicate, v);}    
   
    
  //*--------------*
  //* Feature: object

  /** getter for object - gets The object of the triple - may be a URI or an xml datatype (string, int, etc.).  See isObjeUri to determine if object is a URI.
   * 
   * @return value of the feature 
   */
  public String getObject() {
    if (Triple_Type.featOkTst && ((Triple_Type)jcasType).casFeat_object == null)
      jcasType.jcas.throwFeatMissing("object", "edu.cmu.lti.oaqa.type.kb.Triple");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Triple_Type)jcasType).casFeatCode_object);}
    
  /** setter for object - sets The object of the triple - may be a URI or an xml datatype (string, int, etc.).  See isObjeUri to determine if object is a URI. 
   * 
   * @param v value to set into the feature 
   */
  public void setObject(String v) {
    if (Triple_Type.featOkTst && ((Triple_Type)jcasType).casFeat_object == null)
      jcasType.jcas.throwFeatMissing("object", "edu.cmu.lti.oaqa.type.kb.Triple");
    jcasType.ll_cas.ll_setStringValue(addr, ((Triple_Type)jcasType).casFeatCode_object, v);}    
   
    
  //*--------------*
  //* Feature: isObjUri

  /** getter for isObjUri - gets Boolean flag - true of object field is a URI, false otherwise.
   * 
   * @return value of the feature 
   */
  public boolean getIsObjUri() {
    if (Triple_Type.featOkTst && ((Triple_Type)jcasType).casFeat_isObjUri == null)
      jcasType.jcas.throwFeatMissing("isObjUri", "edu.cmu.lti.oaqa.type.kb.Triple");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Triple_Type)jcasType).casFeatCode_isObjUri);}
    
  /** setter for isObjUri - sets Boolean flag - true of object field is a URI, false otherwise. 
   * 
   * @param v value to set into the feature 
   */
  public void setIsObjUri(boolean v) {
    if (Triple_Type.featOkTst && ((Triple_Type)jcasType).casFeat_isObjUri == null)
      jcasType.jcas.throwFeatMissing("isObjUri", "edu.cmu.lti.oaqa.type.kb.Triple");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Triple_Type)jcasType).casFeatCode_isObjUri, v);}    
  }

    
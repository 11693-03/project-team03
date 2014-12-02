

/* First created by JCasGen Sat Oct 18 19:40:19 EDT 2014 */
package edu.cmu.lti.oaqa.type.retrieval;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import edu.cmu.lti.oaqa.type.kb.Concept;


/** A search result from an ontology.

 *  */
public class ConceptSearchResult extends AnswerSearchResult {
  /** 
   *  
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(ConceptSearchResult.class);
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
  protected ConceptSearchResult() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * 
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public ConceptSearchResult(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** 
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public ConceptSearchResult(JCas jcas) {
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
  //* Feature: concept

  /** getter for concept - gets The relevant concept searched in the ontology.
   * 
   * @return value of the feature 
   */
  public Concept getConcept() {
    if (ConceptSearchResult_Type.featOkTst && ((ConceptSearchResult_Type)jcasType).casFeat_concept == null)
      jcasType.jcas.throwFeatMissing("concept", "edu.cmu.lti.oaqa.type.retrieval.ConceptSearchResult");
    return (Concept)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((ConceptSearchResult_Type)jcasType).casFeatCode_concept)));}
    
  /** setter for concept - sets The relevant concept searched in the ontology. 
   * 
   * @param v value to set into the feature 
   */
  public void setConcept(Concept v) {
    if (ConceptSearchResult_Type.featOkTst && ((ConceptSearchResult_Type)jcasType).casFeat_concept == null)
      jcasType.jcas.throwFeatMissing("concept", "edu.cmu.lti.oaqa.type.retrieval.ConceptSearchResult");
    jcasType.ll_cas.ll_setRefValue(addr, ((ConceptSearchResult_Type)jcasType).casFeatCode_concept, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    

/* First created by JCasGen Sat Oct 18 19:40:19 EDT 2014 */
package edu.cmu.lti.oaqa.type.retrieval;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** A primitive query concept represented by a single text string

 *  */
public class AtomicQueryConcept_Type extends QueryConcept_Type {
  /**  
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /**  */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (AtomicQueryConcept_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = AtomicQueryConcept_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new AtomicQueryConcept(addr, AtomicQueryConcept_Type.this);
  			   AtomicQueryConcept_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new AtomicQueryConcept(addr, AtomicQueryConcept_Type.this);
  	  }
    };
  /**  */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = AtomicQueryConcept.typeIndexID;
  /**  
      */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.lti.oaqa.type.retrieval.AtomicQueryConcept");
 
  /**  */
  final Feature casFeat_text;
  /**  */
  final int     casFeatCode_text;
  /** 
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getText(int addr) {
        if (featOkTst && casFeat_text == null)
      jcas.throwFeatMissing("text", "edu.cmu.lti.oaqa.type.retrieval.AtomicQueryConcept");
    return ll_cas.ll_getStringValue(addr, casFeatCode_text);
  }
  /** 
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setText(int addr, String v) {
        if (featOkTst && casFeat_text == null)
      jcas.throwFeatMissing("text", "edu.cmu.lti.oaqa.type.retrieval.AtomicQueryConcept");
    ll_cas.ll_setStringValue(addr, casFeatCode_text, v);}
    
  
 
  /**  */
  final Feature casFeat_originalText;
  /**  */
  final int     casFeatCode_originalText;
  /** 
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getOriginalText(int addr) {
        if (featOkTst && casFeat_originalText == null)
      jcas.throwFeatMissing("originalText", "edu.cmu.lti.oaqa.type.retrieval.AtomicQueryConcept");
    return ll_cas.ll_getStringValue(addr, casFeatCode_originalText);
  }
  /** 
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setOriginalText(int addr, String v) {
        if (featOkTst && casFeat_originalText == null)
      jcas.throwFeatMissing("originalText", "edu.cmu.lti.oaqa.type.retrieval.AtomicQueryConcept");
    ll_cas.ll_setStringValue(addr, casFeatCode_originalText, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * 
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public AtomicQueryConcept_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_text = jcas.getRequiredFeatureDE(casType, "text", "uima.cas.String", featOkTst);
    casFeatCode_text  = (null == casFeat_text) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_text).getCode();

 
    casFeat_originalText = jcas.getRequiredFeatureDE(casType, "originalText", "uima.cas.String", featOkTst);
    casFeatCode_originalText  = (null == casFeat_originalText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_originalText).getCode();

  }
}



    
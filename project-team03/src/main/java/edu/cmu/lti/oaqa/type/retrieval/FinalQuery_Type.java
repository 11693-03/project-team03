
/* First created by JCasGen Fri Nov 28 15:46:42 EST 2014 */
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
import org.apache.uima.jcas.cas.TOP_Type;

/** 
 * Updated by JCasGen Fri Nov 28 15:46:42 EST 2014
 * @generated */
public class FinalQuery_Type extends TOP_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (FinalQuery_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = FinalQuery_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new FinalQuery(addr, FinalQuery_Type.this);
  			   FinalQuery_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new FinalQuery(addr, FinalQuery_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = FinalQuery.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
 
  /** @generated */
  final Feature casFeat_queryWIthOp;
  /** @generated */
  final int     casFeatCode_queryWIthOp;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getQueryWIthOp(int addr) {
        if (featOkTst && casFeat_queryWIthOp == null)
      jcas.throwFeatMissing("queryWIthOp", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    return ll_cas.ll_getStringValue(addr, casFeatCode_queryWIthOp);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setQueryWIthOp(int addr, String v) {
        if (featOkTst && casFeat_queryWIthOp == null)
      jcas.throwFeatMissing("queryWIthOp", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    ll_cas.ll_setStringValue(addr, casFeatCode_queryWIthOp, v);}
    
  
 
  /** @generated */
  final Feature casFeat_queryWithoutOp;
  /** @generated */
  final int     casFeatCode_queryWithoutOp;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getQueryWithoutOp(int addr) {
        if (featOkTst && casFeat_queryWithoutOp == null)
      jcas.throwFeatMissing("queryWithoutOp", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    return ll_cas.ll_getStringValue(addr, casFeatCode_queryWithoutOp);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setQueryWithoutOp(int addr, String v) {
        if (featOkTst && casFeat_queryWithoutOp == null)
      jcas.throwFeatMissing("queryWithoutOp", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    ll_cas.ll_setStringValue(addr, casFeatCode_queryWithoutOp, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public FinalQuery_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_queryWIthOp = jcas.getRequiredFeatureDE(casType, "queryWIthOp", "uima.cas.String", featOkTst);
    casFeatCode_queryWIthOp  = (null == casFeat_queryWIthOp) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_queryWIthOp).getCode();

 
    casFeat_queryWithoutOp = jcas.getRequiredFeatureDE(casType, "queryWithoutOp", "uima.cas.String", featOkTst);
    casFeatCode_queryWithoutOp  = (null == casFeat_queryWithoutOp) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_queryWithoutOp).getCode();

  }
}



    

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
 *  */
public class FinalQuery_Type extends TOP_Type {
  /**  
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /**  */
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
  /**  */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = FinalQuery.typeIndexID;
  /**  
      */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
 
  /**  */
  final Feature casFeat_queryWithOp;
  /**  */
  final int     casFeatCode_queryWithOp;
  /** 
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getQueryWithOp(int addr) {
        if (featOkTst && casFeat_queryWithOp == null)
      jcas.throwFeatMissing("queryWithOp", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    return ll_cas.ll_getStringValue(addr, casFeatCode_queryWithOp);
  }
  /** 
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setQueryWithOp(int addr, String v) {
        if (featOkTst && casFeat_queryWithOp == null)
      jcas.throwFeatMissing("queryWithOp", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    ll_cas.ll_setStringValue(addr, casFeatCode_queryWithOp, v);}
    
  
 
  /**  */
  final Feature casFeat_queryWithoutOp;
  /**  */
  final int     casFeatCode_queryWithoutOp;
  /** 
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getQueryWithoutOp(int addr) {
        if (featOkTst && casFeat_queryWithoutOp == null)
      jcas.throwFeatMissing("queryWithoutOp", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    return ll_cas.ll_getStringValue(addr, casFeatCode_queryWithoutOp);
  }
  /** 
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setQueryWithoutOp(int addr, String v) {
        if (featOkTst && casFeat_queryWithoutOp == null)
      jcas.throwFeatMissing("queryWithoutOp", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    ll_cas.ll_setStringValue(addr, casFeatCode_queryWithoutOp, v);}
    
  
 
  /**  */
  final Feature casFeat_originalQuery;
  /**  */
  final int     casFeatCode_originalQuery;
  /** 
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getOriginalQuery(int addr) {
        if (featOkTst && casFeat_originalQuery == null)
      jcas.throwFeatMissing("originalQuery", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    return ll_cas.ll_getStringValue(addr, casFeatCode_originalQuery);
  }
  /** 
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setOriginalQuery(int addr, String v) {
        if (featOkTst && casFeat_originalQuery == null)
      jcas.throwFeatMissing("originalQuery", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    ll_cas.ll_setStringValue(addr, casFeatCode_originalQuery, v);}
    
  
 
  /**  */
  final Feature casFeat_queryID;
  /**  */
  final int     casFeatCode_queryID;
  /** 
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getQueryID(int addr) {
        if (featOkTst && casFeat_queryID == null)
      jcas.throwFeatMissing("queryID", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    return ll_cas.ll_getStringValue(addr, casFeatCode_queryID);
  }
  /** 
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setQueryID(int addr, String v) {
        if (featOkTst && casFeat_queryID == null)
      jcas.throwFeatMissing("queryID", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    ll_cas.ll_setStringValue(addr, casFeatCode_queryID, v);}
    
  
 
  /**  */
  final Feature casFeat_keyword;
  /**  */
  final int     casFeatCode_keyword;
  /** 
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getKeyword(int addr) {
        if (featOkTst && casFeat_keyword == null)
      jcas.throwFeatMissing("keyword", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    return ll_cas.ll_getStringValue(addr, casFeatCode_keyword);
  }
  /** 
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setKeyword(int addr, String v) {
        if (featOkTst && casFeat_keyword == null)
      jcas.throwFeatMissing("keyword", "edu.cmu.lti.oaqa.type.retrieval.FinalQuery");
    ll_cas.ll_setStringValue(addr, casFeatCode_keyword, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * 
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public FinalQuery_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_queryWithOp = jcas.getRequiredFeatureDE(casType, "queryWithOp", "uima.cas.String", featOkTst);
    casFeatCode_queryWithOp  = (null == casFeat_queryWithOp) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_queryWithOp).getCode();

 
    casFeat_queryWithoutOp = jcas.getRequiredFeatureDE(casType, "queryWithoutOp", "uima.cas.String", featOkTst);
    casFeatCode_queryWithoutOp  = (null == casFeat_queryWithoutOp) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_queryWithoutOp).getCode();

 
    casFeat_originalQuery = jcas.getRequiredFeatureDE(casType, "originalQuery", "uima.cas.String", featOkTst);
    casFeatCode_originalQuery  = (null == casFeat_originalQuery) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_originalQuery).getCode();

 
    casFeat_queryID = jcas.getRequiredFeatureDE(casType, "queryID", "uima.cas.String", featOkTst);
    casFeatCode_queryID  = (null == casFeat_queryID) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_queryID).getCode();

 
    casFeat_keyword = jcas.getRequiredFeatureDE(casType, "keyword", "uima.cas.String", featOkTst);
    casFeatCode_keyword  = (null == casFeat_keyword) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_keyword).getCode();

  }
}



    
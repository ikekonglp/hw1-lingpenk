/* First created by JCasGen Sun Oct 14 21:16:20 EDT 2012 */
package model;

import org.apache.uima.cas.Feature;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.tcas.Annotation_Type;

/**
 * The info of the sentence Updated by JCasGen Tue Oct 16 11:08:57 EDT 2012
 * 
 * @generated
 */
public class SentenceInfo_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {
    return fsGenerator;
  }

  /** @generated */
  private final FSGenerator fsGenerator = new FSGenerator() {
    public FeatureStructure createFS(int addr, CASImpl cas) {
      if (SentenceInfo_Type.this.useExistingInstance) {
        // Return eq fs instance if already created
        FeatureStructure fs = SentenceInfo_Type.this.jcas.getJfsFromCaddr(addr);
        if (null == fs) {
          fs = new SentenceInfo(addr, SentenceInfo_Type.this);
          SentenceInfo_Type.this.jcas.putJfsFromCaddr(addr, fs);
          return fs;
        }
        return fs;
      } else
        return new SentenceInfo(addr, SentenceInfo_Type.this);
    }
  };

  /** @generated */
  @SuppressWarnings("hiding")
  public final static int typeIndexID = SentenceInfo.typeIndexID;

  /**
   * @generated
   * @modifiable
   */
  @SuppressWarnings("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("model.SentenceInfo");

  /** @generated */
  final Feature casFeat_SID;

  /** @generated */
  final int casFeatCode_SID;

  /** @generated */
  public String getSID(int addr) {
    if (featOkTst && casFeat_SID == null)
      jcas.throwFeatMissing("SID", "model.SentenceInfo");
    return ll_cas.ll_getStringValue(addr, casFeatCode_SID);
  }

  /** @generated */
  public void setSID(int addr, String v) {
    if (featOkTst && casFeat_SID == null)
      jcas.throwFeatMissing("SID", "model.SentenceInfo");
    ll_cas.ll_setStringValue(addr, casFeatCode_SID, v);
  }

  /**
   * initialize variables to correspond with Cas Type and Features
   * 
   * @generated
   */
  public SentenceInfo_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl) this.casType, getFSGenerator());

    casFeat_SID = jcas.getRequiredFeatureDE(casType, "SID", "uima.cas.String", featOkTst);
    casFeatCode_SID = (null == casFeat_SID) ? JCas.INVALID_FEATURE_CODE
            : ((FeatureImpl) casFeat_SID).getCode();

  }
}

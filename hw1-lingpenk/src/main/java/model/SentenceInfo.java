

/* First created by JCasGen Sun Oct 14 21:16:20 EDT 2012 */
package model;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;
import org.apache.uima.jcas.tcas.Annotation;


/** The info of the sentence
 * Updated by JCasGen Sun Oct 14 22:48:14 EDT 2012
 * XML source: /Users/konglingpeng/git/hw1-lingpenk/hw1-lingpenk/src/main/resources/FileCollectionReader.xml
 * @generated */
public class SentenceInfo extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(SentenceInfo.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected SentenceInfo() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public SentenceInfo(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public SentenceInfo(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public SentenceInfo(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: SID

  /** getter for SID - gets The ID of the sentence
   * @generated */
  public String getSID() {
    if (SentenceInfo_Type.featOkTst && ((SentenceInfo_Type)jcasType).casFeat_SID == null)
      jcasType.jcas.throwFeatMissing("SID", "model.SentenceInfo");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SentenceInfo_Type)jcasType).casFeatCode_SID);}
    
  /** setter for SID - sets The ID of the sentence 
   * @generated */
  public void setSID(String v) {
    if (SentenceInfo_Type.featOkTst && ((SentenceInfo_Type)jcasType).casFeat_SID == null)
      jcasType.jcas.throwFeatMissing("SID", "model.SentenceInfo");
    jcasType.ll_cas.ll_setStringValue(addr, ((SentenceInfo_Type)jcasType).casFeatCode_SID, v);}    
  }

    
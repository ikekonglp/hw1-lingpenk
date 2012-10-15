

/* First created by JCasGen Sun Oct 14 20:55:33 EDT 2012 */
package model;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** This is used to mark gen tags
 * Updated by JCasGen Sun Oct 14 22:05:44 EDT 2012
 * XML source: /Users/konglingpeng/git/hw1-lingpenk/hw1-lingpenk/src/main/resources/WriterConsumer.xml
 * @generated */
public class GenTag extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(GenTag.class);
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
  protected GenTag() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public GenTag(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public GenTag(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public GenTag(JCas jcas, int begin, int end) {
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
     
}

    
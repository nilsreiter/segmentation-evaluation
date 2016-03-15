

/* First created by JCasGen Tue Mar 15 20:33:26 CET 2016 */
package de.unistuttgart.ims.segmentation.api;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Mar 15 20:33:26 CET 2016
 * XML source: /Users/reiterns/Documents/Workspace-Mars/segmentation-evaluation/de.unistuttgart.ims.segmentation.api/target/jcasgen/typesystem.xml
 * @generated */
public class SegmentationUnit extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(SegmentationUnit.class);
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
  protected SegmentationUnit() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public SegmentationUnit(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public SegmentationUnit(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public SegmentationUnit(JCas jcas, int begin, int end) {
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

    
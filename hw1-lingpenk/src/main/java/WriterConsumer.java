/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.GenTag;
import model.SentenceInfo;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;

/**
 * A simple CAS consumer that writes the CAS to an output file as we want.
 * <p>
 * This CAS Consumer takes one parameter:
 * <ul>
 * <li><code>OutputFile</code> - path of the file into which outputs will be written</li>
 * </ul>
 */
public class WriterConsumer extends CasConsumer_ImplBase {
  /**
   * Name of configuration parameter that must be set to the path of a directory into which the
   * output files will be written.
   */
  public static final String PARAM_OUTPUTFILE = "OutputFile";

  /**
   * Store the output file as an object
   */
  File mOutputFile;
  public void initialize() throws ResourceInitializationException {
    mOutputFile = new File((String) getConfigParameterValue(PARAM_OUTPUTFILE));
    if(mOutputFile.exists()){
      mOutputFile.delete();
    }
  }

  /**
   * Processes the CAS which was populated by the NERAnnotator. <br>
   * In this case, the CAS is converted to output file as we want.
   * 
   * @param aCAS
   *          a CAS which has been populated by the NERAnnotator
   * 
   * @throws ResourceProcessException
   *           if there is an error in processing the Resource
   * 
   */
  public void processCas(CAS aCAS) throws ResourceProcessException {
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new ResourceProcessException(e);
    }
    FSIterator it = jcas.getAnnotationIndex(SentenceInfo.type).iterator();
    String SID = null;
    if (it.hasNext()) {
      SentenceInfo sinfo = (SentenceInfo)it.next();
      SID = sinfo.getSID();
    }
    
    FSIterator git = jcas.getAnnotationIndex(GenTag.type).iterator();
    while (git.hasNext()) {
      GenTag gt = (GenTag) git.next();
      int begin = gt.getBegin();
      int rBegin = begin
              - (jcas.getDocumentText().substring(0, begin).length() - jcas.getDocumentText()
                      .substring(0, begin).replaceAll(" ", "").length());
      int end = gt.getEnd();
      int rEnd = end
              - (jcas.getDocumentText().substring(0, end).length() - jcas.getDocumentText()
                      .substring(0, end).replaceAll(" ", "").length())-1;
      try {
        FileWriter writer = new FileWriter(mOutputFile, true);

        writer.write(SID + "|"+ rBegin+" "+rEnd+"|"+ jcas.getDocumentText().substring(begin, end) + "\n");
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
  }
}

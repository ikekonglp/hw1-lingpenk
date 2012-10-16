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



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

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
 * A simple CAS consumer that writes the CAS to XMI format.
 * <p>
 * This CAS Consumer takes one parameter:
 * <ul>
 * <li><code>OutputDirectory</code> - path to directory into which output files will be written</li>
 * </ul>
 */
public class StatConsumer extends CasConsumer_ImplBase {
  /**
   * Name of configuration parameter that must be set to the path of a directory into which the
   * output files will be written.
   */
  public static final String PARAM_OUTPUTFILE = "OutputFile";
  public static final String PARAM_GOLDENSETFILE = "GoldenSetFile";

  File mOutputFile;
  File mGoldenSetFile;
  HashSet<String> gset;
  int correct = 0;
  int error = 0;
  int total = 0;
  public void initialize() throws ResourceInitializationException {
    mOutputFile = new File((String) getConfigParameterValue(PARAM_OUTPUTFILE));
    if(mOutputFile.exists()){
      mOutputFile.delete();
    }
    mGoldenSetFile = new File((String) getConfigParameterValue(PARAM_GOLDENSETFILE));
    if(mGoldenSetFile==null || !mGoldenSetFile.exists()){
      throw new ResourceInitializationException();
    }
    gset = new HashSet<String>();
    try {
      FileReader fr = new FileReader(mGoldenSetFile);
      BufferedReader br = new BufferedReader(fr);
      String myreadline;
      while (br.ready()) {
        myreadline = br.readLine().trim();
        gset.add(myreadline);
      }
      br.close();
      fr.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Processes the CAS which was populated by the TextAnalysisEngines. <br>
   * In this case, the CAS is converted to XMI and written into the output file .
   * 
   * @param aCAS
   *          a CAS which has been populated by the TAEs
   * 
   * @throws ResourceProcessException
   *           if there is an error in processing the Resource
   * 
   * @see org.apache.uima.collection.base_cpm.CasObjectProcessor#processCas(org.apache.uima.cas.CAS)
   */
  public void processCas(CAS aCAS) throws ResourceProcessException {
    String modelFileName = null;

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
        String writeLine = SID + "|"+ rBegin+" "+rEnd+"|"+ jcas.getDocumentText().substring(begin, end);
        if(gset.contains(writeLine)){
          correct++;
        }else{
          error++;
        }
        total++;
        double percision = (double)correct/(double)total;
        double recall = (double)correct/(double)gset.size();
        double Fscore = 2.0/(1.0/percision+1.0/recall);
        writer.write("Current Processed:\t"+total+"\tPecision:\t"+ percision+ "\tRecall:\t"+recall+"\tF-Score:\t"+Fscore+ "\n");
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
  }
}

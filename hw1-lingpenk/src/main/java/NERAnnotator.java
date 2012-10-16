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
import java.io.IOException;

import model.GenTag;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceAccessException;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunker;
import com.aliasi.chunk.Chunking;
import com.aliasi.util.AbstractExternalizable;

/**
 * Example annotator that detects room numbers using Java 1.4 regular expressions.
 */
public class NERAnnotator extends JCasAnnotator_ImplBase {
  Chunker chunker = null;
  public void initialize() {
    File modelFile;
    try {
      System.out.println(getContext().getResourceURI("NERModel"));
      modelFile = new File(getContext().getResourceFilePath("NERModel"));
      
    } catch (ResourceAccessException e1) {
      e1.printStackTrace();
      return;
    }
     try {
      chunker = (Chunker) AbstractExternalizable.readObject(modelFile);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
  /**
   * @see JCasAnnotator_ImplBase#process(JCas)
   */
  public void process(JCas aJCas) {
    if(chunker==null){
      initialize();
    }
    
    // get document text
    String docText = aJCas.getDocumentText();
    
    Chunking chunking = chunker.chunk(docText);
    //System.out.println("Chunking=" + chunking);
    for(Chunk ck : chunking.chunkSet()){
      GenTag annotation = new GenTag(aJCas);
      annotation.setBegin(ck.start());
      annotation.setEnd(ck.end());
      annotation.addToIndexes();
    }
      
  }

}

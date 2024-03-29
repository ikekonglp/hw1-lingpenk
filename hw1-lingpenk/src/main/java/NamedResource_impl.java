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

import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

import com.aliasi.chunk.Chunker;
import com.aliasi.util.AbstractExternalizable;

/**
 * The implementation of the interface to manage the model file and provide the chunker
 * 
 */
public class NamedResource_impl implements NamedResource, SharedResourceObject {
  
  private Chunker chunker;
  
  public void load(DataResource aData) throws ResourceInitializationException {
    try {
      chunker = (Chunker) AbstractExternalizable.readObject(new File(aData.getUri().getPath()));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }


  public Chunker getChunker() {
    // TODO Auto-generated method stub
    return chunker;
  }

}

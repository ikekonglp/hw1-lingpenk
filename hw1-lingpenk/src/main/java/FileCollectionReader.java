import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import model.SentenceInfo;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceConfigurationException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

/**
 * A simple collection reader that reads sentences from the document:
 * <ul>
 * <li><code>InputFile</code> - path to the input files</li>
 * </ul>
 * 
 * 
 */
public class FileCollectionReader extends CollectionReader_ImplBase {
  /**
   * Name of configuration parameter that must be set to the path of a directory containing input
   * files.
   */
  public static final String PARAM_INPUTFILE = "InputFile";

  /**
   * The File Reader used by the collection reader
   */
  FileReader fr;
  
  /**
   * The Buffer Reader used by the collection reader
   */
  BufferedReader br;
  
  /**
   * The current sentence number we have reached
   */
  int mCurrentIndex;
  
  /**
   * The total number of lines
   */
  int totalLines;


  /**
   * initialize the reader and the buffer
   * 
   * @throws ResourceInitializationException
   *           if initialization fails
   * 
   */
  public void initialize() throws ResourceInitializationException {
    File inFile = new File(((String) getConfigParameterValue(PARAM_INPUTFILE)).trim());

    // if input directory does not exist or is not a file, throw exception
    if (!inFile.exists() || !inFile.isFile()) {
      throw new ResourceInitializationException(ResourceConfigurationException.DIRECTORY_NOT_FOUND,
              new Object[] { PARAM_INPUTFILE, this.getMetaData().getName(), inFile.getPath() });
    }

    try {
      fr = new FileReader(inFile);
    } catch (FileNotFoundException e) {
      throw new ResourceInitializationException();
    }

    br = new BufferedReader(fr);
    mCurrentIndex = 0;
    long fileLength = inFile.length();

    LineNumberReader rf = null;
    try {
      rf = new LineNumberReader(new FileReader(inFile));
      if (rf != null) {
        totalLines = 0;
        rf.skip(fileLength);
        totalLines = rf.getLineNumber();
        rf.close();
      }
    } catch (IOException e) {
      if (rf != null) {
        try {
          rf.close();
        } catch (IOException ee) {
        }
      }
    }
    
  }

  public boolean hasNext() {
    try {
      return br.ready();
    } catch (IOException e) {
      return false;
    }
  }

  /**
   * This method break the file into lines, store ID and then give out a cas
   * 
   * @throws IOException
   *           when file read fails
   * @throws CollectionException
   *           when get jcas fails
   */
  public void getNext(CAS aCAS) throws IOException, CollectionException {
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new CollectionException(e);
    }
    
    String line;
    if (br.ready()) {
      line = br.readLine().trim();
      String[] contents = line.split(" ",2);
      jcas.setDocumentText(contents[1]);
      SentenceInfo sInfo = new SentenceInfo(jcas);
      sInfo.setSID(contents[0]);
      sInfo.addToIndexes();
    }else{
      throw new CollectionException();
    }
  }

  /**
   * @see org.apache.uima.collection.base_cpm.BaseCollectionReader#close()
   */
  public void close() throws IOException {
  }

  /**
   * @see org.apache.uima.collection.base_cpm.BaseCollectionReader#getProgress()
   */
  public Progress[] getProgress() {
    return new Progress[] { new ProgressImpl(mCurrentIndex, totalLines, Progress.ENTITIES) };
  }

}

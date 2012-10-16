
import model.GenTag;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceAccessException;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunker;
import com.aliasi.chunk.Chunking;

/**
 * Example annotator that detects room numbers using Java 1.4 regular expressions.
 */
public class NERAnnotator extends JCasAnnotator_ImplBase {
  Chunker chunker = null;

  public void initialize() {
    try {
      NamedResource nr = (NamedResource) getContext().getResourceObject("GenModelFile");
      chunker = nr.getChunker();
    } catch (ResourceAccessException e) {
      e.printStackTrace();
    }
  }

  /**
   * @see JCasAnnotator_ImplBase#process(JCas)
   */
  public void process(JCas aJCas) {
    if (chunker == null) {
      initialize();
    }
    
    // get document text
    String docText = aJCas.getDocumentText();

    Chunking chunking = chunker.chunk(docText);
    for (Chunk ck : chunking.chunkSet()) {
      GenTag annotation = new GenTag(aJCas);
      annotation.setBegin(ck.start());
      annotation.setEnd(ck.end());
      annotation.addToIndexes();
    }

  }

}

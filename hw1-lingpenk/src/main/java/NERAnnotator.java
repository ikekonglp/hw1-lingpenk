
import model.GenTag;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceAccessException;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunker;
import com.aliasi.chunk.Chunking;

/**
 * The annotator that detects gene names from the sentence.
 */
public class NERAnnotator extends JCasAnnotator_ImplBase {
  Chunker chunker = null;

  /**
   * initialize the chunker using the NamedResource
   */
  public void initialize() {
    try {
      NamedResource nr = (NamedResource) getContext().getResourceObject("GenModelFile");
      chunker = nr.getChunker();
    } catch (ResourceAccessException e) {
      e.printStackTrace();
    }
  }

  /**
   * annotate on the cas where we have gene names
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

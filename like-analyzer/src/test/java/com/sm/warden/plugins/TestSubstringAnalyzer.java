package com.sm.warden.plugins;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CachingTokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import like.LikeAnalyzer;

/**
 * @author scorpio
 * @version 1.0.0
 */
public class TestSubstringAnalyzer {


  @Test
  public void testTokenizer() throws Exception {
    String text = "中国Abc";
    testTokenizer(new LikeAnalyzer(), text);
  }

  @Test
  public void testSearch() throws Exception {

    TopDocs topDocs = testSearch("ma", "lining", "liming");
    System.out.println(topDocs.totalHits);
    assert topDocs.totalHits == 1;
  }


  private static TopDocs testSearch(String target, String... sources) throws Exception {
    RAMDirectory directory = new RAMDirectory();
    Analyzer analyzer = new LikeAnalyzer();
    IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

    indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
    IndexWriter writer = new IndexWriter(directory,
            indexWriterConfig);
    for (String src : sources) {
      Document doc = new Document();
      doc.add(new TextField("name", src, Store.YES));
      writer.addDocument(doc);
    }
    writer.close();

    IndexSearcher search = new IndexSearcher(DirectoryReader.open(directory));
    Query query = doParse(analyzer, "name", target);
    TopDocs topDocs = search.search(query, 10);

    return topDocs;
  }


  private static Query doParse(Analyzer analyzer, String fieldName, String fieldValue) throws Exception {

    TokenStream source = analyzer.tokenStream(fieldName, new StringReader(
            fieldValue));
    // call reset() before using incrementToken()
    source.reset();
    CachingTokenFilter buffer = new CachingTokenFilter(source);

    CharTermAttribute termAtt = buffer
            .getAttribute(CharTermAttribute.class);
    PositionIncrementAttribute posIncAtt = buffer
            .hasAttribute(PositionIncrementAttribute.class) ? buffer
            .getAttribute(PositionIncrementAttribute.class) : null;
    PhraseQuery pq = new PhraseQuery();
    int position = -1;
    while (buffer.incrementToken()) {
      position += posIncAtt != null ? posIncAtt.getPositionIncrement()
              : 1;
      pq.add(new Term(fieldName, termAtt.toString()), position);
    }
    Term[] terms = pq.getTerms();
    if (terms.length == 1) {
      return new TermQuery(terms[0]);
    }
    return pq;
  }

  /**
   * 展示分词结果
   *
   * @param analyzer
   * @param text
   * @throws java.io.IOException
   */
  private static void testTokenizer(Analyzer analyzer, String text)
          throws IOException {
    TokenStream ts = analyzer.tokenStream(null, new StringReader(text));
    CharTermAttribute termAtt = ts.addAttribute(CharTermAttribute.class);
    PositionIncrementAttribute posIncAtt = ts
            .addAttribute(PositionIncrementAttribute.class);
    //NOTE: must call reset first
    ts.reset();
    OffsetAttribute offsetAtt = ts.addAttribute(OffsetAttribute.class);
    int position = 0;
    while (ts.incrementToken()) {
      int inc = posIncAtt.getPositionIncrement();
      if (inc > 0) {
        position += inc;
      }
      System.out.println(position + ".[" + termAtt.toString() + ":"
              + offsetAtt.startOffset() + ":" + offsetAtt.endOffset()
              + "]");
    }
  }
}

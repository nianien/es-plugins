package com.sm.warden.plugins;


import com.scorpio.like.lucene.analysis.LikeAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CachingTokenFilter;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopAnalyzer;
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
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author scorpio
 * @version 1.0.0
 */
public class TestSubstringAnalyzer {


  private static RAMDirectory directory = new RAMDirectory();


  private static Analyzer likeAnalyzer() {
    return new LikeAnalyzer();
  }


  private static Analyzer stopAnalyzer() {
    return new StopAnalyzer(new CharArraySet(0, true));
  }


  @Test
  public void testTokenizer() throws Exception {
    String text = "lining,liming";
    showTokenizer(likeAnalyzer(), text);
    System.out.println("=========");
    showTokenizer(stopAnalyzer(), text);
  }

  @Test
  public void testSearch() throws Exception {
    String field = "name";
    addDoc(likeAnalyzer(), field, "lining");
    addDoc(likeAnalyzer(), field, "liming");
    TopDocs topDocs = search(new LikeAnalyzer(), field, "min");
    assert topDocs.totalHits == 1 : "expected: 1,actual:" + topDocs.totalHits;
    topDocs = search(likeAnalyzer(), field, "ing");
    assert topDocs.totalHits == 2 : "expected: 2,actual:" + topDocs.totalHits;
  }

  @Test
  public void testStopSearch() throws Exception {
    String field = "name";
    addDoc(stopAnalyzer(), field, "lining,liming");
    TopDocs topDocs = search(stopAnalyzer(), field, "lining");
    assert topDocs.totalHits == 1 : "expected: 1,actual:" + topDocs.totalHits;
    topDocs = search(stopAnalyzer(), field, "liming");
    assert topDocs.totalHits == 1 : "expected: 2,actual:" + topDocs.totalHits;
  }

  /**
   * 添加文档
   *
   * @param analyzer
   * @param field
   * @param values
   * @throws Exception
   */
  private void addDoc(Analyzer analyzer, String field, String... values) throws Exception {
    IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
    indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
    IndexWriter writer = new IndexWriter(directory, indexWriterConfig);
    Document doc = new Document();
    for (String value : values) {
      doc.add(new TextField(field, value, Store.YES));
    }
    writer.addDocument(doc);
    writer.close();
  }


  /**
   * 查询文档
   *
   * @param analyzer
   * @param field
   * @param queryString
   * @return
   * @throws Exception
   */
  private static TopDocs search(Analyzer analyzer, String field, String queryString) throws Exception {
    IndexSearcher search = new IndexSearcher(DirectoryReader.open(directory));
    Query query = buildQuery(analyzer, field, queryString);
    TopDocs topDocs = search.search(query, Integer.MAX_VALUE);
    return topDocs;
  }


  /**
   * 生成Query
   *
   * @param analyzer
   * @param fieldName
   * @param fieldValue
   * @return
   * @throws Exception
   */
  private static Query buildQuery(Analyzer analyzer, String fieldName, String fieldValue) throws Exception {

    TokenStream source = analyzer.tokenStream(fieldName, new StringReader(
            fieldValue));
    // call reset() before using incrementToken()
    source.reset();
    CachingTokenFilter buffer = new CachingTokenFilter(source);

    CharTermAttribute termAtt = buffer
            .getAttribute(CharTermAttribute.class);
    List<String> terms = new ArrayList<>();
    while (buffer.incrementToken()) {
      terms.add(termAtt.toString());
    }
    return new PhraseQuery(fieldName, terms.toArray(new String[0]));
  }

  /**
   * 展示分词结果
   *
   * @param analyzer
   * @param text
   * @throws java.io.IOException
   */
  private static void showTokenizer(Analyzer analyzer, String text)
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

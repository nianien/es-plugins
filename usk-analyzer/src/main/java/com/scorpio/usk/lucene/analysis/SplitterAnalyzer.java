package com.scorpio.usk.lucene.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

/**
 * 根据分隔符切词
 *
 * @author scorpio
 * @version 1.0.0
 */
public class SplitterAnalyzer extends Analyzer {


  private CharSequence charSequence;

  /**
   * @param charSequence
   */
  public SplitterAnalyzer(CharSequence charSequence) {
    this.charSequence = charSequence;
  }

  @Override
  protected TokenStreamComponents createComponents(String fieldName) {
    Tokenizer source = new SplitterTokenizer(charSequence);
    return new TokenStreamComponents(source, source);
  }
}

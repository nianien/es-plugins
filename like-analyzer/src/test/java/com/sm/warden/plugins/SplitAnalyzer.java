package com.sm.warden.plugins;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LetterTokenizer;

/**
 * @author scorpio
 * @version 1.0.0
 */
public class SplitAnalyzer extends Analyzer {
  @Override
  protected TokenStreamComponents createComponents(String fieldName) {
    Tokenizer source = new LetterTokenizer();
    return new TokenStreamComponents(source, source);
  }
}

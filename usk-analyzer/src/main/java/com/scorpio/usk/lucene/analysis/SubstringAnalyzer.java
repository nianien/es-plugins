package com.scorpio.usk.lucene.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;

/**
 * 匹配字符任意子串<br>
 *
 * @author skyfalling
 */
public final class SubstringAnalyzer extends Analyzer {


  @Override
  protected TokenStreamComponents createComponents(String fieldName) {
    SubstringTokenizer src = new SubstringTokenizer();
    TokenStream tok = new LowerCaseFilter(src);
    return new TokenStreamComponents(src, tok);
  }

}

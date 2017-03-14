package com.scorpio.usk.lucene.analysis;


import org.apache.lucene.analysis.util.CharTokenizer;

import java.util.HashSet;
import java.util.Set;

/**
 * 根据分割符切词
 *
 * @author scorpio
 * @version 1.0.0
 */
public class SplitterTokenizer extends CharTokenizer {


  private Set<Integer> codePointSet = new HashSet<>();

  /**
   * @param charSequence 分隔符列表
   */
  public SplitterTokenizer(CharSequence charSequence) {
    for (int i = 0; i < charSequence.length(); i++) {
      codePointSet.add(Character.codePointAt(charSequence, i));
    }
  }

  /**
   * Returns true if a codepoint should be included in a token.
   *
   * @param c
   */
  @Override
  protected boolean isTokenChar(int c) {
    return !codePointSet.contains(c);
  }
}

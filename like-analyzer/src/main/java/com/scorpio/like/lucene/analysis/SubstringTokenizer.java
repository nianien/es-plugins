package com.scorpio.like.lucene.analysis;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

import java.io.IOException;
import java.io.Reader;

/**
 * 匹配字符任意子串
 *
 * @author skyfalling
 */
public class SubstringTokenizer extends Tokenizer {
  private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
  private final PositionIncrementAttribute posIncrAtt = addAttribute(PositionIncrementAttribute.class);
  private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
  private int offset = -1;
  private StringBuilder buffer = new StringBuilder();


  /**
   * 重置偏移量
   *
   * @throws IOException
   */
  @Override
  public void reset() throws IOException {
    //must call super reset first
    super.reset();
    //重置偏移量
    this.offset = -1;
  }

  /**
   * 重新{@link super#incrementToken()}方法,每个字符一个term,偏移量加1
   *
   * @return
   * @throws IOException
   */
  @Override
  public final boolean incrementToken() throws IOException {
    clearAttributes();
    //after reset called, offset is -1, and then read full input
    if (offset == -1) {
      readFully(input);
    }
    offset++;
    if (offset < buffer.length()) {
      termAtt.setEmpty().append(buffer.charAt(offset));
      posIncrAtt.setPositionIncrement(1);
      offsetAtt.setOffset(offset, offset + 1);
      return true;
    }
    return false;
  }


  /**
   * 读取并缓存数据源
   *
   * @param reader
   * @throws IOException
   */
  private void readFully(Reader reader) throws IOException {
    this.buffer.setLength(0);
    int length;
    char[] read = new char[64];
    while ((length = reader.read(read)) != -1) {
      this.buffer.append(read, 0, length);
    }
  }
}

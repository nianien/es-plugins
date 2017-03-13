package com.scorpio.like.lucene.analysis;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * 任意字符子串匹配
 *
 * @author skyfalling
 */
public class SubstringTokenizer extends Tokenizer {
  private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
  private final PositionIncrementAttribute posIncrAtt = addAttribute(PositionIncrementAttribute.class);
  private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
  private int offset = -1;
  private List<Character> buffer = new ArrayList<Character>();


  @Override
  public void reset() throws IOException {
    //must call super reset first
    super.reset();
    this.offset = -1;
  }

  @Override
  public final boolean incrementToken() throws IOException {
    clearAttributes();
    if (offset == -1) {
      readFully(input);
    }
    offset++;
    if (offset < buffer.size()) {
      termAtt.setEmpty().append(buffer.get(offset));
      posIncrAtt.setPositionIncrement(1);
      offsetAtt.setOffset(offset, offset + 1);
      return true;
    }
    return false;
  }


  private void readFully(Reader reader) throws IOException {
    this.buffer.clear();
    int length = -1;
    char[] readed = new char[64];
    while ((length = reader.read(readed)) != -1) {
      for (int i = 0; i < length; i++) {
        this.buffer.add(readed[i]);
      }
    }
  }
}

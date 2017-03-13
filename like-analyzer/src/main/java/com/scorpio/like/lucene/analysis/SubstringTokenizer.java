package com.scorpio.like.lucene.analysis;

import org.apache.lucene.analysis.CharacterUtils;
import org.apache.lucene.analysis.CharacterUtils.CharacterBuffer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

import java.io.IOException;

/**
 * 匹配字符任意子串<br/>
 *
 * @author skyfalling
 */
public class SubstringTokenizer extends Tokenizer {
  private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
  private final PositionIncrementAttribute posIncrAtt = addAttribute(PositionIncrementAttribute.class);
  private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
  private final CharacterBuffer ioBuffer = CharacterUtils.newCharacterBuffer(4096);
  private int finalOffset = -1;
  private int bufferOffset = 0;


  /**
   * 每个字符切一个term,偏移量加1,空间复杂度o(n)
   *
   * @return
   * @throws IOException
   */
  @Override
  public final boolean incrementToken() throws IOException {
    clearAttributes();
    //exhaust the buffer
    if (bufferOffset >= ioBuffer.getLength()) {
      // read supplementary char aware with CharacterUtils
      CharacterUtils.fill(ioBuffer, input);
      if (ioBuffer.getLength() == 0) {
        return false;
      }
      bufferOffset = 0;
    }
    termAtt.append(ioBuffer.getBuffer()[bufferOffset]);
    posIncrAtt.setPositionIncrement(1);
    offsetAtt.setOffset(correctOffset(finalOffset), correctOffset(finalOffset + 1));
    bufferOffset++;
    finalOffset++;
    return true;
  }

  @Override
  public void reset() throws IOException {
    //must call super reset first
    super.reset();
    ioBuffer.reset();
    bufferOffset = 0;
    finalOffset = 0;
  }


}

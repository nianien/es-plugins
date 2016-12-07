package like;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;

/**
 * 单个字符的分词器, 适用于短字符串的任意部分匹配<br>
 *
 * @author skyfalling
 */
public final class LikeAnalyzer extends Analyzer {


  @Override
  protected TokenStreamComponents createComponents(String fieldName) {
    SubstringTokenizer src = new SubstringTokenizer();
    TokenStream tok = new LowerCaseFilter(src);
    return new TokenStreamComponents(src, tok);
  }

}

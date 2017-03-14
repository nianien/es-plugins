package com.scorpio.usk.elasticsearch.index.analysis;

import com.scorpio.usk.lucene.analysis.SubstringTokenizer;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;

/**
 * 提供{@link SubstringTokenizer}
 *
 * @author scorpio
 * @version 1.0.0
 */
public class SubStringTokenizerFactory extends AbstractTokenizerFactory {

  public SubStringTokenizerFactory(IndexSettings indexSettings,
                                   Environment env,
                                   String name,
                                   Settings settings) {
    super(indexSettings, name, settings);
  }

  @Override
  public Tokenizer create() {
    return new SubstringTokenizer();
  }
}

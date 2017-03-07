package com.scorpio.es.plugin.like;

import com.scorpio.lucene.SubstringTokenizer;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;

/**
 * 用于提供{@link Tokenizer}
 *
 * @author scorpio
 * @version 1.0.0
 */
public class LikeTokenizerFactory extends AbstractTokenizerFactory {

  public LikeTokenizerFactory(IndexSettings indexSettings, Environment env, @Assisted String name, @Assisted Settings
          settings) {
    super(indexSettings, name, settings);
  }

  @Override
  public Tokenizer create() {
    return new SubstringTokenizer();
  }
}

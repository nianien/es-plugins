package com.scorpio.usk.elasticsearch.index.analysis;

import com.scorpio.usk.lucene.analysis.SplitterTokenizer;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;

/**
 * 提供{@link SplitterTokenizer}
 *
 * @author scorpio
 * @version 1.0.0
 */
public class SplitTokenizerFactory extends AbstractTokenizerFactory {

  private CharSequence splitters;

  public SplitTokenizerFactory(IndexSettings indexSettings,
                               Environment env,
                               String name,
                               Settings settings) {
    super(indexSettings, name, settings);
    this.splitters = settings.get("splitters", ",");
  }

  @Override
  public Tokenizer create() {
    return new SplitterTokenizer(splitters);
  }
}

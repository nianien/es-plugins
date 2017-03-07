package com.scorpio.es.plugin.like;


import com.scorpio.lucene.LikeAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;

/**
 * 用于提供{@link Analyzer}
 *
 * @author scorpio
 * @version 1.0.0
 */
public class LikeAnalyzerProvider extends AbstractIndexAnalyzerProvider<LikeAnalyzer> {
  private final LikeAnalyzer likeAnalyzer;

  @Inject
  public LikeAnalyzerProvider(IndexSettings indexSettings, Environment env,
                              @Assisted String name,
                              @Assisted Settings settings) {
    super(indexSettings, name, settings);
    this.likeAnalyzer = new LikeAnalyzer();
  }

  @Override
  public LikeAnalyzer get() {
    return this.likeAnalyzer;
  }

}

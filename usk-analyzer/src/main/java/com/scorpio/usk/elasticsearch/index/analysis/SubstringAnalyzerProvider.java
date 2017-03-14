package com.scorpio.usk.elasticsearch.index.analysis;


import com.scorpio.usk.lucene.analysis.SubstringAnalyzer;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;

/**
 * 提供{@link SubstringAnalyzer}
 *
 * @author scorpio
 * @version 1.0.0
 */
public class SubstringAnalyzerProvider extends AbstractIndexAnalyzerProvider<SubstringAnalyzer> {
  private final SubstringAnalyzer substringAnalyzer;

  public SubstringAnalyzerProvider(IndexSettings indexSettings,
                                   Environment env,
                                   String name,
                                   Settings settings) {
    super(indexSettings, name, settings);
    this.substringAnalyzer = new SubstringAnalyzer();
  }

  @Override
  public SubstringAnalyzer get() {
    return this.substringAnalyzer;
  }

}

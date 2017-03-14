package com.scorpio.usk.elasticsearch.index.analysis;

import com.scorpio.usk.lucene.analysis.SplitterAnalyzer;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;

/**
 * 提供{@link SplitterAnalyzer}
 *
 * @author scorpio
 * @version 1.0.0
 */
public class SplitAnalyzerProvider extends AbstractIndexAnalyzerProvider<SplitterAnalyzer> {

  private final SplitterAnalyzer splitterAnalyzer;

  /**
   * 根据分隔符切词
   *
   * @param indexSettings
   * @param env
   * @param name
   * @param settings
   */
  public SplitAnalyzerProvider(IndexSettings indexSettings,
                               Environment env,
                               String name,
                               Settings settings) {
    super(indexSettings, name, settings);
    this.splitterAnalyzer = new SplitterAnalyzer(settings.get("splitters", ","));
  }

  @Override
  public SplitterAnalyzer get() {
    return splitterAnalyzer;
  }
}

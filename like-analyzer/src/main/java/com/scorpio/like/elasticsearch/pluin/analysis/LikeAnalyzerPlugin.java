package com.scorpio.like.elasticsearch.pluin.analysis;

import com.scorpio.like.elasticsearch.index.analysis.LikeAnalyzerProvider;
import com.scorpio.like.elasticsearch.index.analysis.LikeTokenizerFactory;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.index.analysis.AnalyzerProvider;
import org.elasticsearch.index.analysis.TokenizerFactory;
import org.elasticsearch.indices.analysis.AnalysisModule.AnalysisProvider;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;

import java.util.HashMap;
import java.util.Map;


/**
 * 分词器插件,{@link AnalysisPlugin}接口实现
 */
public class LikeAnalyzerPlugin extends Plugin implements AnalysisPlugin {

  public static final String PLUGIN_NAME = "like";

  @Override
  public Map<String, AnalysisProvider<TokenizerFactory>> getTokenizers() {
    Map<String, AnalysisProvider<TokenizerFactory>> extra = new HashMap<>();
    extra.put(PLUGIN_NAME, LikeTokenizerFactory::new);

    return extra;
  }

  @Override
  public Map<String, AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
    Map<String, AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> extra = new HashMap<>();
    extra.put(PLUGIN_NAME, LikeAnalyzerProvider::new);
    return extra;
  }


}
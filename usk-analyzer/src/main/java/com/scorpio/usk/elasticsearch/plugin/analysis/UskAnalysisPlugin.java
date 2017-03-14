package com.scorpio.usk.elasticsearch.plugin.analysis;

import com.scorpio.usk.elasticsearch.index.analysis.SplitAnalyzerProvider;
import com.scorpio.usk.elasticsearch.index.analysis.SplitTokenizerFactory;
import com.scorpio.usk.elasticsearch.index.analysis.SubStringTokenizerFactory;
import com.scorpio.usk.elasticsearch.index.analysis.SubstringAnalyzerProvider;

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
public class UskAnalysisPlugin extends Plugin implements AnalysisPlugin {

  public static final String PLUGIN_SPLIT = "usk_split";
  public static final String PLUGIN_LIKE = "usk_like";

  @Override
  public Map<String, AnalysisProvider<TokenizerFactory>> getTokenizers() {
    Map<String, AnalysisProvider<TokenizerFactory>> extra = new HashMap<>();
    extra.put(PLUGIN_LIKE, SubStringTokenizerFactory::new);
    extra.put(PLUGIN_SPLIT, SplitTokenizerFactory::new);

    return extra;
  }

  @Override
  public Map<String, AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
    Map<String, AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> extra = new HashMap<>();
    extra.put(PLUGIN_LIKE, SubstringAnalyzerProvider::new);
    extra.put(PLUGIN_SPLIT, SplitAnalyzerProvider::new);
    return extra;
  }

}
package like;

import org.elasticsearch.index.analysis.AnalysisModule;

public class LikeAnalyzerBinderProcessor extends
        AnalysisModule.AnalysisBinderProcessor {

  /* This is the only function that you need. It simply adds our
   * LikeAnalyzerProvider class to a list of bindings.
   */
  @Override
  public void processAnalyzers(AnalyzersBindings analyzersBindings) {
    analyzersBindings.processAnalyzer(LikeAnalyzerProvider.NAME,
            LikeAnalyzerProvider.class);
  }
}

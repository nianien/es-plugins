package like;

import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.plugins.Plugin;


public class LikeAnalyzerPlugin extends Plugin {

  /* Set the name that will be assigned to this plugin. */
  @Override
  public String name() {
    return "like-analyzer";
  }

  /* Return a description of this plugin. */
  @Override
  public String description() {
    return "Analyzer worked as sql like operation";
  }

  /* This is the function that will register our analyzer with
   * Elasticsearch.
   */
  public void onModule(AnalysisModule analysisModule) {
    analysisModule.addProcessor(new LikeAnalyzerBinderProcessor());
  }
}
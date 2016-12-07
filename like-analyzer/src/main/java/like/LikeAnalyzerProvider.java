package like;


import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import org.elasticsearch.index.settings.IndexSettings;

/**
 * @author scorpio
 * @version 1.0.0
 */
public class LikeAnalyzerProvider extends AbstractIndexAnalyzerProvider<LikeAnalyzer> {
  private final LikeAnalyzer likeAnalyzer;

  @Inject
  public LikeAnalyzerProvider(Index index,
                              @IndexSettings Settings indexSettings,
                              @Assisted String name,
                              @Assisted Settings settings) {
    super(index, indexSettings, name, settings);
    this.likeAnalyzer = new LikeAnalyzer();
  }

  @Override
  public LikeAnalyzer get() {
    return this.likeAnalyzer;
  }

  public static final String NAME = "like";
}

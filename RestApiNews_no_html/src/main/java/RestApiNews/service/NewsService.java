package RestApiNews.service;

import RestApiNews.entity.News;
import java.util.List;

public interface NewsService extends Service<News> {
   List<News> searchNewsByWord(String word);
}

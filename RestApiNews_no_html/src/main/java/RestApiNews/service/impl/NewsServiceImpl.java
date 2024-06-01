package RestApiNews.service.impl;

import RestApiNews.entity.News;
import RestApiNews.repository.NewsRepository;
import RestApiNews.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public List<News> searchNewsByWord(String word) {
        return newsRepository.searchNewsByWord(word);
    }
    
    @Override
    public News read(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    @Override
    public List<News> read() {
        return newsRepository.findAll();
    }

    @Override
    public void save(News news) {
        newsRepository.save(news);
    }

    @Override
    public void edit(News news) {
        News existingEntity = read(news.getId());
        if (existingEntity != null) {
            existingEntity.setTitle(news.getTitle());
            existingEntity.setContent(news.getContent());
            existingEntity.setPublishDate(news.getPublishDate());
            newsRepository.save(existingEntity);
        }
    }

    @Override
    public void delete(Long id) {
        newsRepository.deleteById(id);
    }
}

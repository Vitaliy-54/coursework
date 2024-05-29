package RestApiNews.service.impl;

import RestApiNews.entity.NewsCategories;
import RestApiNews.repository.NewsCategoriesRepository;
import RestApiNews.service.NewsCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsCategoriesServiceImpl implements NewsCategoriesService {

    @Autowired
    private NewsCategoriesRepository newsCategoriesRepository;

    @Override
    public NewsCategories read(Long id) {
        return newsCategoriesRepository.findById(id).orElse(null);
    }

    @Override
    public List<NewsCategories> read() {
        return newsCategoriesRepository.findAll();
    }

    @Override
    public void save(NewsCategories newsCategories) {
        newsCategoriesRepository.save(newsCategories);
    }

    @Override
    public void edit(NewsCategories newsCategories) {
        NewsCategories existingEntity = read(newsCategories.getId());
        if (existingEntity != null) {
            existingEntity.setNews(newsCategories.getNews());
            existingEntity.setCategories(newsCategories.getCategories());
            newsCategoriesRepository.save(existingEntity);
        }
    }

    @Override
    public void delete(Long id) {
        newsCategoriesRepository.deleteById(id);
    }
}
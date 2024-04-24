package by.vstu.restapinews.repository;

import by.vstu.restapinews.model.NewsCategories;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NewsCategoriesRepository extends CrudRepository<NewsCategories, Long> {

    List<NewsCategories> findByNewsIdAndCategoriesId(Long newsId, Long categoriesId);
}

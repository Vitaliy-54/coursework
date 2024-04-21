package ru.mycompany.restapinews.repository;

import org.springframework.data.repository.CrudRepository;
import ru.mycompany.restapinews.model.NewsCategories;

import java.util.List;

public interface NewsCategoriesRepository extends CrudRepository<NewsCategories, Long> {

    List<NewsCategories> findByNewsIdAndCategoriesId(Long newsId, Long categoriesId);
}

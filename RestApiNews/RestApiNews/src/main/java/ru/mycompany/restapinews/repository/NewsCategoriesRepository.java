package ru.mycompany.restapinews.repository;

import org.springframework.data.repository.CrudRepository;
import ru.mycompany.restapinews.model.NewsCategories;

public interface NewsCategoriesRepository extends CrudRepository<NewsCategories, Long> {
}

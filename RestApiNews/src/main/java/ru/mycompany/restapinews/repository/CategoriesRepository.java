package ru.mycompany.restapinews.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.mycompany.restapinews.model.Categories;

@Repository
public interface CategoriesRepository extends CrudRepository<Categories, Long> {
}

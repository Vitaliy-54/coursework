package by.vstu.restapinews.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import by.vstu.restapinews.model.Categories;

@Repository
public interface CategoriesRepository extends CrudRepository<Categories, Long> {
}

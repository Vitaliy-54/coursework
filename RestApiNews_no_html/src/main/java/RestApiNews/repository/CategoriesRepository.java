package RestApiNews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import RestApiNews.entity.Categories;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
}

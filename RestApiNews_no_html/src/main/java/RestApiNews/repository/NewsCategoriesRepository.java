package RestApiNews.repository;

import RestApiNews.entity.Categories;
import RestApiNews.entity.NewsCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsCategoriesRepository extends JpaRepository<NewsCategories, Long> {
}

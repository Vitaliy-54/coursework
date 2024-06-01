package RestApiNews.repository;

import RestApiNews.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
  @Query("SELECT n FROM News n WHERE LOWER(n.content) LIKE LOWER(CONCAT('%', :word, '%'))")
  List<News> searchNewsByWord(@Param("word") String word);
}

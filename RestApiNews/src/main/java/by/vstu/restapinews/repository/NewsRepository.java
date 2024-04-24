package by.vstu.restapinews.repository;

import org.springframework.data.repository.CrudRepository;
import by.vstu.restapinews.model.News;

public interface NewsRepository extends CrudRepository<News, Long> {
}

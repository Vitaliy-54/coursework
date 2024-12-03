package RestApiNews.controller;

import RestApiNews.entity.Categories;
import RestApiNews.entity.News;
import RestApiNews.entity.NewsCategories;
import RestApiNews.service.NewsCategoriesService;
import RestApiNews.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/newscategories")
public class NewsCategoriesController {

    @Autowired
    private NewsCategoriesService newsCategoriesService;

    @Autowired // Внедрите NewsService
    private NewsService newsService;

    @GetMapping
    public ResponseEntity<List<NewsCategories>> getAll() {
        List<NewsCategories> entities = newsCategoriesService.read();
        if (entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsCategories> getById(@PathVariable Long id) {
        NewsCategories entity = newsCategoriesService.read(id);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NewsCategories> create(@RequestBody NewsCategories entity) {
        newsCategoriesService.save(entity);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsCategories> update(@PathVariable Long id, @RequestBody NewsCategories entity) {
        NewsCategories existingEntity = newsCategoriesService.read(id);
        if (existingEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Обновляем поля существующей сущности
        existingEntity.setNews(entity.getNews());
        existingEntity.setCategories(entity.getCategories());

        // Сохраняем обновленную сущность
        newsCategoriesService.edit(existingEntity);

        return new ResponseEntity<>(existingEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        NewsCategories entity = newsCategoriesService.read(id);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        newsCategoriesService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/CategoriesForNews/{id}")
    public ResponseEntity<List<Categories>> CategoriesForNews(@PathVariable("id") Long id) {
        News news = newsService.read(id);
        if (news != null) {
            List<Categories> categories = news.getNewsCategories().stream()
                    .map(NewsCategories::getCategories)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
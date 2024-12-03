package RestApiNews.controller;

import RestApiNews.entity.News;
import RestApiNews.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping
    public ResponseEntity<List<News>> getAll() {
        List<News> entities = newsService.read();
        if (entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @GetMapping("/search/{word}")
    public ResponseEntity<List<News>> searchNewsByWord(@PathVariable String word) {
    List<News> newsList = newsService.searchNewsByWord(word);
    if (newsList.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(newsList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getById(@PathVariable Long id) {
        News entity = newsService.read(id);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<News> create(@RequestBody News entity) {
        newsService.save(entity);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<News> update(@PathVariable Long id, @RequestBody News entity) {
        News existingEntity = newsService.read(id);
        if (existingEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Обновляем существующую сущность с новыми данными
        existingEntity.setTitle(entity.getTitle());
        existingEntity.setContent(entity.getContent());
        existingEntity.setPublishDate(entity.getPublishDate());

        // Если у вас есть необходимость обновить список категорий, вы можете добавить логику для этого.
        // Например, если вы хотите заменить старые категории на новые:
        existingEntity.setNewsCategories(entity.getNewsCategories());

        newsService.edit(existingEntity); // Сохраняем обновленную сущность
        return new ResponseEntity<>(existingEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        News entity = newsService.read(id);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        newsService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/displayWordcount/{id}")
    public ResponseEntity<Long> wordcountNewsID(@PathVariable("id") Long id) {
        News news = newsService.read(id);
        if (news != null) {
            Long wordCount = 0L;
            if (news.getContent() != null) {
                wordCount = (long) news.getContent().split("\\s+").length;
            }
            return new ResponseEntity<>(wordCount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

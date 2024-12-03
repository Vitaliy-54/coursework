package RestApiNews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import RestApiNews.entity.Categories;
import RestApiNews.service.CategoriesService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping
    public ResponseEntity<List<Categories>> getAll() {
        List<Categories> entities = categoriesService.read();
        if (entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categories> getById(@PathVariable Long id) {
        Categories entity = categoriesService.read(id);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Categories> create(@RequestBody Categories entity) {
        categoriesService.save(entity);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categories> update(@PathVariable Long id, @RequestBody Categories entity) {
        Categories existingEntity = categoriesService.read(id);
        if (existingEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Обновляем существующую сущность с новыми данными
        existingEntity.setName(entity.getName()); // Предположим, что у вас есть метод setName
        // Обновите другие поля, если необходимо

        categoriesService.edit(existingEntity); // Сохраняем обновленную сущность
        return new ResponseEntity<>(existingEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Categories entity = categoriesService.read(id);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        categoriesService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

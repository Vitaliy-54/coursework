package RestApiNews.service.impl;

import RestApiNews.entity.Categories;
import RestApiNews.repository.CategoriesRepository;
import RestApiNews.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public Categories read(Long id) {
        return categoriesRepository.findById(id).orElse(null);
    }

    @Override
    public List<Categories> read() {
        return categoriesRepository.findAll();
    }

    @Override
    public void save(Categories category) {
        categoriesRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        categoriesRepository.deleteById(id);
    }

    @Override
    public void edit(Categories category) {
        Categories existingEntity = read(category.getId());
        if (existingEntity != null) {
            existingEntity.setName(category.getName());
            categoriesRepository.save(existingEntity);
        }
    }

    @Override
    public void addCategory(Categories category) {
        categoriesRepository.save(category);
    }
}

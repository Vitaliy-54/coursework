package RestApiNews.service;

import RestApiNews.entity.Categories;

public interface CategoriesService extends Service<Categories> {
    void addCategory(Categories category);
}
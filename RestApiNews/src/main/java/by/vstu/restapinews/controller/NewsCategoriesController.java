package by.vstu.restapinews.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import by.vstu.restapinews.model.Categories;
import by.vstu.restapinews.model.News;
import by.vstu.restapinews.model.NewsCategories;
import by.vstu.restapinews.repository.NewsRepository;
import by.vstu.restapinews.repository.CategoriesRepository;
import by.vstu.restapinews.repository.NewsCategoriesRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NewsCategoriesController {
    private final NewsRepository newsRepository;
    private final CategoriesRepository categoriesRepository;
    private final NewsCategoriesRepository newsCategoriesRepository;
    @GetMapping("/newscategories")
    public String newscategories(Model model) {
        model.addAttribute("newsCategoriesList", newsCategoriesRepository.findAll());
        return "newscategories";
    }

    @GetMapping("/newscategories/create")
    public String createNewsCategories(Model model) {
        model.addAttribute("newsCategory", new NewsCategories());
        return "createNewsCategories";
    }

    @PostMapping("/newscategories/create")
    public String createNewsCategories(@RequestParam Long newsId, @RequestParam Long categoriesId) {
        // Получаем новость с данным id
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid news Id:" + newsId));
        // Получаем категорию с данным id
        Categories categories = categoriesRepository.findById(categoriesId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + categoriesId));

        // Проверяем, существует ли уже связь NewsCategories для данной пары ID новости и ID категории
        List<NewsCategories> existingNewsCategories = newsCategoriesRepository.findByNewsIdAndCategoriesId(newsId, categoriesId);
        if (!existingNewsCategories.isEmpty()) {
            // Если связь уже существует, просто возвращаемся
            return "redirect:/newscategories";
        }

        // Если связи не существует, создаем новую
        NewsCategories newsCategories = new NewsCategories();
        newsCategories.setNews(news);
        newsCategories.setCategories(categories);
        newsCategoriesRepository.save(newsCategories);
        return "redirect:/newscategories";
    }

    @GetMapping("/newscategories/delete")
    public String deleteNewsCategories(Model model) {
        model.addAttribute("newsCategories", new NewsCategories());
        return "deleteNewsCategories";
    }


    @PostMapping("/newscategories/delete")
    public String deleteNewsCategories(@RequestParam Long newsId, @RequestParam Long categoriesId) {
        // Проверяем, существует ли новость с данным id
        if (!newsRepository.existsById(newsId)) {
            throw new IllegalArgumentException("Invalid news Id:" + newsId);
        }
        // Проверяем, существует ли категория с данным id
        if (!categoriesRepository.existsById(categoriesId)) {
            throw new IllegalArgumentException("Invalid category Id:" + categoriesId);
        }

        // Находим связь NewsCategories по ID новости и ID категории
        List<NewsCategories> existingNewsCategories = newsCategoriesRepository.findByNewsIdAndCategoriesId(newsId, categoriesId);
        if (!existingNewsCategories.isEmpty()) {
            // Если связь существует, удаляем ее
            NewsCategories existingNewsCategory = existingNewsCategories.get(0);
            newsCategoriesRepository.delete(existingNewsCategory);
        }
        return "redirect:/newscategories";
    }
}

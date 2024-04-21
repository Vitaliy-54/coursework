package ru.mycompany.restapinews.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mycompany.restapinews.model.News;
import ru.mycompany.restapinews.model.Categories;
import ru.mycompany.restapinews.model.NewsCategories;
import ru.mycompany.restapinews.repository.NewsCategoriesRepository;
import ru.mycompany.restapinews.repository.NewsRepository;
import ru.mycompany.restapinews.repository.CategoriesRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyController {

    private final NewsRepository newsRepository;
    private final CategoriesRepository categoriesRepository;
    private final NewsCategoriesRepository newsCategoriesRepository;

    // News controllers
    @GetMapping("/news")
    public String news(Model model) {
        model.addAttribute("newsList", newsRepository.findAll());
        return "news";
    }

    @GetMapping("/news/create")
    public String createNews(Model model) {
        model.addAttribute("news", new News());
        return "createNews";
    }

    @PostMapping("/news/create")
    public String createNews(@ModelAttribute News news) {
        newsRepository.save(news);
        return "redirect:/news";
    }

    @GetMapping("/news/search")
    public String searchNewsID(Model model) {
        return "searchNewsID";
    }

    @GetMapping("/news/search/{id}")
    public String searchNewsID(@PathVariable("id") Long id, Model model) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid news Id:" + id));
        model.addAttribute("newsList", news);
        return "news";
    }

    @GetMapping("/news/update")
    public String selectUpdateNewsID(Model model) {
        model.addAttribute("news", new News());
        return "selectUpdateNewsID";
    }

    @GetMapping("/news/update/{id}")
    public String updateNewsID(@PathVariable("id") Long id, Model model) {
        News news = newsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid news Id:" + id));
        model.addAttribute("news", news);
        return "updateNewsID";
    }

    @PostMapping("/news/update/{id}")
    public String updateNewsID(@PathVariable("id") Long id, @ModelAttribute("news") News news) {
        // ensure the ID is preserved
        news.setId(id);
        newsRepository.save(news);
        return "redirect:/news";
    }

    // Categories controllers
    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categoriesList", categoriesRepository.findAll());
        return "categories";
    }

    @GetMapping("/categories/create")
    public String createCategories(Model model) {
        model.addAttribute("categories", new Categories());
        return "createCategories";
    }

    @PostMapping("/categories/create")
    public String createCategories(@ModelAttribute Categories categories) {
        categoriesRepository.save(categories);
        return "redirect:/categories";
    }

    @GetMapping("/categories/search")
    public String searchCategoriesID(Model model) {
        return "searchCategoriesID";
    }

    @GetMapping("/categories/search/{id}")
    public String searchCategoriesID(@PathVariable("id") Long id, Model model) {
        Categories categories = categoriesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid news Id:" + id));
        model.addAttribute("categoriesList", categories);
        return "categories";
    }

    @GetMapping("/categories/update")
    public String selectUpdateCategoriesID(Model model) {
        model.addAttribute("categories", new Categories());
        return "selectUpdateCategoriesID";
    }

    @GetMapping("/categories/update/{id}")
    public String updateCategoriesID(@PathVariable("id") Long id, Model model) {
        Categories categories = categoriesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid news Id:" + id));
        model.addAttribute("categories", categories);
        return "updateCategoriesID";
    }

    @PostMapping("/categories/update/{id}")
    public String updateCategoriesID(@PathVariable("id") Long id, @ModelAttribute("categories") Categories categories) {
        // ensure the ID is preserved
        categories.setId(id);
        categoriesRepository.save(categories);
        return "redirect:/categories";
    }

    // NewsCategories controllers
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


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("newsCategoriesList", newsCategoriesRepository.findAll());
        return "home";
    }
}


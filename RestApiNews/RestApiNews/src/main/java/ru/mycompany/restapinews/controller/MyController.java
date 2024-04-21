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

    // NewsCategories controllers
    @GetMapping("/newscategories")
    public String newsсategories(Model model) {
        model.addAttribute("newsСategoriesList", newsCategoriesRepository.findAll());
        return "newscategories";
    }

    @GetMapping("/newscategories/create")
    public String createNewsCategories(Model model) {
        model.addAttribute("newsCategory", new NewsCategories());
        return "createNewsCategories";
    }

    @PostMapping("/newscategories/create")
    public String createNewsCategories(@ModelAttribute NewsCategories newsCategories) {
        newsCategoriesRepository.save(newsCategories);
        return "redirect:/newscategories";
    }



}
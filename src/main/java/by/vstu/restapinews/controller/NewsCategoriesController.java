package by.vstu.restapinews.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import by.vstu.restapinews.model.Categories;
import by.vstu.restapinews.model.News;
import by.vstu.restapinews.model.NewsCategories;
import by.vstu.restapinews.repository.NewsRepository;
import by.vstu.restapinews.repository.CategoriesRepository;
import by.vstu.restapinews.repository.NewsCategoriesRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequiredArgsConstructor
public class NewsCategoriesController {
    private final NewsRepository newsRepository;
    private final CategoriesRepository categoriesRepository;
    private final NewsCategoriesRepository newsCategoriesRepository;

    @GetMapping("/theme/{id}")
    public String getNewsByCategory(@PathVariable("id") Long id, Model model) {
        Categories category = categoriesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        List<NewsCategories> newsCategories = newsCategoriesRepository.findAllByCategories(category);
        model.addAttribute("newsList", newsCategories.stream()
                .map(NewsCategories::getNews)
                .collect(Collectors.toList()));
        model.addAttribute("themeName", category.getName());
        List<Categories> allCategories = StreamSupport.stream(newsCategoriesRepository.findAll().spliterator(), false)
                .map(NewsCategories::getCategories)
                .distinct()
                .collect(Collectors.toList());
        model.addAttribute("allCategories", allCategories); // Измененная строка
        return "theme";
    }

    @GetMapping("/CategoriesForNews")
    public String selectCategoriesForNews(Model model) {
        model.addAttribute("CategoriesForNews", new NewsCategories());
        return "selectCategoriesForNews";
    }

    @GetMapping("/CategoriesForNews/{id}")
    public String CategoriesForNews(@PathVariable("id") Long id, Model model) {
        if (newsRepository.existsById(id)) {
            News news = newsRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid news Id:" + id));
            List<Categories> categories = news.getNewsCategories().stream()
                    .map(NewsCategories::getCategories)
                    .collect(Collectors.toList());
            model.addAttribute("news", news);
            model.addAttribute("categories", categories);
        } else {
            model.addAttribute("error", "Новость с ID " + id + " не найдена");
            return "selectCategoriesForNews";
        }
            return "CategoriesForNews";
    }

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
    public String createNewsCategories(@RequestParam Long newsId, @RequestParam Long categoriesId, Model model) {
        if (newsRepository.existsById(newsId) && categoriesRepository.existsById(categoriesId)) {
            News news = newsRepository.findById(newsId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid news Id:" + newsId));
            Categories categories = categoriesRepository.findById(categoriesId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + categoriesId));
            List<NewsCategories> existingNewsCategories = newsCategoriesRepository.findByNewsIdAndCategoriesId(newsId, categoriesId);
            if (!existingNewsCategories.isEmpty()) {
                return "redirect:/newscategories";
            }
            NewsCategories newsCategories = new NewsCategories();
            newsCategories.setNews(news);
            newsCategories.setCategories(categories);
            newsCategoriesRepository.save(newsCategories);
            return "redirect:/newscategories";
        } else {
            model.addAttribute("error", "Новость с ID " + newsId + " или категория с ID " + categoriesId + " не найдена");
            return "createNewsCategories";
        }
    }

    @GetMapping("/newscategories/delete")
    public String deleteNewsCategories(Model model) {
        model.addAttribute("newsCategories", new NewsCategories());
        return "deleteNewsCategories";
    }

    @PostMapping("/newscategories/delete")
    public String deleteNewsCategories(@RequestParam Long newsId, @RequestParam Long categoriesId, Model model) {
        if (newsRepository.existsById(newsId) && categoriesRepository.existsById(categoriesId)) {
            List<NewsCategories> existingNewsCategories = newsCategoriesRepository.findByNewsIdAndCategoriesId(newsId, categoriesId);
            if (!existingNewsCategories.isEmpty()) {
                NewsCategories existingNewsCategory = existingNewsCategories.get(0);
                newsCategoriesRepository.delete(existingNewsCategory);
            }
            return "redirect:/newscategories";
        } else {
            model.addAttribute("error", "Новость с ID " + newsId + " или категория с ID " + categoriesId + " не найдена");
            return "deleteNewsCategories";
        }
    }
}
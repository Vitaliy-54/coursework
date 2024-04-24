package by.vstu.restapinews.controller;

import by.vstu.restapinews.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import by.vstu.restapinews.model.News;

@Controller
@RequiredArgsConstructor
public class NewsController {
    private final NewsRepository newsRepository;
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

    @GetMapping("/news/delete")
    public String deleteNews(Model model) {
        model.addAttribute("news", new News());
        return "deleteNews";
    }

    @PostMapping("/news/delete")
    public String deleteNews(@RequestParam("newsId") Long newsId) {
        if (newsRepository.existsById(newsId)) {
            newsRepository.deleteById(newsId);
            return "redirect:/news";
        } else {
            throw new IllegalArgumentException("Новость с ID " + newsId + " не найдена");
        }
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
        news.setId(id);
        newsRepository.save(news);
        return "redirect:/news";
    }
}

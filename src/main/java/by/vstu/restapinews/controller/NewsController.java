package by.vstu.restapinews.controller;

import by.vstu.restapinews.repository.NewsRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import by.vstu.restapinews.model.News;

@Controller
@RequiredArgsConstructor
public class NewsController {
    private final NewsRepository newsRepository;

    @GetMapping("/news/{id}")
    public String getNews(@PathVariable("id") Long id, Model model) {
        News news = newsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid news Id:" + id));
        model.addAttribute("news", news);
        return "news";
    }

    @GetMapping("/news/wordcount/{id}")
    public @ResponseBody Long countWordsInNews(@PathVariable("id") Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid news Id:" + id));
        if (news.getContent() != null) {
            return (long) news.getContent().split("\\s+").length;
        } else {
            return 0L;
        }
    }

    @GetMapping("/news/wordcount")
    public String selectWordcountNewsID(Model model) {
        model.addAttribute("news", new News());
        return "selectWordcountNewsID";
    }

    @GetMapping("/news/displayWordcount/{id}")
    public String wordcountNewsID(@PathVariable("id") Long id, Model model) {
        if (newsRepository.existsById(id)) {
            News news = newsRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid news Id:" + id));
            if (news.getContent() != null) {
                Long wordCount = (long) news.getContent().split("\\s+").length;
                model.addAttribute("news", news);
                model.addAttribute("wordCount", wordCount);
            } else {
                model.addAttribute("news", news);
                model.addAttribute("wordCount", 0L);
            }
            return "wordcountNewsID";
        } else {
            model.addAttribute("error", "Новость с ID " + id + " не найдена");
            return "selectWordcountNewsID";
        }
    }

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
    public String createNews(@ModelAttribute @Valid News news, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || news.getPublishDate() == null) {
            return "createNews";
        }
        newsRepository.save(news);
        return "redirect:/news";
    }

    @GetMapping("/news/delete")
    public String deleteNews(Model model) {
        model.addAttribute("news", new News());
        return "deleteNews";
    }

    @PostMapping("/news/delete")
    public String deleteNews(@RequestParam("newsId") Long newsId, Model model) {
        if (newsRepository.existsById(newsId)) {
            News news = newsRepository.findById(newsId).get();
            if (news.getNewsCategories().isEmpty()) {
                newsRepository.deleteById(newsId);
                return "redirect:/news";
            } else {
                model.addAttribute("error", "Перед удалением новости удалите связь данной новости с категорией");
                return "deleteNews";
            }
        } else {
            model.addAttribute("error", "Новость с ID " + newsId + " не найдена");
            return "deleteNews";
        }
    }

    @GetMapping("/news/search")
    public String searchNewsID(Model model) {
        return "searchNewsID";
    }

    @GetMapping("/news/search/{id}")
    public String searchNewsID(@PathVariable("id") Long newsId, Model model) {
        if (newsRepository.existsById(newsId)) {
            News news = newsRepository.findById(newsId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid news Id:" + newsId));
            model.addAttribute("newsList", news);
        } else {
            model.addAttribute("error", "Новость с ID " + newsId + " не найдена");
            return "searchNewsID";
        }
        return "news";
    }

    @GetMapping("/news/update")
    public String selectUpdateNewsID(Model model) {
        model.addAttribute("news", new News());
        return "selectUpdateNewsID";
    }

    @GetMapping("/news/update/{id}")
    public String updateNewsID(@PathVariable("id") Long id, Model model) {
        News news = newsRepository.findById(id).orElse(null);
        if (news == null) {
            model.addAttribute("error", "Новость с ID " + id + " не найдена");
            return "selectUpdateNewsID";
        }
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
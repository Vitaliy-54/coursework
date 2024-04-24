package by.vstu.restapinews.controller;

import by.vstu.restapinews.repository.NewsCategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final NewsCategoriesRepository newsCategoriesRepository;
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("newsCategoriesList", newsCategoriesRepository.findAll());
        return "home";
    }
}

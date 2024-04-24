package by.vstu.restapinews.controller;

import by.vstu.restapinews.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import by.vstu.restapinews.model.Categories;

@Controller
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoriesRepository categoriesRepository;
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

    @GetMapping("/categories/delete")
    public String deleteCategories(Model model) {
        model.addAttribute("categories", new Categories());
        return "deleteCategories";
    }

    @PostMapping("/categories/delete")
    public String deleteCategories(@RequestParam("categoriesId") Long categoriesId) {
        if (categoriesRepository.existsById(categoriesId)) {
            categoriesRepository.deleteById(categoriesId);
            return "redirect:/categories";
        } else {
            throw new IllegalArgumentException("Категория с ID " + categoriesId + " не найдена");
        }
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
}

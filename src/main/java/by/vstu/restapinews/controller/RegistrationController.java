package by.vstu.restapinews.controller;

import by.vstu.restapinews.model.User;
import by.vstu.restapinews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userRoleService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("isAdmin", userRoleService.isAdmin());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        if (userRoleService.findByUsername(username) != null) {
            model.addAttribute("error", "Логин уже занят. Пожалуйста, введите другой логин.");
            return "registration";
        }
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole("ROLE_USER");
            userRoleService.saveUser(user);
            return "registration-success";
        }
}
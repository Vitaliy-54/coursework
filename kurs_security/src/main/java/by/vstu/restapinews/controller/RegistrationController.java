package by.vstu.restapinews.controller;

import by.vstu.restapinews.model.User;
import by.vstu.restapinews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String saveUser(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword, @RequestParam("role") String role, Model model) {
        User userFromDb = userService.findByUsername(username);
        if (userFromDb != null) {
            model.addAttribute("message", "Пользователь с таким именем уже существует");
            return "registration";
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("message", "Пароли не совпадают");
            return "registration";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRole(role);
        userService.saveUser(user);
        return "redirect:/login";
    }
}



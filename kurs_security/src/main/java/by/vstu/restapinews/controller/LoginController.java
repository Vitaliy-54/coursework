package by.vstu.restapinews.controller;

import by.vstu.restapinews.model.User;
import by.vstu.restapinews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    String login() {
        return "login";
    }
}

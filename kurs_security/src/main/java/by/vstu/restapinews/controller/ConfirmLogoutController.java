package by.vstu.restapinews.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConfirmLogoutController {

    @GetMapping("/confirm-logout")
    public String confirmLogout() {
        return "confirm-logout";
    }
}

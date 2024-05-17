package by.vstu.restapinews.controller;

import by.vstu.restapinews.model.User;
import by.vstu.restapinews.repository.UserRepository;
import by.vstu.restapinews.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ClientController {
    private final UserRepository userRepository;

        @Autowired
        private UserService userRoleService;

        @GetMapping("/client")
        public String client(Model model) {
        model.addAttribute("userList", userRepository.findAll());
        return "client";
        }

        @GetMapping("/client/create")
        public String createClient(Model model) {
            model.addAttribute("isAdmin", userRoleService.isAdmin());
            return "createClient";
        }

        @PostMapping("/client/create")
        public String addClient(@RequestParam("username") String username, @RequestParam("password") String password, String role, Model model) {
            if (userRoleService.findByUsername(username) != null) {
                model.addAttribute("error", "Логин уже занят. Пожалуйста, введите другой логин.");
                return "createClient";
            }
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);
            userRoleService.saveUser(user);
            return "createClient-success";
        }

    @GetMapping("/client/delete")
    public String deleteClientForm() {
        return "deleteClient";
    }

    @PostMapping("/client/delete")
    public String deleteClient(@RequestParam("userId") Long userId, Model model) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            model.addAttribute("error", "Пользователь с ID " + userId + " не найден");
        }
        return "redirect:/client";
    }
}

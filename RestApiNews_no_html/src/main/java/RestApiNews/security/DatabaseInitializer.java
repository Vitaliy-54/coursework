package RestApiNews.security;

import RestApiNews.entity.User;
import RestApiNews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DatabaseInitializer {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            if (userRepository.findByUsername("user") == null){
                User user = new User();
                user.setUsername("user");
                user.setPassword("1111");
                user.setRole("ROLE_USER");
                userService.saveUser(user);
            }

            if (userRepository.findByUsername("admin") == null){
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin");
                admin.setRole("ROLE_ADMIN");
                userService.saveUser(admin);
            }
        };
    }
}
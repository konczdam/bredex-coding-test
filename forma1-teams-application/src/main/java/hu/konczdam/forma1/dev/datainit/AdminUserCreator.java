package hu.konczdam.forma1.dev.datainit;

import hu.konczdam.forma1.dto.UserCreationDto;
import hu.konczdam.forma1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AdminUserCreator implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if (userService.getUserByUsername("admin") == null) {
            userService.registerUser(new UserCreationDto(
                   "admin",
                   "f1test2018",
                    Set.of("admin", "user")
            ));
        }
    }
}

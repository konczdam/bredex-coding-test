package hu.konczdam.forma1.service;

import hu.konczdam.forma1.dto.UserCreationDto;
import hu.konczdam.forma1.model.User;
import hu.konczdam.forma1.repository.RoleRepository;
import hu.konczdam.forma1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User registerUser(UserCreationDto userCreationDto) {
        if (getUserByUsername(userCreationDto.username()) != null) {
            throw new IllegalArgumentException(
                    String.format("A user with the username '%s' alredy exists!", userCreationDto.username())
            );
        }
        return userRepository.save(
                new User(
                        userCreationDto.username(),
                        BCrypt.hashpw(userCreationDto.password(), BCrypt.gensalt()),
                        userCreationDto.roles().stream().map(roleRepository::findByName).collect(Collectors.toSet())
                )
        );
    }

}

package hu.konczdam.forma1.dto;

import java.util.Set;

public record UserCreationDto(
        String username,
        String password,
        Set<String> roles
) {
}

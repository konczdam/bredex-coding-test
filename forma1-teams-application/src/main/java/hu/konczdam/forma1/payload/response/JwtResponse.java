package hu.konczdam.forma1.payload.response;

import java.util.Set;

public record JwtResponse(
        String token,
        String type,
        String username,
        Set<String> roles
) {
}

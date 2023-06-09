package hu.konczdam.forma1.controller;

import hu.konczdam.forma1.dto.LoginDto;
import hu.konczdam.forma1.payload.response.JwtResponse;
import hu.konczdam.forma1.security.jwt.JwtUtils;
import hu.konczdam.forma1.security.services.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(
            @RequestBody LoginDto loginDto
    ) {
        final var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final var jwt = jwtUtils.generateJwtToken(authentication);
        final var userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final var roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        return ResponseEntity.ok(
                new JwtResponse(
                        jwt,
                        "Bearer",
                        userDetails.getUsername(),
                        roles
                )
        );
    }
}

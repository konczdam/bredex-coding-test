package hu.konczdam.forma1.security;

import hu.konczdam.forma1.security.jwt.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;


@Component
@RequiredArgsConstructor
public class FilterChainFactory {
    private final AuthenticationManager authenticationManager;

    private final AuthTokenFilter authTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(httpSecuritySessionManagementConfigurer ->
                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http
                .csrf((protection) -> protection
                        .ignoringRequestMatchers(toH2Console())
                        .disable()

                )
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/auth/signin").permitAll()
                        .requestMatchers(toH2Console()).permitAll()
                        .requestMatchers(HttpMethod.GET, "/teams", "/teams/**").permitAll()
                        .anyRequest().authenticated()
                )

                .authenticationManager(authenticationManager);

        http.headers(httpSecurityHeadersConfigurer ->
                httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
        );
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

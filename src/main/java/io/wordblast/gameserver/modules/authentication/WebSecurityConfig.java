package io.wordblast.gameserver.modules.authentication;

import java.util.List;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

/**
 * The authentication configuration class.
 */
@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {
    /**
     * Configures the http server.
     * 
     * @param http the http server to configure.
     * @return the http server's web filters.
     */
    @Bean
    public SecurityWebFilterChain configureSecurityFilterChain(ServerHttpSecurity http) {
        // TODO: Temporarily disable. Technically a security risk.
        http.csrf().disable();

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfig.setAllowedMethods(List.of("GET", "POST"));

        UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        http.cors()
            .configurationSource(source)
            .and()
            .authorizeExchange()
            .anyExchange()
            .permitAll()
            .and()
            .formLogin()
            .loginPage("/api/user/login")
            .authenticationSuccessHandler((exchange, authentication) -> {
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                UUID userUid = userDetails.getUid();

                byte[] data = String.format("{\"userUid\": \"%s\"}", userUid).getBytes();
                DataBuffer buffer =
                    exchange.getExchange().getResponse().bufferFactory().wrap(data);

                ServerHttpResponse response = exchange.getExchange().getResponse();

                response.setStatusCode(HttpStatus.OK);

                return response.writeWith(Mono.just(buffer));
            })
            .authenticationFailureHandler((exchange, exception) -> {
                exchange.getExchange().getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                return Mono.empty();
            })
            .and()
            .logout()
            .logoutUrl("/api/user/logout")
            .logoutSuccessHandler((exchange, authentication) -> {
                exchange.getExchange().getResponse().setStatusCode(HttpStatus.OK);
                return Mono.empty();
            });
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

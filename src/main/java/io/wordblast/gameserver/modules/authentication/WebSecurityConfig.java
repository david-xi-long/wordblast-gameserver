package io.wordblast.gameserver.modules.authentication;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
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
            .authenticationSuccessHandler((exchange, exception) -> {
                exchange.getExchange().getResponse().setStatusCode(HttpStatus.OK);
                return Mono.empty();
            })
            .authenticationFailureHandler((exchange, exception) -> {
                exchange.getExchange().getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                return Mono.empty();
            })
            .and()
            .logout()
            .logoutUrl("/api/user/logout")
            .logoutSuccessHandler((exchange, exception) -> {
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

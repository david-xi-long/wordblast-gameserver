package io.wordblast.gameserver.modules.authentication;

import java.net.URI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
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

        RedirectServerAuthenticationSuccessHandler authSuccessHandler =
            new RedirectServerAuthenticationSuccessHandler("http://localhost:3000/");
        RedirectServerLogoutSuccessHandler logoutSuccessHandler =
            new RedirectServerLogoutSuccessHandler();
        logoutSuccessHandler.setLogoutSuccessUrl(URI.create("http://localhost:3000/"));

        http.authorizeExchange()
            .anyExchange()
            .permitAll()
            .and()
            .formLogin()
            .loginPage("/api/user/login")
            .authenticationSuccessHandler(authSuccessHandler)
            .authenticationFailureHandler((exchange, exception) -> {
                exchange.getExchange().getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                return Mono.empty();
            })
            .and()
            .logout()
            .logoutUrl("/api/user/logout")
            .logoutSuccessHandler(logoutSuccessHandler);
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

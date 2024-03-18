package com.example.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AuthConfig {

    private final AuthFailureHandler failureHandler;

    private final AuthSuccessHandler successHandler;

    @Autowired
    public AuthConfig(AuthFailureHandler failureHandler, AuthSuccessHandler authSuccessHandler) {
        this.failureHandler = failureHandler;
        this.successHandler = authSuccessHandler;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterSecurity(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers(new AntPathRequestMatcher("/login**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/secret**")).hasAuthority("USER")
                                .requestMatchers(new AntPathRequestMatcher("/secret/**")).hasAuthority("USER")
                                .requestMatchers(new AntPathRequestMatcher("/about")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/getAllBlockedUser")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/admin")).hasAuthority("USER")
                                .requestMatchers(new AntPathRequestMatcher("/info")).hasAuthority("USER")
                                .anyRequest().authenticated()
                ).formLogin(
                        form -> form
                                .loginPage("/login").permitAll()
                                .usernameParameter("email")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/")
                                .successHandler(successHandler)
                                .failureHandler(failureHandler)
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }
}

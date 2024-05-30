package com.zvaryyka.motelwebapplication.config;

import com.zvaryyka.motelwebapplication.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/owner").hasAuthority("ROLE_OWNER")
                        .requestMatchers("/admin").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/guest").hasAuthority("ROLE_USER")
                        .requestMatchers("/stuff").hasAuthority("ROLE_STUFF")
                        .requestMatchers("/super-user").hasAuthority("ROLE_SUPERUSER")
                        .requestMatchers("/index/**", "/registration", "/resources/static/css/**",
                                "/style.css", "/css/style.css", "/img/**", "/statistics", "/graphic",
                                "/totalRevenue", "/totalBookings").permitAll()

                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/process_login")
                        .successHandler((request, response, authentication) -> {
                            // Получаем роли пользователя
                            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

                            // Перенаправляем на страницу в зависимости от роли
                            if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_OWNER"))) {
                                response.sendRedirect("/owner");
                            } else if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
                                response.sendRedirect("/admin");
                            } else if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"))) {
                                response.sendRedirect("/guest");
                            } else if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_STUFF"))) {
                                response.sendRedirect("/stuff");
                            } else if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_SUPERUSER"))) {
                                response.sendRedirect("/super-user");
                            } else {
                                // Если у пользователя нет определенной роли, перенаправляем на общую страницу
                                response.sendRedirect("/index");
                            }
                        })
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/index")
                        .permitAll()
                );
        return http.build();
    }
    // Настраиваем аутентификацию

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

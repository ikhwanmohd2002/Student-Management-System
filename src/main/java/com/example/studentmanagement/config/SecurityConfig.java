package com.example.studentmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.studentmanagement.services.AdminService;

@Configuration
public class SecurityConfig {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(adminService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // http
        //     .csrf(csrf -> csrf.disable()) // Disable CSRF protection for testing
        //     .authorizeHttpRequests(auth -> auth
        //         .anyRequest().permitAll() // Allow access to all endpoints without authentication
        //     )
        //     .formLogin(form -> form.disable()) // Disable the login form
        //     .logout(logout -> logout.disable());
        http
        .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register", "/css/**", "/js/**").permitAll() // Allow public access
                .requestMatchers("/student/register").hasRole("ADMIN") // Only admins can access this
            .requestMatchers("/student/**").hasRole("STUDENT") // Only students can access this
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // Custom login page
                .defaultSuccessUrl("/home", true) // Redirect after login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login") // Redirect after logout
                .permitAll()
            );

        return http.build();
    }
}

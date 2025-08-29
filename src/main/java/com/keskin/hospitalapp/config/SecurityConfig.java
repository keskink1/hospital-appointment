package com.keskin.hospitalapp.config;

import com.keskin.hospitalapp.entities.Role;
import com.keskin.hospitalapp.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        var provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(c -> c
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(c -> c
                        // All access / no auth
                        .requestMatchers(HttpMethod.POST, "/users/register/patient").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/register/doctor").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/refresh").permitAll()
                        .requestMatchers(HttpMethod.GET, "/doctors", "/doctors/{id}").permitAll()

                        // Only admin
                        .requestMatchers(HttpMethod.GET, "/doctors/admin/getAll").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/doctors/updateDoctor/{id}").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/doctors/deleteDoctor/{id}").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH, "/doctors/{id}/change-password").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/doctors/{doctorId}/patients").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/doctors/{doctorId}/patients/{patientId}").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/doctors/{doctorId}/patients/{patientId}").hasRole(Role.ADMIN.name())
                        .requestMatchers("/patients/**").hasRole(Role.ADMIN.name())

                        // Authenticated
                        .requestMatchers("/auth/me").authenticated()
                        .requestMatchers(HttpMethod.GET, "/patients/{id}").authenticated()

                        .anyRequest().denyAll()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(c -> {
                            c.authenticationEntryPoint(
                                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                            c.accessDeniedHandler((request, response, accessDeniedException) -> response.setStatus(HttpStatus.FORBIDDEN.value()));
                        }
                );
        return http.build();
    }
}
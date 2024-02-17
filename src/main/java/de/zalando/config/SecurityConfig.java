package de.zalando.config;

import de.zalando.filter.JwtAuthenticationFilter;
import de.zalando.model.entities.Role;
import de.zalando.model.entities.UserPermissions;
import de.zalando.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final UserService userService;

  @Autowired
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .exceptionHandling()
        .accessDeniedHandler(new CustomAccessDeniedHandler())
        .and()
        .authorizeHttpRequests(auth -> {
              auth
                  .requestMatchers("/*/register", "/*/error", "/*/login")
                  .permitAll();
              auth
                  .requestMatchers(HttpMethod.GET, "/products", "/products/*")
                  .permitAll();
              auth
                  .requestMatchers(HttpMethod.POST, "/product")
                  .hasRole(Role.ADMIN.name())

                  .requestMatchers(HttpMethod.PUT, "/product/*")
                  .hasRole(Role.ADMIN.name())

                  .requestMatchers(HttpMethod.DELETE, "/product/*")
                  .hasRole(Role.ADMIN.name());
              auth
                  .requestMatchers(HttpMethod.GET, "/cart")
                  .hasRole(Role.CUSTOMER.name())

                  .requestMatchers(HttpMethod.POST, "/cart")
                  .hasRole(Role.CUSTOMER.name())

                  .requestMatchers(HttpMethod.PUT, "/cart/*")
                  .hasRole(Role.CUSTOMER.name())

                  .requestMatchers(HttpMethod.DELETE, "/cart/*")
                  .hasRole(Role.CUSTOMER.name());
              auth
                  .anyRequest()
                  .authenticated();
            }
        )
        .csrf(CsrfConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}

package com.nikhilkarn.authwrapper.config;

import com.nikhilkarn.authwrapper.model.PublicPaths;
import com.nikhilkarn.authwrapper.security.ApiKeyAuthFilter;
import com.nikhilkarn.authwrapper.security.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security configuration to allow public routes and protect all others with API key + token filters.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           ApiKeyAuthFilter apiKeyAuthFilter,
                                           TokenFilter tokenFilter) throws Exception {

        http.csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PublicPaths.asArray()).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(tokenFilter, ApiKeyAuthFilter.class);

        return http.build();
    }
}

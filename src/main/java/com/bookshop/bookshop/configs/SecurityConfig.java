package com.bookshop.bookshop.configs;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@ComponentScan(basePackages = 
{
    "com.bookshop.bookshop"
})
public class SecurityConfig 
{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        //настройска csrf
        http.csrf(csrf -> csrf.disable());
        
        // насройка аутентификации пользователей 
        http.authorizeHttpRequests(req ->
            req.anyRequest().permitAll());
        
        // передаем настройки корсов
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    // настройки cors
    CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", configuration);

        return configurationSource;
    }
}

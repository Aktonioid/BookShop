package com.bookshop.bookshop.configs;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.bookshop.bookshop.core.coreServices.IUserService;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@ComponentScan(basePackages = 
{
    "com.bookshop.bookshop"
})
public class SecurityConfig 
{
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private IUserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        //настройска csrf
        http.csrf(csrf -> csrf.disable());
        
        // насройка аутентификации пользователей 
        http.authorizeHttpRequests(req ->req
            .requestMatchers("/test/secured").hasAnyRole("USER","ADMIN") 
            .requestMatchers("/admin").hasRole("ADMIN")// роут админ панели
            .requestMatchers("/crate").hasAnyRole("USER","ADMIN") // корзина
            .requestMatchers("/email").hasAnyRole("USER","ADMIN") // отправка сообщений
            .requestMatchers("/orders").hasAnyRole("USER","ADMIN") // заказы
            .requestMatchers("/users/").hasAnyRole("USER","ADMIN") // страница пользователя
            .requestMatchers("/users/logout").hasAnyRole("USER","ADMIN")// разлогинивание
            .requestMatchers("/users/delete").hasAnyRole("USER","ADMIN")// удаление пользователя только, если он вошел
            .requestMatchers("/users/update").hasAnyRole("USER","ADMIN")// обновление инфы пользователя
            .requestMatchers("/users/update/password").hasAnyRole("USER","ADMIN") // обновление пароля
            .anyRequest().permitAll()
            );
        
        http.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(AuthenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

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

    @Bean
    public PasswordEncoder PasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider AuthenticationProvider()
    {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.UserDetailsService());
        authProvider.setPasswordEncoder(PasswordEncoder());;

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}

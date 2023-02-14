package com.makalu.hrm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    //https://docs.spring.io/spring-security/reference/servlet/oauth2/client/index.html
    @Autowired
    private CustomUserDetailsService userDetailsService;

   /* private static final ClearSiteDataHeaderWriter.Directive[] SOURCE = {
                ClearSiteDataHeaderWriter.Directive.CACHE,
            ClearSiteDataHeaderWriter.Directive.COOKIES,
            ClearSiteDataHeaderWriter.Directive.STORAGE,
            ClearSiteDataHeaderWriter.Directive.EXECUTION_CONTEXTS
    };*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/img/**", "/css/**", "/js/**", "/auth/**", "/employee/api/**")
                .permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .userDetailsService(userDetailsService)
                .formLogin(login ->
                        login
                                .defaultSuccessUrl("/")
                                .failureHandler(
                                        (request, response, exception) ->
                                                response.sendRedirect("/auth/login?error=" + exception.getMessage())
                                )
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .loginPage("/auth/login")
                                .permitAll()
                )
                .logout(logout ->
                        logout.
                                logoutUrl("/auth/logout").
                                invalidateHttpSession(true).
                                clearAuthentication(true).
                                permitAll()
                );

        return http.build();
    }

   /* @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

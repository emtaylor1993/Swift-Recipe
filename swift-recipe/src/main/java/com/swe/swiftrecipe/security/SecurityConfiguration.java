/**
 * SWIFTRECIPE SECURITY CONFIGURATION CLASS
 * 
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class is responsible for configuring security settings for the SwiftRecipe
 *    Web Application. It defines authentication and authorization rules, login/logout
 *    settubgsm abd security filters.
 * 
 * @packages
 *    Spring Framework Beans Factory Annotation (Autowired)
 *    Spring Framework Context Annotation (Bean, Configuration)
 *    Spring Framework Security Configuration Annotation Web Builders (HttpSecurity)
 *    Spring Framework Security Configuration Annotation Web Configuration (EnableWebSecurity)
 *    Spring Framework Security Web (SecurityFilterChain)
 *    Spring Framework Security Web Utilities Matcher (AntPathRequestMatcher)
 */

package com.swe.swiftrecipe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Lombok annotations to register this class as a Configuration bean. This implies
 * that this class contains one or more bean definitions. It also enables Spring's
 * Web Security features. 
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    /**
     * Injection of CustomUserDetailsService Service to handle user authentication.
     */
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    /**
     * Configures the security settings for this web application.
     * 
     * @param http - HttpSecurity object to configure security settings.
     * @return SecurityFilterChain - Object representing the security filter chain.
     * @throws Exception - Handle exceptions that may occur during configuration.
     */
    @SuppressWarnings("unused")
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            // Disable CSRF protections for H2 Database Console access.
            .csrf(csrf -> csrf.ignoringAntMatchers("/h2/**").disable())

            // Define authorization rules for different endpoints.
            .authorizeRequests(auth -> auth
                .antMatchers("/h2/**").permitAll()
                .antMatchers("/login?error=true").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/signupUser").permitAll()
                .antMatchers("/reset").permitAll()
                .antMatchers("/resetPassword").permitAll()
                .antMatchers("/images/*.png", "/*.js", "/*.css").permitAll()
                .anyRequest().authenticated())

            // Configure Login page.
            .formLogin(form -> form.loginPage("/login").permitAll())

            // Sets the CustomUserDetailsService for authentication.
            .userDetailsService(customUserDetailsService)

            // Configure security headers to allow H2 Console access.
            .headers(headers -> headers.frameOptions().sameOrigin())

            // Configure Logout functionality.
            .logout(logout -> logout
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll())
            .build();
    }
}
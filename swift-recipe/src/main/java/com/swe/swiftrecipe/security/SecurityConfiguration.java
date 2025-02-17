/**
 * SWIFTRECIPE SECURITY CONFIGURATION CLASS
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the Security Configuration class.
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

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    /**
     * Configures the security settings for this web application
     * 
     * @param http - HttpSecurity object to configure security settings
     * @return SecurityFilterChain - Object representing the security filter chain
     * @throws Exception - Handle exceptions that may occur during configuration
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.ignoringAntMatchers("/h2/**").disable())
            .authorizeRequests(auth -> auth
                .antMatchers("/h2/**").permitAll()
                .antMatchers("/login?error=true").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/signupUser").permitAll()
                .antMatchers("/reset").permitAll()
                .antMatchers("/resetPassword").permitAll()
                .antMatchers("/images/*.png", "/*.js", "/*.css").permitAll()
                .anyRequest().authenticated())
            .formLogin(form -> form.loginPage("/login").permitAll())
            .userDetailsService(customUserDetailsService)
            .headers(headers -> headers.frameOptions().sameOrigin())
            .logout(logout -> logout
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll())
            .build();
    }
}
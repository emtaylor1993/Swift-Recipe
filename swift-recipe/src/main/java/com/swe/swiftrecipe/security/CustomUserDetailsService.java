/**
 * SWIFTRECIPE CUSTOM USER DETAILS SERVICE CLASS
 * 
 * @author Emmanuel Taylor
 * 
 * @description
 *    This service class is responsible for loading user-specific data during authentication.
 *    It implements Spring Security's {@link UserDetailsService} interface and retrieves
 *    user details from MySQL via the {@link UserRepository}. This class is also tasked with
 *    converting retrieved User entities into SecureUser objects, which makes them
 *    compatible with Spring Security's authentication system.
 * 
 * @packages
 *    Spring Framework Security Core User Details (UserDetails, UserDetailsService, UsernameNotFoundException)
 *    Spring Framework Stereotype (Service)
 *    SwiftRecipe Entity (SecurityUser)
 *    SwiftRecipe Repository (UserRepository)
 *    Lombok (AllArgsConstructor)
 */

package com.swe.swiftrecipe.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.swe.swiftrecipe.entity.SecurityUser;
import com.swe.swiftrecipe.repository.UserRepository;
import lombok.AllArgsConstructor;

/**
 * Lombok annotations to reduce Java boilerplate code for getters, setters, and
 * constructors. Registers the class as an Service Bean to signify the presence
 * of business logic.
 */
@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    UserRepository userRepository;
    
    /**
     * Loads user details by username during authentication.
     * 
     * @param username - The username of the user to be authenticated.
     * @return UserDetails - A {@link SecurityUser} object containing User authentication details.  
     * @throws UsernameNotFoundException - Throws if the user with the given username is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
            .findByUsername(username)
            .map(SecurityUser::new)
            .orElseThrow(() -> new UsernameNotFoundException("Not Found"));
    }
}
/**
 * SWIFTRECIPE CUSTOM USER DETAILS SERVICE CLASS
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the Custom User Details Service and the feature to
 *    load the current logged in User.
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

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    UserRepository userRepository;
    
    /**
     * Loads user details by username
     * 
     * @param username - The username of the user
     * @return UserDetails - An object containing the user details
     * @throws UsernameNotFoundException - For username not found exceptions
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
            .findByUsername(username)
            .map(SecurityUser::new)
            .orElseThrow(() -> new UsernameNotFoundException("Not Found"));
    }
}
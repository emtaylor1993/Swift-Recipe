/**
 * SWIFTRECIPE PASSWORD HASHER CLASS
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class contains functionality to encrypt user passwords to
 *    enhance the security of the Swift Recipe system.
 * 
 * @packages
 *    Spring Framework Context Annotation (Bean, Configuration)
 *    Spring Framework Security Crypto BCrypt (BCryptPasswordEncoder)
 *    Spring Framework Security Crypto Password (PasswordEncoder)
 *    SwiftRecipe Stereotype (Service)
 *    Swift Recipe Repository (UserRepository)
 *    Lombok (AllArgsConstructor)
 */

package com.swe.swiftrecipe.utilities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.swe.swiftrecipe.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@Service
public class PasswordHasher {

    // Injection of UserRepository to access data from users
    UserRepository userRepository;
    
    /**
     * Creates Bean for PasswordEncoder to for BCrypt hashing
     * 
     * @return BCryptPasswordEncoder
     */
	@Bean
	static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    /**
     * Hashes all user's passwords in H2 database incase of database leakage
     */
    public void hashPasswords() {
        userRepository.findAll().forEach(user -> {
            String encodedPassword = passwordEncoder().encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
        });
    }
}
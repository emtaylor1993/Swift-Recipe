/**
 * SWIFTRECIPE PASSWORD HASHER CLASS
 * 
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class provides functionality to securely encrypt User passwords
 *    using the BCrypt hashing algorithm. It enhances security by ensuring
 *    that stored passwords are not kept in plain text.
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

/**
 * Lombok annotations to reduce Java boilerplate code for getters, setters, and
 * constructors. Registers the class as an Service Bean to signify the presence
 * of business logic, as well as a Configuration Bean to signify the definition
 * of other Beans.
 */
@AllArgsConstructor
@Configuration
@Service
public class PasswordHasher {

    /**
     * Repository for accessing and managing recipe entities in MySQL.
     */
    UserRepository userRepository;
    
    /**
     * Creates a Singleton Bean for the {@link PasswordEncoder} using BCrypt.
     * BCrypt automatically generates a salt internally, making each password 
     * hash unique.
     * 
     * @return A {@link BCryptPasswordEncoder} instance.
     */
	@Bean
	static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    /**
     * Hashes and updates all stored User passwords in MySQL. This method iterates
     * through all Users, hashes their passwords using BCrypt, and saves the updated
     * password back to MySQL. This ensures passwords are securely stored even if a
     * MySQL leak occurs.
     */
    public void hashPasswords() {
        userRepository.findAll().forEach(user -> {
            String encodedPassword = passwordEncoder().encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
        });
    }
}
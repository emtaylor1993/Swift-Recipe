/**
 * SWIFTRECIPE USER SERVICE IMPLEMENTATION CLASS
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the service implementation for Users.
 * 
 * @packages
 *    Java Utilities (List, Optional)
 *    Spring Framework Stereotype (Service)
 *    Spring Framework Transaction Annotation (Transactional)
 *    SwiftRecipe Entity (User)
 *    SwiftRecipe Exception (UserNotFoundException)
 *    SwiftRecipe Repository (UserRepository)
 *    Lombok (AllArgsConstructor)
 */

package com.swe.swiftrecipe.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.swe.swiftrecipe.entity.User;
import com.swe.swiftrecipe.exception.UserNotFoundException;
import com.swe.swiftrecipe.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    /**
     * Retrieves a user based on the associated ID
     * 
     * @param id - The ID of the user
     * @return User - The user associated with the given ID
     * @throws UserNotFoundException - Handles exceptions where a user cannot be found
     */
    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return unwrapUser(user, id);
    }

    /**
     * Saves a user to the H2 database
     * 
     * @param user - The user to save
     * @return User - The saved user
     */
    @Override
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Deletes a user by its ID
     * 
     * @param id - The ID of the user to delete
     */
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Retrieves all users
     * 
     * @return List<User> - A list of all users
     */
    @Override
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    /**
     * Retrieves a user by username
     * 
     * @param username - The username of the user
     * @return User - The user with the given username
     */
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    /**
     * Unwraps an optional user entity
     * 
     * @param entity - The optional user entity
     * @param id - The ID of the entity
     * @return User - The unwrapped user entity
     * @throws UserNotFoundException - Handles exceptions when a user cannot be founc
     */
    static User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new UserNotFoundException(id);
    }
}
/**
 * SWIFTRECIPE USER SERVICE IMPLEMENTATION CLASS
 * 
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class provides the implementation of the {@link UserService} interface.
 *    It handles business logic related to User management, including retrieving,
 *    saving, and deleting Users in MySQL. * 
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

/**
 * Lombok annotations to reduce Java boilerplate code for getters, setters, and
 * constructors. Registers the class as an Service Bean to signify the presence
 * of business logic.
 */
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    /**
     * Repository for accessing and managing User entities in MySQL.
     */
    UserRepository userRepository;

    /**
     * Saves a new User or updates an existing User in MySQL.
     * 
     * @param id - The unique identifier for the User.
     * @return User - The {@link User} associated with the given ID.
     * @throws UserNotFoundException - Throws is no User is found with the given ID.
     */
    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return unwrapUser(user, id);
    }

    /**
     * Saves a new user or updates an existing User in MySQL. Uses
     * @Transactional to ensure data integrity during save operations.
     * 
     * @param user - The User entity to be saved.
     * @return User - The saved {@link User} entity.
     */
    @Override
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Deletes a User from MySQL based on its ID.
     * 
     * @param id - The unique identifier of the User to delete.
     */
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Retrieves all Users stored in MySQL.
     * 
     * @return List<User> - A list containing all registered Users.
     */
    @Override
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    /**
     * Retrieves a user by username.
     * 
     * @param username - The username of the User.
     * @return User - The User with the given username.
     */
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    /**
     * Helper method to unwrap an Optional User entity. If the User exists, it 
     * returns the User object. Otherwise, it throws a {@link UserNotFoundException}.
     * 
     * @param entity - The Optional containing a User entity.
     * @param id - The unique ID of the User.
     * @return User - The unwrapped {@link User} object.
     * @throws UserNotFoundException - Throws if no User is found using the given ID.
     */
    static User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new UserNotFoundException(id);
    }
}
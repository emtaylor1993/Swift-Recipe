/**
 * SWIFTRECIPE USER SERVICE CLASS

 * @author Emmanuel Taylor
 * 
 * @description
 *    This interface defines the layer for managing User entities.
 *    It provides methods for retrieving, saving, deleting, and
 *    listing Users. The service interface allows for separation of business
 *    logic from persistence logic, enesuring a clean architecture for
 *    easy-to-test components.
 * 
 * @packages
 *    Java Utilities (List)
 *    SwiftRecipe Entity (User)
 */

package com.swe.swiftrecipe.service;

import java.util.List;
import com.swe.swiftrecipe.entity.User;

public interface UserService {
    User getUser(Long userId);
    User saveUser(User user);
    void deleteUser(Long userId);
    List<User> getUsers();
    User getUserByUsername(String username);
}
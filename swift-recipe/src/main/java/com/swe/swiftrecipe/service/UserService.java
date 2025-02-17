/**
 * SWIFTRECIPE USER SERVICE CLASS
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the service interface to be implemented
 *    by the User service.
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
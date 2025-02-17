/**
 * SWIFTRECIPE USER REPOSITORY CLASS
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the repository to hold User objects
 *    that are loaded into the database.
 * 
 * @packages
 *    Java Utilities (Optional)
 *    Spring Framework Data Repository (CrudRepository)
 *    SwiftRecipe Entity (User)
 */

package com.swe.swiftrecipe.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.swe.swiftrecipe.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
/**
 * SWIFTRECIPE USER REPOSITORY CLASS
 * 
 * @author Emmanuel Taylor
 * 
 * @description
 *    This interface acts as the repository for managing User entities
 *    within MySQL. It extends Spring Data JPA's {@link CrudRepository}, providing
 *    CRUD (Create, Read, Update, Delete) operations without requiring the use of
 *    explicit SQL queries.
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
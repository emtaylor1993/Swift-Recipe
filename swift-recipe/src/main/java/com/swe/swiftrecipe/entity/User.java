/**
 * SWIFTMARKET USER ENTITY CLASS
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the User entity and it's attributes
 *    to be represented on the SwiftMarket's website.
 * 
 * @packages
 *    Java Utilities (Set)
 *    Java Extensions Persistence (CascadeType, Column, Entity, GeneratedValue, GenerationType, Id, Table)
 *    Java Extensions Validation Constraints (Email, NotBlank, Pattern)
 *    SwiftRecipe Custom Validation (UniqueEmail, UniqueUsername)
 *    Lombok (Getter, NoArgsConstructor, NonNull, RequiredArgsConstructor, Setter)
 */

package com.swe.swiftrecipe.entity;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import com.swe.swiftrecipe.validation.UniqueEmail;
import com.swe.swiftrecipe.validation.UniqueUsername;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a user
 */
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank(message = "First name cannot be blank")
    @NonNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @NonNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Please enter a valid email")
    @UniqueEmail 
    @NonNull
    @NotBlank(message = "Email address cannot be blank")
    @Column(name = "email", nullable = false)
    private String email;

    @UniqueUsername
    @NotBlank(message = "Username cannot be blank")
    @NonNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @NonNull
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<Recipe> recipes;

    /**
     * Retrieves the recipes associated with the user
     * @return
     */
    public Set<Recipe> getRecipes() {
        return this.recipes;
    }

    /**
     * Sets the recipes associated with the user
     * 
     * @param recipes - The recipes to be set
     */
    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }
}
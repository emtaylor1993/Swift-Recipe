/**
 * SWIFTMARKET USER ENTITY CLASS
 * 
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class is mapped to the "users" table in the MySQL database and contains
 *    different attributes to define a User, such as first name, last name, and other 
 *    related metadata.
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
 * Lombok annotations to reduce Java boilerplate code for getters, setters, and
 * constructors. Also defines the class as an entity to be stored in MySQL with
 * the table name "users".
 */
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    
    /**
     * The unique identifier for each User (PRIMARY KEY). This value is
     * auto-generated and will increment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    /**
     * Represents the first name of the User. This field cannot be blank.
     */
    @NotBlank(message = "First name cannot be blank")
    @NonNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * Represents the last name of the User. This field cannot be blank.
     */
    @NotBlank(message = "Last name cannot be blank")
    @NonNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * Represents the email address of the User. It follows the email regular expression
     * format and uses the custom @UniqueEmail validation used in /validation/UniqueEmail.java.
     * This field cannot be blank.
     */
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Please enter a valid email")
    @UniqueEmail 
    @NonNull
    @NotBlank(message = "Email address cannot be blank")
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * Represents the username of the User. It uses the custom @UniqueUsername validation 
     * used in /validation/UniqueUsername.java. This field cannot be blank.
     */
    @UniqueUsername
    @NotBlank(message = "Username cannot be blank")
    @NonNull
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * Represents the password for the User. This password will be encrypted using
     * BCrypt. This field cannot be blank.
     */
    @NotBlank(message = "Password cannot be blank")
    @NonNull
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Represents a Many-to-Many relationship with the Recipe entity. Any number of Users
     * can save any number of Recipes. This relationship is mapped to the "user_recipe"
     * table in MySQL.
     */
    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<Recipe> recipes;

    /**
     * Retrieves the recipes associated with the User.
     * @return A set containing the recipes linked to the User.
     */
    public Set<Recipe> getRecipes() {
        return this.recipes;
    }

    /**
     * Sets the recipes associated with the User.
     * 
     * @param recipes - The set of recipes to be assigned to the User.
     */
    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }
}
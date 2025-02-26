/**
 * SWIFTMARKET RECIPE ENTITY CLASS
 * 
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class is mapped to the "recipe" table in the MySQL database and contains
 *    different attributes to define a recipe, such as name, description, and other 
 *    related metadata.
 * 
 * @packages
 *    Java Utilities (Set)
 *    Java Extensions Persistence (CascadeType, Column, Entity, GeneratedValue, GenerationType, Id, JoinTable, JoinColumn, ManyToMany, Table)
 *    Java Extensions Validation Constraints (NotBlank)
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Lombok annotations to reduce Java boilerplate code for getters, setters, and
 * constructors. Also defines the class as an entity to be stored in MySQL with
 * the table name "recipe".
 */
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipe")
public class Recipe {
    
    /**
     * The unique identifier for each recipe (PRIMARY KEY). This value is
     * auto-generated and will increment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long recipeId;

    /**
     * Represents the name of the recipe. This field cannot be blank.
     */
    @NotBlank(message = "Recipe name cannot be blank")
    @NonNull
    @Column(name = "recipe_name", nullable = false)
    private String recipeName;

    /**
     * Represents the textual description of each recipe. This field 
     * cannot be blank.
     */   
    @NotBlank(message = "Description cannot be blank")
    @NonNull
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * Represents the textual list of ingredients required to prepare the recipe.
     * This field cannot be blank.
     */
    @NotBlank(message = "Ingredients cannot be blank")
    @NonNull
    @Column(name = "ingredients", nullable = false)
    private String ingredients;

    /**
     * Represents the the textual list of instructions for preparing the recipe. This
     * field is stored as a byte array due to the amount of data.
     */
    @NonNull
    @Column(name = "instructions", nullable = false)
    private byte[] instructions;

    /**
     * Represents the estimated preparation time in minutes for the recipe.
     */
    @NonNull
    @Column(name = "prep_time", nullable = false)
    private Integer prepTime;

    /**
     * Represents the estimated cooking time in minutes for the recipe.
     */
    @NonNull
    @Column(name = "cook_time", nullable = false)
    private Integer cookTime;

    /**
     * Represents the number of servinces each recipe is intended to provide.
     */
    @NonNull
    @Column(name = "servings", nullable = false)
    private Integer servings;

    /**
     * Represents the difficulty level of preparing the recipe (e.g., Easy, Medium
     * Hard). This field cannot be blank.
     */
    @NotBlank(message = "Difficulty cannot be blank")
    @NonNull
    @Column(name = "difficulty", nullable = false)
    private String difficulty;

    /**
     * Represents the type of cuisine the recipe belongs to (e.g., Italian, Chinese,
     * Indian). This field cannot be blank.
     */
    @NotBlank(message = "Cuisine cannot be blank")
    @NonNull
    @Column(name = "cuisine", nullable = false)
    private String cuisine;

    /**
     * Represents the estimated amount of calories per serving for the recipe.
     */
    @NonNull
    @Column(name = "calories", nullable = false)
    private Integer calories;

    /**
     * Represents the tags related to the recipe (e.g., Vegetarian, Gluten-Free,
     * High Protein). This field cannot be blank.
     */
    @NotBlank(message = "Tags cannot be blank")
    @NonNull
    @Column(name = "tags", nullable = false)
    private String tags;

    /**
     * Represents the URL or reference to an image for the prepared recipe.
     * This field cannot be blank.
     */
    @NotBlank(message = "Image cannot be blank")
    @NonNull
    @Column(name = "image", nullable = false)
    private String image;

    /**
     * Represents the average rating of the recipe, calculated from use reviews.
     */
    @NonNull
    @Column(name = "rating", nullable = false)
    private Double rating;

    /**
     * Represents the total number of reviews submitted for the recipe.
     */
    @NonNull
    @Column(name = "review_count", nullable = false)
    private Integer reviewCount;

    /**
     * Represents the type of meal this recipe is associated with (e.g., Breakfast,
     * Lunch, Dinner). This field cannot be blank.
     */
    @NotBlank(message = "Meal type cannot be blank")
    @NonNull
    @Column(name = "meal_type", nullable = false)
    private String mealType;

    /**
     * Represents a Many-to-Many relationship with the User entity. Any number of Users
     * can save any number of Recipes. This relationship is mapped to the "user_recipe"
     * table in MySQL.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_recipe",
        joinColumns = @JoinColumn(name = "recipe_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;
}
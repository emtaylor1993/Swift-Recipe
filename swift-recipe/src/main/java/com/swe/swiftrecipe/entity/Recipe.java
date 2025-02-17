/**
 * SWIFTMARKET RECIPE ENTITY CLASS
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the Recipe entity and it's attributes
 *    to be represented on the SwiftMarket's website.
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
 * Entity class representing a recipe
 */
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipe")
public class Recipe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long recipeId;

    @NotBlank(message = "Recipe name cannot be blank")
    @NonNull
    @Column(name = "recipe_name", nullable = false)
    private String recipeName;

    @NotBlank(message = "Description cannot be blank")
    @NonNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotBlank(message = "Ingredients cannot be blank")
    @NonNull
    @Column(name = "ingredients", nullable = false)
    private String ingredients;

    @NonNull
    @Column(name = "instructions", nullable = false)
    private byte[] instructions;

    @NonNull
    @Column(name = "prep_time", nullable = false)
    private Integer prepTime;

    @NonNull
    @Column(name = "cook_time", nullable = false)
    private Integer cookTime;

    @NonNull
    @Column(name = "servings", nullable = false)
    private Integer servings;

    @NotBlank(message = "Difficulty cannot be blank")
    @NonNull
    @Column(name = "difficulty", nullable = false)
    private String difficulty;

    @NotBlank(message = "Cuisine cannot be blank")
    @NonNull
    @Column(name = "cuisine", nullable = false)
    private String cuisine;

    @NonNull
    @Column(name = "calories", nullable = false)
    private Integer calories;

    @NotBlank(message = "Tags cannot be blank")
    @NonNull
    @Column(name = "tags", nullable = false)
    private String tags;

    @NotBlank(message = "Image cannot be blank")
    @NonNull
    @Column(name = "image", nullable = false)
    private String image;

    @NonNull
    @Column(name = "rating", nullable = false)
    private Double rating;

    @NonNull
    @Column(name = "review_count", nullable = false)
    private Integer reviewCount;

    @NotBlank(message = "Meal type cannot be blank")
    @NonNull
    @Column(name = "meal_type", nullable = false)
    private String mealType;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_recipe",
        joinColumns = @JoinColumn(name = "recipe_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;
}
/**
 * SWIFTRECIPE WEB CONTROLLER
 * 
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class serves as the controller for handling web requests for the frontend.
 *    It manages user authentication, recipe retrieval, saving functionality, and
 *    User interactions.
 * 
 * @packages
 *    Java IO (IOException)
 *    Java Security (Principal)
 *    Java Utilities (ArrayList, Arrays, Comparator, List)
 *    Java Extensions Validation (Valid)
 *    Spring Framework Beans Factory Annotation (Autowired)
 *    Spring Framework Security Crypto Password (PasswordEncoder)
 *    Spring Framework Stereotype (Controller)
 *    Spring Framework UI (Model)
 *    Spring Framework Validation (BindingResult)
 *    Spring Framework Web Bind Annotation (GetMapping, PostMapping, RequestParam)
 *    SwiftRecipe Entity (Recipe, User)
 *    SwiftRecipe Service (RecipeService, UserService)
 *    SwiftRecipe Utilities (StringSerializer)
 */

package com.swe.swiftrecipe.web;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.swe.swiftrecipe.entity.Recipe;
import com.swe.swiftrecipe.entity.User;
import com.swe.swiftrecipe.service.RecipeService;
import com.swe.swiftrecipe.service.UserService;
import com.swe.swiftrecipe.utilities.StringSerializer;

@Controller
public class WebController {

    @Autowired
    UserService userService;

    @Autowired
    RecipeService recipeService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    StringSerializer stringSerializer;
    
    /**
     * Displays the login page.
     * 
     * @return The login page view.
     */
    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    /**
     * Displays the signup page with optional user data.
     * 
     * @param model - The model object to store attributes.
     * @param userId - Optional user ID for editing existing User.
     * @return The signup page view.
     */
    @GetMapping("/signup")
    public String getSignup(Model model, @RequestParam(required = false) Long userId) {
        int index = getUserIndex(userId);
        model.addAttribute("user", index == -1000 ? new User() : userService.getUsers().get(index)); 
        return "signup";
    }

    /**
     * Displays the dashboard page with all available recipes.
     * 
     * @param model - The model object for storing recipes.
     * @return The dashboard page view.
     */
    @GetMapping("/")
    public String getDashboard(Model model) {
        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "dashboard";
    }

    /**
     * Displays the categories page.
     * 
     * @return The categories page view.
     */
    @GetMapping("/categories")
    public String getCategories() {
        return "categories";
    }

    /**
     * Displays the saved recipes page for a logged-in User.
     *  
     * @param model - The model object to store saved recipes.
     * @param principal - The logged-in User's principal.
     * @return The saved recipes page view or an empty page if no saved recipes.
     */
    @GetMapping("/saved")
    public String getSaved(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        List<Recipe> savedRecipes = new ArrayList<>(user.getRecipes());
        if (!savedRecipes.isEmpty()) {
            savedRecipes.sort(Comparator.comparing(Recipe::getRecipeName));
            model.addAttribute("savedRecipes", savedRecipes);
            return "saved";
        } else {
            return "emptySaved";
        }
    }

    /**
     * Displays the recipeinfo page.
     * 
     * @param model - The model object that stores the recipes information.
     * @param recipeId - The ID of the recipe to display.
     * @return The recipe information page view.
     * @throws ClassNotFoundException - Handles class not found exceptions.
     * @throws IOException - Handles IO exceptions.
     */
    @GetMapping("/recipeInfo")
    public String getRecipe(Model model, @RequestParam(required = true) Long recipeId) throws ClassNotFoundException, IOException {
        Recipe recipe = recipeService.getRecipe(recipeId);
        String deserializedString = stringSerializer.deserializeString((byte[]) recipe.getInstructions());
        List<String> instructions = Arrays.asList(deserializedString.split(";"));
        List<String> ingredients = Arrays.asList(recipe.getIngredients().split(","));
        model.addAttribute("recipe", recipe);
        model.addAttribute("instructions", instructions);
        model.addAttribute("ingredients", ingredients);
        return "recipeInfo";
    }

    /**
     * Displays the results page.
     * 
     * @param model - The model object to store query results.
     * @param query - The search query.
     * @return The search results page view.
     */
    @GetMapping("/results")
    @SuppressWarnings("ConvertToStringSwitch")
    public String getRecipeResults(Model model, @RequestParam(required = true) String query) {
        String formattedQuery = query.substring(0, 1).toUpperCase() + query.substring(1);
        String queryAttribute = formattedQuery;    
        List<Recipe> results = new ArrayList<>();
        List<Recipe> recipes = recipeService.getAllRecipes();
        for (int i = 0; i < recipes.size(); i++) {
            if (formattedQuery.equals("Easy") || formattedQuery.equals("Medium") || formattedQuery.equals("Hard")) {
                if (formattedQuery.equals("Easy")) {
                    queryAttribute = "Beginner";
                } else if (formattedQuery.equals("Medium")) {
                    queryAttribute = "Intermediate";
                } else if (formattedQuery.equals("Hard")) {
                    queryAttribute = "Expert";
                }
                if (recipes.get(i).getDifficulty().equals(formattedQuery)) {
                    results.add(recipes.get(i));
                }
            } else if (formattedQuery.contains("Dinner") || formattedQuery.contains("Lunch") || formattedQuery.contains("Snack") ||
                formattedQuery.contains("Dessert") || formattedQuery.contains("Side") || formattedQuery.contains("Appetizer") ||
                formattedQuery.contains("Beverage")) {
                if (formattedQuery.equals("Snack")) {
                    queryAttribute = "Snacks";
                } else if (formattedQuery.equals("Dessert")) {
                    queryAttribute = "Desserts";
                } else if (formattedQuery.equals("Beverage")) {
                    queryAttribute = "Beverages";
                } else if (formattedQuery.equals("Side")) {
                    queryAttribute = "Side Dishes";
                } else if (formattedQuery.equals("Appetizer")) {
                    queryAttribute = "Appetizers";
                }
                if (recipes.get(i).getMealType().contains(formattedQuery)) {
                    results.add(recipes.get(i));
                }
            } else if (formattedQuery.equals("Hispanic")) {
                queryAttribute = "American and Hispanic";
                if (recipes.get(i).getCuisine().equals("Spanish")) {
                    results.add(recipes.get(i));
                } else if (recipes.get(i).getCuisine().equals("Cocktail")) {
                    results.add(recipes.get(i));
                } else if (recipes.get(i).getCuisine().equals("Smoothie")) {
                    results.add(recipes.get(i));
                } else if (recipes.get(i).getCuisine().equals("Hawaiian")) {
                    results.add(recipes.get(i));
                } else if (recipes.get(i).getCuisine().equals("Mexican")) {
                    results.add(recipes.get(i));
                } else if (recipes.get(i).getCuisine().equals("Brazilian")) {
                    results.add(recipes.get(i));
                }
            } else if (formattedQuery.equals("European")) {
                if (recipes.get(i).getCuisine().equals("Italian")) {
                    results.add(recipes.get(i));
                } else if (recipes.get(i).getCuisine().equals("Greek")) {
                    results.add(recipes.get(i));
                } else if (recipes.get(i).getCuisine().equals("Russian")) {
                    results.add(recipes.get(i));
                }
            } else if (formattedQuery.equals("Asian")) {
                if (recipes.get(i).getCuisine().equals("Asian")) {
                    results.add(recipes.get(i));
                } else if (recipes.get(i).getCuisine().equals("Japanese")) {
                    results.add(recipes.get(i));
                } else if (recipes.get(i).getCuisine().equals("Korean")) {
                    results.add(recipes.get(i));
                } else if (recipes.get(i).getCuisine().equals("Thai")) {
                    results.add(recipes.get(i));
                } else if (recipes.get(i).getCuisine().equals("Vietnamese")) {
                    results.add(recipes.get(i));
                }
            } else if (formattedQuery.equals("Middleeast")) {
                queryAttribute = "Middle Eastern and South Asian";
                if (recipes.get(i).getCuisine().equals("Indian")) {
                    results.add(recipes.get(i));
                } else if (recipes.get(i).getCuisine().equals("Pakistani")) {
                    results.add(recipes.get(i));
                } else if (recipes.get(i).getCuisine().equals("Turkish")) {
                    results.add(recipes.get(i));
                } else if (recipes.get(i).getCuisine().equals("Lebanese")) {
                    results.add(recipes.get(i));
                } else if (recipes.get(i).getCuisine().equals("Moroccan")) {
                    results.add(recipes.get(i));
                } else if (recipes.get(i).getCuisine().equals("Mediterranean")) {
                    results.add(recipes.get(i));
                }
            } else {
                if (recipes.get(i).getRecipeName().contains(formattedQuery)) {
                    if (!results.contains(recipes.get(i))) {
                        results.add(recipes.get(i));
                    }
                }
                if (recipes.get(i).getIngredients().contains(formattedQuery)) {
                    if (!results.contains(recipes.get(i))) {
                        results.add(recipes.get(i));
                    }
                }
                if (recipes.get(i).getDescription().contains(formattedQuery) || recipes.get(i).getDescription().contains(query)) {
                    if (!results.contains(recipes.get(i))) {
                        results.add(recipes.get(i));
                    }
                }
            }
        }

        model.addAttribute("query", queryAttribute);

        if (!results.isEmpty()) {
            model.addAttribute("results", results);
            return "results";
        } else {
            return "emptyResults";
        }
    }

    /**
     * Handles POST requset to sign a user up.
     * 
     * @param user - The user object being signed up.
     * @param result - The binding result object.
     * @return The signup page view or redirect to login.
     */
    @PostMapping("/signupUser")
    public String signupUser(@Valid User user, BindingResult result) {
        if (result.hasErrors()) return "signup";
        int index = getUserIndex(user.getUserId());
        if (index == -1000) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
        } else {
            userService.getUsers().set(index, user);
        }
        return "redirect:/login";
    }

    /**
     * Handles POST request to save a recipe.
     * 
     * @param recipeId - The ID of the recipe to save.
     * @param principal - The principal object representing the logged in user.
     * @return Redirect to saved recipes page.
     */
    @PostMapping("/saveRecipe")
    public String saveRecipe(@RequestParam("recipeId") Long recipeId, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        Recipe recipe = recipeService.getRecipe(recipeId);
        if (user != null && recipe != null) {
            user.getRecipes().add(recipe);
            recipe.getUsers().add(user);        
            userService.saveUser(user);
        }
        return "redirect:/saved";
    }

    /**
     * Retrieves the index of the user in the list based on the user ID.
     * 
     * @param userId - The ID of the user.
     * @return The index of the user in the list of -1000 if not found.
     */
    private int getUserIndex(Long userId) {
        for (int i = 0; i < userService.getUsers().size(); i++) {
            if (userService.getUsers().get(i).getUserId().equals(userId)) return i;
        }
        return -1000;
    }
}
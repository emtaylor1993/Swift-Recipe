/**
 * SWIFTRECIPE APPLICATION LOGIN STEP DEFINITIONS
 * 
 * @author Emmanuel Taylor
 * 
 * This file represents all of the login step definitions used within
 * the login feature file of the SwiftRecipe application.
 */

import { When, Then } from "@badeball/cypress-cucumber-preprocessor";

// This step definition specifies the action of navigating 
// to the username field and typing in the provided username.
When("User Navigates to Username Field and Types in {string}", (username) => {
    cy.get("input[id='username']")
    .should("be.visible")
    .focus()
    .type(username)
})

// This step definition specifies the action of navigating 
// to the password field and typing in the provided password.
Then("User Navigates to Password Field and Types in {string}", (password) => {
    cy.get("input[id='password']")
    .should("be.visible")
    .focus()
    .type(password)
})

// This step definition specifies the action of clicking the sign in button.
Then("User Clicks Sign In Button", function() {
    cy.get("body > div > form > input[type=submit]:nth-child(4)")
    .should("be.visible")
    .click()
})

// This step definition verifies that the user is redirected to the 
// SwiftRecipe home page after a successful login.
Then("User Sees the SwiftRecipe Home Page", function() {
    cy.url()
    .should("eq", "http://swift-recipe:8080/")
})

// This step definition verifies that the "Invalid Username or Password" 
// banner is displayed after a failed login attempt.
Then("User Sees the Invalid Username or Password Banner", function() {
    cy.get("body > div:nth-child(1) > div")
    .should("be.visible")
})
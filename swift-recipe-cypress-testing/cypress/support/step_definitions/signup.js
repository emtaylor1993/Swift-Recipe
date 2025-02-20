/**
 * SWIFTRECIPE APPLICATION SIGNUP STEP DEFINITIONS
 * 
 * @author Emmanuel Taylor
 * 
 * This file represents all of the signup step definitions used within
 * the signup feature file of the SwiftRecipe application.
 */

import { When, Then } from "@badeball/cypress-cucumber-preprocessor";

// This step definition specifies the action of clicking the signup link.
When("User Clicks the Signup Link", function() {
    cy.get("a[href='/signup'")
    .should("be.visible")
    .click()
})

// This step definition verifies the user is redirected to the signup page.
Then ("User Sees the SwiftRecipe Signup Page", function() {
    cy.url()
    .should("eq", "http://swift-recipe:8080/signup")
})

// This step definition specifies the action of navigating 
// to the first name field and typing in the provided first name.
Then("User Navigates to First Name Field and Types in {string}", (firstName) => {
    cy.get("input[id='firstName']")
    .should("be.visible")
    .focus()
    .type(firstName)
})

// This step definition specifies the action of navigating 
// to the last name field and typing in the provided last name.
Then("User Navigates to Last Name Field and Types in {string}", (lastName) => {
    cy.get("input[id='lastName']")
    .should("be.visible")
    .focus()
    .type(lastName)
})

// This step definition specifies the action of navigating 
// to the email field and typing in the provided email.
Then("User Navigates to Email Field and Types in {string}", (email) => {
    cy.get("input[id='email']")
    .should("be.visible")
    .focus()
    .type(email)
})

// This step definition specifies the action of navigating 
// to the username field and typing in the provided username.
Then("User Navigates to Signup Username Field and Types in {string}", (username) => {
    cy.get("input[id='username']")
    .should("be.visible")
    .focus()
    .type(username)
})

// This step definition specifies the action of navigating 
// to the password field and typing in the provided password.
Then("User Navigates to Signup Password Field and Types in {string}", (password) => {
    cy.get("input[id='password']")
    .should("be.visible")
    .focus()
    .type(password)
})

// This step definition specifies the action of clicking the create account button.
Then("User Clicks Create Account Button", function() {
    cy.get("body > div > form > input[type=submit]:nth-child(7)")
    .should("be.visible")
    .click()
})

// This step definition verifies the user is redirected to the login page.
Then("User Sees the SwiftRecipe Signup Home Page", function() {
    cy.url()
    .should("eq", "http://swift-recipe:8080/login")
})

// This step definition verifies that the validation error messages are visible.
Then("User Sees the Validation Error Message {string}", (errorMessage) => {
    cy.get("p[style='color: red;']")
    .should("be.visible")
    .contains(errorMessage)
})
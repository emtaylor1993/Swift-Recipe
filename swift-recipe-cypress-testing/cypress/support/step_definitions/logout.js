/**
 * SWIFTRECIPE APPLICATION LOGIN STEP DEFINITIONS
 * 
 * @author Emmanuel Taylor
 * 
 * This file represents all of the login step definitions used within
 * the login feature file of the SwiftRecipe application.
 */

import { Then } from "@badeball/cypress-cucumber-preprocessor";

Then("User Clicks Logout Button", function() {
    cy.get("a[href='/logout'")
    .should("be.visible")
    .click()
})

Then("User Sees the SwiftRecipe Login Page", function() {
    cy.url()
    .should("eq", "http://swift-recipe:8080/login?logout")
})

Then("User Sees Logout Message {string}", (logoutMessage) => {
    cy.get("body > div:nth-child(1) > div")
    .should("be.visible")
})
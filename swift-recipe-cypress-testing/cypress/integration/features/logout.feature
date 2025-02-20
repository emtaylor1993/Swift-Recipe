# SWIFTRECIPE APPLICATION LOGOUT FEATURE FILE
# 
# @author Emmanuel Taylor
#
# This feature file tests the logout functionality of the SwiftRecipe
# Application.

@logout
Feature: Logging Into SwiftRecipe Application

    # Background steps are executed before each scenario to test the initial context.
    Background:
        Given User Visits SwiftRecipe Login Page

    # Scenario for successful logout.
    @scenario_1.1.1
    Scenario: Attempting Logout of the SwiftRecipe Application
        When User Navigates to Username Field and Types in "etaylor5"
        Then User Navigates to Password Field and Types in "demo5"
        Then User Clicks Sign In Button
        Then User Sees the SwiftRecipe Home Page
        Then User Clicks Logout Button
        Then User Sees the SwiftRecipe Login Page
        And User Sees Logout Message "You have been logged out."
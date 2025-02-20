# SWIFTRECIPE APPLICATION LOGIN FEATURE FILE
# 
# @author Emmanuel Taylor
#
# This feature file tests the login functionality of the SwiftRecipe
# Application.

@login
Feature: Logging Into SwiftRecipe Application

    # Background steps are executed before each scenario to test the initial context.
    Background:
        Given User Visits SwiftRecipe Login Page

    # Scenario for successful login with correct credentials.
    @scenario_1.1.1 @successful-login
    Scenario: Attempting Login with Correct Credentials
        When User Navigates to Username Field and Types in "etaylor5"
        Then User Navigates to Password Field and Types in "demo5"
        Then User Clicks Sign In Button
        And User Sees the SwiftRecipe Home Page
        
    # Scenario for unsuccessful login with both incorrect username and password.
    @scenario_1.1.2 @unsuccessful-login
    Scenario: Attempting Login with Incorrect Username and Password
        When User Navigates to Username Field and Types in "etaylor4"
        Then User Navigates to Password Field and Types in "demo4"
        Then User Clicks Sign In Button
        And User Sees the Invalid Username or Password Banner

    # Scenario for unsuccessful login with incorrect username.
    @scenario_1.1.3 @unsuccessful-login-username
    Scenario: Attempting Login with Incorrect Username
        When User Navigates to Username Field and Types in "etaylor4"
        Then User Navigates to Password Field and Types in "demo5"
        Then User Clicks Sign In Button
        And User Sees the Invalid Username or Password Banner

    # Scenario for unsuccessful login with incorrect password.
    @scenario_1.1.4 @unsuccessful-login-password
    Scenario: Attempting Login with Incorrect Password
        When User Navigates to Username Field and Types in "etaylor5"
        Then User Navigates to Password Field and Types in "demo4"
        Then User Clicks Sign In Button
        And User Sees the Invalid Username or Password Banner  
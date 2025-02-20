# SWIFTRECIPE APPLICATION SIGNUP FEATURE FILE
# 
# @author Emmanuel Taylor
#
# This feature file tests the signup functionality of the SwiftRecipe
# Application.

@signup
Feature: Signing Up For SwiftRecipe Application

    # Background steps are executed before each scenario to test the initial context.
    Background:
        Given User Visits SwiftRecipe Login Page

    # Scenario for successful signup with valid information.
    @scenario_1.1.1 @successful-signup
    Scenario: Attempting to Sign up and Login as a New User Successfully
        When User Clicks the Signup Link
        Then User Sees the SwiftRecipe Signup Page
        Then User Navigates to First Name Field and Types in "LeBron"
        Then User Navigates to Last Name Field and Types in "James"
        Then User Navigates to Email Field and Types in "ljames6@students.towson.edu"
        Then User Navigates to Signup Username Field and Types in "ljames6"
        Then User Navigates to Signup Password Field and Types in "demo6"
        Then User Clicks Create Account Button
        And User Sees the SwiftRecipe Signup Home Page

    # Scenario for unsuccessful signup with blank first name.
    @scenario_1.1.2 @unsuccessful-signup-fname
    Scenario: Attempting to Sign up without a First Name
        When User Clicks the Signup Link
        Then User Sees the SwiftRecipe Signup Page
        Then User Navigates to Last Name Field and Types in "James"
        Then User Navigates to Email Field and Types in "ljames6@students.towson.edu"
        Then User Navigates to Signup Username Field and Types in "ljames6"
        Then User Navigates to Signup Password Field and Types in "demo6"
        Then User Clicks Create Account Button
        And User Sees the Validation Error Message "First name cannot be blank"

    # Scenario for unsuccessful signup with blank last name.
    @scenario_1.1.3 @unsuccessful-signup-lname
    Scenario: Attempting to Sign up without a Last Name
        When User Clicks the Signup Link
        Then User Sees the SwiftRecipe Signup Page
        Then User Navigates to First Name Field and Types in "LeBron"
        Then User Navigates to Email Field and Types in "ljames6@students.towson.edu"
        Then User Navigates to Signup Username Field and Types in "ljames6"
        Then User Navigates to Signup Password Field and Types in "demo6"
        Then User Clicks Create Account Button
        And User Sees the Validation Error Message "Last name cannot be blank"

    # Scenario for unsuccessful signup with blank email.
    @scenario_1.1.4 @unsuccessful-signup-email
    Scenario: Attempting to Sign up without an Email Address
        When User Clicks the Signup Link
        Then User Sees the SwiftRecipe Signup Page
        Then User Navigates to First Name Field and Types in "LeBron"
        Then User Navigates to Last Name Field and Types in "James"
        Then User Navigates to Signup Username Field and Types in "ljames6"
        Then User Navigates to Signup Password Field and Types in "demo6"
        Then User Clicks Create Account Button
        And User Sees the Validation Error Message "Email address cannot be blank"

    # Scenario for unsuccessful signup with blank username.
    @scenario_1.1.5 @unsuccessful-signup-username
    Scenario: Attempting to Sign up without a Username
        When User Clicks the Signup Link
        Then User Sees the SwiftRecipe Signup Page
        Then User Navigates to First Name Field and Types in "LeBron"
        Then User Navigates to Last Name Field and Types in "James"
        Then User Navigates to Email Field and Types in "ljames6@students.towson.edu"
        Then User Navigates to Signup Password Field and Types in "demo6"
        Then User Clicks Create Account Button
        And User Sees the Validation Error Message "Username cannot be blank"

    # Scenario for unsuccessful signup with blank password.
    @scenario_1.1.6 @unsuccessful-signup-password
    Scenario: Attempting to Sign up without a Password
        When User Clicks the Signup Link
        Then User Sees the SwiftRecipe Signup Page
        Then User Navigates to First Name Field and Types in "LeBron"
        Then User Navigates to Last Name Field and Types in "James"
        Then User Navigates to Email Field and Types in "ljames6@students.towson.edu"
        Then User Navigates to Signup Username Field and Types in "ljames6"
        Then User Clicks Create Account Button
        And User Sees the Validation Error Message "Password cannot be blank"
@categories
Feature: Category creation

As an authenticated user
I want to create custom categories
So that I can classify my financial movements

Background:
Given I am authenticated

Scenario: Successful category creation
When I create a category with name "Transporte"
Then the response status code is 200
And the category name is "Transporte"

Scenario: Category creation without name
Given I am authenticated
When I create a category without a name
Then the response status code is 400
And the error message for field "nombre" is "El nombre de la categoría es obligatorio"

Scenario: Category creation with blank name
Given I am authenticated
When I create a category with name ""
Then the response status code is 400
And the error message for field "nombre" is "El nombre de la categoría es obligatorio"

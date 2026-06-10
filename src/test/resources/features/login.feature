@auth
Feature: User login

  As a registered user
  I want to log in with my email and password
  So that I can access my financial data

  Background:
    Given I'm already registered with email "existing@example.com" and password "Password123!"

  Scenario: Successful login returns JWT token
    When I log in with email "existing@example.com" and password "Password123!"
    Then the response status code is 200
    And the response includes a valid token
    And the response email is "existing@example.com"

  Scenario: Login with wrong password returns 401
    When I log in with email "existing@example.com" and password "Incorrecta999!"
    Then the response status code is 401
    And the response error message is "Credenciales inválidas"

  Scenario: Login with empty email returns 400
    When I log in with email "" and password "Password123!"
    Then the response status code is 400
    And the error message for field "email" is "El email es obligatorio"
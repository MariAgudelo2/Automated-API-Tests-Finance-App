@auth
Feature: User registration

  As a visitor of the application
  I want to register with my name, lastname, email and password
  So that I can access the financial management features

  Scenario: Successful registration
    Given no user is registered with the email "new@example.com"
    When I register with first name "Camila", last name "López", email "new@example.com" and password "Password123!"
    Then the response status code is 200
    And the response includes a valid token
    And the response email is "new@example.com"

  Scenario: Registration with already registered email
    Given the email "existing@example.com" is already registered
    When I register with first name "Camila", last name "López", email "existing@example.com" and password "Password123!"
    Then the response status code is 409
    And the response error message is "El email ya se encuentra registrado"

  Scenario Outline: Registration with empty required field
    Given the registration system is available
    When I register with first name "<name>", last name "<lastname>", email "<email>" and password "<password>"
    Then the response status code is 400
    And the error message for field "<field>" is "<message>"

    # Error messages are kept in Spanish as they come directly from the API response
    Examples:
      | name   | lastname | email       | password     | field         | message                    |
      |        | López    | cam@test.co | Password123! | primer_nombre | El nombre es obligatorio   |
      | Camila | López    | no-es-email | Password123! | email         | Email inválido             |
      | Camila | López    | cam@test.co |              | contrasena    | Mínimo 8 caracteres        |
@auth
Feature: User registration

  As a visitor of the application
  I want to register with my name, lastname, email and password
  So that I can access the financial management features

  Scenario: Successful registration
    Given no user is registered with the email "new@example.com"
    When I register with first name "Camila", last name "Castro", email "new@example.com" and password "Password123!"
    Then the response status code is 200
    And the response includes a valid token
    And the response email is "new@example.com"

  Scenario: Registration with already registered email
    Given the email "existing@example.com" is already registered
    When I try to register with first name "Camila", last name "Castro", email "existing@example.com" and password "Password123!"
    Then the response status code is 409
    And the response error message is "El email ya se encuentra registrado"

  Scenario Outline: Registration with invalid required fields
    Given I'm on the registration page
    When I register with first name "<name>", last name "<lastname>", email "<email>" and password "<password>"
    Then the response status code is 400
    And the error message for field "<field>" is "<message>"

    # Error messages are kept in Spanish as they come directly from the API response
    Examples:
      | name   | lastname | email        | password     | field         | message                                            |
      |        | Castro   | cam@test.co  | Password123! | primer_nombre | El nombre es obligatorio                           |
      | Camila | Castro   | not-an-email | Password123! | email         | Email inválido                                     |
      | Camila | Castro   | cam@test.co  |              | contrasena    | La contraseña es obligatoria                       |
      | Camila | Castro   | cam@test.co  | weakpassword | contrasena    | Debe tener mayúscula, número y carácter especial   |  
      | Camila | Castro   | cam@test.co  |              | contrasena    | La contraseña es obligatoria                       |
      | Camila | Castro   | cam@test.co  |    Weak1!    | contrasena    | Mínimo 8 caracteres                            |   
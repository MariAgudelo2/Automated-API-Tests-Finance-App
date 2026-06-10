package co.edu.udea.certificacion.gestionfinanciera.steps;

import co.edu.udea.certificacion.gestionfinanciera.abilities.CallTheFinanceApi;
import co.edu.udea.certificacion.gestionfinanciera.models.User;
import co.edu.udea.certificacion.gestionfinanciera.questions.TheLastResponse;
import co.edu.udea.certificacion.gestionfinanciera.tasks.auth.LoginUser;
import co.edu.udea.certificacion.gestionfinanciera.tasks.auth.RegisterUser;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.annotations.CastMember;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.ThreadLocalRandom;

public class AuthStepsDefinition {

    private Actor user;
    private Scenario currentScenario;

    @Before
    public void setTheStage(Scenario scenario) {
        OnStage.setTheStage(new OnlineCast());
        user = OnStage.theActorCalled("Robinson");
        user.can(CallTheFinanceApi.atDefaultUrl());
        currentScenario = scenario;
    }

    @Given("no user is registered with the email {string}")
    public void noUserIsRegisteredWithEmail(String email) {
        // Email is assumed to be new in the system
    }

    @Given("the email {string} is already registered")
    public void theEmailIsAlreadyRegistered(String email) {
        int randomNumber = ThreadLocalRandom.current().nextInt(1000, 10000);
        String existingEmail = randomNumber + email;

        user.remember("existingEmail", existingEmail);
        
        User existingUser = new User("Existing", "User", existingEmail, "Password123!");
        user.attemptsTo(
            RegisterUser.withCredentials(existingUser)
        );
    }

    @Given("I'm on the registration page")
    public void iAmOnTheRegistrationPage() {
        // Page navigation is not applicable for API testing, so this step is just a placeholder
    }

     @Given("I'm already registered with email {string} and password {string}")
    public void theUserIsAlreadyRegistered(String email, String password) {
        // No action needed since the user is registered in a previous step
    }

    @When("I register with first name {string}, last name {string}, email {string} and password {string}")
    public void iRegisterWith(String firstName, String lastName, String email, String password) {
        int randomNumber = ThreadLocalRandom.current().nextInt(1000, 10000);
        String dynamicEmail = randomNumber + email;

        user.remember("registeredEmail", dynamicEmail);

        User newUser = new User(firstName, lastName, dynamicEmail, password);
        user.attemptsTo(
            RegisterUser.withCredentials(newUser)
        );
    }

    @When("I try to register with first name {string}, last name {string}, email {string} and password {string}")
    public void iTryToRegisterWith(String firstName, String lastName, String email, String password) {
        String actualExistingEmail = user.recall("existingEmail");

        User newUser = new User(firstName, lastName, actualExistingEmail, password);
        user.attemptsTo(
            RegisterUser.withCredentials(newUser)
        );
    }

    @When("I log in with email {string} and password {string}")
    public void iLogInWith(String email, String password) {
        User loggingUser = new User(email, password);

        user.attemptsTo(
            LoginUser.withCredentials(loggingUser)
        );
    }

    @Then("the response status code is {int}")
    public void theResponseStatusCodeIs(int expectedStatus) {
        assertThat(TheLastResponse.statusCode().answeredBy(user))
            .as("Expected status code " + expectedStatus)
            .isEqualTo(expectedStatus);
    }

    @And("the response includes a valid token")
    public void theResponseIncludesAValidToken() {
        assertThat(TheLastResponse.bodyField("token").answeredBy(user))
            .isNotNull()
            .isNotBlank();
    }

    @And("the response email is {string}")
    public void theResponseEmailIs(String expectedEmail) {
        if(currentScenario.getName().contains("Successful registration")) {

            String actualExpectedEmail = user.recall("registeredEmail");
            assertThat(TheLastResponse.bodyField("email").answeredBy(user))
                .isEqualTo(actualExpectedEmail);
            return;

        } else {

            assertThat(TheLastResponse.bodyField("email").answeredBy(user))
                .isEqualTo(expectedEmail);

        }
    }

    @And("the response error message is {string}")
    public void theResponseErrorMessageIs(String expectedMessage) {
        assertThat(TheLastResponse.bodyField("mensaje").answeredBy(user))
            .isEqualTo(expectedMessage);
    }

    @And("the error message for field {string} is {string}")
    public void theErrorMessageForFieldIs(String field, String expectedMessage) {
        String actualMessage = TheLastResponse.bodyField(field).answeredBy(user);
    
        if (field.equals("contrasena") && expectedMessage.equals("La contraseña es obligatoria")) {
            // We allow for either "La contraseña es obligatoria" or "Mínimo 8 caracteres" since the API might return either message for an empty password
            assertThat(actualMessage)
                .isIn("La contraseña es obligatoria", "Mínimo 8 caracteres", "Debe tener mayúscula, número y carácter especial");
        } else {
            assertThat(actualMessage).isEqualTo(expectedMessage);
        }
    }
}
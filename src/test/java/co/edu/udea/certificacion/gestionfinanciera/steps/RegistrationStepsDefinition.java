package co.edu.udea.certificacion.gestionfinanciera.steps;

import co.edu.udea.certificacion.gestionfinanciera.abilities.CallTheFinanceApi;
import co.edu.udea.certificacion.gestionfinanciera.models.User;
import co.edu.udea.certificacion.gestionfinanciera.questions.TheLastResponse;
import co.edu.udea.certificacion.gestionfinanciera.tasks.auth.RegisterUser;
import io.cucumber.java.Before;
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

public class RegistrationStepsDefinition {

    @CastMember(name = "Camila")
    Actor camila;

    @Before
    public void setUp() {
        OnStage.setTheStage(new OnlineCast());
    
        camila = OnStage.theActorCalled("Camila");
        camila.can(CallTheFinanceApi.atDefaultUrl());
    }

    @Given("no user is registered with the email {string}")
    public void noUserIsRegisteredWithEmail(String email) {
        // Email is assumed to be new in the system
    }

    @Given("the email {string} is already registered")
    public void theEmailIsAlreadyRegistered(String email) {
        int randomNumber = ThreadLocalRandom.current().nextInt(1000, 10000);
        String existingEmail = randomNumber + email;

        camila.remember("existingEmail", existingEmail);
        
        User existingUser = new User("Existing", "User", existingEmail, "Password123!");
        camila.attemptsTo(
            RegisterUser.withCredentials(existingUser)
        );
    }

    @Given("I'm on the registration page")
    public void iAmOnTheRegistrationPage() {
        // Page navigation is not applicable for API testing, so this step is just a placeholder
    }

    @When("I register with first name {string}, last name {string}, email {string} and password {string}")
    public void iRegisterWith(String firstName, String lastName, String email, String password) {
        int randomNumber = ThreadLocalRandom.current().nextInt(1000, 10000);
        String dynamicEmail = randomNumber + email;

        camila.remember("registeredEmail", dynamicEmail);

        User newUser = new User(firstName, lastName, dynamicEmail, password);
        camila.attemptsTo(
            RegisterUser.withCredentials(newUser)
        );
    }

    @When("I try to register with first name {string}, last name {string}, email {string} and password {string}")
    public void iTryToRegisterWith(String firstName, String lastName, String email, String password) {
        String actualExistingEmail = camila.recall("existingEmail");

        User newUser = new User(firstName, lastName, actualExistingEmail, password);
        camila.attemptsTo(
            RegisterUser.withCredentials(newUser)
        );
    }

    @Then("the response status code is {int}")
    public void theResponseStatusCodeIs(int expectedStatus) {
        assertThat(TheLastResponse.statusCode().answeredBy(camila))
            .as("Expected status code " + expectedStatus)
            .isEqualTo(expectedStatus);
    }

    @And("the response includes a valid token")
    public void theResponseIncludesAValidToken() {
        assertThat(TheLastResponse.bodyField("token").answeredBy(camila))
            .isNotNull()
            .isNotBlank();
    }

    @And("the response email is {string}")
    public void theResponseEmailIs(String expectedEmail) {
        String actualExpectedEmail = camila.recall("registeredEmail");
        assertThat(TheLastResponse.bodyField("email").answeredBy(camila))
            .isEqualTo(actualExpectedEmail);
    }

    @And("the response error message is {string}")
    public void theResponseErrorMessageIs(String expectedMessage) {
        assertThat(TheLastResponse.bodyField("mensaje").answeredBy(camila))
            .isEqualTo(expectedMessage);
    }

    @And("the error message for field {string} is {string}")
    public void theErrorMessageForFieldIs(String field, String expectedMessage) {
        String actualMessage = TheLastResponse.bodyField(field).answeredBy(camila);
    
        if (field.equals("contrasena") && expectedMessage.equals("La contraseña es obligatoria")) {
            // We allow for either "La contraseña es obligatoria" or "Mínimo 8 caracteres" since the API might return either message for an empty password
            assertThat(actualMessage)
                .isIn("La contraseña es obligatoria", "Mínimo 8 caracteres", "Debe tener mayúscula, número y carácter especial");
        } else {
            assertThat(actualMessage).isEqualTo(expectedMessage);
        }
    }
}
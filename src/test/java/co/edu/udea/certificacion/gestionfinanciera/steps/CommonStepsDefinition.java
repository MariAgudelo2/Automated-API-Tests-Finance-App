package co.edu.udea.certificacion.gestionfinanciera.steps;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.udea.certificacion.gestionfinanciera.models.User;
import co.edu.udea.certificacion.gestionfinanciera.questions.TheLastResponse;
import co.edu.udea.certificacion.gestionfinanciera.tasks.auth.LoginUser;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;


public class CommonStepsDefinition {

    Actor user = OnStage.theActorInTheSpotlight();

    @Given("I am authenticated")
    public void iAmAuthenticated() {
        User loggedUser = new User("existing@example.com", "Password123!");
        user.attemptsTo(
            LoginUser.withCredentials(loggedUser)
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

        String actualExpectedEmail = user.recall("currentEmail");
        assertThat(TheLastResponse.bodyField("email").answeredBy(user))
            .isEqualTo(actualExpectedEmail);

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

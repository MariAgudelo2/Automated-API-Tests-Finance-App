package co.edu.udea.certificacion.gestionfinanciera.steps;

import co.edu.udea.certificacion.gestionfinanciera.models.User;
import co.edu.udea.certificacion.gestionfinanciera.tasks.auth.LoginUser;
import co.edu.udea.certificacion.gestionfinanciera.tasks.auth.RegisterUser;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

import java.util.concurrent.ThreadLocalRandom;

public class AuthStepsDefinition {

    Actor user = OnStage.theActorInTheSpotlight();

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

        user.remember("currentEmail", dynamicEmail);

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
        user.remember("currentEmail", email);
        user.attemptsTo(
            LoginUser.withCredentials(loggingUser)
        );
    }

}
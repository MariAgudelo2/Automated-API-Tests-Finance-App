package co.edu.udea.certificacion.gestionfinanciera.steps;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.udea.certificacion.gestionfinanciera.models.Category;
import co.edu.udea.certificacion.gestionfinanciera.questions.TheLastResponse;
import co.edu.udea.certificacion.gestionfinanciera.tasks.category.CreateCategory;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;


public class CategoryStepsDefinition {

    Actor user = OnStage.theActorInTheSpotlight();

    @When("I create a category with name {string}")
    public void createCategoryWithName(String name) {

        Category category = new Category(name);
        user.attemptsTo(CreateCategory.withData(category));
    }

    @When("I create a category without a name")
    public void createCategoryWithoutName() {

        Category category = new Category();
        category.setNombre(null);

        user.attemptsTo(CreateCategory.withData(category));
    }

    @Then("the category name is {string}")
    public void validateCategoryName(String expectedName) {

        assertThat(TheLastResponse.bodyField("nombre").answeredBy(user))
            .isEqualTo(expectedName);
    }
}

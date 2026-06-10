package co.edu.udea.certificacion.gestionfinanciera.steps;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import co.edu.udea.certificacion.gestionfinanciera.models.Category;
import co.edu.udea.certificacion.gestionfinanciera.models.Transaction;
import co.edu.udea.certificacion.gestionfinanciera.questions.TheLastResponse;
import co.edu.udea.certificacion.gestionfinanciera.tasks.category.CreateCategory;
import co.edu.udea.certificacion.gestionfinanciera.tasks.transaction.CreateTransaction;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

public class TransactionStepsDefinition {

    private final Actor user = OnStage.theActorInTheSpotlight();

    @Given("I have a category {string} created")
    public void iHaveACategoryCreated(String categoryName) {

        user.attemptsTo(CreateCategory.withData(new Category(categoryName)));
    }

    @When("I register an income of {long} COP in category {string} with date {string}")
    public void registerIncome(long amount, String categoryName, String date) {

        Transaction transaction = new Transaction("INGRESO", String.valueOf(amount), categoryName, date);
        user.attemptsTo(CreateTransaction.withData(transaction));
    }

    @When("I register an expense of {long} COP in category {string} with date {string}")
    public void registerExpense(long amount, String categoryName, String date) {

        Transaction transaction = new Transaction("GASTO", String.valueOf(amount), categoryName, date);
        user.attemptsTo(CreateTransaction.withData(transaction));
    }

    @When("I register a {string} transaction with amount {int} in category {string} with date {string}")
    public void registerTransactionWithInvalidAmount(String type, Integer amount, String categoryName, String date) {

        Transaction transaction = new Transaction(type, String.valueOf(amount), categoryName, date);
        user.attemptsTo(CreateTransaction.withData(transaction));
    }

    @When("I register an income of {long} COP in category {string} without a date")
    public void registerIncomeWithoutDate(long amount, String categoryName) {

        Transaction transaction = new Transaction("INGRESO", String.valueOf(amount), categoryName, null);
        user.attemptsTo(CreateTransaction.withData(transaction));
    }

    @And("the transaction details are:")
    public void theTransactionDetailsAre(DataTable dataTable) {

        Map<String, String> expected =
                dataTable.asMap(String.class, String.class);

        assertThat(
                TheLastResponse.bodyField("tipo")
                        .answeredBy(user)
        ).isEqualTo(expected.get("type"));

        assertThat(
                TheLastResponse.bodyField("monto")
                        .answeredBy(user)
        ).isEqualTo(expected.get("amount"));

        assertThat(
                TheLastResponse.bodyField("categoria")
                        .answeredBy(user)
        ).isEqualTo(expected.get("category"));

        assertThat(
                TheLastResponse.bodyField("fecha")
                        .answeredBy(user)
        ).isEqualTo(expected.get("date"));
    }
}
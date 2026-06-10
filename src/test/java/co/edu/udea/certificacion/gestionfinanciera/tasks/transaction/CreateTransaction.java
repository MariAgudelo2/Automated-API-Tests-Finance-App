package co.edu.udea.certificacion.gestionfinanciera.tasks.transaction;

import co.edu.udea.certificacion.gestionfinanciera.interactions.CreateNewTransaction;
import co.edu.udea.certificacion.gestionfinanciera.models.Transaction;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class CreateTransaction implements Task {

    private final Transaction transaction;

    public CreateTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public static CreateTransaction withData(Transaction transaction) {
        return Tasks.instrumented(
                CreateTransaction.class,
                transaction
        );
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(CreateNewTransaction.withDetails(transaction));
    }
}
package co.edu.udea.certificacion.gestionfinanciera.tasks.transaction;

import static co.edu.udea.certificacion.gestionfinanciera.config.ApiConfig.TRANSACCIONES;

import java.util.HashMap;
import java.util.Map;

import co.edu.udea.certificacion.gestionfinanciera.abilities.CallTheFinanceApi;
import co.edu.udea.certificacion.gestionfinanciera.models.Transaction;
import co.edu.udea.certificacion.gestionfinanciera.models.TokenMemory;
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

        String token = actor.recall(TokenMemory.JWT_TOKEN);

        Map<String, Long> categories =
                actor.recall("CATEGORIES");

        Long categoryId =
                categories.get(transaction.getCategoria());

        Map<String, Object> body = new HashMap<>();

        body.put("tipo", transaction.getTipo());
        body.put("monto", transaction.getMonto());
        body.put("categoriaId", categoryId);
        body.put("fecha", transaction.getFecha());

        CallTheFinanceApi.as(actor)
                .asAuthenticatedUser(token)
                .body(body)
                .post(TRANSACCIONES);
    }
}
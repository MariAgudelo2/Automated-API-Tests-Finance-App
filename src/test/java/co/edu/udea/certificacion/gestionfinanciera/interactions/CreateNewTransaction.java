package co.edu.udea.certificacion.gestionfinanciera.interactions;

import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.Actor;

import java.util.HashMap;
import java.util.Map;

import co.edu.udea.certificacion.gestionfinanciera.abilities.CallTheFinanceApi;
import co.edu.udea.certificacion.gestionfinanciera.models.TokenMemory;
import co.edu.udea.certificacion.gestionfinanciera.models.Transaction;

import static co.edu.udea.certificacion.gestionfinanciera.config.ApiConfig.TRANSACCIONES;

public class CreateNewTransaction implements Interaction {
    private final Transaction transaction;

    public CreateNewTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public static CreateNewTransaction withDetails(Transaction transaction) {
        return Tasks.instrumented(CreateNewTransaction.class, transaction);
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

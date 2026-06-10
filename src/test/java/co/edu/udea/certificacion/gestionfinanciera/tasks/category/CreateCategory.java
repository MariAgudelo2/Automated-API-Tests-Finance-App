package co.edu.udea.certificacion.gestionfinanciera.tasks.category;

import co.edu.udea.certificacion.gestionfinanciera.models.Category;
import co.edu.udea.certificacion.gestionfinanciera.models.TokenMemory;
import co.edu.udea.certificacion.gestionfinanciera.abilities.CallTheFinanceApi;

import java.util.HashMap;
import java.util.Map;


import net.serenitybdd.screenplay.Task;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Tasks;

import static co.edu.udea.certificacion.gestionfinanciera.config.ApiConfig.CATEGORIAS;


public class CreateCategory implements Task {

    private final Category category;

    public CreateCategory(Category category) {
        this.category = category;
    }

    public static CreateCategory withData(Category category) {
        return Tasks.instrumented(CreateCategory.class, category);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        String token = actor.recall(TokenMemory.JWT_TOKEN);
        System.out.println("TOKEN RECUPERADO = " + token);

        Map<String, Object> body = new HashMap<>();
        body.put("nombre", category.getNombre());

        CallTheFinanceApi.as(actor)
                .asAuthenticatedUser(token)
                .body(body)
                .post(CATEGORIAS);

        System.out.println("STATUS = " + SerenityRest.lastResponse().statusCode());
        System.out.println("BODY = " + SerenityRest.lastResponse().asString());
        System.out.println("TOKEN = " + token);
    }
}

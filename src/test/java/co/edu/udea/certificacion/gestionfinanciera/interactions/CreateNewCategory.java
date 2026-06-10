package co.edu.udea.certificacion.gestionfinanciera.interactions;

import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

import java.util.HashMap;
import java.util.Map;

import co.edu.udea.certificacion.gestionfinanciera.abilities.CallTheFinanceApi;
import co.edu.udea.certificacion.gestionfinanciera.models.TokenMemory;
import co.edu.udea.certificacion.gestionfinanciera.models.Category;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;

import static co.edu.udea.certificacion.gestionfinanciera.config.ApiConfig.CATEGORIAS;

public class CreateNewCategory implements Interaction {
    private final Category category;

    public CreateNewCategory(Category category) {
        this.category = category;
    }

    public static CreateNewCategory withDetails(Category category) {
        return Tasks.instrumented(CreateNewCategory.class, category);
    }

    @Override
public <T extends Actor> void performAs(T actor) {

    String token = actor.recall(TokenMemory.JWT_TOKEN);

    Map<String, Object> body = new HashMap<>();
    body.put("nombre", category.getNombre());

    CallTheFinanceApi.as(actor)
            .asAuthenticatedUser(token)
            .body(body)
            .post(CATEGORIAS);

    if (SerenityRest.lastResponse().statusCode() == 200) {

        Long categoryId = SerenityRest.lastResponse()
                .jsonPath()
                .getLong("id");

        Map<String, Long> categories = actor.recall("CATEGORIES");

        if (categories == null) {
            categories = new HashMap<>();
        }

        categories.put(
                category.getNombre(),
                categoryId
        );

        actor.remember("CATEGORIES", categories);

        System.out.println("CATEGORY CREATED:");
        System.out.println(category.getNombre() + " -> " + categoryId);
    }
}

}

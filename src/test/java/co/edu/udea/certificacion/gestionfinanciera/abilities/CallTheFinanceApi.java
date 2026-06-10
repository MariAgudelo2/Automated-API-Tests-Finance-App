package co.edu.udea.certificacion.gestionfinanciera.abilities;

import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

import static co.edu.udea.certificacion.gestionfinanciera.config.ApiConfig.BASE_URL;

public class CallTheFinanceApi implements Ability {

    private final String baseUrl;

    private CallTheFinanceApi(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public static CallTheFinanceApi atDefaultUrl() {
        return new CallTheFinanceApi(BASE_URL);
    }

    public static CallTheFinanceApi at(String baseUrl) {
        return new CallTheFinanceApi(baseUrl);
    }

    public RequestSpecification asGuest() {
        return SerenityRest.given()
                .baseUri(baseUrl)
                .contentType("application/json");
    }

    public RequestSpecification asAuthenticatedUser(String token) {
        return SerenityRest.given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .header("Authorization", "Bearer " + token);
    }

    public static CallTheFinanceApi as(Actor actor) {
        return actor.abilityTo(CallTheFinanceApi.class);
    }
}
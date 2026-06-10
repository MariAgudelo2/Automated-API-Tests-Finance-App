package co.edu.udea.certificacion.gestionfinanciera.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Question;

public class TheLastResponse {

    public static Question<Integer> statusCode() {
        return actor -> SerenityRest.lastResponse().statusCode();
    }

    public static Question<String> bodyField(String jsonPath) {
        return actor -> SerenityRest.lastResponse().jsonPath().getString(jsonPath);
    }

    public static Question<Boolean> booleanField(String jsonPath) {
        return actor -> SerenityRest.lastResponse().jsonPath().getBoolean(jsonPath);
    }

    public static Question<Integer> intField(String jsonPath) {
        return actor -> SerenityRest.lastResponse().jsonPath().getInt(jsonPath);
    }

    public static Question<String> body() {
        return actor -> SerenityRest.lastResponse().body().asString();
    }
}
package co.edu.udea.certificacion.gestionfinanciera.tasks.auth;

import co.edu.udea.certificacion.gestionfinanciera.abilities.CallTheFinanceApi;
import co.edu.udea.certificacion.gestionfinanciera.models.TokenMemory;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

import java.util.HashMap;
import java.util.Map;

import static co.edu.udea.certificacion.gestionfinanciera.config.ApiConfig.AUTH_LOGIN;

public class LoginUser implements Task {

    private final String email;
    private final String password;

    public LoginUser(String email, String password) {
        this.email    = email;
        this.password = password;
    }

    public static LoginUser withCredentials(String email, String password) {
        return Tasks.instrumented(LoginUser.class, email, password);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("contrasena", password);

        CallTheFinanceApi.as(actor)
                .asGuest()
                .body(body)
                .post(AUTH_LOGIN);

        if (SerenityRest.lastResponse().statusCode() == 200) {
            String token = SerenityRest.lastResponse().jsonPath().getString("token");
            actor.remember(TokenMemory.JWT_TOKEN, token);
        }
    }
}
package co.edu.udea.certificacion.gestionfinanciera.interactions;

import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

import java.util.HashMap;
import java.util.Map;

import co.edu.udea.certificacion.gestionfinanciera.abilities.CallTheFinanceApi;
import co.edu.udea.certificacion.gestionfinanciera.models.TokenMemory;
import co.edu.udea.certificacion.gestionfinanciera.models.User;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;

import static co.edu.udea.certificacion.gestionfinanciera.config.ApiConfig.AUTH_REGISTER;

public class Register implements Interaction {
    
    private final User user;

    public Register(User user) {
        this.user = user;
    }

    public static Register withCredentials(User user) {
        return Tasks.instrumented(Register.class, user);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Map<String, String> body = new HashMap<>();
        body.put("primer_nombre", user.getFirstName());
        body.put("apellido", user.getLastname());
        body.put("email", user.getEmail());
        body.put("contrasena", user.getPassword());

        CallTheFinanceApi.as(actor)
                .asGuest()
                .body(body)
                .post(AUTH_REGISTER);

        if (SerenityRest.lastResponse().statusCode() == 200) {
            String token = SerenityRest.lastResponse().jsonPath().getString("token");
            actor.remember(TokenMemory.JWT_TOKEN, token);
        }
    }
}


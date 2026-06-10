package co.edu.udea.certificacion.gestionfinanciera.tasks.auth;


import co.edu.udea.certificacion.gestionfinanciera.interactions.Register;
import co.edu.udea.certificacion.gestionfinanciera.models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class RegisterUser implements Task {

    private final User user;

    public RegisterUser(User user) {
        this.user = user;
    }

    public static RegisterUser withCredentials(User user) {
        return Tasks.instrumented(RegisterUser.class, user);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Register.withCredentials(user));
    }
}

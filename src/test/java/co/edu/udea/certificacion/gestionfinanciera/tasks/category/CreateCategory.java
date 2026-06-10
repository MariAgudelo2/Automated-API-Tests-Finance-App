package co.edu.udea.certificacion.gestionfinanciera.tasks.category;

import co.edu.udea.certificacion.gestionfinanciera.interactions.CreateNewCategory;
import co.edu.udea.certificacion.gestionfinanciera.models.Category;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

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
        actor.attemptsTo(CreateNewCategory.withDetails(category));
    }
}

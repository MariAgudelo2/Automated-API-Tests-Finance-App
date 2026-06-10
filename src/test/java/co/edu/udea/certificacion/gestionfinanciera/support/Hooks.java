package co.edu.udea.certificacion.gestionfinanciera.support;

import co.edu.udea.certificacion.gestionfinanciera.abilities.CallTheFinanceApi;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

public class Hooks {

    @Before
    public void setTheStage() {

        OnStage.setTheStage(new OnlineCast());

        Actor actor = OnStage.theActorCalled("Robinson");
        actor.can(CallTheFinanceApi.atDefaultUrl());
    }
}
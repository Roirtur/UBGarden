package fr.ubx.poo.ugarden.go;

import fr.ubx.poo.ugarden.go.bonus.*;

public interface TakeVisitor {

    // HealthInc
    default void take(Heart bonus) {
    }

    // Bee
    default void take(Bee bonus) {
    }

    // Apple
    default void take(Apple bonus) {
    }

    // PoisonedApple
    default void take(PoisonedApple bonus) {
    }

}

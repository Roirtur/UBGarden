package fr.ubx.poo.ugarden.go;

import fr.ubx.poo.ugarden.go.bonus.*;

public interface TakeVisitor {

    // HealthInc
    default void take(Heart bonus) {
    }

    // HealthInc
    default void take(Bee bonus) {
    }

}

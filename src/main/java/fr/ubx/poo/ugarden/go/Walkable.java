package fr.ubx.poo.ugarden.go;


import fr.ubx.poo.ugarden.go.personage.Player;

public interface Walkable {
    default boolean walkableBy(Player player) { return false; }

    default int energyConsumptionWalk() { return 0; }
}

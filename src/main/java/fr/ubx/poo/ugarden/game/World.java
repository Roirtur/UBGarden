package fr.ubx.poo.ugarden.game;


import java.util.List;

public interface World {
    int levels();
    int currentLevel();
    void setCurrentLevel(int level);

    Map getGrid(int level);

    Map getGrid();




}

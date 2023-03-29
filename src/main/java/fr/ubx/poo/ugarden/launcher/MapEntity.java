package fr.ubx.poo.ugarden.launcher;

public enum MapEntity {
    PoisonedApple('-'),
    Apple('+'),
    Bee('B'),
    Carrots('F'),
    Flowers('O'),
    Grass('G'),
    Land('L'),
    Tree('T'),

    Player('P'),
    Princess('W'),
    Key('K'),
    Heart('H'),

    DoorPrevOpened('<'),
    DoorNextOpened('>'),
    DoorNextClosed('D'),

    ;

    private final char code;

    MapEntity(char c) {
        this.code = c;
    }

    public char getCode() { return this.code; }

    public static MapEntity fromCode(char c) {
        for (MapEntity mapEntity : values()) {
            if (mapEntity.code == c)
                return mapEntity;
        }
        throw new MapException("Invalid character " + c);
    }

    @Override
    public String toString() {
        return Character.toString(code);
    }

}

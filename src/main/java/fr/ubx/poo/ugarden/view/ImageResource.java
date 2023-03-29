package fr.ubx.poo.ugarden.view;

public enum ImageResource {
    // Decor elements
    GRASS("grass.jpg"),
    LAND("land.jpg"),
    CARROTS("carrots.jpg"),
    FLOWERS("flowers.jpg"),

    ENERGY("energy.png"),
    TREE("tree.jpg"),


    // Bonus
    POISONED_APPLE("poisonedApple.png"),
    APPLE("apple.jpg"),
    HEART("heart.png"),
    KEY("key.png"),

    DOOR_OPENED("door_opened.png"),
    DOOR_CLOSED("door_closed.png"),

    // Player, princess and bees
    PLAYER_UP("player_up.png"),
    PLAYER_RIGHT("player_right.png"),
    PLAYER_DOWN("player_down.png"),
    PLAYER_LEFT("player_left.png"),

    BEE_UP("bee_up.png"),
    BEE_RIGHT("bee_right.png"),
    BEE_DOWN("bee_down.png"),
    BEE_LEFT("bee_left.png"),

    PRINCESS("princess.jpg"),


    // Status bar

    DIGIT_0("banner_0.jpg"),
    DIGIT_1("banner_1.jpg"),
    DIGIT_2("banner_2.jpg"),
    DIGIT_3("banner_3.jpg"),
    DIGIT_4("banner_4.jpg"),
    DIGIT_5("banner_5.jpg"),
    DIGIT_6("banner_6.jpg"),
    DIGIT_7("banner_7.jpg"),
    DIGIT_8("banner_8.jpg"),
    DIGIT_9("banner_9.jpg"),

    ;


    public static final int size = 40;
    private final String fileName;
    ImageResource(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

}


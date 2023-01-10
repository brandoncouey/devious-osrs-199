package io.ruin.model.activities.gambling;

public enum Flower {
    BLACK(2988),       // black
    WHITE(2987),        // white
    ORANGE(2985),    // orange
    PURPLE(2984),    // purple
    YELLOW(2983),    // yellow
    BLUE(2982),      // blue
    RED(2981),       // red
    MIXED(2980),     // mixed
    ASSORTED(2986);  // assorted

    private final int objId;

    Flower(int objId) {
        this.objId = objId;
    }

    public int getObjId() {
        return objId;
    }
}

package io.ruin.model.map.object.actions.impl.prifddinas.amlodddistrict.gauntlet;

//import io.ruin.model.map.object.actions.impl.prifddinas.amlodddistrict.RoomType;

public enum GauntletDef {

    /*START("Starting", RoomType.START, new Chunk[] {new Chunk(238, 709, 1)})//239, 710

    ;

    public String getName() {
        return name;
    }

    public RoomType getType() {
        return type;
    }

    *//**
     * all chambers are 4x4 chunks, so just use the "base" chunk, the most south western one.`
     * this should either be size 1 or 3.
     * size 1 for "dead end" rooms like the floor-ending room, and 3 for other rooms following the same logic as they are in the map:
     * [0] -> exit to the west of the entrance
     * [1] -> exit on the opposite of the entrance
     * [2] -> exit to the east of the entrance
     * (west/east directions assume the room hasn't been rotated, as is their default state in the map)
     *//*
    private Chunk[] baseChunks;

    private String name;
    private RoomType type;

    GauntletDef(String name, RoomType type, Chunk[] baseChunks) {
        this.name = name;
        this.type = type;
        this.baseChunks = baseChunks;
    }

    public Chunk getBaseChunk(int layout) {
        if (layout < 0 || layout >= baseChunks.length)
            throw new IllegalArgumentException();
        return baseChunks[layout];
    }*/
}

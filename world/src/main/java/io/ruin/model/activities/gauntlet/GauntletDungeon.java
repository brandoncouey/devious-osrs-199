package io.ruin.model.activities.gauntlet;

import io.ruin.model.activities.gauntlet.monsters.GauntletMonster;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.utility.Misc;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Greco
 * @since 04/08/2021
 */
@Getter
public class GauntletDungeon {

    private final Player player;

    private static final int MAP_SIZE = 7;
    private static final int CHUNK_SIZE = 8;
    private static final int ROOM_TILE_SIZE = CHUNK_SIZE * 2;
    private static final int TOTAL_MAP_SIZE = (MAP_SIZE * ROOM_TILE_SIZE);
    private final GauntletRoom[][] rooms;
    //   private final Palette layout;
    private final GauntletType type;

    private final Position center;
    private final Position minimum;
    private final Position maximum;

    public GauntletDungeon(Player player, GauntletType type, Position minimum) {
        this.type = type;
        this.player = player;
        this.minimum = minimum;
        this.center = new Position(minimum.getX() + ((ROOM_TILE_SIZE * 3)), minimum.getY() + ((ROOM_TILE_SIZE * 3)));
        this.maximum = new Position(minimum.getX() + TOTAL_MAP_SIZE, minimum.getY() + TOTAL_MAP_SIZE);
        this.rooms = new GauntletRoom[MAP_SIZE][MAP_SIZE];
        //    this.layout = new Palette(13, 13, 4);
        //   generateDungeon();
    }

    /**
     * Generates a room based on the location in the layout.
     *
     * @param x
     * @param y
     * @return
     */
    public GauntletRoom generateRoom(int x, int y) {
        boolean corner = (x == 0 && y == 0) || (x == 6 && y == 0) || (x == 0 && y == 6) || (x == 6 && y == 6);
        boolean side = (x == 0 || y == 0 || x == 6 || y == 6);
        boolean center = (x == 3 && y == 3);
        boolean boss = (x == 2 && y == 3);
        int rotation = Misc.random(3);
        double dist = Math.hypot(Math.abs(x - 3.5), Math.abs(y - 3.5));
        double monster_chance = Misc.randomDouble(GauntletMonster.isOuterBounds(x, y) ? .25D : 0D, 1D - (0.3D * (3 - dist)));
        Position location = minimum.translate(x * ROOM_TILE_SIZE, y * ROOM_TILE_SIZE, 0);
        if (center) {
            return new GauntletRoom(x, y, location, GauntletRooms.STARTER_ROOM, rotation, monster_chance);
        } else if (boss) {
            return new GauntletRoom(x, y, location, GauntletRooms.BOSS_ROOM, rotation, monster_chance);
        } else if (corner) {
            GauntletRooms roomReference = GauntletRooms.findRoomOfType(GauntletRoomType.CORNER);
            return new GauntletRoom(x, y, location, roomReference, getCorrectedRotation(roomReference, x, y), monster_chance);
        } else if (side) {
            GauntletRooms roomReference = GauntletRooms.findRoomOfType(GauntletRoomType.SIDE);
            return new GauntletRoom(x, y, location, roomReference, getCorrectedRotation(roomReference, x, y), monster_chance);
        }
        return new GauntletRoom(x, y, location, GauntletRooms.findRoomOfType(GauntletRoomType.MIDDLE), rotation, monster_chance);
    }

    /**
     * Gets the adjusted rotation for rooms on the border of the layout.
     *
     * @param x
     * @param y
     * @return
     */
    public int getCorrectedRotation(GauntletRooms room, int x, int y) {
        GauntletRoomType type = room.getType();
        if (x == 0 && y > 0 && type == GauntletRoomType.SIDE) {
            return 3;
        }
        if (x > 0 && y == 0 && type == GauntletRoomType.SIDE) {
            return 2;
        }
        if (x == 6 && y > 0 && type == GauntletRoomType.SIDE) {
            return 1;
        }
        if (x == 0 && y == 0 && type == GauntletRoomType.CORNER) {
            return 2;
        }
        if (x == 0 && y == 6 && type == GauntletRoomType.CORNER) {
            return 3;
        }
        if (x == 6 && y == 0 && type == GauntletRoomType.CORNER) {
            return 1;
        }
        return 0;
    }

    public Optional<GauntletRoom> getRoomByType(GauntletRooms type) {
        return Stream.of(rooms).flatMap(Arrays::stream).filter(room -> room.getRoom() == type).findFirst();
    }

    public void doOuterLights(GauntletRoom openedRoom) {
        Position min = openedRoom.getLocation().translate(-2, -2, 0);
        Position max = openedRoom.getLocation().translate(ROOM_TILE_SIZE + 2, ROOM_TILE_SIZE + 2, 0);

    }

    /**
     * Handles destroying the dungeon.
     */
    public void destroy() {
        Stream.of(rooms).filter(Objects::nonNull).flatMap(Stream::of).forEach(GauntletRoom::destroy);
    }

    /**
     * @param room
     * @param plrLocation
     * @return
     */
    public Position getChunkRelativeTo(GauntletRoom room, Position plrLocation) {
        int x = room.getLocation().getRegion().baseX - plrLocation.getRegion().baseX + 6;
        int y = room.getLocation().getRegion().baseY - plrLocation.getRegion().baseY + 6;
        return new Position(x, y);
    }

    /**
     * Gets a room by the location provided.
     * @param location The location.
     * @return
     */
    /*public Optional<GauntletRoom> getRoom(Position location) {
        return Stream.of(rooms).flatMap(Arrays::stream).filter(room -> room.getBoundary().contains(location)).findFirst();
    }*/
}

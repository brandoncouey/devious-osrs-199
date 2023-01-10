package io.ruin.model.activities.raids.tob.dungeon;

import io.ruin.model.activities.raids.tob.dungeon.room.*;
import io.ruin.model.activities.raids.tob.party.TheatreParty;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.util.ListenedList;
import io.ruin.util.ListenedMap;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ReverendDread on 7/24/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@Getter
@Setter
public class TheatreDungeon {
        DynamicMap map;
    /**
     * List of rooms in the dungeon.
     */
    private final ListenedMap<RoomType, TheatreRoom> rooms = new ListenedMap<>();

    /**
     * List of rooms that have been started.
     */
    private final ListenedList<RoomType> started = new ListenedList<>();

    /**
     * The party assosiated with the dungeon.
     */
    private final TheatreParty party;

    /**
     * If the dungeon has been built.
     */
    private boolean built;

    /**
     * @param party
     */
    public TheatreDungeon(TheatreParty party) {
        this.party = party;
        rooms.postAdd(room -> {
            room.onLoad();
            room.onDeath(party);
            room.registerObjects();
            room.addMultiArea();
        });
        rooms.preRemove(room -> {
            room.removeMultiArea();
            room.destroy();
        });
    }

    /**
     * Builds the dungeon.
     */
    public void build() {
        if (!isBuilt()) {
            rooms.put(RoomType.MAIDEN, new MaidenRoom(party));
            rooms.put(RoomType.BLOAT, new BloatRoom(party));
            rooms.put(RoomType.VASILIAS, new VasiliasRoom(party));
            rooms.put(RoomType.SOTETSEG, new SotetsegRoom(party));
            rooms.put(RoomType.XARPUS, new XarpusRoom(party));
            rooms.put(RoomType.VERZIK, new VerzikRoom(party));
            rooms.put(RoomType.TREASURE, new TreasureRoom(party));
            setBuilt(true);
        }
    }

    /**
     * Moves a player into the converted position for the dungeon.
     *
     * @param player
     * @param type
     * @param position
     */
    public void move(Player player, RoomType type, Position position) {
        player.getMovement().teleport(
                rooms.get(type).convertX(position.getX()),
                rooms.get(type).convertY(position.getY()),
                position.getZ()
        );
        rooms.get(type).assignMapListener(player);
    }

    /**
     * Makes a player enter a certain room.
     *
     * @param player
     * @param type
     */
    public void enterRoom(Player player, RoomType type) {
        player.startEvent(e -> {
            move(player, type, rooms.get(type).getEntrance());
        });
    }

    /**
     * Checks if the desired room type has been started.
     *
     * @param type
     * @return
     */
    public boolean isStarted(RoomType type) {
        return started.contains(type);
    }

}

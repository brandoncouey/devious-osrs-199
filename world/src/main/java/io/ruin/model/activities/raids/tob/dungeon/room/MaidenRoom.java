package io.ruin.model.activities.raids.tob.dungeon.room;

import com.google.common.collect.Lists;
import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.cache.NpcID;
import io.ruin.model.World;
import io.ruin.model.activities.raids.tob.dungeon.RoomType;
import io.ruin.model.activities.raids.tob.party.PartyStatus;
import io.ruin.model.activities.raids.tob.party.TheatreParty;
import io.ruin.model.activities.raids.tob.party.TheatrePartyManager;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.GameMode;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;

import java.util.List;

/**
 * @author NuLL on 28/12/2021
 * https://www.rune-server.ee/members/null1001/
 * @project Devious
 */
public class MaidenRoom extends TheatreRoom {//Room 1

    private NPC maiden;
    private GameObject booth;
    public Player player;
    public boolean complete = false;
    public int deadplayer = 0;

    private final TheatreParty party;

    public MaidenRoom(TheatreParty party) {
        super(party);
        this.party = party;
    }

    @Override
    public void onLoad() {
        buildSw(12613, 1);
        buildSe(12869, 1);
        GameObject.spawn(32756, convertX(3192), convertY(4448), 0, 11, 4);
        Tile.get(convertX(3167), convertY(4449), 0, true).flagUnmovable(); // block floor
        Tile.get(convertX(3167), convertY(4448), 0, true).flagUnmovable(); // block floor
        Tile.get(convertX(3167), convertY(4447), 0, true).flagUnmovable(); // block floor
        Tile.get(convertX(3167), convertY(4446), 0, true).flagUnmovable(); // block floor
        Tile.get(convertX(3167), convertY(4445), 0, true).flagUnmovable(); // block floor
        Tile.get(convertX(3167), convertY(4444), 0, true).flagUnmovable(); // block floor
        Tile.get(convertX(3166), convertY(4444), 0, true).flagUnmovable(); // block floor
        Tile.get(convertX(3165), convertY(4444), 0, true).flagUnmovable(); // block floor
        Tile.get(convertX(3164), convertY(4444), 0, true).flagUnmovable(); // block floor
        Tile.get(convertX(3163), convertY(4444), 0, true).flagUnmovable(); // block floor
        Tile.get(convertX(3162), convertY(4444), 0, true).flagUnmovable(); // block floor
        Tile.get(convertX(3162), convertY(4445), 0, true).flagUnmovable(); // block floor
        Tile.get(convertX(3162), convertY(4446), 0, true).flagUnmovable(); // block floor
        Tile.get(convertX(3162), convertY(4447), 0, true).flagUnmovable(); // block floor
        Tile.get(convertX(3162), convertY(4448), 0, true).flagUnmovable(); // block floor
        Tile.get(convertX(3162), convertY(4449), 0, true).flagUnmovable(); // block floor
        Tile.get(convertX(3166), convertY(4449), 0, true).flagUnmovable(); // block floor
        Tile.get(convertX(3165), convertY(4449), 0, true).flagUnmovable(); // block floor
        Tile.get(convertX(3164), convertY(4449), 0, true).flagUnmovable(); // block floor
        Tile.get(convertX(3163), convertY(4449), 0, true).flagUnmovable(); // block floor
        booth = new GameObject(13441, convertX(3191), convertY(4449), 0, 11, 0);
        maiden = new NPC(NpcID.THE_MAIDEN_OF_SUGADINTI).spawn(convertX(3162), convertY(4444), 0, Direction.EAST, 0);
        maiden.setIgnoreMulti(true);
        maiden.getCombat().isAggressive();
            maiden.deathStartListener = (DeathListener.SimpleKiller) killer -> {
                party.forPlayers(p -> {
                    p.theatreOfBloodStage = 1;
                    p.sendFilteredMessage("You have completed stage 1!");
                    p.tobDeaths = 0;
                    if (p.getCombat().isDead()) {
                        System.out.println("Setting dead to false");
                        p.getCombat().setDead(false);
                        p.sendMessage("You can now move forward.");
                        p.animate(-1, 0);
                    }
                    if (p.getCombat().isDead()) {
                        System.out.println("Setting dead to false");
                        p.getCombat().setDead(false);
                        p.sendMessage("You can now move forward.");
                        p.animate(-1, 0);
                    }
                    if (p.isLocked()) {
                        p.sendMessage("Your team has completed the room. Please move to Next location.");
                        p.unlock();
                    }
                });
                maiden.getCombat().setAllowRespawn(false);
            };
    }

    @Override
    public void registerObjects() {
        ObjectAction.register(32755, "pass", (player, obj) -> {
            if (player.getPosition().getX() > obj.getPosition().getX()) {
                player.getMovement().teleport(obj.getPosition().getX() - 2, obj.getPosition().getY(), obj.getPosition().getZ()); // Teleport because it will not walk correctly :S wtf
            } else if (player.getPosition().getX() < obj.getPosition().getX()) {
                player.getMovement().teleport(obj.getPosition().getX() + 2, obj.getPosition().getY(), obj.getPosition().getZ());
            } else if (player.getPosition().getY() > obj.getPosition().getY()) {
                player.getMovement().teleport(obj.getPosition().getX(), obj.getPosition().getY() - 2, obj.getPosition().getZ());
            } else if (player.getPosition().getY() < obj.getPosition().getY()) {
                player.getMovement().teleport(obj.getPosition().getX(), obj.getPosition().getY() + 2, obj.getPosition().getZ());
            }
        });
        ObjectAction.register(33113, "enter", ((player, obj) -> {
            if (player.theatreOfBloodStage == 1) {
                TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).ifPresent(party ->
                        party.getDungeon().enterRoom(player, RoomType.BLOAT));
                player.theatreroom = "bloat";
            } else if (player.theatreOfBloodStage == 2) {
                TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).ifPresent(party ->
                        party.getDungeon().enterRoom(player, RoomType.VASILIAS));
                player.theatreroom = "vasilias";
            } else if (player.theatreOfBloodStage == 3) {
                TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).ifPresent(party ->
                        party.getDungeon().enterRoom(player, RoomType.SOTETSEG));
                player.theatreroom = "sotetseg";
            } else if (player.theatreOfBloodStage == 4) {
                TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).ifPresent(party ->
                        party.getDungeon().enterRoom(player, RoomType.XARPUS));
                player.theatreroom = "xarpus";
            }
        }));


    }

    @Override
    public List<Position> getSpectatorSpots() {
        return Lists.newArrayList(
                Position.of(convertX(3167), convertY(4460)),
                Position.of(convertX(3166), convertY(4460)),
                Position.of(convertX(3166), convertY(4433)),
                Position.of(convertX(3167), convertY(4433))
        );
    }

    @Override
    public Position getEntrance() {
        return Position.of(3219, 4458);
    }

//    public void addBloodPit(Position pitLocation, boolean object) {
//        if (!getBloodPitLocations().contains(pitLocation)) {
//            getBloodPitLocations().add(pitLocation);
//            if (object) {
//                final GameObject pitObject = new GameObject(32984, pitLocation, 10, 0);
//
//                ObjectBuilder.add(pitObject);
//                GameWorld.submit(30, pitObject, () -> {
//                    getBloodPitLocations().remove(pitLocation);
//                    ObjectBuilder.remove(pitObject);
//                });
//            } else {
//                Graphic.send(1579, pitLocation);
//                GameObject.spawn(pitObject);
//                GameWorld.submit(10, () -> getBloodPitLocations().remove(pitLocation));
//            }
//        }
//    }

}

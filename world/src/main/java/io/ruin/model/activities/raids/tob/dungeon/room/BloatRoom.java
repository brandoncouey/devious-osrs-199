package io.ruin.model.activities.raids.tob.dungeon.room;

import com.google.common.collect.Lists;
import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.cache.NpcID;
import io.ruin.model.activities.raids.tob.dungeon.RoomType;
import io.ruin.model.activities.raids.tob.party.PartyStatus;
import io.ruin.model.activities.raids.tob.party.TheatreParty;
import io.ruin.model.activities.raids.tob.party.TheatrePartyManager;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.utility.Broadcast;

import java.util.List;

/**
 * @author NuLL on 28/12/2021
 * https://www.rune-server.ee/members/null1001/
 * @project Devious
 */
public class BloatRoom extends TheatreRoom {//Room 2

    private NPC bloat;
    public Player player;
    public BloatRoom(TheatreParty party) {
        super(party);
    }

    @Override
    public void onLoad() {
        build(13125, 1);
        new GameObject(13441, convertX(3281), convertY(4446), 0, 11, 3);
        bloat = new NPC(NpcID.PESTILENT_BLOAT).spawn(convertX(3299), convertY(4448), 0, Direction.SOUTH, 10);
        bloat.setIgnoreMulti(true);
            bloat.deathStartListener = (DeathListener.SimpleKiller) killer -> {
                party.forPlayers(p -> {
                    p.theatreOfBloodStage = 2;
                    p.sendFilteredMessage("You have completed stage 2!");
                    p.tobDeaths = 0;
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
                bloat.getCombat().setAllowRespawn(false);
            };
    }

    @Override
    public void registerObjects() {
//        for (int y = 4446; y < 4449; y++) {
//            ObjectAction.register(32755, convertX(3304), convertY(y), 0, "pass", (player, obj) -> {
//                boolean west = player.getAbsX() > convertX(3304);
//                Direction dir = west ? Direction.WEST : Direction.EAST;
//                if (!player.getCombat().isDefending(25)) {
//                    player.getMovement().force(west ? -1 : 1, 0, 0, 0, 10, 30, dir);
//                } else {
//                    player.sendMessage("You can't pass this barrier while in combat.");
//                }
//            });
//            ObjectAction.register(32755, convertX(3287), convertY(y), 0, "pass", (player, obj) -> {
//                boolean west = player.getAbsX() > convertX(3287);
//                Direction dir = west ? Direction.WEST : Direction.EAST;
//                if (!player.getCombat().isDefending(25)) {
//                    player.getMovement().force(west ? -1 : 1, 0, 0, 0, 10, 30, dir);
//                } else {
//                    player.sendMessage("You can't pass this barrier while in combat.");
//                }
//            });
//        }
    }

    @Override
    public List<Position> getSpectatorSpots() {
        return Lists.newArrayList(
                Position.of(convertX(3297), convertY(4435)),
                Position.of(convertX(3298), convertY(4435)),
                Position.of(convertX(3296), convertY(4435)),
                Position.of(convertX(3295), convertY(4435))

        );
    }

    @Override
    public Position getEntrance() {
        return Position.of(3321, 4448, 0);
    }

}

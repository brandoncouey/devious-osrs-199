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
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.utility.Broadcast;

import java.util.List;

/**
 * @author NuLL on 28/12/2021
 * https://www.rune-server.ee/members/null1001/
 * @project Devious
 */
public class VasiliasRoom extends TheatreRoom {//Room 3

    private NPC vasilias;

    public VasiliasRoom(TheatreParty party) {
        super(party);
    }

    @Override
    public void onLoad() {
        buildSw(13122, 1);
        new GameObject(13441, convertX(3298), convertY(4276), 0, 11, 0);
        vasilias = new NPC(NpcID.NYLOCAS_VASILIAS_8356).spawn(convertX(3294), convertY(4247), 0, Direction.EAST, 0);
        Tile.get(convertX(3289), convertY(4248), 0, true).destroy(); // unblock exit
        Tile.get(convertX(3289), convertY(4249), 0, true).destroy(); // unblock exit
        Tile.get(convertX(3302), convertY(4248), 0, true).destroy(); // unblock exit
        Tile.get(convertX(3302), convertY(4249), 0, true).destroy(); // unblock exit
        vasilias.getCombat().isAggressive();
        vasilias.setIgnoreMulti(true);
            vasilias.deathStartListener = (DeathListener.SimpleKiller) killer -> {
                party.forPlayers(p -> {
                    p.theatreOfBloodStage = 3;
                    p.sendFilteredMessage("You have completed stage 3!");
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
                vasilias.remove();
                vasilias.getCombat().setAllowRespawn(false);
            };
    }

    @Override
    public void registerObjects() {

    }

    @Override
    public List<Position> getSpectatorSpots() {
        return Lists.newArrayList(
                //TODO
        );
    }

    @Override
    public Position getEntrance() {
        return Position.of(3296, 4283);
    }

}

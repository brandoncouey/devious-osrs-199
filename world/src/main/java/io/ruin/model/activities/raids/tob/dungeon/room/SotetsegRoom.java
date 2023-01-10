package io.ruin.model.activities.raids.tob.dungeon.room;

import com.google.common.collect.Lists;
import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.model.activities.raids.tob.dungeon.RoomType;
import io.ruin.model.activities.raids.tob.party.PartyStatus;
import io.ruin.model.activities.raids.tob.party.TheatreParty;
import io.ruin.model.activities.raids.tob.party.TheatrePartyManager;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
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
public class SotetsegRoom extends TheatreRoom {//Room 4

    private NPC sotetseg;

    public SotetsegRoom(TheatreParty party) {
        super(party);
    }

    @Override
    public void onLoad() {
        buildSw(13123, 1);
        new GameObject(13441, convertX(3282), convertY(4301), 0, 11, 0);
        sotetseg = new NPC(10868).spawn(convertX(3277), convertY(4328), 0, Direction.SOUTH, 0);
        sotetseg.setIgnoreMulti(true);
        sotetseg.getCombat().isAggressive();
        sotetseg.getCombat().setAllowRetaliate(true);
        sotetseg.deathStartListener = (DeathListener.SimpleKiller) killer -> {
            party.forPlayers(p -> {
                p.theatreOfBloodStage = 4;
                p.sendFilteredMessage("You have completed stage 4!");
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
            sotetseg.getCombat().setAllowRespawn(false);
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
        return Position.of(3279, 4293);
    }

}

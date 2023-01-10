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
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;

import java.util.List;

/**
 * @author NuLL on 28/12/2021
 * https://www.rune-server.ee/members/null1001/
 * @project Devious
 */
public class XarpusRoom extends TheatreRoom {//Room 5

    private NPC xarpus;

    public XarpusRoom(TheatreParty party) {
        super(party);
    }

    @Override
    public void onLoad() {
        buildSw(12612, 1);
        new GameObject(13441, convertX(3171), convertY(4396), 1, 11, 2);
        xarpus = new NPC(NpcID.XARPUS_8340).spawn(convertX(3170), convertY(4387), 1, Direction.EAST, 0);
        xarpus.setIgnoreMulti(true);
            xarpus.deathStartListener = (DeathListener.SimpleKiller) killer -> {
                party.forPlayers(p -> {
                    p.theatreOfBloodStage = 5;
                    p.sendFilteredMessage("You have completed stage 5!");
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
                xarpus.getCombat().setAllowRespawn(false);
            };
    }

    @Override
    public void registerObjects() {
        ObjectAction.register(32751, "enter", ((player, obj) -> {
            if (player.theatreOfBloodStage == 5) {
                TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).ifPresent(party ->
                        party.getDungeon().enterRoom(player, RoomType.VERZIK));
            }
        }));
    }

    @Override
    public List<Position> getSpectatorSpots() {
        return Lists.newArrayList(
                //TODO
        );
    }

    @Override
    public Position getEntrance() {
        return Position.of(3170, 4377, 1);
    }

}

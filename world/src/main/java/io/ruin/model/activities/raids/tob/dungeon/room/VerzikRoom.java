package io.ruin.model.activities.raids.tob.dungeon.room;

import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.cache.NpcID;
import io.ruin.model.activities.duelarena.DuelArena;
import io.ruin.model.activities.raids.tob.dungeon.RoomType;
import io.ruin.model.activities.raids.tob.party.PartyStatus;
import io.ruin.model.activities.raids.tob.party.TheatreParty;
import io.ruin.model.activities.raids.tob.party.TheatrePartyManager;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Direction;
import io.ruin.model.map.MapListener;
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
public class VerzikRoom extends TheatreRoom {//Room 6

    private NPC verzik;

    public Object throne;

    public VerzikRoom(TheatreParty party) {
        super(party);
    }

    @Override
    public void onLoad() {
        build(12611, 1);
        verzik = new NPC(NpcID.VERZIK_VITUR_8374).spawn(convertX(3165), convertY(4314), 0, Direction.SOUTH, 0);
        GameObject obj = GameObject.spawn(32737, convertX(3167), convertY(4324), 0, 10, 0);


        verzik.setIgnoreMulti(true);
        verzik.getCombat().setAllowRespawn(false);
        verzik.deathEndListener = (DeathListener.SimpleKiller) killer -> {
            verzik.startEvent(event -> {
                verzik.animate(8128);
                verzik.transform(8375);
                event.delay(6);
                verzik.remove();
            });
            party.forPlayers(p -> {
                p.theatreOfBloodStage = 6;
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
                p.sendMessage("You beat Theatre of blood! Congratulations! go claim your treasure!");
                p.theatreOfBloodKills.increment(p);
                p.tobreward = false;
            });
            verzik.getCombat().setAllowRespawn(false);
            obj.setId(32738);
        };
     }

    @Override
    public void registerObjects() {
        ObjectAction.register(32738, "enter", ((player, obj) -> {
            TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).ifPresent(party -> {
                party.getDungeon().enterRoom(player, RoomType.TREASURE);
            });
        }));
    }

    @Override
    public List<Position> getSpectatorSpots() {
        return null;
    }

    @Override
    public Position getEntrance() {
        return Position.of(3167, 4304, 0);
    }

}

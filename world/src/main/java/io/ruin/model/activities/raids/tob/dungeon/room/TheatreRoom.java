package io.ruin.model.activities.raids.tob.dungeon.room;

import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.model.activities.raids.tob.dungeon.RoomType;
import io.ruin.model.activities.raids.tob.dungeon.TheatreBoss;
import io.ruin.model.activities.raids.tob.party.PartyStatus;
import io.ruin.model.activities.raids.tob.party.TheatreParty;
import io.ruin.model.activities.raids.tob.party.TheatrePartyManager;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.journal.JournalTab;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.MultiZone;
import io.ruin.model.map.Position;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.utility.Broadcast;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author NuLL on 28/12/2021
 * https://www.rune-server.ee/members/null1001/
 * @project Devious
 */
public abstract class TheatreRoom extends DynamicMap {

    protected int lastSpectatorSpot;
    protected final TheatreParty party;
    protected TheatreBoss boss;

    protected TheatreRoom(TheatreParty party) {
        this.party = party;
    }

    //Returns the entrance spot for the room.
    public abstract Position getEntrance();

    //Called upon a room being loaded for the first time.
    public abstract void onLoad();

    //Used to register object listeners.
    public abstract void registerObjects();

    //Returns a list of spectator spots.
    public abstract List<Position> getSpectatorSpots();


    /**
     * Assigns the map listener for this area.
     *
     * @param player
     */
    public void assignMapListener(Player player) {
            System.out.println("Assigned map listener.");
        player.tobcannon = true;
        player.openInterface(InterfaceType.PRIMARY_OVERLAY, Interface.TOB_PARTY_MEMBERS_OVERLAY);
        player.deathEndListener = (DeathListener.Simple) () -> handleDeath(player);
            assignListener(player)
                    .onEnter(this::entered)
                    .onExit(this::exited);
    }


    public void handleDeath(Player player) {
        System.out.println("I'm printing I handled the death successfully.");
        player.getCombat().restore();
        player.getPacketSender().resetCamera();
        player.getCombat().setDead(false);
        player.unlock();
        party.forPlayers(pmember -> {
            TheatreParty tobparty = TheatrePartyManager.instance().getPartyForPlayer(pmember.getUserId()).get();
            if (pmember.tobDeaths >= 4) {
                tobparty.forPlayers(p -> {
                    p.getMovement().teleport(3665, 3219, 0);
                    p.deathEndListener = null;
                    p.tobDeaths = 0;
                    p.getCombat().reset();
                    if (p.getCombat().isDead()) {
                        System.out.println("Setting dead to false");
                        p.getCombat().setDead(false);
                        p.sendMessage("You can now move forward.");
                        p.animate(-1, 0);
                    }
                    p.sendMessage("Everyone has died! The raid has ended.");
                    p.unlock();
                    p.getCombat().setDead(false);
                    TheatreParty.updatePartyStatus(p, PartyStatus.NO_PARTY);
                    p.theatreOfBloodStage = 0;
                    p.tobDamage = 0;
                    p.theatreroom = "";
                    if (p.getInventory().contains(25961)) {
                        p.getInventory().remove(25961, Integer.MAX_VALUE);
                    }
                    p.tobcannon = false;
                    p.tobreward = false;
                });
                TheatrePartyManager.instance().deregister(tobparty);
            }
            tobparty.forPlayers(p -> {
                if (player.getGameMode().isHardcoreIronman()) {
                    player.hcimdeath = true;
                    Config.IRONMAN_MODE.set(player, 1);
                    Broadcast.WORLD.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.getName() + " has died as a Hardcore Ironman at Theatre of Blood!"));
                }
                if (player.getGameMode().isHardcoreGroupIronman()) {
                    player.hcgimdeath = true;
                    Config.IRONMAN_MODE.set(player, 4);
                    Broadcast.WORLD.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.getName() + " has died as a Group Hardcore Ironman at Theatre of Blood!"));
                }
                p.tobDeaths++;
                p.tobDamage = 0;
                p.getCombat().restore();
                p.getPacketSender().resetCamera();
                p.getCombat().reset();
                if (tobparty.getUsers().size() != 1) {
                    player.lock();
                    player.sendMessage("As a result of your death. You cannot move until the room is finished.");
                    if (player.theatreOfBloodStage == 0) {
                        TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).ifPresent(party ->
                                party.getDungeon().enterRoom(player, RoomType.MAIDEN));
                        player.theatreroom = "maiden";
                        player.getCombat().reset();
                    } else if (player.theatreOfBloodStage == 1) {
                        TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).ifPresent(party ->
                                party.getDungeon().enterRoom(player, RoomType.BLOAT));
                        player.theatreroom = "bloat";
                        player.getCombat().reset();
                    } else if (player.theatreOfBloodStage == 2) {
                        TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).ifPresent(party ->
                                party.getDungeon().enterRoom(player, RoomType.VASILIAS));
                        player.theatreroom = "vasilias";
                        player.getCombat().reset();
                    } else if (player.theatreOfBloodStage == 3) {
                        TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).ifPresent(party ->
                                party.getDungeon().enterRoom(player, RoomType.SOTETSEG));
                        player.theatreroom = "sotetseg";
                        player.getCombat().reset();
                    } else if (player.theatreOfBloodStage == 4) {
                        TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).ifPresent(party ->
                                party.getDungeon().enterRoom(player, RoomType.XARPUS));
                        player.theatreroom = "xarpus";
                        player.getCombat().reset();
                    }
                } else if (tobparty.getUsers().size() <= 1) {
                    player.unlock();
                    player.getMovement().teleport(3665, 3219, 0);
                    player.deathEndListener = null;
                    player.tobDeaths = 0;
                    player.tobDamage = 0;
                    player.sendMessage("Everyone has died! The raid has ended.");
                        TheatreParty.updatePartyStatus(p, PartyStatus.NO_PARTY);
                    player.getCombat().reset();
                        if (player.getCombat().isDead()) {
                            System.out.println("Setting dead to false");
                            player.getCombat().setDead(false);
                            player.tobDamage = 0;
                            player.sendMessage("You can now move forward.");
                            player.animate(-1, 0);
                        }
                    player.theatreOfBloodStage = 0;
                    player.theatreroom = "";
                        if (player.getInventory().contains(25961))
                            player.getInventory().findItem(25961).remove();
                    player.tobreward = false;
                    player.tobcannon = false;
                    player.tobDamage = 0;
                    TheatrePartyManager.instance().deregister(tobparty);
                    }
                });
        });
    }

    public void entered(Player player) {
        System.out.println("I'm printing that I entered a listening map.");
      //  player.deathEndListener = (DeathListener.Simple) () -> handleDeath(player);
        player.tobcannon = true;
        player.openInterface(InterfaceType.PRIMARY_OVERLAY, Interface.TOB_PARTY_MEMBERS_OVERLAY);
       }

    public void exited(Player player, boolean logout) {
        System.out.println("I'm printing that I exited a listening map.");
        if (logout) {
            player.getMovement().teleport(3675, 3219, 0);
            player.tobcannon = false;
            player.tobDamage = 0;
        } else {
            player.tobDamage = 0;
            player.getPacketSender().resetCamera();
            player.tobcannon = false;
            player.closeInterface(InterfaceType.PRIMARY_OVERLAY);
        }
        //player.deathEndListener = null;
    }
    /**
     * Adds multi to this area.
     */
    public void addMultiArea() {
        Stream.of(getRegions()).filter(Objects::nonNull).forEach(region -> MultiZone.add(region.bounds));
    }

    public void onDeath(TheatreParty party) {
        party.forPlayers(p -> {
            p.getCombat().restore();
            p.getPacketSender().resetCamera();
            p.getCombat().setDead(false);
            p.unlock();
        });
    }

    /**
     * Removes multi to this area.
     */
    public void removeMultiArea() {
        Stream.of(getRegions()).filter(Objects::nonNull).forEach(region -> MultiZone.remove(region.bounds));
    }

    /**
     * Handles a player's death while in this area.
     *
     * @param player
     */
    public void handlePlayerDeath(Player player) {
        player.getMovement().teleport(getSpectatorSpots().get(lastSpectatorSpot));
        lastSpectatorSpot++;
    }

}

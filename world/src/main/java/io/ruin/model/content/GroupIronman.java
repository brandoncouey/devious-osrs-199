package io.ruin.model.content;

import com.google.common.collect.Lists;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAction;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Data
public class GroupIronman {

    public static final Bounds INVITE_ZONE = new Bounds(3085, 3003, 3119, 3048, 0);
    public Optional<GIMRepository.GroupIMData> saveData;
    // public static final Bounds INVITE_ZONE = new Bounds(3325, 3200, 3391, 3286, 0); //Duel arena test

    public GroupIronman(Player player) {
        this.player = player;
    }

    public final Player player;
    public String leader;
    public int lives;
    public List<String> teamates = Lists.newArrayList();
    public IronmanGroupRequest request;

    public void createTeam() {
        if (player.getGroupIronman().getLeader() == null) {
            player.getGroupIronman().setLeader(player.getName());
            player.getGroupIronman().getTeamates().add(player.getName());
            player.getGroupIronman().setLives(5);
            player.sendMessage("<col=107900>You've created a Group Ironman team.");
        } else {
            player.sendMessage("<col=107900>You're already locked into a group.");
        }
    }

    public boolean invite(Player other) {
        long ms = System.currentTimeMillis(); //ew oh well
        long delay = player.inviteDelay - ms;
        int delaySeconds; //be sure this is set in ascending order.

        if (delay > 0) {
            long seconds = delay / 1000L;
            if (seconds <= 1)
                player.sendMessage("You need to wait 1 more second before inviting again.");
            else
                player.sendMessage("You need to wait " + seconds + " more seconds before inviting again.");
            return false;
        }

        if (other.isVisibleInterface(Interface.SHOP) || other.isVisibleInterface(Interface.TRADE_SCREEN) || other.isVisibleInterface(Interface.TRADE_CONFIRMATION) || other.isVisibleInterface(Interface.BANK) || other.isVisibleInterface(Interface.NPC_DIALOGUE) || other.isVisibleInterface(Interface.PLAYER_DIALOGUE)) {
            player.sendMessage("This player is currently busy.");
            return false;
        }

        if (!other.getPosition().inBounds(INVITE_ZONE) || !player.getPosition().inBounds(INVITE_ZONE)) {
            player.sendMessage("Both players must be in Edgeville to invite someone to your team.");
            return false;
        }
        if (other.isLocked()) {
            player.sendMessage("This player is currently busy.");
            return false;
        }
        if (other.getStats().totalLevel > 50) {
            player.sendMessage("You can't invite this player, they need to be below 50 total level.");
            return false;
        }
        if (!other.isGroupIronman()) {
            player.sendMessage("This player isn't a group Ironman.");
            return false;
        }
        if (other.getGroupIronman().hasPendingRequest()) {
            player.sendMessage("This player already has a pending request.");
            return false;
        }
        if (getLeader() == null) {
            leader = player.getGroupIronman().getLeader();
            if (leader == null) {
                createTeam();
            }
        }
        if (!player.getName().equalsIgnoreCase(leader)) {
            player.sendMessage("You have to be group leader to invite others.");
            return false;
        }
        if (isTeamFull()) {
            player.sendMessage("Your team is already full.");
            return false;
        }
        if (isTeamate(other)) {
            player.sendMessage("This player is already on your team.");
            return false;
        }
        if (other.getGroupIronman().hasTeam()) {
            player.sendMessage("This player already has a team.");
            return false;
        }
        delaySeconds = 20;
        player.inviteDelay = ms + (delaySeconds * 1000L);
        //player.lock();
        other.getGroupIronman().sendRequest(new IronmanGroupRequest(player, other));
        player.dialogue(
                new MessageDialogue("Invitation sent. Awaiting response.."
                ).hideContinue()
        );
        other.lock();
        other.dialogue(
                new MessageDialogue(player.getName() + " has invited you to their Group Ironman team. Would you like to join? This cannot be undone."),
                new OptionsDialogue(
                        new Option("Yes, I'll join", () -> {
                            other.getGroupIronman().request.accept();
                            enteredZone(other);
                            other.unlock();
                            player.closeInterfaces();
                            player.unlock();
                        }),
                        new Option("No thanks", () -> {
                            other.getGroupIronman().setRequest(null);
                            other.closeInterfaces();
                            player.sendMessage(other.getName() + " has declined your invitation.");
                            other.unlock();
                            player.unlock();
                            player.closeInterfaces();
                        })
                )
        );
        return true;
    }

    private boolean hasTeam() {
        return getTeamates().stream().filter(name -> name != null && !name.equalsIgnoreCase(player.getName())).count() > 0;
    }

    /**
     * @param groupRequest
     */
    private void sendRequest(IronmanGroupRequest groupRequest) {
        groupRequest.getReciever().getGroupIronman().setRequest(groupRequest);
    }

    public boolean hasPendingRequest() {
        return getRequest() != null;
    }

    public boolean isTeamate(String name) {
        return getTeamates().stream().anyMatch(other -> name.equalsIgnoreCase(other));
    }

    public boolean isTeamate(Player other) {
        for (int i = 0; i < teamates.size(); i++) {
            if (teamates.get(i).equalsIgnoreCase(other.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns if the group is full or not.
     *
     * @return
     */
    public boolean isTeamFull() {
        return getTeamates().size() >= 4;
    }

    public void setTeam(String[] names) {
        if (names != null) {
            teamates = Lists.newArrayList();
            Arrays.stream(names).forEach(teamates::add);
        }
    }

    public void setLives(int lives) {
        if (lives != -1) {
            teamates = Lists.newArrayList();
        }
    }

    /**
     * Returns if the player is the group leader.
     *
     * @return
     */
    public boolean isLeader() {
        return player.getName().equalsIgnoreCase(leader) || leader == null;
    }

    private static void enteredZone(Player player) {
        if (player.isGroupIronman() && player.getGroupIronman().isLeader() && !player.getGroupIronman().isTeamFull()) {
            player.setAction(1, PlayerAction.GIM_Invite);
        }
    }

    private static void exitedZone(Player player, boolean logout) {
        if (player.isGroupIronman()) {
            if (!logout) {
                player.setAction(1, null);
            }
        }
    }

    static {
        try {
            MapListener.registerBounds(INVITE_ZONE)
                    .onEnter(GroupIronman::enteredZone)
                    .onExit(GroupIronman::exitedZone);
        } catch (Throwable e) {
            System.out.println(e);
        }
    }

}

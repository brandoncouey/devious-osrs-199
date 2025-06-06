package io.ruin.model.skills.construction;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import lombok.Getter;

import java.util.Locale;
@Getter
public enum HouseLocation {
    EDGEVILLE(15477, 1, 10000, new Position(3099, 3503, 0)), // same as taverly
    TAVERLEY(15477, 10, 10000, new Position(2893, 3465, 0)),
    POLLNIVNEACH(15479, 20, 15000, new Position(3340, 3003, 0)),
    GREAT_KOUREND(28822, 25, 17500, new Position(1742, 3517, 0)),
    RELLEKKA(15480, 30, 20000, new Position(2670, 3631, 0)),
    BRIMHAVEN(15481, 40, 30000, new Position(2757, 3178, 0)),
    YANILLE(15482, 50, 50000, new Position(2544, 3096, 0));

    private final int portalId;
    private final int levelReq;
    private final int cost;
    private final Position position;

    HouseLocation(int portalId, int levelReq, int cost, Position position) {
        this.portalId = portalId;
        this.levelReq = levelReq;
        this.cost = cost;
        this.position = position;
    }

    public int getPortalId() {
        return portalId;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public int getCost() {
        return cost;
    }

    public Position getPosition() {
        return position;
    }

    public void home(Player player, boolean buildingMode) {
        if (player.house == null) {
            player.dialogue(new MessageDialogue("You don't own a house. Visit the estate agent in Edgeville to purchase one."));
            return;
        }
        player.house.buildAndEnter(player, buildingMode);
    }

    public void friendsHouse(Player player, String friend) {
        if (player.getGameMode().isIronMan() && !player.isGroupIronman()) {
            player.dialogue(new MessageDialogue("Ironmen cannot enter other players' houses."));
            return;
        }
        if (player.isGroupIronman()) {
            if (!player.getGroupIronman().isTeamate(friend.toLowerCase(Locale.ROOT))) {
                player.dialogue(new MessageDialogue("Group Ironmen cannot enter other players' houses."));
                return;
            }
        }
        Player other = World.getPlayer(friend);
        if (other == null) {
            player.dialogue(new MessageDialogue("Player not found.<br>Make sure you typed their name correctly and that they are online."));
            return;
        }
        if (other == player) { // :thinking:
            home(player, false);
            return;
        }
        if (other.house == null) {
            player.dialogue(new MessageDialogue(other.getName() + " is homeless."));
            return;
        }
        if (!other.isInOwnHouse()) {
            player.dialogue(new MessageDialogue(other.getName() + " isn't home right now."));
            return;
        }
        if (other.house.getLocation() != this) {
            player.dialogue(new MessageDialogue(other.getName() + "'s house is not located here."));
            return;
        }
        if (other.house.isBuildingMode()) {
            player.dialogue(new MessageDialogue(other.getName() + " is currently in building mode."));
            return;
        }
        if (other.house.isLocked()) {
            player.dialogue(new MessageDialogue(other.getName() + "'s house is currently locked."));
            return;
        }
        other.house.guestEnter(player, null);
    }

    static {
        for (HouseLocation houseLocation : values()) {
            ObjectAction.register(houseLocation.getPortalId(), 2, (player, obj) -> {
                houseLocation.home(player, false);
            });
            ObjectAction.register(houseLocation.getPortalId(), 3, (player, obj) -> {
                houseLocation.home(player, true);
            });
            ObjectAction.register(houseLocation.getPortalId(), 4, (player, obj) -> {
                player.nameInput("Enter friend's name:", name -> houseLocation.friendsHouse(player, name));
            });
            ObjectAction.register(houseLocation.getPortalId(), 1, (player, obj) -> {
//                if (obj.getPosition() != player.house.getLocation().getPosition()) {
//                    player.dialogue(new MessageDialogue("You don't have a house here.<br><br>Your house is currently located at the " + player.house.getLocation().getName() + " portal."));
//                    return;
//                }
                player.dialogue(new OptionsDialogue("Choose an option",
                        new Option("Enter your house", () -> houseLocation.home(player, false)),
                        new Option("Enter your house in building mode", () -> houseLocation.home(player, true)),
                        new Option("Enter a friend's house", () -> {
                            player.nameInput("Enter friend's name:", name -> houseLocation.friendsHouse(player, name));
                        })));
            });
        }
    }

    public String getName() {
        return StringUtils.fixCaps(name().replace("_", " "));
    }
}

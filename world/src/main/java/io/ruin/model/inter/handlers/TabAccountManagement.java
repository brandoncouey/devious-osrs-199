package io.ruin.model.inter.handlers;

import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.entity.player.SecondaryGroup;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

public class TabAccountManagement {

    private static final int OSPVP_CREDITS = 13190;
    private static final String FORUM_INBOX_URL = "https://deviousps.com/forums/index.php?conversations/";
    private static final String VOTE_URL = World.type.getWebsiteUrl() + "/vote";
    private static final String HISCORES = World.type.getWebsiteUrl() + "/highscores";

    static {
        /**
         * Send the interface when our player logs in
         */
        LoginListener.register(player -> player.getPacketSender().sendAccountManagement(getDonatorRank(player), getUsername(player), player.getUnreadPMs()));

        /**
         * Interface buttons
         */
        InterfaceHandler.register(Interface.ACCOUNT_MANAGEMENT, h -> {
            h.actions[26] = (SimpleAction) p -> p.dialogue(
                    new OptionsDialogue("Would you like to open our store?",
                            new Option("Yes", () -> p.openUrl(World.type.getWorldName() + " Store", World.type.getWebsiteUrl() + "/store")),
                            new Option("No", p::closeDialogue)
                    )
            );
            h.actions[31] = (SimpleAction) p -> p.dialogue(
                    new OptionsDialogue("Would you like to open the credit purchase page?",
                            new Option("Yes", () -> p.openUrl(World.type.getWorldName() + " Store", World.type.getWebsiteUrl() + "/store")),
                            new Option("No", p::closeDialogue)
                    )
            );
            h.actions[38] = (SimpleAction) p -> p.dialogue(
                    new PlayerDialogue("This feature is only available for staff members.")
            );
            h.actions[45] = (SimpleAction) p -> p.dialogue(new PlayerDialogue("To change your in-game name you will need to submit a ticket on discord."));
            h.actions[60] = (SimpleAction) p -> p.dialogue(new PlayerDialogue("Any and all polls are located on our discord."));
            h.actions[67] = (SimpleAction) p -> p.dialogue(new PlayerDialogue("Any and all polls are located on our discord."));
            h.actions[72] = (SimpleAction) p -> p.dialogue(new PlayerDialogue("Any and all polls are located on our discord."));
            h.actions[80] = (SimpleAction) p -> p.openUrl("Runescape Wikipedia", "https://oldschool.runescape.wiki");
            h.actions[81] = (SimpleAction) p -> {
                for (Player player : World.players) {
                    if (player.isStaff()) {
                        player.sendMessage(Color.BABY_BLUE, p.getName() + " is requesting support with a subject.");
                    }
                }
                p.sendFilteredMessage("Your request has been noted and a member of staff will be in touch shortly.");
            };
//            h.actions[82] = (SimpleAction) p -> p.getMovement().teleport(1747,3598,0);
            h.actions[83] = (SimpleAction) p -> p.openUrl("Devious Hiscores", "https://deviousps.com");
            h.actions[84] = (SimpleAction) p -> p.openUrl("Runescape Wikipedia", "https://oldschool.runescape.wiki");
            h.actions[85] = (SimpleAction) p -> p.openUrl("Devious Store", "https://deviousps.com");

        });
    }

    public static String getUsername(Player player) {
        PlayerGroup clientGroup = player.getClientGroup();
        return (clientGroup.clientImgId != -1 ? clientGroup.tag() + " " : "") + player.getName();
    }

    public static String getDonatorRank(Player player) {
        if (player.isADonator()) {
            return SecondaryGroup.DIAMOND.tag() + " Diamond";
        } else if (player.isGroups(SecondaryGroup.RUBY)) {
            return SecondaryGroup.RUBY.tag() + " Ruby";
        } else if (player.isGroups(SecondaryGroup.EMERALD)) {
            return SecondaryGroup.EMERALD.tag() + " Emerald";
        } else if (player.isGroups(SecondaryGroup.SAPPHIRE)) {
            return SecondaryGroup.SAPPHIRE.tag() + " Sapphire";
        } else if (player.isGroups(SecondaryGroup.RED_TOPAZ)) {
            return SecondaryGroup.RED_TOPAZ.tag() + " Red Topaz";
        } else if (player.isGroups(SecondaryGroup.OPAL)) {
            return SecondaryGroup.OPAL.tag() + " Opal";
        } else {
            return "Unranked";
        }
    }

}

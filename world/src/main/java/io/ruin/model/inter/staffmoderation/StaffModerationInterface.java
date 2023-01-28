package io.ruin.model.inter.staffmoderation;

import io.ruin.cache.Icon;
import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAction;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.item.containers.Inventory;
import io.ruin.model.item.containers.bank.Bank;
import io.ruin.model.item.containers.bank.BankItem;
import io.ruin.services.Punishment;
import io.ruin.utility.Broadcast;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.//jda.api.EmbedBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

import static io.ruin.services.discord.DiscordConnection.jda;

public class StaffModerationInterface {

    public static int INTERFACE_ID = 835;

    @Getter
    @Setter
    public static String playerSelected;
    @Getter
    @Setter
    public static int actionSelected;
    @Getter
    @Setter
    public static String actionSelectedString;


    public static int playerString = 19;
    public static int actionString = 20;

    public static void setActionModerate(Player player) {
        if (!player.isModerator()) {
            return;
        }
        player.setAction(6, PlayerAction.MODERATE);
    }

    public static void openStaffModeration(Player player, Player p2) {
        if (!player.isModerator()) {
            player.sendMessage("You don't have access to this interface.");
            return;
        }
        setPlayerSelected(p2.getName());
        player.getPacketSender().sendString(INTERFACE_ID, playerString, p2.getName());
        player.openInterface(InterfaceType.MAIN, INTERFACE_ID);
    }

    public static void handleActions(Player player, int id) {
        switch (id) {
            case 24://cancel
                player.closeInterface(InterfaceType.MAIN);
                setPlayerSelected("");
                setActionSelected(0);
                setActionSelectedString("");
                break;
            case 26://confirm
                player.closeInterface(InterfaceType.MAIN);
                switch (getActionSelected()) {
                    case 29: // tele-to
                        for (Player p2 : World.players)
                            if (p2.getName().equalsIgnoreCase(getPlayerSelected())) {
                                player.getMovement().teleport(p2.getPosition());
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                Date date = new Date();
                                EmbedBuilder eb = new EmbedBuilder();
                                eb.setTitle("Tele-to has been used!");
                                eb.addField("Username: ", player.getName(), true);
                                eb.addField("Target: ", p2.getName(), true);
                                eb.addField("When: ", formatter.format(date), true);
                                eb.setColor(new java.awt.Color(0xB00D03));
                                //jda.getTextChannelById("991831249637351474").sendMessage(eb.build()).queue();

                            }
                        break;
                    case 31://tele-to-me
                        for (Player p2 : World.players)
                            if (p2.getName().equalsIgnoreCase(getPlayerSelected())) {
                                p2.getMovement().teleport(player.getPosition());
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                Date date = new Date();
                                EmbedBuilder eb = new EmbedBuilder();
                                eb.setTitle("Tele-to-me has been used!");
                                eb.addField("Username: ", player.getName(), true);
                                eb.addField("Target: ", p2.getName(), true);
                                eb.addField("When: ", formatter.format(date), true);
                                eb.setColor(new java.awt.Color(0xB00D03));
                                //jda.getTextChannelById("991831249637351474").sendMessage(eb.build()).queue();

                            }
                        break;
                    case 33://telehome
                        for (Player p2 : World.players)
                            if (p2.getName().equalsIgnoreCase(getPlayerSelected())) {
                                p2.getMovement().teleport(World.HOME);
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                Date date = new Date();
                                EmbedBuilder eb = new EmbedBuilder();
                                eb.setTitle("Tele-Home has been used!");
                                eb.addField("Username: ", player.getName(), true);
                                eb.addField("Target: ", p2.getName(), true);
                                eb.addField("When: ", formatter.format(date), true);
                                eb.setColor(new java.awt.Color(0xB00D03));
                                //jda.getTextChannelById("991831249637351474").sendMessage(eb.build()).queue();

                            }
                        break;
                    case 35://tele last tp?
                        for (Player p2 : World.players)
                            if (p2.getName().equalsIgnoreCase(getPlayerSelected())) {
                                p2.getMovement().teleport(p2.lastTeleport);
                            }
                        break;
                    case 38://check inventory
                        if (!player.isAdmin()) {
                            player.sendMessage("Your Request has been noted, and you do not have access to this feature.");
                            return;
                        }
                        for (Player p2 : World.players)
                            if (p2.getName().equalsIgnoreCase(getPlayerSelected())) {
                                player.dialogue(new OptionsDialogue("Viewing a players inventory will clear yours.",
                                        new Option("View " + p2.getName() + " inventory.", () -> {
                                            Inventory inventory = p2.getInventory();
                                            player.getInventory().clear();
                                            for (Item item : inventory.getItems()) {
                                                if (item == null)
                                                    continue;
                                                player.getInventory().add(item);
                                            }
                                        }),
                                        new Option("No, thanks."))
                                );
                            }
                        break;
                    case 40://check equipment?
                        if (!player.isAdmin()) {
                            player.sendMessage("Your Request has been noted, and you do not have access to this feature.");
                            return;
                        }
                        for (Player p2 : World.players)
                            if (p2.getName().equalsIgnoreCase(getPlayerSelected())) {
                                player.dialogue(new OptionsDialogue("Viewing a players equipment will clear yours.",
                                        new Option("View " + p2.getName() + " equipment.", () -> {
                                            Equipment equipment = p2.getEquipment();
                                            player.getEquipment().clear();
                                            for (Item item : equipment.getItems()) {
                                                if (item == null)
                                                    continue;
                                                player.getEquipment().equip(item.copy());
                                            }
                                        }),
                                        new Option("No, thanks."))
                                );
                            }
                        break;
                    case 47://Kick
                        for (Player p2 : World.players)
                            if (p2.getName().equalsIgnoreCase(getPlayerSelected())) {
                                Punishment.kick(player, p2);
                            }
                        break;
                    case 49://jail (integer input for time jail)
                        for (Player p2 : World.players)
                            if (p2.getName().equalsIgnoreCase(getPlayerSelected())) {
                                player.integerInput("How many ores do you wish this player to mine", ores -> {
                                    Punishment.jail(player, p2, ores);
                                });
                            }
                        break;
                    case 51://unmute
                        for (Player p2 : World.players)
                            if (p2.getName().equalsIgnoreCase(getPlayerSelected())) {
                                Punishment.unmute(player, p2);
                            }
                        break;
                    case 53://kill
                        for (Player p2 : World.players)
                            if (p2.getName().equalsIgnoreCase(getPlayerSelected())) {
                                p2.hit(new Hit().fixedDamage(p2.getHp()));
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                Date date = new Date();
                                EmbedBuilder eb = new EmbedBuilder();
                                eb.setTitle("Kill has been used!");
                                eb.addField("Username: ", player.getName(), true);
                                eb.addField("Target: ", p2.getName(), true);
                                eb.addField("When: ", formatter.format(date), true);
                                eb.setColor(new java.awt.Color(0xB00D03));
                                //jda.getTextChannelById("991831249637351474").sendMessage(eb.build()).queue();

                            }
                        break;
                    case 56://temp mute
                    case 58://perm mute
                        for (Player p2 : World.players)
                            if (p2.getName().equalsIgnoreCase(getPlayerSelected())) {
                                Punishment.mute(player, p2, 999999999, true);

//                                Broadcast.WORLD.sendNews(Icon.BLUE_INFO_BADGE, "User: " + p2.getName() + " Has been flicked out of the server!");
                            }
                        break;
                    case 60://perm ban
                        for (Player p2 : World.players)
                            if (p2.getName().equalsIgnoreCase(getPlayerSelected())) {
                                Punishment.ban(player, p2);
                                //        CentralClient.reloadBans();
                                Broadcast.WORLD.sendNews(Icon.BLUE_INFO_BADGE, "User: " + p2.getName() + " Has been flicked out of the server!");
                            }
                        break;
                    case 62://mac ban (flick command)
                        for (Player p2 : World.players)
                            if (p2.getName().equalsIgnoreCase(getPlayerSelected())) {
                                Punishment.uuidBan(player, p2);
                                //        CentralClient.reloadBans();
                                Broadcast.WORLD.sendNews(Icon.BLUE_INFO_BADGE, "User: " + p2.getName() + " Has been flicked out of the server!");
                            }
                        break;
                    case 42://check bank
                        if (player.isAdmin()) {
                            for (Player p2 : World.players)
                                if (p2.getName().equalsIgnoreCase(getPlayerSelected())) {
                                    player.dialogue(new OptionsDialogue("Viewing a players bank will clear yours.",
                                            new Option("View " + p2.getName() + " bank.", () -> {
                                                Bank targetBank = p2.getBank();
                                                player.getBank().clear();
                                                for (BankItem item : targetBank.getItems()) {
                                                    if (item == null)
                                                        continue;
                                                    player.getBank().add(item);
                                                }
                                                player.getBank().open();
                                            }),
                                            new Option("No, thanks."))
                                    );
                                }
                        } else {
                            player.sendMessage("Your Request has been noted, and you do not have access to this feature.");
                        }
                        break;
                }
                break;
            case 29://tele-to
                setActionSelected(29);
                setActionSelectedString("Tele-to");
                player.getPacketSender().sendString(INTERFACE_ID, actionString, "Tele-to");
                player.openInterface(InterfaceType.MAIN, INTERFACE_ID);
                break;
            case 31://tele-to-me
                setActionSelected(31);
                setActionSelectedString("Tele-to-me");
                player.openInterface(InterfaceType.MAIN, INTERFACE_ID);
                player.getPacketSender().sendString(INTERFACE_ID, actionString, getActionSelectedString());
                break;
            case 33://tele-home
                setActionSelected(33);
                setActionSelectedString("Tele home");
                player.openInterface(InterfaceType.MAIN, INTERFACE_ID);
                player.getPacketSender().sendString(INTERFACE_ID, actionString, getActionSelectedString());
               break;
            case 35://tele last tp
                setActionSelected(35);
                setActionSelectedString("Tele last tp");
                player.openInterface(InterfaceType.MAIN, INTERFACE_ID);
                player.getPacketSender().sendString(INTERFACE_ID, actionString, getActionSelectedString());
                break;
            case 38://check inv
                setActionSelected(38);
                setActionSelectedString("Check inventory");
                player.openInterface(InterfaceType.MAIN, INTERFACE_ID);
                player.getPacketSender().sendString(INTERFACE_ID, actionString, getActionSelectedString());
                break;
            case 40://check equip
                setActionSelected(40);
                setActionSelectedString("Check Equipment");
                player.openInterface(InterfaceType.MAIN, INTERFACE_ID);
                player.getPacketSender().sendString(INTERFACE_ID, actionString, getActionSelectedString());
                break;
            case 42://check bank
                setActionSelected(42);
                setActionSelectedString("Check Bank");
                player.openInterface(InterfaceType.MAIN, INTERFACE_ID);
                player.getPacketSender().sendString(INTERFACE_ID, actionString, getActionSelectedString());
                break;
            case 44://check looting bag (not in use)
            case 71://not in use
            case 69:
            case 67:
            case 65:
                player.closeInterface(InterfaceType.MAIN);
                player.sendMessage("Not in use.");
                break;
            case 47://kick
                setActionSelected(47);
                setActionSelectedString("Kick");
                player.openInterface(InterfaceType.MAIN, INTERFACE_ID);
                player.getPacketSender().sendString(INTERFACE_ID, actionString, getActionSelectedString());
                break;
            case 49://jail
                setActionSelected(49);
                setActionSelectedString("Jail");
                player.openInterface(InterfaceType.MAIN, INTERFACE_ID);
                player.getPacketSender().sendString(INTERFACE_ID, actionString, getActionSelectedString());
                break;
            case 51://unmute
                setActionSelected(51);
                setActionSelectedString("Unmute");
                player.openInterface(InterfaceType.MAIN, INTERFACE_ID);
                player.getPacketSender().sendString(INTERFACE_ID, actionString, getActionSelectedString());
                break;
            case 53://kill
                setActionSelected(53);
                setActionSelectedString("Kill");
                player.openInterface(InterfaceType.MAIN, INTERFACE_ID);
                player.getPacketSender().sendString(INTERFACE_ID, actionString, getActionSelectedString());
                break;
            case 56://temp mute
                setActionSelected(56);
                setActionSelectedString("Temp mute");
                player.openInterface(InterfaceType.MAIN, INTERFACE_ID);
                player.getPacketSender().sendString(INTERFACE_ID, actionString, getActionSelectedString());
                break;
            case 58://perm mute
                setActionSelected(58);
                setActionSelectedString("Perm mute");
                player.openInterface(InterfaceType.MAIN, INTERFACE_ID);
                player.getPacketSender().sendString(INTERFACE_ID, actionString, getActionSelectedString());
                break;
            case 60://perm ban
                setActionSelected(60);
                setActionSelectedString("Perm ban");
                player.openInterface(InterfaceType.MAIN, INTERFACE_ID);
                player.getPacketSender().sendString(INTERFACE_ID, actionString, getActionSelectedString());
                break;
            case 62://mac ban
                setActionSelected(62);
                setActionSelectedString("Mac ban");
                player.openInterface(InterfaceType.MAIN, INTERFACE_ID);
                player.getPacketSender().sendString(INTERFACE_ID, actionString, getActionSelectedString());
                break;

        }
    }

    static {
        LoginListener.register(StaffModerationInterface::setActionModerate);

        InterfaceHandler.register(INTERFACE_ID, h -> {

            h.actions[24] = (DefaultAction) (player, option, slot, itemId) ->
                    handleActions(player, 24);

            h.actions[26] = (DefaultAction) (player, option, slot, itemId) ->
                    handleActions(player, 26);

            h.actions[29] = (DefaultAction) (player, option, slot, itemId) ->
                    handleActions(player, 29);

            h.actions[31] = (DefaultAction) (player, option, slot, itemId) ->
                    handleActions(player, 31);

            h.actions[33] = (DefaultAction) (player, option, slot, itemId) ->
                    handleActions(player, 33);

            h.actions[35] = (DefaultAction) (player, option, slot, itemId) ->
                    handleActions(player, 35);

            h.actions[38] = (DefaultAction) (player, option, slot, itemId) ->
                    handleActions(player, 38);

            h.actions[40] = (DefaultAction) (player, option, slot, itemId) ->
                    handleActions(player, 40);

            h.actions[42] = (DefaultAction) (player, option, slot, itemId) ->
                    handleActions(player, 42);

            h.actions[44] = (DefaultAction) (player, option, slot, itemId) ->
                    handleActions(player, 44);

            h.actions[47] = (DefaultAction) (player, option, slot, itemId) ->
                    handleActions(player, 47);

            h.actions[49] = (DefaultAction) (player, option, slot, itemId) ->
                    handleActions(player, 49);

            h.actions[51] = (DefaultAction) (player, option, slot, itemId) ->
                    handleActions(player, 51);

            h.actions[53] = (DefaultAction) (player, option, slot, itemId) ->
                    handleActions(player, 53);

            h.actions[56] = (DefaultAction) (player, option, slot, itemId) ->
                    handleActions(player, 56);

            h.actions[58] = (DefaultAction) (player, option, slot, itemId) ->
                    handleActions(player, 58);

            h.actions[60] = (DefaultAction) (player, option, slot, itemId) ->
                    handleActions(player, 60);

            h.actions[62] = (DefaultAction) (player, option, slot, itemId) ->
                    handleActions(player, 62);
        });
    }
}

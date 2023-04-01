package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.SkipLoad;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemPlayerAction;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.DAYS;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/20/2020
 */
@SkipLoad
public class Bonds {

    private static void redeem(Player player, int amount, Item item) {
        player.dialogue(new ItemDialogue().one(item.getId(), "You are about to redeem this bond<br>" +
                        "adding $" + amount + " to your amount donated.<br>" +
                        "This will consume the bond forever."),
                new OptionsDialogue(
                        new Option("Yes!", (player1 -> {
                            player.amountDonated += amount;
                            player.getInventory().remove(item);
                            player.sendMessage("You have redeemed a $" + amount + " Bond. Your new total is: $" + player.amountDonated);
                        })),
                        new Option("I'll keep it for now.", player::closeDialogue)
                ));
    }

    private static final List<Long> times = Arrays.asList(
            DAYS.toMillis(365),
            DAYS.toMillis(30),
            DAYS.toMillis(1),
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.MINUTES.toMillis(1),
            TimeUnit.SECONDS.toMillis(1));

    private static final List<String> timesString = Arrays.asList("year", "month", "day", "hour", "minute", "second");

    private static String toDuration(long duration) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < times.size(); i++) {
            Long current = times.get(i);
            long temp = duration / current;
            if (temp > 0) {
                res.append(temp).append(" ").append(timesString.get(i)).append(temp != 1 ? "s" : "");
                break;
            }
        }
        if ("".equals(res.toString())) {
            return "0 seconds";
        } else {
            return res.toString();
        }
    }

    public static String getRemainingTime(Player player) {
        return toDuration(System.currentTimeMillis() - System.currentTimeMillis());
    }

    static {
        ItemAction.registerInventory(30307, 1, (player, item) -> {
            player.getInventory().remove(30307, 1);
            /*if (player.memberTimeLeft < System.currentTimeMillis()) {
                player.memberTimeLeft = System.currentTimeMillis() + DAYS.toMillis(3);
            } else {
                player.memberTimeLeft += DAYS.toMillis(3);
            }
            player.memberStatus = 1;*/
            player.sendMessage("You have added 3 Days to your current membership time. Your new remaining time is: " + getRemainingTime(player));
        });
        ItemAction.registerInventory(30248, 1, ((player, item) -> redeem(player, 5, item)));
        ItemAction.registerInventory(30250, 1, ((player, item) -> redeem(player, 10, item)));
        ItemAction.registerInventory(30249, 1, ((player, item) -> redeem(player, 25, item)));
        ItemAction.registerInventory(30251, 1, ((player, item) -> redeem(player, 50, item)));
        ItemAction.registerInventory(30252, 1, ((player, item) -> redeem(player, 100, item)));
        ItemAction.registerInventory(30253, 1, ((player, item) -> redeem(player, 250, item)));
        ItemAction.registerInventory(30254, 1, ((player, item) -> redeem(player, 500, item)));
        ItemPlayerAction.register(30307, (player, item, other) -> {
            player.getInventory().remove(30307, 1);
           /* if (other.memberTimeLeft < System.currentTimeMillis()) {
                other.memberTimeLeft = System.currentTimeMillis() + DAYS.toMillis(3);
            } else {
                other.memberTimeLeft += DAYS.toMillis(3);
            }
            other.memberStatus = 1;*/
            other.sendMessage("You have been gifted 3 Days of Membership from " + player.getName());
            player.sendMessage("You have gifted 3 Days of Membership to " + other.getName());
        });
        LoginListener.register(player -> {
            /*if (player.memberTimeLeft < System.currentTimeMillis()) {
                player.sendFilteredMessage(Color.CRIMSON.wrap("You are currently not an Active Donator of Edgeville. type ::store to purchase membership tokens! Thanks for playing " +
                        World.type.getWorldName() + "!"));
                player.dzHome = false;
                player.memberStatus = 0;
            } else {
                player.sendFilteredMessage(Color.RAID_PURPLE.wrap("You are currently an Active Donator of Edgeville. Your remaining time is: "
                        + getRemainingTime(player)) + Color.RAID_PURPLE.wrap(" Thanks for playing " +
                        World.type.getWorldName() + "!"));
            }*/
        });
    }
}
package io.ruin.services;

import io.ruin.cache.Color;
import io.ruin.cache.NpcID;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Direction;
import io.ruin.utility.Broadcast;
import lombok.SneakyThrows;

public class EverythingRS implements Runnable {

    private final Player player;

    public EverythingRS(Player player) {
        this.player = player;
    }

    @SneakyThrows
    @Override
    public void run() {
        /*try {
            com.everythingrs.donate.Donation[] donations = com.everythingrs.donate.Donation.donations("EQZaQmVpdQx83692NEK3wWeYIIHbi8l4rnOiwcEDucc3CqfJuo5q2GCtANne9oqYQ77K3OzU",
                    player.getName());
            if (donations.length == 0) {
                player.getPacketSender().sendMessage("You currently don't have any items waiting. You must donate first!", null, 102);
                return;
            }
            if (donations[0].message != null) {
                player.sendMessage(donations[0].message);
                return;
            }
            for (com.everythingrs.donate.Donation donate : donations) {
                if (donate.product_id == -1) {
                    player.getBattlePass().purchaseBattlePass();
                    player.sendMessage("Thank you for donating, Your Battle Pass has been activated!");
                    Broadcast.WORLD.sendNews("[News]" + player.getName() + " has purchased The Battle Pass!");
                    amount -= donate.product_price;
                    player.storeAmountSpent += donate.product_price;
                    player.getInventory().add(30307, (int) donate.product_price);
                    Broadcast.WORLD.sendNews(player.getName() + " Just Donated to Defiled!, $" + amount + " left until Donator Boss Spawns!");
                } else if (donate.product_id == 2) {
                    player.hasCustomTitle = true;
                    player.sendMessage("Thank you for donating, Your can now set your Custom title at Makeover Mage");
                    amount -= donate.product_price;
                    player.storeAmountSpent += donate.product_price;
                    player.getInventory().add(30307, (int) donate.product_price);
                    Broadcast.WORLD.sendNews(player.getName() + " Just Donated to Defiled!, $" + amount + " left until Donator Boss Spawns!");
                } else {
                    player.getBank().add(donate.product_id, donate.product_amount);
                    player.sendMessage("Thank you for donating!");
                    player.sendMessage(Color.CRIMSON.wrap("The items you've donated for have been sent to your Bank!"));
                    amount -= donate.product_price * donate.product_amount;
                    player.storeAmountSpent += donate.product_price * donate.product_amount;
                    player.getInventory().add(30307, (int) donate.product_price * donate.product_amount);
                    Broadcast.WORLD.sendNews(player.getName() + " Just Donated to Defiled!, $" + amount + " left until Donator Boss Spawns!");
                    if (donate.product_amount > 40) {
                        for (int i = 0; i < (donate.product_price / 40); i++) {
                            World.startEvent(e -> {
                                new NPC(NpcID.AVATAR_OF_DESTRUCTION_10532).spawn(3811, 2870, 1, Direction.WEST, 1).getCombat().setAllowRespawn(false);
                                Broadcast.WORLD.sendNews("<shad=000000>" + Color.BABY_BLUE.wrap("[DONATION BOSS] ") + "Avatar of destruction Has just spawned" + " use ::donboss to get there!</shad>");
                                Broadcast.WORLD.sendNews("<shad=000000>" + Color.BABY_BLUE.wrap("[DONATION BOSS] ") + "Multiple donation bosses pending! 5 minutes until the next one spawns!</shad>");
                                amount = 40;
                                e.delay(500);
                            });
                        }
                    } else {
                        if (amount <= 0) {
                            new NPC(NpcID.AVATAR_OF_DESTRUCTION_10532).spawn(3811, 2870, 1, Direction.WEST, 1).getCombat().setAllowRespawn(false);
                            Broadcast.WORLD.sendNews("<shad=000000>" + Color.BABY_BLUE.wrap("[DONATION BOSS] ") + "Avatar of destruction Has just spawned" + " ::donboss to get there!</shad>");
                            amount = 40;
                        }
                    }
                }
            }
        } catch (Exception e) {
            player.sendMessage("Our API Services are currently offline. Please contact a staff member.");
            e.printStackTrace();
        }*/

    }

    public static int amount = 40;

}

package io.ruin.model.entity.npc.actions;

import io.ruin.api.utils.Random;
import io.ruin.data.impl.teleports;
import io.ruin.model.activities.pvminstances.InstanceDialogue;
import io.ruin.model.activities.pvminstances.InstanceType;
import io.ruin.model.diaries.fremennik.FremennikDiaryEntry;
import io.ruin.model.diaries.pvm.PvMDiaryEntry;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.shop.ShopManager;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.model.skills.slayer.Slayer;
import io.ruin.utility.Misc;

import java.util.Arrays;
import java.util.stream.Collectors;

public class NPCDialogue {

    static {
        NPCAction.register(9505, "talk-to", (player, npc) -> {
            player.dialogue(new OptionsDialogue("Would you like to buy some mithril seeds?", new Option("Yes.", () -> {
                ShopManager.openIfExists(player, "JESTERSHOPSEEDS");
            }), new Option("No thanks.")));
        });

        NPCAction.register(4402, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "Trade me to spend your blood money.")));

       // NPCAction.register(10631, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "Hey traveller, check out my stardust shop, I take all stardust's as currency for some amazing items.")));

        NPCAction.register(2820, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "Welcome to Devious's General Store")));
        NPCAction.register(3200, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "Welcome to Devious's General Store")));
        NPCAction.register(2891, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "Welcome to the Fishing Store")));
        NPCAction.register(3213, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "Welcome to the Fishing Store")));
        NPCAction.register(1788, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "Welcome to the Afk Store")));
        NPCAction.register(6955, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "I'll un-note any bones you have for 500 coins each")));
        NPCAction.register(2814, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "Welcome to the supply shop!")));
        NPCAction.register(7240, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "Check out my wares, I have some fine axes!")));
        NPCAction.register(8686, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "Have a look at my selection of Pickaxe's.")));
        NPCAction.register(2882, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "I'd be happy to enchant some of your items, for a fee of-course.")));
        NPCAction.register(10876, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "The items I have for sale you don't want to miss out on ;-)")));
        NPCAction.register(3212, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "welcome to the fletching store!")));
        NPCAction.register(2318, "talk-to", (player, npc) -> player.dialogue(new OptionsDialogue("How can I assist you today?", new Option("Reset my slayer task. (500k)", () -> {
            if (player.getInventory().count(995) < 500_000) {
                player.sendMessage("You do not have enough gold in your inventory");
                return;
            }
            player.getInventory().remove(995, 500_000);
            Slayer.reset(player);
        }), new Option("Extend my slayer task. " + Misc.currency(5_000_000), () -> {
            int amount = player.getInventory().count(995);
            if (player.slayerExtendedCounter >= 1) {
                player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "You need to complete your current task to reset the counter!"));
                return;
            }
            if (amount < 5_000_000) {
                player.sendMessage("You do not have enough gold in your inventory");
                return;
            }
            player.slayerExtendedCounter += 1;
            player.getInventory().remove(995, 5_000_000);
            player.slayerTaskRemaining += Random.get(50, 100);
            player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "Your new slayer amount is " + player.slayerTaskRemaining + ", Come back soon."));
        }), new Option("View Slayer Point Store", () -> ShopManager.openIfExists(player, "SLAYERPOINTSHOPPOG")))));
        NPCAction.register(11061, "talk-to", (player, npc) -> {
            OptionScroll.open(player, "Select a npc to instance.", true, Arrays.stream(InstanceType.values()).map(instanceType -> new Option(instanceType.getName(), () -> InstanceDialogue.open(player, InstanceType.valueOf(instanceType.name())))).collect(Collectors.toList()));
        });
        NPCAction.register(3247, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "welcome to the Wizards Guild, Be sure to check out my wares!")));
        NPCAction.register(3249, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "welcome to the Wizards Guild, Be sure to check out my wares!")));
        NPCAction.register(2875, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "Welcome, be sure to check out my wares I have for sale!")));
        NPCAction.register(534, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "Welcome to my shop, this is my fashion store.")));
        NPCAction.register(2871, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "welcome to the champion's guild, besure to check out my wares!")));
        NPCAction.register(2870, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "welcome to the champion's guild, besure to check out my wares!")));
        NPCAction.register(1781, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "Do you dare enter the pyramid?")));
        NPCAction.register(7042, "talk-to", (player, npc) -> player.dialogue(new OptionsDialogue("Yes child how can I help you?",
                new Option("Take me to the mine.", () -> player.getMovement().teleport(1764, 3852, 0)),
                new Option("Take me to the Soul Altar", () -> player.getMovement().teleport(1819, 3855, 0)),
                new Option("Take me to the Blood Altar", () -> player.getMovement().teleport(1721, 3827, 0)),
                new Option("Take me to the Dark Altar", () -> player.getMovement().teleport(1712, 3882, 0)))));
        NPCAction.register(7050, "talk-to", (player, npc) -> player.dialogue(new OptionsDialogue("Yes child how can I help you?",
                new Option("Take me to the mine.", () -> player.getMovement().teleport(1764, 3852, 0)),
                new Option("Take me to the Soul Altar", () -> player.getMovement().teleport(1819, 3855, 0)),
                new Option("Take me to the Blood Altar", () -> player.getMovement().teleport(1721, 3827, 0)),
                new Option("Take me to the Dark Altar", () -> player.getMovement().teleport(1712, 3882, 0)))));
        NPCAction.register(7053, "talk-to", (player, npc) -> player.dialogue(new OptionsDialogue("Yes child how can I help you?",
                new Option("Take me to the mine.", () -> player.getMovement().teleport(1764, 3852, 0)),
                new Option("Take me to the Soul Altar", () -> player.getMovement().teleport(1819, 3855, 0)),
                new Option("Take me to the Blood Altar", () -> player.getMovement().teleport(1721, 3827, 0)),
                new Option("Take me to the Dark Altar", () -> player.getMovement().teleport(1712, 3882, 0)))));
        NPCAction.register(7040, "talk-to", (player, npc) -> player.dialogue(new OptionsDialogue("Yes child how can I help you?",
                new Option("Take me to the mine.", () -> player.getMovement().teleport(1764, 3852, 0)),
                new Option("Take me to the Soul Altar", () -> player.getMovement().teleport(1819, 3855, 0)),
                new Option("Take me to the Blood Altar", () -> player.getMovement().teleport(1721, 3827, 0)),
                new Option("Take me to the Dark Altar", () -> player.getMovement().teleport(1712, 3882, 0)))));


        NPCAction.register(8764, "talk-to", (player, npc) -> player.dialogue(new OptionsDialogue("Ahoy there matey, where might you be going?",
                new Option("Take me to Ardougne.", () -> {
                    player.getMovement().teleport(2683, 3271, 0);
                    player.getDiaryManager().getPvmDiary().progress(PvMDiaryEntry.SAIL_TO_ARDOUGNE);
                }),
                new Option("Take me to Rimmington", () -> player.getMovement().teleport(2915, 3235, 0)))));

        NPCAction.register(3648, "talk-to", (player, npc) -> player.dialogue(new OptionsDialogue("The boat for port sarim is leaving!",
                new Option("Let's go!.", () -> {
                    player.getMovement().teleport(3029, 3217, 0);
                    player.getDiaryManager().getPvmDiary().progress(PvMDiaryEntry.SAIL_TO_PORT_SARIM);
                }))));

        NPCAction.register(3648, "pay-fare", (player, npc) -> player.dialogue(new OptionsDialogue("The boat for port sarim is leaving!",
                new Option("Let's go!.", () -> {
                    player.getMovement().teleport(3029, 3217, 0);
                    player.getDiaryManager().getPvmDiary().progress(PvMDiaryEntry.SAIL_TO_PORT_SARIM);
                }))));

        NPCAction.register(1883, "talk-to", (player, npc) -> player.dialogue(
                new PlayerDialogue("Hello there, are you heading anywhere?"),
                new io.ruin.model.inter.dialogue.NPCDialogue(npc, "yes I'm heading to Neitiznot"),
                new PlayerDialogue("Oh may I travel along as well ?"),
                new io.ruin.model.inter.dialogue.NPCDialogue(npc, "Yes join me on my boat."),
                new ActionDialogue(() -> {
                    teleports.teleport(player, 2310, 3781, 0);
                    player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.TRAVEL_NEITIZNOT);
                })));
        NPCAction.register(1883, "neitiznot", (player, npc) -> {
            teleports.teleport(player, 2310, 3781, 0);
            player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.TRAVEL_NEITIZNOT);
        });

        NPCAction.register(10726, "talk-to", (player, npc) -> player.dialogue(new io.ruin.model.inter.dialogue.NPCDialogue(npc, "Good day, traveller.")));
        NPCAction.register(10726, "port sarim", (player, npc) -> {
            ModernTeleport.teleport(player, 3055, 3245, 0);
            //player.getDiaryManager().getKourendDiary().progress(KourendDiaryEntry.KOUREND_SARIM);
        });

        NPCAction.register(10726, "land's end", (player, npc) -> {
            ModernTeleport.teleport(player, 1504, 3400, 0);
        });

        NPCAction.register(5519, 1, (player, npc) -> player.getDiaryManager().getPvpDiary().claimReward(npc));
        NPCAction.register(5520, 1, (player, npc) -> player.getDiaryManager().getMinigamesDiary().claimReward(npc));
        NPCAction.register(5524, 1, (player, npc) -> player.getDiaryManager().getSkillingDiary().claimReward(npc));
        NPCAction.register(5526, 1, (player, npc) -> player.getDiaryManager().getFremennikDiary().claimReward(npc));
        NPCAction.register(5517, 1, (player, npc) -> player.getDiaryManager().getKandarinDiary().claimReward(npc));
        NPCAction.register(7650, 1, (player, npc) -> player.getDiaryManager().getPvmDiary().claimReward(npc));
        NPCAction.register(8538, 1, (player, npc) -> player.getDiaryManager().getDeviousDiary().claimReward(npc));
        NPCAction.register(5523, 1, (player, npc) -> player.getDiaryManager().getLumbridgeDraynorDiary().claimReward(npc));
        NPCAction.register(5521, 1, (player, npc) -> player.getDiaryManager().getMorytaniaDiary().claimReward(npc));
        NPCAction.register(5525, 1, (player, npc) -> player.getDiaryManager().getVarrockDiary().claimReward(npc));
        NPCAction.register(5518, 1, (player, npc) -> player.getDiaryManager().getWesternDiary().claimReward(npc));
        NPCAction.register(5514, 1, (player, npc) -> player.getDiaryManager().getWildernessDiary().claimReward(npc));


    }

}

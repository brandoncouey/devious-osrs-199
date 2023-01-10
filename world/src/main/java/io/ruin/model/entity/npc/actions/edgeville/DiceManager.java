package io.ruin.model.entity.npc.actions.edgeville;
import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemNPCAction;

public class DiceManager {

    private final static int DICE_HOST = 4306;
    static {
        NPCAction.register(DICE_HOST, "Talk-to", (player, npc) -> {
            player.dialogue(new NPCDialogue(npc, "Are you sure you want to dice?"),
                    new OptionsDialogue(
                            new Option("Yes, I'm sure.",  () -> player.dialogue(
                                    new PlayerDialogue("Yes please."),
                                    new NPCDialogue(npc, "Hand me the Currency you would like to use."))),
                            new Option("No thanks.", () -> player.dialogue(new PlayerDialogue("No thanks.")))
                    ));
        });
            ItemNPCAction.register(995, DICE_HOST, DiceManager::promptForDice);
        ItemNPCAction.register(13307, DICE_HOST, DiceManager::promptForDiceBM);
    }
    private static void promptForDice(Player player, Item item, NPC npc) {
        Item coins = player.getInventory().findItem(995);
        if(coins == null || coins.getAmount() < 1) {
            player.dialogue(new NPCDialogue(npc, "You do not have enough Money to dice."));
            return;
        }
        if(player.hasDialogue()) {
            player.resetAnimation();
            return;
        }
        if (World.isEco()) {
            player.sendMessage("This NPC has been disabled until further notice.");
            return;
        }
        player.integerInput("How many would you like to exchange?", amt -> offerAmount(player, amt));
    }

    private static void promptForDiceBM(Player player, Item item, NPC npc) {
        Item coins = player.getInventory().findItem(13307);
        if(coins == null || coins.getAmount() < 1) {
            player.dialogue(new NPCDialogue(npc, "You do not have enough Money to dice."));
            return;
        }
        if(player.hasDialogue()) {
            player.resetAnimation();
            return;
        }
        if (World.isEco()) {
            player.sendMessage("This NPC has been disabled until further notice.");
            return;
        }
        player.integerInput("How many would you like to exchange?", amt -> offerAmountBM(player, amt));
    }

    private static void offerAmount(Player player, int amt) {
            Item coins = player.getInventory().findItem(995);
            if(coins == null || coins.getAmount() < 1)
                return;
            if(player.getInventory().isFull())
                return;
            player.diceOfferGP += amt;
            DiceManager.roll(player, 100);
    }

    private static void offerAmountBM(Player player, int amt) {
        Item coins = player.getInventory().findItem(13307);
        if(coins == null || coins.getAmount() < 1)
            return;
        if(player.getInventory().isFull())
            return;
        player.diceOfferBM += amt;
        DiceManager.rollBM(player, 100);
    }


    public static void roll(Player player, int sides) {
        if (player.getInventory().count(995) < player.diceOfferGP) {
            player.sendMessage("You have failed to reach the requirements to bet this much GP");
            return;
        }
        player.startEvent(event -> {
                int roll = Random.get(1, sides);
                if (roll > 10 && sides == 100 && Random.rollDie(10, 1))
                    roll /= 10;
                if (Random.rollDie(100, 60)) {
                    roll = Random.get(56, 100);
                }
                //player.dialogue(new NPCDialogue(3253, "Doesn't look like you're caryring any mole parts.<br>Come back to me when you have some."));
                player.dialogue(new NPCDialogue(4306, "<img=23> Rolling against " + player.getName() + "...").hideContinue());
            event.delay(3);
            player.dialogue(new NPCDialogue(4306, "You roll the dice..").hideContinue());
                event.delay(3);
            player.dialogue(new NPCDialogue(4306, "<img=23> " + player.getName() + " ...Rolled a " + roll + "/" + sides + "").hideContinue());
            if (roll >= 55) {
                player.getInventory().add(995, player.diceOfferGP);
                player.diceOfferGP = 0;
                player.dialogue(new NPCDialogue(4306, "Congratulations " + player.getName() + " on winning the pot! Rolled a " + roll + "/" + sides + ""));
            } else {
                player.getInventory().remove(995, player.diceOfferGP);
                player.diceOfferGP = 0;
                player.dialogue(new NPCDialogue(4306, "Bad luck @" + player.getName() + " on losing the pot! Rolled a " + roll + "/" + sides + ""));
            }
            event.delay(3);
                player.unlock();
            });
        }
    public static void rollBM(Player player, int sides) {
        if (player.getInventory().count(13307) < player.diceOfferBM) {
            player.sendMessage("You have failed to reach the requirements to bet this much BM");
            return;
        }
        player.startEvent(event -> {
            int roll = Random.get(1, sides);
            if (roll > 10 && sides == 100 && Random.rollDie(10, 1))
                roll /= 10;
            player.dialogue(new NPCDialogue(4306, "<img=23> Rolling against " + player.getName() + "...").hideContinue());
            event.delay(3);
            player.dialogue(new NPCDialogue(4306, "You roll the dice..").hideContinue());
            event.delay(3);
            player.dialogue(new NPCDialogue(4306, "<img=23> " + player.getName() + " ...Rolled a " + roll + "/" + sides + "").hideContinue());
            if (roll >= 55) {
                player.getInventory().add(13307, player.diceOfferBM);
                player.diceOfferBM = 0;
                player.dialogue(new NPCDialogue(4306, "Congratulations " + player.getName() + " on winning the pot! Rolled a " + roll + "/" + sides + ""));
            } else {
                player.getInventory().remove(13307, player.diceOfferBM);
                player.diceOfferBM = 0;
                player.dialogue(new NPCDialogue(4306, "Bad luck @" + player.getName() + " on losing the pot! Rolled a " + roll + "/" + sides + ""));
            }
            event.delay(5);
            player.unlock();
        });
    }

    }

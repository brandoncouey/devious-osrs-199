package io.ruin.model.reputation;


import io.ruin.model.diaries.varrock.VarrockDiaryEntry;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;


public class AuburyRep {

    private static final int AUBURY = 2886;

    static {
        NPCAction.register(AUBURY, "talk-to", AuburyRep::auburyDialogue);
        NPCAction.register(AUBURY, "teleport", ((player, npc) -> {
            player.sendMessage("Aubury teleports you to the essence mine.");
            player.getMovement().teleport(2910, 4830, 0);
            player.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.TELEPORT_ESSENCE_VAR);
        }));
    }

    private static void auburyDialogue(Player player, NPC npc) {
        if (PlayerCounter.AUBURY_REP.get(player) == 0) {
            player.dialogue(
                    new NPCDialogue(npc, "Hello there, adventurer. What is it you need?"),
                    new OptionsDialogue("Choose an option",
                            new Option("Do you have any quests for me?", () -> getQuest(player, npc)),
                            new Option("Nevermind")

                    )
            );
        }
        if (PlayerCounter.AUBURY_REP.get(player) == 1 && player.getInventory().contains(7936, 28)) {
            player.dialogue(
                    new PlayerDialogue("I've brought you some pure essence!"),
                    new NPCDialogue(npc, "Bless you, adventurer. This will surely help my study!"),
                    new ActionDialogue(() -> {
                        player.getInventory().remove(7936, 28);
                        player.varrockRep = +10;
                        player.sendMessage("+10 Varrock Reputation");
                    })
            );
        }
    }

    public static void getQuest(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Well, now that you mention it, yes.. I am discovering the power of pure essence and I have a limited supply. " +
                        "If you could bring me 28 pure essence, I would be indebted to you!"),
                new PlayerDialogue("28 pure essence, got it!"),
                new MessageDialogue("Bring Aubury 28 unnoted pure essence. This can be repeated."),
                new ActionDialogue(() -> {
                    PlayerCounter.AUBURY_REP.increment(player, 1);//stage 1
                })
        );
    }

}
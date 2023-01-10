package io.ruin.model.reputation;

import io.ruin.model.diaries.falador.FaladorDiaryEntry;
import io.ruin.model.diaries.wilderness.WildernessDiaryEntry;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.MessageDialogue;

public class ReputationKills {

    public static void kills(Player player, int npcId) {
        switch (npcId) {
            case 264:
                if (player.varrockRep < 8000 && PlayerCounter.KING_ROALD.get(player) == 1 && player.greendragonRep < 100) {
                    player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.KILL_GREEN_DRAGON);
                    player.varrockRep += 5;
                    player.sendMessage("@blu@+5 Varrock Reputation");
                    player.greendragonRep += 1;
                }
                if (player.greendragonRep >= 100 && PlayerCounter.KING_ROALD.get(player) == 1) {
                    player.dialogue((new MessageDialogue("You have finished slaying 100 green dragons. Return to King Roald to receive a reward!")));
                    PlayerCounter.KING_ROALD.increment(player, 1);//stage 2
                }
                break;
            case 8061://Vorkath
                if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 11) {
                    PlayerCounter.DRAGON_SLAYER_2.increment(player, 1);//stage 12
                    player.dialogue(
                            new MessageDialogue("Return to Alec Kincade at the Myths Guild!")
                    );
                }
                break;
            case 655:
            case 656:
                if (player.edgevilleRep < 21000 && PlayerCounter.KING_ARTHUR.get(player) == 1) {
                    player.goblinRep += 1;
                }
                if (player.goblinRep >= 12 && PlayerCounter.KING_ARTHUR.get(player) == 1) {
                    player.dialogue((new MessageDialogue("You have finished slaying 12 goblins. Return to King Arthur to receive a reward!")));
                    player.sendMessage("You have finished slaying 12 goblins. Return to King Arthur to receive a reward!");
                    PlayerCounter.KING_ARTHUR.increment(player, 1);//stage 2
                }
                break;
            case 1443://Jungle Demon MM
                PlayerCounter.MONKEY_MADNESS.increment(player, 1);//stage 2
                player.getMovement().teleport(2465, 3496, 0);
                break;
            case 6766:
                player.kebosRep += 3;
                player.sendMessage("+3 Kebos Reputation");
                if (player.shamanRep < 75) {
                    player.shamanRep += 1;
                }
                if (player.shamanRep >= 75 && PlayerCounter.PENTYN.get(player) == 1) {
                    player.dialogue((new MessageDialogue("You have finished slaying 75 Lizardman Shamans. Return to Pentyn to receive a reward!")));
                    player.sendMessage("You have finished slaying 75 Lizardman Shamans. Return to Pentyn to receive a reward!");
                    PlayerCounter.PENTYN.increment(player, 1);//stage 2
                }
                break;
            case 5779:
                player.faladorRep += 5;
                player.sendMessage("+5 Falador Reputation");
                player.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.KILL_GIANT_MOLE);
                if (player.faladorRep < 10000) {
                    player.giantmoleRep += 1;
                }
                if (player.giantmoleRep >= 50 && PlayerCounter.DOORMAN_REP.get(player) == 1) {
                    player.dialogue((new MessageDialogue("You have finished slaying 50 giant moles. Return to the doorman to receive a reward!")));
                    PlayerCounter.DOORMAN_REP.increment(player, 1);//stage 2
                }
                break;
            case 8610:
            case 8611:
                if (player.wyrmRep < 50) {
                    player.wyrmRep += 1;
                }
                if (player.wyrmRep >= 50 && PlayerCounter.PENTYN.get(player) == 4) {
                    player.dialogue((new MessageDialogue("You have finished slaying 50 Wyrms. Return to Pentyn to receive a reward!")));
                    player.sendMessage("You have finished slaying 50 Wyrms. Return to Pentyn to receive a reward!");
                    PlayerCounter.PENTYN.increment(player, 1);//stage 5
                }
                break;
            case 7544:
                if (PlayerCounter.SIR_KAY.get(player) == 8) {
                    PlayerCounter.SIR_KAY.increment(player, 1);//stage 9
                    player.dialogue((new MessageDialogue("You've slain Tekton! Return to Sir Kay.")));
                }
                break;
            case 7563:
                if (PlayerCounter.SIR_KAY.get(player) == 12) {
                    PlayerCounter.PENTYN.increment(player, 1);//stage 13
                    player.dialogue((new MessageDialogue("You've slain Muttadile! Return to Pentyn in Kebos.")));
                }
                break;
            case 7566:
                if (PlayerCounter.SIR_KAY.get(player) == 14) {
                    PlayerCounter.PENTYN.increment(player, 1);//stage 15
                    player.dialogue((new MessageDialogue("You've slain Vasa! Return to Pentyn in Kebos.")));
                }
                break;
        }
    }
}
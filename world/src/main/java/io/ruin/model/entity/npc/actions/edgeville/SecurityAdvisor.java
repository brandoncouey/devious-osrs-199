package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

public class SecurityAdvisor {

    private static void imSecure(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Yes, I'm secure."),
                new ActionDialogue(() -> {
                    if (player.getBankPin().hasPin()) {
                        player.dialogue(new NPCDialogue(npc, "Good to know! It always warms my heart to see people valuing their security."));
                    } else {
                        player.dialogue(
                                new NPCDialogue(npc, "It doesn't look like you are.. setting up the security for your account takes no" +
                                        " more than a few minutes and can save you a ton of time and heartache should anything ever happen "),
                                new NPCDialogue(npc, "Would you like me to give you a rundown of how you can properly secure your account?"),
                                new OptionsDialogue(
                                        new Option("Yes, help me secure my account.", () -> player.dialogue(
                                                new PlayerDialogue("Yes, please help me secure my account!"),
                                                new ActionDialogue(() -> helpSecure(player, npc)))
                                        ),
                                        new Option("No, I don't want me account to be safe.", () -> player.dialogue(
                                                new PlayerDialogue("No, I don't want my account to be safe.")
                                        ))
                                ));
                    }
                })
        );
    }

    private static void helpSecure(Player player, NPC npc) {
        if (player.getBankPin().hasPin()) {
            player.dialogue(new NPCDialogue(npc, "It looks like you have all the security options we have to offer!"),
                    new PlayerDialogue("Oh, I guess I am secure."));
        } else {
            player.dialogue(
                    new NPCDialogue(npc, "Alright! Well there's currently one type of security features here at " + World.type.getWorldName() + " that you can take advantage of. That is " +
                            "a bank pin."),
                    new OptionsDialogue(
                            new Option("I'd like to set a bank pin", () -> player.getBankPin().openSettings())
                    )
            );
        }
    }

    static {
        NPCAction.register(5442, "talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "Making sure your account is secure is absolutely critical! Have you taken the time to secure your account with a bank pin?"),
                new OptionsDialogue(
                        new Option("Yes, I'm secure.", () -> imSecure(player, npc)),
                        new Option("I'm not secure yet.", () -> player.dialogue(
                                new PlayerDialogue("I'm not secure yet."),
                                new ActionDialogue(() -> {
                                    if (player.getBankPin().hasPin()) {
                                        player.dialogue(
                                                new NPCDialogue(npc, "It looks like you have all the security options we have to offer!"),
                                                new PlayerDialogue("Oh, I guess I am secure."));
                                    } else {
                                        player.dialogue(
                                                new NPCDialogue(npc, "Well that just simply won't do! Setting up the security for your account takes no more " +
                                                        "than a few minutes and can save you a ton of time and heartache should anything ever happen."),
                                                new NPCDialogue(npc, "Would you like me to give you a rundown of how you can properly secure your account?"),
                                                new OptionsDialogue(
                                                        new Option("Yes, help me secure my account.", () -> player.dialogue(
                                                                new PlayerDialogue("Yes, please help me secure my account!"),
                                                                new ActionDialogue(() -> helpSecure(player, npc)))
                                                        ),
                                                        new Option("No, I don't want me account to be safe.", () -> player.dialogue(
                                                                new PlayerDialogue("No, I don't want my account to be safe.")
                                                        ))
                                                ));
                                    }
                                })
                        ))
                )
        ));
    }
}

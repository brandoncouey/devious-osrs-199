package io.ruin.model.inter.dialogue;

import io.ruin.cache.NPCDef;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NPCDialogue implements Dialogue {

    private final NPCDef npcDef;

    private final String message;

    private Runnable action;

    private Runnable onDialogueOpened;

    private int animationId = 554;

    private int lineHeight;

    private boolean hideContinue;

    public NPCDialogue(int npcId, String message) {
        this(NPCDef.get(npcId), message);
    }

    public NPCDialogue(NPC npc, String message) {
        this(npc.getDef(), message);
    }

    private NPCDialogue(NPCDef npcDef, String message) {
        this.npcDef = npcDef;
        this.message = message;
    }

    public NPCDialogue onDialogueOpened(Runnable openDialogueOpened) {
        this.onDialogueOpened = openDialogueOpened;
        return this;
    }

    public NPCDialogue animate(int animationId) {
        this.animationId = animationId;
        return this;
    }

    public NPCDialogue lineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    public NPCDialogue hideContinue() {
        this.hideContinue = true;
        return this;
    }

    public NPCDialogue action(Runnable action) {
        this.action = action;
        return this;
    }

    public static final int headChild = 6, nameChild = 3, textChild = 5, continueChild = 4;

    @Override
    public void open(Player player) {
        player.openInterface(InterfaceType.CHATBOX, Interface.NPC_DIALOGUE);
        player.getPacketSender().sendNpcHead(Interface.NPC_DIALOGUE, headChild, npcDef.id);
        player.getPacketSender().animateInterface(Interface.NPC_DIALOGUE, headChild, animationId);
        player.getPacketSender().sendString(Interface.NPC_DIALOGUE, nameChild, npcDef.name);
        player.getPacketSender().sendString(Interface.NPC_DIALOGUE, textChild, message);
        player.getPacketSender().setTextStyle(Interface.NPC_DIALOGUE, textChild, 1, 1, lineHeight);
        player.getPacketSender().sendAccessMask(Interface.NPC_DIALOGUE, continueChild, -1, -1, 1);
        if (hideContinue)
            player.getPacketSender().setHidden(Interface.NPC_DIALOGUE, continueChild, true);
        else
            player.getPacketSender().sendString(Interface.NPC_DIALOGUE, continueChild, "Click here to continue");
        if (onDialogueOpened != null)
            onDialogueOpened.run();
    }

    static {
        InterfaceHandler.register(Interface.NPC_DIALOGUE, h -> h.actions[continueChild] = (SimpleAction) player -> {
            player.continueDialogue();
            player.onDialogueContinued();
        });
    }

}

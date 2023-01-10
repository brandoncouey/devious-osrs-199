package io.ruin.network.incoming.desktop.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.api.utils.Random;
import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.journal.bestiary.Bestiary;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.DebugMessage;
import io.ruin.utility.IdHolder;

import java.util.Arrays;
import java.util.HashSet;

@IdHolder(ids = {70, 88, 95, 6, 18, 81})//@IdHolder(ids = {89, 96, 35, 79, 37, 9})
public class NPCActionHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        if (player.isLocked())
            return;
        player.resetActions(true, true, true);

        int option = OPTIONS[opcode];
        if (option == 1) {
            int ctrlRun = in.readByte();
            int targetIndex = in.readLEShort();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if (option == 2) {
            int targetIndex = in.readShort();
            int ctrlRun = in.readByteS();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if (option == 3) {
            int targetIndex = in.readLEShort();
            int ctrlRun = in.readByte();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if (option == 4) {
            int ctrlRun = in.readByteC();
            int targetIndex = in.readShort();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if (option == 5) {
            int ctrlRun = in.readByte();
            int targetIndex = in.readShortA();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if (option == 6) {
            int id = in.readLEShortA();
            NPCDef def = NPCDef.get(id);
            if (def == null)
                return;
            if (def.id == 4249) {
                player.sendMessage("I hope she doesn't call my manager.");
            } else {
                player.sendMessage(def.name);
                Bestiary.open(player);
                Bestiary.search(player, def.name, true);
            }
            if (player.debug)
                debug(player, null, def, -1);
            return;
        }
        player.sendFilteredMessage("Unhandled npc action: option=" + option + " opcode=" + opcode);
    }

    private static void handleAction(Player player, int option, int npcIndex, int ctrlRun) {
        NPC npc = World.getNpc(npcIndex);
        if (npc == null)
            return;
        NPCDef def = npc.getDef();
        if (player.debug)
            debug(player, npc, def, option);
        player.face(npc);
        player.getMovement().setCtrlRun(ctrlRun == 1);
        if (option == def.attackOption) {
            player.getCombat().setTarget(npc);
            return;
        }
        if (npc.skipMovementCheck) {
            player.face(npc);
            int i = option - 1;
            if (i < 0 || i >= 5)
                return;
            NPCAction action = null;
            NPCAction[] actions = npc.actions;
            if (actions != null)
                action = actions[i];
            if (action == null && (actions = def.defaultActions) != null)
                action = actions[i];
            if (action != null) {
                action.handle(player, npc);
                return;
            }
            return;
        }
        TargetRoute.set(player, npc, () -> {
            int i = option - 1;
            if (i < 0 || i >= 5)
                return;
            NPCAction action = null;
            NPCAction[] actions = npc.actions;
            if (actions != null)
                action = actions[i];
            if (def.cryptic != null && def.cryptic.advance(player))
                return;
            if (def.anagram != null && def.anagram.advance(player))
                return;
            if (action == null && (actions = def.defaultActions) != null)
                action = actions[i];
            if (action != null) {
                action.handle(player, npc);
                player.face(npc);
                return;
            }
            /* default to a dialogue */
                player.dialogue(new PlayerDialogue("They seem busy."));
        });
    }

    private static void debug(Player player, NPC npc, NPCDef def, int option) {
        HashSet<Integer> showIds = new HashSet<>();
        if (def.showIds != null) {
            for (int id : def.showIds)
                showIds.add(id);
            showIds.remove(-1);
        }
        DebugMessage debug = new DebugMessage();
        if (option != -1)
            debug.add("option", option);
        debug.add("id", def.id + (showIds.isEmpty() ? "" : (" " + showIds)));
        debug.add("name", def.name);
        if (npc != null) {
            debug.add("index", npc.getIndex());
            debug.add("x", npc.getAbsX());
            debug.add("y", npc.getAbsY());
            debug.add("z", npc.getHeight());
        }
        debug.add("options", Arrays.toString(def.options));
        debug.add("varpbitId", def.varpbitId);
        debug.add("varpId", def.varpId);
        if (def.varpbitId != -1 || def.varpId != -1)
            debug.add("variants", Arrays.toString(def.showIds));
        player.sendFilteredMessage("[NpcAction] " + debug);
    }

}
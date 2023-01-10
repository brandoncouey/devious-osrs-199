package io.ruin.model.activities.tasks;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;

import java.util.function.Predicate;

public enum NPCKillTaskListener implements TaskListener {
    ZULRAH(n -> n.getDef().name.equalsIgnoreCase("Zulrah")),
    ABYSSAL_SIRE(n -> n.getDef().name.equalsIgnoreCase("Abyssal Sire")),
    KRAKEN(494),
    DAGANNOTH_KINGS(n -> n.getId() >= 2265 && n.getId() <= 2267),
    GODWARS_GENERALS(n -> n.getId() == 6493 || n.getId() == 6494 || n.getId() == 6492 || n.getId() == 6495),
    COMMANDER_ZILYANA(6493),
    GENERAL_GRAARDOR(6494),
    KREE_ARRA(6492),
    KRIL_TSUTSAROTH(6495),
    KALPHITE_QUEEN(n -> n.getDef().name.equalsIgnoreCase("Kalphite Queen")),
    THERMONUCLEAR_SMOKE_DEVIL(499),
    LIZARDMAN_SHAMAN(n -> n.getDef().name.equalsIgnoreCase("Lizardman Shaman")),
    CERBERUS(n -> n.getDef().name.equalsIgnoreCase("Cerberus")),
    BARROWS_BROTHERS(n -> n.getId() == 1672 || n.getId() == 1677 || n.getId() == 1675 || n.getId() == 1673 || n.getId() == 1674 || n.getId() == 1676),
    CALLISTO(6609),
    VETION(n -> n.getDef().name.contains("Vet'ion")),
    VENENATIS(6610),
    SCORPIA(6615),
    REVENANTS(n -> n.getDef().name.toLowerCase().contains("revenant")),
    ;

    NPCKillTaskListener(int npcId) {
        this(n -> n.getId() == npcId);
    }

    NPCKillTaskListener(Predicate<NPC> isTaskNPC) {
        this.isTaskNPC = isTaskNPC;
    }

    private Predicate<NPC> isTaskNPC;

    @Override
    public int onPlayerKill(Player player, Player killed) {
        return 0;
    }

    @Override
    public int onNPCKill(Player player, NPC killed) {
        return isTaskNPC.test(killed) ? 1 : 0;
    }

}

package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.model.stat.StatType;

public class DefenceSkillCape {

    private static final int CAPE = StatType.Defence.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Defence.trimmedCapeId;

    static {
        ItemAction.registerInventory(CAPE, "Toggle Effect", DefenceSkillCape::defenceToggle);
        ItemAction.registerInventory(CAPE, "Toggle Respawn", DefenceSkillCape::defenceRespawn);
        ItemAction.registerEquipment(CAPE, "Toggle Effect", DefenceSkillCape::defenceToggle);
        ItemAction.registerEquipment(CAPE, "Toggle Respawn", DefenceSkillCape::defenceRespawn);

        ItemAction.registerInventory(TRIMMED_CAPE, "Toggle Effect", DefenceSkillCape::defenceToggle);
        ItemAction.registerInventory(TRIMMED_CAPE, "Toggle Respawn", DefenceSkillCape::defenceRespawn);
        ItemAction.registerEquipment(TRIMMED_CAPE, "Toggle Effect", DefenceSkillCape::defenceToggle);
        ItemAction.registerEquipment(TRIMMED_CAPE, "Toggle Respawn", DefenceSkillCape::defenceRespawn);
    }

    public static void check(Player player) {
        if (player.tournament != null || player.lmsSession != null || player.joinedTournament)
            return;
        if (player.getDuel().stage >= 4)
            return;
        if (player.getHp() <= player.getMaxHp() * 0.10 && !player.getCombat().isDead()) {
            Item cape = player.getEquipment().get(Equipment.SLOT_CAPE);
            if (cape == null || cape.getId() != (9753 | 9754))
                return;
            if (ModernTeleport.teleport(player, World.HOME)) {
                player.setHp(player.getMaxHp());
                player.clearHits();
                player.sendFilteredMessage("Your cape saves you.");
            }
        }
    }

    private static void defenceToggle(Player player, Item item) {
        player.sendMessage("The cape doesn't wish to see you harmed and will remain active always.");
    }

    private static void defenceRespawn(Player player, Item item) {
        player.sendMessage("The cape will only see you safely to home.");
    }
}

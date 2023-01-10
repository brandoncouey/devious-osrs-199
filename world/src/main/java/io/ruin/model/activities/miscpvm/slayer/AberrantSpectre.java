package io.ruin.model.activities.miscpvm.slayer;

import io.ruin.cache.Color;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.slayer.Slayer;
import io.ruin.model.stat.StatType;

public class AberrantSpectre extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(336, 37, 38, 5, 30, 15, 16, 0);
    private static final StatType[] DRAIN = {StatType.Attack, StatType.Strength, StatType.Defence, StatType.Ranged, StatType.Magic};

    public static void killCounter(Player player, NPC npc) {
        if (player.aberrantSpectreKills.getKills() <= 1) {
            player.openInterface(InterfaceType.SECONDARY_OVERLAY, 660);
            player.getPacketSender().sendClientScript(3343, "iss", 0xff981f, "Combat Task Completed!", "Task Completed:" + Color.WHITE.wrap(" Noxious Foe"));
            Config.NOXIOUS_FOE.set(player, 1);
            Config.COMBAT_ACHIVEMENTS_OVERVIEW_EASY.increment(player, 1);
        }
    }

    @Override
    public void init() {
        npc.deathEndListener = (entity, killer, killHit) -> killCounter(killer.player, npc);
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        npc.animate(info.attack_animation);
        int delay = PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, info.attack_style).clientDelay(delay);
        if (target.player != null && target.player.getEquipment().getId(Equipment.SLOT_HAT) != 4168 && !Slayer.hasSlayerHelmEquipped(target.player)) {
            hit.randDamage(info.max_damage + 3).ignoreDefence().ignorePrayer();
            for (StatType statType : DRAIN) {
                target.player.getStats().get(statType).drain(6);
            }
            target.player.sendMessage("<col=ff0000>The aberrant spectre's stench disorients you!");
            target.player.sendMessage("<col=ff0000>A nose peg can protect you from this attack.");
        } else {
            hit.randDamage(info.max_damage);
        }
        target.hit(hit);
        return true;
    }
}

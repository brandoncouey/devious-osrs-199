package io.ruin.model.activities.miscpvm.slayer;

import io.ruin.cache.ItemID;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.combine.SlayerHelm;

public class CaveHorror extends NPCCombat {

    private static final int BLACK_MASK = 8901;
    private static final int BLACK_MASK_IMBUE = 11784;
    private static final int SPINY_HELM = 4551;
    private static final int SLAYER_HELM = 11864;
    private static final int SLAYER_HELM_IMBUE = 11865;

    private static final int RED_SLAYER_HELM = 19647;
    private static final int RED_HELM_IMBUE = 19649;
    private static final int GREEN_SLAYER_HELM = 19643;
    private static final int GREEN_HELM_IMBUE = 19645;
    private static final int BLACK_SLAYER_HELM = 19639;
    private static final int BLACK_HELM_IMBUE = 19641;
    private static final int PURPLE_SLAYER_HELM = 21264;
    private static final int PURPLE_HELM_IMBUE = 21266;
    private static final int TURQUOISE_SLAYER_HELM = 21888;
    private static final int TURQUOISE_HELM_IMBUE = 21890;
    private static final int HYDRA_SLAYER_HELM = ItemID.HYDRA_SLAYER_HELMET;
    private static final int HYDRA_HELM_IMBUE = ItemID.HYDRA_SLAYER_HELMET_I;

    private static boolean hasFaceMask(Player player) {
        Item faceMask = player.getEquipment().findFirst(SlayerHelm.FACEMASK, 21889, 21890, 21891, 23073, 23074, 23075, 23076, 24370, 24371, 24444,
                24445, 25177, 25178, 25179, 25180, 25181, 25182, 25183, 25184, 25185, 25186, 25187, 25188, 25189, 25190, 25191, 25192, 25898, 25899, 25900, 25901,
                25902, 25903, 25904, 25905, 25906, 25907, 25908, 25909, 25910, 25911, 25912, 25913, 25914, 25915, 8923, 11864, 11865, 19639, 19641, 19643, 19645, 19647, 19649,
                21264, 21266, 21888, 21890, 23073, 23075, 24370, 24444, 25898, 25900, 25902, 25904, 25906, 25908, 25910, 25912, 25914,
                8901, 8903, 8905, 8907, 8909, 8911, 8913, 8915, 8917, 8919, 8921, // black masks
                SLAYER_HELM, RED_SLAYER_HELM, GREEN_SLAYER_HELM, BLACK_SLAYER_HELM, PURPLE_SLAYER_HELM, TURQUOISE_SLAYER_HELM, HYDRA_SLAYER_HELM, 24370, 25898, 25904, 25910);
        return faceMask != null;
    }

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        if (target.player != null && !hasFaceMask(target.player)) {
            npc.animate(4237);
            target.hit(new Hit(npc, AttackStyle.MAGIC).fixedDamage(target.getMaxHp() / 10).ignoreDefence().ignorePrayer());
            if (target.player != null) {
                target.player.sendMessage("<col=ff0000>The cave horror's scream rips through you!");
                target.player.sendMessage("<col=ff0000>A witchwood icon or Slayer helmet can protect you from this attack.");
            }
        } else
            basicAttack();
        return true;
    }
}

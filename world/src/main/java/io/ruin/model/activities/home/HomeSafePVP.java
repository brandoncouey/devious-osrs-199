package io.ruin.model.activities.home;

import io.ruin.model.activities.clanwars.FFAClanWars;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAction;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.object.actions.ObjectAction;

public class HomeSafePVP {

    public static final Bounds HOME_FFA_FIGHT_BOUNDS = new Bounds(3573, 3096, 3583, 3131, 1);

    private static final Bounds HOME_DEATH_BOUNDS = new Bounds(3566, 3116, 3572, 3131, 1);

    private static final Bounds HOME_SAFE_BOUNDS = new Bounds(3566, 3116, 3572, 3131, 1);

    static {
        ObjectAction.register(11805, 3541, 3112, 1, "climb-up", (player, obj) -> player.getMovement().teleport(3541, 3116, 2));
        ObjectAction.register(11800, 3541, 3113, 2, "climb-down", (player, obj) -> player.getMovement().teleport(3541, 3111, 1));
        ObjectAction.register(11805, 3526, 3123, 0, "climb-up", (player, obj) -> player.getMovement().teleport(3531, 3122, 1));
        ObjectAction.register(11800, 3528, 3122, 1, "climb-down", (player, obj) -> player.getMovement().teleport(3525, 3123, 0));

        /**
         * Enter/exit bounds
         */
        MapListener.registerBounds(HOME_SAFE_BOUNDS)
                .onEnter(player -> {
                    player.openInterface(InterfaceType.SECONDARY_OVERLAY, Interface.FFA_CLAN_WARS);
                    player.getPacketSender().sendVarp(20003, 0);
                    player.openInterface(InterfaceType.WILDERNESS_OVERLAY, Interface.WILDERNESS_OVERLAY);
                    player.getPacketSender().setHidden(Interface.WILDERNESS_OVERLAY, 26, true);//do we remove this?
                })
                .onExit((player, logout) -> {
                    if (!logout) {
                        player.closeInterface(InterfaceType.SECONDARY_OVERLAY);
                        player.closeInterface(InterfaceType.WILDERNESS_OVERLAY);
                    }
                });
        MapListener.registerBounds(HOME_FFA_FIGHT_BOUNDS)
            .onEnter(player -> {
        player.setAction(1, PlayerAction.ATTACK);
        player.attackPlayerListener = HomeSafePVP::allowAttack;
        player.deathEndListener = (DeathListener.Simple) () -> {
            player.getMovement().teleport(HOME_DEATH_BOUNDS.randomPosition());
            player.sendMessage("Oh dear, you have died!");
        };
        Config.IN_PVP_AREA.set(player, 0);
        player.getPacketSender().setHidden(90, 47, true);

            })
            .onExit((player, logout) -> {
        if (!logout) {
            player.setAction(1, null);
            player.getCombat().resetTb();
            player.getCombat().resetKillers();
            player.getPacketSender().setHidden(90, 47, false);
            Config.IN_PVP_AREA.set(player, 0);
            player.clearHits();
            player.pvpAttackZone = false;
            player.attackPlayerListener = null;
            player.deathEndListener = null;
        }
    });
}

    public static boolean allowAttack(Player player, Player pTarget, boolean message) {
        if (!player.getPosition().inBounds(HOME_FFA_FIGHT_BOUNDS)) {
            player.sendMessage("You can't attack players from where you're standing.");
            return false;
        }
        if (!pTarget.getPosition().inBounds(HOME_FFA_FIGHT_BOUNDS)) {
            player.sendMessage("You can't attack players who aren't who haven't stepped over the line.");
            return false;
        }
        return true;
    }

}

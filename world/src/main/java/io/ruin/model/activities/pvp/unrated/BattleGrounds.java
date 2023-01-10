package io.ruin.model.activities.pvp.unrated;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LogoutListener;
import io.ruin.model.inter.InterfaceType;

import java.util.ArrayList;

public class BattleGrounds {

    private boolean ended;

    private final BattleGroundsSettings settings;

    private final ArrayList<Player> players = new ArrayList<>();

    BattleGrounds(BattleGroundsSettings settings) {
        this.settings = settings;
    }

    public boolean ended() {
        return ended;
    }

    public void start(ArrayList<Player> participants) {
        players.addAll(participants);
        players.forEach(player -> {
            player.battleGroundsGame = this;
            player.getMovement().teleport(3100, 3504, 0);
            player.teleportListener = p -> {
                p.sendMessage("Spells of this kind are not allowed within the battlegrounds.");
                return false;
            };
            player.logoutListener = new LogoutListener().onLogout(this::leave);
            player.sendMessage("Successfully entered a Battleground game here");
        });
    }

    public void leave(Player player) {
        clearActivityOf(player);
        players.remove(player);
        player.getMovement().teleport(3100, 3500, 0);
    }

    private void clearActivityOf(Player player) {
        player.closeInterface(InterfaceType.PRIMARY_OVERLAY);
        player.battleGroundsGame = null;
        player.teleportListener = null;
        player.deathEndListener = null;
        player.logoutListener = null;
        player.getCombat().restore();
    }

}
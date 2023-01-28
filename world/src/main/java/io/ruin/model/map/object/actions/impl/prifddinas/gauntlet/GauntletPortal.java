package io.ruin.model.map.object.actions.impl.prifddinas.gauntlet;


import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.prifddinas.amlodddistrict.gauntlet.TheGauntlet;

public class GauntletPortal {

    public static final Bounds GAUNTLET_BOUNDS = new Bounds(new Position(1975, 5682, 1), 24);

    static {
        ObjectAction.register(36081, "enter", (player, obj) -> toGauntlet(player));
        ObjectAction.register(36082, "channel", (player, obj) -> leaveGauntlet(player));
        ObjectAction.register(37340, 1, (player, obj) -> enterGauntlet(player));
    }

    public static void toGauntlet(Player player) {
        player.lock();
        player.dialogue(
                new MessageDialogue("<col=880000>Welcome to The Gauntlet.")
        );
        player.getMovement().teleport(3036, 6124, 1);
        player.unlock();
    }

    public static void leaveGauntlet(Player player) {
        player.lock();
        player.dialogue(
                new MessageDialogue("Welcome to the Almodd District")
        );
        player.getMovement().teleport(3228, 6116, 0);
        player.unlock();
    }

    public static void enterGauntlet(Player player) {
        TheGauntlet.enterGauntlet(player);
    }

}


package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.cache.Color;
import io.ruin.cache.ItemID;
import io.ruin.model.activities.pvminstances.InstanceDialogue;
import io.ruin.model.activities.pvminstances.InstanceType;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.combine.SlayerHelm;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.object.actions.ObjectAction;

public class SmokeDevilDungeon {
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
                25902, 25903, 25904, 25905, 25906, 25907, 25908, 25909, 25910, 25911, 25912, 25913, 25914, 25915, 8923, 11864, 11865,
                8901, 8903, 8905, 8907, 8909, 8911, 8913, 8915, 8917, 8919, 8921, 19649,
                19641,
                19645,
                21266,// black masks
                SLAYER_HELM, RED_SLAYER_HELM, GREEN_SLAYER_HELM, BLACK_SLAYER_HELM, PURPLE_SLAYER_HELM, TURQUOISE_SLAYER_HELM, HYDRA_SLAYER_HELM, 24370, 25898, 25904, 25910);
        return faceMask != null;
    }

    private static final Bounds DUNGEON_BOUNDS = new Bounds(2317, 9418, 2431, 9471, -1);
    private static final Bounds DUNGEON_BOSS = new Bounds(2350, 9436, 2378, 9460, -1);

    private static int playerCount;

    static {
        /**
         * Entrance/exit
         */
        ObjectAction.register(30176, 2411, 3061, 0, "enter", (player, obj) -> {
            if (hasFaceMask(player)) {
                player.getMovement().teleport(2404, 9415);
            } else {
                player.dialogue(new NPCDialogue(7654, "Hey you don't " + Color.COOL_BLUE.wrap("*cough*") + " wanna go in there without " + Color.COOL_BLUE.wrap("*cough*") +
                                " some kind of protection from the smoke. Your lungs aren't as tough as " + Color.COOL_BLUE.wrap("*wheeze*") + " mine."),
                        new OptionsDialogue(
                                new Option("Enter anyway.", () -> player.getMovement().teleport(2404, 9415)),
                                new Option("Stay outside.", player::closeDialogue)
                        ));
            }
        });
        ObjectAction.register(534, 2404, 9414, 0, "use", (player, obj) -> player.getMovement().teleport(2412, 3060));

        /**
         * Boss room
         */
        ObjectAction.register(535, 2378, 9452, 0, "use", (player, obj) -> {
            if (player.smokeBossWarning) {
                player.dialogue(
                        new MessageDialogue(Color.DARK_RED.wrap("WARNING!") +
                                "<br> This is the lair of the Smoke Devil boss. <br> Are you sure you want to enter?").lineHeight(24),
                        new OptionsDialogue("Enter the boss area?",
                                new Option("Yes.", () -> player.getMovement().teleport(2376, 9452)),
                                new Option("Yes, and don't warn me again.", () -> {
                                    player.getMovement().teleport(2376, 9452);
                                    player.smokeBossWarning = false;
                                }),
                                new Option("No.", player::closeDialogue)
                        )
                );
            } else {
                player.getMovement().teleport(2376, 9452);
            }
        });
        ObjectAction.register(535, 2378, 9452, 0, "peek", (player, obj) -> {
            if (playerCount == 0)
                player.sendFilteredMessage("You look inside the crevice and see no adventurers inside the cave.");
            else
                player.sendFilteredMessage("You look inside the crevice and see " + playerCount + " adventurer" + (playerCount == 1 ? "" : "s") + " inside the cave.");
        });
        ObjectAction.register(535, 2378, 9452, 0, "instance", (player, obj) -> InstanceDialogue.open(player, InstanceType.THERMONUCLEAR_SMOKE_DEVIL));
        ObjectAction.register(536, 2377, 9452, 0, "use", (player, obj) -> player.getMovement().teleport(2379, 9452));

        MapListener.registerBounds(DUNGEON_BOSS).onEnter(player -> playerCount++).onExit((player, logout) -> playerCount--);

        MapListener.registerBounds(DUNGEON_BOUNDS).onEnter(player -> player.addEvent(e -> {
            while (player.getPosition().inBounds(DUNGEON_BOUNDS)) {
                if (hasFaceMask(player))
                    return;
                player.hit(new Hit().fixedDamage(20));
                e.delay(50);
            }
        }));
    }
}

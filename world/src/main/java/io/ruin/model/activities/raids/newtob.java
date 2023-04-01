package io.ruin.model.activities.raids;

import io.ruin.api.utils.Random;
import io.ruin.api.utils.TimeUtils;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;

import java.util.concurrent.TimeUnit;

public class newtob {

    public static DynamicMap map = new DynamicMap().build(12611, 1);

    private static final NPC verzik = new NPC(8369).spawn(map.convertX(3166), map.convertY(4323), 0, 0);

    public static Bounds arenaBounds = new Bounds(map.convertX(3155), map.convertY(4304), map.convertX(3181), map.convertY(4322), 0);

    public static long nextspawn = 0;

    public static boolean spawned = false;

    static {
        /* *
         * Verzik
         * */

        ObjectAction.register(41437, 1, (player, obj) -> {
            if (player.TheatreOfBloodCompletions <= 0) {
                player.dialogue(new NPCDialogue(8369, "You don't have any remaining rewards to claim!"));
                return;
            }
            player.TOBChests += 1;

            player.sendFilteredMessage("<col=006400>You have opened a total of " + player.TOBChests + " Theatre Of Blood Chests!");
            Item rolled = rollRegular();
            player.TheatreOfBloodCompletions -= 1;
            if (rolled.getAmount() > 1 && !rolled.getDef().stackable && !rolled.getDef().isNote())
                rolled.setId(rolled.getDef().notedId);

            if (player.getInventory().isFull()) {
                player.getBank().add(rolled.getId(), rolled.getAmount());
                player.sendMessage("Your inventory was full so we placed the items in your bank!");
            } else {
                player.getInventory().addOrDrop(rolled);
            }
            player.sendMessage("<shad=000000>" + Color.COOL_BLUE.wrap("You got " + rolled.getDef().name + " X " + rolled.getAmount()) + "</shad>");
            player.getCollectionLog().collect(rolled);
            if (rolled.lootBroadcast != null) {
                Broadcast.GLOBAL.sendNews("<shad=000000>" + Color.ORANGE.wrap(player.getName() + " has just received " + rolled.getDef().name + " from Theatre Of Blood!" + "</shad>"));
            }
        });

        World.startEvent(event -> {
            while (true) {
                if (nextspawn == 0 || nextspawn < System.currentTimeMillis() && !spawned) {
                    new NPC(8374).spawn(map.convertX(3165), map.convertY(4314), 0).getCombat().setAllowRespawn(false);
                    nextspawn = (System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2));
                    spawned = true;
                }
                event.delay(5);
            }
        });

        NPCAction.register(8369, 1, (player, npc) -> {
            if (spawned) {
                player.dialogue(new NPCDialogue(npc, "What are you doing child!, help me save man kind by killing my evil sister!"));
            } else {
                player.dialogue(new NPCDialogue(npc, "There is " + TimeUtils.fromMs(nextspawn - System.currentTimeMillis(), true) + ", until my sister respawns!"));
            }
        });

        MapListener.registerMap(map).onEnter(player -> {
            player.tobcannon = true;
        }).onExit((player1, logout) -> {
            if (logout)
                player1.getMovement().teleport(3674, 3219, 0);
            player1.tobcannon = false;
        });


        ObjectAction.register(32653, "enter", (player, obj) -> {
            if (player.getCombat().getLevel() < 110) {
                player.dialogue(new NPCDialogue(8369, "You need atleast 110 Combat level, to even attempt this challenge!"));
                return;
            }
            player.getMovement().startTeleport(event -> {
                player.getPacketSender().fadeOut();
                event.delay(1);
                player.getMovement().teleport(map.convertX(3168), map.convertY(4303), 0);
                player.getPacketSender().clearFade();
                player.dialogue(new MessageDialogue("You enter Verzik's Chamber!"));
            });
        });

        //Chest
        GameObject.spawn(31949, map.convertX(3154), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3155), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3156), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3157), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3158), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3159), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3160), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3161), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3162), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3163), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3164), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3165), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3166), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3170), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3171), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3172), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3173), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3174), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3175), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3176), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3177), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3178), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3179), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3180), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3181), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
        GameObject.spawn(31949, map.convertX(3182), map.convertY(4303), 0, 10, 0).tile.flagUnmovable();
    }

    public static LootTable regularTable = new LootTable()
            .addTable(1,
                    new LootItem(995, Random.get(200000, 250000), 60), // Coins
                    new LootItem(1514, Random.get(50, 250), 100), // Magic Logs
                    new LootItem(5316, Random.get(3, 5), 100), //magic seed
                    new LootItem(1320, Random.get(2, 10), 100), //Rune 2h sword
                    new LootItem(1304, Random.get(2, 11), 100), //Rune longsword
                    new LootItem(1290, Random.get(2, 11), 100), //Rune sword
                    new LootItem(1276, Random.get(2, 10), 100), //Rune pickaxe
                    new LootItem(1128, Random.get(2, 8), 100), // Rune Platebody
                    new LootItem(1320, Random.get(2, 10), 100), //Rune 2h sword
                    new LootItem(1304, Random.get(2, 11), 100), //Rune longsword
                    new LootItem(1290, Random.get(2, 11), 100), //Rune sword
                    new LootItem(1276, Random.get(2, 10), 100), //Rune pickaxe
                    new LootItem(1128, Random.get(2, 8), 100), // Rune Platebody
                    new LootItem(4088, Random.get(2, 3), 80), // Dragon platelegs
                    new LootItem(5731, Random.get(1, 7), 80), // dragon spear
                    new LootItem(22804, Random.get(50, 125), 80), // Dragon Knife
                    new LootItem(19484, Random.get(50, 125), 80), // Dragon javelin
                    new LootItem(6694, Random.get(30, 90), 80), //Crushed nest
                    new LootItem(3052, Random.get(25, 80), 80), // Grimy Snapdragon
                    new LootItem(208, Random.get(10, 20), 80), //Grimy Ranarr
                    new LootItem(5300, Random.get(10, 25), 80), // Snapdragon seed
                    new LootItem(3025, Random.get(20, 50), 80), // Super Restore(4)
                    new LootItem(12696, 25, 60), // super combat potion(4) noted x25
                    new LootItem(6686, 50, 60), // saradomin brew(4) noted x50
                    new LootItem(561, Random.get(600, 700), 40), //nature rune
                    new LootItem(563, Random.get(500, 600), 40), //law rune
                    new LootItem(560, Random.get(600, 700), 40), // Death rune
                    new LootItem(565, Random.get(500, 700), 40), // Blood rune
                    new LootItem(21880, Random.get(300, 500), 40), //Wrath rune
                    new LootItem(454, Random.get(115, 120), 40), // Coal ore
                    new LootItem(450, Random.get(125, 250), 40), // Adamant ore
                    new LootItem(452, Random.get(175, 350), 35), // Runite ores
                    new LootItem(12073, 1, 11), // Elite clue scroll
                    new LootItem(6199, 1, 11), // mystery box
                    new LootItem(290, 1, 5).broadcast(Broadcast.GLOBAL), // Luxury mystery box
                    new LootItem(6571, 1, 2).broadcast(Broadcast.GLOBAL), // Uncut Onyx
                    new LootItem(22477, 1, 2).broadcast(Broadcast.GLOBAL), // Avernic Hilt
                    new LootItem(22326, 1, 2).broadcast(Broadcast.GLOBAL), // Justi helm
                    new LootItem(22327, 1, 2).broadcast(Broadcast.GLOBAL), // Justi plate
                    new LootItem(22328, 1, 2).broadcast(Broadcast.GLOBAL), // Justi legs
                    new LootItem(22324, 1, 2).broadcast(Broadcast.GLOBAL), // Rapier
                    new LootItem(25744, 1, 2).broadcast(Broadcast.GLOBAL), // Sanguine Ornament Kit
                    new LootItem(25742, 1, 2).broadcast(Broadcast.GLOBAL), // Holy Ornament Kit
                    new LootItem(22481, 1, 1).broadcast(Broadcast.GLOBAL), // Sang Staff
                    new LootItem(22486, 1, 1).broadcast(Broadcast.GLOBAL) // Scythe
            );

    private static Item rollRegular() {
        return regularTable.rollItem();
    }

}

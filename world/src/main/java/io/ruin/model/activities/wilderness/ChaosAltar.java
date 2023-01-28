package io.ruin.model.activities.wilderness;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.diaries.devious.DeviousDiaryEntry;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.PrayerAltar;
import io.ruin.model.skills.prayer.Bone;
import io.ruin.model.stat.StatType;

import static io.ruin.cache.ItemID.COINS_995;

public class ChaosAltar {

    private static final int CHAOS_ALTAR = 411;
    private static final int ELDER_CHAOS_DRUID = 7995;
    private static int COST_PER_BONE = 200;

    static {
        NPCAction.register(ELDER_CHAOS_DRUID, "talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "Hello, and welcome to The Dark Lord's temple. Zamorak will reward those who offer the bones of the vanquished on his altar."),
                new NPCDialogue(npc, "I can exchange banknotes for bones, should you require such a service."),
                new NPCDialogue(npc, "My services, however, are not free. I charge " + COST_PER_BONE + " coins per banknote."),
                new OptionsDialogue(
                        new Option("Yes please.", () -> player.dialogue(
                                new PlayerDialogue("Yes please."),
                                new NPCDialogue(npc, "Hand me the banknotes you wish to exchange."))),
                        new Option("No thanks.", () -> player.dialogue(new PlayerDialogue("No thanks.")))
                )
        ));

        ObjectAction.register(CHAOS_ALTAR, 1, (player, obj) -> PrayerAltar.pray(player));

        for (Bone bone : Bone.values()) {
            ItemObjectAction.register(bone.id, CHAOS_ALTAR, ChaosAltar::boneOnAltar);
            ItemObjectAction.register(bone.id, 27501, (player, item, obj) -> boneOutWildyAltar(player, item, Bone.get(item.getId())));
            if (bone.notedId == -1)
                continue;
            ItemNPCAction.register(bone.notedId, 6955, (player, npc, item) -> {
                COST_PER_BONE = 500;
                promptForAmount(player, npc, item);
            });
            ItemNPCAction.register(bone.notedId, ELDER_CHAOS_DRUID, (player, npc, item) -> {
                COST_PER_BONE = 200;
                promptForAmount(player, npc, item);
            });
        }
    }

    /**
     * Elder Chaos Druid
     */

    private static void promptForAmount(Player player, Item item, NPC npc) {
        Item coins = player.getInventory().findItem(COINS_995);
        if (coins == null || coins.getAmount() < COST_PER_BONE) {
            player.dialogue(new NPCDialogue(npc, "I charge " + COST_PER_BONE + " coins for exchanging each banknote."));
            return;
        }

        player.dialogue(new OptionsDialogue(
                new Option("Exchange '" + item.getDef().name + "': " + COST_PER_BONE + " coins", () -> exchange(player, item, 1, npc)),
                new Option("Exchange All: " + (item.getAmount() * COST_PER_BONE), () -> exchange(player, item, Integer.MAX_VALUE, npc)),
                new Option("Exchange X", () -> player.integerInput("How many would you like to exchange?", amt -> exchange(player, item, amt, npc)))
        ));
    }

    private static void exchange(Player player, Item item, int amt, NPC npc) {
        if (npc.getId() != 7995) {
            COST_PER_BONE = 1;
        }
        if (amt > item.getAmount())
            amt = item.getAmount();
        while (amt-- > 0) {
            Item coins = player.getInventory().findItem(COINS_995);
            if (coins == null || coins.getAmount() < COST_PER_BONE)
                break;
            if (player.getInventory().isFull())
                break;
            item.remove(1);
            coins.remove(250);
            player.getInventory().add(item.getDef().fromNote().id, 1);
        }
        player.dialogue(new ItemDialogue().one(item.getDef().fromNote().id, npc.getDef().name + " converts your banknote."));
    }

    /**
     * Chaos Altar
     */

    private static final Bounds WILDY = new Bounds(2945, 3819, 2949, 3822, 0);

    private static void boneOnAltar(Player player, Item item, Object obj) {
        if (!player.getPosition().inBounds(WILDY)) {
            boneOutWildyAlter2(player, item, Bone.get(item.getId()));
            return;
        }
        player.startEvent(event -> {
            while (true) {
                Item bone = player.getInventory().findItem(item.getId());
                if (bone == null)
                    break;
                if (Random.rollDie(100, (int) player.chaosAltarBoneChance)) {
                    player.sendMessage("The Dark Lord spares your sacrifice but still rewards you for your efforts.");
                    player.chaosAltarBoneChance = player.chaosAltarBoneChance / 2;
                } else {
                    player.chaosAltarBoneChance = 50;
                    player.getInventory().remove(item.getId(), 1);
                }
                player.animate(3705);
                player.getStats().addXp(StatType.Prayer, Bone.get(item.getId()).exp * 1.5, true);
                World.sendGraphics(624, 10, 0, player.getAbsX() - 1, player.getAbsY(), 0);
                Bone.get(item.getId()).altarCounter.increment(player, 1);
                event.delay(2);
            }
        });
    }

    private static void boneOutWildyAlter2(Player player, Item item, Bone bone) {
        player.startEvent(event -> {
            while (true) {
                Item bone2 = player.getInventory().findItem(item.getId());
                if (bone2 == null)
                    break;
                player.getInventory().remove(item.getId(), 1);
                player.animate(3705);
                player.getStats().addXp(StatType.Prayer, Bone.get(item.getId()).exp * 1.5, true);
                World.sendGraphics(624, 10, 0, player.getAbsX() - 1, player.getAbsY(), 0);
                Bone.get(item.getId()).altarCounter.increment(player, 1);
                event.delay(2);
            }
        });
    }

    private static void boneOutWildyAltar(Player player, Item item, Bone bone) {
        player.startEvent(event -> {
            while (true) {
                Item bones = player.getInventory().findItem(item.getId());
                if (bones == null)
                    break;
                item.remove();
                player.animate(3705);
                player.sendFilteredMessage("The gods are pleased with your offerings.");
                player.getStats().addXp(StatType.Prayer, bone.exp * 1.5, true);
                World.sendGraphics(624, 0, 0, 3095, 3505, 0);
                bone.altarCounter.increment(player, 1);
                event.delay(2);
                if (bone.id == 536 && player.getPosition().regionId() == 6967) {
                    //player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.DBONE_ALTAR);
                }
            }
        });
    }
}

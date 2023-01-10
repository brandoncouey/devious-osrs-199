package io.ruin.model.skills.fishing;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.pets.Pets;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Bounds;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

public class FishingSpot {

    private FishingTool tool;

    private FishingCatch[] regularCatches, barehandCatches;

    private FishingSpot(FishingTool tool) {
        this.tool = tool;
    }

    private FishingSpot regularCatches(FishingCatch... regularCatches) {
        this.regularCatches = regularCatches;
        return this;
    }

    private FishingSpot barehandCatches(FishingCatch... barehandCatches) {
        this.barehandCatches = barehandCatches;
        return this;
    }

    private FishingCatch randomCatch(int level, boolean barehand, FishingTool tool) {
        FishingCatch[] catches = barehand ? barehandCatches : regularCatches;
        double roll = Random.get();
        for (int i = catches.length - 1; i >= 0; i--) {
            FishingCatch c = catches[i];
            int levelDifference = level - c.levelReq;
            if (levelDifference < 0) {
                /* not high enough level */
                continue;
            }
            double chance = c.baseChance;
            if (chance >= 1.0) {
                /* always catch this bad boy */
                return c;
            }
            if (tool == FishingTool.DRAGON_HARPOON)
                chance += 1.20;
            chance += (double) levelDifference * 0.01;
            if (roll > Math.min(chance, 0.90)) {
                /* failed to catch */
                continue;
            }
            return c;
        }
        return null;
    }

    private static final Bounds DZONE = new Bounds(3815, 2827, 3805, 2819, 1);
    private static final Bounds DZONE2 = new Bounds(3808, 2821, 3812, 2826, 1);

    public void fish(Player player, NPC npc) {
        boolean barehand;
        Stat fishing = player.getStats().get(StatType.Fishing);
        if (tool == FishingTool.HARPOON && hasDragonHarpoon(player)) {
            if (fishing.currentLevel < 61) {
                player.sendMessage("You need a Fishing level of at least 61 to fish with a dragon harpoon.");
                return;
            }

            tool = FishingTool.DRAGON_HARPOON;
        }
        if (tool == FishingTool.HARPOON && hasCrystalHarpoon(player)) {
            if (fishing.currentLevel < 71) {
                player.sendMessage("You need a Fishing level of at least 71 to fish with a crystal harpoon.");
                return;
            }

            tool = FishingTool.CRYSTAL_HARPOON;
        }

        if (player.getInventory().contains(new Item(tool.id)) || (tool == FishingTool.HARPOON && hasDragonHarpoon(player) || (tool == FishingTool.HARPOON && hasCrystalHarpoon(player)))) {
            FishingCatch lowestCatch = regularCatches[0];

            if (fishing.currentLevel < lowestCatch.levelReq) {
                player.sendMessage("You need a Fishing level of at least " + lowestCatch.levelReq + " to fish at this spot.");
                return;
            }

            barehand = false;
        } else {
            if (barehandCatches == null) {
                player.sendMessage("You need a " + tool.primaryName + " to fish at this spot.");
                return;
            }

            FishingCatch lowestCatch = barehandCatches[0];

            if (fishing.currentLevel < lowestCatch.levelReq) {
                player.sendMessage("You need a Fishing level of at least " + lowestCatch.levelReq + " to barehand fish at this spot.");
                player.sendMessage("To fish at this spot normally, you'll need a " + tool.primaryName + ".");
                return;
            }

            if (player.getStats().get(StatType.Agility).currentLevel < lowestCatch.agilityReq) {
                player.sendMessage("You need an Agility level of at least " + lowestCatch.agilityReq + " to barehand fish at this spot.");
                player.sendMessage("To fish at this spot normally, you'll need a " + tool.primaryName + ".");
                return;
            }

            if (player.getStats().get(StatType.Strength).currentLevel < lowestCatch.strengthReq) {
                player.sendMessage("You need a Strength level of at least " + lowestCatch.strengthReq + " to barehand fish at this spot.");
                player.sendMessage("To fish at this spot normally, you'll need a " + tool.primaryName + ".");
                return;
            }

            barehand = true;
        }

        Item secondary;
        if (barehand || tool.secondaryId == -1) {
            secondary = null;
        } else if ((secondary = player.getInventory().findItem(tool.secondaryId)) == null) {
            player.sendMessage("You need at least one " + tool.secondaryName + " to fish at this spot.");
            return;
        }

        if (player.getInventory().isFull() && !player.getInventory().contains(11935)) {
            player.sendMessage("Your inventory is too full to hold any more fish.");
            player.resetAnimation();
            return;
        }

        if (npc.getId() == INFERNO_EEL) {
            if (!player.getEquipment().hasId(1580)) {
                player.sendMessage("You need to be wearing ice gloves to catch infernal eel.");
                return;
            }
        }

        /**
         * Start event
         */
        player.animate(barehand ? 6703 : tool.startAnimationId);
        player.startEvent(event -> {
            int animTicks = 2;
            boolean firstBarehandAnim = true;
            while (true) {
                if (animTicks > 0) { //we do this so we can check if the npc has moved every tick
                    int diffX = Math.abs(player.getAbsX() - npc.getAbsX());
                    int diffY = Math.abs(player.getAbsY() - npc.getAbsY());
                    if (diffX + diffY > 1) {
                        player.resetAnimation();
                        return;
                    }

                    event.delay(1);
                    animTicks--;
                    continue;
                }

                FishingCatch c = randomCatch(fishing.currentLevel, barehand, tool);
                if (c != null) {
                    if (npc.getId() == MINNOWS && (npc.minnowsFish)) {
                        if (npc.getPosition().inBounds(DZONE)) {
                            npc.minnowsFish = false;
                        }
                        npc.graphics(1387);
                        player.getInventory().remove(c.id, 26);
                        player.sendFilteredMessage("A flying fish jumps up and eats some of your minnows!");
                    } else {
                        if (secondary != null)
                            secondary.incrementAmount(-1);

                        if (npc.getId() == MINNOWS)
                            player.sendFilteredMessage("You catch some minnows!");

                        if (npc.getId() == INFERNO_EEL)
                            player.sendFilteredMessage("You catch an infernal eel. It hardens as you handle it with your ice gloves.");

                        int amount = npc.getId() == MINNOWS && !player.getPosition().inBounds(DZONE) ? Random.get(10, 26) : 1;

                        if (player.darkCrabBoost.isDelayed()) {
                            if (Random.rollPercent(20))
                                amount++;
                        }
                        if (player.isMember() && npc.getId() == CAGE) {
                            player.getInventory().add(c.notedid, amount);
                        } else
                            player.getInventory().add(c.id, amount);

                        if (npc.getId() != MINNOWS)
                            PlayerCounter.TOTAL_FISH.increment(player, 1);

                        player.getStats().addXp(StatType.Fishing, c.xp * anglerBonus(player), true);
                        if (Random.rollDie(50, 1)) {
                            player.getInventory().addOrDrop(6828, 1);
                            player.sendMessage("You've discovered a Skilling box. It's been added to your inventory.");
                        }
                        if (!player.getPosition().inBounds(DZONE2)) {
                            FishingClueBottle.roll(player, c, barehand);
                        }
                        if (Random.rollDie(c.petOdds))
                            Pets.HERON.unlock(player);

                        if (c.barbarianXp > 0) {
                            player.getStats().addXp(StatType.Agility, c.barbarianXp, true);
                            player.getStats().addXp(StatType.Strength, c.barbarianXp, true);
                            if (Random.rollDie(50, 1)) {
                                player.getInventory().addOrDrop(6828, 1);
                                player.sendMessage("You've discovered a Skilling box. It's been added to your inventory.");
                            }
                            if (barehand) {
                                if (c == FishingCatch.BARBARIAN_TUNA)
                                    player.animate(firstBarehandAnim ? 6710 : 6711);
                                else if (c == FishingCatch.BARBARIAN_SWORDFISH)
                                    player.animate(firstBarehandAnim ? 6707 : 6708);
                                else if (c == FishingCatch.BARBARIAN_SHARK)
                                    player.animate(firstBarehandAnim ? 6705 : 6706);

                                firstBarehandAnim = !firstBarehandAnim;
                                animTicks = 8;
                            }
                        }

                        if (player.getInventory().isFull() && !player.getInventory().contains(11935)) {
                            player.sendMessage("Your inventory is too full to hold any more fish.");
                            player.resetAnimation();
                            return;
                        }

                        if (!barehand && tool.secondaryId != -1) {
                            Item requiredSecondary = player.getInventory().findItem(tool.secondaryId);

                            if (requiredSecondary == null) {
                                player.sendMessage("You need at least one " + tool.secondaryName + " to fish at this spot.");
                                return;
                            }
                        }
                    }
                }

                if (animTicks == 0) {
                    player.animate(barehand ? 6704 : tool.loopAnimationId);
                    animTicks = 3;
                }
            }
        });
    }

    private static boolean hasDragonHarpoon(Player player) {
        if (player.getInventory().hasId(FishingTool.DRAGON_HARPOON.id))
            return true;

        ItemDef playerWeapon = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
        return playerWeapon != null && playerWeapon.id == FishingTool.DRAGON_HARPOON.id;
    }
    private static boolean hasCrystalHarpoon(Player player) {
        if (player.getInventory().hasId(FishingTool.CRYSTAL_HARPOON.id))
            return true;

        ItemDef playerWeapon = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
        return playerWeapon != null && playerWeapon.id == FishingTool.CRYSTAL_HARPOON.id;
    }


    private void register(int npcId, String option) {
        NPCAction.register(npcId, option, this::fish);
    }

    /**
     * :)
     */

    public static final int NET_BAIT = 1518;            //shrimps,anchovies / sardine,herring

    public static final int NET_BAIT_1544 = 1544;            //shrimps,anchovies / sardine,herring

    public static final int NET_BAIT_1517 = 1517;            //shrimps,anchovies / sardine,herring

    public static final int NET_BAIT_1523 = 1523;            //shrimps,anchovies / sardine,herring

    public static final int NET_BAIT_7155 = 7155;            //shrimps,anchovies / sardine,herring

    public static final int NET_BAIT_7467 = 7467;            //shrimps,anchovies / sardine,herring

    public static final int NET_BAIT_7469 = 7469;            //shrimps,anchovies / sardine,herring

    public static final int NET_BAIT_7459 = 7459;            //shrimps,anchovies / sardine,herring

    public static final int LURE_BAIT = 1506;           //trout,salmon / pike

    public static final int LURE_BAIT_1509 = 1509;           //trout,salmon / pike

    public static final int LURE_BAIT_1507 = 1507;           //trout,salmon / pike

    public static final int LURE_BAIT_3417 = 3417;           //trout,salmon / pike

    public static final int LURE_BAIT_3418 = 3418;           //trout,salmon / pike7468

    public static final int LURE_BAIT_7468 = 7468;           //trout,salmon / pike7468

    public static final int LURE_BAIT_1516 = 1516;           //trout,salmon / pike

    public static final int CAGE_HARPOON = 1519;        //lobster / tuna,swordfish

    public static final int CAGE_HARPOON_5820 = 5820;        //lobster / tuna,swordfish

    public static final int CAGE_HARPOON_3657 = 3657;        //lobster / tuna,swordfish

    public static final int CAGE_HARPOON_9174 = 9174;        //lobster / tuna,swordfish

    public static final int CAGE_HARPOON_9173 = 9173;        //lobster / tuna,swordfish

    public static final int CAGE_HARPOON_7946 = 7946;        //lobster / tuna,swordfish

    public static final int CAGE_HARPOON_7199 = 7199;        //lobster / tuna,swordfish

    public static final int CAGE_HARPOON_7465 = 7465;        //lobster / tuna,swordfish

    public static final int CAGE_HARPOON_7460 = 7460;        //lobster / tuna,swordfish

    public static final int BIG_NET_HARPOON = 1520;     //shark / tuna,swordfish

    public static final int BIG_NET_HARPOON_5233 = 5233;     //shark / tuna,swordfish

    public static final int BIG_NET_HARPOON_9172 = 9172;     //shark / tuna,swordfish

    public static final int BIG_NET_HARPOON_9171 = 9171;     //shark / tuna,swordfish

    public static final int BIG_NET_HARPOON_3419 = 3419;     //shark / tuna,swordfish

    public static final int BIG_NET_HARPOON_4476 = 4476;     //shark / tuna,swordfish

    public static final int BIG_NET_HARPOON_4477 = 4477;     //shark / tuna,swordfish

    public static final int BIG_NET_HARPOON_7466 = 7466;     //shark / tuna,swordfish

    public static final int BIG_NET_HARPOON_7461 = 7461;     //shark / tuna,swordfish

    public static final int BIG_NET_HARPOON_8525 = 8525;     //shark / tuna,swordfish

    public static final int SMALL_NET_HARPOON_7200 = 7200;     //monkfish / swordfish

    public static final int SMALL_NET_HARPOON = 4316;   //monkfish / swordfish

    public static final int SMALL_NET_HARPOON_8526 = 8526;   //monkfish / swordfish

    public static final int SMALL_NET_HARPOON_8527 = 8527;   //monkfish / swordfish

    public static final int USE_ROD = 1542;             //leaping

    public static final int USE_ROD_7323 = 7323;             //leaping

    public static final int CAGE = 1535;                //dark crab

    public static final int BAIT = 6825;                //angler

    public static final int BAIT_2653 = 2653;                //Slimy Eel

    public static final int BAIT_2654 = 2654;                //Slimy Eel

    public static final int MINNOWS = 7731;            //minnows

    public static final int INFERNO_EEL = 7676;        //infernal eel

    public static final int INFERNO_EEL_6488 = 6488;        //infernal eel

    public static final int KARAMBWAN_SPOT = 4712;

    public static final int TEMPOROSS_SPOT = 10568;

    public static final int MOLTEN_EEL = 15018;

    static {

        new FishingSpot(FishingTool.KARAMBWAN_VESSEL)
                .regularCatches(FishingCatch.KARAMBWAN)
                .register(KARAMBWAN_SPOT, "fish");
        /**
         * Net / Bait
         */
        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.SHRIMPS, FishingCatch.ANCHOVIES)
                .register(NET_BAIT, "small net");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.SARDINE, FishingCatch.HERRING)
                .register(NET_BAIT, "bait");

        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.SHRIMPS, FishingCatch.ANCHOVIES)
                .register(NET_BAIT_1544, "small net");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.SARDINE, FishingCatch.HERRING)
                .register(NET_BAIT_1544, "bait");

        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.SHRIMPS, FishingCatch.ANCHOVIES)
                .register(NET_BAIT_1517, "small net");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.SARDINE, FishingCatch.HERRING)
                .register(NET_BAIT_1517, "bait");

        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.SHRIMPS, FishingCatch.ANCHOVIES)
                .register(NET_BAIT_1523, "small net");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.SARDINE, FishingCatch.HERRING)
                .register(NET_BAIT_1523, "bait");

        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.SHRIMPS, FishingCatch.ANCHOVIES)
                .register(NET_BAIT_7155, "small net");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.SARDINE, FishingCatch.HERRING)
                .register(NET_BAIT_7155, "bait");

        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.SHRIMPS, FishingCatch.ANCHOVIES)
                .register(NET_BAIT_7467, "small net");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.SARDINE, FishingCatch.HERRING)
                .register(NET_BAIT_7467, "bait");

        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.SHRIMPS, FishingCatch.ANCHOVIES)
                .register(NET_BAIT_7469, "small net");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.SARDINE, FishingCatch.HERRING)
                .register(NET_BAIT_7469, "bait");

        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.SHRIMPS, FishingCatch.ANCHOVIES)
                .register(NET_BAIT_7459, "small net");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.SARDINE, FishingCatch.HERRING)
                .register(NET_BAIT_7459, "bait");
        /**
         * Lure / Bait
         */
        new FishingSpot(FishingTool.FLY_FISHING_ROD)
                .regularCatches(FishingCatch.TROUT, FishingCatch.SALMON)
                .register(LURE_BAIT, "lure");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.PIKE)
                .register(LURE_BAIT, "bait");

        new FishingSpot(FishingTool.FLY_FISHING_ROD)
                .regularCatches(FishingCatch.TROUT, FishingCatch.SALMON)
                .register(LURE_BAIT_1509, "lure");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.PIKE)
                .register(LURE_BAIT_1509, "bait");

        new FishingSpot(FishingTool.FLY_FISHING_ROD)
                .regularCatches(FishingCatch.TROUT, FishingCatch.SALMON)
                .register(LURE_BAIT_3417, "lure");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.PIKE)
                .register(LURE_BAIT_3417, "bait");

        new FishingSpot(FishingTool.FLY_FISHING_ROD)
                .regularCatches(FishingCatch.TROUT, FishingCatch.SALMON)
                .register(LURE_BAIT_3418, "lure");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.PIKE)
                .register(LURE_BAIT_3418, "bait");

        new FishingSpot(FishingTool.FLY_FISHING_ROD)
                .regularCatches(FishingCatch.TROUT, FishingCatch.SALMON)
                .register(LURE_BAIT_1516, "lure");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.PIKE)
                .register(LURE_BAIT_1516, "bait");

        new FishingSpot(FishingTool.FLY_FISHING_ROD)
                .regularCatches(FishingCatch.TROUT, FishingCatch.SALMON)
                .register(LURE_BAIT_1507, "lure");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.PIKE)
                .register(LURE_BAIT_1507, "bait");

        new FishingSpot(FishingTool.FLY_FISHING_ROD)
                .regularCatches(FishingCatch.TROUT, FishingCatch.SALMON)
                .register(LURE_BAIT_7468, "lure");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.PIKE)
                .register(LURE_BAIT_7468, "bait");
        /**
         * Cage / Harpoon
         */
        new FishingSpot(FishingTool.LOBSTER_POT)
                .regularCatches(FishingCatch.LOBSTER)
                .register(CAGE_HARPOON, "cage");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.TUNA, FishingCatch.SWORDFISH)
                .barehandCatches(FishingCatch.BARBARIAN_TUNA, FishingCatch.BARBARIAN_SWORDFISH)
                .register(CAGE_HARPOON, "harpoon");

        new FishingSpot(FishingTool.LOBSTER_POT)
                .regularCatches(FishingCatch.LOBSTER)
                .register(CAGE_HARPOON_5820, "cage");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.TUNA, FishingCatch.SWORDFISH)
                .barehandCatches(FishingCatch.BARBARIAN_TUNA, FishingCatch.BARBARIAN_SWORDFISH)
                .register(CAGE_HARPOON_5820, "harpoon");

        new FishingSpot(FishingTool.LOBSTER_POT)
                .regularCatches(FishingCatch.LOBSTER)
                .register(CAGE_HARPOON_3657, "cage");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.TUNA, FishingCatch.SWORDFISH)
                .barehandCatches(FishingCatch.BARBARIAN_TUNA, FishingCatch.BARBARIAN_SWORDFISH)
                .register(CAGE_HARPOON_3657, "harpoon");

        new FishingSpot(FishingTool.LOBSTER_POT)
                .regularCatches(FishingCatch.LOBSTER)
                .register(CAGE_HARPOON_9174, "cage");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.TUNA, FishingCatch.SWORDFISH)
                .barehandCatches(FishingCatch.BARBARIAN_TUNA, FishingCatch.BARBARIAN_SWORDFISH)
                .register(CAGE_HARPOON_9174, "harpoon");

        new FishingSpot(FishingTool.LOBSTER_POT)
                .regularCatches(FishingCatch.LOBSTER)
                .register(CAGE_HARPOON_9173, "cage");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.TUNA, FishingCatch.SWORDFISH)
                .barehandCatches(FishingCatch.BARBARIAN_TUNA, FishingCatch.BARBARIAN_SWORDFISH)
                .register(CAGE_HARPOON_9173, "harpoon");

        new FishingSpot(FishingTool.LOBSTER_POT)
                .regularCatches(FishingCatch.LOBSTER)
                .register(CAGE_HARPOON_7946, "cage");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.TUNA, FishingCatch.SWORDFISH)
                .barehandCatches(FishingCatch.BARBARIAN_TUNA, FishingCatch.BARBARIAN_SWORDFISH)
                .register(CAGE_HARPOON_7946, "harpoon");

        new FishingSpot(FishingTool.LOBSTER_POT)
                .regularCatches(FishingCatch.LOBSTER)
                .register(CAGE_HARPOON_7199, "cage");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.TUNA, FishingCatch.SWORDFISH)
                .barehandCatches(FishingCatch.BARBARIAN_TUNA, FishingCatch.BARBARIAN_SWORDFISH)
                .register(CAGE_HARPOON_7199, "harpoon");

        new FishingSpot(FishingTool.LOBSTER_POT)
                .regularCatches(FishingCatch.LOBSTER)
                .register(CAGE_HARPOON_7465, "cage");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.TUNA, FishingCatch.SWORDFISH)
                .barehandCatches(FishingCatch.BARBARIAN_TUNA, FishingCatch.BARBARIAN_SWORDFISH)
                .register(CAGE_HARPOON_7465, "harpoon");

        new FishingSpot(FishingTool.LOBSTER_POT)
                .regularCatches(FishingCatch.LOBSTER)
                .register(CAGE_HARPOON_7460, "cage");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.TUNA, FishingCatch.SWORDFISH)
                .barehandCatches(FishingCatch.BARBARIAN_TUNA, FishingCatch.BARBARIAN_SWORDFISH)
                .register(CAGE_HARPOON_7460, "harpoon");
        /**
         * Net (big) / Harpoon
         */
        new FishingSpot(FishingTool.BIG_FISHING_NET)
                .regularCatches(FishingCatch.MACKEREL, FishingCatch.COD, FishingCatch.BASS)
                .register(BIG_NET_HARPOON, "big net");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.SHARK)
                .barehandCatches(FishingCatch.BARBARIAN_SHARK)
                .register(BIG_NET_HARPOON, "harpoon");

        new FishingSpot(FishingTool.BIG_FISHING_NET)
                .regularCatches(FishingCatch.MACKEREL, FishingCatch.COD, FishingCatch.BASS)
                .register(BIG_NET_HARPOON_5233, "big net");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.SHARK)
                .barehandCatches(FishingCatch.BARBARIAN_SHARK)
                .register(BIG_NET_HARPOON_5233, "harpoon");

        new FishingSpot(FishingTool.BIG_FISHING_NET)
                .regularCatches(FishingCatch.MACKEREL, FishingCatch.COD, FishingCatch.BASS)
                .register(BIG_NET_HARPOON_9172, "big net");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.SHARK)
                .barehandCatches(FishingCatch.BARBARIAN_SHARK)
                .register(BIG_NET_HARPOON_9172, "harpoon");

        new FishingSpot(FishingTool.BIG_FISHING_NET)
                .regularCatches(FishingCatch.MACKEREL, FishingCatch.COD, FishingCatch.BASS)
                .register(BIG_NET_HARPOON_9171, "big net");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.SHARK)
                .barehandCatches(FishingCatch.BARBARIAN_SHARK)
                .register(BIG_NET_HARPOON_9171, "harpoon");

        new FishingSpot(FishingTool.BIG_FISHING_NET)
                .regularCatches(FishingCatch.MACKEREL, FishingCatch.COD, FishingCatch.BASS)
                .register(BIG_NET_HARPOON_3419, "big net");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.SHARK)
                .barehandCatches(FishingCatch.BARBARIAN_SHARK)
                .register(BIG_NET_HARPOON_3419, "harpoon");

        new FishingSpot(FishingTool.BIG_FISHING_NET)
                .regularCatches(FishingCatch.MACKEREL, FishingCatch.COD, FishingCatch.BASS)
                .register(BIG_NET_HARPOON_4476, "big net");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.SHARK)
                .barehandCatches(FishingCatch.BARBARIAN_SHARK)
                .register(BIG_NET_HARPOON_4476, "harpoon");

        new FishingSpot(FishingTool.BIG_FISHING_NET)
                .regularCatches(FishingCatch.MACKEREL, FishingCatch.COD, FishingCatch.BASS)
                .register(BIG_NET_HARPOON_4477, "big net");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.SHARK)
                .barehandCatches(FishingCatch.BARBARIAN_SHARK)
                .register(BIG_NET_HARPOON_4477, "harpoon");

        new FishingSpot(FishingTool.BIG_FISHING_NET)
                .regularCatches(FishingCatch.MACKEREL, FishingCatch.COD, FishingCatch.BASS)
                .register(BIG_NET_HARPOON_7466, "big net");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.SHARK)
                .barehandCatches(FishingCatch.BARBARIAN_SHARK)
                .register(BIG_NET_HARPOON_7466, "harpoon");

        new FishingSpot(FishingTool.BIG_FISHING_NET)
                .regularCatches(FishingCatch.MACKEREL, FishingCatch.COD, FishingCatch.BASS)
                .register(BIG_NET_HARPOON_7461, "big net");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.SHARK)
                .barehandCatches(FishingCatch.BARBARIAN_SHARK)
                .register(BIG_NET_HARPOON_7461, "harpoon");

        new FishingSpot(FishingTool.BIG_FISHING_NET)
                .regularCatches(FishingCatch.MACKEREL, FishingCatch.COD, FishingCatch.BASS)
                .register(BIG_NET_HARPOON_8525, "big net");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.SHARK)
                .barehandCatches(FishingCatch.BARBARIAN_SHARK)
                .register(BIG_NET_HARPOON_8525, "harpoon");
        /**
         * Net (small) / Harpoon
         */
        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.MONKFISH)
                .register(SMALL_NET_HARPOON, "net");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.SWORDFISH)
                .barehandCatches(FishingCatch.BARBARIAN_SWORDFISH)
                .register(SMALL_NET_HARPOON, "harpoon");

        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.MONKFISH)
                .register(SMALL_NET_HARPOON_7200, "net");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.SWORDFISH)
                .barehandCatches(FishingCatch.BARBARIAN_SWORDFISH)
                .register(SMALL_NET_HARPOON_7200, "harpoon");

        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.MONKFISH)
                .register(SMALL_NET_HARPOON_8526, "net");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.SWORDFISH)
                .barehandCatches(FishingCatch.BARBARIAN_SWORDFISH)
                .register(SMALL_NET_HARPOON_8526, "harpoon");

        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.MONKFISH)
                .register(SMALL_NET_HARPOON_8527, "net");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.SWORDFISH)
                .barehandCatches(FishingCatch.BARBARIAN_SWORDFISH)
                .register(SMALL_NET_HARPOON_8527, "harpoon");

        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.HARPOON_FISH)
                .register(TEMPOROSS_SPOT, "harpoon");
        /**
         * Use-rod (Leaping)
         */
        new FishingSpot(FishingTool.BARBARIAN_ROD)
                .regularCatches(FishingCatch.LEAPING_TROUT, FishingCatch.LEAPING_SALMON, FishingCatch.LEAPING_STURGEON)
                .register(USE_ROD, "use-rod");

        new FishingSpot(FishingTool.BARBARIAN_ROD)
                .regularCatches(FishingCatch.LEAPING_TROUT, FishingCatch.LEAPING_SALMON, FishingCatch.LEAPING_STURGEON)
                .register(USE_ROD_7323, "use-rod");
        /**
         * Cage (Dark crab)
         */
        new FishingSpot(FishingTool.DARK_CRAB_POT)
                .regularCatches(FishingCatch.DARK_CRAB)
                .register(CAGE, "cage");

        /**
         * Bait (Angler)
         */
        new FishingSpot(FishingTool.ANGLER_ROD)
                .regularCatches(FishingCatch.ANGLERFISH)
                .register(BAIT, "bait");

        new FishingSpot(FishingTool.ANGLER_ROD)
                .regularCatches(FishingCatch.SLIMY_EEL)
                .register(BAIT_2653, "bait");

        new FishingSpot(FishingTool.ANGLER_ROD)
                .regularCatches(FishingCatch.SLIMY_EEL)
                .register(BAIT_2654, "bait");
        /**
         * Minnows
         */
        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.MINNOWS)
                .register(MINNOWS, "small net");
        NPC minnow1 = new NPC(MINNOWS).spawn(2611, 3443, 0);
        NPC minnow2 = new NPC(MINNOWS).spawn(2610, 3444, 0);
        NPC minnow3 = new NPC(MINNOWS).spawn(2618, 3443, 0);
        NPC minnow4 = new NPC(MINNOWS).spawn(2619, 3444, 0);
        NPC minnow5 = new NPC(MINNOWS).spawn(3810, 2824, 1);
        NPC minnow10 = new NPC(BAIT).spawn(3811, 2823, 1);
        NPC minnow6 = new NPC(BIG_NET_HARPOON).spawn(3809, 2823, 1);
        NPC minnow7 = new NPC(KARAMBWAN_SPOT).spawn(3810, 2822, 1);
        NPC minnow8 = new NPC(BIG_NET_HARPOON).spawn(3525, 3128, 0);
        NPC minnow9 = new NPC(CAGE_HARPOON).spawn(3522, 3124, 0);
        World.startEvent(e -> {
            while (true) {
                e.delay(20);
                moveMinnow(minnow1, minnow3);
                e.delay(4);
                moveMinnow(minnow2, minnow4);
            }
        });
        /**
         * Infernal eel
         */
        new FishingSpot(FishingTool.OILY_FISHING_ROD)
                .regularCatches(FishingCatch.INFERNAL_EEL)
                .register(INFERNO_EEL, "bait");

        new FishingSpot(FishingTool.OILY_FISHING_ROD)
                .regularCatches(FishingCatch.INFERNAL_EEL)
                .register(INFERNO_EEL_6488, "bait");
    }

    private static void moveMinnow(NPC... minnows) {
        for (NPC minnow : minnows) {
            int x = minnow.getAbsX();
            int y = minnow.getAbsY();

            if (y == 3443) {
                if (x == 2609 || x == 2617)
                    minnow.step(0, 1, StepType.FORCE_WALK);
                else
                    minnow.step(-1, 0, StepType.FORCE_WALK);
            } else {
                if (x == 2612 || x == 2620)
                    minnow.step(0, -1, StepType.FORCE_WALK);
                else
                    minnow.step(1, 0, StepType.FORCE_WALK);
            }


            minnow.minnowsFish = false;
        }
    }

    private static double anglerBonus(Player player) {
        double bonus = 1.0;
        Item hat = player.getEquipment().get(Equipment.SLOT_HAT);
        Item top = player.getEquipment().get(Equipment.SLOT_CHEST);
        Item waders = player.getEquipment().get(Equipment.SLOT_LEGS);
        Item boots = player.getEquipment().get(Equipment.SLOT_FEET);

        if (hat != null && hat.getId() == (13258 | 25592))
            bonus += 0.4;
        if (top != null && top.getId() == (13259 | 25594))
            bonus += 0.8;
        if (waders != null && waders.getId() == (13260 | 25596))
            bonus += 0.6;
        if (boots != null && boots.getId() == (13261 | 25598))
            bonus += 0.2;

        /* Whole set gives an additional 0.5% exp bonus */
        if (bonus >= 3.0)
            bonus += 0.5;

        return bonus;
    }

}
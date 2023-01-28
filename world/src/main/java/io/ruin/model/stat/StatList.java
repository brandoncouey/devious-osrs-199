package io.ruin.model.stat;

import com.google.gson.annotations.Expose;
import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.model.World;
import io.ruin.model.activities.pvp.PVPInstance;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.XpMode;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.actions.impl.CompletionistCape;
import io.ruin.model.item.actions.impl.skillcapes.HitpointsSkillCape;
import io.ruin.model.map.Bounds;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.utility.Broadcast;

public class StatList {

    @Expose
    private Stat[] stats;
    @Expose
    private boolean[] announced104m;
    @Expose
    private boolean[] announced200m;
    @Expose
    private StatCounter[] counters;
    public static final Bounds DZ_UPPER = new Bounds(3777, 2816, 3843, 2879, 1);
    public static final Bounds DZ_LOWER = new Bounds(3777, 2816, 3843, 2879, 0);
    public int total99s;

    public int totalLevel;
    @Expose
    public boolean announcedmax = false;

    public long totalXp;
    public int[] totalXp2;

    private Player player;

    public void init(Player player) {
        this.player = player;
        if (stats == null) {
            StatType[] types = StatType.values();
            stats = new Stat[types.length];
            announced104m = new boolean[types.length];
            announced200m = new boolean[types.length];
            for (int id = 0; id < types.length; id++) {
                StatType type = types[id];
                if (type == StatType.Hitpoints)
                    stats[id] = new Stat(10, 1154);
                else
                    stats[id] = new Stat(1, 0);
                stats[id].updated = true;
            }
            return;
        }
        if (counters == null) {
            counters = new StatCounter[stats.length + 1];
            for (int i = 0; i < counters.length; i++)
                counters[i] = new StatCounter(i);
        } else {
            for (int i = 0; i < counters.length; i++) {
                StatCounter counter = counters[i];
                counter.index = i;
                counter.send(player); //i don't think we need to send if we have configs save ????
            }
        }
        for (Stat stat : stats) {
            stat.fixedLevel = Stat.levelForXp(stat.experience);
            stat.updated = true;
        }
    }

    public void set(StatType type, int level) {
        set(type, level, Stat.xpForLevel(level));
    }

    public void set(StatType type, int level, int experience) {
        Stat stat = get(type);
        stat.currentLevel = stat.fixedLevel = level;
        stat.experience = experience;
        stat.updated = true;
    }

    public boolean check(StatType type, int lvlReq) {
        return get(type).currentLevel >= lvlReq;
    }

    /**
     * Checks the fixed (not boosted) level.
     */
    public boolean checkFixed(StatType type, int levelReq, String action) {
        if (get(type).fixedLevel < levelReq) {
            player.sendMessage("You need " + type.descriptiveName + " level of " + levelReq + " or higher to " + action + ".");
            return false;
        }
        return true;
    }

    public boolean check(StatType type, int levelReq, String action) {
        if (!check(type, levelReq)) {
            player.sendMessage("You need " + type.descriptiveName + " level of " + levelReq + " or higher to " + action + ".");
            return false;
        }
        return true;
    }

    public boolean check(StatType type, int lvlReq, int itemId, String action) {
        if (!check(type, lvlReq)) {
            player.dialogue(new ItemDialogue().one(itemId, "You need " + type.descriptiveName + " level of " + lvlReq + " or higher to " + action + "."));
            return false;
        }
        return true;
    }

    public boolean check(StatType type, int lvlReq, int itemId1, int itemId2, String action) {
        if (!check(type, lvlReq)) {
            player.dialogue(new ItemDialogue().two(itemId1, itemId2, "You need " + type.descriptiveName + " level of " + lvlReq + " or higher to " + action + "."));
            return false;
        }
        return true;
    }

    public void restore(boolean restoreBoosted) {
        for (Stat stat : stats) {
            if (!restoreBoosted && stat.currentLevel > stat.fixedLevel)
                continue;
            stat.alter(stat.fixedLevel);
        }
    }


    public void addXp(StatType type, double amount, boolean useMultiplier) {

        int statId = type.ordinal();
        Stat stat = stats[statId];
        double baseAmount = amount;

        if (player.showHitAsExperience() && amount <= 132 && type != StatType.Hitpoints && !useMultiplier) {
            int intValue = (int) Math.round(amount);
            player.getPacketSender().sendClientScript(2091, statId, intValue);
        }
        if (player.experienceLock) {
            return;
        }

        if (useMultiplier) {
            if (World.xpMultiplier > 0 && player.xpMode != XpMode.REALISTIC)
                amount += baseAmount * (World.xpMultiplier - 1);
            amount += baseAmount * (Wilderness.getXPModifier(player, type));
        }
        /*
         * XP Modes
         */
        if (useMultiplier) {
            if (stat.fixedLevel >= 99) {
                amount *= player.xpMode.getAfter99Rate();
            } else {
                if (type.isCombat()) {
                    amount *= player.xpMode.getCombatRate();
                } else {
                    amount *= player.xpMode.getSkillRate();
                }
            }
        }


        /*
         * 50% experience boost from scroll
         */
        if (player.isADonator())
            amount *= 1.25;
        if (player.expBonus.isDelayed())
            amount *= 2.00;
        if (player.getPosition().inBounds(DZ_UPPER) || player.getPosition().inBounds(DZ_LOWER))
            amount *= 2.00;
        /*
         * 50% range XP from attuned sigil
         */
        if (player.SOTRR && type == StatType.Ranged) {
            amount *= 2.00;
        }
        boolean safeZone = player.getPosition().inBounds(PVPInstance.EDGE_BANK) || player.getPosition().inBounds(PVPInstance.EDGE_CHESTS)
                || player.getPosition().inBounds(PVPInstance.CAMELOT) || player.getPosition().inBounds(PVPInstance.CAMELOT2)
                || player.getPosition().inBounds(PVPInstance.CAMELOT3) || player.getPosition().inBounds(PVPInstance.DZ_UPPER)
                || player.getPosition().inBounds(PVPInstance.DEATH_OUTSIDE) || player.getPosition().inBounds(PVPInstance.DEATH_INSIDE)
                || player.getPosition().inBounds(PVPInstance.HOME_MIDDLE)
                || player.getPosition().inBounds(PVPInstance.HOME_LOWER)
                || player.getPosition().inBounds(PVPInstance.HOME_UPPER);

        if(World.isPVPWorld() && !safeZone)
            amount *= 2.00;

        if (player.SOTMM && type == StatType.Magic) {
            amount *= 2.00;
        }
        /*
         * 10% experience boost from first 3 days
         */
        if (player.first3.isDelayed())
            amount *= 1.10;
        /*
         * 25% experience boost inside the wilderness
         */
        if (player.wildernessLevel > 1)
            amount *= 1.25;
        /*
         * 25% weekend experience boost
         */
        if (World.weekendExpBoost)
            amount *= 1.25;

        double newXp = stat.experience + amount;
        if (newXp > Stat.MAX_XP) {
            newXp = Stat.MAX_XP;
        }
        if (newXp == Stat.MAX_XP && stat.experience < Stat.MAX_XP) {
            if (!player.getGameMode().isIronMan()) {
                Broadcast.GLOBAL.sendNews(Icon.NORMAL, "<col=ff0000>[" + player.xpMode.getCombatRate() + "x] </col>" + player.getName() + " has just reached 1 billion experience in " + type.name() + "!");
            } else if (player.getGameMode().isHardcoreIronman()) {
                Broadcast.GLOBAL.sendNews(Icon.HCIM, "<col=ff0000>[" + player.xpMode.getCombatRate() + "x] </col>" + player.getName() + " has just reached 1 billion experience in " + type.name() + "!");
            } else if (player.getGameMode().isUltimateIronman()) {
                Broadcast.GLOBAL.sendNews(Icon.UIM, "<col=ff0000>[" + player.xpMode.getCombatRate() + "x] </col>" + player.getName() + " has just reached 1 billion experience in " + type.name() + "!");
            } else if (player.getGameMode().isGroupIronman()) {
                Broadcast.GLOBAL.sendNews(Icon.GIM, "<col=ff0000>[" + player.xpMode.getCombatRate() + "x] </col>" + player.getName() + " has just reached 1 billion experience in " + type.name() + "!");
            } else if (player.getGameMode().isHardcoreGroupIronman()) {
                Broadcast.GLOBAL.sendNews(Icon.HGIM, "<col=ff0000>[" + player.xpMode.getCombatRate() + "x] </col>" + player.getName() + " has just reached 1 billion experience in " + type.name() + "!");
            } else {
                Broadcast.GLOBAL.sendNews(Icon.IRONMAN, "<col=ff0000>[" + player.xpMode.getCombatRate() + "x] </col>" + player.getName() + " has just reached 1 billion experience in " + type.name() + "!");
            }
            player.sendMessage("Congratulations, you have reached max experience in " + type.name() + "!");
        }

        stat.experience = newXp;
        stat.updated = true;

        int oldLevel = stat.fixedLevel;
        int newLevel = stat.fixedLevel = Stat.levelForXp(stat.experience);
        int oldtotalLevel = totalLevel;
        int gain = newLevel - oldLevel;
        if (gain == 0) {
            return;
        }
        if (stat.currentLevel < stat.fixedLevel) {
            if (type == StatType.Hitpoints || type == StatType.Prayer) {
                if (stat.currentLevel == oldLevel)
                    stat.currentLevel += gain;
            } else {
                stat.currentLevel += gain;
            }
        }
        World.sendGraphics(1388, 50, 0, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
        player.sendMessage("You've just advanced " + type.descriptiveName + " level. You have reached level " + newLevel + ".");
        if (newLevel == 99) {
            player.sendMessage(Color.ORANGE_RED.tag() + "Congratulations on achieving level 99 in " + type.name() + "!");
            player.sendMessage(Color.ORANGE_RED.tag() + "You may now purchase a skillcape from Mac who can be found at home.");
            if (!player.getGameMode().isIronMan()) {
                Broadcast.GLOBAL.sendNews(Color.ORANGE.tag() + player.getName() + " has just achieved level 99 in " + type.name() + " on " + player.xpMode.name().toLowerCase() + " mode!");
            } else if (player.getGameMode().isHardcoreIronman()) {
                Broadcast.GLOBAL.sendNews(Icon.HCIM, Color.ORANGE.tag() + player.getName() + " has just achieved level 99 in " + type.name() + " on " + player.xpMode.name().toLowerCase() + " mode!");
            } else if (player.getGameMode().isUltimateIronman()) {
                Broadcast.GLOBAL.sendNews(Icon.UIM, Color.ORANGE.tag() + player.getName() + " has just achieved level 99 in " + type.name() + " on " + player.xpMode.name().toLowerCase() + " mode!");
            } else if (player.getGameMode().isGroupIronman()) {
                Broadcast.GLOBAL.sendNews(Icon.GIM, Color.ORANGE.tag() + player.getName() + " has just achieved level 99 in " + type.name() + " on " + player.xpMode.name().toLowerCase() + " mode!");
            } else if (player.getGameMode().isHardcoreGroupIronman()) {
                Broadcast.GLOBAL.sendNews(Icon.HGIM, Color.ORANGE.tag() + player.getName() + " has just achieved level 99 in " + type.name() + " on " + player.xpMode.name().toLowerCase() + " mode!");
            } else {
                Broadcast.GLOBAL.sendNews(Icon.IRONMAN, Color.ORANGE.tag() + player.getName() + " has just achieved level 99 in " + type.name() + " on " + player.xpMode.name().toLowerCase() + " mode!");
            }
        }
        if (totalLevel == 250) {
            player.sendMessage("Congratulations on 250 total level! Your statistics will now appear on the highscores!");
        }
        if (stat.experience >= 104000000 && !announced104m[statId]) {
            player.sendMessage(Color.ORANGE_RED.tag() + "Congratulations on achieving 104M experience in " + type.name() + "!");
            player.sendMessage(Color.ORANGE_RED.tag() + "You may now purchase a master skillcape from Mac who can be found at home.");
            Broadcast.GLOBAL.sendNews(Color.ORANGE.tag() + player.getName() + " has just achieved 104M experience in " + type.name() + " on " + player.xpMode.name().toLowerCase() + " mode!");
            announced104m[statId] = true;
        }
        if (stat.experience >= 200000000 && !announced200m[statId]) {
            Broadcast.GLOBAL.sendNews(Color.ORANGE.tag() + player.getName() + " has just achieved 200M experience in " + type.name() + " on " + player.xpMode.name().toLowerCase() + " mode!");
            announced200m[statId] = true;
        }
        if (statId <= 6)
            player.getCombat().updateLevel();
    }


    public void process() {
        boolean rapidRestore = player.getPrayer().isActive(Prayer.RAPID_RESTORE);
        boolean rapidHeal = player.getPrayer().isActive(Prayer.RAPID_HEAL) || HitpointsSkillCape.wearsHitpointsCape(player) || player.getInventory().hasItem(25990, 1);
        boolean preserve = player.getPrayer().isActive(Prayer.PRESERVE);
        StatType[] types = StatType.values();
        totalLevel = 0;
        totalXp = 0;
        total99s = 0;
        for (int statId = 0; statId < types.length; statId++) {
            Stat stat = stats[statId];
            StatType type = types[statId];
            if (type != StatType.Prayer)
                stat.process(type == StatType.Hitpoints, rapidRestore, rapidHeal, preserve);
            if (stat.updated) {
                stat.updated = false;
            }
            player.getPacketSender().sendStat(statId, stat.currentLevel, (int) stat.experience);

            if (stat.fixedLevel == 99)
                total99s++;
            totalLevel += stat.fixedLevel;
            totalXp += stat.experience;
            if (CompletionistCape.doneAchieves(player) && !player.achievedComp) {
                CompletionistCape.compDone(player);
            }
            if (totalLevel == 2277 && !announcedmax) {
                player.sendMessage(Color.ORANGE_RED.tag() + "Congratulations on achieving 2277 Total!");
                if (!player.getGameMode().isIronMan()) {
                    announcedmax = true;
                    Broadcast.GLOBAL.sendNews(Icon.NORMAL, "<col=ff0000>[" + player.xpMode.getCombatRate() + "x] </col>" + player.getName() + " has just achieved 2277 Total.");
                } else if (player.getGameMode().isHardcoreIronman()) {
                    announcedmax = true;
                    Broadcast.GLOBAL.sendNews(Icon.HCIM, "<col=ff0000>[" + player.xpMode.getCombatRate() + "x] </col>" + player.getName() + " has just achieved 2277 Total.");
                } else if (player.getGameMode().isUltimateIronman()) {
                    announcedmax = true;
                    Broadcast.GLOBAL.sendNews(Icon.UIM, "<col=ff0000>[" + player.xpMode.getCombatRate() + "x] </col>" + player.getName() + " has just achieved 2277 Total.");
                } else if (player.getGameMode().isGroupIronman()) {
                    announcedmax = true;
                    Broadcast.GLOBAL.sendNews(Icon.GIM, "<col=ff0000>[" + player.xpMode.getCombatRate() + "x] </col>" + player.getName() + " has just achieved 2277 Total.");
                } else if (player.getGameMode().isHardcoreGroupIronman()) {
                    announcedmax = true;
                    Broadcast.GLOBAL.sendNews(Icon.HGIM, "<col=ff0000>[" + player.xpMode.getCombatRate() + "x] </col>" + player.getName() + " has just achieved 2277 Total.");
                } else {
                    announcedmax = true;
                    Broadcast.GLOBAL.sendNews(Icon.IRONMAN, "<col=ff0000>[" + player.xpMode.getCombatRate() + "x] </col>" + player.getName() + " has just achieved 2277 Total.");
                }
            }
        }
        player.getCombat().checkLevel();
    }

    public StatCounter getCounter(int slot) {
        return counters[slot];
    }

    public Stat get(int statId) {
        return stats[statId];
    }

    public Stat get(StatType type) {
        return stats[type.ordinal()];
    }

    public Stat[] get() {
        return stats;
    }

    public boolean check(StatRequirement statRequirement) {
        return statRequirement.hasRequirement(player);
    }
}
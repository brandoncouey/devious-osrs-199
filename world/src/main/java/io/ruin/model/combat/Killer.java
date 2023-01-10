package io.ruin.model.combat;

import io.ruin.api.utils.Random;
import io.ruin.api.utils.TimeUtils;
import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.model.World;
import io.ruin.model.activities.wilderness.Hotspot;
import io.ruin.model.activities.wilderness.StaffBounty;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.SecondaryGroup;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.actions.impl.WildernessDeadmanKey;
import io.ruin.model.map.Bounds;
import io.ruin.model.stat.StatType;
import io.ruin.services.Loggers;
import io.ruin.services.discord.impl.KillingSpreeEmbedMessage;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Misc;

public class Killer {
    public static final Bounds EDGEVILLE_FARM_SKIP_BOUNDS = new Bounds(3034, 3523, 3125, 3574, 0);
    public static final Bounds EDGEVILLE_PRESET_BOUNDS = new Bounds(3043, 3482, 3122, 3524, 0);
    public static final Bounds MAGEBANK_PRESET_BOUNDS = new Bounds(2531, 4711, 2547, 4721, 0);
    public static final Bounds RAIDS_PRESET_BOUNDS = new Bounds(1223, 3542, 1263, 3576, 0);

    public static int BASE_BM_REWARD = 50;
    public static int nondonator = Misc.random(1000, 15000);
    public static int donator = Misc.random(1000, 50000);

    public Player player;

    public int damage;

    public void reward(Player pKilled, boolean bhTarget) {
        /*
         * Farming checks
         */
        long ms = System.currentTimeMillis();
        if (player.getIpInt() == pKilled.getIpInt()) //don't reward bm for killing someone on your same ip
            return;
        if (!pKilled.getPosition().inBounds(EDGEVILLE_FARM_SKIP_BOUNDS)) {
            boolean logged = player.bmIpLogs.contains(pKilled.getIpInt(), ms, 5L)
                    || player.bmUserLogs.contains(pKilled.getUserId(), ms, 5L);
            player.bmIpLogs.add(pKilled.getIpInt(), ms);
            player.bmUserLogs.add(pKilled.getUserId(), ms);
            if (logged)
                return;
        }

        /*
         * Increment killer kills & spree.
         */
        Config.PVP_KILLS.increment(player, 1);
        int killerSpree = ++player.currentKillSpree;
        if (killerSpree > player.highestKillSpree)
            player.highestKillSpree = killerSpree;
        if (killerSpree == 10)
            player.getInventory().add(13307, 250000);
        player.sendMessage("You are currently on a killing spree of " + killerSpree + "!");
        if (killerSpree == 20)
            player.getInventory().add(13307, 500000);
        player.sendMessage("You are currently on a killing spree of " + killerSpree + "!");
        if (killerSpree == 30)
            player.getInventory().add(13307, 750000);
        player.sendMessage("You are currently on a killing spree of " + killerSpree + "!");
        if (killerSpree == 40)
            player.getInventory().add(13307, 1000000);
        player.sendMessage("You are currently on a killing spree of " + killerSpree + "!");
        if (!player.isMember()) {
            if (killerSpree > 1)
                player.sendMessage("You are currently on a killing spree of " + killerSpree + "!");
            if (killerSpree % 5 == 0 || killerSpree > 15) {
                String spreeMessage = player.getName() + " is on a killing spree of " + killerSpree + ". Kill "
                        + (player.getAppearance().isMale() ? "him" : "her") + " for a bounty reward of " + (nondonator + bountyValue(killerSpree)) + " Blood money!";
                Broadcast.WORLD.sendPlain(KillingSpree.imgTag(killerSpree) + Color.DARK_GREEN.tag() + " " + spreeMessage);
                KillingSpreeEmbedMessage.sendDiscordMessage(spreeMessage);
            } else if (player.isMember()) {
                // player.sendMessage("You are currently on a killing spree of " + killerSpree + "!");
                if (killerSpree % 5 == 0 || killerSpree > 15) {
                    String spreeMessage = player.getName() + " is on a killing spree of " + killerSpree + ". Kill "
                            + (player.getAppearance().isMale() ? "him" : "her") + " for a bounty reward of " + (donator + bountyValue(killerSpree)) + " Blood money!";
                    Broadcast.WORLD.sendPlain(KillingSpree.imgTag(killerSpree) + Color.DARK_GREEN.tag() + " " + spreeMessage);
                    KillingSpreeEmbedMessage.sendDiscordMessage(spreeMessage);
                }
                if (player.getCombat().isSkulled()) //Overheads start at sprees of 2, so this fits here.
                    player.getAppearance().setSkullIcon(KillingSpree.overheadId(player));
            }
        }
        /*
         * Check for shutdown
         */
        int targetSpree = pKilled.currentKillSpree; //Spree is set to 0 in death method. (After this method)
        if (targetSpree >= 5) {
            String shutdownMessage2 = KillingSpree.shutdownMessage(player.getName(), pKilled.getName(), targetSpree);
            Broadcast.WORLD.sendPlain("<img=36> " + Color.DARK_GREEN.tag() + shutdownMessage2);
//            ShutdownEmbedMessage.sendDiscordMessage(shutdownMessage);
            if (targetSpree > player.highestShutdown)
                player.highestShutdown = targetSpree;
        }
        /*
         * Reward blood money
         */
        int bmAmount = nondonator;

        /*
         * Check if we're doubling blood money for the weekend event
         */
        int bmMultiplier = World.bmMultiplier;
        if (bmMultiplier != 0) {
            bmAmount *= bmMultiplier;
        }

        bmAmount *= donatorBonus(player);

        if (bhTarget)
            bmAmount *= 1.05;

        if (player.getPosition().inBounds(Hotspot.ACTIVE.bounds)) {
            bmAmount *= 2.0;
            player.sendFilteredMessage("<col=6f0000>You get double blood money for killing a player in a hotspot!");
        }

        bmAmount += bountyValue(targetSpree);
        bmAmount += streakValue(killerSpree);
        bmAmount += player.wildernessLevel * 1.3;

        if (ms >= player.nextWildernessBonus) {
            player.nextWildernessBonus = (ms + TimeUtils.getHoursToMillis(12));
            player.sendFilteredMessage("<col=6f0000>You get an additional 250 Blood money for your first kill of the day!");
            bmAmount += 250;
        }

        /**
         * Check for staff bounty event
         */
        if (StaffBounty.EVENT_ACTIVE) {
            if (pKilled.isSupport()) {
                bmAmount += StaffBounty.SUPPORT_KILL_VALUE;
                StaffBounty.incrementStaffBounty("support_deaths");
                Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Staff Bounty", pKilled.getName() + " has been slain! " + player.getName() +
                        " has claimed " + StaffBounty.SUPPORT_KILL_VALUE + " for their bounty! The hunt continues...");

            }
            if (pKilled.isModerator()) {
                bmAmount += StaffBounty.MODERATOR_KILL_VALUE;
                StaffBounty.incrementStaffBounty("moderator_deaths");
                Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Staff Bounty", pKilled.getName() + " has been slain! " + player.getName() +
                        " has claimed " + StaffBounty.MODERATOR_KILL_VALUE + " for their bounty! The hunt continues...");
            }
            if (pKilled.isAdmin()) {
                bmAmount += StaffBounty.ADMINISTRATOR_KILL_VALUE;
                StaffBounty.incrementStaffBounty("administrator_deaths");
                Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Staff Bounty", pKilled.getName() + " has been slain! " + player.getName() +
                        " has claimed " + StaffBounty.ADMINISTRATOR_KILL_VALUE + " for their bounty! The hunt continues...");
            }
            Loggers.logStaffBountyKill(player, pKilled);
        }


        if (player.getPosition().isWithinDistance(pKilled.getPosition(), 32)) {
            player.getCombat().restoreSpecial(100);
        }

        player.rewardBm(pKilled, bmAmount);

        if (World.isPVPWorld()) {
           WildernessDeadmanKey.rollForDeadmanKey(player, pKilled);
        }

        if (player.insideWildernessAgilityCourse) {
            player.getStats().addXp(StatType.Agility, 50000, false);
            player.getInventory().addOrDrop(11849, 10);
            player.sendFilteredMessage("<col=6f0000> You receive 50,000 agility experience and 10 marks of grace for killing a player inside the Agility course.");
        }

        if (Random.rollDie(5, 1)) {
            player.getInventory().addOrDrop(30290, 1);
        }
        WildernessRating.adjustEloRatings(pKilled, player);
    }

    private static double donatorBonus(Player player) {
        if (player.isGroups(SecondaryGroup.DIAMOND)) {
            return 1.25;
        } else if (player.isGroups(SecondaryGroup.RUBY)) {
            return 1.20;
        } else if (player.isGroups(SecondaryGroup.EMERALD)) {
            return 1.15;
        } else if (player.isGroups(SecondaryGroup.SAPPHIRE)) {
            return 1.10;
        } else if (player.isGroups(SecondaryGroup.RED_TOPAZ)) {
            return 1.05;
        } else if (player.isGroups(SecondaryGroup.OPAL)) {
            return 1.00;
        } else if (player.isGroups(SecondaryGroup.JADE)) {
            return 1.00;
        } else {
            return 1.00;
        }
    }
    private static int bountyValue(int spree) {
        return (10 * spree + 50 * (spree / 10));
    }

    private static int streakValue(int spree) {
        return 5 * spree;
    }


}
package io.ruin.model.reputation;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAttributes;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;


public class RepInter {

    public static void open(Player c) {
        OptionScroll.open(c, "<col=8B0000>Devious Reputation",
                new Option(""),
                new Option("<col=0040ff>Click a name to review that areas tasks and rewards"),
                new Option("Edgeville", () -> displayEdge(c)),
                new Option("Falador", () -> displayFalador(c)),
                new Option("Kebos", () -> displayKebos(c)),
                new Option("Varrock", () -> displayVarrock(c))
        );
        return;
    }

    public static void displayEdge(Player c) {
        double percent = ((double) c.edgevilleRep / (double) PlayerAttributes.maxEdgevilleRep) * 100;
        OptionScroll.open(c, "<col=8B0000>Devious Reputation - " + percent + "% complete",
                new Option("Previous Menu", () -> open(c)),
                new Option("<col=126312>Current amount: " + c.edgevilleRep + ""),
                new Option("----------------------------------------------------------"),
                new Option("<col=0040ff>How to earn Reputation"),
                new Option("-Speak to King Arthur to receive your first task (100 Rep)"),
                new Option("-Earn 500 rep in Varrock, then return to King Arthur (200 Rep)"),
                new Option("-Earn 500 rep in Falador, then return to King Arthur (200 Rep)"),
                new Option("-Speak to Sir Kay to being receiving tasks from him"),
                new Option("-Earn 10 rep each time you kill a player in the wilderness"),
                new Option("-Earn 10 rep for every lap you complete at the Wilderness Course"),
                new Option("-Earn 5 reputation for each boss you kill in the wilderness"),
                new Option("----------------------------------------------------------"),
                new Option("<col=0040ff>Rewards"),
                new Option("At each rank of reputation reached in Devious, you will receive"),
                new Option("discounts on all items in the Pk Point Shop, reaching a maximum of"),
                new Option("up to 20% off of the cost of items in this shop! You will reach a new"),
                new Option("rank at 4,000, 8,000, 12,000 and finally max at 15,000 rep in Devious."),
                new Option("----------------------------------------------------------")
        );
        return;
    }

    public static void displayFalador(Player c) {
        double percent = ((double) c.faladorRep / (double) PlayerAttributes.maxFaladorRep) * 100;
        OptionScroll.open(c, "<col=8B0000>Falador Reputation - " + percent + "% complete",
                new Option("Previous Menu", () -> open(c)),
                new Option("<col=126312>Current amount: " + c.faladorRep + ""),
                new Option("----------------------------------------------------------"),
                new Option("<col=0040ff>How to earn Reputation"),
                new Option("-Complete a quest for Flynn (200 Rep)"),
                new Option("-Bring Herquin uncut rubies, diamonds, or dragonstones (repeatable)"),
                new Option("-Bring Wayne a dragon metal lump (repeateable)"),
                new Option("-Complete a quest for the Doorman (200 rep)"),
                new Option("-Kill giant moles (5 rep per kill)"),
                new Option("-Complete laps on the Falador rooftop course (10 rep per lap)"),
                new Option("-Complete a series of mini-quests for Nulodion"),
                new Option("----------------------------------------------------------"),
                new Option("<col=0040ff>Rewards"),
                new Option("At each rank of reputation reached in Falador, you will unlock access"),
                new Option("to new items in Sir Rebrals shop! Additionally, you will have a chance"),
                new Option("at getting noted ores while mining in the mining guild at 4,000 rep!"),
                new Option("Reach each rank at 2,000, 5,000, 8,000 and finally max at 10,000 rep in Falador."),
                new Option("----------------------------------------------------------")
        );
        return;
    }

    public static void displayKebos(Player c) {
        double percent = ((double) c.kebosRep / (double) PlayerAttributes.maxKebosRep) * 100;
        OptionScroll.open(c, "<col=8B0000>Kebos Reputation - " + percent + "% complete",
                new Option("Previous Menu", () -> open(c)),
                new Option("<col=126312>Current amount: " + c.kebosRep + ""),
                new Option("----------------------------------------------------------"),
                new Option("<col=0040ff>How to earn Reputation"),
                new Option("-Speak to Pentyn to begin an in-depth series of quests and tasks"),
                new Option("-Complete the Chambers of Xeric (200 Rep per run)"),
                new Option("-Kill lizardman shamans (7 Rep per kill)"),
                new Option("-Loot the brimstone chest (10 Rep each time)"),
                new Option("-Complete a short series of mini-quests for Councillor Andrews"),
                new Option("----------------------------------------------------------"),
                new Option("<col=0040ff>Rewards"),
                new Option("The rewards for earning reputation in Kebos take a bit more time,"),
                new Option("however, they are well worth it once achieved. At 8,000 rep, your"),
                new Option("account will gain a permanent perk giving a boost to your chances at"),
                new Option("receiving rare rewards from Chambers of Xeric."),
                new Option("----------------------------------------------------------")
        );
        return;
    }

    public static void displayVarrock(Player c) {
        double percent = ((double) c.varrockRep / (double) PlayerAttributes.maxVarrockRep) * 100;
        OptionScroll.open(c, "<col=8B0000>Varrock Reputation - " + percent + "% complete",
                new Option("Previous Menu", () -> open(c)),
                new Option("<col=126312>Current amount: " + c.varrockRep + ""),
                new Option("----------------------------------------------------------"),
                new Option("<col=0040ff>How to earn Reputation"),
                new Option("-Complete a series of mini-quests for King Roald"),
                new Option("-Bring Thessalia 28 unnoted silk (repeatable)"),
                new Option("-Complete laps on the Varrock rooftop course (10 Rep per lap)"),
                new Option("-Bring Aubury 28 unnoted pure essence (repeatable)"),
                new Option("-Bring Ellamaria various seeds she requires (repeatable)"),
                new Option("-Killing green dragons during one of King Roalds mini-quests"),
                new Option("----------------------------------------------------------"),
                new Option("<col=0040ff>Rewards"),
                new Option("At each rank of reputation reached in Varrock, you will unlock access"),
                new Option("to new items in Tobys shop! Reach each rank at 2,000, 4,000, 6,000 and finally max at 8,000 rep in Varrock."),
                new Option("----------------------------------------------------------")
        );
        return;
    }
}
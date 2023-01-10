package io.ruin.model.stat;

import io.ruin.api.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public enum StatType {

    Attack(1,
            60.0, 10.0,
            9747, 9748, 9749, 30113, 30114
    ),
    Defence(5,
            60.0, 10.0,
            9753, 9754, 9755, 30194, 30195
    ),
    Strength(2,
            60.0, 10.0,
            9750, 9751, 9752, 30224, 30225
    ),
    Hitpoints(6,
            60.0, 10.0,
            9768, 9769, 9770, 30206, 30207
    ),
    Ranged(3,
            60.0, 10.0,
            9756, 9757, 9758, 30216, 30217
    ),
    Prayer(7,
            60.0, 10.0,
            9759, 9760, 9761, 30214, 30215
    ),
    Magic(4,
            60.0, 10.0,
            9762, 9763, 9764, 30210, 30211
    ),
    Cooking(16,
            15.0, 10.0,
            9801, 9802, 9803, 30190, 30191
    ),
    Woodcutting(18,
            15.0, 10.0,
            9807, 9808, 9809, 30228, 30229
    ),
    Fletching(19,
            15.0, 10.0,
            9783, 9784, 9785, 30202, 30203
    ),
    Fishing(15,
            15.0, 10.0,
            9798, 9799, 9800, 30200, 30201
    ),
    Firemaking(17,
            15.0, 10.0,
            9804, 9805, 9806, 30198, 30199
    ),
    Crafting(11,
            15.0, 10.0,
            9780, 9781, 9782, 30192, 30193
    ),
    Smithing(14,
            15.0, 10.0,
            9795, 9796, 9797, 30222, 30223
    ),
    Mining(13,
            15.0, 10.0,
            9792, 9793, 9794, 30212, 30213
    ),
    Herblore(9,
            15.0, 10.0,
            9774, 9775, 9776, 30204, 30205
    ),
    Agility(8,
            15.0, 10.0,
            9771, 9772, 9773, 30111, 30112
    ),
    Thieving(10,
            15.0, 10.0,
            9777, 9778, 9779, 30226, 30227
    ),
    Slayer(20,
            20.0, 10.0,
            9786, 9787, 9788, 30220, 30221
    ),
    Farming(21,
            15.0, 10.0,
            9810, 9811, 9812, 30196, 30197
    ),
    Runecrafting(12,
            15.0, 10.0,
            9765, 9766, 9767, 30218, 30219
    ),
    Hunter(23,
            15.0, 10.0,
            9948, 9949, 9950, 30208, 30209
    ),
    Construction(22,
            15.0, 10.0,
            9789, 9790, 9791, 30188, 30189
    );

    public final int clientId;

    public final double defaultXpMultiplier, after99XpMultiplier;

    public final int regularCapeId, trimmedCapeId, hoodId, masterCapeId, masterHoodId;

    public final String descriptiveName;

    public boolean isCombat() {
        return this.ordinal() <= 6;
    }

    StatType(int clientId, double defaultXpMultiplier, double after99XpMultiplier, int regularCapeId, int trimmedCapeId, int hoodId, int masterCapeId, int masterHoodId) {
        this.clientId = clientId;
        this.defaultXpMultiplier = defaultXpMultiplier;
        this.after99XpMultiplier = after99XpMultiplier;
        this.regularCapeId = regularCapeId;
        this.trimmedCapeId = trimmedCapeId;
        this.hoodId = hoodId;
        this.masterCapeId = masterCapeId;
        this.masterHoodId = masterHoodId;

        String name = name();
        if (StringUtils.vowelStart(name))
            descriptiveName = "an " + name;
        else
            descriptiveName = "a " + name;
    }

    /**
     * Get by id
     */

    public static StatType get(int id) {
        return values()[id];
    }

    /**
     * Get by name
     */

    public static StatType get(String name) {
        for (StatType type : values()) {
            if (name.equalsIgnoreCase(type.name()))
                return type;
        }
        switch (name.toLowerCase()) {
            case "att":
            case "atk":
                return Attack;
            case "str":
                return Strength;
            case "def":
                return Defence;
            case "hp":
                return Hitpoints;
            case "wc":
                return Woodcutting;
            case "rc":
                return Runecrafting;
            case "fm":
                return Firemaking;
            case "con":
                return Construction;
        }
        return null;
    }

    /**
     * Client order
     */

    public static final StatType[] CLIENT_ORDER;

    static {
        ArrayList<StatType> list = new ArrayList<>(Arrays.asList(values()));
        list.sort(Comparator.comparingInt(s -> s.clientId));
        CLIENT_ORDER = list.toArray(new StatType[list.size()]);
    }

}

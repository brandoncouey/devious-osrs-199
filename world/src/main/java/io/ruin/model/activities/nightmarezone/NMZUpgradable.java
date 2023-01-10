package io.ruin.model.activities.nightmarezone;

public enum NMZUpgradable {
    BLACK_MASK(8921, 11784, 1250000),
    BLACK_MASK_10(8901, 11774, 1250000),
    SLAYER_HELMET(11864, 11865, 1250000),
    BLACK_SLAYER_HELMET(19639, 19641, 1250000),
    GREEN_SLAYER_HELMET(19643, 19645, 1250000),
    RED_SLAYER_HELMET(19647, 19649, 1250000),
    PURPLE_SLAYER_HELMET(21264, 21266, 1250000),
    TURQUOISE_SLAYER_HELMET(21888, 21890, 1250000),
    HYDRA_SLAYER_HELMET(23073, 23075, 1250000),
    TWISTED_SLAYER_HELMET(24370, 24444, 1250000),
    SALVE_AMULET(4081, 12017, 800000),
    SALVE_AMULET_E(10588, 12018, 800000),
    RING_OF_THE_GODS(12601, 13202, 650000),
    RING_OF_SUFFERING(19550, 19710, 725000),
    BERSERKER_RING(6737, 11773, 650000),
    WARRIOR_RING(6735, 11772, 650000),
    ARCHERS_RING(6733, 11771, 650000),
    SEERS_RING(6731, 11770, 650000),
    TYRANNICAL_RING(12603, 12691, 650000),
    TREASONOUS_RING(12605, 12692, 650000),
    GRANITE_RING(21739, 21752, 500000),
    ROW(2572, 12785, 500000);

    public int id, upgraded, price;

    NMZUpgradable(int id, int upgraded, int price) {
        this.id = id;
        this.upgraded = upgraded;
        this.price = price;
    }

    public static NMZUpgradable getUpgradable(int id) {
        for (NMZUpgradable upgradable : values())
            if (upgradable.id == id)
                return upgradable;
        return null;
    }
}

package io.ruin.model.activities.deadman

enum class DmmIKODStats(val buttonId: Int, val bitmask: Int, val statId: Int, val isCombat: Boolean = false) {
    ATTACK(22, 1, 0, true),
    STR(23, 2, 2, true),
    DEF(24, 16, 1, true),
    RANGE(25, 4, 4, true),
    PRAY(26, 64, 5, true),
    MAGE(27, 8, 6, true),
    HITPOINTS(30, 32, 3, true),
    RUNECRAFTING(28, 2048, 20),
    CONSTRUCTION(29, 2097152, 22),
    AGILITY(31, 128, 16),
    HERB(32, 256, 15),
    THEIV(33, 512, 17),
    CRAFT(34, 1024, 12),
    FLETCH(35, 262144, 9),
    SLAYER(36, 524288, 18),
    HUNTER(37, 4194304, 21),
    MINING(38, 4096, 14),
    SMITH(39, 8192, 13),
    FISHING(40, 16384, 10),
    COOKING(41, 32768, 7),
    FIREMAKING(42, 65536, 11),
    WOODCUTTING(43, 131072, 8),
    FARMING(44, 1048576, 19)
}
package io.ruin.cache;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;
import io.ruin.api.utils.StringUtils;
import io.ruin.data.impl.npcs.npc_combat;
import io.ruin.model.activities.cluescrolls.impl.AnagramClue;
import io.ruin.model.activities.cluescrolls.impl.CrypticClue;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.KillCounter;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.item.loot.LootTable;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class NPCDef {

    public static Map<Integer, NPCDef> cached = Maps.newConcurrentMap();

    public static void load() {
        IndexFile index = Server.fileStore.get(2);
        int size = index.getLastFileId(9) + 1;
        for (int id = 0; id < size; id++) {
            byte[] data = index.getFile(9, id);
            if (data != null) {
                NPCDef def = new NPCDef();
                def.id = id;
                def.decode(new InBuffer(data));
                cached.put(id, def);
            }
        }
    }

    public static void forEach(Consumer<NPCDef> consumer) {
        for (NPCDef def : cached.values()) {
            if (def != null)
                consumer.accept(def);
        }
    }

    public static NPCDef get(int id) {
        return cached.get(id);
    }


    /**
     * Custom data
     */

    public String descriptiveName;

    public Class<? extends NPCCombat> combatHandlerClass;

    public LootTable lootTable;

    public NPCAction[] defaultActions;

    public HashMap<Integer, ItemNPCAction> itemActions;

    public ItemNPCAction defaultItemAction;

    public AnagramClue anagram;

    public CrypticClue cryptic;

    public npc_combat.Info combatInfo;


    public int attackOption = -1;

    public boolean flightClipping, swimClipping;

    public boolean occupyTiles = true;

    public boolean ignoreOccupiedTiles;

    public double giantCasketChance; // only used for bosses atm, other npcs use a formula (see GoldCasket)

    public boolean dragon;

    public boolean demon;

    public boolean leafBladed;

    public boolean undead;

    public boolean ignoreMultiCheck = false;

    public Function<Player, KillCounter> killCounter;

    /**
     * Cache data
     */

    public int id;
    public boolean isMinimapVisible = true;
    public int anInt3558;
    public int standAnimation = -1;
    @Getter
    public int size = 1;
    public int walkBackAnimation = -1;
    public String name = "null";
    public int rotateLeftAnimation = -1;
    public int rotateRightAnimation = -1;
    public int walkAnimation = -1;
    public int walkLeftAnimation = -1;
    public int walkRightAnimation = -1;
    public boolean isPet = false;
    public String[] options = new String[5];
    public int combatLevel = -1;
    public boolean hasRenderPriority = false;
    public int headIcon = -1;
    public int rotationSpeed = 32;
    public int[] showIds;
    public boolean isInteractable = true;
    public boolean rotationFlag = true;
    short[] retextureToReplace;
    short[] retextureToFind;
    public int varpId = -1;
    public int[] models;
    short[] recolorToFind;
    int[] chatheadModels;
    public int varpbitId = -1;
    short[] recolorToReplace;
    int resizeX = 128;
    int resizeY = 128;
    int ambient = 0;
    int contrast = 0;
    public HashMap<Object, Object> attributes;

    void decode(InBuffer buffer) {
        for (; ; ) {
            int opcode = buffer.readUnsignedByte();
            if (opcode == 0)
                break;
            decode(buffer, opcode);
        }
        final ImmutableSet DEMON = ImmutableSet.of(
                415, 416, 7241, 2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032, 7245, 7244, 7871, 7872, 7873,
                7246, 240, 1432, 2048, 2049, 2050, 2051, 2052, 5874, 5875, 5876, 5877, 6357, 7243, 7242, 7874, 7875, 7876,
                6295, 2005, 2006, 2007, 2008, 2018, 3982, 7656, 7657, 7664, 7247, 7865, 7866, 7867, 7248, 1443, 6382, 6321,
                7144, 7145, 7146,3129, 6495,  7147, 7148, 7149, 7152, 7584, 7585, 5862, 5863, 5866, 7095, 7096, 7097, 7150, 7151, 7153, 10508
        );

        final ImmutableSet UNDEAD_MONSTERS = ImmutableSet.of(
                26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 6596, 6597, 6598, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 3980, 3981, 2501, 2502, 2503, // Zombie
                924, 74, 75, 76, 70, 71, 72, 73, 3565, 3972, 3973, 3974, 7265, 130, 77, 78, 79, 80, 81, 6444, 6446, 82, 83, 2521, 2522, 2523, 2520, 1685, 1686, 1687, 1688, 6442, 2524, 2525, 2526, // Skeleton
                85, 3975, 6815, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 3976, 3977, 3978, 3979, 6816, 6817, 6818, 6819, 6820, 6821, 6822, 7263, 7264, 5370, 2527, 2528, 2529, 2530, // Ghost
                2514, 2517, // Ankou
                10508,
                949, 950, 951, 952, 953, 7658, 7659, 7660, 7661, 7662, 717, 720, 721, 722, 723, 725, 726, 727, 728, // Mummy
                448, 449, 451, 452, 453, 454, 456, 457, // Crawling hand
                2, 3, 4, 5, 6, 7, // Aberrant spectre
                7402, // Abhorrent spectre
                7279, // Deviant spectre
                6611, // Vet'ion
                7604, 7605, 7606, // Skeletal mystic
                7934, 7938, 7936, 7940, 7935, 7939, 7937, 7932, 7881, 7931, 7933, // Revenants
                8059, 8061, // Vortkath
                8063, // Zombified spawn
                15001, // corrupted nech
                6612 // Vet'ion reborn

        );

        final ImmutableSet LEAF_BLADED_NPCS = ImmutableSet.of(
                4158, 11902, 20727, 4160, 11875, 21316, 10508
        );


        if (name != null) {
            if (name.isEmpty())
                descriptiveName = name;
            else if (name.equals("Kalphite Queen") || name.equals("Corporeal Beast") || name.equals("King Black Dragon")
                    || name.equals("Kraken") || name.equals("Thermonuclear Smoke Devil") || name.equalsIgnoreCase("Crazy archaeologist") || name.equalsIgnoreCase("Chaos Fanatic") || name.equalsIgnoreCase("Chaos Elemental"))
                descriptiveName = "the " + name;
            else if (name.toLowerCase().matches("dagannoth (rex|prime|supreme)")
                    || name.equals("Cerberus") || name.equals("Zulrah") || name.equals("Callisto") || name.equals("Venenatis") || name.equals("Vet'ion") || name.equals("Scorpia"))
                descriptiveName = name;
            else if (StringUtils.vowelStart(name))
                descriptiveName = "an " + name;
            else
                descriptiveName = "a " + name;
        }

        attackOption = getOption("attack", "fight");
        flightClipping = name.toLowerCase().contains("impling") || name.toLowerCase().contains("butterfly");

        undead = UNDEAD_MONSTERS.contains(id);
        leafBladed = LEAF_BLADED_NPCS.contains(id);
        demon = DEMON.contains(id);
        dragon = name.toLowerCase().contains("vorkath") || name.toLowerCase().contains("dragon") || name.equalsIgnoreCase("elvarg") || name.equalsIgnoreCase("wyrm") || name.equalsIgnoreCase("drake") || name.toLowerCase().contains("hydra") || name.toLowerCase().contains("great olm") || name.toLowerCase().contains("dummy");
        // demon = name.toLowerCase().contains("demon") || name.equalsIgnoreCase("skotizo") || name.equalsIgnoreCase("imp") || name.toLowerCase().contains("nechryael") || name.toLowerCase().contains("abyssal sire") || name.toLowerCase().contains("k'ril") || name.toLowerCase().contains("balfrug") || name.toLowerCase().contains("tstanon") || name.toLowerCase().contains("zakl'n") || name.toLowerCase().contains("cerb") || name.toLowerCase().contains("hellhound") || name.toLowerCase().contains("bloodveld");
    }

    private Player player;

    void decode(InBuffer var1, int var2) {
        if (id == 15106) {
            combatLevel = 850;
            copyactions(7938);
            resizeX *= 2;
            resizeY *= 2;
            size = 2;
        } else if (var2 == 1) {
            int var4 = var1.readUnsignedByte();
            models = new int[var4];
            for (int var5 = 0; var5 < var4; var5++)
                models[var5] = var1.readUnsignedShort();
        } else if (var2 == 2)
            name = var1.readString();
        else if (var2 == 12)
            size = var1.readUnsignedByte();
        else if (var2 == 13)
            standAnimation = var1.readUnsignedShort();
        else if (var2 == 14)
            walkAnimation = var1.readUnsignedShort();
        else if (var2 == 15)
            rotateLeftAnimation = var1.readUnsignedShort();
        else if (var2 == 16)
            rotateRightAnimation = var1.readUnsignedShort();
        else if (var2 == 17) {
            walkAnimation = var1.readUnsignedShort();
            walkBackAnimation = var1.readUnsignedShort();
            walkLeftAnimation = var1.readUnsignedShort();
            walkRightAnimation = var1.readUnsignedShort();
        } else if (var2 == 18) {
            var1.readUnsignedShort(); // category
        } else if (var2 >= 30 && var2 < 35) {
            options[var2 - 30] = var1.readString();
            if (options[var2 - 30].equalsIgnoreCase("Hidden"))
                options[var2 - 30] = null;
        } else if (var2 == 40) {
            int var4 = var1.readUnsignedByte();
            recolorToFind = new short[var4];
            recolorToReplace = new short[var4];
            for (int var5 = 0; var5 < var4; var5++) {
                recolorToFind[var5] = (short) var1.readUnsignedShort();
                recolorToReplace[var5] = (short) var1.readUnsignedShort();
            }
        } else if (var2 == 41) {
            int var4 = var1.readUnsignedByte();
            retextureToFind = new short[var4];
            retextureToReplace = new short[var4];
            for (int var5 = 0; var5 < var4; var5++) {
                retextureToFind[var5] = (short) var1.readUnsignedShort();
                retextureToReplace[var5] = (short) var1.readUnsignedShort();
            }
        } else if (var2 == 60) {
            int length = var1.readUnsignedByte();
            chatheadModels = new int[length];
            for (int var5 = 0; var5 < length; var5++)
                chatheadModels[var5] = var1.readUnsignedShort();
        } else if (var2 == 93)
            isMinimapVisible = false;
        else if (var2 == 95)
            combatLevel = var1.readUnsignedShort();
        else if (var2 == 97)
            resizeX = var1.readUnsignedShort();
        else if (var2 == 98)
            resizeY = var1.readUnsignedShort();
        else if (var2 == 99)
            hasRenderPriority = true;
        else if (var2 == 100)
            ambient = var1.readByte();
        else if (var2 == 101)
            contrast = var1.readByte() * 5;
        else if (var2 == 102)
            headIcon = var1.readUnsignedShort();
        else if (var2 == 103)
            rotationSpeed = var1.readUnsignedShort();
        else if (var2 == 106 || var2 == 118) {
            varpbitId = var1.readUnsignedShort();
            if (varpbitId == 65535)
                varpbitId = -1;
            varpId = var1.readUnsignedShort();
            if (varpId == 65535)
                varpId = -1;
            int var4 = -1;
            if (var2 == 118) {
                var4 = var1.readUnsignedShort();
                if (var4 == 65535)
                    var4 = -1;
            }
            int var5 = var1.readUnsignedByte();
            showIds = new int[var5 + 2];
            for (int var6 = 0; var6 <= var5; var6++) {
                showIds[var6] = var1.readUnsignedShort();
                if (showIds[var6] == 65535)
                    showIds[var6] = -1;
            }
            showIds[var5 + 1] = var4;
        } else if (var2 == 107)
            isInteractable = false;
        else if (var2 == 109)
            rotationFlag = false;
        else if (var2 == 111)
            isPet = true;
        else if (var2 == 249) {
            int size = var1.readUnsignedByte();
            if (attributes == null)
                attributes = new HashMap<>();
            for (int i = 0; i < size; i++) {
                boolean stringType = var1.readUnsignedByte() == 1;
                int key = var1.readMedium();
                if (stringType)
                    attributes.put(key, var1.readString());
                else
                    attributes.put(key, var1.readInt());
            }
        } else {
            System.err.println("MISSING NPC OPCODE " + var2 + " FOR ID " + id);
        }
    }

    void copy(int otherId) {
        NPCDef otherDef = cached.get(otherId);
        try {
            isMinimapVisible = otherDef.isMinimapVisible;
            anInt3558 = otherDef.anInt3558;
            standAnimation = otherDef.standAnimation;
            size = otherDef.size;
            walkBackAnimation = otherDef.walkBackAnimation;
            name = otherDef.name;
            rotateLeftAnimation = otherDef.rotateLeftAnimation;
            rotateRightAnimation = otherDef.rotateRightAnimation;
            walkAnimation = otherDef.walkAnimation;
            walkLeftAnimation = otherDef.walkLeftAnimation;
            walkRightAnimation = otherDef.walkRightAnimation;
            isPet = otherDef.isPet;
            options = otherDef.options == null ? null : otherDef.options.clone();
            combatLevel = otherDef.combatLevel;
            hasRenderPriority = otherDef.hasRenderPriority;
            headIcon = otherDef.headIcon;
            rotationSpeed = otherDef.rotationSpeed;
            showIds = otherDef.showIds == null ? null : otherDef.showIds.clone();
            isInteractable = otherDef.isInteractable;
            rotationFlag = otherDef.rotationFlag;
            retextureToReplace = otherDef.retextureToReplace == null ? null : otherDef.retextureToReplace.clone();
            retextureToFind = otherDef.retextureToFind == null ? null : otherDef.retextureToFind.clone();
            varpId = otherDef.varpId;
            models = otherDef.models;
            recolorToFind = otherDef.recolorToFind == null ? null : otherDef.recolorToFind.clone();
            chatheadModels = otherDef.chatheadModels == null ? null : otherDef.chatheadModels.clone();
            varpbitId = otherDef.varpbitId;
            recolorToReplace = otherDef.recolorToReplace == null ? null : otherDef.recolorToReplace.clone();
            resizeX = otherDef.resizeX;
            resizeY = otherDef.resizeY;
            ambient = otherDef.ambient;
            contrast = otherDef.contrast;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void copyactions(int otherId) {
        NPCDef otherDef = cached.get(otherId);
        try {
            options = otherDef.options == null ? null : otherDef.options.clone();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean hasOption(String... searchOptions) {
        return getOption(searchOptions) != -1;
    }

    public int getOption(String... searchOptions) {
        if (options != null) {
            for (String s : searchOptions) {
                for (int i = 0; i < options.length; i++) {
                    String option = options[i];
                    if (s.equalsIgnoreCase(option))
                        return i + 1;
                }
            }
        }
        return -1;
    }

}

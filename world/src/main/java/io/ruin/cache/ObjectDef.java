package io.ruin.cache;

import com.google.common.collect.Maps;
import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;
import io.ruin.api.utils.StringUtils;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ObjectDef {

    public static boolean aBool1550 = false;
    private static final byte[] EMPTY_BUFFER = new byte[]{0};

    public static Map<Integer, ObjectDef> LOADED = Maps.newConcurrentMap();
    public static ObjectDef[] LOADED_EXTRA = new ObjectDef[10];

    @SuppressWarnings("Duplicates")
    public static void load() {
        IndexFile index = Server.fileStore.get(2);
        int size = index.getLastFileId(6) + 1;
        for (int id = 0; id < size; id++) {
            byte[] data = index.getFile(6, id);
            if (data != null) {
                ObjectDef def = new ObjectDef();
                def.id = id;
                def.decode(new InBuffer(data));
                if (def.hollow) {
                    def.interactType = 0;
                    def.blocksProjectile = false;
                }
                if (StringUtils.vowelStart(def.name))
                    def.descriptiveName = "an " + def.name;
                else
                    def.descriptiveName = "a " + def.name;
                LOADED.put(id, def);
            }
        }
    }

    public static void forEach(Consumer<ObjectDef> consumer) {
        for (ObjectDef def : LOADED.values()) {
            if (def != null)
                consumer.accept(def);
        }
    }

    public static ObjectDef get(int id) {
        return LOADED.get(id);
    }


    /**
     * Custom data
     */

    public String descriptiveName;

    public ObjectAction[] defaultActions;

    public HashMap<Integer, ItemObjectAction> itemActions;

    public ItemObjectAction defaultItemAction;

    public boolean bank;

    /**
     * Door data
     */

    public boolean gateType;

    public boolean longGate;

    public int doorOppositeId = -1;

    public boolean doorReversed, doorClosed;

    public int doorOpenSound = -1, doorCloseSound = -1;

    public boolean reversedConstructionDoor;

    /**
     * Separator
     */

    public int id;
    public int wallOrDoor = -1;
    public int supportsItems = -1;
    public String name = "null";
    public int xLength = 1;
    public int yLength = 1;
    public int interactType = 2;
    public boolean blocksProjectile = true;
    public boolean aBool1552 = false;
    public int animationID = -1; // idle animation
    public int decorDisplacement = 16;
    public int mapMarkerId = -1;
    public boolean shadow = true;
    public int anInt1578 = -1;
    public boolean obstructsGround = false;
    public int ambientSoundID = -1;
    public int unknownOpcode_78_79 = 0;
    public int anInt1548 = 0;
    public int anInt1571 = 0;
    public int[] anIntArray1597;
    public int[] showIds;
    public String[] options = new String[5];
    public int[] modelTypes;
    public int[] modelIds;
    int contouredGround = -1;
    boolean mergeNormals = false;
    int ambient = 0;
    int contrast = 0;
    public short[] originalModelColors;
    public short[] modifiedModelColors;
    short[] retextureToFind;
    short[] textureToReplace;
    public boolean rotated = false;
    public int render0x1 = 128;
    public int render0x2 = 128;
    public int render0x4 = 128;
    int offsetX = 0;
    int offsetHeight = 0;
    int offsetY = 0;
    public boolean hollow = false;
    public int varpBitId = -1;
    public int varpId = -1;
    public int someDirection;
    public int blockingMask;

    private void decode(InBuffer in) {
        for (; ; ) {
            int opcode = in.readUnsignedByte();
            if (opcode == 0)
                break;
            decode(in, opcode);
        }
    }

    private void decode(InBuffer in, int i) {
        if (i == 1) {
            int i_7_ = in.readUnsignedByte();
            if (i_7_ > 0) {
                if (modelIds == null || aBool1550) {
                    modelTypes = new int[i_7_];
                    modelIds = new int[i_7_];
                    for (int i_8_ = 0; i_8_ < i_7_; i_8_++) {
                        modelIds[i_8_] = in.readUnsignedShort();
                        modelTypes[i_8_] = in.readUnsignedByte();
                    }
                } else in.skip(i_7_ * 3);
            }
        } else if (i == 2)
            name = in.readString();
        else if (i == 5) {
            int i_9_ = in.readUnsignedByte();
            if (i_9_ > 0) {
                if (modelIds == null || aBool1550) {
                    modelTypes = null;
                    modelIds = new int[i_9_];
                    for (int i_10_ = 0; i_10_ < i_9_; i_10_++)
                        modelIds[i_10_] = in.readUnsignedShort();
                } else in.skip(i_9_ * 2);
            }
        } else if (i == 14)
            xLength = in.readUnsignedByte();
        else if (i == 15)
            yLength = in.readUnsignedByte();
        else if (i == 17) {
            interactType = 0;
            blocksProjectile = false;
        } else if (i == 18)
            blocksProjectile = false;
        else if (i == 19)
            wallOrDoor = in.readUnsignedByte();
        else if (i == 21)
            contouredGround = 0;
        else if (i == 22)
            mergeNormals = true;
        else if (i == 23)
            aBool1552 = true;
        else if (i == 24) {
            animationID = in.readUnsignedShort();
            if (animationID == 65535)
                animationID = -1;
        } else if (i == 27)
            interactType = 1;
        else if (i == 28)
            decorDisplacement = in.readUnsignedByte();
        else if (i == 29)
            ambient = in.readByte();
        else if (i == 39)
            contrast = in.readByte() * 25;
        else if (i >= 30 && i < 35) {
            options[i - 30] = in.readString();
            if (options[i - 30].equalsIgnoreCase("Hidden"))
                options[i - 30] = null;
        } else if (i == 40) {
            int i_11_ = in.readUnsignedByte();
            originalModelColors = new short[i_11_];
            modifiedModelColors = new short[i_11_];
            for (int i_12_ = 0; i_12_ < i_11_; i_12_++) {
                originalModelColors[i_12_] = (short) in.readUnsignedShort();
                modifiedModelColors[i_12_] = (short) in.readUnsignedShort();
            }
        } else if (i == 41) {
            int i_13_ = in.readUnsignedByte();
            retextureToFind = new short[i_13_];
            textureToReplace = new short[i_13_];
            for (int i_14_ = 0; i_14_ < i_13_; i_14_++) {
                retextureToFind[i_14_] = (short) in.readUnsignedShort();
                textureToReplace[i_14_] = (short) in.readUnsignedShort();
            }
        } else if (i == 60) //this was removed
            mapMarkerId = in.readUnsignedShort();
        else if (i == 61)
            in.readUnsignedShort(); // category
        else if (i == 62)
            rotated = true;
        else if (i == 64)
            shadow = false;
        else if (i == 65)
            render0x1 = in.readUnsignedShort();
        else if (i == 66)
            render0x2 = in.readUnsignedShort();
        else if (i == 67)
            render0x4 = in.readUnsignedShort();
        else if (i == 68)
            anInt1578 = in.readUnsignedShort();
        else if (i == 69)
            blockingMask = in.readUnsignedByte();
        else if (i == 70)
            offsetX = in.readShort();
        else if (i == 71)
            offsetHeight = in.readShort();
        else if (i == 72)
            offsetY = in.readShort();
        else if (i == 73)
            obstructsGround = true;
        else if (i == 74)
            hollow = true;
        else if (i == 75)
            supportsItems = in.readUnsignedByte();
        else if (i == 77 || i == 92) {
            varpBitId = in.readUnsignedShort();
            if (varpBitId == 65535)
                varpBitId = -1;
            varpId = in.readUnsignedShort();
            if (varpId == 65535)
                varpId = -1;
            int i_17_ = -1;
            if (i == 92) {
                i_17_ = in.readUnsignedShort();
                if (i_17_ == 65535)
                    i_17_ = -1;
            }
            int i_18_ = in.readUnsignedByte();
            showIds = new int[i_18_ + 2];
            for (int i_19_ = 0; i_19_ <= i_18_; i_19_++) {
                showIds[i_19_] = in.readUnsignedShort();
                if (showIds[i_19_] == 65535)
                    showIds[i_19_] = -1;
            }
            showIds[i_18_ + 1] = i_17_;
        } else if (i == 78) {
            ambientSoundID = in.readUnsignedShort();
            unknownOpcode_78_79 = in.readUnsignedByte();
        } else if (i == 79) {
            anInt1548 = in.readUnsignedShort();
            anInt1571 = in.readUnsignedShort();
            unknownOpcode_78_79 = in.readUnsignedByte();
            int i_15_ = in.readUnsignedByte();
            anIntArray1597 = new int[i_15_];
            for (int i_16_ = 0; i_16_ < i_15_; i_16_++)
                anIntArray1597[i_16_] = in.readUnsignedShort();
        } else if (i == 81)
            contouredGround = in.readUnsignedByte() * 256;
        else if (i == 82)
            in.readUnsignedShort(); // setMapAreaId(is.readUnsignedShort());
        else if (i == 89) ; // setRandomizeAnimStart(true);
        else if (i == 249) {
            int length = in.readUnsignedByte();

            Map<Integer, Object> params = new HashMap<>(length);
            for (i = 0; i < length; i++) {
                boolean isString = in.readUnsignedByte() == 1;
                int key = in.read24BitInt();
                Object value;

                if (isString) {
                    value = in.readString();
                } else {
                    value = in.readInt();
                }

                params.put(key, value);
            }
        }
            //System.err.println("MISSING OBJECT OPCODE " + i + " FOR ID " + id);
    }

    public boolean isClippedDecoration() {
        return wallOrDoor != 0 || interactType == 1 || obstructsGround;
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

    private void copy(int id) {
        if (this.id < id) {
            System.err.println("Unable to copy Object where target has lower id.");
            return;
        }

        ObjectDef from = LOADED.get(id);

        try {
            wallOrDoor = from.wallOrDoor;
            supportsItems = from.supportsItems;
            name = from.name;
            xLength = from.xLength;
            yLength = from.yLength;
            interactType = from.interactType;
            blocksProjectile = from.blocksProjectile;
            aBool1552 = from.aBool1552;
            animationID = from.animationID;
            decorDisplacement = from.decorDisplacement;
            mapMarkerId = from.mapMarkerId;
            shadow = from.shadow;
            anInt1578 = from.anInt1578;
            obstructsGround = from.obstructsGround;
            ambientSoundID = from.ambientSoundID;
            unknownOpcode_78_79 = from.unknownOpcode_78_79;
            anInt1548 = from.anInt1548;
            anInt1571 = from.anInt1571;
            anIntArray1597 = from.anIntArray1597 == null ? null : Arrays.copyOf(from.anIntArray1597, from.anIntArray1597.length);
            showIds = from.showIds == null ? null : Arrays.copyOf(from.showIds, from.showIds.length);
            options = from.options == null ? null : Arrays.copyOf(from.options, from.options.length);
            modelTypes = from.modelTypes == null ? null : Arrays.copyOf(from.modelTypes, modelTypes.length);
            modelIds = from.modelIds == null ? null : Arrays.copyOf(from.modelIds, from.modelIds.length);
            contouredGround = from.contouredGround;
            mergeNormals = from.mergeNormals;
            ambient = from.ambient;
            contrast = from.contrast;
            originalModelColors = from.originalModelColors == null ? null : Arrays.copyOf(from.originalModelColors, from.originalModelColors.length);
            modifiedModelColors = from.modifiedModelColors == null ? null : Arrays.copyOf(from.modifiedModelColors, from.modifiedModelColors.length);
            retextureToFind = from.retextureToFind == null ? null : Arrays.copyOf(from.retextureToFind, from.retextureToFind.length);
            textureToReplace = from.textureToReplace == null ? null : Arrays.copyOf(from.textureToReplace, from.textureToReplace.length);
            rotated = from.rotated;
            render0x1 = from.render0x1;
            render0x2 = from.render0x2;
            render0x4 = from.render0x4;
            offsetX = from.offsetX;
            offsetHeight = from.offsetHeight;
            offsetY = from.offsetY;
            hollow = from.hollow;
            varpBitId = from.varpBitId;
            varpId = from.varpId;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}

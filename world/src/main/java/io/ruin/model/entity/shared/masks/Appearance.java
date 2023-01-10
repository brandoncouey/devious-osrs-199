package io.ruin.model.entity.shared.masks;

import com.google.gson.annotations.Expose;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.cache.ItemDef;
import io.ruin.cache.NPCDef;
import io.ruin.model.combat.WeaponType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.UpdateMask;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;

public class Appearance extends UpdateMask {

    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Colors
     * 0=hair
     * 1=torso
     * 2=legs
     * 3=shoes
     * 4=skin
     */

    public static void changeColorForType(Player player, int colorType, int colorId) {
        switch (colorType) {
            case 0://hair type
                player.getAppearance().colors[0] = colorId;
                player.getAppearance().update();
                return;
            case 1://torso type
                player.getAppearance().colors[1] = colorId;
                player.getAppearance().update();
                return;
            case 2://legs type
                player.getAppearance().colors[2] = colorId;
                player.getAppearance().update();
                return;
            case 3://shoe type
                player.getAppearance().colors[3] = colorId;
                player.getAppearance().update();
                return;
            case 4://skin type
                player.getAppearance().colors[4] = colorId;
                player.getAppearance().update();
                return;
        }
    }

    public static void changeHairColor(Player player, int colorId) {
        player.getAppearance().colors[0] = colorId;
        player.getAppearance().update();
    }

    public static void changeTorsoColor(Player player, int colorId) {
        player.getAppearance().colors[1] = colorId;
        player.getAppearance().update();
    }

    public static void changeLegsColor(Player player, int colorId) {
        player.getAppearance().colors[2] = colorId;
        player.getAppearance().update();
    }

    public static void changeShoeColor(Player player, int colorId) {
        player.getAppearance().colors[3] = colorId;
        player.getAppearance().update();
    }

    /**
     * Styles
     * 0=hair
     * 1=beard
     * 2=top
     * 3=arms
     * 4=wrists
     * 5=legs
     * 6=shoes
     */

    public static void changeSyleForType(Player player, int styleType, int styleId) {
        switch (styleType) {
            case 0://hair
                player.getAppearance().styles[0] = styleId;
                player.getAppearance().update();
                return;
            case 1://beard
                player.getAppearance().styles[1] = styleId;
                player.getAppearance().update();
                return;
            case 2://top
                player.getAppearance().styles[2] = styleId;
                player.getAppearance().update();
                return;
            case 3://arms
                player.getAppearance().styles[3] = styleId;
                player.getAppearance().update();
                return;
            case 4://hair
                player.getAppearance().styles[4] = styleId;
                player.getAppearance().update();
                return;
            case 5://hair
                player.getAppearance().styles[5] = styleId;
                player.getAppearance().update();
                return;
            case 6://hair
                player.getAppearance().styles[6] = styleId;
                player.getAppearance().update();
                return;
        }


    }

    /**
     * Styles
     * 0=hair
     * 1=beard
     * 2=top
     * 3=arms
     * 4=wrists
     * 5=legs
     * 6=shoes
     */

    @Expose
    public int[] styles = {0, 10, 18, 26, 33, 36, 42};

    /**
     * Colors
     * 0=hair
     * 1=torso
     * 2=legs
     * 3=shoes
     * 4=skin
     */

    @Expose
    public int[] colors = new int[5];

    /**
     * Gender
     * 0=male
     * 1=female
     */

    @Expose
    private int gender = 0;

    public void setGender(int gender) {
        this.gender = gender;
    }

    public boolean isMale() {
        return gender == 0;
    }

    /**
     * Skull icon
     */

    private int skullIcon = -1;

    public void setSkullIcon(int skullIcon) {
        this.skullIcon = skullIcon;
        update();
    }

    public int getSkullIcon() {
        return skullIcon;
    }

    /**
     * Prayer icon
     */

    private int prayerIcon = -1;

    public void setPrayerIcon(int prayerIcon) {
        this.prayerIcon = prayerIcon;
        update();
    }

    /**
     * Npc id
     */

    private int npcId = -1;

    public void setNpcId(int npcId) {
        this.npcId = npcId;
        if (npcId == -1) {
            removeCustomRenders();
            return;
        }
        NPCDef def = NPCDef.get(npcId);
        setCustomRenders(def.standAnimation, -1, def.walkAnimation, def.walkBackAnimation, def.walkLeftAnimation, def.walkRightAnimation, -1);
    }

    public int getNpcId() {
        return npcId;
    }

    /**
     * Custom renders
     */

    private int[] customRenders;

    public void setCustomRenders(int... renders) {
        this.customRenders = renders;
        update();
    }

    public void removeCustomRenders() {
        this.customRenders = null;
        update();
    }

    /**
     * Mask info
     */

    private volatile boolean update = true;

    private final OutBuffer data = new OutBuffer(255);

    public void update() {
        update = true;
        data.position(0);
    }

    @Override
    public void reset() {
        update = false;
    }

    @Override
    public boolean hasUpdate(boolean added) {
        return update || added;
    }

    @Override
    public void send(OutBuffer out, boolean playerUpdate) {
        if (data.position() == 0) {
            data.addByte(gender);
            data.addByte(skullIcon);
            data.addByte(prayerIcon);
            if (npcId != -1) {
                data.addShort(-1);
                data.addShort(npcId);
            } else {
                append(player, data, Equipment.SLOT_HAT, -1);
                append(player, data, Equipment.SLOT_CAPE, -1);
                append(player, data, Equipment.SLOT_AMULET, -1);
                append(player, data, Equipment.SLOT_WEAPON, -1);

                append(player, data, Equipment.SLOT_CHEST, 2);
                append(player, data, Equipment.SLOT_SHIELD, -1);
                append(player, data, Equipment.SLOT_CHEST, 3);
                append(player, data, Equipment.SLOT_LEGS, 5);

                append(player, data, Equipment.SLOT_HAT, 0);
                append(player, data, Equipment.SLOT_HANDS, 4);
                append(player, data, Equipment.SLOT_FEET, 6);
                append(player, data, Equipment.SLOT_HAT, 1);

            }
            for (int color : colors)
                data.addByte(color);
            int[] renders;
            if (customRenders != null) {
                renders = customRenders;
            } else {
                ItemDef def = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
                if (def == null || def.weaponType == null)
                    renders = WeaponType.UNARMED.renderAnimations;
                else
                    renders = def.weaponType.renderAnimations;
            }
            for (int id : renders)
                if (player.isFly()) {
                    data.addShort(1501);
                } else {
                    data.addShort(id);
                }
            data.writeStringCp1252NullTerminated(player.getName());
/*            if (player.getTitle() == null) { //TODO: Custom Edits
                data.addString("");
                data.addString("");
            } else {
                String title = "";
                if (player.titleId != -1 && player.titleId < Title.PRESET_TITLES.length) { //normal titles
                    title = Title.PRESET_TITLES[player.titleId].getPrefix();
                    if (player.titleId == 22) { //custom title
                        title = player.customTitle;
                    }
                }
                data.addString(title);
                data.addString(player.getTitle().getSuffix() == null ? "" : player.getTitle().getSuffix());
            }*/
            data.addByte(player.getCombat().getLevel());
            data.addShort(0); // skillLevel
            data.addByte(0); // isHidden if this == 1

            data.addShort(0); // Vex (clan hub) shit.
        }
        int length = data.position();
        out.addByte(length);
        out.addBytesSpecial(data.payload(), 0, length);
    }

    private void append(Player player, OutBuffer out, int slot, int styleIndex) {
        if (styleIndex == 0 || styleIndex == 1 || styleIndex == 3) {
            Item item = player.getEquipment().get(slot);
            boolean hide = false;
            if (item != null) {
                if (styleIndex == 0)
                    hide = item.getDef().hideHair;
                else if (styleIndex == 1)
                    hide = item.getDef().hideBeard;
                else
                    hide = item.getDef().hideArms;
            }
            if (hide)
                out.addByte(0);
            else
                out.addShort(256 + styles[styleIndex]);
        } else {
            int itemId = player.getEquipment().getId(slot);
            if (itemId == -1) {
                if (styleIndex == -1)
                    out.addByte(0);
                else
                    out.addShort(256 + styles[styleIndex]);
                return;
            }
            out.addShort(512 + itemId);
        }
    }

    @Override
    public int get(boolean playerUpdate) {
        return 2;
    }

}
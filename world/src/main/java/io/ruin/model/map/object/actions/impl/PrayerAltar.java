package io.ruin.model.map.object.actions.impl;

import io.ruin.cache.ObjectDef;
import io.ruin.model.World;
import io.ruin.model.activities.godwars.GodwarsAltars;
import io.ruin.model.diaries.ardougne.ArdougneDiaryEntry;
import io.ruin.model.diaries.desert.DesertDiaryEntry;
import io.ruin.model.diaries.falador.FaladorDiaryEntry;
import io.ruin.model.diaries.kourend.KourendDiaryEntry;
import io.ruin.model.diaries.wilderness.WildernessDiaryEntry;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.inter.handlers.TabCombat;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.magic.SpellBook;
import io.ruin.model.skills.prayer.Bone;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

public class PrayerAltar {

    public static void pray(Player player) {
        Stat prayer = player.getStats().get(StatType.Prayer);
        if (prayer.currentLevel == prayer.fixedLevel) {
            player.sendMessage("You already have full prayer points.");
            return;
        }
        if (player.getPrayer().isActive(Prayer.CHIVALRY) && player.getPosition().inBounds(new Bounds(2613, 3304, 2621, 3310, 0))) {
            player.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.PRAY_WITH_CHIVALRY);
        }
        if (prayer.currentLevel <= 14 && player.getPosition().regionId() == 13099) {
            player.getDiaryManager().getDesertDiary().progress(DesertDiaryEntry.PRAY_SOPHANEM);
        }
        if (player.getPosition().regionId() == 11574) {
            player.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.ALTAR_OF_GUTHIX);
        }
        if (player.wildernessLevel >= 38) {
            player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.WILDERNESS_ALTAR);
        }
        prayer.restore();
        player.animate(645);
        player.privateSound(2674);
    }

    public static void switchBook(Player player, SpellBook book, boolean altar) {
        if (book.isActive(player) && altar) {
            player.dialogue(new MessageDialogue("You're already using this spellbook."));
            return;
        }
        book.setActive(player);
        TabCombat.updateAutocast(player, false);
        if (altar) {
            player.getDiaryManager().getKourendDiary().progress(KourendDiaryEntry.SPELL_BOOK);
            player.animate(645);
            player.sendMessage("You are now using the " + book.name + " spellbook.");
        }
    }

    private static void bonesOnAltar(Player player, Item item, Bone bone) {
        item.remove();
        player.animate(3705);
        player.sendFilteredMessage("The gods are pleased with your offerings.");
        player.getStats().addXp(StatType.Prayer, bone.exp * 1.5, true);
        World.sendGraphics(624, 0, 0, 3095, 3505, 0);
        bone.altarCounter.increment(player, 1);
        if (bone.id == 536 && player.getPosition().regionId() == 12342) {
            player.getDiaryManager().getKourendDiary().progress(KourendDiaryEntry.DBONE_ALTAR);
        }
    }

    static {
        /**
         * Registering all prayer altars
         */
        ObjectDef.forEach(def -> {
            if (def.hasOption("pray-at") && def.id != 411) {
                ObjectAction.register(def.id, "pray-at", (player, obj) -> pray(player));
                for (Bone bone : Bone.values()) {
                    SkillItem item = new SkillItem(bone.id).addAction((player, amount, event) -> {
                        while (amount-- > 0) {
                            Item boneItem = player.getInventory().findItem(bone.id);
                            if (boneItem == null)
                                return;
                            if (player.getInventory().hasMultiple(boneItem.getId())) {
                                bonesOnAltar(player, boneItem, bone);
                                event.delay(3);
                                continue;
                            }
                            bonesOnAltar(player, boneItem, bone);
                            break;
                        }
                    });
                    ItemObjectAction.register(bone.id, def.id, (player, boneItem, obj) -> {
                        if (player.getInventory().hasMultiple(boneItem.getId())) {
                            SkillDialogue.make(player, item);
                            return;
                        }
                        bonesOnAltar(player, boneItem, bone);
                    });
                }
            }
            for (GodwarsAltars value : GodwarsAltars.values())
                if (def.hasOption("pray") && def.id != value.altarId) {
                    ObjectAction.register(def.id, "pray", (player, obj) -> pray(player));
                    for (Bone bone : Bone.values()) {
                        SkillItem item = new SkillItem(bone.id).addAction((player, amount, event) -> {
                            while (amount-- > 0) {
                                Item boneItem = player.getInventory().findItem(bone.id);
                                if (boneItem == null)
                                    return;
                                if (player.getInventory().hasMultiple(boneItem.getId())) {
                                    bonesOnAltar(player, boneItem, bone);
                                    event.delay(3);
                                    continue;
                                }
                                bonesOnAltar(player, boneItem, bone);
                                break;
                            }
                        });
                        ItemObjectAction.register(bone.id, def.id, (player, boneItem, obj) -> {
                            if (player.getInventory().hasMultiple(boneItem.getId())) {
                                SkillDialogue.make(player, item);
                                return;
                            }
                            bonesOnAltar(player, boneItem, bone);
                        });
                    }
                }
        });
        /**
         * Custom Edgeville altar
         */
        final int[] ALTARS = new int[]{31861};
        for (int altar : ALTARS) {
            ObjectAction.register(altar, actions -> {
                actions[1] = (player, obj) -> pray(player);
                actions[2] = (player, obj) -> switchBook(player, SpellBook.MODERN, true);
                actions[3] = (player, obj) -> switchBook(player, SpellBook.ANCIENT, true);
                actions[4] = (player, obj) -> switchBook(player, SpellBook.LUNAR, true);
            });
        }
        /**
         * Bones on our home location altar
         */

        for (Bone boneo : Bone.values()) {
            ItemNPCAction.register(boneo.id, 111, (player, item, npc) -> {
                player.animate(827);
                player.getInventory().remove(item);
                npc.forceText("Woof Woof!");
            });
            ItemNPCAction.register(boneo.id, 2902, (player, item, npc) -> {
                player.animate(827);
                player.getInventory().remove(item);
                npc.forceText("Woof Woof!");
            });
            ItemNPCAction.register(boneo.id, 1760, (player, item, npc) -> {
                player.animate(827);
                player.getInventory().remove(item);
                npc.forceText("Woof Woof!");
            });
            ItemNPCAction.register(boneo.id, 2802, (player, item, npc) -> {
                player.animate(827);
                player.getInventory().remove(item);
                npc.forceText("Woof Woof!");
            });
            ItemNPCAction.register(boneo.id, 2922, (player, item, npc) -> {
                player.animate(827);
                player.getInventory().remove(item);
                npc.forceText("Woof Woof!");
            });
            ItemNPCAction.register(boneo.id, 3829, (player, item, npc) -> {
                player.animate(827);
                player.getInventory().remove(item);
                npc.forceText("Woof Woof!");
            });
            ItemNPCAction.register(boneo.id, 3830, (player, item, npc) -> {
                player.animate(827);
                player.getInventory().remove(item);
                npc.forceText("Woof Woof!");
            });
            ItemNPCAction.register(boneo.id, 4228, (player, item, npc) -> {
                player.animate(827);
                player.getInventory().remove(item);
                npc.forceText("Woof Woof!");
            });
            ItemNPCAction.register(boneo.id, 7025, (player, item, npc) -> {
                player.animate(827);
                player.getInventory().remove(item);
                npc.forceText("Woof Woof!");
            });
            ItemNPCAction.register(boneo.id, 7771, (player, item, npc) -> {
                player.animate(827);
                player.getInventory().remove(item);
                npc.forceText("Woof Woof!");
            });
            ItemNPCAction.register(boneo.id, 8041, (player, item, npc) -> {
                player.animate(827);
                player.getInventory().remove(item);
                npc.forceText("Woof Woof!");
            });
            ItemNPCAction.register(boneo.id, 10439, (player, item, npc) -> {
                player.animate(827);
                player.getInventory().remove(item);
                npc.forceText("Woof Woof!");
            });
            ItemNPCAction.register(boneo.id, 10675, (player, item, npc) -> {
                player.animate(827);
                player.getInventory().remove(item);
                npc.forceText("Woof Woof!");
            });
            ItemNPCAction.register(boneo.id, 10760, (player, item, npc) -> {
                player.animate(827);
                player.getInventory().remove(item);
                npc.forceText("Woof Woof!");
            });

        }

        for (Bone bone : Bone.values()) {
            SkillItem item = new SkillItem(bone.id).addAction((player, amount, event) -> {
                while (amount-- > 0) {
                    Item boneItem = player.getInventory().findItem(bone.id);
                    if (boneItem == null)
                        return;
                    if (player.getInventory().hasMultiple(boneItem.getId())) {
                        bonesOnAltar(player, boneItem, bone);
                        event.delay(3);
                        continue;
                    }
                    bonesOnAltar(player, boneItem, bone);
                    break;
                }
            });
            ItemObjectAction.register(bone.id, 18258, (player, boneItem, obj) -> {
                if (player.getInventory().hasMultiple(boneItem.getId())) {
                    SkillDialogue.make(player, item);
                    return;
                }
                bonesOnAltar(player, boneItem, bone);
            });
/*            ItemObjectAction.register(bone.id, 50001, (player, boneItem, obj) -> {
                if (player.getInventory().hasMultiple(boneItem.getId())) {
                    SkillDialogue.make(player, item);
                    return;
                }
                bonesOnAltar(player, boneItem, bone);
            });*/
        }
    }

}

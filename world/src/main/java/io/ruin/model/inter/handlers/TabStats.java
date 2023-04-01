package io.ruin.model.inter.handlers;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.combat.CombatUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAction;
import io.ruin.model.entity.player.XpMode;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.Bounds;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import io.ruin.network.incoming.desktop.handlers.CommandHandler;

import static io.ruin.model.entity.player.XpMode.EASY;

public class TabStats {

    static {
        InterfaceHandler.register(Interface.SKILLS, h -> {
            h.actions[1] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Attack);
                        break;
                    case 2:
                        int cbLevel = CombatUtils.getCombatLevel(player);
                        if (cbLevel <= 50) {
                            teleport(player, 2674, 3704, 0);
                            return;
                        }
                        if (cbLevel <= 90) {
                            teleport(player, 1723, 3465, 0);
                            return;
                        }
                        int level = player.getStats().get(StatType.Attack).currentLevel;
                        break;
                }
            };
            h.actions[2] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Strength);
                        break;
                    case 2:
                        int cbLevel = CombatUtils.getCombatLevel(player);
                        if (cbLevel <= 50) {
                            teleport(player, 2674, 3704, 0);
                            return;
                        }
                        if (cbLevel <= 90) {
                            teleport(player, 1723, 3465, 0);
                            return;
                        }
                        int level = player.getStats().get(StatType.Strength).currentLevel;
                        break;
                }
            };
            h.actions[3] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Defence);
                        break;
                    case 2:
                        int cbLevel = CombatUtils.getCombatLevel(player);
                        if (cbLevel <= 50) {
                            teleport(player, 2674, 3704, 0);
                            return;
                        }
                        if (cbLevel <= 90) {
                            teleport(player, 1723, 3465, 0);
                            return;
                        }
                        int level = CombatUtils.getCombatLevel(player);
                        break;
                }
            };
            h.actions[4] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Ranged);
                        break;
                    case 2:
                        int cbLevel = CombatUtils.getCombatLevel(player);
                        if (cbLevel <= 50) {
                            teleport(player, 2674, 3704, 0);
                            return;
                        }
                        if (cbLevel <= 90) {
                            teleport(player, 1723, 3465, 0);
                            return;
                        }
                        int level = player.getStats().get(StatType.Ranged).currentLevel;
                        break;
                }
            };
            h.actions[5] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Prayer);
                        break;
                    case 2:
                        int level = player.getStats().get(StatType.Prayer).currentLevel;
                        break;
                }
            };
            h.actions[6] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Magic);
                        break;
                    case 2:
                        int cbLevel = CombatUtils.getCombatLevel(player);
                        if (cbLevel <= 50) {
                            teleport(player, 2674, 3704, 0);
                            return;
                        }
                        if (cbLevel <= 90) {
                            teleport(player, 1723, 3465, 0);
                            return;
                        }
                        int level = player.getStats().get(StatType.Magic).currentLevel;
                        break;
                }
            };
            h.actions[7] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Runecrafting);
                        break;
                    case 2:
                        int level = player.getStats().get(StatType.Runecrafting).currentLevel;
                        break;
                }
            };
            h.actions[8] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Construction);
                        break;
                    case 2:
                        teleport(player, 3099, 3503, 0);
                        break;
                }
            };
            h.actions[9] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Hitpoints);
                        break;
                    case 2:
                        int level = player.getStats().get(StatType.Hitpoints).currentLevel;
                        break;
                }
            };
            h.actions[10] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Agility);
                        break;
                    case 2:
                        int level = player.getStats().get(StatType.Agility).currentLevel;
                         if (level >= 90) {//Ardougne
                            teleport(player, 2673, 3297, 0);
                        } else if (level >= 80) {//Relleka
                            teleport(player, 2625, 3677, 0);
                        } else if (level >= 70) {//Pollnivneach
                            teleport(player, 3351, 2961, 0);
                        } else if (level >= 60) {//Seers
                            teleport(player, 2729, 3489, 0);
                        } else if (level >= 50) {//Falador
                            teleport(player, 3036, 3341, 0);
                        } else if (level >= 40) {//Canafis
                            teleport(player, 3504, 3489, 0);
                        } else if (level >= 35) {//Barbarian Outpost
                            teleport(player, 2551, 3555, 0);
                        } else if (level >= 30) {//Varrock
                            teleport(player, 3221, 3414, 0);
                        } else if (level >= 20) {//Alkharid
                            teleport(player, 3273, 3195, 0);
                        } else if (level >= 10) {//Draynor
                            teleport(player, 3103, 3279, 0);
                        } else {
                            teleport(player, 2474, 3437, 0);
                        }
                        break;
                }
            };
            h.actions[11] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Herblore);
                        break;
                    case 2:
                        teleport(player, 3081, 3506, 0);
                        break;
                }
            };
            h.actions[12] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Thieving);
                        break;
                    case 2:
                        int level = player.getStats().get(StatType.Thieving).currentLevel;
                        if (level >= 95) {
                            teleport(player, 3086, 3509, 0);
                        } else if (level >= 80) {
                            teleport(player, 3086, 3508, 0);
                        } else if (level >= 60) {
                            teleport(player, 3086, 3507, 0);
                        } else if (level >= 30) {
                            teleport(player, 3086, 3506, 0);
                        } else {
                            teleport(player, 3086, 3505, 0);
                        }
                        break;
                }
            };
            h.actions[13] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Crafting);
                        break;
                    case 2:
                        teleport(player, 3086, 3510, 0);
                        break;
                }
            };
            h.actions[14] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Fletching);
                        break;
                    case 2:
                        teleport(player, 3082, 3472, 0);
                        break;
                }
            };
            h.actions[15] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Slayer);
                        break;
                    case 2:
                        teleport(player, 3112, 3510, 0);
                        break;
                }
            };
            h.actions[16] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Hunter);
                        break;
                    case 2:
                        int level = player.getStats().get(StatType.Hunter).currentLevel;
                        break;
                }
            };
            h.actions[17] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Mining);
                        break;
                    case 2:
                        int level = player.getStats().get(StatType.Mining).currentLevel;
                        break;
                }
            };
            h.actions[18] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Smithing);
                        break;
                    case 2:
                        teleport(player, 3109, 3499, 0);
                        break;
                }
            };
            h.actions[19] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Fishing);
                        break;
                    case 2:
                        int level = player.getStats().get(StatType.Fishing).currentLevel;
                        break;
                }
            };
            h.actions[20] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Cooking);
                        break;
                    case 2:
                        int level = player.getStats().get(StatType.Cooking).currentLevel;
                        break;
                }
            };
            h.actions[21] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Firemaking);
                        break;
                    case 2:
                        int level = player.getStats().get(StatType.Firemaking).currentLevel;
                        break;
                }
            };
            h.actions[22] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Woodcutting);
                        break;
                    case 2:
                        int level = player.getStats().get(StatType.Woodcutting).currentLevel;
                        break;
                }
            };
            h.actions[23] = (OptionAction) (player, option) -> {
                switch (option) {
                    case 1:
                        handleStat(player, StatType.Farming);
                        break;
                    case 2:
                        int level = player.getStats().get(StatType.Farming).currentLevel;
                        break;
                }
            };

        });

        InterfaceHandler.register(Interface.SKILL_GUIDE, h -> {
            h.actions[11] = (SimpleAction) p -> selectCategory(p, 0);
            h.actions[12] = (SimpleAction) p -> selectCategory(p, 1);
            h.actions[13] = (SimpleAction) p -> selectCategory(p, 2);
            h.actions[14] = (SimpleAction) p -> selectCategory(p, 3);
            h.actions[15] = (SimpleAction) p -> selectCategory(p, 4);
            h.actions[16] = (SimpleAction) p -> selectCategory(p, 5);
            h.actions[17] = (SimpleAction) p -> selectCategory(p, 6);
            h.actions[18] = (SimpleAction) p -> selectCategory(p, 7);
            h.actions[19] = (SimpleAction) p -> selectCategory(p, 8);
            h.actions[20] = (SimpleAction) p -> selectCategory(p, 9);
            h.actions[21] = (SimpleAction) p -> selectCategory(p, 10);
            h.actions[22] = (SimpleAction) p -> selectCategory(p, 11);
            h.actions[23] = (SimpleAction) p -> selectCategory(p, 12);
            h.actions[24] = (SimpleAction) p -> selectCategory(p, 13);
        });
    }

    private static void handleStat(Player player, StatType statType) {
        openGuide(player, statType, 0);
    }

    private static void handleTotal(Player player, int option) {
        if (option == 1)
            player.forceText("!My " + Color.ORANGE.wrap("Total Level") + " is " + NumberUtils.formatNumber(player.getStats().totalLevel) + ".");
        else
            player.forceText("!My " + Color.ORANGE.wrap("Total XP") + " is " + NumberUtils.formatNumber(player.getStats().totalXp) + ".");
    }

    public static void openGuide(Player player, StatType statType, int category) {
        Config.SKILL_GUIDE_STAT.set(player, statType.clientId);
        Config.SKILL_GUIDE_CAT.set(player, category);
        player.getPacketSender().sendAccessMask(Interface.SKILL_GUIDE, 8, 0, 99, 0);
        //player.getPacketSender().sendClientScript(917, "ii", 4600861, 80);
        player.getPacketSender().sendClientScript(2524, "ii", -1, -1);
        player.openInterface(InterfaceType.MAIN, Interface.SKILL_GUIDE);
    }

    private static void selectCategory(Player player, int category) {
        Config.SKILL_GUIDE_CAT.set(player, category);
    }

    public static void setLevel(Player player, StatType statType) {
        PlayerAction action = player.getAction(1);
        if (action == PlayerAction.FIGHT || action == PlayerAction.ATTACK) {
            player.sendMessage("You can't set levels from here.");
            return;
        }
        if (player.getBountyHunter().target != null) {
            player.sendMessage("You can't set levels while you have a target.");
            return;
        }
        if (player.joinedTournament) {
            player.sendMessage("You can't set levels while inside a tournament.");
            return;
        }
        int min = statType == StatType.Hitpoints ? 10 : 1;
        int max = 99;
        player.integerInput("Enter desired " + statType.name() + " level: (" + min + "-" + max + ")", level -> {
            if (level < min || level > max && !player.isAdmin()) {
                player.dialogue(new MessageDialogue("Invalid level, please try again."));
                return;
            }
            if (!player.isNearBank() && !player.getPosition().inBounds(new Bounds(3036, 3478, 3144, 3520, 0))) {
                player.dialogue(new MessageDialogue("You can only set your stats near a bank or around Devious."));
                return;
            }
            for (Item item : player.getEquipment().getItems()) {
                if (item == null)
                    continue;
                ItemDef def = item.getDef();
                int[] reqs = def.equipReqs;
                if (reqs == null)
                    continue;
                for (int req : reqs) {
                    StatType type = StatType.values()[req >> 8];
                    int lvl = req & 0xff;
                    if (type == statType && level < lvl) {
                        player.dialogue(new MessageDialogue("Before you can set your " + type.name() + " level to " + level + ",<br>you must first unequip your " + def.name + ".").lineHeight(24));
                        return;
                    }
                }
            }
            Stat stat = player.getStats().get(statType);
            stat.currentLevel = stat.fixedLevel = level;
            stat.experience = Stat.xpForLevel(level);
            stat.updated = true;
            player.getPrayer().deactivateAll();
            player.getCombat().updateLevel();
            player.dialogue(new MessageDialogue("Your " + statType.name() + " level is now " + level + "."));
        });
    }

    private static void teleport(Player player, int x, int y, int z) {
        if (player.wildernessLevel >= 20  || player.pvpAttackZone) {
            player.sendMessage("You can't use this command from where you are standing.");
            return;
        }
        player.getMovement().startTeleport(event -> {
            player.animate(3864);
            player.graphics(1039);
            player.privateSound(200, 0, 10);
            event.delay(2);
            player.getMovement().teleport(x, y, z);
        });
    }

}

package io.ruin.model.skills.slayer;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.impl.CompletionistCape;
import io.ruin.model.item.actions.impl.MaxCape;
import io.ruin.model.shop.ShopManager;
import io.ruin.model.stat.StatType;

import static io.ruin.model.skills.slayer.Slayer.reset;
import static io.ruin.model.skills.slayer.Slayer.set;

public class SlayerMaster {

    public static final int[] IDS = {6797, 7663, 8623, 401, 403, 405};

    public static final int TURAEL = 401;
    public static final int VANNAKA = 403;
    public static final int NIEVE = 6797;
    public static final int DURADEL = 405;

    public static final int KRYSTILIA = 7663;


    static {
        NPCAction.register(TURAEL, "Talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "'Ello, and what are you after then?"),
                new OptionsDialogue(
                        player.slayerTask == null ?
                                new Option("I need another easy assignment.", () -> {
                                    player.dialogue(
                                            new PlayerDialogue("I need another easy assignment."),
                                            new ActionDialogue(() -> assignEasy(player, npc))
                                    );
                                }) :
                                new Option("Tell me about my Slayer assignment.", () -> {
                                    player.dialogue(
                                            new PlayerDialogue("Tell me about my Slayer assignment."),
                                            new ActionDialogue(() -> assignEasy(player, npc))
                                    );
                                }),
                        new Option("Do you have anything to trade?", () -> {
                            player.dialogue(
                                    new PlayerDialogue("Do you have anything to trade?"),
                                    new NPCDialogue(npc, "I have a wide variety of Slayer equipment for sale! Have a look..").lineHeight(28),
                                    new ActionDialogue(() -> ShopManager.openIfExists(player, "06bf1d5a-e4d8-49f5-9aac-6229a965b98a"))
                            );
                        }),
                        new Option("Have you any rewards for me?", () -> {
                            player.dialogue(
                                    new PlayerDialogue("Have you any rewards for me?"),
                                    new NPCDialogue(npc, "I have quite a few rewards you can earn!<br>Take a look..").lineHeight(28),
                                    new ActionDialogue(() -> rewards(player))
                            );
                        }),
                        new Option("Pick a Task.", () -> player.dialogue(
                                new PlayerDialogue("Pick a task."),
                                new ActionDialogue(() -> {
                                    if (player.getStats().totalLevel < 2277) {
                                        player.sendMessage("You must have a total level of 2277 to select your tasks");
                                    } else {
                                        TaskSelector.TaskSelect(player);
                                    }
                                }))))));
        NPCAction.register(VANNAKA, "Talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "'Ello, and what are you after then?"),
                new OptionsDialogue(
                        player.slayerTask == null ?
                                new Option("I need another medium assignment.", () -> {
                                    player.dialogue(
                                            new PlayerDialogue("I need another medium assignment."),
                                            new ActionDialogue(() -> assignMedium(player, npc))
                                    );
                                }) :
                                new Option("Tell me about my Slayer assignment.", () -> {
                                    player.dialogue(
                                            new PlayerDialogue("Tell me about my Slayer assignment."),
                                            new ActionDialogue(() -> assignMedium(player, npc))
                                    );
                                }),
                        new Option("Do you have anything to trade?", () -> {
                            player.dialogue(
                                    new PlayerDialogue("Do you have anything to trade?"),
                                    new NPCDialogue(npc, "I have a wide variety of Slayer equipment for sale! Have a look..").lineHeight(28),
                                    new ActionDialogue(() -> ShopManager.openIfExists(player, "06bf1d5a-e4d8-49f5-9aac-6229a965b98a"))
                            );
                        }),
                        new Option("Have you any rewards for me?", () -> {
                            player.dialogue(
                                    new PlayerDialogue("Have you any rewards for me?"),
                                    new NPCDialogue(npc, "I have quite a few rewards you can earn!<br>Take a look..").lineHeight(28),
                                    new ActionDialogue(() -> rewards(player))
                            );
                        }),
                        new Option("Er... Nothing...", () -> player.dialogue(new PlayerDialogue("Er... Nothing...").animate(588)))
                )
        ));
        NPCAction.register(NIEVE, "Talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "'Ello, and what are you after then?"),
                new OptionsDialogue(
                        player.slayerTask == null ?
                                new Option("I need another hard assignment.", () -> {
                                    player.dialogue(
                                            new PlayerDialogue("I need another hard assignment."),
                                            new ActionDialogue(() -> assignHard(player, npc))
                                    );
                                }) :
                                new Option("Tell me about my Slayer assignment.", () -> {
                                    player.dialogue(
                                            new PlayerDialogue("Tell me about my Slayer assignment."),
                                            new ActionDialogue(() -> assignHard(player, npc))
                                    );
                                }),
                        new Option("Do you have anything to trade?", () -> {
                            player.dialogue(
                                    new PlayerDialogue("Do you have anything to trade?"),
                                    new NPCDialogue(npc, "I have a wide variety of Slayer equipment for sale! Have a look..").lineHeight(28),
                                    new ActionDialogue(() -> ShopManager.openIfExists(player, "06bf1d5a-e4d8-49f5-9aac-6229a965b98a"))
                            );
                        }),
                        new Option("Have you any rewards for me?", () -> {
                            player.dialogue(
                                    new PlayerDialogue("Have you any rewards for me?"),
                                    new NPCDialogue(npc, "I have quite a few rewards you can earn!<br>Take a look..").lineHeight(28),
                                    new ActionDialogue(() -> rewards(player))
                            );
                        }),
                        new Option("Er... Nothing...", () -> player.dialogue(new PlayerDialogue("Er... Nothing...").animate(588)))
                )
        ));
        NPCAction.register(DURADEL, "Talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "'Ello, and what are you after then?"),
                new OptionsDialogue(
                        player.slayerTask == null ?
                                new Option("I need another boss assignment.", () -> {
                                    player.dialogue(
                                            new PlayerDialogue("I need another boss assignment."),
                                            new ActionDialogue(() -> assignBoss(player, npc))
                                    );
                                }) :
                                new Option("Tell me about my Slayer assignment.", () -> {
                                    player.dialogue(
                                            new PlayerDialogue("Tell me about my Slayer assignment."),
                                            new ActionDialogue(() -> assignBoss(player, npc))
                                    );
                                }),
                        new Option("Do you have anything to trade?", () -> {
                            player.dialogue(
                                    new PlayerDialogue("Do you have anything to trade?"),
                                    new NPCDialogue(npc, "I have a wide variety of Slayer equipment for sale! Have a look..").lineHeight(28),
                                    new ActionDialogue(() -> ShopManager.openIfExists(player, "06bf1d5a-e4d8-49f5-9aac-6229a965b98a"))
                            );
                        }),
                        new Option("Have you any rewards for me?", () -> {
                            player.dialogue(
                                    new PlayerDialogue("Have you any rewards for me?"),
                                    new NPCDialogue(npc, "I have quite a few rewards you can earn!<br>Take a look..").lineHeight(28),
                                    new ActionDialogue(() -> rewards(player))
                            );
                        }),
                        new Option("Er... Nothing...", () -> player.dialogue(new PlayerDialogue("Er... Nothing...").animate(588)))
                )
        ));
        NPCAction.register(KRYSTILIA, "Talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "'Ello, and what are you after then?"),
                new OptionsDialogue(
                        player.slayerTask == null ?
                                new Option("I need another wilderness assignment.", () -> {
                                    player.dialogue(
                                            new PlayerDialogue("I need another wilderness assignment."),
                                            new ActionDialogue(() -> assignWilderness(player, npc))
                                    );
                                }) :
                                new Option("Tell me about my Slayer assignment.", () -> {
                                    player.dialogue(
                                            new PlayerDialogue("Tell me about my Slayer assignment."),
                                            new ActionDialogue(() -> assignWilderness(player, npc))
                                    );
                                }),
                        new Option("Do you have anything to trade?", () -> {
                            player.dialogue(
                                    new PlayerDialogue("Do you have anything to trade?"),
                                    new NPCDialogue(npc, "I have a wide variety of Slayer equipment for sale! Have a look..").lineHeight(28),
                                    new ActionDialogue(() -> ShopManager.openIfExists(player, "06bf1d5a-e4d8-49f5-9aac-6229a965b98a"))
                            );
                        }),
                        new Option("Have you any rewards for me?", () -> {
                            player.dialogue(
                                    new PlayerDialogue("Have you any rewards for me?"),
                                    new NPCDialogue(npc, "I have quite a few rewards you can earn!<br>Take a look..").lineHeight(28),
                                    new ActionDialogue(() -> rewards(player))
                            );
                        }),
                        new Option("Er... Nothing...", () -> player.dialogue(new PlayerDialogue("Er... Nothing...").animate(588)))
                )
        ));
        NPCAction.register(TURAEL, "Assignment", SlayerMaster::assignEasy);
        NPCAction.register(TURAEL, "Prev-Task(Completionist)", SlayerMaster::prevTask);
        NPCAction.register(TURAEL, "Trade", (player, npc) -> ShopManager.openIfExists(player, "06bf1d5a-e4d8-49f5-9aac-6229a965b98a"));
        NPCAction.register(TURAEL, "Rewards", (player, npc) -> rewards(player));
        NPCAction.register(VANNAKA, "Assignment", SlayerMaster::assignMedium);
        NPCAction.register(VANNAKA, "Trade", (player, npc) -> ShopManager.openIfExists(player, "06bf1d5a-e4d8-49f5-9aac-6229a965b98a"));
        NPCAction.register(VANNAKA, "Rewards", (player, npc) -> rewards(player));
        NPCAction.register(NIEVE, "Assignment", SlayerMaster::assignHard);
        NPCAction.register(NIEVE, "Trade", (player, npc) -> ShopManager.openIfExists(player, "06bf1d5a-e4d8-49f5-9aac-6229a965b98a"));
        NPCAction.register(NIEVE, "Rewards", (player, npc) -> rewards(player));
        NPCAction.register(DURADEL, "Assignment", SlayerMaster::assignBoss);
        NPCAction.register(DURADEL, "Trade", (player, npc) -> ShopManager.openIfExists(player, "06bf1d5a-e4d8-49f5-9aac-6229a965b98a"));
        NPCAction.register(DURADEL, "Rewards", (player, npc) -> rewards(player));
        NPCAction.register(KRYSTILIA, "Assignment", SlayerMaster::assignWilderness);
        NPCAction.register(KRYSTILIA, "Trade", (player, npc) -> ShopManager.openIfExists(player, "06bf1d5a-e4d8-49f5-9aac-6229a965b98a"));
        NPCAction.register(KRYSTILIA, "Rewards", (player, npc) -> rewards(player));
    }

    /**
     * Assignment
     */
    private static void assignEasy(Player player, NPC npc) {
        if (player.currentSlayerMaster == 0 && Slayer.getTask(player) == null) {
            player.dialogue(new NPCDialogue(npc, "Would you like me to assign your first slayer task? I assign Easy tasks."),
                    new OptionsDialogue(
                            new Option("Yes please.", () -> player.dialogue(
                                    new PlayerDialogue("Yes please."),
                                    new ActionDialogue(() -> {
                                        set(player, SlayerTask.Type.EASY);
                                        player.currentSlayerMaster = TURAEL;
                                        player.dialogue(new NPCDialogue(npc, "Your new task is to kill " + player.slayerTaskRemaining + " " + player.slayerTaskName + ".<br>Good luck!").lineHeight(28));
                                    }))),
                            new Option("No, Thanks.")
                    )
            );
            return;
        }
        if (Slayer.getTask(player) == null) {
            set(player, SlayerTask.Type.EASY);
            player.currentSlayerMaster = TURAEL;
            player.dialogue(new NPCDialogue(npc, "Your new task is to kill " + player.slayerTaskRemaining + " " + player.slayerTaskName + ".<br>Good luck!").lineHeight(28));
            return;
        }
        if (player.currentSlayerMaster == TURAEL && Slayer.getTask(player) != null) {
            player.dialogue(new NPCDialogue(npc, "You already have an easy task, Go kill " + player.slayerTaskRemaining + " more " + player.slayerTaskName));
            return;
        }
        if (player.currentSlayerMaster != TURAEL && Slayer.getTask(player) != null) {
            player.dialogue(new NPCDialogue(npc, "If you would like an easier task, I will need to reset your task streak."),
                    new OptionsDialogue(
                            new Option("Yes please.", () -> player.dialogue(
                                    new PlayerDialogue("Yes please."),
                                    new ActionDialogue(() -> {
                                        reset(player);
                                        player.slayerTasksCompleted = 0;
                                        set(player, SlayerTask.Type.EASY);
                                        player.currentSlayerMaster = TURAEL;
                                        player.dialogue(new NPCDialogue(npc, "Your new task is to kill " + player.slayerTaskRemaining + " " + player.slayerTaskName + ".<br>Good luck!").lineHeight(28));
                                    }))),
                            new Option("No, thanks.")
                    )
            );
        }
    }

    private static void prevTask(Player player, NPC npc) {
        if (!CompletionistCape.wearing(player)) {
            player.dialogue(new NPCDialogue(npc, "You must be wearing a completionist cape to use this feature."));
            return;
        }
        if (Slayer.getTask(player) == null) {
            player.slayerTaskName = player.prevslayerTaskName;
            player.dialogue(new ActionDialogue(() -> requestAmount(player, npc)));
        } else {
            player.dialogue(new NPCDialogue(npc, "You must have completed your current task before setting a new one"));
        }
    }

    private static void assignMedium(Player player, NPC npc) {
        if (player.getCombat().getLevel() < 40) {
            player.dialogue(new NPCDialogue(npc, "You need atleast 40 combat before i can assign you a task."));
            return;
        }
        if (Slayer.getTask(player) == null) {
            set(player, SlayerTask.Type.MEDIUM);
            player.currentSlayerMaster = VANNAKA;
            player.dialogue(new NPCDialogue(npc, "Your new task is to kill " + player.slayerTaskRemaining + " " + player.slayerTaskName + ".<br>Good luck!").lineHeight(28));
            return;
        }
        if (Slayer.getTask(player) != null) {
            player.dialogue(new NPCDialogue(npc, "You already have a task, Go kill " + player.slayerTaskRemaining + " more " + player.slayerTaskName + " Speak to Turael for an easier task."));
        }
    }

    private static void assignHard(Player player, NPC npc) {
        if (player.getCombat().getLevel() < 85) {
            player.dialogue(new NPCDialogue(npc, "You need atleast 85 combat before i can assign you a task."));
            return;
        }
        if (Slayer.getTask(player) == null) {
            set(player, SlayerTask.Type.HARD);
            player.currentSlayerMaster = NIEVE;
            player.dialogue(new NPCDialogue(npc, "Your new task is to kill " + player.slayerTaskRemaining + " " + player.slayerTaskName + ".<br>Good luck!").lineHeight(28));
            return;
        }
        if (Slayer.getTask(player) != null) {
            player.dialogue(new NPCDialogue(npc, "You already have a task, Go kill " + player.slayerTaskRemaining + " more " + player.slayerTaskName + " Speak to Turael for an easier task."));
        }

    }

    private static void assignBoss(Player player, NPC npc) {
        if (player.getCombat().getLevel() < 100 && player.getStats().get(StatType.Slayer).fixedLevel < 50 && Config.LIKE_A_BOSS.get(player) != 1) {
            player.dialogue(new NPCDialogue(npc, "You meet none of the requirements to talk to me."));
        } else if (player.getCombat().getLevel() < 100 || player.getStats().get(StatType.Slayer).fixedLevel < 50) {
            player.dialogue(new NPCDialogue(npc, "You need at least 100 Combat and 50 Slayer before I can assign you a task!"));
        } else if (Config.LIKE_A_BOSS.get(player) == 1) {
            if (Slayer.getTask(player) == null) {
                player.currentSlayerMaster = DURADEL;
                set(player, SlayerTask.Type.BOSS);
                player.dialogue(new ActionDialogue(() -> requestAmount(player, npc)));
                return;
            }
        } else {
            player.dialogue(new NPCDialogue(npc, "You haven't unlocked the ability to receive boss tasks yet."));
            return;
        }
        if (Slayer.getTask(player) != null) {
            player.dialogue(new NPCDialogue(npc, "You already have a task, Go kill " + player.slayerTaskRemaining + " more " + player.slayerTaskName + " Speak to Turael for an easier task."));
        }

    }

    private static void assignWilderness(Player player, NPC npc) {
        if (Slayer.getTask(player) == null) {
            player.currentSlayerMaster = KRYSTILIA;
            if (Config.LIKE_A_BOSS.get(player) == 1) {
                if (Random.rollDie(6, 1)) {
                    set(player, SlayerTask.Type.WILDERNESSBOSS);
                } else {
                    set(player, SlayerTask.Type.WILDERNESS);
                }
            } else {
                set(player, SlayerTask.Type.WILDERNESS);
            }
            player.dialogue(new NPCDialogue(npc, "Your new task is to kill " + player.slayerTaskRemaining + " " + player.slayerTaskName + ".<br>Good luck!").lineHeight(28));
            return;
        }
        if (Slayer.getTask(player) != null) {
            player.dialogue(new NPCDialogue(npc, "You already have a task, Go kill " + player.slayerTaskRemaining + " more " + player.slayerTaskName + " Speak to Turael for an easier task."));
        }

    }

    private static void requestAmount(Player player, NPC npc) {
        SlayerTask task = Slayer.getTask(player);
        if (task == null) { //literally should NEVER happen lol.
            reset(player);
            player.dialogue(new NPCDialogue(npc, "Oh my!"));
        } else {
            player.dialogue(
                    new NPCDialogue(npc, "Your new task is to kill " + player.slayerTaskName + ". How many would you like to slay?").lineHeight(28),
                    new NPCDialogue(npc, "If you don't put in an amount, you will be assigned a random amount.").lineHeight(28),
                    new ActionDialogue(() -> {
                        player.closeDialogue();
                        player.integerInput("Enter amount: (" + task.min + "-" + task.max + ")", amt -> {
                            if (amt < task.min || amt > task.max) {
                                player.retryIntegerInput("Invalid amount, try again: (" + task.min + "-" + task.max + ")");
                                return;
                            }
                            player.slayerTaskRemaining = amt;
                            player.dialogue(new NPCDialogue(npc, "Your new task is to kill " + player.slayerTaskRemaining + " " + player.slayerTaskName + ".<br>Good luck!").lineHeight(28));
                        });
                    })
            );
        }
    }

    /**
     * Rewards
     */

    private static void rewards(Player player) {
        SlayerUnlock.openRewards(player);
    }

}
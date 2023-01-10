package io.ruin.model.skills.slayer;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;

public class TaskSelector {
    // normal 50 - 150
    // boss 10 - 35
    public static void TaskSelect(Player player) {
        //Easy:
        //  - Spiders
        //  - Skeletons
        //  - Hill Giants
        //  - Ghosts
        //  - Bats
        player.dialogue(new OptionsDialogue("Select your TASK Difficulty!",
                new Option("Easy Slayer Task!", () -> {
                    player.dialogue(new OptionsDialogue("Select your TASK!",
                            new Option("Spiders!", () -> {
                                player.slayerTaskName = "Spiders";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(50, 150);
                                player.currentSlayerMaster = SlayerMaster.TURAEL;
                                player.slayerTaskType = SlayerTask.Type.EASY;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("Skeletons!", () -> {
                                player.slayerTaskName = "Skeletons";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(50, 150);
                                player.currentSlayerMaster = SlayerMaster.TURAEL;
                                player.slayerTaskType = SlayerTask.Type.EASY;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("Hill Giants!", () -> {
                                player.slayerTaskName = "Hill Giants";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(50, 150);
                                player.currentSlayerMaster = SlayerMaster.TURAEL;
                                player.slayerTaskType = SlayerTask.Type.EASY;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("Ghosts!", () -> {
                                player.slayerTaskName = "Ghosts";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(50, 150);
                                player.currentSlayerMaster = SlayerMaster.TURAEL;
                                player.slayerTaskType = SlayerTask.Type.EASY;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("Bats!", () -> {
                                player.slayerTaskName = "Bats";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(50, 150);
                                player.currentSlayerMaster = SlayerMaster.TURAEL;
                                player.slayerTaskType = SlayerTask.Type.EASY;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            })
                    ));
                }),
                //
                //Medium:
                //  - Fire Giants
                //  - Bloodvelds
                //  - Dust Devils
                //  - Cave Horrors
                //  - Blue Dragons
                //VANNAKA
                new Option("Medium Slayer Task!", () -> {
                    player.dialogue(new OptionsDialogue("Select your TASK!",
                            new Option("Fire Giants!", () -> {
                                player.slayerTaskName = "Fire Giants";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(50, 150);
                                player.currentSlayerMaster = SlayerMaster.VANNAKA;
                                player.slayerTaskType = SlayerTask.Type.MEDIUM;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("Bloodveld!", () -> {
                                player.slayerTaskName = "Bloodveld";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(50, 150);
                                player.currentSlayerMaster = SlayerMaster.VANNAKA;
                                player.slayerTaskType = SlayerTask.Type.MEDIUM;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("Dust Devils!", () -> {
                                player.slayerTaskName = "Dust Devils";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(50, 150);
                                player.currentSlayerMaster = SlayerMaster.VANNAKA;
                                player.slayerTaskType = SlayerTask.Type.MEDIUM;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("Cave Horrors!", () -> {
                                player.slayerTaskName = "Cave Horrors";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(50, 150);
                                player.currentSlayerMaster = SlayerMaster.VANNAKA;
                                player.slayerTaskType = SlayerTask.Type.MEDIUM;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("Blue Dragons!", () -> {
                                player.slayerTaskName = "Blue Dragons";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(50, 150);
                                player.currentSlayerMaster = SlayerMaster.VANNAKA;
                                player.slayerTaskType = SlayerTask.Type.MEDIUM;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            })
                    ));
                }),
                //Hard:
                //  - Black Demons
                //  - Hellhounds
                //  - Cave Krakens
                //  - Abyssal Demons
                //  - Smoke Devils
                //  - Black Dragons
                //NIEVE
                new Option("Hard Slayer Task!", () -> {
                    player.dialogue(new OptionsDialogue("Select your TASK!",
                            new Option("Black Demons!", () -> {
                                player.slayerTaskName = "Black Demons";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(50, 150);
                                player.currentSlayerMaster = SlayerMaster.NIEVE;
                                player.slayerTaskType = SlayerTask.Type.HARD;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("Black Dragons!", () -> {
                                player.slayerTaskName = "Black Dragons";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(50, 150);
                                player.currentSlayerMaster = SlayerMaster.NIEVE;
                                player.slayerTaskType = SlayerTask.Type.HARD;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("Hellhounds!", () -> {
                                player.slayerTaskName = "Hellhounds";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(50, 150);
                                player.currentSlayerMaster = SlayerMaster.NIEVE;
                                player.slayerTaskType = SlayerTask.Type.HARD;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("Cave Kraken!", () -> {
                                player.slayerTaskName = "Cave Kraken";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(50, 150);
                                player.currentSlayerMaster = SlayerMaster.NIEVE;
                                player.slayerTaskType = SlayerTask.Type.HARD;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("More", () -> {
                                player.dialogue(new OptionsDialogue("Select your TASK!",
                            new Option("Abyssal Demons!", () -> {
                                player.slayerTaskName = "Abyssal Demons";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(50, 150);
                                player.currentSlayerMaster = SlayerMaster.NIEVE;
                                player.slayerTaskType = SlayerTask.Type.HARD;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                                        new Option("Nechs", () -> {
                                            player.slayerTaskName = "Nechryael";
                                            player.prevslayerTaskName = player.slayerTaskName;
                                            player.slayerTaskRemaining = Random.get(50, 100);
                                            player.currentSlayerMaster = SlayerMaster.DURADEL;
                                            player.slayerTaskType = SlayerTask.Type.BOSS;
                                            player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                            if (player.Dslayertask == 2) {
                                                player.slayertasktimer = System.currentTimeMillis();
                                                player.Dslayertask = 3;
                                            }
                                            player.Dslayertask += 1;
                                        })));
                            })));
                }),
                //Boss:
                //  - Zulrah
                //  - Dagg Kings
                //  - Shamans
                //  - Aby sire
                //  - GWD   - Kree'arra
                //          - General Graardor
                //          - Kril Tsustaroth
                //          - Commander Zilyanna
                //DURADEL
                new Option("Boss Slayer Task!", () -> {
                    player.dialogue(new OptionsDialogue("Select your TASK!",
                            new Option("Zulrah!", () -> {
                                player.slayerTaskName = "Zulrah";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(50, 100);
                                player.currentSlayerMaster = SlayerMaster.DURADEL;
                                player.slayerTaskType = SlayerTask.Type.BOSS;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("Dagannoth Kings!", () -> {
                                player.slayerTaskName = "Dagannoth Kings";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(50, 100);
                                player.currentSlayerMaster = SlayerMaster.DURADEL;
                                player.slayerTaskType = SlayerTask.Type.BOSS;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("The Abyssal Sire!", () -> {
                                player.slayerTaskName = "The Abyssal Sire";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(50, 100);
                                player.currentSlayerMaster = SlayerMaster.DURADEL;
                                player.slayerTaskType = SlayerTask.Type.BOSS;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("The Alchemical Hydra!", () -> {
                                player.slayerTaskName = "The Alchemical Hydra";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(50, 100);
                                player.currentSlayerMaster = SlayerMaster.DURADEL;
                                player.slayerTaskType = SlayerTask.Type.BOSS;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("GWD Bosses!", () -> {
                                player.dialogue(new OptionsDialogue("Select your TASK!",
                                        new Option("Kree'arra!", () -> {
                                            player.slayerTaskName = "Kree'arra";
                                            player.prevslayerTaskName = player.slayerTaskName;
                                            player.slayerTaskRemaining = Random.get(50, 100);
                                            player.currentSlayerMaster = SlayerMaster.DURADEL;
                                            player.slayerTaskType = SlayerTask.Type.BOSS;
                                            player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                            if (player.Dslayertask == 2) {
                                                player.slayertasktimer = System.currentTimeMillis();
                                                player.Dslayertask = 3;
                                            }
                                            player.Dslayertask += 1;
                                        }),
                                        new Option("Commander Zilyana!", () -> {
                                            player.slayerTaskName = "Commander Zilyana";
                                            player.prevslayerTaskName = player.slayerTaskName;
                                            player.slayerTaskRemaining = Random.get(50, 100);
                                            player.currentSlayerMaster = SlayerMaster.DURADEL;
                                            player.slayerTaskType = SlayerTask.Type.BOSS;
                                            player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                            if (player.Dslayertask == 2) {
                                                player.slayertasktimer = System.currentTimeMillis();
                                                player.Dslayertask = 3;
                                            }
                                            player.Dslayertask += 1;
                                        }),
                                        new Option("General Graardor!", () -> {
                                            player.slayerTaskName = "General Graardor";
                                            player.prevslayerTaskName = player.slayerTaskName;
                                            player.slayerTaskRemaining = Random.get(50, 100);
                                            player.currentSlayerMaster = SlayerMaster.DURADEL;
                                            player.slayerTaskType = SlayerTask.Type.BOSS;
                                            player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                            if (player.Dslayertask == 2) {
                                                player.slayertasktimer = System.currentTimeMillis();
                                                player.Dslayertask = 3;
                                            }
                                            player.Dslayertask += 1;
                                        }),
                                        new Option("K'ril Tsutsaroth!", () -> {
                                            player.slayerTaskName = "K'ril Tsutsaroth";
                                            player.prevslayerTaskName = player.slayerTaskName;
                                            player.slayerTaskRemaining = Random.get(50, 100);
                                            player.currentSlayerMaster = SlayerMaster.DURADEL;
                                            player.slayerTaskType = SlayerTask.Type.BOSS;
                                            player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                            if (player.Dslayertask == 2) {
                                                player.slayertasktimer = System.currentTimeMillis();
                                                player.Dslayertask = 3;
                                            }
                                            player.Dslayertask += 1;
                                        })
                                ));
                            })
                    ));
                }),
                //Wilderness:
                //  - Spiders
                //  - Bears
                //  - Revenants
                //  - Green Dragons
                //  - Scorpions
                //KRYSTILIA
                new Option("Wilderness Slayer Task!", () -> {
                    player.dialogue(new OptionsDialogue("Select your TASK!",
                            new Option("Bears!", () -> {
                                player.slayerTaskName = "Bears";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(10, 35);
                                player.currentSlayerMaster = SlayerMaster.KRYSTILIA;
                                player.slayerTaskType = SlayerTask.Type.WILDERNESS;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("Revenants!", () -> {
                                player.slayerTaskName = "Revenants";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(10, 35);
                                player.currentSlayerMaster = SlayerMaster.KRYSTILIA;
                                player.slayerTaskType = SlayerTask.Type.WILDERNESS;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("Green Dragons!", () -> {
                                player.slayerTaskName = "Green Dragons";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(10, 35);
                                player.currentSlayerMaster = SlayerMaster.KRYSTILIA;
                                player.slayerTaskType = SlayerTask.Type.WILDERNESS;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("Scorpions!", () -> {
                                player.slayerTaskName = "Scorpions!";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(10, 35);
                                player.currentSlayerMaster = SlayerMaster.KRYSTILIA;
                                player.slayerTaskType = SlayerTask.Type.WILDERNESS;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            }),
                            new Option("Venenatis!", () -> {
                                player.slayerTaskName = "Venenatis";
                                player.prevslayerTaskName = player.slayerTaskName;
                                player.slayerTaskRemaining = Random.get(10, 35);
                                player.currentSlayerMaster = SlayerMaster.KRYSTILIA;
                                player.slayerTaskType = SlayerTask.Type.WILDERNESS;
                                player.sendMessage("Your new task is to kill " + (Color.RED.wrap(String.valueOf(player.slayerTaskRemaining)) + " X " + (Color.RED.wrap(String.valueOf(player.slayerTaskName)) + ".<br>Good luck!")));
                                if (player.Dslayertask == 2) {
                                    player.slayertasktimer = System.currentTimeMillis();
                                    player.Dslayertask = 3;
                                }
                                player.Dslayertask += 1;
                            })
                    ));
                })
        ));
    }
}

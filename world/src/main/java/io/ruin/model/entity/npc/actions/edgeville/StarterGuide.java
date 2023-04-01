package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.cache.Color;
import io.ruin.cache.NPCDef;
import io.ruin.data.impl.Help;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.GameMode;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAction;
import io.ruin.model.entity.player.XpMode;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.entity.shared.listeners.LogoutListener;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.map.Direction;
import io.ruin.network.central.CentralClient;
import io.ruin.services.Referral;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Misc;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.model.entity.player.XpMode.*;

@Slf4j
public class StarterGuide {

    private static final NPC GUIDE = SpawnListener.first(306);

    private static final Item[] REGULAR_STARTER = new Item[] {
           new Item(COINS_995, 100000), // gp
            new Item(558, 500), // Mind Rune
            new Item(556, 2000), // Air Rune
            new Item(554, 1000), // Fire Rune
            new Item(555, 1000), // Water Rune
            new Item(557, 1000), // Earth Rune
            new Item(562, 500), // Chaos Rune
            new Item(560, 250), // Death Rune
            new Item(1381, 1), // Air Staff
            new Item(362, 50), // Tuna
            new Item(841, 1), // Shortbow
            new Item(884, 500), // Iron Arrow
            new Item(863, 300), // Iron Knives
            new Item(867, 150), // Adamant Knives
            new Item(1169, 1), // Coif
            new Item(1129, 1), // Leather body
            new Item(1095, 1), // Leather Chaps
            new Item(12867, 1), // Blue d hide set
            new Item(13012, 1), // Addy set
            new Item(1323, 1), // Iron scim
            new Item(1331, 1), // Addy scim
            new Item(1712, 1), // Amulet of glory (4)
            new Item(2552, 1), // RIng of dueling (8)
            new Item(20211, 1), // Team Cape
    };

    private static final Item[] IRONMAN_STARTER = new Item[] {
            new Item(COINS_995, 25000), // gp
            new Item(558, 500), // Mind Rune
            new Item(556, 1000), // Air Rune
            new Item(554, 500), // Fire Rune
            new Item(555, 500), // Water Rune
            new Item(557, 500), // Earth Rune
            new Item(1381, 1), // Air Staff
            new Item(362, 25), // Tuna
            new Item(841, 1), // Shortbow
            new Item(884, 250), // Iron Arrow
            new Item(863, 150), // Iron Knives
            new Item(1169, 1), // Coif
            new Item(1129, 1), // Leather body
            new Item(1095, 1), // Leather Chaps
            new Item(12810, 1), // Ironman Helm
            new Item(12811, 1), // Ironman Chest
            new Item(12812, 1), // Ironman legs
            new Item(1323, 1), // Iron scim
            new Item(20211, 1), // Team Cape

    };

    private static final Item[] ULTIMATE_IRONMAN_STARTER = new Item[] {
            new Item(COINS_995, 15000), // gp
            new Item(558, 250), // Mind Rune
            new Item(556, 500), // Air Rune
            new Item(554, 250), // Fire Rune
            new Item(555, 250), // Water Rune
            new Item(557, 250), // Earth Rune
            new Item(1381, 1), // Air Staff
            new Item(362, 25), // Tuna
            new Item(841, 1), // Shortbow
            new Item(884, 150), // Iron Arrow
            new Item(863, 100), // Iron Knives
            new Item(1169, 1), // Coif
            new Item(1129, 1), // Leather body
            new Item(1095, 1), // Leather Chaps
            new Item(12813, 1), // Ultimate Ironman Helm
            new Item(12814, 1), // Ultimate Ironman Chest
            new Item(12815, 1), // Ultimate Ironman legs
            new Item(1323, 1), // Iron scim
            new Item(20211, 1), // Team Cape
    };

    private static final Item[] HARDCORE_IRONMAN_STARTER = new Item[] {
            new Item(COINS_995, 10000), // gp
            new Item(558, 150), // Mind Rune
            new Item(556, 250), // Air Rune
            new Item(554, 150), // Fire Rune
            new Item(555, 150), // Water Rune
            new Item(557, 150), // Earth Rune
            new Item(362, 15), // Tuna
            new Item(841, 1), // Shortbow
            new Item(884, 50), // Iron Arrow
            new Item(1169, 1), // Coif
            new Item(1129, 1), // Leather body
            new Item(1095, 1), // Leather Chaps
            new Item(20792, 1), // HC Ironman Helm
            new Item(20794, 1), // HC Ironman Chest
            new Item(20796, 1), // HC Ironman legs
            new Item(1323, 1), // Iron scim
            new Item(20211, 1), // Team Cape
    };

    private static final Item[] GROUP_IRONMAN_STARTER = new Item[] {
            new Item(COINS_995, 25000), // gp
            new Item(558, 500), // Mind Rune
            new Item(556, 1000), // Air Rune
            new Item(554, 500), // Fire Rune
            new Item(555, 500), // Water Rune
            new Item(557, 500), // Earth Rune
            new Item(1381, 1), // Air Staff
            new Item(362, 25), // Tuna
            new Item(841, 1), // Shortbow
            new Item(884, 250), // Iron Arrow
            new Item(863, 150), // Iron Knives
            new Item(1169, 1), // Coif
            new Item(1129, 1), // Leather body
            new Item(1095, 1), // Leather Chaps
            new Item(26156, 1), // Group Ironman Helm
            new Item(26158, 1), // Group Ironman Chest
            new Item(26166, 1), // Group Ironman legs
            new Item(26168, 1), // Group Ironman Vamp
            new Item(1323, 1), // Iron scim
            new Item(20211, 1), // Team Cape
    };

    private static final Item[] HARDCORE_GROUP_IRONMAN_STARTER = new Item[] {
            new Item(COINS_995, 10000), // gp
            new Item(558, 100), // Mind Rune
            new Item(556, 150), // Air Rune
            new Item(554, 100), // Fire Rune
            new Item(555, 100), // Water Rune
            new Item(557, 100), // Earth Rune
            new Item(362, 15), // Tuna
            new Item(841, 1), // Shortbow
            new Item(884, 50), // Iron Arrow
            new Item(1169, 1), // Coif
            new Item(1129, 1), // Leather body
            new Item(1095, 1), // Leather Chaps
            new Item(1323, 1), // Iron scim
            new Item(26170, 1), // HC Group Ironman Helm
            new Item(26172, 1), // HC Group Ironman Chest
            new Item(26180, 1), // HC Group Ironman legs
            new Item(26182, 1), // HC Group Ironman Vamp
            new Item(20211, 1), // Team Cape

    };

    public static List<String> ipsClaimed = new ArrayList<>();


    static {

        InterfaceHandler.register(1040, h -> {

            h.actions[21] = (SimpleAction) p -> { // Easy
                if (!p.newPlayer && p.xpMode == EASY) {
                    p.sendMessage("You are already on that XP Mode.");
                    return;
                }
                if (!p.newPlayer) {
                    p.dialogue(new OptionsDialogue("Change XP Mode to Easy?",
                            new Option("Yes, I want [Easy] as my XP Mode", () -> setXpMode(p, EASY)),
                            new Option("Nevermind", () -> changeXPMode(p))));
                    return;
                }
                setXpMode(p, EASY);
                p.getPacketSender().sendString(1040, 15, "Current Mode: " + "Easy");
            };
            h.actions[24] = (SimpleAction) p -> { // Normal
                if (!p.newPlayer && p.xpMode == NORMAL) {
                    p.sendMessage("You are already on that XP Mode.");
                    return;
                }
                if (!p.newPlayer && XpMode.NORMAL.ordinal() > p.xpMode.ordinal()) {
                    p.sendMessage("<col=ff0000>You cannot change your XP mode to a harder mode.");
                    return;
                }
                if (!p.newPlayer) {
                    p.dialogue(new OptionsDialogue("Change XP Mode to Normal?",
                            new Option("Yes, I want [Normal] as my XP Mode", () -> setXpMode(p, NORMAL)),
                            new Option("Nevermind", () -> changeXPMode(p))));
                    return;
                }
                setXpMode(p, NORMAL);
                p.getPacketSender().sendString(1040, 15, "Current Mode: " + "Normal");
            };
            h.actions[27] = (SimpleAction) p -> { // Medium
                if (!p.newPlayer && p.xpMode == MEDIUM) {
                    p.sendMessage("You are already on that XP Mode.");
                    return;
                }
                if (!p.newPlayer && XpMode.MEDIUM.ordinal() > p.xpMode.ordinal()) {
                    p.sendMessage("<col=ff0000>You cannot change your XP mode to a harder mode.");
                    return;
                }
                if (!p.newPlayer) {
                    p.dialogue(new OptionsDialogue("Change XP Mode to Medium?",
                            new Option("Yes, I want [Medium] as my XP Mode", () -> setXpMode(p, MEDIUM)),
                            new Option("Nevermind", () -> changeXPMode(p))));
                    return;
                }
                setXpMode(p, MEDIUM);
                p.getPacketSender().sendString(1040, 15, "Current Mode: " + "Medium");
            };
            h.actions[30] = (SimpleAction) p -> { // Hard
                if (!p.newPlayer && p.xpMode == HARD) {
                    p.sendMessage("You are already on that XP Mode.");
                    return;
                }
                if (!p.newPlayer && XpMode.HARD.ordinal() > p.xpMode.ordinal()) {
                    p.sendMessage("<col=ff0000>You cannot change your XP mode to a harder mode.");
                    return;
                }
                if (!p.newPlayer) {
                    p.dialogue(new OptionsDialogue("Change XP Mode to Hard?",
                            new Option("Yes, I want [Hard] as my XP Mode", () -> setXpMode(p, HARD)),
                            new Option("Nevermind", () -> changeXPMode(p))));
                    return;
                }
                setXpMode(p, HARD);
                p.getPacketSender().sendString(1040, 15, "Current Mode: " + "Hard");
            };
            h.actions[33] = (SimpleAction) p -> { // Realistic
                if (!p.newPlayer && p.xpMode == REALISTIC) {
                    p.sendMessage("You are already on that XP Mode.");
                    return;
                }
                if (!p.newPlayer) {
                    p.sendMessage("<col=ff0000>You cannot change your XP mode to a harder mode.");
                    return;
                }
                setXpMode(p, REALISTIC);
                p.getPacketSender().sendString(1040, 15, "Current Mode: " + "Realistic");
            };

        });


        InterfaceHandler.register(1032, h -> {

            h.actions[80] = (SimpleAction) p ->  {
                p.openInterface(InterfaceType.MAIN, 1040);
                p.getPacketSender().sendString(1040, 15, "Current Mode: " + "Normal");
            };

            h.actions[18] = (SimpleAction) p -> { // Regular
                setXpMode(p, MEDIUM);
                Config.IRONMAN_MODE.set(p, 0);
                sendItems(p, REGULAR_STARTER);
                p.getPacketSender().sendString(Interface.STARTER_INTERFACE, 73, "Regular");
                        getInfo(p);
            };

            h.actions[21] = (SimpleAction) p -> { // Ironman
                Config.IRONMAN_MODE.set(p, 1);
                sendItems(p, IRONMAN_STARTER);
                p.getPacketSender().sendString(Interface.STARTER_INTERFACE, 73, "Ironman");
                getInfo(p);
            };

            h.actions[24] = (SimpleAction) p -> { // Ultimate Ironman
                sendItems(p, ULTIMATE_IRONMAN_STARTER);
                p.getPacketSender().sendString(Interface.STARTER_INTERFACE, 73, "Ultimate Ironman");
                Config.IRONMAN_MODE.set(p, 2);
                getInfo(p);
            };

            h.actions[27] = (SimpleAction) p -> { // Hardcore Ironman
                sendItems(p, HARDCORE_IRONMAN_STARTER);
                p.getPacketSender().sendString(Interface.STARTER_INTERFACE, 73, "Hardcore Ironman");
                Config.IRONMAN_MODE.set(p, 3);
                getInfo(p);
            };


            h.actions[30] = (SimpleAction) p -> { // Group Ironman
                sendItems(p, GROUP_IRONMAN_STARTER);
                p.getPacketSender().sendString(Interface.STARTER_INTERFACE, 73, "Group Ironman");
                Config.IRONMAN_MODE.set(p, 4);
                getInfo(p);
            };

            h.actions[33] = (SimpleAction) p -> { // HC Group Ironman
                p.getPacketSender().sendString(Interface.STARTER_INTERFACE, 73, "Hardcore Group Ironman");
                sendItems(p, HARDCORE_GROUP_IRONMAN_STARTER);
                Config.IRONMAN_MODE.set(p, 5);
                getInfo(p);
            };


        });
        NPCDef.get(306).ignoreOccupiedTiles = true;
        NPCAction.register(306, "talk-to", StarterGuide::optionsDialogue);

        LoginListener.register(player -> {
            for (Player p : World.players) {
                if (p.newPlayer && !p.inTutorial) {
                    player.startEvent(e -> {
                        Config.IRONMAN_MODE.set(player, 0);
                        setXpMode(p, NORMAL);
                        sendItems(p, REGULAR_STARTER);
                        player.lock(LockType.FULL_ALLOW_LOGOUT);
                        CentralClient.sendClanRequest(player.getUserId(), "help");
                        player.openInterface(InterfaceType.MAIN, Interface.STARTER_INTERFACE);
                        player.getPacketSender().sendString(Interface.STARTER_INTERFACE, 73, "Normal");
                        getInfo(player);
                        while (player.isVisibleInterface(Interface.STARTER_INTERFACE)) {
                            e.delay(1);
                        }
                        if (!p.choseXpMode) {
                            p.openInterface(InterfaceType.MAIN, 1040);
                            p.getPacketSender().sendString(1040, 15, "Current Mode: " + Misc.formatStringFormal(p.xpMode.name()));
                        }
                        while (player.isVisibleInterface(1040)) {
                            e.delay(1);
                        }
                        p.choseXpMode = true;
                        tutorial(player);
                    });
                }
            }

            if (!player.ipAddress.equals(player.LastipAddress) && player.LastipAddress != null) {
                if (player.isStaff()) {
                    player.lock();
                    player.forceLogout();
                }
                for (Player p : World.players) {
                    if (p.isStaff()) {
                        p.sendMessage(Color.RED, player.getName() + " has logged in from an ip address that doesn't match there last ip!");
                    }
                }
            }
            player.LastipAddress = player.ipAddress;
        });
    }
    public static void openXPModeSelection(Player player) {
        player.openInterface(InterfaceType.MAIN, 1040);
    }
    private static void optionsDialogue(Player player, NPC npc) {
        player.dialogue(new NPCDialogue(npc, "Hello " + player.getName() + ", is there something I could assist you with?"),
                new OptionsDialogue(
                        new Option("View help pages", () -> Help.open(player)),
                        new Option("Change XP Mode", () -> changeXPMode(player)),
                        new Option("Change home point", () -> {
                            npc.startEvent(event -> {
                                if (!player.dzHome) {
                                    player.dialogue(new NPCDialogue(npc, "I can move your spawn point and <br>" +
                                                    "home teleport location to Donator Zone, " +
                                                    "Both options are 10M GP.<br>" +
                                                    "Would you like to do this?"),
                                            new OptionsDialogue(
                                                    new Option("No thanks.", () -> {
                                                        player.dialogue(new PlayerDialogue("No thanks."),
                                                                new NPCDialogue(npc, "Okay, if you change your mind you know where to find me."));
                                                    }),
                                                    new Option("Yes! I want to respawn at Donator Zone!", () -> {
                                                        if (player.getInventory().hasItem(995, 10000000)) {
                                                            player.getInventory().remove(995, 10000000);
                                                            player.dzHome = true;
                                                            player.dialogue(new NPCDialogue(npc, "Your spawn point has been changed<br>" +
                                                                    "to Donator Zone! If you'd like to change<br>" +
                                                                    "it back, just speak to me again."));
                                                        } else {
                                                            player.dialogue(new NPCDialogue(npc, "Oh, you don't seem to have the money with you right now. Come back when you do!"));
                                                        }
                                                    })));
                                } else {
                                    player.dialogue(
                                            new NPCDialogue(npc, "Are you wanting to move your<br>" +
                                                    "spawn point back to home? It will cost<br>" +
                                                    "another 10,000,000 GP."),
                                            new OptionsDialogue(
                                                    new Option("No thanks.", player::closeDialogue),
                                                    new Option("Yes please!", () -> {
                                                        if (player.getInventory().hasItem(995, 10000000)) {
                                                            player.getInventory().remove(995, 10000000);
                                                            player.dzHome = false;
                                                            player.dialogue(new NPCDialogue(npc, "Your spawn point has been changed<br>" +
                                                                    "back to home. If you'd like it changed,<br>" +
                                                                    "just speak to me again!"));
                                                        } else {
                                                            player.dialogue(new NPCDialogue(npc, "I'm sorry but it doesn't look like<br>" +
                                                                    "you can afford this.."));
                                                        }
                                                    })));
                                }
                            });
                        })));
    }

    @SneakyThrows
    public static void ecoTutorial(Player player) {
        boolean actuallyNew = player.newPlayer;
        player.finishedIntro = false;
        player.inTutorial = true;
        player.startEvent(event -> {
            player.lock(LockType.FULL_ALLOW_LOGOUT);
            if (actuallyNew) {
                player.openInterface(InterfaceType.MAIN, Interface.MAKE_OVER_MAGE);
                while (player.isVisibleInterface(Interface.MAKE_OVER_MAGE)) {
                    event.delay(1);
                }
            }
            NPC guide = new NPC(306).spawn(3088, 3501, 0, Direction.EAST, 0); // 307 is a copy of 306 without options so it doesnt get in other people's way
            player.logoutListener = new LogoutListener().onLogout(p -> guide.remove());
            player.getPacketSender().sendHintIcon(guide);
            guide.face(player);
            player.face(guide);
            if (actuallyNew) {
               /*                player.dialogue(
                        new NPCDialogue(guide, "Please select an experience mode.<br>" +
                                "You can change this later, but only to an easier<br>" +
                                "experience mode, not harder."));*/

                /*                event.waitForDialogue(player);*/
                String text = "You want to be a part of the economy, then? Great!";
                if (player.getGameMode() == GameMode.IRONMAN) {
                    text = "Iron Man, huh? Self-sufficiency is quite a challenge, good luck!";
                } else if (player.getGameMode() == GameMode.HARDCORE_IRONMAN) {
                    text = "Hardcore?! You only live once... make it count!";
                } else if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN) {
                    text = "Ultimate Iron Man... Up for quite the challenge, aren't you?";
                } else if (player.getGameMode() == GameMode.GROUP_IRONMAN) {
                    player.checkGIM = true;
                    player.newPlayer = false;
                    player.setAction(1, PlayerAction.GIM_Invite);
                    text = "Group Iron Man... You enjoy the team challenge, Don't you?";
                } else if (player.getGameMode() == GameMode.HARDCORE_GROUP_IRONMAN) {
                    player.checkGIM = true;
                    player.newPlayer = false;
                    player.setAction(1, PlayerAction.GIM_Invite);
                    text = "Hardcore Group Iron Man?! You only live once... make it count! Upon death you will became standard group ironman.";
                }
                if (player.getGameMode().isIronMan()) {
                    player.dialogue(new NPCDialogue(guide, text),
                            new NPCDialogue(guide, "I'll give you a few items to help get you started..."),
                            new NPCDialogue(guide, "There you go, some basic stuff. If you need anything else, remember to check the shops.") {
                                @Override
                                public void open(Player player) {
                                    giveStarter(player);
                                    player.newPlayer = false;
                                    super.open(player);
                                }
                            });
                } else {
                    player.dialogue(new NPCDialogue(guide, "Excellent.. I'll give you a few items to help get you started and fill your bank with various starter items"),
                            new NPCDialogue(guide, "There you go, some basic stuff. If you need anything else, remember to check Sigmund's shop.") {
                                @Override
                                public void open(Player player) {
                                    giveStarter(player);
                                    player.newPlayer = false;
                                    super.open(player);
                                }
                            });
                }
                event.waitForDialogue(player);
                Broadcast.WORLD.sendNews(player.getName() + " has just joined " + World.type.getWorldName() + " for the first time!");
                for (Player p : World.players) {
                    p.getPacketSender().sendMessage(player.getName() + " has just joined " + World.type.getWorldName() + " for the first time!", "", 14);
                }
                    player.dialogue(new NPCDialogue(guide,

                "Greetings, " + player.getName() + "! Welcome to " + World.type.getWorldName() + ".<br>" +
                                            "Would you like a run down on how the server works?"),
                            new OptionsDialogue("Play the tutorial?",
                                    new Option("Yes, Show me the ropes!", () -> startTutorial(guide, player)),
                                    new Option("No, I'm an experienced player.", () -> {
                                    player.closeDialogue();
                                    player.inTutorial = false;
                                    player.logoutListener = null;
                                    player.newPlayer = false;
                                    player.finishedIntro = true;
                                    player.setTutorialStage(0);
                                    player.unlock();
                                    guide.addEvent(evt -> {
                                        evt.delay(2);
                                        World.sendGraphics(86, 50, 0, guide.getPosition());
                                        guide.remove();
                                    });
                })));
        }
    });
}
        public static void referralCode(NPC guide, Player player) {
            guide.startEvent((e) -> {
                player.stringInput("Please enter the referral code you'd like to claim:", referralCode -> {
                    Referral.checkClaimed(player, referralCode, alreadyClaimed -> {
                        if (alreadyClaimed) {
                            player.dialogue(new MessageDialogue("You've already claimed this referral code."));
                            return;
                        }
                        Referral.claim(player, referralCode, success -> {
                            if (success) {
                                player.dialogue(new MessageDialogue("You've successfully claimed the referral code."));
                                guide.animate(863);
                                player.inTutorial = false;
                                player.unlock();
                                player.setTutorialStage(0);
                                guide.addEvent(evt -> {
                                    evt.delay(2);
                                    World.sendGraphics(86, 50, 0, guide.getPosition());
                                    player.logoutListener = null;
                                    guide.remove();
                                    player.finishedIntro = true;
                                    player.getPacketSender().resetCamera();
                                });
                            } else
                                player.dialogue(new MessageDialogue("Error claiming referral code. Please try again later."));
                            guide.animate(863);
                            player.inTutorial = false;
                            player.unlock();
                            player.setTutorialStage(0);
                            player.newPlayer = false;
                            guide.addEvent(evt -> {
                                evt.delay(2);
                                World.sendGraphics(86, 50, 0, guide.getPosition());
                                player.logoutListener = null;
                                guide.remove();
                                player.finishedIntro = true;
                                player.getPacketSender().resetCamera();
                            });
                        });
                    });
                });
            });
        }
    public static void startTutorial(NPC guide, Player player) {
        guide.startEvent((e) -> {
            player.getPacketSender().resetCamera();
            player.getPacketSender().sendClientScript(39, "i", 100);
            Config.LOCK_CAMERA.set(player, 1);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "Welcome to Devious.<br>" +
                            "Where you will have an experience like no other!<br>" +
                            "Join the discord for updates<br>" +
                            "Good luck!"));
            e.waitForDialogue(player);

            // bank loc under here second one tp
            player.getMovement().teleport(3081, 3496, 0);
            player.getPacketSender().moveCameraToLocation(3091, 3502, 1500, 0, 12);
            player.getPacketSender().turnCameraToLocation(3087, 3495, 2500, 0, 18);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "Here we have the trading post.<br>" +
                            "You can buy items from other players, much like<br>" +
                            "the grand exchange."));
            e.waitForDialogue(player);
//construction portal
            player.getMovement().teleport(3099, 3502, 0);
            player.getPacketSender().moveCameraToLocation(3095, 3497, 1500, 0, 12);
            player.getPacketSender().turnCameraToLocation(3099, 3504, 2500, 0, 30);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "Here is the construction portal. Devious offers full<br>" +
                            "construction! There are also NPC's here to sell you<br>" +
                            "supplies to build your house and remodel it as well."));
            e.waitForDialogue(player);
            // safe pvp zone
            player.getMovement().teleport(3083, 3515, 0);
            player.getPacketSender().moveCameraToLocation_HOME(3087, 3515, 1500, 0, 12);
            player.getPacketSender().turnCameraToLocation_HOME(3080, 3515, 2500, 0, 30);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "Here are the PvP vendors and altars.<br>" +
                            "You can find death by going down those stairs..."));
            e.waitForDialogue(player);

            player.getMovement().teleport(3082, 3492, 0);
            player.getPacketSender().moveCameraToLocation(3089, 3487, 1500, 0, 12);
            player.getPacketSender().turnCameraToLocation(3092, 3483, 2500, 0, 10);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "You can find all of our shops between these 4 buildings."));
            e.waitForDialogue(player);
            player.getMovement().teleport(3088, 3465, 0);
            player.getPacketSender().moveCameraToLocation(3092, 3466, 1500, 0, 12);
            player.getPacketSender().turnCameraToLocation(3081, 3463, 2500, 0, 10);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "Here you can find the AFK skilling. You can receieve x2 xp here by voting!"));
            e.waitForDialogue(player);
            player.getMovement().teleport(3122, 3475, 0);
            player.getPacketSender().moveCameraToLocation(3109, 3474, 1500, 0, 12);
            player.getPacketSender().turnCameraToLocation(3121, 3477, 2500, 0, 10);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "This is the pen. You can find all pet upgrades and services here."));
            e.waitForDialogue(player);
            Config.LOCK_CAMERA.set(player, 0);
            player.getPacketSender().resetCamera();
            player.setTutorialStage(1);

            guide.getMovement().teleport(3088, 3501, 0);
            player.getMovement().teleport(3087, 3501, 0);
            guide.face(player);
            player.face(guide);
            player.getPacketSender().moveCameraToLocation(3087, 3497, 450, 0, 12);
            player.getPacketSender().turnCameraToLocation(3087, 3501,400, 0, 30);
            player.dialogue(new NPCDialogue(guide,
                    "If you have any other questions, there are always<br>" +
                            "helpful users in the help clan chat"));
            e.waitForDialogue(player);
            /*player.dialogue(new OptionsDialogue("Were you referred by a Content Creator?",
                    new Option("Yes!", () -> referralCode(guide, player)),
                    new Option("No, I know what I'm doing!", () -> {*/
            guide.animate(863);
            player.inTutorial = false;
            player.unlock();
            player.setTutorialStage(0);
            guide.addEvent(evt -> {
                evt.delay(2);
                World.sendGraphics(86, 50, 0, guide.getPosition());
                player.logoutListener = null;
                guide.remove();
            });
            player.finishedIntro = true;
            player.getPacketSender().resetCamera();
       // })));
    });
    }


    private static void giveStarter(Player player) {
        final int mode = Config.IRONMAN_MODE.get(player);
        switch (mode) {
            case 0:
                for (Item item : REGULAR_STARTER) {
                    player.getInventory().add(item);
                }
                break;
            case 1:
                for (Item item : IRONMAN_STARTER) {
                    player.getInventory().add(item);
                }
                break;
            case 2:
                for (Item item : ULTIMATE_IRONMAN_STARTER) {
                    player.getInventory().add(item);
                }
                break;
            case 3:
                for (Item item : HARDCORE_IRONMAN_STARTER) {
                    player.getInventory().add(item);
                }
                break;
            case 4:
                for (Item item : GROUP_IRONMAN_STARTER) {
                    player.getInventory().add(item);
                }
                break;
            case 5:
                for (Item item : HARDCORE_GROUP_IRONMAN_STARTER) {
                    player.getInventory().add(item);
                }
                break;
        }
    }

    public static void changeXPMode(Player player) {
        player.startEvent(e -> {
            player.openInterface(InterfaceType.MAIN, 1040);
            player.getPacketSender().sendString(1040, 15, "Current Mode: " + "Easy");
            player.sendMessage("<col=ff0000>You can only change to an easier XP Mode!");
            player.choseXpMode = false;
            while (player.isVisibleInterface(1040)) {
                e.delay(1);
            }
            player.choseXpMode = true;
        });

    }

    private static void giveIronStarter(Player player) {
        player.getInventory().add(COINS_995, 10000); // gp
        player.getInventory().add(558, 300); // Mind Rune
        player.getInventory().add(556, 500); // Air Rune
        player.getInventory().add(554, 500); // Fire Rune
        player.getInventory().add(555, 500); // Water Rune
        player.getInventory().add(557, 500); // Earth Rune
        player.getInventory().add(562, 200); // Chaos Rune
        player.getInventory().add(1381, 1); // Air Staff
        player.getInventory().add(362, 50); // Tuna
        player.getInventory().add(863, 200); // Iron Knives
        player.getInventory().add(1323, 1); // Iron scim
        player.getInventory().add(20211, 1); // Team cape
        switch (player.getGameMode()) {
            case IRONMAN:
                player.getInventory().add(12810, 1);
                player.getInventory().add(12811, 1);
                player.getInventory().add(12812, 1);
                break;
            case ULTIMATE_IRONMAN:
                player.getInventory().add(12813, 1);
                player.getInventory().add(12814, 1);
                player.getInventory().add(12815, 1);
                break;
            case HARDCORE_IRONMAN:
                player.getInventory().add(20792, 1);
                player.getInventory().add(20794, 1);
                player.getInventory().add(20796, 1);
                break;
            case GROUP_IRONMAN:
                player.getInventory().add(26156, 1);
                player.getInventory().add(26158, 1);
                player.getInventory().add(26166, 1);
                player.getInventory().add(26168, 1);
                player.getMovement().teleport(3105, 3028, 0);
                break;
            case HARDCORE_GROUP_IRONMAN:
                player.getInventory().add(26170, 1);
                player.getInventory().add(26172, 1);
                player.getInventory().add(26180, 1);
                player.getInventory().add(26182, 1);
                player.getMovement().teleport(3105, 3028, 0);
                break;
            case STANDARD:
                player.getInventory().add(COINS_995, 115000);
                break;
        }
        if (player.xpMode == REALISTIC) {
            player.getInventory().add(30416, 1);
        }

    }

    private static NPC find(Player player, int id) {
        for (NPC n : player.localNpcs()) {
            if (n.getId() == id)
                return n;
        }
        throw new IllegalArgumentException();
    }

    private static void setDrag(Player player) {
        player.dialogue(
                new OptionsDialogue("What drag setting would you like to use?",
                        new Option("5 (OSRS) (2007) Drag", () -> setDrag(player, 5)),
                        new Option("10 (Pre-EoC) (2011) Drag", () -> setDrag(player, 10))
                )
        );
    }

    private static void setDrag(Player player, int drag) {
        player.dragSetting = drag;
    }

    private static void tutorial(Player player) {
        ecoTutorial(player);
    }

    private static String getCombatRate(Player player) {
        if (player.xpMode == XpMode.EASY) {
            return "Combat XP Rate: 200x";
        } else if (player.xpMode == XpMode.HARD) {
            return "Combat XP Rate: 10x";
        } else if (player.xpMode == XpMode.NORMAL) {
            return "Combat XP Rate: 100x";
        } else if (player.xpMode == MEDIUM) {
            return "Combat XP Rate: 25x";
        } else if (player.xpMode == REALISTIC) {
            return "Combat XP Rate: 5x";
        } else {
            return "Combat XP Rate: ";
        }
    }

    private static String getSkillRate(Player player) {
        if (player.xpMode == XpMode.EASY) {
            return "Skill XP Rate: 200x";
        } else if (player.xpMode == XpMode.HARD) {
            return "Skill XP Rate: 10x";
        } else if (player.xpMode == XpMode.MEDIUM) {
            return "Skill XP Rate: 25x";
        } else if (player.xpMode == XpMode.NORMAL) {
            return "Skill XP Rate: 100x";
        } else if (player.xpMode == REALISTIC) {
            return "Skill XP Rate: 5x";
        } else {
            return "Skill XP Rate: ";
        }
    }

    private static String getDropRate(Player player) {
        if (player.xpMode == XpMode.EASY) {
            return "Base Drop Rate: 0%";
        } else if (player.xpMode == XpMode.HARD) {
            return "Base Drop Rate: 10%";
        } else if (player.xpMode == XpMode.NORMAL) {
            return "Base Drop Rate: 6%";
        } else if (player.xpMode == XpMode.MEDIUM) {
            return "Base Drop Rate: 8%";
        } else if (player.xpMode == XpMode.REALISTIC) {
            return "Base Drop Rate: 15%";
        } else {
            return "Base Drop Rate: 0%";
        }
    }

    private static String getBonusDropRate(Player player) {
        if (player.xpMode == REALISTIC) {
            return "Extra Items: Devious Katana";
        } else if (player.getGameMode() == GameMode.IRONMAN) {
            return "Extra Items: Ironman Armour";
        } else if (player.getGameMode() == GameMode.HARDCORE_IRONMAN) {
            return "Extra Items: HC Ironman Armour";
        } else if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN) {
            return "Extra Items: UIM Ironman Armour";
        } else if (player.getGameMode() == GameMode.GROUP_IRONMAN) {
            return "Extra Items: GIM Armour";
        } else if (player.getGameMode() == GameMode.HARDCORE_GROUP_IRONMAN) {
            return "Extra Items: HC GIM Armour";
        } else {
            return "Extra Items: N/A";
        }
    }

    private static String getRestrictions(Player player) {
        if (player.getGameMode() == GameMode.IRONMAN) {
            return "Ironman Standalone";
        } else if (player.getGameMode() == GameMode.HARDCORE_IRONMAN) {
            return "One life, Standing alone";
        } else if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN) {
            return "No Trading/Banking";
        } else if (player.getGameMode() == GameMode.GROUP_IRONMAN) {
            return "Trading within Group only";
        } else if (player.getGameMode() == GameMode.HARDCORE_GROUP_IRONMAN) {
            return "One life/Trade in a Group";
        } else {
            return "No Restrictions.";
        }
    }

    private static void getInfo(Player player) {
        player.getPacketSender().sendString(Interface.STARTER_INTERFACE, 74, getCombatRate(player) + "<br>" + getSkillRate(player) + "<br>" + getDropRate(player) + "<br>" + getBonusDropRate(player) + "<br>" + getRestrictions(player));
    }

    private static void sendItems(Player player, Item[] items) {
        for (int index = 0; index < 27; index++) {
            player.getPacketSender().sendItems(1032, (41 + index), 0, new Item(-1));
        }
        for (int index = 0; index < items.length; index++) {
            player.getPacketSender().sendItems(1032, (41 + index), 0, items[index]);
        }
    }

    public static void setXpMode(Player player, XpMode xpMode) {
        player.xpMode = xpMode;
    }

}

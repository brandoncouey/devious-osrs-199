package io.ruin.process;

import io.ruin.Server;
import io.ruin.api.utils.Random;
import io.ruin.event.GameEventProcessor;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.events.EventManager;
import io.ruin.model.inter.journal.toggles.TargetOverlay;
import io.ruin.model.object.owned.OwnedObject;

import static io.ruin.network.MobileType.ANDROID;

public class CoreWorker extends World {

    private static Stage processStage = Stage.INDEX;
    public static long LAST_CYCLE_UPDATE;

    public enum Stage {

        INDEX(CoreWorker::index),
        LOGIC(CoreWorker::logic),
        UPDATE(CoreWorker::update);

        private final Runnable runnable;

        Stage(Runnable runnable) {
            this.runnable = runnable;
        }

    }

    public static void process() {
        try {
            for (Stage stage : Stage.values()) {
                processStage = stage;
                processStage.runnable.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LAST_CYCLE_UPDATE = System.currentTimeMillis();
    }

    public static boolean isPast(Stage stage) {
        return processStage.ordinal() > stage.ordinal();
    }

    /**
     * Index - Used for indexing players & npcs.
     */

    private static int scrambleTicks;

    private static void index() {
        /**
         * Npc indexing
         */
        try {
            npcs.resetCount();
            for (NPC npc : npcs.entityList) {
                if (npc != null)
                    npcs.index(npc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * Player indexing
         */
        try {
            players.resetCount();
            for (Player player : players.entityList) {
                if (player != null)
                    players.index(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
         * Scrambling
         */
        try {
            if (--scrambleTicks <= 0) {
                scrambleTicks = Random.get(40, 60);
                npcs.scramble();
                players.scramble();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Logic - Things like packet handling, combat, movement, etc.
     */
    private static void logic() {
        try {
            GameEventProcessor.pulse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            EventManager.getInstance().checkEvents();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(NPC npc : npcs) {
            try {
                npc.processed = true;
                npc.process();
            } catch(Exception t) {
                t.printStackTrace();
            }
        }
        for(Player player : players) {
            try {
                player.checkLogout();
            } catch(Exception t) {
                t.printStackTrace();
            }
        }
        for(Player player : players) {
            try {
                if(player.isOnline()) {
                    player.processed = true;
                    player.process();
                }
            } catch(Exception t) {
                t.printStackTrace();
            }
        }
        for (OwnedObject object : ownedObjects.values()) {
            try {
                object.tick();
            } catch (Exception t) {
                t.printStackTrace();
            }
        }
    }

    /**
     * Update - Things like updating local players, local npcs, writing packets to the channel, etc.
     */

    private static void update() {
        for(NPC npc : npcs) {
            try {
                npc.resetUpdates();
                npc.processed = false;
            } catch(Exception t) {
                t.printStackTrace();
            }
        }
        for(Player player : players) {
            try {
                if(player.isOnline()) {

                    if (player.confClientType > ANDROID) {
//                        player.getUpdater().processMobile();
//                        player.getNpcUpdater().processMobile();
                    } else {
                        player.getUpdater().process();
                        player.getNpcUpdater().process();
                    }
                    TargetOverlay.process(player);
                    player.sendVarps();
                }
            } catch(Exception t) {
                t.printStackTrace();
            }
        }
        for(Player player : players) {
            try {
                if(player.isOnline())
                    player.resetUpdates();
                player.getChannel().flush();
                player.processed = false;
            } catch(Exception t) {
                t.printStackTrace();
            }
        }
    }

}
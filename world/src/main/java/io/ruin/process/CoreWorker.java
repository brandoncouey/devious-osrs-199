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
        for(Stage stage : Stage.values()) {
            processStage = stage;
            processStage.runnable.run();
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
        npcs.resetCount();
        for(NPC npc : npcs.entityList) {
            if(npc != null)
                npcs.index(npc);
        }
        /**
         * Player indexing
         */
        players.resetCount();
        for(Player player : players.entityList) {
            if(player != null)
                players.index(player);
        }
        /*
         * Scrambling
         */
        if(--scrambleTicks <= 0) {
            scrambleTicks = Random.get(40, 60);
            npcs.scramble();
            players.scramble();
        }
    }

    /**
     * Logic - Things like packet handling, combat, movement, etc.
     */
    private static void logic() {
        GameEventProcessor.pulse();
        EventManager.getInstance().checkEvents();
        for(NPC npc : npcs) {
            try {
                npc.processed = true;
                npc.process();
            } catch(Throwable t) {
                Server.logError("", t);
            }
        }
        for(Player player : players) {
            try {
                player.checkLogout();
            } catch(Throwable t) {
                Server.logError("", t);
            }
        }
        for(Player player : players) {
            try {
                if(player.isOnline()) {
                    player.processed = true;
                    player.process();
                }
            } catch(Throwable t) {
                Server.logError("", t);
            }
        }
        for (OwnedObject object : ownedObjects.values()) {
            try {
                object.tick();
            } catch (Throwable t) {
                Server.logError("", t);
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
            } catch(Throwable t) {
                Server.logError("", t);
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
            } catch(Throwable t) {
                Server.logError("", t);
            }
        }
        for(Player player : players) {
            try {
                if(player.isOnline())
                    player.resetUpdates();
                player.getChannel().flush();
                player.processed = false;
            } catch(Throwable t) {
                Server.logError("", t);
            }
        }
    }

}
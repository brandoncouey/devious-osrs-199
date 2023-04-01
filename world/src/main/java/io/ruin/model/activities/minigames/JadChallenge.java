package io.ruin.model.activities.minigames;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.activities.ActivityTimer;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JadChallenge {

    static {
        List<Position> spawns = Arrays.asList(
                new Position(2259, 5353, 0),
                new Position(2279, 5352, 0),
                new Position(2260, 5346, 0),
                new Position(2279, 5346, 0),
                new Position(2272, 5340, 0),
                new Position(2261, 5339, 0),
                new Position(2260, 5332, 0),
                new Position(2271, 5331, 0),
                new Position(2279, 5332, 0)
        );
        Collections.shuffle(spawns); //let's not get predictable ;)
        SPAWN_POSITIONS = spawns.toArray(new Position[0]);
    }

    private static final Position EXIT = new Position(2542, 5111, 0);

    private static final Position[] SPAWN_POSITIONS;

    private static final int TZ_TOK_JAD = 7704, HOST = 2180;

    private Player player;

    @Expose
    private int wave;

    private ActivityTimer timer;

    private DynamicMap map;

    private int spawnOffset;

    private boolean preparingWave;

    private JadChallenge(Player player, int wave) {
        this.player = player;
        this.wave = wave;
    }

    private void start() {
        player.sixjad = this;
        player.deathEndListener = (DeathListener.Simple) this::handleDeath;
        map = new DynamicMap().build(9043, 1);
        spawnOffset = Random.get(SPAWN_POSITIONS.length - 1);
        player.getMovement().teleport(map.convertX(2270), map.convertY(5344), 0);
        map.assignListener(player).onExit((p, logout) -> stop(false));
        timer = new ActivityTimer();
        prepareWave();
    }

    public static JadChallenge getInstance(NPC npc) {
        return npc.get("CHALLENGE");
    }

    public DynamicMap getMap() {
        return map;
    }

    private void stop(boolean logout) {
        if (logout) {
            player.getMovement().teleport(EXIT);
            player.sixjad = null;
            player = null;
            return;
        }
        if (wave == 6) {
            Broadcast.WORLD.sendNews(Color.ORANGE.wrap(player.getName() + " Has just completed the six jad challenge!"));
            player.getInventory().add(6529, Random.get(250, 750));
        }
        player.sixjadBestTime = timer.stop(player, player.sixjadBestTime);
        player.getInventory().add(6529, Random.get(100, 500));
        player.deathEndListener = null;
        player.sixjad = null;
        player = null;
        map.destroy();
        map = null;
    }

    private void prepareWave() {
        if (preparingWave)
            return;
        preparingWave = true;
        wave++;
        map.addEvent(event -> {
            player.sendMessage(Color.RED.wrap("Wave: " + wave));
            event.delay(10);
            beginWave();
            preparingWave = false;
        });
    }

    private void beginWave() {
        int wave = this.wave;
        player.sendMessage("<col=ef1020>Wave: " + wave);
        spawn(wave);
        if (wave == 6) {
            player.sendMessage("<col=ef1020>Final Challenge!");
            player.dialogue(new NPCDialogue(HOST, "Look out, here comes JalTok-Jad!").animate(615));
        }
    }

    private void handleDeath() {
        player.getMovement().teleport(EXIT);
        player.sendMessage("You have been defeated!");
    }

    public int getWave() {
        return wave;
    }

    public Player getPlayer() {
        return player;
    }

    public static void join(Player player, int wave) {
        if (player.sixjad == null) //probably will always be true, but just to be safe?
            new JadChallenge(player, wave - 1).start();
    }

    private static void exit(Player player) {
        //map listener will stop your session the second you leave the map.
        player.getMovement().teleport(EXIT);
    }

    static {
        NPCAction.register(10622, "talk-to", (p, npc) -> {
            p.dialogue(new OptionsDialogue("JalYt-Mej-Xo-" + p.getName() + ". The challenge awaits, if you're brave enough.",
                    new Option("Of course I'm brave enough.", () -> open(p)),
                    new Option("I'll pass.", p::closeDialogue)
            ));
        });

        NPCAction.register(10622, "challenge", (p, npc) -> open(p));

        LoginListener.register(p -> {
            if (p.sixjad != null) {
                p.sixjad.player = p;
                p.sixjad.stop(true);
            }
        });

        ObjectAction.register(30283, "exit", (player, obj) -> player.dialogue(
                new OptionsDialogue("Really leave?",
                        new Option("Yes - really leave.", () -> exit(player)),
                        new Option("No, I'll stay.", player::closeDialogue)
                )
        ));

        ObjectAction.register(30283, "quick-exit", (player, obj) -> exit(player));
    }

    public static void open(Player c) {
        if (!c.Challenge2) {
            OptionScroll.open(c, "<col=8B0000>Which challenge would you like?",
                    new Option("Tzhaar-Ket_rak's First Challenge", () -> join(c, 1)),
                    new Option("<str>Tzhaar-Ket_rak's Second Challenge</str>"),
                    new Option("<str>Tzhaar-Ket_rak's Third Challenge </str>"),
                    new Option("<str>Tzhaar-Ket_rak's Fourth Challenge</str>"),
                    new Option("<str>Tzhaar-Ket_rak's Fifth Challenge </str>"),
                    new Option("<str>Tzhaar-Ket_rak's Sixth Challenge </str>")
            );
        } else if (!c.Challenge3) {
            OptionScroll.open(c, "<col=8B0000>Which challenge would you like?",
                    new Option("Tzhaar-Ket_rak's First Challenge", () -> join(c, 1)),
                    new Option("Tzhaar-Ket_rak's Second Challenge", () -> join(c, 2)),
                    new Option("<str>Tzhaar-Ket_rak's Third Challenge </str>"),
                    new Option("<str>Tzhaar-Ket_rak's Fourth Challenge</str>"),
                    new Option("<str>Tzhaar-Ket_rak's Fifth Challenge </str>"),
                    new Option("<str>Tzhaar-Ket_rak's Sixth Challenge </str>")
            );
        } else if (!c.Challenge4) {
            OptionScroll.open(c, "<col=8B0000>Which challenge would you like?",
                    new Option("Tzhaar-Ket_rak's First Challenge", () -> join(c, 1)),
                    new Option("Tzhaar-Ket_rak's Second Challenge", () -> join(c, 2)),
                    new Option("Tzhaar-Ket_rak's Third Challenge", () -> join(c, 3)),
                    new Option("<str>Tzhaar-Ket_rak's Fourth Challenge</str>"),
                    new Option("<str>Tzhaar-Ket_rak's Fifth Challenge </str>"),
                    new Option("<str>Tzhaar-Ket_rak's Sixth Challenge </str>")
            );
        } else if (!c.Challenge5) {
            OptionScroll.open(c, "<col=8B0000>Which challenge would you like?",
                    new Option("Tzhaar-Ket_rak's First Challenge", () -> join(c, 1)),
                    new Option("Tzhaar-Ket_rak's Second Challenge", () -> join(c, 2)),
                    new Option("Tzhaar-Ket_rak's Third Challenge", () -> join(c, 3)),
                    new Option("Tzhaar-Ket_rak's Fourth Challenge", () -> join(c, 4)),
                    new Option("<str>Tzhaar-Ket_rak's Fifth Challenge </str>"),
                    new Option("<str>Tzhaar-Ket_rak's Sixth Challenge </str>")
            );
        } else if (!c.Challenge6) {
            OptionScroll.open(c, "<col=8B0000>Which challenge would you like?",
                    new Option("Tzhaar-Ket_rak's First Challenge", () -> join(c, 1)),
                    new Option("Tzhaar-Ket_rak's Second Challenge", () -> join(c, 2)),
                    new Option("Tzhaar-Ket_rak's Third Challenge", () -> join(c, 3)),
                    new Option("Tzhaar-Ket_rak's Fourth Challenge", () -> join(c, 4)),
                    new Option("Tzhaar-Ket_rak's Fifth Challenge", () -> join(c, 5)),
                    new Option("<str>Tzhaar-Ket_rak's Sixth Challenge </str>")
            );
        } else {
            OptionScroll.open(c, "<col=8B0000>Which challenge would you like?",
                    new Option("Tzhaar-Ket_rak's First Challenge", () -> join(c, 1)),
                    new Option("Tzhaar-Ket_rak's Second Challenge", () -> join(c, 2)),
                    new Option("Tzhaar-Ket_rak's Third Challenge", () -> join(c, 3)),
                    new Option("Tzhaar-Ket_rak's Fourth Challenge", () -> join(c, 4)),
                    new Option("Tzhaar-Ket_rak's Fifth Challenge", () -> join(c, 5)),
                    new Option("Tzhaar-Ket_rak's Sixth Challenge", () -> join(c, 6))
            );
        }
    }

    public static int kc = 0;

    private void handleDeath(NPC npc) {
        if (player.getCombat().isDead()) {
            //nothing should happen cause you failed
            return;
        }
        npc.remove();
        kc++;

        if (kc == wave) {
            if (wave == 1) {
                player.Challenge2 = true;
                player.getMovement().teleport(EXIT);
                return;
            }
            if (wave == 2) {
                player.Challenge3 = true;
                player.getMovement().teleport(EXIT);
                return;
            }
            if (wave == 3) {
                player.Challenge4 = true;
                player.getMovement().teleport(EXIT);
                return;
            }
            if (wave == 4) {
                player.Challenge5 = true;
                player.getMovement().teleport(EXIT);
                return;
            }
            if (wave == 5) {
                player.Challenge6 = true;
                player.getMovement().teleport(EXIT);
                return;
            }
            if (wave == 6) {
                player.getMovement().teleport(EXIT);
            }
        }
    }

    private int finalI = 4;

    private void spawn(int amount) {
        int i;
        for (i = 0; i < amount; i++) {
            World.startEvent(e -> {
                Position pos = SPAWN_POSITIONS[spawnOffset];
                if (++spawnOffset >= SPAWN_POSITIONS.length)
                    spawnOffset = 0;
                NPC npc = new NPC(JadChallenge.TZ_TOK_JAD).spawn(map.convertX(pos.getX()), map.convertY(pos.getY()), 0);
                npc.getCombat().updateLastAttack(finalI);
                registerNPC(npc);
                finalI += 3;
            });
        }
    }

    private void registerNPC(NPC npc) {
        npc.set("CHALLENGE", this);
        npc.getCombat().setAllowRespawn(false);
        npc.deathEndListener = (DeathListener.Simple) () -> handleDeath(npc);
        npc.targetPlayer(player, false);
        npc.attackTargetPlayer();
        map.addNpc(npc);
    }
}

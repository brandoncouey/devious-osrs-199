package io.ruin.model.skills.events;

import io.ruin.Server;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.cache.NpcID;
import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.mining.Pickaxe;
import io.ruin.model.stat.StatType;
import io.ruin.services.discord.DiscordConnection;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ShootingStars {

    private static final Projectile LAVA_PROJECTILE = new Projectile(1839, 150, 0, 0, 100, 0, 45, 0);

    private static final boolean DISABLED = false;

    private static long spawnTicks = 0;
    private static final int STAR_START = 41223;
    private static final int STAR_PROGRESS_1 = 41224;
    private static final int STAR_PROGRESS_2 = 41225;
    private static final int STAR_PROGRESS_3 = 41226;
    private static final int STAR_PROGRESS_4 = 41227;
    private static final int STAR_PROGRESS_5 = 41228;
    private static final int STAR_FINISH = 41229;
    private static int METEORITE_REMAINING = 1000;
    private static ShootingStars ACTIVE;
    private static long timeRemaining;
    public static GameObject rock;
    public static NPC maledictus;
    public static ArrayList<Player> players = new ArrayList<>(500);
    public static int STAR_CURRENCY = 25527;

    private static final ShootingStars[] SPAWNS = {
            new ShootingStars(new Position(2458, 3092, 0)),
            new ShootingStars(new Position(2728, 3478, 0))
    };

    public static String getLocation() {
        if (ACTIVE.starSpawn.equals(2728, 3478)) {
            return "South of Camelot Bank!";

        } else if (ACTIVE.starSpawn.equals(2458, 3092)) {
            return "Outside of Castlewars!";
        }
        return "Unknown";
    }

    /**
     * Separator
     */

    private final Position starSpawn;

    public ShootingStars(Position starSpawn) {
        this.starSpawn = starSpawn;
    }

    static {
        /*
         * Event
         */
        if (!DISABLED) {
            /* event runs every 45 minutes */
            World.startEvent(e -> {
                while (true) {
                    spawnTicks = Server.getEnd(240 * 100); // 4 Hours
                    ShootingStars next = Random.get(SPAWNS);
                    if (next == ACTIVE) {
                        e.delay(1);
                        continue;
                    }
                    ACTIVE = next;
                   // String revsMessage = "A superior revenant has been awoken in the north of the Revenant Cave";
                    String eventMessage = "There's been a sighting of a star around " + getLocation() + " in World: " + World.id + "!";
                    broadcastEvent(eventMessage);
                    addStar();
                    /* stop event after 15 minutes */
                    timeRemaining = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(45);
                    e.delay(45 * 100);
                    removeStar(false);
                    /*delay 90 minutes before starting event */
                    e.delay(90 * 100);
                }
            });

            /*
             * Boulder
             */
            ObjectAction.register(STAR_PROGRESS_1, 1, (player, obj) -> mine(player));
            ObjectAction.register(STAR_PROGRESS_1, "Prospect", (player, obj) -> inspect(player));

            ObjectAction.register(STAR_START, 1, (player, obj) -> mine(player));
            ObjectAction.register(STAR_START, "Prospect", (player, obj) -> inspect(player));

            ObjectAction.register(STAR_PROGRESS_2, 1, (player, obj) -> mine(player));
            ObjectAction.register(STAR_PROGRESS_2, "Prospect", (player, obj) -> inspect(player));

            ObjectAction.register(STAR_PROGRESS_3, 1, (player, obj) -> mine(player));
            ObjectAction.register(STAR_PROGRESS_3, "Prospect", (player, obj) -> inspect(player));

            ObjectAction.register(STAR_PROGRESS_4, 1, (player, obj) -> mine(player));
            ObjectAction.register(STAR_PROGRESS_4, "Prospect", (player, obj) -> inspect(player));

            ObjectAction.register(STAR_PROGRESS_5, 1, (player, obj) -> mine(player));
            ObjectAction.register(STAR_PROGRESS_5, "Prospect", (player, obj) -> inspect(player));

            ObjectAction.register(STAR_FINISH, 1, (player, obj) -> mine(player));
            ObjectAction.register(STAR_FINISH, "Prospect", (player, obj) -> inspect(player));

        }
    }

    private static void broadcastEvent(String eventMessage) {
        for (Player p : World.players) {
            p.getPacketSender().sendMessage(eventMessage, "", 14);
        }
    }

    private static void addStar() {
        rock = GameObject.spawn(STAR_START, ACTIVE.starSpawn.getX(), ACTIVE.starSpawn.getY(), 0, 10, 0);
        METEORITE_REMAINING = 2200;
    }

    public static void addStar(int x, int y, int z) {
        rock = GameObject.spawn(STAR_START, x, y, z, 10, 0);
        METEORITE_REMAINING = 2200;
    }


    private static void attackingLava(Player player, Position position) {
        Position source = ACTIVE.starSpawn;
        Position pos = player.getPosition().copy();
        World.startEvent(event -> {
            event.delay(1);
            for (Player p : player.localPlayers()) {
                int delay = LAVA_PROJECTILE.send(source.getX(), source.getY(), pos.getX(), pos.getY());
                if (p.getPosition().equals(position)) {
                    p.sendFilteredMessage(Color.DARK_RED.wrap("You hit a vein of molten lava within the meteorite, move!"));
                    rock.graphics(delay);
                    event.delay(delay);
                    World.sendGraphics(1840, 0, delay, pos);
                    if (p.getPosition().equals(position)) {
                        p.hit(new Hit().randDamage(10, 30));
                        p.resetAnimation();
                        p.sendFilteredMessage(Color.DARK_RED.wrap("You're badly burned as the molten lava hits your skin."));
                    } else {
                        p.sendFilteredMessage(Color.DARK_RED.wrap("You move just in time to avoid the heat from the lava!"));
                    }
                }
            }
        });
    }

    public static void removeStar(boolean success) {
        if (rock != null) {
            rock.setId(-1);
            rock = null;
            if (success) {
                String successMessage = "The Shooting star has been completely mined!";
                broadcastEvent(successMessage);
            } else {
                String failedMessage = "The dwarfs decided to mine the Shooting star as players didn't!";
                broadcastEvent(failedMessage);
            }
        }
    }

    private static void removeShards(int amt) {
        METEORITE_REMAINING -= amt;
        if (METEORITE_REMAINING <= 0)
            METEORITE_REMAINING = 0;
    }

    private static String boulderColor() {
        if (METEORITE_REMAINING > 300)
            return "<col=00FF00>";
        else if (METEORITE_REMAINING > 100)
            return "<col=ffff00>";
        return "<col=FF0000>";
    }


    private static void inspect(Player player) {
        player.dialogue(new MessageDialogue("The rock looks like it has " + METEORITE_REMAINING + " x fragments in it."));
    }

    private static void mine(Player player) {
        player.startEvent(event -> {
            Pickaxe pickaxe = Pickaxe.find(player);
            if (pickaxe == null) {
                player.sendMessage("You do not have an pick-axe which you have the mining level to use.");
                player.privateSound(2277);
                return;
            }
            if (player.getInventory().isFull() && !player.getInventory().hasId(STAR_CURRENCY)) {
                player.dialogue(new MessageDialogue("Your inventory is too full to do this."));
                return;
            }
            player.animate(pickaxe.crystalAnimationID);
            player.sendFilteredMessage("You swing your pickaxe at the rock.");
            event.delay(Random.get(3, 8));
            while (true) {
                if (player.getInventory().isFull() && !player.getInventory().hasId(STAR_CURRENCY)) {
                    player.dialogue(new MessageDialogue("You can't hold anymore " + ItemDef.get(STAR_CURRENCY).name.toLowerCase() + " in your inventory."));
                    return;
                }
                int random = Random.get(1, 3);
                player.animate(pickaxe.crystalAnimationID);
                player.getInventory().add(STAR_CURRENCY, random);
                player.getStats().addXp(StatType.Mining, 30, true);
                if (Random.rollDie(50, 1)) {
                    player.getInventory().addOrDrop(6828, 1);
                    player.sendMessage("You've discovered a Skilling box. It's been added to your inventory.");
                }
                removeShards(random);
                player.sendFilteredMessage("You mine " + random + " " + ItemDef.get(STAR_CURRENCY).name.toLowerCase() + ".");
                if (METEORITE_REMAINING > 500 && METEORITE_REMAINING <= 700) {
                    rock.setId(STAR_PROGRESS_1);
                }
                if (METEORITE_REMAINING >= 400 && METEORITE_REMAINING <= 500) {
                    rock.setId(STAR_PROGRESS_2);
                }
                if (METEORITE_REMAINING >= 300 && METEORITE_REMAINING <= 400) {
                    rock.setId(STAR_PROGRESS_3);
                }
                if (METEORITE_REMAINING >= 200 && METEORITE_REMAINING <= 300) {
                    rock.setId(STAR_PROGRESS_4);
                }
                if (METEORITE_REMAINING >= 100 && METEORITE_REMAINING <= 200) {
                    rock.setId(STAR_PROGRESS_5);
                }
                if (METEORITE_REMAINING >= 1 && METEORITE_REMAINING <= 100) {
                    rock.setId(STAR_FINISH);
                }
                if (METEORITE_REMAINING <= 0) {
                    removeStar(true);
                    player.resetAnimation();
                    break;
                }
                if (rock == null) {
                    player.resetAnimation();
                    break;
                }
                event.delay(Random.get(3, 8));
            }
        });
    }

    private static String timeRemaining(long ms) {
        long hours = TimeUnit.MILLISECONDS.toHours(ms);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(ms);
        if (ms < 0)
            return "None!";
        return String.format(
                "%02d:%02d",
                minutes - TimeUnit.HOURS.toMinutes(hours),
                TimeUnit.MILLISECONDS.toSeconds(ms) - TimeUnit.MINUTES.toSeconds(minutes)
        );
    }

}
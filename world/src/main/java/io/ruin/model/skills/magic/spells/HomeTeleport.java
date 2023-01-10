package io.ruin.model.skills.magic.spells;

import io.ruin.model.World;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.teleports.TeleportDestination;
import io.ruin.model.map.Position;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class HomeTeleport extends Spell {

    public static final HomeTeleport MODERN = homeTeleport();
    public static final HomeTeleport ANCIENT = homeTeleport();
    public static final HomeTeleport LUNAR = homeTeleport();
    public static final HomeTeleport ARCEUUS = homeTeleport();

    private static HomeTeleport homeTeleport() {
        return new HomeTeleport(p -> {
            TeleportDestination dest = p.homeTeleportDestination;
            if (p.teleCooldown.isDelayed()) {
                int delay = p.teleCooldown.remaining();
                if (delay >= 100) {
                    int minutes = delay / 100;
                    p.sendMessage("Your teleport is on cooldown. Try again in " + minutes + " minutes.");
                } else {
                 //   int seconds = p.teleCooldown.remaining() / 10 * 6;
                    p.sendMessage("Your teleport is on cooldown. Try again in 6 seconds.");
                }
            } else if (World.isPVPWorld()) {
                    ModernTeleport.teleport(p, dest == null ? World.HOMEPVP : dest.position);
                } else {
                    ModernTeleport.teleport(p, dest == null ? World.HOME : dest.position);
            }
        });
    }

    private static final List<HomeTeleportOverride> OVERRIDES = new LinkedList<>();

    private HomeTeleport(Consumer<Player> consumer) {
        clickAction = (p, i) -> {
            if (p.wildernessLevel > 20) {
                p.sendMessage("You can't cast this spell over level 20 of the Wilderness");
                return;
            }
            Position override = getHomeTeleportOverride(p);
            if (override != null) {
                ModernTeleport.teleport(p, override.getX(), override.getY(), override.getZ());
            } else {
                if (p.edgeHome) {
                    ModernTeleport.teleport(p, World.EDGEHOME);
                    return;
                } else {
                    if (p.dzHome) {
                        ModernTeleport.teleport(p, World.DZ);
                        return;
                    }
                }
                consumer.accept(p);
            }
        };
    }

    private static class HomeTeleportOverride {
        Predicate<Player> condition;
        Position destination;

        public HomeTeleportOverride(Predicate<Player> condition, Position destination) {
            this.condition = condition;
            this.destination = destination;
        }
    }

    public static void registerHomeTeleportOverride(Predicate<Player> condition, Position destination) {
        OVERRIDES.add(new HomeTeleportOverride(condition, destination));
    }

    public static Position getHomeTeleportOverride(Player player) {
        for (HomeTeleportOverride teleportOverride : OVERRIDES) {
            if (teleportOverride.condition.test(player))
                return teleportOverride.destination;
        }
        return null;
    }
}
package io.ruin.model.activities.raids.tob.dungeon.boss.bloat;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;

public class BloatCombat extends NPCCombat {

    private static final int FLIES_GFX = 1568;

    @Override
    public void init() {
        npc.setMaxHp((int) ((double) npc.getMaxHp() * 0.75));
        flesh(0, 2500);
        sleepyTime();
        npc.hitListener = new HitListener().preDefend(hit -> {
            if (sleepytime) {
                hit.boostDefence(0.75);
            }
        });
        npc.deathStartListener = (DeathListener.Simple) () -> {
            npc.animate(8085);
        };
    }

    @Override
    public void follow() {

    }

    @Override
    public boolean attack() {
        return false;
    }

    public boolean sleepytime = false;

    public void sleepyTime() {
        npc.startEvent(event -> {
//            System.out.println("[BLOAT WALKING]"+count);
            event.delay(2);
            sleepyTime();
            if (!sleepytime) {
                count--;
                if (count == 0) {
                    sleepytime = true;
//                    System.out.println("[BLOAT] Sleepy time");
                    npc.animate(8082);
                    while (npc.isAnimating()) {
                        npc.lock();
                        event.delay(13);
                        npc.unlock();
                    }
                }
            } else if (sleepytime && ++count < 10) {
//                System.out.println("[BLOAT SLEEPING]"+count);
                event.delay(1);
            } else if (sleepytime && count == 10) {
                count = 30;
                sleepytime = false;
                flesh(0, 3000);
//                System.out.println("[BLOAT WAKEY WAKEY]"+count);
                npc.localPlayers().forEach(plr -> {
                    if (plr.getPosition().isWithinDistance(npc.getPosition(), 3)) {
                        plr.hit(new Hit().randDamage(5, 25));
                    }
                });
            }


        });
    }

    public Position getAbsolute(int localX, int localY) {
        return new Position(npc.getPosition().getRegion().baseX + localX, npc.getPosition().getRegion().baseY + localY, npc.getPosition().getZ());
    }

    public static int count = 30;

    public void flesh(int delay, int duration) {
        npc.addEvent(event -> {
            event.setCancelCondition(this::isDead);
            event.delay(delay);
            int ticks = 0;
            while (ticks < duration && !sleepytime) {
                for (int i = 0; i < 8; i++) {
                    Position position;
                    position = getAbsolute(Random.get(23, 40), Random.get(23, 40));

                    if (position.getTile().isTileFreeCheckDecor() && position.inBounds(npc.getPosition().getRegion().bounds)) {

                        if (Random.get() <= 0.5)
                            World.sendGraphics(1572, 0, 5, position);
                        else
                            World.sendGraphics(1573, 0, 5, position);

                        npc.localPlayers().forEach(p -> {
                            if (p.getPosition().equals(position)) {
                                p.hit(new Hit(npc).randDamage(30, 60));
                            }
                        });
                        // Flies
                        damagePlayersInLos();
                    }
                }
                ticks += 2;
                event.delay(2);
            }
        });
    }

    private void damagePlayersInLos() {
        Bounds psouth = new Bounds(npc.getPosition().getRegion().baseX + 23, npc.getPosition().getRegion().baseY + 23, npc.getPosition().getRegion().baseX + 39, npc.getPosition().getRegion().baseY + 28, 0);
        Bounds pnorth = new Bounds(npc.getPosition().getRegion().baseX + 35, npc.getPosition().getRegion().baseY + 23, npc.getPosition().getRegion().baseX + 35, npc.getPosition().getRegion().baseY + 40, 0);
        // ^^ DONE South East Corner -> Span to North East and South West Corner

        Bounds psouth1 = new Bounds(npc.getPosition().getRegion().baseX + 24, npc.getPosition().getRegion().baseY + 24, npc.getPosition().getRegion().baseX + 28, npc.getPosition().getRegion().baseY + 39, 0);
        Bounds pnorth1 = new Bounds(npc.getPosition().getRegion().baseX + 24, npc.getPosition().getRegion().baseY + 35, npc.getPosition().getRegion().baseX + 39, npc.getPosition().getRegion().baseY + 39, 0);
        // ^^ DONE North West Corner -> Span to North East Corner and South West corner

        Bounds psouth2 = new Bounds(npc.getPosition().getRegion().baseX + 24, npc.getPosition().getRegion().baseY + 24, npc.getPosition().getRegion().baseX + 28, npc.getPosition().getRegion().baseY + 39, 0);
        Bounds pnorth2 = new Bounds(npc.getPosition().getRegion().baseX + 24, npc.getPosition().getRegion().baseY + 24, npc.getPosition().getRegion().baseX + 39, npc.getPosition().getRegion().baseY + 28, 0);
        // ^^ DONE South West Corner -> Span to North West Corner and South East Corner

        Bounds psouth3 = new Bounds(npc.getPosition().getRegion().baseX + 35, npc.getPosition().getRegion().baseY + 24, npc.getPosition().getRegion().baseX + 39, npc.getPosition().getRegion().baseY + 39, 0);
        Bounds pnorth3 = new Bounds(npc.getPosition().getRegion().baseX + 24, npc.getPosition().getRegion().baseY + 35, npc.getPosition().getRegion().baseX + 39, npc.getPosition().getRegion().baseY + 39, 0);
        // ^^ DONE North East Corner -> Span to South East Corner and North West Corner
        npc.localPlayers().forEach(plr -> {
            if (npc.getPosition().inBounds(psouth) && plr.getPosition().inBounds(psouth) || npc.getPosition().inBounds(pnorth) && plr.getPosition().inBounds(pnorth)) {
                plr.graphics(FLIES_GFX);
                plr.localPlayers().forEach(player -> {
                    if (player.getPosition().isWithinDistance(plr.getPosition(), 3)) {
                        player.graphics(FLIES_GFX);
                    }
                });
            } else if (npc.getPosition().inBounds(psouth1) && plr.getPosition().inBounds(psouth1) || npc.getPosition().inBounds(pnorth1) && plr.getPosition().inBounds(pnorth1)) {
                plr.graphics(FLIES_GFX);
                plr.localPlayers().forEach(player -> {
                    if (player.getPosition().isWithinDistance(plr.getPosition(), 3)) {
                        player.graphics(FLIES_GFX);
                    }
                });
            } else if (npc.getPosition().inBounds(psouth2) && plr.getPosition().inBounds(psouth2) || npc.getPosition().inBounds(pnorth2) && plr.getPosition().inBounds(pnorth2)) {
                plr.graphics(FLIES_GFX);
                plr.localPlayers().forEach(player -> {
                    if (player.getPosition().isWithinDistance(plr.getPosition(), 3)) {
                        player.graphics(FLIES_GFX);
                    }
                });
            } else if (npc.getPosition().inBounds(psouth3) && plr.getPosition().inBounds(psouth3) || npc.getPosition().inBounds(pnorth3) && plr.getPosition().inBounds(pnorth3)) {
                plr.graphics(FLIES_GFX);
                plr.localPlayers().forEach(player -> {
                    if (player.getPosition().isWithinDistance(plr.getPosition(), 3)) {
                        player.graphics(FLIES_GFX);
                    }
                });
            }
        });

    }

}

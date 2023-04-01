package io.ruin.model.activities.raids.tob.dungeon.boss.bloat;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.npc.actions.edgeville.Nurse;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.utility.Utils;

public class BloatCombat extends NPCCombat {

    /**
     * 4 Mechanics
     * 1. STOP
     * 2. FLIES
     * 3. FALLING FLESH
     * 4. Walk/Run
     */

    private static final int FLIES_GFX = 1568;

    public static int count = 30;

    public boolean frozen = false;

    public boolean flesh = false;

    public int frozenTicks = 0;

    public int fleshTicks = 0;

    public int unfrozenTicks = 0;

    @Override
    public void init() {
        npc.setMaxHp((int) ((double) npc.getMaxHp() * 0.75));
        npc.hitListener = new HitListener().preDefend(hit -> {
            if (frozen) {
                hit.boostDefence(0.75);
            }
            if (npc.getHp() <= 0) {
                for (Player p : npc.getPosition().getRegion().players) {
                    Nurse.restoreOnly(p);
                }
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

    @Override
    public void process() {
        //Bloat is not frozen
        if (!frozen) {
            frozenTicks = 0;
            unfrozenTicks++;
            damagePlayersInLos();

            //Bloat is doing flesh
            if (flesh) {
                if (fleshTicks % 2 == 0) {
                    flesh();
                }
                if (fleshTicks > 14) {
                    flesh = false;
                    fleshTicks = 0;
                    npc.startEvent(event -> {
                        event.delay(2);
                        frozen();
                        unfrozenTicks = 0;
                    });
                }
                fleshTicks++;

                //If we are not doing flesh, we will attempt to 'freeze'
            } else {
                //TODO handle random freezing
                if (unfrozenTicks > 47) {
                    frozen();
                    unfrozenTicks = 0;
                }
            }
            //TODO pathfinding walking in rotation
           // DumbRoute.
            //Bloat is Frozen
        } else {
            unfrozenTicks = 0;
            frozenTicks++;
            //Unfreeze
            if (frozenTicks > 24) {
                unfreeze();
                frozenTicks = 0;
            }
        }
    }

    public void frozen() {
        frozen = true;
        npc.lock();
        npc.getMovement().reset();
        npc.animate(8082);
    }

    public void unfreeze() {
        frozen = false;
        npc.startEvent(event -> {
            event.delay(3);
            npc.localPlayers().forEach(plr -> {
                if (plr.getPosition().isWithinDistance(npc.getPosition(), 3)) {
                    int dmg = plr.hit(new Hit().randDamage(40, 80));
                }
            });
            event.delay(6);
            npc.unlock();
            event.delay(3);
            if (npc.getHp() <= Utils.getPercent(90, npc.getMaxHp())) {
                flesh = true;
            }
        });
    }

    public void flesh() {
        npc.addEvent(event -> {
            event.delay(1);
            event.setCancelCondition(this::isDead);
            for (int i = 0; i < 8; i ++) {
                Position position;
                position = getAbsolute(Random.get(23, 40),Random.get(23, 40));

                if (position.getTile().isTileFreeCheckDecor() && position.inBounds(npc.getPosition().getRegion().bounds)) {

                    if(Random.get() <= 0.5)
                        World.sendGraphics(1572, 0, 5, position);
                    else
                        World.sendGraphics(1573, 0, 5, position);

                    event.delay(2);

                    npc.localPlayers().forEach(p -> {
                        if (p.getPosition().equals(position)) {
                            p.hit(new Hit(npc).randDamage(20, 50));
                        }
                    });
                }
            }

        });
    }

    private void damagePlayersInLos() {
        if (frozen) return;
        Bounds psouth  = new Bounds(npc.getPosition().getRegion().baseX + 23,npc.getPosition().getRegion().baseY + 23,npc.getPosition().getRegion().baseX + 39,npc.getPosition().getRegion().baseY + 28,0);
        Bounds pnorth  = new Bounds(npc.getPosition().getRegion().baseX + 35,npc.getPosition().getRegion().baseY + 23,npc.getPosition().getRegion().baseX + 35,npc.getPosition().getRegion().baseY + 40,0);
        // ^^ DONE South East Corner -> Span to North East and South West Corner

        Bounds psouth1 = new Bounds(npc.getPosition().getRegion().baseX + 24,npc.getPosition().getRegion().baseY + 24,npc.getPosition().getRegion().baseX + 28,npc.getPosition().getRegion().baseY + 39,0);
        Bounds pnorth1 = new Bounds(npc.getPosition().getRegion().baseX + 24,npc.getPosition().getRegion().baseY + 35,npc.getPosition().getRegion().baseX + 39,npc.getPosition().getRegion().baseY + 39,0);
        // ^^ DONE North West Corner -> Span to North East Corner and South West corner

        Bounds psouth2 = new Bounds(npc.getPosition().getRegion().baseX + 24,npc.getPosition().getRegion().baseY + 24,npc.getPosition().getRegion().baseX + 28,npc.getPosition().getRegion().baseY + 39,0);
        Bounds pnorth2 = new Bounds(npc.getPosition().getRegion().baseX + 24,npc.getPosition().getRegion().baseY + 24,npc.getPosition().getRegion().baseX + 39,npc.getPosition().getRegion().baseY + 28,0);
        // ^^ DONE South West Corner -> Span to North West Corner and South East Corner

        Bounds psouth3 = new Bounds(npc.getPosition().getRegion().baseX + 35,npc.getPosition().getRegion().baseY + 24,npc.getPosition().getRegion().baseX + 39,npc.getPosition().getRegion().baseY + 39,0);
        Bounds pnorth3 = new Bounds(npc.getPosition().getRegion().baseX + 24,npc.getPosition().getRegion().baseY + 35,npc.getPosition().getRegion().baseX + 39,npc.getPosition().getRegion().baseY + 39,0);
        // ^^ DONE North East Corner -> Span to South East Corner and North West Corner
        npc.localPlayers().forEach(plr -> {
            if (npc.getPosition().inBounds(psouth) && plr.getPosition().inBounds(psouth) || npc.getPosition().inBounds(pnorth) && plr.getPosition().inBounds(pnorth)) {
                plr.graphics(FLIES_GFX);
                plr.hit(new Hit().fixedDamage(plr.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES) ? (Utils.random(10, 20) / 2) : Utils.random(10, 20)));
                plr.localPlayers().forEach(player -> {
                    if (player != plr) {
                        if (player.getPosition().isWithinDistance(plr.getPosition(), 3)) {
                            player.graphics(FLIES_GFX);
                            player.hit(new Hit().fixedDamage(plr.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES) ? (Utils.random(10, 20) / 2) : Utils.random(10, 20)));
                        }
                    }
                });
            } else if (npc.getPosition().inBounds(psouth1) && plr.getPosition().inBounds(psouth1) || npc.getPosition().inBounds(pnorth1) && plr.getPosition().inBounds(pnorth1)) {
                plr.graphics(FLIES_GFX);
                plr.hit(new Hit().fixedDamage(plr.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES) ? (Utils.random(10, 20) / 2) : Utils.random(10, 20)));
                plr.localPlayers().forEach(player -> {
                    if (player != plr) {
                        if (player.getPosition().isWithinDistance(plr.getPosition(), 3)) {
                            player.graphics(FLIES_GFX);
                            player.hit(new Hit().fixedDamage(plr.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES) ? (Utils.random(10, 20) / 2) : Utils.random(10, 20)));
                        }
                    }
                });
            } else if (npc.getPosition().inBounds(psouth2) && plr.getPosition().inBounds(psouth2) || npc.getPosition().inBounds(pnorth2) && plr.getPosition().inBounds(pnorth2)) {
                plr.graphics(FLIES_GFX);
                plr.hit(new Hit().fixedDamage(plr.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES) ? (Utils.random(10, 20) / 2) : Utils.random(10, 20)));
                plr.localPlayers().forEach(player -> {
                    if (player != plr) {
                        if (player.getPosition().isWithinDistance(plr.getPosition(), 3)) {
                            player.graphics(FLIES_GFX);
                            player.hit(new Hit().fixedDamage(plr.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES) ? (Utils.random(10, 20) / 2) : Utils.random(10, 20)));
                        }
                    }
                });
            } else if (npc.getPosition().inBounds(psouth3) && plr.getPosition().inBounds(psouth3) || npc.getPosition().inBounds(pnorth3) && plr.getPosition().inBounds(pnorth3)) {
                plr.graphics(FLIES_GFX);
                plr.hit(new Hit().fixedDamage(plr.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES) ? (Utils.random(10, 20) / 2) : Utils.random(10, 20)));
                plr.localPlayers().forEach(player -> {
                    if (player != plr) {
                        if (player.getPosition().isWithinDistance(plr.getPosition(), 3)) {
                            player.graphics(FLIES_GFX);
                            player.hit(new Hit().fixedDamage(plr.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES) ? (Utils.random(10, 20) / 2) : Utils.random(10, 20)));
                        }
                    }
                });
            }
        });

    }

    public Position getAbsolute(int localX, int localY) {
        return new Position(npc.getPosition().getRegion().baseX + localX, npc.getPosition().getRegion().baseY + localY, npc.getPosition().getZ());
    }

}

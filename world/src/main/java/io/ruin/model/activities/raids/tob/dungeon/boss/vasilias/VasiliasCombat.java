package io.ruin.model.activities.raids.tob.dungeon.boss.vasilias;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;

public class VasiliasCombat extends NPCCombat {

    private static final int START_HEIGHT = 25;
    private static final int END_HEIGHT = 30;
    private static final int DELAY = 30;
    private static final int DURATION_START = 46;
    private static final int DURATION_INCREMENT = 8;
    private static final int CURVE = 15;
    private static final int OFFSET = 255;
    private static final int TILE_OFFSET = 1;

    private static final Projectile RANGED_PROJECTILE = new Projectile(1560, START_HEIGHT, END_HEIGHT, DELAY, DURATION_START, DURATION_INCREMENT, CURVE, OFFSET).tileOffset(TILE_OFFSET);
    private static final Projectile MAGIC_PROJECTILE = new Projectile(1580, START_HEIGHT, END_HEIGHT, DELAY, DURATION_START, DURATION_INCREMENT, CURVE, OFFSET).tileOffset(TILE_OFFSET);

    public static final int ANIM = 7989;
    public static final int ANIM_RANGED = 7999;

    private static final int MELEE = 8355;
    private static final int MAGIC = 8356;
    private static final int RANGED = 8357;

    private int stage = 0;
    private int wave = 0;
    private int spawn = 0;
    private boolean sleep = false;

    @Override
    public void init() {
        //  npc.setMaxHp((int) ((double) npc.getMaxHp() * 0.75));
        npc.hitListener = new HitListener().postDamage(hit -> {
            double ratio = ((double) npc.getHp() / npc.getMaxHp());
            if (ratio <= .70 && stage == 0) {
                npc.lock(LockType.FULL);
                npc.animate(8000, 3);
                npc.setHidden(true);
                wave();
            } else if (ratio <= .50 && stage == 1) {
                npc.lock(LockType.FULL);
                npc.animate(8000, 3);
                npc.setHidden(true);
                wave();
            } else if (ratio <= .3 && stage == 2) {
                npc.lock(LockType.FULL);
                npc.animate(8000, 3);
                npc.setHidden(true);
                wave();
            }
            if (!npc.isHidden()) {
                npc.startEvent(event -> {
                    event.delay(10);
                    if (npc.getId() == MELEE) {
                        npc.transform(RANGED);
                    } else if (npc.getId() == RANGED) {
                        npc.transform(MELEE);
                    } else if (npc.getId() == MAGIC) {
                        npc.transform(MAGIC);
                    }
                });
            }
        });
    }


    @Override
    public void follow() {
        follow(30);
    }

    @Override
    public boolean attack() {
        if (npc.getId() == MELEE) {
            basicAttack(8004, AttackStyle.CRUSH, info.max_damage);
        } else if (npc.getId() == RANGED) {
            rangedAttack();
        } else if (npc.getId() == MAGIC) {
            magicAttack();
        }
        return true;
    }

    private void magicAttack() {
        npc.animate(ANIM);
        int delay = MAGIC_PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(25).clientDelay(delay));
    }

    private void rangedAttack() {
        npc.animate(ANIM_RANGED);
        int delay = RANGED_PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.RANGED).randDamage(25).clientDelay(delay));
    }

    public Position getAbsolute(int localX, int localY) {
        return new Position(npc.getPosition().getRegion().baseX + localX, npc.getPosition().getRegion().baseY + localY, npc.getPosition().getZ());
    }

    private void wave() {
        sleep = false;
        if (wave == 0) {
            npc.startEvent(e -> {
                while (!sleep) {
                    e.delay(4);
                    NPC n = new NPC(Random.get(10800, 10802)).spawn(getAbsolute(46, Random.get(24, 25)));
                    e.delay(1);
                    n.getCombat().setAllowRetaliate(true);
                    n.getCombat().isAggressive();
                    movetocenter(n);
                    spawn += 4;
                    if (spawn >= 30) {
                        sleep = true;
                        e.delay(10);
                        npc.setHidden(false);
                        npc.unlock();
                    }
                }
            });
        } else if (wave == 1) {
            npc.startEvent(e -> {
                while (!sleep) {
                    e.delay(4);
                    NPC n = new NPC(Random.get(10800, 10802)).spawn(getAbsolute(46, Random.get(24, 25)));
                    e.delay(1);
                    n.getCombat().setAllowRetaliate(true);
                    n.getCombat().isAggressive();
                    movetocenter(n);
                    spawn += 4;
                    if (spawn >= 35) {
                        sleep = true;
                        e.delay(10);
                        npc.setHidden(false);
                        npc.unlock();
                    }
                }
            });
        } else if (wave == 2) {
            npc.startEvent(e -> {
                while (!sleep) {
                    e.delay(4);
                    NPC n = new NPC(Random.get(10800, 10802)).spawn(getAbsolute(46, Random.get(24, 25)));
                    e.delay(1);
                    n.getCombat().setAllowRetaliate(true);
                    n.getCombat().isAggressive();
                    movetocenter(n);
                    spawn += 4;
                    if (spawn >= 40) {
                        sleep = true;
                        e.delay(10);
                        npc.setHidden(false);
                        npc.unlock();
                    }
                }
            });
        } else if (wave == 3) {
            npc.startEvent(e -> {
                while (!sleep) {
                    e.delay(4);
                    NPC n = new NPC(Random.get(10800, 10802)).spawn(getAbsolute(46, Random.get(24, 25)));
                    e.delay(1);
                    n.getCombat().setAllowRetaliate(true);
                    n.getCombat().isAggressive();
                    movetocenter(n);
                    spawn += 4;
                    if (spawn >= 50) {
                        sleep = true;
                        e.delay(10);
                        npc.setHidden(false);
                        npc.unlock();
                    }
                }
            });
        }
        wave++;
        stage++;
    }

    private void movetocenter(NPC n) {
        n.getRouteFinder().routeEntity(npc);
        n.getDef().ignoreOccupiedTiles = true;
    }
}

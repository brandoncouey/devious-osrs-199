package io.ruin.model.activities.raids.tob.dungeon.boss.sotetseg;

import io.ruin.model.World;
import io.ruin.model.activities.raids.tob.dungeon.boss.sotetseg.attacks.SmallBallAttack;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.npc.actions.edgeville.Nurse;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Direction;
import io.ruin.model.map.route.routes.DumbRoute;


public class SotetsegCombat extends NPCCombat {


    private int stage = 0;
    private static final int ANIMATION = 8315;

    @Override
    public void init() {
        npc.deathEndListener = (DeathListener.Simple) () -> {
            for (Player p : npc.getPosition().getRegion().players) {
                Nurse.restoreOnly(p);
            }
        };
/*
//        npc.setMaxHp((int) ((double) npc.getMaxHp() * 0.75));
       /* npc.hitListener = new HitListener().postDamage(hit -> {
            double ratio = ((double) npc.getHp() / npc.getMaxHp());
            if (ratio <= .70 && stage == 0) {
                for (Player p : npc.localPlayers()) {
                    knockback(p);
                    npc.animate(8138);
                }
            } else if (ratio <= .50 && stage == 1) {
                for (Player p : npc.localPlayers()) {
                    knockback(p);
                    npc.animate(8138);
                }
            } else if (ratio <= .3 && stage == 2) {
                for (Player p : npc.localPlayers()) {
                    knockback(p);
                    npc.animate(8138);
                }
            }
        });*/
    }

    @Override
    public void follow() {
        //follow(50);
    }

    @Override
    public boolean attack() {
           /* if (Random.rollPercent(10)) {
                new BigBallAttack(target, npc);
            } else {*/
        new SmallBallAttack(target, npc);
        return true;
    }

    private void knockback(Player target) {
        if (target == null || npc == null || isDead())
            return;
        int vecX = (target.getAbsX() - getClosestX());
        if (vecX != 0)
            vecX /= Math.abs(vecX); // determines X component for knockback
        int vecY = (target.getAbsY() - getClosestY());
        if (vecY != 0)
            vecY /= Math.abs(vecY); // determines Y component for knockback
        int endX = target.getAbsX();
        int endY = target.getAbsY();
        for (int i = 0; i < 4; i++) {
            if (DumbRoute.getDirection(endX, endY, npc.getHeight(), target.getSize(), endX + vecX, endY + vecY) != null) {
                endX += vecX;
                endY += vecY;
            } else
                break; // cant take the step, stop here
        }
        Direction dir;
        if (vecX == -1)
            dir = Direction.EAST;
        else if (vecX == 1)
            dir = Direction.WEST;
        else if (vecY == -1)
            dir = Direction.NORTH;
        else
            dir = Direction.SOUTH;

        if (endX != target.getAbsX() || endY != target.getAbsY()) {
            if (target.player != null) {
                int finalEndX = endX;
                int finalEndY = endY;
                World.startEvent(e -> {
                    final Player p = target.player;
                    p.lock();
                    p.animate(1157);
                    p.graphics(245, 5, 124);
                    p.hit(new Hit().fixedDamage(20));
                    p.stun(2, true);
                    int diffX = finalEndX - target.getAbsX();
                    int diffY = finalEndY - target.getAbsY();
                    p.getMovement().teleport(finalEndX, finalEndY, npc.getHeight());
                    p.getMovement().force(diffX, diffY, 0, 0, 10, 60, dir);
                    p.sendMessage("Sotetseg, knocks you back!");
                    p.unlock();
                });
            }
        } else {
            target.hit(new Hit().fixedDamage(20));
            target.animate(1157);
            target.graphics(245, 5, 124);
            target.stun(2, true);
            if (target.player != null)
                target.player.sendMessage("Sotetseg, knocks you back!");
        }
        stage++;
    }

    private int getClosestX() {
        if (target.getAbsX() < npc.getAbsX())
            return npc.getAbsX();
        else if (target.getAbsX() >= npc.getAbsX() && target.getAbsX() <= npc.getAbsX() + npc.getSize() - 1)
            return target.getAbsX();
        else
            return npc.getAbsX() + npc.getSize() - 1;
    }

    private int getClosestY() {
        if (target.getAbsY() < npc.getAbsY())
            return npc.getAbsY();
        else if (target.getAbsY() >= npc.getAbsY() && target.getAbsY() <= npc.getAbsY() + npc.getSize() - 1)
            return target.getAbsY();
        else
            return npc.getAbsY() + npc.getSize() - 1;
    }

    private SotetsegNPC asSotetseg() {
        return (SotetsegNPC) npc;
    }
}

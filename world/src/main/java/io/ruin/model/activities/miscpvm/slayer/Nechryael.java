package io.ruin.model.activities.miscpvm.slayer;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.route.routes.ProjectileRoute;

import java.util.ArrayList;
import java.util.List;

public class Nechryael extends NPCCombat {

    // private NPC[] deathSpawns = new NPC[2];


    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(1);
    }


    @Override
    public void startDeath(Hit killHit) {
        // for (NPC deathSpawn : deathSpawns) {
        // if (deathSpawn != null && !deathSpawn.isRemoved() && !deathSpawn.getCombat().isDead())
        //super.startDeath(killHit);
        //}
        super.startDeath(killHit);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        //if (Random.rollDie(2, 1))
        // checkDeathSpawns();
        basicAttack();
        return true;
    }

    private Position getSpawnPosition() {
        List<Position> positions = new ArrayList<>(18);
        int radius = 1;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                Tile tile = Tile.get(target.getAbsX() + x, target.getAbsY() + y, npc.getHeight(), false);
                if (tile == null || (tile.npcCount == 0 && tile.playerCount == 0 && ProjectileRoute.allow(target, target.getAbsX() + x, target.getAbsY() + y)))
                    positions.add(new Position(target.getAbsX() + x, target.getAbsY(), target.getHeight()));
            }
        }

        // If no potential positions can be resolved then just default to death tile.
        if (positions.isEmpty()) {
            return getDropPosition();
        }

        return Random.get(positions);
    }

}

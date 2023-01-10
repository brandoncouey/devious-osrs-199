package io.ruin.model.activities.bosses.sarachnis;

import io.ruin.model.entity.npc.NPCCombat;

public class SarachnisSpawn extends NPCCombat {

    /**
     * Sarachnis & minions need work.
     * TODO
     */



    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if(npc.getId() == 8714) {
            if((withinDistance(1))) {
                return true;
            }
        }
        if(npc.getId() == 8715) {
            //System.out.println(npc.getId() + " Has spawned");
        }
        return false;
    }
}

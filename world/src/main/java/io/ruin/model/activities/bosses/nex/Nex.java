package io.ruin.model.activities.bosses.nex;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;

import java.util.ArrayList;

public class Nex extends NPC {

    public static ArrayList<Player> deadplayers = new ArrayList<>();

    public static NPC Nex = new NPC(14647).spawn(2909, 4706, 1);

    private final Position base;

    public Nex(int id, Position base) {
        super(id);
        this.base = base;
        Nex.getCombat().setTarget(player);
        init();
    }

    public Position getBase() {
        return base;
    }

    //Fumus NPC 14651 Cannot be attacked until nex's HP <= 80% (Smoke)
    //Umbra NPC 14652 Cannot be attacked until nex's HP <= 60% (Shadow)
    //Cruor NPC 14653 Cannot be attacked until nex's HP <= 40% (Blood)
    //Glacies NPC 14654 Cannot be attacked until nex's HP <= 20% (Ice)

}

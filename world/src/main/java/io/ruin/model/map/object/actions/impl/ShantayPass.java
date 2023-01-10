package io.ruin.model.map.object.actions.impl;

import io.ruin.model.diaries.desert.DesertDiaryEntry;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.object.actions.ObjectAction;

public class ShantayPass {

    static {
        ObjectAction.register(4031, 1, (player, obj) -> {
            player.getDiaryManager().getDesertDiary().progress(DesertDiaryEntry.PASS_GATE);
            player.getDiaryManager().getDesertDiary().progress(DesertDiaryEntry.PASS_GATE_ROBES);
            player.step(0, player.getAbsY() > obj.y ? -2 : 2, StepType.FORCE_WALK);
        });
    }

}

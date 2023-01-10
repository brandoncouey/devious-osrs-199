package io.ruin.model.item.actions.impl.upgrade;

import io.ruin.model.stat.StatType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpgradeSkillRequirements {

    /**
     * Represents the Skill associated with the requirement
     */
    private final StatType skill;

    /**
     * Represents the Level Required
     */
    private final int level;
}

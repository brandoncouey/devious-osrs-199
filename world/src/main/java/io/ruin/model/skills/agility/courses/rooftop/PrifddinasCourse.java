package io.ruin.model.skills.agility.courses.rooftop;

import io.ruin.api.utils.Random;
import io.ruin.model.contracts.agility.AgilityContract;
import io.ruin.model.contracts.agility.AgilityContractType;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class PrifddinasCourse {

    private static final List<Position> MARK_SPAWNS = Arrays.asList(
            new Position(123, 123, 1)
    );

    static {
        /**
         * Ladder #1
         */
        ObjectAction.register(36221, "climb", (p, obj) -> p.startEvent(e -> {
            if (!p.getStats().check(StatType.Agility, 75, "attempt this"))
                return;
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(828, 15);
            e.delay(2);
            p.getMovement().teleport(3255, 6109, 2);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 11.5, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));


        /**
         *  Tightrope #1
         */
        ObjectAction.register(36225, "cross", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.stepAbs(3258, 6105, StepType.FORCE_RUN);
            p.stepAbs(3272, 6105, StepType.FORCE_WALK);
            e.delay(1);
            p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            e.delay(14);
            p.getAppearance().removeCustomRenders();
            e.delay(1);
            p.getStats().addXp(StatType.Agility, 30.7, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /**
         * Chimney
         */
        Tile.getObject(36227, 3273, 6107, 2).skipReachCheck = p -> p.equals(3272, 6105) || p.equals(3273, 6105);
        ObjectAction.register(36227, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.stepAbs(3273, 6105, StepType.FORCE_WALK);
            p.animate(2583, 20);
            p.getMovement().force(0, 1, 0, 0, 25, 30, Direction.NORTH);
            e.delay(1);
            p.animate(2585);
            e.delay(1);
            p.stepAbs(3273, 6107, StepType.FORCE_WALK);
            p.getMovement().force(0, 1, 0, 0, 17, 26, Direction.NORTH);
            e.delay(1);
            p.animate(741);
            p.getMovement().force(-4, 5, 0, 0, 5, 30, Direction.NORTH_WEST);
            e.delay(1);
            p.getStats().addXp(StatType.Agility, 28.1, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /**
         * Roof Edge
         */

        ObjectAction.register(36228, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(2586, 15);
            e.delay(1);
            p.getMovement().teleport(3269, 6117, 0);
            p.animate(2588);
            e.delay(1);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 23.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /**
         * Dark Hole
         */
        ObjectAction.register(36229, "enter", (p, obj) -> p.startEvent(e -> {
            p.lock();
            p.animate(827);
            e.delay(1);
            p.getPacketSender().fadeOut();
            e.delay(5);
            p.getPacketSender().fadeIn();
            p.getMovement().teleport(3293, 6141, 0);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 11.5, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));


        /**
         * Ladder #2
         */
        ObjectAction.register(36231, "climb", (p, obj) -> p.startEvent(e -> {
            if (!p.getStats().check(StatType.Agility, 75, "attempt this"))
                return;
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(828, 15);
            e.delay(2);
            p.getMovement().teleport(3293, 6145, 2);
            p.resetAnimation();
            //no experience given for this obstacle //p.getStats().addXp(StatType.Agility, 0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /**
         * Rope Bridge #1
         */
        ObjectAction.register(36233, "cross", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            //p.stepAbs(3289, 6142, StepType.FORCE_RUN);
            p.stepAbs(3281, 6142, StepType.FORCE_WALK);
            e.delay(1);
            p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            e.delay(6);
            p.getAppearance().removeCustomRenders();
            e.delay(1);
            p.getStats().addXp(StatType.Agility, 25.6, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /**
         * Tightrope #2
         */
        ObjectAction.register(36234, "cross", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.stepAbs(3277, 6143, StepType.FORCE_RUN);
            e.delay(1);
            p.stepAbs(3271, 6149, StepType.FORCE_WALK);
            e.delay(1);
            p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            e.delay(6);
            p.getAppearance().removeCustomRenders();
            e.delay(1);
            p.getStats().addXp(StatType.Agility, 30.7, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /**
         * Rope Bridge #2
         */
        ObjectAction.register(36235, "cross", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.stepAbs(3270, 6150, StepType.FORCE_RUN);
            p.stepAbs(3270, 6158, StepType.FORCE_WALK);
            e.delay(1);
            p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            e.delay(6);
            p.getAppearance().removeCustomRenders();
            e.delay(1);
            p.getStats().addXp(StatType.Agility, 25.6, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /**
         * Tightrope #3
         */
        ObjectAction.register(36236, "cross", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.stepAbs(3267, 6161, StepType.FORCE_RUN);
            e.delay(1);
            p.stepAbs(3274, 6168, StepType.FORCE_WALK);
            e.delay(1);
            p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            e.delay(6);
            p.getAppearance().removeCustomRenders();
            e.delay(1);
            p.getStats().addXp(StatType.Agility, 30.7, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /**
         * Tightrope #4
         */
        ObjectAction.register(36237, "cross", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.stepAbs(3278, 6171, StepType.FORCE_RUN);
            e.delay(1);
            p.stepAbs(3284, 6177, StepType.FORCE_WALK);
            e.delay(1);
            p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            e.delay(6);
            p.getAppearance().removeCustomRenders();
            e.delay(1);
            p.getMovement().teleport(3284, 6177, 0);
            p.getStats().addXp(StatType.Agility, 30.7, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /**
         * Dark Hole #2
         */
        ObjectAction.register(36238, "enter", (p, obj) -> p.startEvent(e -> {
            p.lock();
            p.animate(827);
            e.delay(1);
            p.getPacketSender().fadeOut();
            e.delay(5);
            p.getPacketSender().fadeIn();
            p.getMovement().teleport(3240, 6109, 0);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 1037.1, true);
            p.getInventory().add(23962, Random.get(200, 600));
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
            if(p.agilityContractType == AgilityContractType.PRIFDDINAS_ROOFTOP)
                AgilityContract.advanceAgilityContract(p);
            if (Random.rollDie(50, 1)) {
                p.getInventory().addOrDrop(6828);
                p.sendMessage("You've discovered a Skilling box. It's been added to your inventory.");
            }
        }));

    }
}
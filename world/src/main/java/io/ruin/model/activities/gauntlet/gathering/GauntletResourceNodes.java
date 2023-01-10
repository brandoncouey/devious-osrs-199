/**
 *
 */
package io.ruin.model.activities.gauntlet.gathering;

import io.ruin.utility.Misc;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ReverendDread
 * Aug 17, 2019
 */
@Getter
@RequiredArgsConstructor
public enum GauntletResourceNodes {

    CRYSTAL_DEPOSIT(36064, 36065, 23877, 3, 2, 23863, 8345, 1),
    PHREN_ROOTS(36066, 36067, 23878, 3, 3, 23862, 8324, 1),
    FISHING_SPOT(36068, 36069, 23872, 3, 3, 23864, 8336, 1),
    GRYM_ROOT(36070, 36071, 23875, 3, 2, -1, 2286, 1),
    LINUM_TIRINUM(36072, 36073, 23876, 3, 2, -1, 2286, 1);

    private final int objectId, depletedId, itemId, harvestAmount, time, toolId, animation;
    private final int experience;

    /**
     * An unmodifiable set of {@link GauntletResourceNodes} objects that will be used as a
     * constant for obtaining information about certain nodes.
     */
    private static final List<GauntletResourceNodes> NODES = Arrays.asList(values());

    /**
     * Retrieves the {@link GauntletResourceNodes} object with the same objectId as the parameter
     * passed.
     *
     * @param objectId the object id of the node
     * @return the resource node object with the corresponding object id
     */
    public static GauntletResourceNodes forObjectId(int objectId) {
        return NODES.stream().filter(node -> node.objectId == objectId).findFirst().orElse(null);
    }

    /**
     * Gets a random resource node.
     * @return
     */
    public static GauntletResourceNodes getRandomNode() {
        List<GauntletResourceNodes> nodes = NODES.stream().collect(Collectors.toList());
        return Misc.randomTypeOfList(nodes);
    }

}

/**
 *
 */
package io.ruin.model.activities.gauntlet.crafting;

import io.ruin.model.entity.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Greco
 * @since 04/08/2021
 */
@AllArgsConstructor
@Getter
public enum GauntletCraftables implements GauntletCraftable {

    BASIC_CRYSTAL_HELM(new int[][]{{23962, 40}, {23877, 1}, {23878, 1}, {23876, 1}}, 23886, null),
    ATTUNED_CRYSTAL_HELM(new int[][]{{23962, 60}, {23877, 2}, {23878, 2}, {23876, 2}, {23886, 1}}, 23887, BASIC_CRYSTAL_HELM),
    PERFECTED_CRYSTAL_HELM(new int[][]{{23962, 80}, {23877, 3}, {23878, 3}, {23876, 3}, {23887, 1}}, 23888, ATTUNED_CRYSTAL_HELM),

    BASIC_CRYSTAL_BODY(new int[][]{{23962, 40}, {23877, 1}, {23878, 1}, {23876, 1}}, 23889, null),
    ATTUNED_CRYSTAL_BODY(new int[][]{{23962, 60}, {23877, 2}, {23878, 2}, {23876, 2}, {23889, 1}}, 23890, BASIC_CRYSTAL_BODY),
    PERFECTED_CRYSTAL_BODY(new int[][]{{23962, 80}, {23877, 3}, {23878, 3}, {23876, 3}, {23890, 1}}, 23891, ATTUNED_CRYSTAL_BODY),

    BASIC_CRYSTAL_LEGS(new int[][]{{23962, 40}, {23877, 1}, {23878, 1}, {23876, 1}}, 23892, null),
    ATTUNED_CRYSTAL_LEGS(new int[][]{{23962, 60}, {23877, 2}, {23878, 2}, {23876, 2}, {23892, 1}}, 23893, BASIC_CRYSTAL_LEGS),
    PERFECTED_CRYSTAL_LEGS(new int[][]{{23962, 80}, {23877, 3}, {23878, 3}, {23876, 3}, {23893, 1}}, 23894, ATTUNED_CRYSTAL_LEGS),

    BASIC_CRYSTAL_HALBERD(new int[][]{{23962, 20}, {23871, 1}}, 23895, null),
    ATTUNED_CRYSTAL_HALBERD(new int[][]{{23962, 60}, {23895, 1}}, 23896, BASIC_CRYSTAL_HALBERD),
    PERFECTED_CRYSTAL_HALBERD(new int[][]{{23962, 1}, {23868, 1}}, 23897, ATTUNED_CRYSTAL_HALBERD),

    BASIC_CRYSTAL_BOW(new int[][]{{23962, 20}, {23871, 1}}, 23901, null),
    ATTUNED_CRYSTAL_BOW(new int[][]{{23962, 60}, {23901, 1}}, 23902, BASIC_CRYSTAL_BOW),
    PERFECTED_CRYSTAL_BOW(new int[][]{{23962, 1}, {23869, 1}}, 23903, ATTUNED_CRYSTAL_BOW),

    BASIC_CRYSTAL_STAFF(new int[][]{{23962, 20}, {23871, 1}}, 23898, null),
    ATTUNED_CRYSTAL_STAFF(new int[][]{{23962, 60}, {23898, 1,}}, 23899, BASIC_CRYSTAL_STAFF),
    PERFECTED_CRYSTAL_STAFF(new int[][]{{23962, 1}, {23870, 1}}, 23900, ATTUNED_CRYSTAL_STAFF),

    TELEPORT_CRYSTAL(new int[][]{{23962, 40}}, 23904, null),
    VIAL(new int[][]{{23962, 10}}, 229, null),

    ;

    private final int[][] materials;
    private final int product;
    private final GauntletCraftables inherits;

    public static GauntletCraftables findLowest(Player player, GauntletCraftables tier) {
        if (tier.inherits == null) {
            return tier;
        } else if (player.getInventory().hasAtLeastOneOf(tier.inherits.product) && player.getEquipment().hasAtLeastOneOf(tier.inherits.product)) {
            return tier;
        } else {
            return findLowest(player, tier.inherits);
        }
    }

    public static Optional<GauntletCraftables> getCraftableForProduct(int product) {
        return Stream.of(values()).filter(item -> item.getProduct() == product).findFirst();
    }

}
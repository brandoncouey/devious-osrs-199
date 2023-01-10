/**
 *
 */
package io.ruin.model.activities.gauntlet.crafting;

/**
 * @author Greco
 * @since 04/08/2021
 */
public interface GauntletCraftable<T> {

    int[][] getMaterials();

    int getProduct();

}

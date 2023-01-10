package io.ruin.model.contracts;

import io.ruin.model.contracts.agility.AgilityContract;
import io.ruin.model.contracts.farming.FarmingContract;
import io.ruin.model.contracts.fishing.FishingContract;
import io.ruin.model.contracts.mining.MiningContract;
import io.ruin.model.contracts.smithing.SmithingContract;
import io.ruin.model.contracts.woodcutting.WoodcuttingContract;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;

/**
 * @author Greco
 * @since 03/30/2021
 */
public class SkillingContract {

    /* displays the skilling contract menu */
    public static void open(Player c) {
        OptionScroll.open(c, "<col=8B0000>Select a Skilling Contract",
                new Option("-"),
                new Option("<col=0040ff>Click an option to select a contract"),
                new Option("-"),
                new Option("Woodcutting", () -> displayContract(c, SkillingContractType.WOODCUTTING)),
                new Option("Fishing", () -> displayContract(c, SkillingContractType.FISHING)),
                new Option("Mining", () -> displayContract(c, SkillingContractType.MINING)),
                new Option("Smithing", () -> displayContract(c, SkillingContractType.SMITHING)),
                new Option("Agility", () -> displayContract(c, SkillingContractType.AGILITY)),
                new Option("Farming", () -> displayContract(c, SkillingContractType.FARMING))
        );

        return;
    }

    /* handles the contracts menu */
    public static void displayContract(Player c, SkillingContractType contractType) {
        switch (contractType) {
            case WOODCUTTING:
                WoodcuttingContract.displayWoodcuttingContract(c);
                break;
            case FISHING:
                FishingContract.displayFishingContract(c);
                break;
            case MINING:
                MiningContract.displayMiningContract(c);
                break;
            case SMITHING:
                SmithingContract.displaySmithingContract(c);
                break;
            case AGILITY:
                AgilityContract.displayAgilityContract(c);
                break;
            case FARMING:
                FarmingContract.displayFarmingContracts(c);
                break;
        }

    }

}

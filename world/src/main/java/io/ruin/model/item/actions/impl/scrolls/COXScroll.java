package io.ruin.model.item.actions.impl.scrolls;

import io.ruin.model.activities.raids.party.Party;
import io.ruin.model.activities.raids.xeric.ChambersOfXeric;
import io.ruin.model.activities.raids.xeric.chamber.Chamber;
import io.ruin.model.activities.raids.xeric.chamber.ChamberDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Position;
import io.ruin.model.map.dynamic.DynamicMap;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class COXScroll {
    static {
            ItemAction.registerInventory(30418, "redeem", (player, item) -> {
                int rotation = 0;
                int layout = 0;
                int finalRotation = rotation;
                int finalLayout = layout;
                Consumer<ChamberDefinition> run = definition -> {
                    Chamber chamber = definition.newChamber();
                    if (chamber == null) {
                        player.sendMessage("Failed to generate room");
                        return;
                    }
                    ChambersOfXeric raid = new ChambersOfXeric();
                    Party party = new Party(player);
                    player.raidsParty = party;
                    raid.setParty(party);
                    party.setRaid(raid);
                    chamber.setRaid(raid);
                    chamber.setRotation(finalRotation);
                    chamber.setLayout(finalLayout);
                    chamber.setLocation(0, 0, 0);
                    DynamicMap map = new DynamicMap().build(chamber.getChunks());
                    raid.setMap(map);
                    chamber.setBasePosition(new Position(map.swRegion.baseX, map.swRegion.baseY, 0));
                    chamber.onBuild();
                    chamber.onRaidStart();
                    party.addPoints(30_000);
                    player.getMovement().teleport(chamber.getPosition(30, 25));
                    player.getInventory().remove(30418,1);
                };
                OptionScroll.open(player, "Select a room type",
                        new Option("Olm", () -> run.accept(ChamberDefinition.GREAT_OLM)));
            });
    }
}

package io.ruin.model.activities.donator;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.SecondaryGroup;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.utility.Misc;
import io.ruin.utility.Utils;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class DonatorPerks {

    @RequiredArgsConstructor
   enum Perks {
       OPAL(new Set[] {

       }),
       JADE(new Set[] {

       }),
       RED_TOPAZ(new Set[] {

       }),
       SAPPHIRE(new Set[] {

       }),
       EMERALD(new Set[] {

       }),
       RUBY(new Set[] {

       }),
       DIAMOND(new Set[] {

       }),
       DRAGONSTONE(new Set[] {

       }),
       ONYX(new Set[] {

       }),

       ;

       @Getter
       private final Set[] benefits;
   }

    public static void select(Player player, int option) {
        Perks perks = Perks.values()[option];
        if (perks == null) select(player, 0);
        for (int i = 0; i < 11; i++) {
            player.getPacketSender().setHidden(1036, 61 + (i * 4), true);
        }
        SecondaryGroup currentGroup = player.getSecondaryGroup();
        if (currentGroup == SecondaryGroup.ZENYTE) {
            player.getPacketSender().sendString(1036, 110, "$" + Utils.formatMoneyString(player.amountDonated) + " / Unavailable");
        } else {
            SecondaryGroup nextGroup = SecondaryGroup.values()[option + 1];
            player.getPacketSender().sendString(1036, 110, "$" + Utils.formatMoneyString(player.amountDonated) + " / $" + Utils.formatMoneyString(nextGroup.dontationRequired) + " until " + Misc.formatStringFormal(nextGroup.name()));
        }
        for (int i = 0; i < perks.getBenefits().length; i++) {
            Set benefits = perks.getBenefits()[i];
            player.getPacketSender().setHidden(1036, 61 + (i * 4), false);
            player.getPacketSender().sendString(1036, 63 + (i * 4), benefits.getName());//Left Text
            player.getPacketSender().sendString(1036, 64 + (i * 4), benefits.getDescription());//Right Text
        }
    }

    public static void open(Player player) {
        select(player, 0);
        player.getPacketSender().sendString(1036, 24, Utils.formatMoneyString(player.donatorPoints));
        player.openInterface(InterfaceType.MAIN, 1036);
    }

    static {
        InterfaceHandler.register(1036, h -> {
            for (int i = 0; i < 9; i++) {
                h.actions[27 + (i * 3)] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                    DonatorPerks.select(player, (childId - 27) / 3);
                };
            }
        });
    }

    @Data
    @RequiredArgsConstructor
    static
    class Set {
        private final String name;
        private final String description;
    }
}

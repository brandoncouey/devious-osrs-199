package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.World;
import io.ruin.model.diaries.pvm.PvMDiaryEntry;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.utility.TeleportConstants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class JeweleryBox {

    @Getter
    @RequiredArgsConstructor
    private static class Teleport {
        private final String location;
        private final Position position;
    }

    public static final Teleport[][] OPTIONS = new Teleport[][] {
            new Teleport[] {
                    new Teleport("Al Kharid Duel Arena", TeleportConstants.DUEL_ARENA),
                    new Teleport("Castle Wars Arena", TeleportConstants.CASTLE_WARS),
                    new Teleport("Clan Wars Arena", TeleportConstants.CLAN_WARS),
                    new Teleport("Burthorpe", TeleportConstants.BURTHORPE),
                    new Teleport("Barbarian Outpost", TeleportConstants.BARBARIAN_OUTPOST),
                    new Teleport("Corporeal Beast", TeleportConstants.CORPOREAL_BEAST),
                    new Teleport("Tears of Guthix", TeleportConstants.EDGEVILLE),
                    new Teleport("Wintertodt Camp", TeleportConstants.WINTERTODT)
            },
            new Teleport[] {
                    new Teleport("Al Kharid Duel Arena", TeleportConstants.DUEL_ARENA),
                    new Teleport("Castle Wars Arena", TeleportConstants.CASTLE_WARS),
                    new Teleport("Clan Wars Arena", TeleportConstants.CLAN_WARS),
                    new Teleport("Burthorpe", TeleportConstants.BURTHORPE),
                    new Teleport("Barbarian Outpost", TeleportConstants.BARBARIAN_OUTPOST),
                    new Teleport("Corporeal Beast", TeleportConstants.CORPOREAL_BEAST),
                    new Teleport("Tears of Guthix", TeleportConstants.EDGEVILLE),
                    new Teleport("Wintertodt Camp", TeleportConstants.WINTERTODT),
                    new Teleport("Warriors' Guild", TeleportConstants.WARRIORS_GUILD),
                    new Teleport("Champions' Guild", TeleportConstants.CHAMPIONS_GUILD),
                    new Teleport("Edgeville Monastery", TeleportConstants.EDGEVILLE),
                    new Teleport( "Ranging guild", TeleportConstants.RANGING_GUILD),
                    new Teleport("Fishing Guild", TeleportConstants.FISHING_GUILD),
                    new Teleport("Mining Guild", TeleportConstants.MINING_GUILD),
                    new Teleport("Crafting Guild", TeleportConstants.CRAFTING_GUILD),
                    new Teleport("Cooking Guild", TeleportConstants.COOKING_GUILD),
                    new Teleport("Woodcutting Guild", TeleportConstants.WOODCUTTING_GUILD),
            },
            new Teleport[] {
                    new Teleport("Al Kharid Duel Arena", TeleportConstants.DUEL_ARENA),
                    new Teleport("Castle Wars Arena", TeleportConstants.CASTLE_WARS),
                    new Teleport("Clan Wars Arena", TeleportConstants.CLAN_WARS),
                    new Teleport("Burthorpe", TeleportConstants.BURTHORPE),
                    new Teleport("Barbarian Outpost", TeleportConstants.BARBARIAN_OUTPOST),
                    new Teleport("Corporeal Beast", TeleportConstants.CORPOREAL_BEAST),
                    new Teleport("Tears of Guthix", TeleportConstants.EDGEVILLE),
                    new Teleport("Wintertodt Camp", TeleportConstants.WINTERTODT),
                    new Teleport("Warriors' Guild", TeleportConstants.WARRIORS_GUILD),
                    new Teleport("Champions' Guild", TeleportConstants.CHAMPIONS_GUILD),
                    new Teleport("Edgeville Monastery", TeleportConstants.EDGEVILLE_MONESTARY),
                    new Teleport( "Ranging guild", TeleportConstants.RANGING_GUILD),
                    new Teleport("Fishing Guild", TeleportConstants.FISHING_GUILD),
                    new Teleport("Mining Guild", TeleportConstants.MINING_GUILD),
                    new Teleport("Crafting Guild", TeleportConstants.CRAFTING_GUILD),
                    new Teleport("Cooking Guild", TeleportConstants.COOKING_GUILD),
                    new Teleport("Woodcutting Guild", TeleportConstants.WOODCUTTING_GUILD),
                    new Teleport("Miscellania", TeleportConstants.MISCELLANIA),
                    new Teleport("Grand Exchange", TeleportConstants.GRAND_EXCHANGE),
                    new Teleport("Falador Park", TeleportConstants.FALADOR_PARK),
                    new Teleport("Dondakan's Rock", TeleportConstants.DONDAKANS_ROCK),
                    new Teleport("Edgeville", TeleportConstants.EDGEVILLE),
                    new Teleport("Karamja", TeleportConstants.KARAMJA),
                    new Teleport("Draynor Village", TeleportConstants.DRAYNOR_VILLAGE),
                    new Teleport("Al Kharid", TeleportConstants.AL_KHARID)
            }
    };



    public static void open(int id, Player player) {
        Option[] options = new Option[OPTIONS[id].length];
        for (int i = 0; i < OPTIONS[id].length; i++) {
            Teleport teleport = OPTIONS[id][i];
            options[i] = new Option(teleport.getLocation(), () ->  {
                JeweleryBox.select(player, teleport);
            });
        }
        if (options.length > 5) {
            OptionScroll scroll; scroll = new OptionScroll("Where would you like to teleport to?", true, options);
            scroll.open(player);
        } else {
            player.dialogue(new OptionsDialogue("Where would you like to teleport to?", options));
        }
    }

    public static void select(Player player, Teleport teleport) {
        player.getMovement().startTeleport(58, event -> {
            player.animate(714);
            player.graphics(111, 92, 0);
            player.publicSound(200);
            event.delay(3);
            player.getMovement().teleport(teleport.getPosition());
        });
    }

    static {
        ObjectAction.register(29154, 1, (player, obj) -> {
            JeweleryBox.open(0, player);
        });
        ObjectAction.register(29155, 1, (player, obj) -> {
            JeweleryBox.open(1, player);
        });
        ObjectAction.register(29156, 1, (player, obj) -> {
            JeweleryBox.open(2, player);
        });
    }

}

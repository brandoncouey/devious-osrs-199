package io.ruin.model.inter.handlers;

import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.teleports.TeleportInterface;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.services.discord.impl.commands.BugReport;

public class MainFrame {

    static {
        InterfaceHandler.register(Interface.ORBS, h -> {
            h.actions[1] = (OptionAction) XpCounter::select;
            h.actions[15] = (OptionAction) (p, option) -> {
                if (option == 1)
                    p.getPrayer().toggleQuickPrayers();
                else
                    TabPrayer.setupQuickPrayers(p, true);
            };
            h.actions[23] = (OptionAction) (player, option) -> {
                if (option == 1)
                    player.getMovement().toggleRunning();
                else if (option == 2) {
                    player.getMovement().toggleResting();
                }
            };
            h.actions[48] = (SimpleAction) p -> {
                p.getPacketSender().sendClientScript(1749, "c", p.getPosition().getTileHash());
                p.openInterface(InterfaceType.WORLD_MAP, Interface.WORLD_MAP);
                p.getPacketSender().sendAccessMask(Interface.WORLD_MAP, 17, 0, 4, 2);
            };
            h.actions[31] = (SimpleAction) p -> p.getCombat().toggleSpecial();
            h.actions[41] = (OptionAction) (p, option) -> {
                if (p.isLocked()) {
                    return;
                }

                if (p.wildernessLevel > 0) {
                    p.sendMessage("You cannot use this feature inside the wilderness.");
                    return;
                }

                if (p.jailed) {
                    p.sendMessage("You cannot use this feature while jailed.");
                    return;
                }

                if (option == 1) {
                    TeleportInterface.open(p);
                } else if (option == 2) {
                    if (p.pvpAttackZone) {
                        p.sendMessage("You cannot use this in a PVP zone");
                        return;
                    }
                    if (p.previousTeleportX == -1)
                        p.dialogue(new MessageDialogue("You haven't teleported anywhere yet."));
                    else
                        ModernTeleport.teleport(p, p.previousTeleportX, p.previousTeleportY, p.previousTeleportZ);
                }
            };
        });
        InterfaceHandler.register(Interface.CHAT_BAR, h -> {
            h.actions[7] = (OptionAction) (player, option) -> {
                if (option == 2)
                    Config.GAME_FILTER.toggle(player);
                else if (option == 3) {
                    player.dialogue(
                            new MessageDialogue("Would you like to filter yells from non-staff members?"),
                            new OptionsDialogue(
                                    new Option("Yes", () -> {
                                        player.yellFilter = true;
                                        player.sendMessage("Yells from non-staff members will now be hidden when your game chat is set to filtered.");
                                    }),
                                    new Option("No", () -> {
                                        player.yellFilter = false;
                                        player.sendMessage("Yells from non-staff members will now show even when your game chat is set to filtered.");
                                    })
                            )
                    );
                }
            };
        });

        InterfaceHandler.register(Interface.CHAT_BAR, h -> {
            h.actions[31] = (SimpleAction) BugReport::open;
        });


        InterfaceHandler.register(Interface.FIXED_SCREEN, actions -> {
            actions.actions[62] = (DefaultAction) (player, option, slot, itemId) -> {
                if (option == 2)
                    Config.DISABLE_SPELL_FILTERING.toggle(player);
            };

        });


        InterfaceHandler.register(Interface.RESIZED_SCREEN, actions -> {
            actions.actions[68] = (DefaultAction) (player, option, slot, itemId) -> {
                if (option == 2)
                    Config.DISABLE_SPELL_FILTERING.toggle(player);
            };

        });
        InterfaceHandler.register(Interface.RESIZED_STACKED_SCREEN, actions -> {
            actions.actions[65] = (DefaultAction) (player, option, slot, itemId) -> {
                if (option == 2)
                    Config.DISABLE_SPELL_FILTERING.toggle(player);
            };
        });
    }

}

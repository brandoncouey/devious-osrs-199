package io.ruin.model.content.scratchies;

import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.utility.Broadcast;

public class MainCard extends ItemContainer {

    public static final LootTable DonScrathCard = new LootTable().addTable(1,
            new LootItem(13307, 100000, 10), //Blood Money
            new LootItem(11806, 1, 6).broadcast(Broadcast.WORLD), //Saradomin godsword
            new LootItem(11802, 1, 6).broadcast(Broadcast.WORLD), //Armadyl godsword
            new LootItem(11804, 1, 6).broadcast(Broadcast.WORLD), //Bandos godsword
            new LootItem(30294, 2, 6).broadcast(Broadcast.WORLD), //Donator Scratch Card
            new LootItem(21295, 1, 4).broadcast(Broadcast.WORLD), //Infernal Cape
            new LootItem(13343, 1, 1).broadcast(Broadcast.WORLD), //Black Santa
            new LootItem(11847, 1, 1).broadcast(Broadcast.WORLD), //Black H'ween
            new LootItem(11862, 1, 1).broadcast(Broadcast.WORLD), //Black Party Hat
            new LootItem(30308, 10, 4).broadcast(Broadcast.WORLD), //Donator Coins
            new LootItem(19547, 1, 5).broadcast(Broadcast.WORLD), //Amulet of Anguish
            new LootItem(19553, 1, 5).broadcast(Broadcast.WORLD), //Amulet of torture
            new LootItem(22638, 1, 1).broadcast(Broadcast.WORLD), //Morrigans Coif
            new LootItem(22641, 1, 1).broadcast(Broadcast.WORLD), //Morrigans Top
            new LootItem(22644, 1, 1).broadcast(Broadcast.WORLD), //Morrigans Bottom
            new LootItem(22650, 1, 1).broadcast(Broadcast.WORLD), //Zuriel hood
            new LootItem(22653, 1, 1).broadcast(Broadcast.WORLD), //Zuriel Top
            new LootItem(22656, 1, 1).broadcast(Broadcast.WORLD), //Zuriel bottom
            new LootItem(22616, 1, 1).broadcast(Broadcast.WORLD), //Vesta Top
            new LootItem(22619, 1, 1).broadcast(Broadcast.WORLD), //Vesta Bottom
            new LootItem(22613, 1, 1).broadcast(Broadcast.WORLD), //Vesta Longsword
            new LootItem(22625, 1, 1).broadcast(Broadcast.WORLD), //Statius Helm
            new LootItem(22628, 1, 1).broadcast(Broadcast.WORLD), //Statius top
            new LootItem(22631, 1, 1).broadcast(Broadcast.WORLD), //Statius Bottom
            new LootItem(22622, 1, 1).broadcast(Broadcast.WORLD), //Statius Warhammer
            new LootItem(30319, 1, 1).broadcast(Broadcast.WORLD), //Colossal Blade
            new LootItem(22647, 1, 1).broadcast(Broadcast.WORLD), //Zuriel Staff
            new LootItem(962, 1, 1).broadcast(Broadcast.WORLD), //Christmas Cracker
            new LootItem(2577, 1, 5).broadcast(Broadcast.WORLD) //Ranger boots
    );

    private static final LootTable PvmScrathCard = new LootTable().addTable(1,
            new LootItem(995, 100_000, 100), //COINS
            new LootItem(30260, 10, 50), //Large Tooth
            new LootItem(989, 5, 40), //Crytal Key
            new LootItem(12696, 25, 30), //Combat Potion
            new LootItem(30295, 2, 20), //PVM Scratch Card
            new LootItem(12004, 1, 10).broadcast(Broadcast.GLOBAL), //Kraken Tentacle
            new LootItem(11283, 1, 10).broadcast(Broadcast.GLOBAL), //Dragonfire shield
            new LootItem(11283, 1, 5).broadcast(Broadcast.GLOBAL), //Blood shard
            new LootItem(2577, 1, 5).broadcast(Broadcast.GLOBAL) //Ranger boots
    );

    public static void OpenCard(Player player) {
        player.openInterface(InterfaceType.MAIN, 1015);
        player.tonel = false;
        player.ttwol = false;
        player.tthreel = false;
        player.tfourl = false;
        player.tfivel = false;
        player.tsixl = false;
        player.tsevenl = false;
        player.teightl = false;
        player.tninel = false;
        player.tone = 0;
        player.ttwo = 0;
        player.tthree = 0;
        player.tfour = 0;
        player.tfive = 0;
        player.tsix = 0;
        player.tseven = 0;
        player.teight = 0;
        player.tnine = 0;
    }

    private static void checkCard(Player player) {
        if (!player.tonel || !player.ttwol || !player.tthreel || !player.tfourl || !player.tfivel || !player.tsixl || !player.tsevenl || !player.teightl || !player.tninel)
            return;
        int amount = 1;
        if (player.tone == player.ttwo && player.tone == player.tthree && player.tone == player.tfour && player.tone == player.tfive && player.tone == player.tsix && player.tone == player.tseven && player.tone == player.teight && player.tone == player.tnine && player.tone != 0) {
            if (player.card == 30295) {
                for (Item allItem : PvmScrathCard.allItems()) {
                    if (allItem.getId() == player.tone && allItem.lootBroadcast != null) {
                        player.getInventory().remove(player.card, 1);
                        Broadcast.WORLD.sendNews("[SCRATCH] " + player.getName() + " has just gotten " + (amount * 4) + " x " + ItemDef.get(player.tone).name + " from a Scratch Card!");
                    }
                    if (allItem.getId() == player.tone) {
                        player.getInventory().remove(player.card, 1);
                        amount = allItem.getAmount();
                    }
                }
            } else {
                for (Item allItem : DonScrathCard.allItems()) {
                    if (allItem.getId() == player.tone && allItem.lootBroadcast != null) {
                        player.getInventory().remove(player.card, 1);
                        Broadcast.WORLD.sendNews("[SCRATCH] " + player.getName() + " has just gotten " + (amount * 4) + " x " + ItemDef.get(player.tone).name + " from a Scratch Card!");
                    }
                    if (allItem.getId() == player.tone) {
                        player.getInventory().remove(player.card, 1);
                        amount = allItem.getAmount();
                    }
                }
            }
            player.getInventory().addOrDrop(player.tone, (amount * 4));
        } else if (player.tone == player.ttwo && player.tone == player.tthree && player.tone != 0) {
            if (player.card == 30295) {
                for (Item allItem : PvmScrathCard.allItems()) {
                    if (allItem.getId() == player.tone && allItem.lootBroadcast != null) {
                        player.getInventory().remove(player.card, 1);
                        Broadcast.WORLD.sendNews("[SCRATCH] " + player.getName() + " has just gotten a " + ItemDef.get(player.tone).name + " from a Scratch Card!");
                    }
                    if (allItem.getId() == player.tone) {
                        player.getInventory().remove(player.card, 1);
                        amount = allItem.getAmount();
                    }
                }
            } else {
                for (Item allItem : DonScrathCard.allItems()) {
                    if (allItem.getId() == player.tone && allItem.lootBroadcast != null) {
                        player.getInventory().remove(player.card, 1);
                        Broadcast.WORLD.sendNews("[SCRATCH] " + player.getName() + " has just gotten a " + ItemDef.get(player.tone).name + " from a Scratch Card!");
                    }
                    if (allItem.getId() == player.tone) {
                        player.getInventory().remove(player.card, 1);
                        amount = allItem.getAmount();
                    }
                }
            }

            player.getInventory().addOrDrop(player.tone, amount);
        } else if (player.tfour == player.tfive && player.tfour == player.tsix && player.tfour != 0) {
            if (player.card == 30295) {
                for (Item allItem : PvmScrathCard.allItems()) {
                    if (allItem.getId() == player.tfour && allItem.lootBroadcast != null) {
                        player.getInventory().remove(player.card, 1);
                        Broadcast.WORLD.sendNews("[SCRATCH] " + player.getName() + " has just gotten a " + ItemDef.get(player.tfour).name + " from a Scratch Card!");
                    }
                    if (allItem.getId() == player.tfour) {
                        player.getInventory().remove(player.card, 1);
                        amount = allItem.getAmount();
                    }
                }
            } else {
                for (Item allItem : DonScrathCard.allItems()) {
                    if (allItem.getId() == player.tfour && allItem.lootBroadcast != null) {
                        player.getInventory().remove(player.card, 1);
                        Broadcast.WORLD.sendNews("[SCRATCH] " + player.getName() + " has just gotten a " + ItemDef.get(player.tfour).name + " from a Scratch Card!");
                    }
                    if (allItem.getId() == player.tfour) {
                        player.getInventory().remove(player.card, 1);
                        amount = allItem.getAmount();
                    }
                }
            }
            player.getInventory().addOrDrop(player.tfour, amount);
        } else if (player.tseven == player.teight && player.tseven == player.tnine && player.tseven != 0) {
            if (player.card == 30295) {
                for (Item allItem : PvmScrathCard.allItems()) {
                    if (allItem.getId() == player.tseven && allItem.lootBroadcast != null) {
                        player.getInventory().remove(player.card, 1);
                        Broadcast.WORLD.sendNews("[SCRATCH] " + player.getName() + " has just gotten a " + ItemDef.get(player.tseven).name + " from a Scratch Card!");
                    }
                    if (allItem.getId() == player.tseven) {
                        player.getInventory().remove(player.card, 1);
                        amount = allItem.getAmount();
                    }
                }
            } else {
                for (Item allItem : DonScrathCard.allItems()) {
                    if (allItem.getId() == player.tseven && allItem.lootBroadcast != null) {
                        player.getInventory().remove(player.card, 1);
                        Broadcast.WORLD.sendNews("[SCRATCH] " + player.getName() + " has just gotten a " + ItemDef.get(player.tseven).name + " from a Scratch Card!");
                    }
                    if (allItem.getId() == player.tseven) {
                        player.getInventory().remove(player.card, 1);
                        amount = allItem.getAmount();
                    }
                }
            }
            player.getInventory().addOrDrop(player.tseven, amount);
        } else if (player.tone == player.tfour && player.tone == player.tseven && player.tone != 0) {
            if (player.card == 30295) {
                for (Item allItem : PvmScrathCard.allItems()) {
                    if (allItem.getId() == player.tone && allItem.lootBroadcast != null) {
                        player.getInventory().remove(player.card, 1);
                        Broadcast.WORLD.sendNews("[SCRATCH] " + player.getName() + " has just gotten a " + ItemDef.get(player.tone).name + " from a Scratch Card!");
                    }
                    if (allItem.getId() == player.tone) {
                        player.getInventory().remove(player.card, 1);
                        amount = allItem.getAmount();
                    }
                }
            } else {
                for (Item allItem : DonScrathCard.allItems()) {
                    if (allItem.getId() == player.tone && allItem.lootBroadcast != null) {
                        player.getInventory().remove(player.card, 1);
                        Broadcast.WORLD.sendNews("[SCRATCH] " + player.getName() + " has just gotten a " + ItemDef.get(player.tone).name + " from a Scratch Card!");
                    }
                    if (allItem.getId() == player.tone) {
                        player.getInventory().remove(player.card, 1);
                        amount = allItem.getAmount();
                    }
                }
            }
            player.getInventory().addOrDrop(player.tone, amount);
        } else if (player.ttwo == player.tfive && player.ttwo == player.teight && player.ttwo != 0) {
            if (player.card == 30295) {
                for (Item allItem : PvmScrathCard.allItems()) {
                    if (allItem.getId() == player.ttwo && allItem.lootBroadcast != null) {
                        player.getInventory().remove(player.card, 1);
                        Broadcast.WORLD.sendNews("[SCRATCH] " + player.getName() + " has just gotten a " + ItemDef.get(player.ttwo).name + " from a Scratch Card!");
                    }
                    if (allItem.getId() == player.ttwo) {
                        player.getInventory().remove(player.card, 1);
                        amount = allItem.getAmount();
                    }
                }
            } else {
                for (Item allItem : DonScrathCard.allItems()) {
                    if (allItem.getId() == player.ttwo && allItem.lootBroadcast != null) {
                        player.getInventory().remove(player.card, 1);
                        Broadcast.WORLD.sendNews("[SCRATCH] " + player.getName() + " has just gotten a " + ItemDef.get(player.ttwo).name + " from a Scratch Card!");
                    }
                    if (allItem.getId() == player.ttwo) {
                        player.getInventory().remove(player.card, 1);
                        amount = allItem.getAmount();
                    }
                }
            }
            player.getInventory().addOrDrop(player.ttwo, amount);
        } else if (player.tthree == player.tsix && player.tthree == player.tnine && player.tthree != 0) {
            if (player.card == 30295) {
                for (Item allItem : PvmScrathCard.allItems()) {
                    if (allItem.getId() == player.tthree && allItem.lootBroadcast != null) {
                        player.getInventory().remove(player.card, 1);
                        Broadcast.WORLD.sendNews("[SCRATCH] " + player.getName() + " has just gotten a " + ItemDef.get(player.tthree).name + " from a Scratch Card!");
                    }
                    if (allItem.getId() == player.tthree) {
                        player.getInventory().remove(player.card, 1);
                        amount = allItem.getAmount();
                    }
                }
            } else {
                for (Item allItem : DonScrathCard.allItems()) {
                    if (allItem.getId() == player.tthree && allItem.lootBroadcast != null) {
                        player.getInventory().remove(player.card, 1);
                        Broadcast.WORLD.sendNews("[SCRATCH] " + player.getName() + " has just gotten a " + ItemDef.get(player.tthree).name + " from a Scratch Card!");
                    }
                    if (allItem.getId() == player.tthree) {
                        player.getInventory().remove(player.card, 1);
                        amount = allItem.getAmount();
                    }
                }
            }
            player.getInventory().addOrDrop(player.tthree, amount);
        } else if (player.tone == player.tfive && player.tone == player.tnine && player.tone != 0) {
            if (player.card == 30295) {
                for (Item allItem : PvmScrathCard.allItems()) {
                    if (allItem.getId() == player.tone && allItem.lootBroadcast != null) {
                        player.getInventory().remove(player.card, 1);
                        Broadcast.WORLD.sendNews("[SCRATCH] " + player.getName() + " has just gotten a " + ItemDef.get(player.tone).name + " from a Scratch Card!");
                    }
                    if (allItem.getId() == player.tone) {
                        player.getInventory().remove(player.card, 1);
                        amount = allItem.getAmount();
                    }
                }
            } else {
                for (Item allItem : DonScrathCard.allItems()) {
                    if (allItem.getId() == player.tone && allItem.lootBroadcast != null) {
                        player.getInventory().remove(player.card, 1);
                        Broadcast.WORLD.sendNews("[SCRATCH] " + player.getName() + " has just gotten a " + ItemDef.get(player.tone).name + " from a Scratch Card!");
                    }
                    if (allItem.getId() == player.tone) {
                        player.getInventory().remove(player.card, 1);
                        amount = allItem.getAmount();
                    }
                }
            }

            player.getInventory().addOrDrop(player.tone, amount);
        } else if (player.tthree == player.tfive && player.tthree == player.tseven && player.tthree != 0) {
            if (player.card == 30295) {
                for (Item allItem : PvmScrathCard.allItems()) {
                    if (allItem.getId() == player.tthree && allItem.lootBroadcast != null) {
                        player.getInventory().remove(player.card, 1);
                        Broadcast.WORLD.sendNews("[SCRATCH] " + player.getName() + " has just gotten a " + ItemDef.get(player.tthree).name + " from a Scratch Card!");
                    }
                    if (allItem.getId() == player.tthree) {
                        player.getInventory().remove(player.card, 1);
                        amount = allItem.getAmount();
                    }
                }
            } else {
                for (Item allItem : DonScrathCard.allItems()) {
                    if (allItem.getId() == player.tthree && allItem.lootBroadcast != null) {
                        player.getInventory().remove(player.card, 1);
                        Broadcast.WORLD.sendNews("[SCRATCH] " + player.getName() + " has just gotten a " + ItemDef.get(player.tthree).name + " from a Scratch Card!");
                    }
                    if (allItem.getId() == player.tthree) {
                        player.getInventory().remove(player.card, 1);
                        amount = allItem.getAmount();
                    }
                }
            }

            player.getInventory().addOrDrop(player.tthree, amount);
        } else if (player.tone != 0 && player.ttwo != 0 && player.tthree != 0 && player.tfour != 0 && player.tfive != 0 && player.tsix != 0 && player.tseven != 0 && player.teight != 0 && player.tnine != 0) {
            player.sendMessage(Color.DARK_RED, "[SCRATCHY] Unlucky, this time. Maybe next time?");
            player.getInventory().add(13307, 20000);
            player.getInventory().remove(player.card, 1);
        }
        player.unlock();

    }

    static {

        ItemAction.registerInventory(30295, "Scratch", (player1, item) -> {
            //PVM Card
            if (player1.isVisibleInterface(1015) && (!player1.player.tonel || !player1.ttwol || !player1.tthreel || !player1.tfourl || !player1.tfivel || !player1.tsixl || !player1.tsevenl || !player1.teightl || !player1.tninel)) {
                return;
            }
            if (player1.isVisibleInterface(1015)) {
                player1.closeInterfaces();
            }
            player1.card = item.getId();
            OpenCard(player1);
        });
        ItemAction.registerInventory(30294, "Scratch", (player1, item) -> {
            //Donator Card
            if (player1.isVisibleInterface(1015) && (!player1.tonel || !player1.ttwol || !player1.tthreel || !player1.tfourl || !player1.tfivel || !player1.tsixl || !player1.tsevenl || !player1.teightl || !player1.tninel)) {
                return;
            }
            if (player1.isVisibleInterface(1015)) {
                player1.closeInterfaces();
            }
            player1.card = item.getId();
            OpenCard(player1);
        });

        InterfaceHandler.register(1015, h -> {
            h.actions[13] = (DefaultAction) (player, option, slot, itemId) -> {
                player.closeInterfaces();
            };
            h.actions[21] = (DefaultAction) (player, option, slot, itemId) -> {
                if (player.tonel) {
                    return;
                }
                int itemid = 0;
                if (player.card == 30294) {
                    itemid = DonScrathCard.rollItem().getId();
                } else if (player.card == 30295) {
                    itemid = PvmScrathCard.rollItem().getId();
                }
                player.getPacketSender().sendItem(1015, 21, itemid, itemid == 995 ? 10000 : 1);
                player.tone = itemid;
                player.tonel = true;
                checkCard(player);
            };
            h.actions[22] = (DefaultAction) (player, option, slot, itemId) -> {
                if (player.ttwol)
                    return;
                int itemid = 0;
                if (player.card == 30294) {
                    itemid = DonScrathCard.rollItem().getId();
                } else if (player.card == 30295) {
                    itemid = PvmScrathCard.rollItem().getId();
                }
                player.getPacketSender().sendItem(1015, 22, itemid, itemid == 995 ? 10000 : 1);
                player.ttwo = itemid;
                player.ttwol = true;
                checkCard(player);
            };
            h.actions[23] = (DefaultAction) (player, option, slot, itemId) -> {
                if (player.tthreel)
                    return;
                int itemid = 0;
                if (player.card == 30294) {
                    itemid = DonScrathCard.rollItem().getId();
                } else if (player.card == 30295) {
                    itemid = PvmScrathCard.rollItem().getId();
                }
                player.getPacketSender().sendItem(1015, 23, itemid, itemid == 995 ? 10000 : 1);
                player.tthree = itemid;
                player.tthreel = true;
                checkCard(player);
            };
            h.actions[24] = (DefaultAction) (player, option, slot, itemId) -> {
                if (player.tfourl)
                    return;
                int itemid = 0;
                if (player.card == 30294) {
                    itemid = DonScrathCard.rollItem().getId();
                } else if (player.card == 30295) {
                    itemid = PvmScrathCard.rollItem().getId();
                }
                player.getPacketSender().sendItem(1015, 24, itemid, itemid == 995 ? 10000 : 1);
                player.tfour = itemid;
                player.tfourl = true;
                checkCard(player);
            };
            h.actions[25] = (DefaultAction) (player, option, slot, itemId) -> {
                if (player.tfivel)
                    return;
                int itemid = 0;
                if (player.card == 30294) {
                    itemid = DonScrathCard.rollItem().getId();
                } else if (player.card == 30295) {
                    itemid = PvmScrathCard.rollItem().getId();
                }
                player.getPacketSender().sendItem(1015, 25, itemid, itemid == 995 ? 10000 : 1);
                player.tfive = itemid;
                player.tfivel = true;
                checkCard(player);
            };
            h.actions[26] = (DefaultAction) (player, option, slot, itemId) -> {
                if (player.tsixl)
                    return;
                int itemid = 0;
                if (player.card == 30294) {
                    itemid = DonScrathCard.rollItem().getId();
                } else if (player.card == 30295) {
                    itemid = PvmScrathCard.rollItem().getId();
                }
                player.getPacketSender().sendItem(1015, 26, itemid, itemid == 995 ? 10000 : 1);
                player.tsix = itemid;
                player.tsixl = true;
                checkCard(player);
            };
            h.actions[27] = (DefaultAction) (player, option, slot, itemId) -> {
                if (player.tsevenl)
                    return;
                int itemid = 0;
                if (player.card == 30294) {
                    itemid = DonScrathCard.rollItem().getId();
                } else if (player.card == 30295) {
                    itemid = PvmScrathCard.rollItem().getId();
                }
                player.getPacketSender().sendItem(1015, 27, itemid, itemid == 995 ? 10000 : 1);
                player.tseven = itemid;
                player.tsevenl = true;
                checkCard(player);
            };
            h.actions[28] = (DefaultAction) (player, option, slot, itemId) -> {
                if (player.teightl)
                    return;
                int itemid = 0;
                if (player.card == 30294) {
                    itemid = DonScrathCard.rollItem().getId();
                } else if (player.card == 30295) {
                    itemid = PvmScrathCard.rollItem().getId();
                }
                player.getPacketSender().sendItem(1015, 28, itemid, itemid == 995 ? 10000 : 1);
                player.teight = itemid;
                player.teightl = true;
                checkCard(player);
            };
            h.actions[29] = (DefaultAction) (player, option, slot, itemId) -> {
                if (player.tninel)
                    return;
                int itemid = 0;
                if (player.card == 30294) {
                    itemid = DonScrathCard.rollItem().getId();
                } else if (player.card == 30295) {
                    itemid = PvmScrathCard.rollItem().getId();
                }
                player.getPacketSender().sendItem(1015, 29, itemid, itemid == 995 ? 10000 : 1);
                player.tnine = itemid;
                player.tninel = true;
                checkCard(player);
            };
            h.actions[30] = (DefaultAction) (player, option, slot, itemId) -> {
                if (player.tonel && player.ttwol && player.tthreel && player.tfourl && player.tfivel && player.tsixl && player.tsevenl && player.teightl && player.tninel) {
                    if (player.getInventory().count(player.card) > 0) {
                        player.closeInterfaces();
                        OpenCard(player);
                    } else {
                        player.sendMessage(Color.DARK_RED, "[SCATCHY] You need to use another ticket before you can get anymore rewards.");
                    }
                    return;
                }
                for (int i = 21; i < 30; i++) {
                    int itemid = 0;
                    if (player.card == 30294) {
                        itemid = DonScrathCard.rollItem().getId();
                    } else if (player.card == 30295) {
                        itemid = PvmScrathCard.rollItem().getId();
                    }

                    int amount = itemid == 995 ? 10000 : 1;
                    switch (i) {
                        case 21:
                            if (!player.tonel) {
                                player.tone = itemid;
                                player.tonel = true;
                                player.getPacketSender().sendItem(1015, 21, itemid, amount);
                            }
                            break;
                        case 22:
                            if (!player.ttwol) {
                                player.ttwo = itemid;
                                player.ttwol = true;
                                player.getPacketSender().sendItem(1015, 22, itemid, amount);
                            }
                            break;
                        case 23:
                            if (!player.tthreel) {
                                player.tthree = itemid;
                                player.tthreel = true;
                                player.getPacketSender().sendItem(1015, 23, itemid, amount);
                            }
                            break;
                        case 24:
                            if (!player.tfourl) {
                                player.tfour = itemid;
                                player.tfourl = true;
                                player.getPacketSender().sendItem(1015, 24, itemid, amount);
                            }
                            break;
                        case 25:
                            if (!player.tfivel) {
                                player.tfive = itemid;
                                player.tfivel = true;
                                player.getPacketSender().sendItem(1015, 25, itemid, amount);
                            }
                            break;
                        case 26:
                            if (!player.tsixl) {
                                player.tsix = itemid;
                                player.tsixl = true;
                                player.getPacketSender().sendItem(1015, 26, itemid, amount);
                            }
                            break;
                        case 27:
                            if (!player.tsevenl) {
                                player.tseven = itemid;
                                player.tsevenl = true;
                                player.getPacketSender().sendItem(1015, 27, itemid, amount);
                            }
                            break;
                        case 28:
                            if (!player.teightl) {
                                player.teight = itemid;
                                player.teightl = true;
                                player.getPacketSender().sendItem(1015, 28, itemid, amount);
                            }
                            break;
                        case 29:
                            if (!player.tninel) {
                                player.tnine = itemid;
                                player.tninel = true;
                                player.getPacketSender().sendItem(1015, 29, itemid, amount);
                            }
                            break;
                    }
                }
                checkCard(player);
            };

        });
    }


}

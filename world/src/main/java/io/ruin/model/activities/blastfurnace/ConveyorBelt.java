package io.ruin.model.activities.blastfurnace;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;

public class ConveyorBelt {

    public static boolean placeOresOnBelt(Player player, int objectId) {
        if (objectId != BlastFurnace.CONVEYOR_BELTS[0])
            return false;

        if (Random.get(100) == 1) {
            DriveBelt.breakDriveBelt(player);
            player.sendMessage("You cannot access the conveyor belt when the drive belt is broken!");
            return false;
        }

        if (player.isDriveBeltBroken()) {
            player.sendMessage("You cannot access the conveyor belt when the drive belt is broken!");
            return false;
        }

        /*if(!player.getInventory().contains(BlastFurnace.COAL_ORE_ID) && !player.getInventory().contains(BlastFurnace.SMITHABLE_ORE_IDS)) {
            player.sendMessage("You need either coal ores or a mixture of coal and another type of ore.");
            return false;
        }*/

        /*if(player.getInventory().contains(BlastFurnace.COAL_ORE_ID) && !player.getInventory().contains(BlastFurnace.SMITHABLE_ORE_IDS)) {
            int coalOreAmount = player.getInventory().getAmount(BlastFurnace.COAL_ORE_ID);
            int totalCoalOreAmount = player.getBlastFurnaceCoalAmount();
            int newCoalAmount = totalCoalOreAmount + coalOreAmount;

            player.getInventory().remove(BlastFurnace.COAL_ORE_ID, coalOreAmount);

           /* BlastFurnace.cycleEvent = new CycleEvent() {

                NPC coalOre = World.getWorld().getNpcHandler()
                        .spawnNpc(player, BlastFurnace.ORE_NPC_IDS[0], 1942, 4966, 0, 0, 0, 0, 0, 0, false, false);

                @Override
                public void execute(CycleEventContainer container) {

                    if (coalOre.getLocation().getY() == 4964) {
                        player.setBlastFurnaceCoalAmount(player.getBlastFurnaceCoalAmount() + coalOreAmount);
                        player.sendMessage("You add " + coalOreAmount + " coal ores, you now have a total of " + player.getBlastFurnaceCoalAmount() + " in the furnace.");
                        coalOre.remove();
                        container.stop();
                        return;
                    }

                    //TODO: Fix the walking, at the moment it wont move.
                    //coalOre.moveX = 0;
                    //coalOre.moveY = -1;
                   // coalOre.getNextNPCMovement();
                   // coalOre.updateRequired = true;
                    return;

                }
            };
            //BlastFurnace.cycleEventContainer = CycleEventHandler.getSingleton().addEvent(BlastFurnace.class, BlastFurnace.cycleEvent, Misc.toCycles(4, TimeUnit.SECONDS));
            return true;
        }*/
       /* if(!player.getInventory().contains(BlastFurnace.COAL_ORE_ID) && player.getInventory().contains(BlastFurnace.SMITHABLE_ORE_IDS)) {
            //int currentOre = player.getInventory().getAmount(BlastFurnace.SMITHABLE_ORE_IDS).getId();

           // int currentOreId = BlastFurnace.Ore.getOre(currentOre).getNpcId();

            //int specialOreAmount = player.getInventory().getAmount(currentOre);
            int totalSpecialOreAmount = player.getBlastFurnaceOres();
            //int newSpecialAmount = totalSpecialOreAmount + specialOreAmount;

            /*if(player.getCurrentBlastFurnaceOre() != BlastFurnace.Ore.getOre(currentOre) && player.getBlastFurnaceOres() > 0) {
                player.sendMessage("You cannot mix ores! You're already smelting " + player.getBlastFurnaceOres() + " " + player.getCurrentBlastFurnaceOre().name().toLowerCase() + " ores!");
                return false;
            }*/

        //player.getInventory().remove(currentOre, specialOreAmount);

            /*BlastFurnace.cycleEvent = new CycleEvent() {

                NPC specialOre = World.getWorld().getNpcHandler().spawnNpc(player,
                        currentOreId,
                        1942,
                        4966,
                        0, 0, 0, 0, 0, 0, false, false);

                @Override
                public void execute(CycleEventContainer container) {

                    if (specialOre.getLocation().getY() == 4964) {
                        specialOre.remove();
                        player.setCurrentBlastFurnaceOre(BlastFurnace.Ore.getOre(currentOre));
                        player.setBlastFurnaceOres(player.getBlastFurnaceOres() + specialOreAmount);
                        player.sendMessage("You add " + specialOreAmount + " " + player.getCurrentBlastFurnaceOre().name().toLowerCase() + " ores, you now have a total of " + player.getBlastFurnaceOres() + " in the furnace.");
                        container.stop();
                        return;
                    }

                    //TODO: Fix the walking, at the moment it wont move.
                    specialOre.moveX = 0;
                    specialOre.moveY = -1;
                    specialOre.getNextNPCMovement();
                    specialOre.updateRequired = true;
                    return;

                }
            };
           // BlastFurnace.cycleEventContainer = CycleEventHandler.getSingleton().addEvent(BlastFurnace.class, BlastFurnace.cycleEvent, Misc.toCycles(4, TimeUnit.SECONDS));
            return true;
        }*/
       /* if(player.getInventory().contains(BlastFurnace.COAL_ORE_ID) && player.getItems().containsAnyItem(BlastFurnace.SMITHABLE_ORE_IDS)) {

           // int currentOre = player.getItems().getAnyOfItems(BlastFurnace.SMITHABLE_ORE_IDS).getId();

            //int currentOreId = BlastFurnace.Ore.getOre(currentOre).getNpcId();

            int coalOreAmount = player.getInventory().getAmount(BlastFurnace.COAL_ORE_ID);
            int totalCoalOreAmount = player.getBlastFurnaceCoalAmount();
            int newCoalAmount = totalCoalOreAmount + coalOreAmount;

            int specialOreAmount = player.getInventory().getAmount(currentOre);
            int totalSpecialOreAmount = player.getBlastFurnaceOres();
            int newSpecialAmount = totalSpecialOreAmount + specialOreAmount;

            if(player.getCurrentBlastFurnaceOre() != BlastFurnace.Ore.getOre(currentOre) && player.getBlastFurnaceOres() > 0) {
                player.sendMessage("You cannot mix ores! You're already smelting " + player.getBlastFurnaceOres() + " " + player.getCurrentBlastFurnaceOre().name().toLowerCase() + " ores!");
                return false;
            }

            player.getInventory().remove(BlastFurnace.COAL_ORE_ID, coalOreAmount);
            player.getInventory().remove(currentOre, specialOreAmount);

            BlastFurnace.cycleEvent = new CycleEvent() {

                NPC specialOre = World.getWorld().getNpcHandler().spawnNpc(player,
                        currentOreId,
                        1942,
                        4966,
                        0, 0, 0, 0, 0, 0, false, false);

                NPC coalOre = World.getWorld().getNpcHandler().spawnNpc(player,
                        BlastFurnace.ORE_NPC_IDS[0],
                        1942,
                        4966,
                        0, 0, 0, 0, 0, 0, false, false);

                boolean firstStep = true;

                @Override
                public void execute(CycleEventContainer container) {

                    if (coalOre.getLocation().getY() == 4964) {
                        player.setCurrentBlastFurnaceOre(BlastFurnace.Ore.getOre(currentOre));
                        player.setBlastFurnaceOres(player.getBlastFurnaceOres() + specialOreAmount);
                        player.sendMessage("You add " + specialOreAmount + " " + player.getCurrentBlastFurnaceOre().name().toLowerCase() + " ores, you now have a total of " + player.getBlastFurnaceOres() + " " + player.getCurrentBlastFurnaceOre().name().toLowerCase() + " ores in the furnace.");
                        coalOre.remove();
                        return;
                    }
                    if (specialOre.getLocation().getY() == 4964) {
                        specialOre.remove();
                        player.setBlastFurnaceCoalAmount(player.getBlastFurnaceCoalAmount() + coalOreAmount);
                        player.sendMessage("You add " + coalOreAmount + " coal ores, you now have a total of " + player.getBlastFurnaceCoalAmount() + " in the furnace.");
                        container.stop();
                        return;
                    }

                    if(firstStep) {
                        //TODO: Fix the walking, at the moment it wont move.
                        coalOre.moveX = 0;
                        coalOre.moveY = -1;
                        coalOre.getNextNPCMovement();
                        coalOre.updateRequired = true;

                        firstStep = false;
                    } else {
                        specialOre.moveX = 0;
                        specialOre.moveY = -1;

                        coalOre.moveX = 0;
                        coalOre.moveY = -1;

                        coalOre.getNextNPCMovement();
                        specialOre.getNextNPCMovement();

                        coalOre.updateRequired = true;
                        specialOre.updateRequired = true;
                    }

                    return;

                }
            };
            BlastFurnace.cycleEventContainer = CycleEventHandler.getSingleton().addEvent(BlastFurnace.class, BlastFurnace.cycleEvent, Misc.toCycles(4, TimeUnit.SECONDS));
            return true;
        }*/

        return false;
    }
}

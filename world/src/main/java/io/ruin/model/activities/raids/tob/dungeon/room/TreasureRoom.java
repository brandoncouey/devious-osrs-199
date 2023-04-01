package io.ruin.model.activities.raids.tob.dungeon.room;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.activities.raids.tob.party.TheatreParty;
import io.ruin.model.activities.raids.tob.party.TheatrePartyManager;
import io.ruin.model.activities.raids.xeric.ChambersOfXeric;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.inter.utils.Unlock;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.Position;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.services.Loggers;
import io.ruin.utility.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author NuLL on 28/12/2021
 * https://www.rune-server.ee/members/null1001/
 * @project Devious
 */
public class TreasureRoom extends TheatreRoom {//Room 7

    private static final int PURP_CHEST_CHANCE = 99;


    public TreasureRoom(TheatreParty party) {
        super(party);
    }

    @Override
    public void onLoad() {
        build(12867, 1);
        GameObject[] CHESTS = new GameObject[] {
                new GameObject(32990, convertX(3240), convertY(4323), 0, 10, 1),
                new GameObject(32990, convertX(3240), convertY(4328), 0, 10, 1),
                new GameObject(32990, convertX(3226), convertY(4327), 0, 10, 3),
                new GameObject(32990, convertX(3226), convertY(4323), 0, 10, 3),
                new GameObject(32990, convertX(3233), convertY(4331), 0, 10, 0),
        };
        GameObject[] PURP_CHESTS = new GameObject[] {
                new GameObject(32991, convertX(3240), convertY(4323), 0, 10, 1),
                new GameObject(32991, convertX(3240), convertY(4328), 0, 10, 1),
                new GameObject(32991, convertX(3226), convertY(4327), 0, 10, 3),
                new GameObject(32991, convertX(3226), convertY(4323), 0, 10, 3),
                new GameObject(32991, convertX(3233), convertY(4331), 0, 10, 0),
        };
        int purpleChest = 0;
        for (int index = 0; index < party.getUsers().size(); index++) {
            GameObject chest = CHESTS[index];
            if ((Utils.random(0, 100) < PURP_CHEST_CHANCE) && purpleChest == 0) {
                purpleChest++;
                chest = PURP_CHESTS[index];
            }
            Player player = World.getPlayer(party.getUsers().get(index));
            if (player != null) {
                player.getPacketSender().sendHintIcon(chest.getPosition().translate(1, 1));
                chest.ownerId = player.getUserId();
                chest.onlyOwnerUse = true;
            }
            chest.spawn();
        }
        List<String> raidNames = new LinkedList<>();
        for (Integer userId : party.getUsers()) {
            Optional<Player> rp = World.getPlayerByUid(userId);
            rp.ifPresent(player -> raidNames.add(player.getName()));
        }
        ServerLog.log(ServerLog.Type.TOB_RAIDS_COMPLETION, "Players=" + raidNames + ", Elapsed=" + (System.currentTimeMillis() - party.getTickCreatedOn()) + "ms");
    }

    @Override
    public void registerObjects() {
        ObjectAction.register(32996, "use", (player, obj) -> {
            Optional<TheatreParty> tobparty = TheatrePartyManager.instance().getPartyForPlayer(player.getUserId());
            if (tobparty.isEmpty()) return;
            if (player.getUserId() == tobparty.get().getLeaderId() && tobparty.get().getUsers().size() > 1) {
                player.dialogue(new MessageDialogue("Has everyone claimed there loot, if not they will loose it!"),
                        new OptionsDialogue(
                                new Option("Yes.", () -> {
                                    tobparty.get().forPlayers(p -> {
                                        p.getMovement().teleport(3665, 3219, 0);
                                        p.theatreOfBloodStage = 0;
                                        p.tobreward = false;
                                    });
                                    TheatrePartyManager.instance().deregister(tobparty.get());
                                }),
                                new Option("No.")));
            } else if (player.getUserId() == tobparty.get().getLeaderId()) {
                tobparty.get().forPlayers(p -> {
                    p.getMovement().teleport(3665, 3219, 0);
                    p.theatreOfBloodStage = 0;
                    p.tobreward = false;
                });
                TheatrePartyManager.instance().deregister(tobparty.get());
            } else {
                tobparty.get().leave(player.getUserId(), false);
                player.getMovement().teleport(3665, 3219, 0);
                player.theatreOfBloodStage = 0;
                player.tobreward = false;
            }

        });

        ObjectAction.register(33016, "check", (player, obj) -> { // reward chest
            for (int i = 0; i < 2; i++) {
                Item rolled = rollRandom();
                int amount = rolled.getAmount();
                if (amount > 1 && !rolled.getDef().stackable && !rolled.getDef().isNote())
                    rolled.setId(rolled.getDef().notedId);

                player.getInventory().addOrDrop(rolled.getId(), rolled.getAmount());
                PlayerLog.log(PlayerLog.Type.TOB_REWARDS, player.getName(), "Collected Reward " + rolled + ".");
            }
            obj.setId(35836);
        });

        ObjectAction.register(35836, "search", (player, obj) -> {
            player.dialogue(new MessageDialogue("The chest is empty."));
        });

        ObjectAction.register(35836, "shut", (player, obj) -> {
            player.dialogue(new MessageDialogue("The chest seems to be stuck now."));
        });

        ObjectAction.register(32994, "search", (player, obj) -> {
            player.dialogue(new MessageDialogue("The chest is empty, I wonder what was once inside."));
        });

        ObjectAction.register(32990, "open", (player, obj) -> { // reward chest
            if ((player.getUserId() != obj.ownerId) && obj.onlyOwnerUse) {
                player.dialogue(new MessageDialogue("This is not your chest!"));
                return;
            }
            if (player.tobreward) {
                player.dialogue(new MessageDialogue("It would seem you have already obtained your reward from this chest."));
                return;
            }
            for (int i = 0; i < 2; i++) {
                Item rolled = rollRegular();
                int amount = rolled.getAmount();
                if (amount > 1 && !rolled.getDef().stackable && !rolled.getDef().isNote())
                    rolled.setId(rolled.getDef().notedId);

                player.getInventory().addOrDrop(rolled.getId(), rolled.getAmount());
                player.sendMessage("<shad=000000>" + Color.COOL_BLUE.wrap("You got " + rolled.getDef().name + " X " + rolled.getAmount()) + "</shad>");
                if (rolled.getId() == 24420 || rolled.getId() == 24421 || rolled.getId() == 24419 || rolled.getId() == 24417 || rolled.getId() == 24514 || rolled.getId() == 24517 || rolled.getId() == 24511 || rolled.getId() == 24422) {
                    Broadcast.GLOBAL.sendNews("<shad=000000>" + Color.ORANGE.wrap(player.getName() + " has just received " + rolled.getDef().name + " from Theatre Of Blood!" + "</shad>"));
                }
            }
            player.getPacketSender().resetHintIcon(false);
            player.tobreward = true;
            obj.setId(32994);
        });
        ObjectAction.register(32991, "open", (player, obj) -> { // purple reward chest
            if ((player.getUserId() != obj.ownerId) && obj.onlyOwnerUse) {
                player.dialogue(new MessageDialogue("This is not your chest!"));
                return;
            }
            if (player.tobreward) {
                player.dialogue(new MessageDialogue("It would seem you have already obtained your reward from this chest."));
                return;
            }
            for (int roll = 0; roll < 2; roll++) {
                Item reward = null;
                if (roll == 0)
                    reward = rollRare();
                else
                    reward = rollRegular();
                if (reward != null) {
                    int amount = reward.getAmount();
                    if (amount > 1 && !reward.getDef().stackable && !reward.getDef().isNote())
                        reward.setId(reward.getDef().notedId);
                    player.getInventory().addOrDrop(reward.getId(), reward.getAmount());
                    player.sendMessage("<shad=000000>" + Color.COOL_BLUE.wrap("You got " + reward.getDef().name + " X " + reward.getAmount()) + "</shad>");
                    if (reward.getId() == 24420 || reward.getId() == 24421 || reward.getId() == 24419 || reward.getId() == 24417 || reward.getId() == 24514 || reward.getId() == 24517 || reward.getId() == 24511 || reward.getId() == 24422) {
                        Broadcast.GLOBAL.sendNews("<shad=000000>" + Color.ORANGE.wrap(player.getName() + " has just received " + reward.getDef().name + " from Theatre Of Blood!" + "</shad>"));
                    }
                }
            }
            player.getPacketSender().resetHintIcon(false);
            player.tobreward = true;
            obj.setId(32994);
        });
    }

    @Override
    public List<Position> getSpectatorSpots() {
        return null;
    }

    @Override
    public Position getEntrance() {
        return Position.of(3237, 4306, 0);
    }

    public static LootTable regularTable = new LootTable()
            .addTable(20,
                    new LootItem(1320, Random.get(2, 5), 3), //Rune 2h sword
                    new LootItem(1276, Random.get(2, 5), 3), //Rune pickaxe
                    new LootItem(1304, Random.get(2, 5), 2), //Rune longsword
                    new LootItem(1290, Random.get(2, 5), 2), //Rune sword
                    new LootItem(5316, Random.get(3, 5), 3), //magic seed

                    new LootItem(2, 3000, 3), //cannonballs x3000
                    new LootItem(6686, 50, 3), //saradomin brew noted x50
                    new LootItem(12696, 25, 2), //super combat pooition noted x25
                    new LootItem(6199, 1, 2), //mystery box
                    new LootItem(5730, 1, 3) //dragon spear
            )
            .addTable(20,
                    new LootItem(1128, 5, 3),
                    new LootItem(4088, Random.get(2, 3), 3),
                    new LootItem(22804, Random.get(50, 125), 3),
                    new LootItem(23648, Random.get(50, 125), 3)
            )
            .addTable(20,
                    new LootItem(561, Random.get(600, 700), 5),
                    new LootItem(21880, Random.get(300, 500), 5),
                    new LootItem(563, Random.get(500, 600), 5),
                    new LootItem(565, Random.get(500, 700), 5),
                    new LootItem(560, Random.get(600, 700), 5)
            )
            .addTable(20,
                    new LootItem(450, Random.get(25, 50), 4),
                    new LootItem(452, Random.get(15, 20), 4),
                    new LootItem(454, Random.get(115, 120), 3)
            )
            .addTable(20,
                    new LootItem(995, Random.get(200000, 250000), 3),
                    new LootItem(5300, 1, 3),
                    new LootItem(1514, Random.get(15, 20), 3),
                    new LootItem(3024, 3, 2),
                    new LootItem(3052, 3, 2),
                    new LootItem(10506, 5, 50, 5),

                    new LootItem(12073, 1, 2)
            );

    public static LootTable rareTable = new LootTable()
            .addTable(1,
                    new LootItem(24420, 1, 2),
                    new LootItem(24421, 1, 2),
                    new LootItem(24419, 1, 2),
                    new LootItem(24417, 1, 2)
            )
            .addTable(1,
                    new LootItem(24514, 1, 2),
                    new LootItem(24517, 1, 2),
                    new LootItem(24511, 1, 2),
                    new LootItem(24422, 1, 4)
            );


    private static Item rollRegular() {
        return regularTable.rollItem();
    }

    private static Item rollRare() {
        return rareTable.rollItem();
    }


    private static LootTable rollRandom = new LootTable()
            .addTable(20,
                    new LootItem(1320, Random.get(2, 5), 3), //Rune 2h sword
                    new LootItem(1276, Random.get(2, 5), 3), //Rune pickaxe
                    new LootItem(1304, Random.get(2, 5), 2), //Rune longsword
                    new LootItem(1290, Random.get(2, 5), 2), //Rune sword
                    new LootItem(5316, Random.get(3, 5), 3), //magic seed

                    new LootItem(2, 3000, 3), //cannonballs x3000
                    new LootItem(6686, 50, 3), //saradomin brew noted x50
                    new LootItem(12696, 25, 2), //super combat pooition noted x25
                    new LootItem(6199, 1, 2), //mystery box
                    new LootItem(5730, 1, 3) //dragon spear
            );

    private static Item rollRandom() {
        return rollRandom.rollItem();
    }

}

package io.ruin.model.activities.raids.tob.dungeon.room;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.activities.raids.tob.party.PartyStatus;
import io.ruin.model.activities.raids.tob.party.TheatreParty;
import io.ruin.model.activities.raids.tob.party.TheatrePartyManager;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.XpMode;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Utils;

import java.util.List;

import static io.ruin.model.World.weekendExpBoost;

/**
 * @author NuLL on 28/12/2021
 * https://www.rune-server.ee/members/null1001/
 * @project Devious
 */
public class TreasureRoom extends TheatreRoom {  ///Room 7

    private static final int PURP_CHEST_CHANCE = 5;

    public static int purpleChest = 0;

    public TreasureRoom(TheatreParty party) {
        super(party);
    }

    @Override
    public void onLoad() {
        build(12867, 1);
        GameObject[] CHESTS = new GameObject[]{
                new GameObject(32990, convertX(3240), convertY(4323), 0, 10, 1),
                new GameObject(32990, convertX(3240), convertY(4328), 0, 10, 1),
                new GameObject(32990, convertX(3226), convertY(4327), 0, 10, 3),
                new GameObject(32990, convertX(3226), convertY(4323), 0, 10, 3),
                new GameObject(32990, convertX(3233), convertY(4331), 0, 10, 0),
        };
        GameObject[] PURP_CHESTS = new GameObject[]{
                new GameObject(32991, convertX(3240), convertY(4323), 0, 10, 1),
                new GameObject(32991, convertX(3240), convertY(4328), 0, 10, 1),
                new GameObject(32991, convertX(3226), convertY(4327), 0, 10, 3),
                new GameObject(32991, convertX(3226), convertY(4323), 0, 10, 3),
                new GameObject(32991, convertX(3233), convertY(4331), 0, 10, 0),
        };
        party.getUsers().forEach(u -> {
            System.out.println("u.toString() = " + u.toString());
        });
        for (int index = 0; index <= party.getUsers().size() - 1; index++) {
            Player player = World.getPlayer(party.getUsers().get(index));
            int userId = TheatrePartyManager.instance().forUserId(party.getUsers().get(index)).get().getUserId();
            GameObject chest = CHESTS[index];
            chest.ownerId = userId;
            //  System.out.println("I spwned the chest for non purp " + userId);
            chest.onlyOwnerUse = true;
            if (player.lootit && Utils.random(0, 6) < PURP_CHEST_CHANCE) {
                purpleChest++;
                chest = PURP_CHESTS[index];
                chest.ownerId = userId;
                chest.onlyOwnerUse = true;
            }
         if (Utils.random(0, 100) < PURP_CHEST_CHANCE && player.tobDamage > 100) {
                    purpleChest++;
                    chest = PURP_CHESTS[index];
                    chest.ownerId = userId;
                    chest.onlyOwnerUse = true;
                    //  System.out.println("I spawned the chest for purple " + userId);
                }
                if (player != null) {
                    player.getPacketSender().sendHintIcon(chest.getPosition().translate(1, 1));
                }
                chest.spawn();
        }
    }

    @Override
    public void registerObjects() {
        ObjectAction.register(32996, "use", (player, obj) -> {
            TheatreParty tobparty = TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).get();
            if (player.getUserId() == tobparty.getLeaderId() && tobparty.getUsers().size() > 1) {
                player.dialogue(new MessageDialogue("Has everyone claimed there loot, if not they will loose it!"),
                        new OptionsDialogue(
                                new Option("Yes.", () -> {
                                    tobparty.forPlayers(p -> {
                                        p.sendMessage("You Were rewarded 50 Raid points, you now have " + p.raidPoints);
                                        p.raidPoints += 50;
                                        p.deathEndListener = null;
                                        p.getMovement().teleport(3665, 3219, 0);
                                        TheatreParty.updatePartyStatus(p, PartyStatus.NO_PARTY);
                                        p.theatreOfBloodStage = 0;
                                        p.tobDamage = 0;
                                        p.getCombat().setDead(false);
                                        p.unlock();
                                        p.theatreroom = "";
                                        if (p.getInventory().contains(25961))
                                            p.getInventory().findItem(25961).remove();
                                        p.tobreward = false;
                                    });
                                    TheatrePartyManager.instance().deregister(tobparty);
                                }),
                                new Option("No.")));
            } else if (player.getUserId() == tobparty.getLeaderId()) {
                tobparty.forPlayers(p -> {
                    p.sendMessage("You Were rewarded 50 Raid points, you now have " + p.raidPoints);
                    p.raidPoints += 50;
                    p.getMovement().teleport(3665, 3219, 0);
                    p.deathEndListener = null;
                    p.getCombat().setDead(false);
                    p.tobDamage = 0;
                    p.unlock();
                    if (p.getInventory().contains(25961))
                        p.getInventory().findItem(25961).remove();
                    TheatreParty.updatePartyStatus(p, PartyStatus.NO_PARTY);
                    p.theatreOfBloodStage = 0;
                    p.theatreroom = "";
                    p.tobreward = false;
                });
                TheatrePartyManager.instance().deregister(tobparty);
            } else {
                player.sendMessage("You Were rewarded 50 Raid points, you now have " + player.raidPoints);
                player.raidPoints += 50;
                tobparty.leave(player.getUserId(), false);
                player.deathEndListener = null;
                player.getMovement().teleport(3665, 3219, 0);
                if (player.getInventory().contains(25961))
                    player.getInventory().findItem(25961).remove();
                TheatreParty.updatePartyStatus(player, PartyStatus.NO_PARTY);
                player.theatreOfBloodStage = 0;
                player.getCombat().setDead(false);
                player.tobDamage = 0;
                player.unlock();
                player.theatreroom = "";
                player.tobreward = false;
            }

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

            if (player.getUserId() != obj.ownerId && obj.onlyOwnerUse) {
                player.dialogue(new MessageDialogue("This is not your chest!"));
                return;
            }
            if (player.tobreward && obj.onlyOwnerUse) {
                player.dialogue(new MessageDialogue("It would seem you have already obtained your reward from this chest."));
                return;
            }
            for (int roll = 0; roll < 2; roll++) {
                Item reward;
                reward = rollRegular();
                if (reward != null) {
                    int amount = reward.getAmount();
                    if (World.doubleRaids && !World.isPVPWorld() || World.isPVPWorld())
                        amount++;
                    if (amount > 1 && !reward.getDef().stackable && !reward.getDef().isNote())
                        reward.setId(reward.getDef().notedId);
                    player.getInventory().addOrDrop(reward.getId(), amount);
                    player.sendMessage(Color.COOL_BLUE.wrap("You got " + reward.getDef().name + " X " + amount));
                    // if (rolled.getId() == 24420 || rolled.getId() == 24421 || rolled.getId() == 24419 || rolled.getId() == 24417 || rolled.getId() == 24514 || rolled.getId() == 24517 || rolled.getId() == 24511 || rolled.getId() == 24422) {
                    // Broadcast.GLOBAL.sendNews(Color.RAID_PURPLE.wrap("[RARE DROP] ") + player.getName() + " Has just received " + Color.DARK_RED.wrap(rolled.getDef().name) + " from Theatre Of Blood!");
                    //}
                }
            }
            player.getPacketSender().resetHintIcon(false);
            player.tobreward = true;
            System.out.println("I destroyed thischest " + player.getUserId() + " " + player.getName());
            obj.setId(32994);
        });
        ObjectAction.register(32991, "open", (player, obj) -> { // purple reward chest
            if (player.getUserId() != obj.ownerId && obj.onlyOwnerUse) {
                player.dialogue(new MessageDialogue("This is not your chest!"));
                return;
            }
            if (player.tobreward && obj.onlyOwnerUse) {
                player.dialogue(new MessageDialogue("It would seem you have already obtained your reward from this chest."));
                return;
            }
            for (int roll = 0; roll < 2; roll++) {
                Item reward;
                if (roll == 0)
                    reward = rollRare();
                else
                    reward = rollRegular();
                if (reward != null) {
                    int amount = reward.getAmount();
                    if (World.doubleRaids)
                        amount++;
                    if (amount > 1 && !reward.getDef().stackable && !reward.getDef().isNote())
                        reward.setId(reward.getDef().notedId);
                    player.getInventory().add(reward.getId(), amount);
                    player.sendMessage(Color.COOL_BLUE.wrap("You got " + reward.getDef().name + " X " + reward.getAmount()));
                    player.getCollectionLog().collect(reward.getId());
                    if (reward.getId() == 22477 || reward.getId() == 22326 || reward.getId() == 30350 ||  reward.getId() == 22327 || reward.getId() == 22328 || reward.getId() == 22324 || reward.getId() == 25744 || reward.getId() == 25742 || reward.getId() == 22481 || reward.getId() == 22486) {
                        Broadcast.GLOBAL.sendNews(Color.RAID_PURPLE.wrap(player.getName() +
                                " received " + reward.getDef().name) +
                                " from TOB at KC: " + player.theatreOfBloodKills.getKills());

                    }
                }
            }
            player.getPacketSender().resetHintIcon(false);
            //  System.out.println("I destroyed thischest2 " + player.getUserId() + " " + player.getName());

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
                    new LootItem(19484, Random.get(50, 125), 3)
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
                    new LootItem(22477, 1, 5),
                    new LootItem(22326, 1, 3),
                    new LootItem(22327, 1, 3),
                    new LootItem(22328, 1, 3),
                    new LootItem(22324, 1, 2),
                    new LootItem(30350, 1, 2)
            )
            .addTable(1,
                    new LootItem(25744, 1, 2),
                    new LootItem(25742, 1, 2),
                    new LootItem(22481, 1, 1),
                    new LootItem(22486, 1, 1)
            );


    private static Item rollRegular() {
        return regularTable.rollItem();
    }

    private static Item rollRare() {
        return rareTable.rollItem();
    }


    private static final LootTable rollRandom = new LootTable()
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
package io.ruin.model.item.containers;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.shared.Movement;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.map.ground.GroundItem;

import java.util.*;

public class Inventory extends ItemContainer {

    public double weight;

    public void addOrDrop(Item item) {
        addOrDrop(item.getId(), item.getAmount());
    }

    public void addOrDrop(int id) {
        addOrDrop(id, 1);
    }

    public void addOrDrop(int id, int amount) {
        if (add(id, amount) > 0) {
            /* added normally */
            return;
        }
        if (player.isAdmin()) {
            player.sendMessage("Not enough space to spawn item (" + id + ", " + amount + ")");
//            return;
        }
        if (player.getDuel().stage >= 4) {
            //player.sendMessage("You can't drop items in a duel.");
            return;
        }
        if (player.joinedTournament) {
            //player.sendMessage("You can't drop items while you're signed up for a tournament.");
            return;
        }
        Movement movement = player.getMovement();
        int x, y, z;
        if (movement.isTeleportQueued()) {
            x = movement.teleportX;
            y = movement.teleportY;
            z = movement.teleportZ;
        } else {
            x = player.getAbsX();
            y = player.getAbsY();
            z = player.getHeight();
        }
        new GroundItem(id, amount).owner(player).position(x, y, z).spawn();
    }

    public boolean containsAll(Item...items) {
        return containsAll(Arrays.stream(items).filter(Objects::nonNull).mapToInt(i -> i.getId()).toArray());
    }

    public boolean containsAll(int... ids) {
        Set<Integer> set = new HashSet<>();

        for (var item : items) {
            if (item == null) {
                continue;
            }

            for (var i : ids) {
                if (i == item.getId()) {
                    set.add(i);
                }
            }
        }

        return set.size() == ids.length;
    }

    @Override
    public boolean sendUpdates() {
        if (!super.sendUpdates())
            return false;
        weight = 0;
        for (Item item : items) {
            if (item == null) continue;
            ItemDef def = item.getDef();
            if (def == null) continue;
            weight += def.weightInventory;
        }
        return true;
    }

}
package io.ruin.model.inter.handlers;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.stat.StatType;

public class FossilIslandStorage {

    public static int remove;

    public static void fossilstorage(Player p) {

        for (Item item : p.getInventory().getItems()) {
            if (item != null)
                if (item.getId() == 21570) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.SMALL_FOSSLISED_LIMBS.increment(p, amount);
                } else if (item.getId() == 21572) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.SMALL_FOSSLISED_SPINE.increment(p, amount);
                } else if (item.getId() == 21574) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.SMALL_FOSSLISED_RIBS.increment(p, amount);
                } else if (item.getId() == 21576) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.SMALL_FOSSLISED_PELVIS.increment(p, amount);
                } else if (item.getId() == 21578) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.SMALL_FOSSLISED_SKULL.increment(p, amount);
                } else if (item.getId() == 21580) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.MEDIUM_FOSSLISED_LIMBS.increment(p, amount);
                } else if (item.getId() == 25182) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.MEDIUM_FOSSLISED_SPINE.increment(p, amount);
                } else if (item.getId() == 21584) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.MEDIUM_FOSSLISED_RIBS.increment(p, amount);
                } else if (item.getId() == 21586) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.MEDIUM_FOSSLISED_PELVIS.increment(p, amount);
                } else if (item.getId() == 21588) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.MEDIUM_FOSSLISED_SKULL.increment(p, amount);
                } else if (item.getId() == 21600) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.LARGE_FOSSLISED_LIMBS.increment(p, amount);
                } else if (item.getId() == 21602) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.LARGE_FOSSLISED_SPINE.increment(p, amount);
                } else if (item.getId() == 21604) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.LARGE_FOSSLISED_RIBS.increment(p, amount);
                } else if (item.getId() == 21606) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.LARGE_FOSSLISED_PELVIS.increment(p, amount);
                } else if (item.getId() == 21608) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.LARGE_FOSSLISED_SKULL.increment(p, amount);
                } else if (item.getId() == 21590) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.FOSSILISED_ROOTS.increment(p, amount);
                } else if (item.getId() == 21592) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.FOSSILISED_STUMP.increment(p, amount);
                } else if (item.getId() == 21594) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.FOSSILISED_BRANCH.increment(p, amount);
                } else if (item.getId() == 21596) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.FOSSILISED_LEAF.increment(p, amount);
                } else if (item.getId() == 21598) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.FOSSILISED_MUSHROOM.increment(p, amount);
                } else if (item.getId() == 21610) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.RARE_FOSSLISED_LIMBS.increment(p, amount);
                } else if (item.getId() == 21612) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.RARE_FOSSLISED_SPINE.increment(p, amount);
                } else if (item.getId() == 21614) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.RARE_FOSSLISED_RIBS.increment(p, amount);
                } else if (item.getId() == 21616) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.RARE_FOSSLISED_PELVIS.increment(p, amount);
                } else if (item.getId() == 21618) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.RARE_FOSSLISED_SKULL.increment(p, amount);
                } else if (item.getId() == 21620) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.RARE_FOSSLISED_TUSK.increment(p, amount);
                } else if (item.getId() == 21562) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.UNIDENTIFIED_SMALL_FOSSIL.increment(p, amount);
                } else if (item.getId() == 21564) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.UNIDENTIFIED_MEDIUM_FOSSIL.increment(p, amount);
                } else if (item.getId() == 21566) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.UNIDENTIFIED_LARGE_FOSSIL.increment(p, amount);
                } else if (item.getId() == 21568) {
                    int amount = item.getAmount();
                    item.remove(amount);
                    Config.UNIDENTIFIED_RARE_FOSSIL.increment(p, amount);
                }

        }
    }

    public static void withdrawStore(Player p, int item) {
        switch (item) {
            case 21570:
                remove = Math.min(Config.SMALL_FOSSLISED_LIMBS.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.SMALL_FOSSLISED_LIMBS.increment(p, -remove);
                break;
            case 21572:
                remove = Math.min(Config.SMALL_FOSSLISED_SPINE.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.SMALL_FOSSLISED_SPINE.increment(p, -remove);
                break;
            case 21574:
                remove = Math.min(Config.SMALL_FOSSLISED_RIBS.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.SMALL_FOSSLISED_RIBS.increment(p, -remove);
                break;
            case 21576:
                remove = Math.min(Config.SMALL_FOSSLISED_PELVIS.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.SMALL_FOSSLISED_PELVIS.increment(p, -remove);
                break;
            case 21578:
                remove = Math.min(Config.SMALL_FOSSLISED_SKULL.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.SMALL_FOSSLISED_SKULL.increment(p, -remove);
                break;
            case 21580:
                remove = Math.min(Config.MEDIUM_FOSSLISED_LIMBS.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.MEDIUM_FOSSLISED_LIMBS.set(p, -remove);
                break;
            case 21582:
                remove = Math.min(Config.MEDIUM_FOSSLISED_SPINE.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.MEDIUM_FOSSLISED_SPINE.increment(p, -remove);
                break;
            case 21584:
                remove = Math.min(Config.MEDIUM_FOSSLISED_RIBS.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.MEDIUM_FOSSLISED_RIBS.increment(p, -remove);
                break;
            case 21586:
                remove = Math.min(Config.MEDIUM_FOSSLISED_PELVIS.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.MEDIUM_FOSSLISED_PELVIS.increment(p, -remove);
                break;
            case 21588:
                remove = Math.min(Config.MEDIUM_FOSSLISED_SKULL.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.MEDIUM_FOSSLISED_SKULL.increment(p, -remove);
                break;
            case 21600:
                remove = Math.min(Config.LARGE_FOSSLISED_LIMBS.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.LARGE_FOSSLISED_LIMBS.increment(p, -remove);
                break;
            case 21602:
                remove = Math.min(Config.LARGE_FOSSLISED_SPINE.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.LARGE_FOSSLISED_SPINE.increment(p, -remove);
                break;
            case 21604:
                remove = Math.min(Config.LARGE_FOSSLISED_RIBS.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.LARGE_FOSSLISED_RIBS.increment(p, -remove);
                break;
            case 21606:
                remove = Math.min(Config.LARGE_FOSSLISED_PELVIS.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.LARGE_FOSSLISED_PELVIS.increment(p, -remove);
                break;
            case 21608:
                remove = Math.min(Config.LARGE_FOSSLISED_SKULL.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.LARGE_FOSSLISED_SKULL.increment(p, -remove);
                break;
            case 21590:
                remove = Math.min(Config.FOSSILISED_ROOTS.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.FOSSILISED_ROOTS.increment(p, -remove);
                break;
            case 21592:
                remove = Math.min(Config.FOSSILISED_STUMP.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.FOSSILISED_STUMP.increment(p, -remove);
                break;
            case 21594:
                remove = Math.min(Config.FOSSILISED_BRANCH.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.FOSSILISED_BRANCH.increment(p, -remove);
                break;
            case 21596:
                remove = Math.min(Config.FOSSILISED_LEAF.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.FOSSILISED_LEAF.increment(p, -remove);
                break;
            case 21598:
                remove = Math.min(Config.FOSSILISED_MUSHROOM.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.FOSSILISED_MUSHROOM.increment(p, -remove);
                break;
            case 21610:
                remove = Math.min(Config.RARE_FOSSLISED_LIMBS.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.RARE_FOSSLISED_LIMBS.increment(p, -remove);
                break;
            case 21612:
                remove = Math.min(Config.RARE_FOSSLISED_SPINE.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.RARE_FOSSLISED_SPINE.increment(p, -remove);
                break;
            case 21614:
                remove = Math.min(Config.RARE_FOSSLISED_RIBS.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.RARE_FOSSLISED_RIBS.increment(p, -remove);
                break;
            case 21616:
                remove = Math.min(Config.RARE_FOSSLISED_PELVIS.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.RARE_FOSSLISED_PELVIS.increment(p, -remove);
                break;
            case 21618:
                remove = Math.min(Config.RARE_FOSSLISED_SKULL.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.RARE_FOSSLISED_SKULL.increment(p, -remove);
                break;
            case 21620:
                remove = Math.min(Config.RARE_FOSSLISED_TUSK.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.RARE_FOSSLISED_TUSK.increment(p, -remove);
                break;
            case 21562:
                remove = Math.min(Config.UNIDENTIFIED_SMALL_FOSSIL.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.UNIDENTIFIED_SMALL_FOSSIL.increment(p, -remove);
                break;
            case 21564:
                remove = Math.min(Config.UNIDENTIFIED_MEDIUM_FOSSIL.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.UNIDENTIFIED_MEDIUM_FOSSIL.increment(p, -remove);
                break;
            case 21566:
                remove = Math.min(Config.UNIDENTIFIED_LARGE_FOSSIL.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.UNIDENTIFIED_LARGE_FOSSIL.increment(p, -remove);
                break;
            case 21568:
                remove = Math.min(Config.UNIDENTIFIED_RARE_FOSSIL.get(p), p.getInventory().getFreeSlots());
                p.getInventory().add(item, remove);
                Config.UNIDENTIFIED_RARE_FOSSIL.increment(p, -remove);
                break;
        }
    }

    static {
        ItemObjectAction.register(30973, (player, item, obj) -> {
            switch (item.getId()) {
                case 21570:
                case 21572:
                case 21574:
                case 21576:
                case 21578:
                    player.startEvent(e -> {
                        if (player.getInventory().hasMultiple(item.getId())) {
                            player.animate(899);
                            e.delay(3);
                            item.setId(21547);
                        }
                    });
                    break;
                case 21580:
                case 21588:
                case 21586:
                case 21584:
                case 21582:
                    player.startEvent(e -> {
                        if (player.getInventory().hasMultiple(item.getId())) {
                            player.animate(899);
                            e.delay(3);
                            item.setId(21549);
                        }
                    });
                    break;
                case 21600:
                case 21606:
                case 21608:
                case 21604:
                case 21602:
                    player.startEvent(e -> {
                        if (player.getInventory().hasMultiple(item.getId())) {
                            player.animate(899);
                            e.delay(3);
                            item.setId(21551);
                        }
                    });
                    break;
                case 21610:
                case 21620:
                case 21618:
                case 21616:
                case 21614:
                case 21612:
                    player.startEvent(e -> {
                        if (player.getInventory().hasMultiple(item.getId())) {
                            player.animate(899);
                            e.delay(3);
                            item.setId(21553);
                        }
                    });
                    break;
            }
        });

        ItemObjectAction.register(30945, (player, item, obj) -> {
            switch (item.getId()) {
                case 21547: {
                    if (item.getId() == 21547) {
                        player.startEvent(e -> {
                            player.lock();
                            obj.animate(7730);
                            player.animate(3705);
                            player.getStats().addXp(StatType.Prayer, 75 * 1.5, true);
                            item.remove();
                            e.delay(3);
                            player.unlock();
                        });
                    }
                }
                case 21549: {
                    if (item.getId() == 21549) {
                        player.startEvent(e -> {
                            player.lock();
                            obj.animate(7730);
                            player.animate(3705);
                            player.getStats().addXp(StatType.Prayer, 100 * 1.5, true);
                            item.remove();
                            e.delay(3);
                            player.unlock();
                        });
                    }
                }
                case 21551: {
                    if (item.getId() == 21551) {
                        player.startEvent(e -> {
                            player.lock();
                            obj.animate(7730);
                            player.animate(3705);
                            player.getStats().addXp(StatType.Prayer, 150 * 1.5, true);
                            item.remove();
                            e.delay(3);
                            player.unlock();
                        });
                    }
                }
                case 21553: {
                    if (item.getId() == 21553) {
                        player.startEvent(e -> {
                            player.lock();
                            obj.animate(7730);
                            player.animate(3705);
                            player.getStats().addXp(StatType.Prayer, 200 * 1.5, true);
                            item.remove();
                            e.delay(3);
                            player.unlock();
                        });
                    }
                }
            }
        });
    }
}

package io.ruin.model.shop;

import io.ruin.cache.ItemID;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Currency {
    COINS(new ItemCurrencyHandler(ItemID.COINS_995)),
    STARDUST(new ItemCurrencyHandler(25527)),
    BLOOD_MONEY(new ItemCurrencyHandler(ItemID.BLOOD_MONEY)),
    TOKKUL(new ItemCurrencyHandler(ItemID.TOKKUL)),
    MARKS_OF_GRACE(new ItemCurrencyHandler(ItemID.MARK_OF_GRACE)),
    EASTER_EGGS(new ItemCurrencyHandler(ItemID.EASTER_EGG)),
    MOLCH_PEARLS(new ItemCurrencyHandler(ItemID.MOLCH_PEARL)),
    PVP_TOKENS(new ItemCurrencyHandler(ItemID.SURVIVAL_TOKEN)),
    GOLDEN_NUGGETS(new ItemCurrencyHandler(ItemID.GOLDEN_NUGGET)),
    WARRIOR_GUILD_TOKEN(new ItemCurrencyHandler(ItemID.WARRIOR_GUILD_TOKEN)),
    DONATION_COINS(new ItemCurrencyHandler(30308)),
    VOTE_TICKETS(new ItemCurrencyHandler(30256)),
    UNIDENTIFIED_MINERALS(new ItemCurrencyHandler(ItemID.UNIDENTIFIED_MINERALS)),
    SLAYER_POINTS(new CurrencyHandler("Slayer Points") {
        @Override
        public int getCurrencyCount(Player player) {
            return Config.SLAYER_POINTS.get(player);
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            if (amount > Config.SLAYER_POINTS.get(player)) {
                return Config.SLAYER_POINTS.get(player);
            }
            Config.SLAYER_POINTS.decrement(player, amount);
            return amount;
        }

        @Override
        public int addCurrency(Player player, int amount) {
            if ((long) Config.SLAYER_POINTS.get(player) + (long) amount > Integer.MAX_VALUE) {
                Config.SLAYER_POINTS.set(player, Integer.MAX_VALUE);
            } else {
                Config.SLAYER_POINTS.increment(player, amount);
            }
            return amount;
        }
    }),
    AFK_TOKENS(new CurrencyHandler("Afk Points") {
        @Override
        public int getCurrencyCount(Player player) {
            return player.afkPoints;
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            if (amount > player.afkPoints) {
                return player.afkPoints;
            }
            player.afkPoints -= amount;
            return amount;
        }

        @Override
        public int addCurrency(Player player, int amount) {
            if ((long) player.afkPoints + (long) amount > Integer.MAX_VALUE) {
                player.afkPoints = Integer.MAX_VALUE;
            } else {
                player.afkPoints += amount;
            }
            return amount;
        }
    }),
    TASK_POINTS(new CurrencyHandler("Task Points") {
        @Override
        public int getCurrencyCount(Player player) {
            return player.dailyTaskPoints;
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            if (amount > player.dailyTaskPoints) {
                return player.dailyTaskPoints;
            }
            player.dailyTaskPoints -= amount;
            return amount;
        }

        @Override
        public int addCurrency(Player player, int amount) {
            if ((long) player.dailyTaskPoints + (long) amount > Integer.MAX_VALUE) {
                player.dailyTaskPoints = Integer.MAX_VALUE;
            } else {
                player.dailyTaskPoints += amount;
            }
            return amount;
        }
    }),
    EXCHANGE_POINTS(new CurrencyHandler("Exchange Points") {
        @Override
        public int getCurrencyCount(Player player) {
            return player.exchangePoints;
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            if (amount > player.exchangePoints) {
                return player.exchangePoints;
            }
            player.exchangePoints -= amount;
            return amount;
        }

        @Override
        public int addCurrency(Player player, int amount) {
            if ((long) player.exchangePoints + (long) amount > Integer.MAX_VALUE) {
                player.exchangePoints = Integer.MAX_VALUE;
            } else {
                player.exchangePoints += amount;
            }
            return amount;
        }
    }),
    WILDERNESS_SLAYER_POINTS(new CurrencyHandler("wilderness slayer points") {
        @Override
        public int getCurrencyCount(Player player) {
            return player.wildernessSlayerPoints;
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            if (amount > player.wildernessSlayerPoints) {
                return 0;
            }
            player.wildernessSlayerPoints -= amount;
            return amount;
        }

        @Override
        public int addCurrency(Player player, int amount) {
            if ((long) player.wildernessSlayerPoints + (long) amount > Integer.MAX_VALUE) {
                player.wildernessSlayerPoints = Integer.MAX_VALUE;
            } else {
                player.wildernessSlayerPoints += amount;
            }
            return amount;
        }
    }),
    MAGE_ARENA_POINTS(new CurrencyHandler("mage arena points") {
        @Override
        public int getCurrencyCount(Player player) {
            return player.mageArenaPoints;
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            if (amount > player.mageArenaPoints) {
                return 0;
            }
            player.mageArenaPoints -= amount;
            return amount;
        }

        @Override
        public int addCurrency(Player player, int amount) {
            if ((long) player.mageArenaPoints + (long) amount > Integer.MAX_VALUE) {
                player.mageArenaPoints = Integer.MAX_VALUE;
            } else {
                player.mageArenaPoints += amount;
            }
            return amount;
        }
    }),
    APPRECIATION_POINTS(new CurrencyHandler("appreciation points") {
        @Override
        public int getCurrencyCount(Player player) {
            return player.appreciationPoints;
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            if (amount > player.appreciationPoints) {
                return 0;
            }
            player.appreciationPoints -= amount;
            return amount;
        }

        @Override
        public int addCurrency(Player player, int amount) {
            if ((long) player.appreciationPoints + (long) amount > Integer.MAX_VALUE) {
                player.appreciationPoints = Integer.MAX_VALUE;
            } else {
                player.appreciationPoints += amount;
            }
            return amount;
        }
    }),
    PVM_POINTS(new CurrencyHandler("pvm points") {
        @Override
        public int getCurrencyCount(Player player) {
            return player.PvmPoints;
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            if (amount > player.PvmPoints) {
                return 0;
            }
            player.PvmPoints -= amount;
            return amount;
        }

        @Override
        public int addCurrency(Player player, int amount) {
            if ((long) player.PvmPoints + (long) amount > Integer.MAX_VALUE) {
                player.PvmPoints = Integer.MAX_VALUE;
            } else {
                player.PvmPoints += amount;
            }
            return amount;
        }
    }),
    RAID_POINTS(new CurrencyHandler("raid points") {
        @Override
        public int getCurrencyCount(Player player) {
            return player.raidPoints;
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            if (amount > player.raidPoints) {
                return 0;
            }
            player.raidPoints -= amount;
            return amount;
        }

        @Override
        public int addCurrency(Player player, int amount) {
            if ((long) player.raidPoints + (long) amount > Integer.MAX_VALUE) {
                player.raidPoints = Integer.MAX_VALUE;
            } else {
                player.raidPoints += amount;
            }
            return amount;
        }
    }),
    BOSS_POINTS(new CurrencyHandler("boss points") {
        @Override
        public int getCurrencyCount(Player player) {
            return player.bossPoints;
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            if (amount > player.bossPoints) {
                return 0;
            }
            player.bossPoints -= amount;
            return amount;
        }

        @Override
        public int addCurrency(Player player, int amount) {
            if ((long) player.bossPoints + (long) amount > Integer.MAX_VALUE) {
                player.bossPoints = Integer.MAX_VALUE;
            } else {
                player.bossPoints += amount;
            }
            return amount;
        }
    }),
    PEST_CONTROL_POINTS(new CurrencyHandler("pest control") {
        @Override
        public int getCurrencyCount(Player player) {
            return player.pestPoints;
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            if (amount > player.pestPoints) {
                return 0;
            }
            player.pestPoints -= amount;
            return amount;
        }

        @Override
        public int addCurrency(Player player, int amount) {
            if ((long) player.pestPoints + (long) amount > Integer.MAX_VALUE) {
                player.pestPoints = Integer.MAX_VALUE;
            } else {
                player.pestPoints += amount;
            }
            return amount;
        }
    }),
    WILDERNESS_POINTS(new CurrencyHandler("wilderness points") {

        @Override
        public int getCurrencyCount(Player player) {
            return player.wildernessPoints;
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            if (amount > player.wildernessPoints) {
                return 0;
            }
            player.wildernessPoints -= amount;
            return amount;
        }

        @Override
        public int addCurrency(Player player, int amount) {
            if ((long) player.wildernessPoints + (long) amount > Integer.MAX_VALUE) {
                player.wildernessPoints = Integer.MAX_VALUE;
            } else {
                player.wildernessPoints += amount;
            }
            return amount;
        }
    }),
    WINTERTODT_POINTS(new CurrencyHandler("wintertodt points") {

        @Override
        public int getCurrencyCount(Player player) {
            return player.wintertodtstorePoints;
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            if (amount > player.wintertodtstorePoints) {
                return 0;
            }
            player.wintertodtstorePoints -= amount;
            return amount;
        }

        @Override
        public int addCurrency(Player player, int amount) {
            if ((long) player.wintertodtstorePoints + (long) amount > Integer.MAX_VALUE) {
                player.wintertodtstorePoints = Integer.MAX_VALUE;
            } else {
                player.wintertodtstorePoints += amount;
            }
            return amount;
        }
    }),
    SNOWBALL_POINTS(new CurrencyHandler("snowball", "snowballs") {
        @Override
        public int getCurrencyCount(Player player) {
            return player.snowballPoints;
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            if (amount > player.snowballPoints) {
                return 0;
            }
            player.snowballPoints -= amount;
            return amount;
        }

        @Override
        public int addCurrency(Player player, int amount) {
            if ((long) player.snowballPoints + (long) amount > Integer.MAX_VALUE) {
                player.snowballPoints = Integer.MAX_VALUE;
            } else {
                player.snowballPoints += amount;
            }
            return amount;
        }
    });


    Currency(CurrencyHandler currencyHandler) {
        this.currencyHandler = currencyHandler;
    }

    private final CurrencyHandler currencyHandler;


    public static Stream<ItemCurrencyHandler> itemCurrencyStream() {
        return Stream.of(values()).filter(currency -> currency.currencyHandler instanceof ItemCurrencyHandler).map(currency -> (ItemCurrencyHandler) currency.currencyHandler);
    }
}

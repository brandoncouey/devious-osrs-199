package io.ruin.model.activities.wilderness;

public class BloodyMerchant {

    /*private static final int BLOODY_MERCHANT = 4402;
    private static NPC bloodyMerchant = null;
    private static BloodyMerchant ACTIVE;
    private static List<ShopItem> bloodyMerchantItems;
    private static Shop bloodyMerchantShop = null;

    private static long spawnTicks = 0;

    private static final BloodyMerchant[] SPAWN_LOCATIONS = {
            new BloodyMerchant(new Position(3029, 3631, 0), "at the center of the Dark Warriors' Fortress"),
            new BloodyMerchant(new Position(3079, 3871, 0), "inside the Lava Maze"),
            new BloodyMerchant(new Position(3357, 3872, 0), "at the Fountain of Rune"),
            new BloodyMerchant(new Position(2994, 3956, 0), "inside the Wilderness Agility Course"),
            new BloodyMerchant(new Position(3294, 3933, 0), "at the Rogues' Castle"),
    };

    /**
     * Separator
     */

 /*   private final Position spawnPosition;

    private final String positionHint;

    public BloodyMerchant(Position spawnPosition, String positionHint) {
        this.spawnPosition = spawnPosition;
        this.positionHint = positionHint;
    }

    /**
     * Event
     */
   /* static {
        World.startEvent(e -> {
            while (true) {
                spawnTicks = Server.getEnd(1 * 20 * 25); // half hour
                e.delay(1 * 20 * 25); // half hour
                int randomLocation = Random.get(0, SPAWN_LOCATIONS.length - 1);

                BloodyMerchant next = SPAWN_LOCATIONS[randomLocation];
                while (next == ACTIVE) {
                    randomLocation = Random.get(0, SPAWN_LOCATIONS.length - 1);
                    next = SPAWN_LOCATIONS[randomLocation];
                }

                ACTIVE = next;

                String spawnMessage = "The Bloody Merchant has made an appearance " + ACTIVE.positionHint + "!";
                broadcastEvent(spawnMessage);
                spawnMerchant();
                e.delay(30 * 100); // 30 mins
                despawnMerchant();
            }
        });

        NPCAction.register(BLOODY_MERCHANT, "Trade", (player, npc) -> {
            if (bloodyMerchant == null) {
                player.sendMessage("The bloody merchant isn't interested in trading.");
                return;
            }

            if (player.bloodyMerchantTradeWarning) {
                player.dialogue(
                        new MessageDialogue("<col=ff0000>Warning:</col> Trading the bloody merchant will HIGH RISK skull you."),
                        new OptionsDialogue("Are you sure you wish to trade him?",
                                new Option("Yes, I'm brave.", () -> tradeMerchant(player)),
                                new Option("Eep! No thank you.", () -> player.sendFilteredMessage("You decide to not to trade the merchant."))));
            } else {
                tradeMerchant(player);
            }
        });
    }

    private static void broadcastEvent(String eventMessage) {
        for(Player p : World.players) {
            if(p.broadcastBloodyMechant) {
                p.getPacketSender().sendMessage(eventMessage, "", 14);
            }
        }
        Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Wilderness Event", eventMessage);
    }

    private static void tradeMerchant(Player player) {
        player.getCombat().skullHighRisk();
        bloodyMerchantShop.open(player);
    }

    private static void spawnMerchant() {
        World.startEvent(event -> {
            bloodyMerchant = new NPC(BLOODY_MERCHANT).spawn(ACTIVE.spawnPosition);
            bloodyMerchantItems = IntStream
                    .range(0, 10)
                    .mapToObj(i -> BloodMerchantItems.randomItem())
                    .map(bloodMerchantItems -> ShopItem.builder()
                            .id(bloodMerchantItems.itemId)
                            .price(bloodMerchantItems.itemPrice)
                            .amount(Random.get(1, bloodMerchantItems.maxAmt))
                            .build())
                    .collect(Collectors.toList());

            bloodyMerchantShop = Shop.builder()
                    .identifier("f28248e1-969b-4018-b30f-0a93ddb64ba3")//TODO Fill this in
                    .title("Bloody Merchant's Findings")
                    .currency(Currency.BLOOD_MONEY)
                    .defaultStock(bloodyMerchantItems)
                    .canSellToStore(false)
                    .accesibleByRegular(true)
                    .restockRules(RestockRules.generateDefault())
                    .generatedByBuilder(true)
                    .build();
            ShopManager.registerShop(bloodyMerchantShop);
        });
    }

    private static void despawnMerchant() {
        if (bloodyMerchant != null) {
            bloodyMerchant.remove();
            bloodyMerchantItems.clear();
            bloodyMerchantShop = null;
            bloodyMerchant = null;
            String despawnMessage = "The Bloody Merchant has left to collect more items.";
            Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Wilderness Event", despawnMessage);
            broadcastEvent(despawnMessage);
        }
    }

    /**
     * Entry
     */
  /*  public static final class Entry extends JournalEntry {

        public static final Entry INSTANCE = new Entry();

        @Override
        public void send(Player player) {
            int minsLeft = (int) ((spawnTicks - Server.currentTick()) / 100);
            if(minsLeft < 0) {
                send(player, "Bloody Merchant", "Active ", Color.GREEN);
                return;
            }
            if (minsLeft == 0)
                send(player, "Bloody Merchant", "Active!", Color.GREEN);
            else if (minsLeft == 1)
                send(player, "Bloody Merchant", "1 minute", Color.YELLOW);
            else if (minsLeft == 60)
                send(player, "Bloody Merchant", "1 hour", Color.RED);
            else if (minsLeft > 60) {
                int hours = minsLeft / 60;
                send(player, "Bloody Merchant", hours + " hour" + (hours > 1 ? "s" : ""), Color.RED);
            } else
                send(player, "Bloody Merchant", minsLeft + " minutes", Color.RED);
        }

        @Override
        public void select(Player player) {
            Help.open(player, "bloody_merchant");
        }
    }

    /**
     * All the possible items our beautiful merchant offers!
     */
 /*   private enum BloodMerchantItems {
        ARMADYL_GODSWORD(11802, 125000, 1),
        ABYSSAL_WHIP(4151, 15000, 3),
        ABYSSAL_TENTACLE(12006, 20000, 2),
        HEAVY_BALLISTA(19481, 75000, 1),
        BANDOS_GODSWORD(11804, 75000, 1),
        ABYSSAL_BLUDGEON(13263, 125000, 1),
        ANGLERFISH(13441, 500, 500),
        DARK_CRAB(11937, 500, 500),
        SUPER_COMBAT_POTION(12696, 500, 200),
        STAMINA_POTION(12626, 750, 200);

        private final int itemId, itemPrice, maxAmt;

        BloodMerchantItems(int itemId, int itemPrice, int maxAmt) {
            this.itemId = itemId;
            this.itemPrice = itemPrice;
            this.maxAmt = maxAmt;
        }

        private static final List<BloodMerchantItems> VALUES = Collections.unmodifiableList(Arrays.asList(values()));

        public static BloodMerchantItems randomItem()  {
            return VALUES.get(Random.get(VALUES.size() - 1));
        }

    }

  */

}

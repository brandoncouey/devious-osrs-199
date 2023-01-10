package io.ruin.model.skills.farming;


import com.google.gson.annotations.Expose;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.TreeCrop;
import io.ruin.model.skills.farming.crop.impl.*;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.skills.farming.patch.PatchData;
import io.ruin.model.skills.farming.patch.PatchGroup;
import io.ruin.model.skills.farming.patch.impl.*;

import java.util.*;

public class Farming {

    private Player player;
    private final Map<Integer, Patch> patches = new HashMap<>();
    @Expose
    private final ToolStorage storage = new ToolStorage();
    @Expose
    private CompostBin faladorCompostBin, canifisCompostBin, catherbyCompostBin, ardougneCompostBin, zeahCompostBin;
    @Expose
    private HerbPatch canifisHerb, ardougneHerb, faladorHerb, catherbyHerb, trollheimHerb, zeahHerb, guildHerb;
    @Expose
    private FlowerPatch faladorFlower, catherbyFlower, ardougneFlower, canifisFlower, zeahFlower, guildFlower;
    @Expose
    private AllotmentPatch faladorNorth, faladorSouth, catherbyNorth, catherbySouth, ardougneNorth, ardougneSouth, canifisNorth, canifisSouth, zeahNorth, zeahSouth, guildNorth, guildSouth;
    @Expose
    private WoodTreePatch lumbridgeTree, faladorTree, varrockTree, gnomeTree, taverleyTree, guildTree;
    @Expose
    private FruitTreePatch catherbyFruit, brimhavenFruit, gnomeFruit, villageFruit, lletyaFruit, guildFruit;
    @Expose
    private BushPatch varrockBush, ardougneBush, etceteriaBush, rimmingtonBush, guildBush;
    @Expose
    private HopsPatch lumbridgeHops, seersHops, yanilleHops, entranaHops;
    @Expose
    private CalquatTreePatch calquat;
    @Expose
    private CactusPatch cactus, guildCactus;
    @Expose
    private SpiritTreePatch brimhavenSpiritTree, etceteriaSpiritTree, portSarimSpiritTree, zeahSpiritTree, guildSpiritTree;
    @Expose
    private MushroomPatch canifisMushroom;
    @Expose
    private RedwoodTreePatch guildRedwood1, guildRedwood2, guildRedwood3, guildRedwood4, guildRedwood5, guildRedwood6, guildRedwood7, guildRedwood8, guildRedwood9;
    @Expose
    private CelastrusTreePatch guildCelastrus;
    @Expose
    private HesporiPatch guildHespori;
    @Expose
    private AnimaPatch guildAnima;

    private PatchGroup visibleGroup;

    public Farming() {

    }

    public void init(Player player) {
        this.player = player;
        storage.setPlayer(player);
        if (faladorHerb == null) { // if one is null, all need to be initialized, this is a new player

            faladorHerb = new HerbPatch();
            catherbyHerb = new HerbPatch();
            ardougneHerb = new HerbPatch();
            canifisHerb = new HerbPatch();
            trollheimHerb = new HerbPatch();

            faladorFlower = new FlowerPatch();
            catherbyFlower = new FlowerPatch();
            ardougneFlower = new FlowerPatch();
            canifisFlower = new FlowerPatch();

            faladorNorth = new AllotmentPatch();
            faladorSouth = new AllotmentPatch();
            catherbyNorth = new AllotmentPatch();
            catherbySouth = new AllotmentPatch();
            ardougneNorth = new AllotmentPatch();
            ardougneSouth = new AllotmentPatch();
            canifisNorth = new AllotmentPatch();
            canifisSouth = new AllotmentPatch();

            taverleyTree = new WoodTreePatch();
            faladorTree = new WoodTreePatch();
            varrockTree = new WoodTreePatch();
            lumbridgeTree = new WoodTreePatch();
            gnomeTree = new WoodTreePatch();

            gnomeFruit = new FruitTreePatch();
            villageFruit = new FruitTreePatch();
            brimhavenFruit = new FruitTreePatch();
            catherbyFruit = new FruitTreePatch();
            lletyaFruit = new FruitTreePatch();
        }

        if (guildHespori == null) {
            guildAnima = new AnimaPatch();
            guildHerb = new HerbPatch();
            guildBush = new BushPatch();
            guildCactus = new CactusPatch();
            guildCelastrus = new CelastrusTreePatch();
            guildNorth = new AllotmentPatch();
            guildSouth = new AllotmentPatch();
            guildSpiritTree = new SpiritTreePatch();
            guildRedwood1 = new RedwoodTreePatch();
            guildRedwood2 = new RedwoodTreePatch();
            guildRedwood3 = new RedwoodTreePatch();
            guildRedwood4 = new RedwoodTreePatch();
            guildRedwood5 = new RedwoodTreePatch();
            guildRedwood6 = new RedwoodTreePatch();
            guildRedwood7 = new RedwoodTreePatch();
            guildRedwood8 = new RedwoodTreePatch();
            guildRedwood9 = new RedwoodTreePatch();
            guildTree = new WoodTreePatch();
            guildFruit = new FruitTreePatch();
            guildFlower = new FlowerPatch();
            guildHespori = new HesporiPatch();
        }

        if (faladorCompostBin == null) {
            faladorCompostBin = new CompostBin();
            canifisCompostBin = new CompostBin();
            catherbyCompostBin = new CompostBin();
            ardougneCompostBin = new CompostBin();
        }

        if (zeahNorth == null) {
            zeahNorth = new AllotmentPatch();
            zeahSouth = new AllotmentPatch();
            zeahFlower = new FlowerPatch();
            zeahHerb = new HerbPatch();
            zeahCompostBin = new CompostBin();
        }

        if (varrockBush == null) {
            varrockBush = new BushPatch();
            etceteriaBush = new BushPatch();
            rimmingtonBush = new BushPatch();
            ardougneBush = new BushPatch();
        }

        if (lumbridgeHops == null) {
            lumbridgeHops = new HopsPatch();
            entranaHops = new HopsPatch();
            seersHops = new HopsPatch();
            yanilleHops = new HopsPatch();
        }

        if (calquat == null) {
            calquat = new CalquatTreePatch();
        }
        if (cactus == null) {
            cactus = new CactusPatch();
        }
        if (brimhavenSpiritTree == null) {
            brimhavenSpiritTree = new SpiritTreePatch();
            etceteriaSpiritTree = new SpiritTreePatch();
            zeahSpiritTree = new SpiritTreePatch();
            portSarimSpiritTree = new SpiritTreePatch();
        }

        if (canifisMushroom == null) {
            canifisMushroom = new MushroomPatch();
        }

        addPatch(faladorCompostBin.set(PatchData.FALADOR_COMPOST_BIN).setPlayer(player));
        addPatch(catherbyCompostBin.set(PatchData.CATHERBY_COMPOST_BIN).setPlayer(player));
        addPatch(canifisCompostBin.set(PatchData.CANIFIS_COMPOST_BIN).setPlayer(player));
        addPatch(ardougneCompostBin.set(PatchData.ARDOUGNE_COMPOST_BIN).setPlayer(player));

        addPatch(faladorHerb.set(PatchData.FALADOR_HERB).setPlayer(player));
        addPatch(catherbyHerb.set(PatchData.CATHERBY_HERB).setPlayer(player));
        addPatch(ardougneHerb.set(PatchData.ARDOUGNE_HERB).setPlayer(player));
        addPatch(canifisHerb.set(PatchData.CANIFIS_HERB).setPlayer(player));
        addPatch(trollheimHerb.set(PatchData.TROLLHEIM_HERB).setPlayer(player));

        addPatch(faladorFlower.set(PatchData.FALADOR_FLOWER).setPlayer(player));
        addPatch(catherbyFlower.set(PatchData.CATHERBY_FLOWER).setPlayer(player));
        addPatch(ardougneFlower.set(PatchData.ARDOUGNE_FLOWER).setPlayer(player));
        addPatch(canifisFlower.set(PatchData.CANIFIS_FLOWER).setPlayer(player));

        addPatch(faladorNorth.set(PatchData.FALADOR_NORTH, faladorFlower).setPlayer(player));
        addPatch(faladorSouth.set(PatchData.FALADOR_SOUTH, faladorFlower).setPlayer(player));
        addPatch(catherbyNorth.set(PatchData.CATHERBY_NORTH, catherbyFlower).setPlayer(player));
        addPatch(catherbySouth.set(PatchData.CATHERBY_SOUTH, catherbyFlower).setPlayer(player));
        addPatch(ardougneNorth.set(PatchData.ARDOUGNE_NORTH, ardougneFlower).setPlayer(player));
        addPatch(ardougneSouth.set(PatchData.ARDOUGNE_SOUTH, ardougneFlower).setPlayer(player));
        addPatch(canifisNorth.set(PatchData.CANIFIS_NORTH, canifisFlower).setPlayer(player));
        addPatch(canifisSouth.set(PatchData.CANIFIS_SOUTH, canifisFlower).setPlayer(player));

        addPatch(taverleyTree.set(PatchData.TAVERLEY_TREE).setPlayer(player));
        addPatch(faladorTree.set(PatchData.FALADOR_TREE).setPlayer(player));
        addPatch(varrockTree.set(PatchData.VARROCK_TREE).setPlayer(player));
        addPatch(lumbridgeTree.set(PatchData.LUMBRIDGE_TREE).setPlayer(player));
        addPatch(gnomeTree.set(PatchData.GNOME_TREE).setPlayer(player));

        addPatch(gnomeFruit.set(PatchData.GNOME_FRUIT).setPlayer(player));
        addPatch(villageFruit.set(PatchData.VILLAGE_FRUIT).setPlayer(player));
        addPatch(brimhavenFruit.set(PatchData.BRIMHAVEN_FRUIT).setPlayer(player));
        addPatch(catherbyFruit.set(PatchData.CATHERBY_FRUIT).setPlayer(player));
        addPatch(lletyaFruit.set(PatchData.LLETYA_FRUIT).setPlayer(player));

        addPatch(zeahNorth.set(PatchData.ZEAH_NORTH).setPlayer(player));
        addPatch(zeahSouth.set(PatchData.ZEAH_SOUTH).setPlayer(player));
        addPatch(zeahFlower.set(PatchData.ZEAH_FLOWER).setPlayer(player));
        addPatch(zeahHerb.set(PatchData.ZEAH_HERB).setPlayer(player));
        addPatch(zeahCompostBin.set(PatchData.ZEAH_COMPOST_BIN).setPlayer(player));

        addPatch(varrockBush.set(PatchData.VARROCK_BUSH).setPlayer(player));
        addPatch(rimmingtonBush.set(PatchData.RIMMINGTON_BUSH).setPlayer(player));
        addPatch(etceteriaBush.set(PatchData.ETCETERIA_BUSH).setPlayer(player));
        addPatch(ardougneBush.set(PatchData.ARDOUGNE_BUSH).setPlayer(player));

        addPatch(lumbridgeHops.set(PatchData.LUMBRIDGE_HOPS).setPlayer(player));
        addPatch(entranaHops.set(PatchData.ENTRANA_HOPS).setPlayer(player));
        addPatch(yanilleHops.set(PatchData.YANILLE_HOPS).setPlayer(player));
        addPatch(seersHops.set(PatchData.SEERS_HOPS).setPlayer(player));

        addPatch(calquat.set(PatchData.CALQUAT).setPlayer(player));
        addPatch(cactus.set(PatchData.CACTUS).setPlayer(player));

        addPatch(brimhavenSpiritTree.setTeleportPosition(SpiritTreePatch.BRIMHAVEN_TELEPORT).set(PatchData.BRIMHAVEN_SPIRIT_TREE).setPlayer(player));
        addPatch(portSarimSpiritTree.setTeleportPosition(SpiritTreePatch.PORT_SARIM_TELEPORT).set(PatchData.PORT_SARIM_SPIRIT_TREE).setPlayer(player));
        addPatch(etceteriaSpiritTree.setTeleportPosition(SpiritTreePatch.ETCETERIA_TELEPORT).set(PatchData.ETCETERIA_SPIRIT_TREE).setPlayer(player));
        addPatch(zeahSpiritTree.setTeleportPosition(SpiritTreePatch.ZEAH_TELEPORT).set(PatchData.ZEAH_SPIRIT_TREE).setPlayer(player));

        addPatch(canifisMushroom.set(PatchData.CANIFIS_MUSHROOM).setPlayer(player));

        addPatch(guildSouth.set(PatchData.GUILD_SOUTH).setPlayer(player));
        addPatch(guildNorth.set(PatchData.GUILD_NORTH).setPlayer(player));
        addPatch(guildSpiritTree.set(PatchData.GUILD_SPIRIT).setPlayer(player));
        addPatch(guildTree.set(PatchData.GUILD_TREE).setPlayer(player));
        addPatch(guildCelastrus.set(PatchData.GUILD_CELASTRUS).setPlayer(player));
        addPatch(guildFruit.set(PatchData.GUILD_FRUIT).setPlayer(player));
        addPatch(guildBush.set(PatchData.GUILD_BUSH).setPlayer(player));
        addPatch(guildFlower.set(PatchData.GUILD_FLOWER).setPlayer(player));
        addPatch(guildHerb.set(PatchData.GUILD_HERB).setPlayer(player));
        addPatch(guildHespori.set(PatchData.GUILD_HESPORI).setPlayer(player));
        addPatch(guildCactus.set(PatchData.GUILD_CACTUS).setPlayer(player));
        addPatch(guildAnima.set(PatchData.ANIMA_PATCH).setPlayer(player));
//        addPatch(guildCompostBin.set(PatchData.GUILD_COMPOST_BIN).setPlayer(player));
        addPatch(guildRedwood1.set(PatchData.GUILD_REDWOOD_TREE_34051).setPlayer(player));
        addPatch(guildRedwood2.set(PatchData.GUILD_REDWOOD_TREE_34052).setPlayer(player));
        addPatch(guildRedwood3.set(PatchData.GUILD_REDWOOD_TREE_34053).setPlayer(player));
        addPatch(guildRedwood4.set(PatchData.GUILD_REDWOOD_TREE_34054).setPlayer(player));
        addPatch(guildRedwood5.set(PatchData.GUILD_REDWOOD_TREE_34055).setPlayer(player));
        addPatch(guildRedwood6.set(PatchData.GUILD_REDWOOD_TREE_34056).setPlayer(player));
        addPatch(guildRedwood7.set(PatchData.GUILD_REDWOOD_TREE_34057).setPlayer(player));
        addPatch(guildRedwood8.set(PatchData.GUILD_REDWOOD_TREE_34058).setPlayer(player));
        addPatch(guildRedwood9.set(PatchData.GUILD_REDWOOD_TREE_34059).setPlayer(player));


        patches.forEach((id, patch) -> patch.onLoad()); // force ticks
        refresh();
        player.addEvent(event -> { // absolutely disgusting
            while (true) {
                event.delay(20);
                refresh();
            }
        });
        player.addEvent(event -> {
            while (true) {
                event.delay(50);
                tick();
            }
        }); // farming tick
    }


    public void sendPatch(GameObject gameObject) {
        Patch patch = getPatch(gameObject);
        if (patch == null)
            return;
        patch.send();
    }


    public void refresh() {
        PatchGroup prevGroup = visibleGroup;
        if (visibleGroup == null || (!player.getPosition().inBounds(visibleGroup.getBounds()))) {
            boolean found = false;
            for (PatchGroup pg : PatchGroup.values()) {
                if (player.getPosition().inBounds(pg.getBounds())) {
                    visibleGroup = pg;
                    found = true;
                    break;
                }
            }
            if (!found) {
                visibleGroup = null;
            }
        }

        if (visibleGroup != null && prevGroup != visibleGroup) {
            for (PatchData pd : visibleGroup.getPatches()) {
                getPatch(pd.getObjectId()).send();
            }
        }
    }

    private void tick() {
        patches.forEach((id, patch) -> {
            patch.tick();
            if (patch.needsUpdate()) {
                patch.update();
                patch.setNeedsUpdate(false);
            }
        });
    }

    public void addPatch(Patch patch) {
        patches.put(patch.getObjectId(), patch);
    }

    public boolean handleObject(GameObject obj, int option) {
        Patch patch = patches.get(obj.id);
        if (patch != null) {
            patch.objectAction(option);
        }
        return false;
    }

    public boolean itemOnPatch(Item item, GameObject obj) {
        Patch patch = patches.get(obj.id);
        if (patch != null) {
            patch.handleItem(item);
            return true;
        }
        return false;
    }


    public Patch getPatch(GameObject obj) {
        return patches.get(obj.id);
    }

    public Patch getPatch(int objId) {
        return patches.get(objId);
    }

    public boolean hasSecateurs() {
        return player.getEquipment().getId(Equipment.SLOT_WEAPON) == 7409 || player.getInventory().contains(7409, 1)
                || player.getInventory().contains(5329, 1);
    }

    public ToolStorage getStorage() {
        return storage;
    }


    public static final List<Crop> CROPS = new ArrayList<>();

    static {
        Collections.addAll(CROPS, HerbCrop.values());
        Collections.addAll(CROPS, AllotmentCrop.values());
        Collections.addAll(CROPS, FlowerCrop.values());
        Collections.addAll(CROPS, WoodTreeCrop.values());
        Collections.addAll(CROPS, FruitTreeCrop.values());
        Collections.addAll(CROPS, MushroomCrop.values());
        Collections.addAll(CROPS, BushCrop.values());
        Collections.addAll(CROPS, HopsCrop.values());
        Collections.addAll(CROPS, CalquatCrop.INSTANCE);
        Collections.addAll(CROPS, CactusCrop.INSTANCE);
        Collections.addAll(CROPS, SpiritTreeCrop.INSTANCE);
        Collections.addAll(CROPS, RedwoodTreeCrop.INSTANCE);
        Collections.addAll(CROPS, CelastrusCrop.values());
        Collections.addAll(CROPS, AnimaCrop.values());
        Collections.addAll(CROPS, HesporiCrop.INSTANCE);
        ItemDef.cached.values().stream().filter(Objects::nonNull).forEach(def -> {
            def.produceOf = getCropForProduce(def.id);
            def.seedType = getCropForSeed(def.id);
            if (def.seedType == null)
                def.seedType = getCropForSapling(def.id);
        });

        CROPS.stream().filter(crop -> crop instanceof TreeCrop).map(crop -> (TreeCrop) crop).forEach(crop -> {
            ItemItemAction.register(5356, crop.getSeed(), (player, primary, secondary) -> {
                primary.setId(crop.getSeedling());
                secondary.remove(1);
                player.sendFilteredMessage("You plant the seed in the pot.");
            });
        });

        CROPS.stream().filter(crop -> crop instanceof TreeCrop).map(crop -> (TreeCrop) crop).forEach(crop -> {
            for (int i = 1; i < AllotmentPatch.WATERING_CAN_IDS.size(); i++) {
                final int charges = i;
                ItemItemAction.register(AllotmentPatch.WATERING_CAN_IDS.get(charges), crop.getSeedling(), (player, primary, secondary) -> {
                    primary.setId(AllotmentPatch.WATERING_CAN_IDS.get(charges - 1));
                    secondary.setId(crop.getSapling());
                    if (charges == 1)
                        player.sendMessage("Your watering can is now empty.");
                });
            }
        });

    }


    public static Crop getCropForSeed(int seed) {
        for (Crop crop : CROPS) {
            if (crop.getSeed() == seed)
                return crop;
        }
        return null;
    }

    public static Crop getCropForProduce(int produce) {
        for (Crop crop : CROPS) {
            if (crop.getProduceId() == produce)
                return crop;
        }
        return null;
    }

    public static TreeCrop getCropForSapling(int sapling) {
        for (Crop crop : CROPS) {
            if (crop instanceof TreeCrop && ((TreeCrop) crop).getSapling() == sapling)
                return (TreeCrop) crop;
        }
        return null;
    }


    public PatchGroup getVisibleGroup() {
        return visibleGroup;
    }

    public SpiritTreePatch getBrimhavenSpiritTree() {
        return brimhavenSpiritTree;
    }

    public SpiritTreePatch getEtceteriaSpiritTree() {
        return etceteriaSpiritTree;
    }

    public SpiritTreePatch getPortSarimSpiritTree() {
        return portSarimSpiritTree;
    }

    public SpiritTreePatch getZeahSpiritTree() {
        return zeahSpiritTree;
    }
}



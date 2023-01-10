package io.ruin.model.skills.magic;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.handlers.TabCombat;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Bounds;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.BountyTeleport;
import io.ruin.model.skills.magic.spells.HomeTeleport;
import io.ruin.model.skills.magic.spells.TodoSpell;
import io.ruin.model.skills.magic.spells.ancient.*;
import io.ruin.model.skills.magic.spells.arceuus.ArceuusTeleport;
import io.ruin.model.skills.magic.spells.arceuus.Reanimate;
import io.ruin.model.skills.magic.spells.lunar.*;
import io.ruin.model.skills.magic.spells.modern.*;

public enum SpellBook {

    MODERN(
            HomeTeleport.MODERN,
            new WindStrike(),
            new Confuse(),
            new EnchantCrossbowBolt(),
            new WaterStrike(),
            new JewelleryEnchant(JewelleryEnchant.EnchantLevel.ONE),
            new EarthStrike(),
            new Weaken(),
            new FireStrike(),
            new BonesBananas(),
            new WindBolt(),
            new Curse(),
            new Bind(),
            new Alchemy(true),
            new WaterBolt(),
            ModernTeleport.VARROCK_TELEPORT,
            new JewelleryEnchant(JewelleryEnchant.EnchantLevel.TWO),
            new EarthBolt(),
            ModernTeleport.LUMBRIDGE_TELEPORT,
            new TodoSpell("Telekinetic Grab"),
            new FireBolt(),
            ModernTeleport.FALADOR_TELEPORT,
            CrumbleUndead.INSTANCE,
            new TeleportToHouse(),
            new WindBlast(),
            new SuperheatItem(),
            ModernTeleport.CAMELOT_TELEPORT,
            new WaterBlast(),
            new JewelleryEnchant(JewelleryEnchant.EnchantLevel.THREE),
            new IbanBlast(),
            new Snare(),
            new MagicDart(),
            ModernTeleport.ARDOUGNE_TELEPORT,
            new EarthBlast(),
            new Alchemy(false),
            new ChargeOrb(ChargeOrb.ChargeSpell.CHARGE_WATER),
            new JewelleryEnchant(JewelleryEnchant.EnchantLevel.FOUR),
            ModernTeleport.WATCHTOWER_TELEPORT,
            new FireBlast(),
            new ChargeOrb(ChargeOrb.ChargeSpell.CHARGE_EARTH),
            new BonesPeaches(),
            new SaradominStrike(),
            new ClawsOfGuthix(),
            new FlamesOfZamorak(),
            ModernTeleport.TROLLHEIM_TELEPORT,
            new WindWave(),
            new ChargeOrb(ChargeOrb.ChargeSpell.CHARGE_FIRE),
            ModernTeleport.APE_ATOLL_TELEPORT,
            new WaterWave(),
            new ChargeOrb(ChargeOrb.ChargeSpell.CHARGE_AIR),
            new Vulnerability(),
            new JewelleryEnchant(JewelleryEnchant.EnchantLevel.FIVE),
            ModernTeleport.KOUREND_TELEPORT,
            new EarthWave(),
            new Enfeeble(),
            Teleother.LUMBRIDGE.toSpell(),
            new FireWave(),
            new Entangle(),
            new Stun(),
            new Charge(),
            new WindSurge(),
            Teleother.FALADOR.toSpell(),
            new WaterSurge(),
            new Teleblock(),
            new BountyTeleport(),
            new JewelleryEnchant(JewelleryEnchant.EnchantLevel.SIX),
            Teleother.CAMELOT.toSpell(),
            new EarthSurge(),
            new JewelleryEnchant(JewelleryEnchant.EnchantLevel.SEVEN),
            new FireSurge()
    ),
    ANCIENT(
            new IceRush(),
            new IceBlitz(),
            new IceBurst(),
            new IceBarrage(),
            new BloodRush(),
            new BloodBlitz(),
            new BloodBurst(),
            new BloodBarrage(),
            new SmokeRush(),
            new SmokeBlitz(),
            new SmokeBurst(),
            new SmokeBarrage(),
            ShadowRush.INSTANCE,
            ShadowBlitz.INSTANCE,
            ShadowBurst.INSTANCE,
            ShadowBarrage.INSTANCE,

            new AncientTeleport(54, 64.0, new Bounds(3098, 9881, 3095, 9884, 0), Rune.LAW.toItem(2), Rune.FIRE.toItem(1), Rune.AIR.toItem(1)),
            new AncientTeleport(60, 70.0, new Bounds(3349, 3345, 3346, 3348, 0), Rune.LAW.toItem(2), Rune.SOUL.toItem(1)),
            new AncientTeleport(66, 76.0, new Bounds(3490, 3471, 3493, 3472, 0), Rune.LAW.toItem(2), Rune.BLOOD.toItem(1)),
            new AncientTeleport(72, 82.0, new Bounds(3013, 3500, 3015, 3501, 0), Rune.LAW.toItem(2), Rune.WATER.toItem(4)),
            new AncientTeleport(78, 88.0, new Bounds(2965, 3964, 2969, 3697, 0), Rune.LAW.toItem(2), Rune.FIRE.toItem(3), Rune.AIR.toItem(2)),
            new AncientTeleport(84, 94.0, new Bounds(3162, 3663, 3164, 3665, 0), Rune.LAW.toItem(2), Rune.SOUL.toItem(2)),
            new AncientTeleport(90, 100.0, new Bounds(3287, 3886, 3288, 3887, 0), Rune.LAW.toItem(2), Rune.BLOOD.toItem(2)),
            new AncientTeleport(96, 106.0, new Bounds(2970, 3871, 2975, 3875, 0), Rune.LAW.toItem(2), Rune.WATER.toItem(8)),
            HomeTeleport.ANCIENT
    ),
    LUNAR(
            HomeTeleport.LUNAR,
            new BakePie(),
            new TodoSpell("Cure Plant"),
            new MonsterExamine(),
            new TodoSpell("NPC Contact"),
            new CureOther(),
            new Humidify(),
            LunarTeleport.MOONCLAN_TELEPORT,
            new TodoSpell("Tele Group Moonclan"),
            new CureMe(),
            new TodoSpell("Hunter Kit"),
            LunarTeleport.WATERBIRTH_TELEPORT,
            new TodoSpell("Tele Group Waterbirth"),
            new TodoSpell("Cure Group"),
            new TodoSpell("Stat Spy"),
            LunarTeleport.BARBARIAN_TELEPORT,
            new TodoSpell("Tele Group Barbarian"),
            new SuperglassMake(),
            new TodoSpell("Tan Leather"),
            LunarTeleport.KHAZARD_TELEPORT,
            new TodoSpell("Tele Group Khazard"),
            new TodoSpell("Dream"),
            new TodoSpell("String Jewellery"),
            new TodoSpell("Stat Restore Pot Share"),
            new MagicImbue(),
            new FertileSoil(),
            new TodoSpell("Boost Potion Share"),
            LunarTeleport.FISHING_GUILD_TELEPORT,
            new TodoSpell("Tele Group Fishing Guild"),
            new TodoSpell("Plank Make"),
            LunarTeleport.CATHERBY_TELEPORT,
            new TodoSpell("Tele Group Catherby"),
            new TodoSpell("Recharge Dragonstone"),
            LunarTeleport.ICE_PLATEAU_TELEPORT,
            new TodoSpell("Tele Group Ice Plateau"),
            new GiveSpecial(),
            new TodoSpell("Heal Other"),
            new VengOther(),
            new Vengeance(),
            new TodoSpell("Heal Group"),
            new TodoSpell("Spellbook Swap"),
            new TodoSpell("Geomancy"),
            new SpinFlaxSpell(),
            LunarTeleport.OURIANA_TELEPORT
    ),
    ARCEUUS(
            HomeTeleport.ARCEUUS,
            ArceuusTeleport.ARCEUUS_LIBRARY,
            new Reanimate(Reanimate.Monster.GOBLIN),
            new ArceuusTeleport(17, 16.0, new Bounds(3108, 3350, 3109, 3351, 0), Rune.LAW.toItem(1), Rune.EARTH.toItem(1), Rune.WATER.toItem(1)),
            new TodoSpell("Battlefront Teleport"),
            new TodoSpell("Mind Altar Teleport"),
            new TodoSpell("Respawn Teleport"),
            new ArceuusTeleport(28, 22.0, new Bounds(2979, 3509, 2980, 3510, 0), Rune.LAW.toItem(1), Rune.MIND.toItem(2)),
            new ArceuusTeleport(34, 27.0, new Bounds(3084, 3489, 3088, 3492, 0), Rune.LAW.toItem(1), Rune.SOUL.toItem(1)),
            new TodoSpell("Ghostly Grasp"),
            new TodoSpell("Resurrect Lessed Ghost"),
            new TodoSpell("Resurrect Lesser Skeleton"),
            new Reanimate(Reanimate.Monster.CHAOS_DRUID),
            new ArceuusTeleport(40, 30.0, new Bounds(3433, 3460, 3435, 3462, 0), Rune.LAW.toItem(1), Rune.SOUL.toItem(2)),
            new TodoSpell("Resurrect Lesser Zombie"),
            new TodoSpell("Inferior Demonbane"),
            new TodoSpell("Shadow Veil"),
            new ArceuusTeleport(48, 50.0, new Bounds(3547, 3528, 3549, 3529, 0), Rune.LAW.toItem(1), Rune.EARTH.toItem(1), Rune.SOUL.toItem(1)),
            new TodoSpell("Dark Lure"),
            new TodoSpell("Skeletal Grasp"),
            new TodoSpell("Resurrect Superior Ghost"),
            new TodoSpell("Resurrect Superior Skeleton"),
            new TodoSpell("Resurrect Superior Zombie"),
            new TodoSpell("Mark of Darkness"),
            new ArceuusTeleport(61, 68.0, new Bounds(2499, 3291, 2501, 3292, 0), Rune.LAW.toItem(2), Rune.SOUL.toItem(2)),
            new TodoSpell("Superior Demonbane"),
            new TodoSpell("Lesser Corruption"),
            new ArceuusTeleport(65, 74.0, new Bounds(3796, 2864, 3798, 2866, 0), Rune.LAW.toItem(1), Rune.SOUL.toItem(1), Rune.NATURE.toItem(1)),
            new TodoSpell("Vile Vigour"),
            new TodoSpell("Degrime"),
            new ArceuusTeleport(71, 82.0, new Bounds(2979, 3763, 2981, 3763, 0), Rune.LAW.toItem(1), Rune.SOUL.toItem(1), Rune.BLOOD.toItem(1)),
            new TodoSpell("Resurrect Lesser Zombie"),
            new TodoSpell("Ward of Arceuus"),
            new TodoSpell("Resurrect Greater Ghost"),
            new TodoSpell("Resurrect Greater Skeleton"),
            new TodoSpell("Resurrect Greater Zombie"),
            new TodoSpell("Resurrect Crops"),
            new TodoSpell("Undead Grasp"),
            new TodoSpell("Death Charge"),
            new TodoSpell("Dark Demonbane"),
            new ArceuusTeleport(83, 90.0, new Bounds(3564, 3313, 3566, 3315, 0), Rune.LAW.toItem(2), Rune.SOUL.toItem(2), Rune.BLOOD.toItem(1)),
            new TodoSpell("Demonic Offering"),
            new TodoSpell("Greater Corruption"),
            new TodoSpell("Resurrect Lesser Zombie"),
            new ArceuusTeleport(90, 100.0, new Bounds(2800, 9210, 2801, 9211, 0), Rune.LAW.toItem(2), Rune.SOUL.toItem(2), Rune.BLOOD.toItem(2)),
            new TodoSpell("Sinister Offering")
    );

    public final Spell[] spells;

    public final String name;

    public int spellIdOffset;

    SpellBook(Spell... spells) {
        this.spells = spells;
        this.name = name().toLowerCase();
    }

    public void setActive(Player player) {
        TabCombat.updateAutocast(player, false);
        Config.MAGIC_BOOK.set(player, ordinal());
        TabCombat.resetAutocast(player);
        player.getCombat().lastAutoCastIndex = -1;
    }

    public boolean isActive(Player player) {
        return Config.MAGIC_BOOK.get(player) == ordinal();
    }

    public static void updateSpellOffsets() {
        SpellBook.MODERN.spellIdOffset = 5;
        SpellBook.ANCIENT.spellIdOffset = 75;
        SpellBook.LUNAR.spellIdOffset = 100;
        SpellBook.ARCEUUS.spellIdOffset = 144;
    }

    static {
        updateSpellOffsets();
    }
}
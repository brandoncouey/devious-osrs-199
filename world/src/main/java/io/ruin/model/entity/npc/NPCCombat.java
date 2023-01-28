package io.ruin.model.entity.npc;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.NPCDef;
import io.ruin.data.impl.npcs.npc_combat;
import io.ruin.model.achievements.listeners.experienced.DemonSlayer;
import io.ruin.model.activities.bosses.nex.Nex;
import io.ruin.model.activities.tasks.DailyTaskKills;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.combat.*;
import io.ruin.model.content.PvmPoints;
import io.ruin.model.diaries.minigames.MinigamesDiaryEntry;
import io.ruin.model.diaries.skilling.SkillingDiaryEntry;
import io.ruin.model.diaries.fremennik.FremennikDiaryEntry;
import io.ruin.model.diaries.kandarin.KandarinDiaryEntry;
import io.ruin.model.diaries.pvm.PvMDiaryEntry;
import io.ruin.model.diaries.devious.DeviousDiaryEntry;
import io.ruin.model.diaries.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import io.ruin.model.diaries.morytania.MorytaniaDiaryEntry;
import io.ruin.model.diaries.western.WesternDiaryEntry;
import io.ruin.model.diaries.wilderness.WildernessDiaryEntry;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.DoubleDrops;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.BoneCrusher;
import io.ruin.model.item.actions.impl.jewellery.BraceletOfEthereum;
import io.ruin.model.item.actions.impl.jewellery.RingofEndlessRecoil;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Graphic;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.actions.impl.dungeons.KourendCatacombs;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.model.skills.prayer.Ashes;
import io.ruin.model.skills.prayer.Bone;
import io.ruin.model.skills.slayer.Slayer;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import io.ruin.services.discord.impl.RareDropEmbedMessage;
import io.ruin.utility.Broadcast;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.cache.ItemID.VORKATHS_HEAD;
import static io.ruin.model.item.actions.impl.BoneCrusher.hasCharges;
import static io.ruin.model.item.actions.impl.jewellery.RingOfWealth.wearingRingOfWealth;

public abstract class NPCCombat extends Combat {

    protected NPC npc;

    protected npc_combat.Info info;

    protected Stat[] stats;

    private int[] bonuses;

    private boolean allowRespawn = true;

    @Setter
    @Getter
    private boolean allowRetaliate = true;

    public final NPCCombat init(NPC npc, npc_combat.Info info) {
        this.npc = npc;
        this.info = info;
        this.stats = new Stat[]{
                new Stat(info.attack),      //0
                new Stat(info.defence),     //1
                new Stat(info.strength),    //2
                new Stat(info.hitpoints),   //3
                new Stat(info.ranged),      //4
                new Stat(1),           //5 (prayer, not used)
                new Stat(info.magic)        //6
        };
        this.bonuses = new int[]{
                /*
                 * Attack bonuses
                */
                info.stab_attack,
                info.slash_attack,
                info.crush_attack,
                info.magic_attack,
                info.ranged_attack,
                /*
                 * Defence bonuses
                 */
                info.stab_defence,
                info.slash_defence,
                info.crush_defence,
                info.magic_defence,
                info.ranged_defence,
        };
        init();
        return this;
    }

    public void updateInfo(npc_combat.Info newInfo) { // only used when transforming!
        info = newInfo;
        if (stats[0].fixedLevel != newInfo.attack) stats[0] = new Stat(newInfo.attack);
        if (stats[1].fixedLevel != newInfo.defence) stats[1] = new Stat(newInfo.defence);
        if (stats[2].fixedLevel != newInfo.strength) stats[2] = new Stat(newInfo.strength);
        if (stats[3].fixedLevel != newInfo.hitpoints) stats[3] = new Stat(newInfo.hitpoints);
        if (stats[4].fixedLevel != newInfo.ranged) stats[4] = new Stat(newInfo.ranged);
        if (stats[6].fixedLevel != newInfo.magic) stats[6] = new Stat(newInfo.magic);

        this.bonuses = new int[]{ // bonuses cannot be changed so we can just set to the new ones
                /*
                 * Attack bonuses
                */
                info.stab_attack,
                info.slash_attack,
                info.crush_attack,
                info.magic_attack,
                info.ranged_attack,
                /*
                 * Defence bonuses
                */
                info.stab_defence,
                info.slash_defence,
                info.crush_defence,
                info.magic_defence,
                info.ranged_defence,
        };
    }

    /**
     * Following
     */
    public final void follow0() {
        checkAggression();
        if (target == null || npc.isLocked()) //why can an npc be locked but still have a target.. hmm..
            return;
        if (!canAttack(target)) {
            reset();
            return;
        }
        follow();
    }

    protected void follow(int distance) {
        DumbRoute.step(npc, target, distance);
    }

    protected boolean withinDistance(int distance) {
        return DumbRoute.withinDistance(npc, target, distance);
    }

    /**
     * Attacking
     */
    public final void attack0() {
        if (target == null || hasAttackDelay() || npc.isLocked() || !attack())
            return;
        updateLastAttack(info.attack_ticks);
    }

    public boolean canAttack(Entity target) {
        if (isDead())
            return false;
        if (target == null || target.isHidden())
            return false;
        if (target.getCombat() == null)
            return false;
        if (target.getCombat().isDead())
            return false;
        if (!multiCheck(target))
            return false;
        if (npc.targetPlayer == null) {
            if (!npc.getPosition().isWithinDistance(target.getPosition()))
                return false;
            Bounds attackBounds = npc.attackBounds;
            if (attackBounds != null && !npc.getPosition().inBounds(attackBounds)) {
                DumbRoute.route(npc, npc.spawnPosition.getX(), npc.spawnPosition.getY());
                //possibly consider resetting the monster to prevent abusing this mechanic
                return false;
            }
        }
        return true;
    }

    public boolean multiCheck(Entity target) {
        return npc.inMulti() || target.getCombat().allowPj(npc);
    }

    protected Hit basicAttack() {
        return basicAttack(info.attack_animation, info.attack_style, info.max_damage);
    }

    protected Hit basicAttack(int animation, AttackStyle attackStyle, int maxDamage) {
        npc.animate(animation);
        defendAnim();
        Hit hit = new Hit(npc, attackStyle, null).randDamage(maxDamage);
        target.hit(hit);
        return hit;
    }

    protected Hit projectileAttack(Projectile projectile, int animation, AttackStyle attackStyle, int maxDamage) {
        return projectileAttack(projectile, animation, Graphic.builder().id(-1).build(), attackStyle, maxDamage);
    }

    protected Hit projectileAttack(Projectile projectile, int animation, Graphic hitgfx, AttackStyle attackStyle, int maxDamage) {
        return projectileAttack(projectile, animation, hitgfx, Graphic.builder().id(-1).build(), attackStyle, maxDamage, false);
    }

    protected Hit projectileAttack(Projectile projectile, int animation, Graphic hitgfx, Graphic splashgfx, AttackStyle attackStyle, int maxDamage, boolean ignorePrayer) {
        if (animation != -1)
            npc.animate(animation);
        int delay = projectile.send(npc, target);
        Hit hit = new Hit(npc, attackStyle, null).randDamage(maxDamage).clientDelay(delay);
        hit.afterPostDamage(e -> {
            boolean splash = hit.isBlocked();
            if (target != null) {
                target.graphics(
                        splash ? splashgfx.getId() : hitgfx.getId(),
                        splash ? splashgfx.getHeight() : hitgfx.getHeight(),
                        splash ? splashgfx.getDelay() : hitgfx.getDelay()
                );
                if (splash ? splashgfx.getSoundId() != -1 : hitgfx.getSoundId() != -1) {
                    target.publicSound(
                            splash ? splashgfx.getSoundId() : hitgfx.getSoundId(),
                            splash ? splashgfx.getSoundType() : hitgfx.getSoundType(),
                            splash ? splashgfx.getSoundDelay() : hitgfx.getSoundDelay()
                    );
                }
            }
        });

        target.hit(hit);
        if (attackStyle == null || !attackStyle.isMagical()) {
            defendAnim(delay);
        }
        return hit;
    }

    /**
     * Reset
     */
    @Override
    public void reset() {
        super.reset();
        npc.faceNone(!isDead());
        TargetRoute.reset(npc);
    }

    /**
     * Death
     */
    @Override
    public void startDeath(Hit killHit) {
        setDead(true);
        if (target != null)
            reset();
        Killer killer = getKiller();
        if (npc.deathStartListener != null) {
            npc.deathStartListener.handle(npc, killer, killHit);
            if (npc.isRemoved())
                return;
        }
        npc.startEvent(event -> {
            npc.lock();
            event.delay(1);
            if (info.death_animation != -1)
                npc.animate(info.death_animation);
            if (info.death_ticks > 0)
                event.delay(info.death_ticks);
            dropItems(killer);
            if (Slayer.isTask(killer.player, npc) && killer.player.slayerTask != null) {
                Slayer.onNPCKill(killer.player, npc);
            }
            if (npc.getDef().name.contains("Bloodveld")) {
                if (killer.player.bloodveldKills.getKills() <= 1) {
                    killer.player.openInterface(InterfaceType.SECONDARY_OVERLAY, 660);
                    killer.player.getPacketSender().sendClientScript(3343, "iss", 0xff981f, "Combat Task Completed!", "Task Completed:" + Color.WHITE.wrap(" The Demonic Punching Bag"));
                    Config.THE_DEMONIC_PUNCHING_BAG.set(killer.player, 1);
                    Config.COMBAT_ACHIVEMENTS_OVERVIEW_EASY.increment(killer.player, 1);
                }
            }
            if (npc.getDef().name.contains("Fire giant")) {
                if (killer.player.fireGiantKills.getKills() <= 1) {
                    killer.player.openInterface(InterfaceType.SECONDARY_OVERLAY, 660);
                    killer.player.getPacketSender().sendClientScript(3343, "iss", 0xff981f, "Combat Task Completed!", "Task Completed:" + Color.WHITE.wrap(" The Walking Volcano"));
                    Config.THE_WALKING_VOLCANO.set(killer.player, 1);
                    Config.COMBAT_ACHIVEMENTS_OVERVIEW_EASY.increment(killer.player, 1);
                }
            }
            if (killer != null && killer.player != null) {
                DiaryKCCheck(killer.player, npc);
                if (npc.getDef().killCounter != null)
                    npc.getDef().killCounter.apply(killer.player).increment(killer.player);
                if (info.pet != null && Random.rollDie(info.pet.dropAverage))
                    info.pet.unlock(killer.player);
                int id = npc.getId();
                DailyTaskKills.kills(killer.player, id);
                DemonSlayer.check(killer.player, npc);
            }

            if (npc.deathEndListener != null) {
                npc.deathEndListener.handle(npc, killer, killHit);
                if (npc.isRemoved())
                    return;
            } else if (info.respawn_ticks < 0) {
                npc.remove();
                return;
            }

            if (info.respawn_ticks > 0)
                npc.setHidden(true);
            if (!allowRespawn())
                return;

            event.delay(info.respawn_ticks);
            respawn();
        });
    }

    public final void respawn() {
        broadcastGlobalSpawn(npc);
        npc.animate(info.spawn_animation);
        npc.getPosition().copy(npc.spawnPosition);
        resetKillers();
        restore();
        setDead(false);
        npc.setHidden(false);
        if (npc.respawnListener != null)
            npc.respawnListener.onRespawn(npc);
        npc.unlock();
    }

    private void broadcastGlobalSpawn(NPC npc) {
        if (npc.getId() == 14647) {
            // Broadcast.WORLD.sendNews("<shad=000000>" + Color.BABY_BLUE.wrap("[GLOBAL BOSS] ") + npc.getDef().name + " Has just respawned! GoGoGo!</shad>");
            // NPC Fumusc = new NPC(14651).spawn(2901, 4714, 1);
            //  NPC Umbrac = new NPC(14652).spawn(2901, 4698, 1);
            //  NPC Cruorc = new NPC(14653).spawn(2917, 4698, 1);
            //  NPC Glaciesc = new NPC(14654).spawn(2917, 4714, 1);
            Nex.deadplayers.clear();
        }
    }

    public void setAllowRespawn(boolean allowRespawn) {
        this.allowRespawn = allowRespawn;
    }

    public boolean allowRespawn() {
        return allowRespawn;
    }

    public void dropItems(Killer killer) {
        NPCDef def = npc.getDef();
        Position dropPosition = getDropPosition();
        Player pKiller = killer == null ? null : killer.player;
        if (pKiller == null) {
            return;
        }

        /*
         * Drop table loots
         */
        LootTable t = def.lootTable;
        if (t != null) {
           int rolls = 0;
            int getRoll = DoubleDrops.getRolls(killer.player);
            rolls += getRoll;
            for (int i = 0; i < rolls; i++) {
                List<Item> items = t.rollItems(i == 0);
                if (items != null) {
                    handleDrop(killer, dropPosition, pKiller, items);
                }
            }
        }

        /*
         * Handle giving player vorkaths head after 50 kills.
         */
        vorkathHead(dropPosition, pKiller);

        /*
         * Summer Loot
         */
        //SummerTokens.npcKill(pKiller, npc, dropPosition);
        /*
         * Catacombs loot
         */
        KourendCatacombs.drop(pKiller, npc, dropPosition);

        /*
         * Roll for OSRS wilderness key
         */
/*        if(World.wildernessKeyEvent)
            WildernessKey.rollForWildernessBossKill(pKiller, npc);*/

        /*
         * PvP Item loots
         */
//        Wilderness.rollPvPItemDrop(pKiller, npc, dropPosition);

        /*
         * Roll for wilderness clue key
         */
//        Wilderness.rollClueKeyDrop(pKiller, npc, dropPosition);

        /*
         * Blood Money
         */
        Wilderness.bloodMoneyDrop(pKiller, npc);
        Wilderness.bloodMoneyDropPVP(pKiller, npc);
        /*
         * Resource packs
         */
//        Wilderness.resourcePackWithBoss(pKiller, npc);

    }

    private final int[] runes = {556, 558, 555, 557, 554, 559, 564, 563, 561, 562, 560, 9075, 565, 566, 21880, 4696, 4695, 4698, 4697, 4699, 4694};

    private void vorkathHead(Position dropPosition, Player pKiller) {
        if (pKiller.vorkathKills.getKills() >= 50 && !pKiller.obtained50KCVorkathHead) {
            Item item = new Item(VORKATHS_HEAD);
            GroundItem groundItem = new GroundItem(item).position(dropPosition);
            groundItem.spawn();
            pKiller.obtained50KCVorkathHead = true;
        }
    }


    private void handleDrop(Killer killer, Position dropPosition, Player pKiller, List<Item> items) {
        for (Item item : items) {
            if (item.getId() == 21820) {
                if (BraceletOfEthereum.handleEthereumDrop(pKiller, item)) {
                    continue;
                }
            }

            boolean dropItem = true;
            for (Item equipment : pKiller.getEquipment().getItems()) {
                if (item.getId() == 1751 || item.getId() == 22124) {
                    if (pKiller.isADonator()) {
                        if (item.getDef().notedId > -1)
                            item.setId(item.getDef().notedId);
                    }
                }
            }
            /*
             * Donator Benefit: [Noted dragon bones in wilderness]
             */
            if (item.getId() == 534 || item.getId() == 536 || item.getId() == 6812 || item.getId() == 11943 || item.getId() == 22124) {
                if (pKiller.isADonator() && pKiller.wildernessLevel > 0) {
                    item.setId(item.getDef().notedId);
                }
            }

            /*
             * Donator Benefit: [Noted herbs in wilderness]
             */
            if (item.getDef().name.toLowerCase().contains("grimy")) {
                if (pKiller.isADonator() && pKiller.wildernessLevel > 0) {
                    if (item.getDef().notedId > -1)
                        item.setId(item.getDef().notedId);
                }
            }

            if (item.getDef().name.toLowerCase().contains("statius") ||
                    item.getDef().name.toLowerCase().contains("vesta") ||
                    item.getDef().name.toLowerCase().contains("zuriel")) {
                pKiller.sendMessage("You have been red skulled and tele-blocked because of your loot!");
                pKiller.getCombat().skullHighRisk();
                pKiller.getCombat().teleblock();
            }

            /*
             * Modify drop for specific npc
             */
            if (npc.dropListener != null)
                npc.dropListener.dropping(killer, item);

            /*
             * Global Broadcast
             */
            if (item.lootBroadcast != null || item.getDef().dropAnnounce) {
                getRareDropAnnounce(pKiller, item);
            }

            /*
             * Local Broadcast!
             */
            if (info.local_loot) {
                getLocalAnnounce(pKiller, item);
            }

            pKiller.getCollectionLog().collect(item);

            if (item.getId() == COINS_995) {
                if (wearingRingOfWealth(pKiller)) {
                    pKiller.getInventory().addOrDrop(item);
                    dropItem = false;
                }
            }
            if (item.getId() == COINS_995) {
                if (RingofEndlessRecoil.wearingEndlessI(pKiller)) {
                    pKiller.getInventory().addOrDrop(item);
                    dropItem = false;
                }
            }

            for (int rune : runes) {
                if (item.getId() == rune) {
                    if (wearingRingOfWealth(pKiller)) {
                        pKiller.getInventory().addOrDrop(item);
                        dropItem = false;
                    }
                }
            }

            if (item.getId() == 30258 || item.getId() == 30259 || item.getId() == 30260) {
                if (wearingRingOfWealth(pKiller)) {
                    pKiller.getInventory().addOrDrop(item);
                    dropItem = false;
                }
            }

            if (item.getId() == 6831
                    || item.getId() == 24361  || item.getId() == 24362
                    || item.getId() == 24363  || item.getId() == 24364
                    || item.getId() == 24365  || item.getId() == 24366) {
                if (RingofEndlessRecoil.wearingEndlessI(pKiller)) {
                    pKiller.getInventory().addOrDrop(item);
                    dropItem = false;
                }
            }

            for (Bone value : Bone.values()) {
                if (item.getId() == value.id) {
                    if (pKiller.boneCrusher && pKiller.getInventory().contains(13116) && hasCharges(pKiller)) {
                        BoneCrusher.handleBury(pKiller, item);
                        dropItem = false;
                    }
                }
            }

            for (Ashes value : Ashes.values()) {
                if (item.getId() == value.id) {
                    if (pKiller.boneCrusher && pKiller.getInventory().contains(13116) && hasCharges(pKiller)) {
                        BoneCrusher.handleBury(pKiller, item);
                        dropItem = false;
                    }
                }
            }


            /*
             * Spawn the item on the ground.
             */
            int amount = item.getAmount();
            if(dropItem)
                new GroundItem(item.getId(), amount).position(dropPosition).owner(pKiller).spawn();

        }
    }

    private void getRareDropAnnounce(Player pKiller, Item item) {
        int amount = item.getAmount();
        String message = pKiller.getName() + " received ";
        if (amount > 1)
            message += NumberUtils.formatNumber(amount) + " x " + item.getDef().name;
        else
            message += item.getDef().descriptiveName;
        if (item.lootBroadcast != null) {
            item.lootBroadcast.sendNews(pKiller, message + " from " + npc.getDef().descriptiveName + " at KC: " + npc.getDef().killCounter.apply(pKiller).getKills());
        } else {
            Broadcast.WORLD.sendNews(pKiller, message + " from " + npc.getDef().descriptiveName + " at KC: " + npc.getDef().killCounter.apply(pKiller).getKills());
        }
        RareDropEmbedMessage.sendDiscordMessage(message, npc.getDef().descriptiveName, item.getId(), npc.getDef().killCounter.apply(pKiller).getKills());
    }

    private void getLocalAnnounce(Player pKiller, Item item) {
        npc.localPlayers().forEach(p -> p.sendMessage(Color.DARK_GREEN.wrap(pKiller.getName() + " received a drop: " + NumberUtils.formatNumber(item.getAmount()) + " x " + item.getDef().name)));
    }

    public Position getDropPosition() {
        return npc.getPosition();
    }

    public void restore() {
        for (Stat stat : stats)
            stat.restore();
        npc.resetFreeze();
        npc.cureVenom(0);
    }

    /**
     * Misc
     */
    @Override
    public boolean allowRetaliate(Entity attacker) {
        if (npc.targetPlayer != null && attacker != npc.targetPlayer) //npc has a designated target
            return false;
        if (npc.isLocked())
            return false;
        if (npc.getId() == 8359 || npc.getId() == 10812 || npc.getId() == 10813 || npc.getId() == 11184)
            return false;
        if (!allowRetaliate)
            return false;
        if (target != null && target.getCombat().getTarget() == npc) { //npc is being attacked by target
            boolean prioritizePlayer = target.npc != null && attacker.player != null; //target is an npc and attacker is a player
            return prioritizePlayer;
        }
        return true;
    }

    @Override
    public AttackStyle getAttackStyle() {
        return info.attack_style;
    }

    @Override
    public AttackType getAttackType() {
        return null;
    }

    @Override
    public double getLevel(StatType statType) {
        int i = statType.ordinal();
        return i >= stats.length ? 0 : stats[i].currentLevel;
    }

    public Stat getStat(StatType statType) {
        return stats[statType.ordinal()];
    }

    @Override
    public double getBonus(int bonusType) {
        return bonusType >= bonuses.length ? 0 : bonuses[bonusType];
    }

    @Override
    public Killer getKiller() {
        if (npc.targetPlayer != null) {
            Killer killer = new Killer();
            killer.player = npc.targetPlayer;
            return killer;
        }
        //Player's didn't like this mechanic so we removed it.
       /* if (killers.entrySet().stream().filter(e -> e.getValue().player != null).anyMatch(e -> e.getValue().player.getGameMode().isIronMan()) // ironman did damage
                && killers.entrySet().stream().filter(e -> e.getValue().player != null).anyMatch(e -> !e.getValue().player.getGameMode().isIronMan())) { // but so did a non-ironman :(
            killers.entrySet().removeIf(e -> e.getValue().player != null && e.getValue().player.getGameMode().isIronMan()); // remove all iron men from potential killer list
        }*/
        return super.getKiller();
    }

    @Override
    public int getDefendAnimation() {
        return info.defend_animation;
    }

    public int getMaxDamage() {
        return info.max_damage;
    }

    public npc_combat.Info getInfo() {
        return info;
    }

    @Override
    public double getDragonfireResistance() {
        return 0;
    }

    public final void checkAggression() {
        if (target == null && isAggressive()) {
            target = findAggressionTarget();
            if (target != null)
                faceTarget();
        }
    }

    protected Entity findAggressionTarget() {
        if (npc.localPlayers().isEmpty())
            return null;
        if (npc.hasTarget())
            return null;
        List<Player> targets = npc.localPlayers().stream()
                .filter(this::canAggro)
                .collect(Collectors.toList()); // i don't mind if this is done in a different way as long as it picks a RANDOM target that passes the canAggro check
        if (targets.isEmpty())
            return null;
        return Random.get(targets);
    }

    protected int getAggressiveLevel() {
        if (npc.wildernessSpawnLevel > 0)
            return Integer.MAX_VALUE;
        else if (info.aggressive_level == -1)
            return npc.getDef().combatLevel * 2;
        else
            return info.aggressive_level;
    }

    public boolean isAggressive() {
        return getInfo().aggressive_level != 0;
    }

    protected boolean canAggro(Player player) {
        int aggroLevel = getAggressiveLevel();
        return player.getCombat().getLevel() <= aggroLevel // in level range
                && canAttack(player) // can attack
                && !player.isIdle // player isn't idle
                && (player.inMulti() || !player.getCombat().isDefending(12))
                && DumbRoute.withinDistance(npc, player, getAggressionRange()) // distance and line of sight
                && (npc.inMulti() || (StreamSupport.stream(npc.localNpcs().spliterator(), false)
                .noneMatch(n -> n.getCombat() != null && n.getCombat().getTarget() == player && !n.getCombat().isAttacking(10) && !n.getMovement().isAtDestination()))) // not 100% sure how i feel about this check, but it ensures multiple npcs don't try to go for the same player at the same time in a single-way zone since they wouldn't be able to attack upon reaching
                && (npc.aggressionImmunity == null || !npc.aggressionImmunity.test(player));

    }

    public int getAggressionRange() {
        switch (npc.getId()) {
            case 7554:
                return 25;
            case 2218://Sergeant Grimspike
            case 2217://Sergeant Steelwill
            case 239://KBD
            case 7940://Rev Dragon
            case 319://Coporal Beast
            case 2216://Sergeant Strongstack
            case 5282:
            case 7278:
            case 7241:
            case 1047:
            case 2207:
            case 7286:
            case 2205:
            case 2208:
            case 13192:
            case 9044:
            case 9046:
            case 9047:
            case 9048:
            case 412:
            case 3163://Wingman Skree
            case 3165://Flight Kilisa
            case 3164://Flockleader Geerin
            case 6494://GWD Boss GRAARDOR
            case 6492://GWD Boss KREE_ARRA
            case 6493://GWD Boss COMMANDER_ZILYANA
            case 6495://GWD Boss KRIL_TSUTSAROTH
            case 5535://Cave kraken tenticles
                return 13;
        }
        return npc.wildernessSpawnLevel > 0 ? 2 : 4; //just for gw or generally, might be smarter to do them seperate yeh
    }

    public int getAttackBoundsRange() {
        return 12;
    }

    @Override
    public void faceTarget() {
        npc.face(target);
    }

    /**
     * Handler functions
     */
    public abstract void init();

    public abstract void follow();

    public abstract boolean attack();

    public int getRandomDropCount() {
        return info.random_drop_count;
    }

    public void DiaryKCCheck(Player player, NPC npc) {
        if (npc.getId() == 2215) { //  bandos
            player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.KILL_BANDOS);
        }
        if (npc.getId() == 3129) { // zammy
            player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.KILL_ZAMORAK);
        }
        if (npc.getId() == 3162) { // kree
            player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.KILL_ARMADYL);
        }
        if (npc.getId() == 2205) { // sara
            player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.KILL_SARADOMIN);
        }
        if (npc.getDef().name.equalsIgnoreCase("earth warrior") && player.getPosition().regionId() == 12444) { // sara
            player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.KILL_EARTH_WARRIOR);
        }
        if (npc.getDef().name.equalsIgnoreCase("green dragon") && player.wildernessLevel > 5) { // sara
            player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.KILL_GREEN_DRAGON);
        }
        if (npc.getDef().name.equalsIgnoreCase("ankou") && player.wildernessLevel > 5) { // sara
            player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.KILL_ANKOU);
        }
        if (npc.getDef().name.equalsIgnoreCase("bloodveld") && player.wildernessLevel > 5) { // sara
            player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.KILL_BLOODVELD);
        }
        if (npc.getDef().name.equalsIgnoreCase("abyssal demon") && player.getPosition().regionId() == 13623) { //
            player.getDiaryManager().getMorytaniaDiary().progress(MorytaniaDiaryEntry.KILL_ABYSSAL_DEMON);
        }
        if (npc.getDef().name.equalsIgnoreCase("mithril dragon") && player.getPosition().regionId() == 6995) { //
            player.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.KILL_MITHRIL_DRAGON_KAN);
        }

        if (npc.getDef().name.equalsIgnoreCase("zulrah") || npc.getId() == 2042 || npc.getId() == 2043 || npc.getId() == 2044) { //
            player.getDiaryManager().getWesternDiary().progress(WesternDiaryEntry.KILL_ZULRAH);
        }

        if (npc.getId() == 499) { //
            player.getDiaryManager().getWesternDiary().progress(WesternDiaryEntry.KILL_THERMO);
        }

        if (npc.getDef().name.equalsIgnoreCase("mammoth") && player.wildernessLevel > 0) { //
            player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.KILL_MAMMOTH);
        }

        if (npc.getDef().name.equalsIgnoreCase("ghoul")) { //
            player.getDiaryManager().getMorytaniaDiary().progress(MorytaniaDiaryEntry.KILL_GHOUL);
        }
        if (npc.getDef().name.equalsIgnoreCase("cave horror") && (player.getPosition().regionId() == 14994 | player.getPosition().regionId() == 14995 | player.getPosition().regionId() == 15251)) { //
            player.getDiaryManager().getMorytaniaDiary().progress(MorytaniaDiaryEntry.KILL_CAVE_HORROR);
        }

        if (npc.getDef().name.equalsIgnoreCase("banshee") && player.getPosition().regionId() == 13623) { //
            player.getDiaryManager().getMorytaniaDiary().progress(MorytaniaDiaryEntry.KILL_BANSHEE);
        }
        if (npc.getDef().name.equalsIgnoreCase("cave bug")) { //
            player.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.SLAY_BUG);
        }
        if (npc.getDef().name.equalsIgnoreCase("giant mole")) {
            player.getDiaryManager().getSkillingDiary().progress(SkillingDiaryEntry.KILL_GIANT_MOLE);
        }
        if (npc.getDef().name.equalsIgnoreCase("lava dragon")) {
            player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.KILL_LAVA_DRAGON);
        }
        if (npc.getDef().name.equalsIgnoreCase("chaos elemental")) {
            player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.CHAOS_ELEMENTAL);
        }
        if (npc.getDef().name.equalsIgnoreCase("crazy archaeologist")) {
            player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.CRAZY_ARCHAEOLOGIST);
        }
        if (npc.getDef().name.equalsIgnoreCase("chaos fanatic")) {
            player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.CHAOS_FANATIC);
        }
        if (npc.getDef().name.equalsIgnoreCase("scorpia")) {
            player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.SCORPIA);
        }
        if (npc.getDef().name.equalsIgnoreCase("spiritual warrior")) {
            player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.SPIRITUAL_WARRIOR);
        }
        if (npc.getDef().name.equalsIgnoreCase("spiritual mage")) {
            player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.SPIRITUAL_MAGE);
        }
        if (npc.getDef().name.equalsIgnoreCase("callisto")) {
            player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.CALLISTO);
        }

        if (npc.getDef().name.equalsIgnoreCase("venenatis")) {
            player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.VENENATIS);
        }
        if (npc.getDef().name.equalsIgnoreCase("skeletal wyvern") && (player.getPosition().regionId() == 12181 || player.getPosition().regionId() == 12437)) {
            player.getDiaryManager().getSkillingDiary().progress(SkillingDiaryEntry.KILL_WYVERN);
        }

        if (npc.getDef().name.equalsIgnoreCase("white knight")) {
            player.getDiaryManager().getSkillingDiary().progress(SkillingDiaryEntry.FARMING_SHOP);
        }

        if (npc.getId() == 6611 || npc.getId() == 6612) {
            player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.VETION);
        }
        if (npc.getDef().name.equalsIgnoreCase("dagannoth supreme") || npc.getDef().name.equalsIgnoreCase("dagannoth rex") || npc.getDef().name.equalsIgnoreCase("dagannoth prime")) {
            player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.DAGANNOTH_KINGS);
        }

        if (npc.getDef().name.equalsIgnoreCase("brine rat")) {
            player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.KILL_BRINE_RAT);
        }
        if (npc.getDef().name.equalsIgnoreCase("bronze dragon") && player.getPosition().regionId() == 10643) {
            player.getDiaryManager().getPvmDiary().progress(PvMDiaryEntry.KILL_METAL_DRAGON);
        }
        if (npc.getDef().name.equalsIgnoreCase("iron dragon") && player.getPosition().regionId() == 10643) {
            player.getDiaryManager().getPvmDiary().progress(PvMDiaryEntry.KILL_METAL_DRAGON);
        }
        if (npc.getDef().name.equalsIgnoreCase("steel dragon") && player.getPosition().regionId() == 10643) {
            player.getDiaryManager().getPvmDiary().progress(PvMDiaryEntry.KILL_METAL_DRAGON);
        }
        if (npc.getDef().name.equalsIgnoreCase("iron dragon") && player.getPosition().regionId() == 10899) {
            player.getDiaryManager().getPvmDiary().progress(PvMDiaryEntry.KILL_METAL_DRAGON);
        }
        if (npc.getDef().name.equalsIgnoreCase("steel dragon") && player.getPosition().regionId() == 10899) {
            player.getDiaryManager().getPvmDiary().progress(PvMDiaryEntry.KILL_METAL_DRAGON);
        }

        if (npc.getDef().name.equalsIgnoreCase("Rock Crab") && (player.getPosition().regionId() == 10554 || player.getPosition().regionId() == 10553)) {
            player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.KILL_ROCK_CRAB);
        }
        if (npc.getDef().name.equalsIgnoreCase("black dragon") && player.getPosition().regionId() == 11161) {
            player.getDiaryManager().getSkillingDiary().progress(SkillingDiaryEntry.KILL_BLACK_DRAGON);
        }
        if (npc.getDef().name.equalsIgnoreCase("dust devil") && player.getPosition().inBounds(new Bounds(3168, 9344, 3327, 9407, 0)) && player.getEquipment().wearsSlayerHelm()) {
            player.getDiaryManager().getMinigamesDiary().progress(MinigamesDiaryEntry.KILL_DUST_DEVIL);
        }
        if (npc.getDef().name.contains("Lizard") && player.getPosition().inBounds(new Bounds(3143, 2753, 3504, 3179, 0))) {
            player.getDiaryManager().getMinigamesDiary().progress(MinigamesDiaryEntry.KILL_LIZARDS_DESERT);
        }
        if (npc.getId() == 1838 || npc.getId() == 1839) {
            player.getDiaryManager().getSkillingDiary().progress(SkillingDiaryEntry.KILL_DUCK);
        }
        if (npc.getDef().name.contains("snake") && player.getPosition().inBounds(new Bounds(3143, 2753, 3504, 3179, 0))) {
            player.getDiaryManager().getMinigamesDiary().progress(MinigamesDiaryEntry.KILL_SNAKES_DESERT);
        }
        if (npc.getDef().name.equalsIgnoreCase("bandit") && player.getPosition().inBounds(new Bounds(3143, 2753, 3504, 3179, 0))) {
            player.getDiaryManager().getMinigamesDiary().progress(MinigamesDiaryEntry.KILL_BANDIT);
        }
        if (npc.getDef().name.equalsIgnoreCase("vulture") && player.getPosition().inBounds(new Bounds(3143, 2753, 3504, 3179, 0))) {
            player.getDiaryManager().getMinigamesDiary().progress(MinigamesDiaryEntry.KILL_VULTURE);
        }

        if (npc.getDef().name.equalsIgnoreCase("Warped Jelly")) {
            //player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.KILL_JELLIES);
        }

        if (npc.getDef().name.equalsIgnoreCase("sand crab")) {
            //player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.KILL_SANDCRAB);
        }

        if (npc.getDef().name.equalsIgnoreCase("lizardman shaman")) {
            //player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.KILL_LIZARDSHAMAN);
        }

        if (npc.getId() == 7278 && player.getPosition().regionId() == 6813) {
            //player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.KILL_NECHRYAEL);
        }

        if (npc.getDef().name.equalsIgnoreCase("Abyssal demon") && player.getPosition().regionId() == 6813) {
            //player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.KILL_ABYSSAL);
        }

        if (npc.getDef().name.equalsIgnoreCase("skotizo")) {
            //player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.KILL_SKOTIZO);
        }


    }
}
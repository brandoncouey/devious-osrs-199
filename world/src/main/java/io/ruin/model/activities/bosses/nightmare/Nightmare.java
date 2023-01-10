package io.ruin.model.activities.bosses.nightmare;


import io.ruin.model.World;
import io.ruin.model.combat.CombatUtils;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;

import java.util.ArrayList;

public class Nightmare extends NPC {

    public static final int NO_TELEPORT = 0, CENTER = 1, EDGE = 2;

    private int stage = -1, specialDelta = 60, stageDelta = -1, sleepwalkerCount = 0, parasiteDelta = -1, flowerRotary = -1, flyDirection = -1;

    private TotemPlugin[] totems;

    private final Position base;

    private boolean shield;

    public Nightmare(int id, Position base) {
        super(id);
        this.base = base;
        init();
    }

    static {
        NPCAction.register(9461, "disturb", (player, npc) -> {
            player.startEvent(event -> {
                player.dialogue(new MessageDialogue("" + player.getName() + ", would you like to fight The Nightmare?"), new OptionsDialogue("Choose an option",
                        new Option("Start a new instance", () -> {
                            ArrayList<Player> team = new ArrayList<Player>();
                            team.add(player);
                            player.animate(2796);
                            NightmareEvent.createInstance(team);
                        }),
                        new Option("Join a friends instance", () -> {
                            if (player.getInventory().contains(6) || player.getInventory().contains(8) || player.getInventory().contains(10) || player.getInventory().contains(12)) {
                                player.sendMessage("You cannot bring a cannon into The Nightmare");
                                return;
                            }
                            player.nameInput("Enter friend's name:", key -> {
                                player.animate(2796);
                                NightmareEvent.joinInstance(key, player);
                            });
                        }),
                        new Option("No thanks.")));
                return;
            });

        });
    }

    @Override
    public boolean isMovementBlocked(boolean message, boolean ignoreFreeze) {
        return getFlyDirection() != -1 || get("next_attack") == null || getId() >= 9431;
    }

    @Override
    public int getMaxHp() {
        return shield ? 2400 : 2400;
    }

    public ArrayList<Position> getSleepwalkerPositions() {
        ArrayList<Position> Positions = new ArrayList<Position>();
        int[][] spots = new int[][]{{26, 24}, {28, 24}, {36, 24}, {38, 24}, {41, 21}, {41, 19}, {41, 11}, {41, 9}, {38, 6}, {36, 6}, {28, 6}, {26, 6}, {23, 9}, {23, 11}, {23, 19}, {23, 21}, {23, 14}, {23, 16}, {41, 16}, {41, 14}, {31, 5}, {33, 5}, {31, 25}, {33, 25}};
        for (int[] spot : spots) {
            Positions.add(getBase().translated(spot[0], spot[1], 0));
        }
        return Positions;
    }

    @Override
    public int animate(int id) {
        if (getId() == 9431 && id != 8604 && id > 0) {
            return 0;
        }
        return super.animate(id);
    }

    @Override
    public int hit(Hit... hits) {
        if (queuedHits == null)
            queuedHits = new ArrayList<>();
        int damage = 0;
        boolean process = true;
        boolean dead = false;
        for (Hit hit : hits) {
            Entity attacker = hit.attacker;

            if ((attacker instanceof TotemPlugin) && stageDelta == -1 && stage < 2) {
                stageDelta = 6;
                toggleShield();
                process = false;
            } else if ((attacker instanceof TotemPlugin) && stage < 2) {
                process = false;
                queuedHits.add(hit);
            } else if (attacker instanceof TotemPlugin) {
                if (stage >= 2) {
                    dead = true;
                }
            }

            if (attacker instanceof Parasite) {
                hit.type = HitType.HEAL;
                hit.damage = (Misc.random(100));
            }

            if (attacker.isPlayer() && !isShield()) {
                process = false;
            }

            if (process && hit.defend(this)) {
                if (!isLocked(LockType.FULL_NULLIFY_DAMAGE))
                    queuedHits.add(hit);
                damage += hit.damage;
            }

            if (shield && 40 >= getCombat().getStat(StatType.Hitpoints).currentLevel && stage <= 2) {
                toggleShield();
                for (Player p : getPosition().getRegion().players) {
                    p.sendMessage("<col=ff0000>As the Nightmare's shield fails, the totems in the area are activated!");
                }
                process = false;
            }

        }
        Hit baseHit = hits[0];
        if (process) {
            if (baseHit.type.resetActions) {
                if (player != null)
                    player.resetActions(true, false, false);
                else
                    npc.resetActions(false, false);
            }
            if (baseHit.attacker != null) {
                if (baseHit.attacker.player != null && baseHit.attackStyle != null) {
                    if (player != null) //important that this happens here for things that hit multiple targets
                        baseHit.attacker.player.getCombat().skull(player);
                    if (baseHit.attackSpell == null)
                        CombatUtils.addXp(baseHit.attacker.player, this, baseHit.attackStyle, baseHit.attackType, damage);
                }
                getCombat().updateLastDefend(baseHit.attacker);
            }
        }
        if (getCombat().getStat(StatType.Hitpoints).currentLevel <= 0) {
            for (TotemPlugin t : totems) {
                t.setChargeable(false);
            }
        }
        if (dead && getCombat().getStat(StatType.Hitpoints).currentLevel > 0) {
            super.hit(new Hit().fixedDamage(getCombat().getStat(StatType.Hitpoints).currentLevel));
            getCombat().getStat(StatType.Hitpoints).currentLevel = 0;
        }
        return damage;
    }


    @Override
    public NightmareCombat getCombat() {
        if (!(super.getCombat() instanceof NightmareCombat)) {
            initCombat();
        }
        return (NightmareCombat) super.getCombat();
    }

    @Override
    public void process() {
        if (stage < 0) {
            super.process();
            return;
        }
        if (stageDelta > 0 && --stageDelta == 0 && stage < 2) {
            stageDelta = -1;
            getCombat().reset();
            transform(9431);
            animate(-1);
            getCombat().setSpecial(SpecialAttacks.SLEEPWALKERS);
            getCombat().attack();
            specialDelta = 30;
            stage++;
        }

        if (stageDelta == 4) {
            teleport(getSpawnPosition());
        }
        if (stageDelta != -1) {
            super.process();
            return;
        }

        if (isAttackable() && !getCombat().hasAttackDelay() && getCombat().getTarget() == null) {
            for (Entity mob : getPossibleTargets()) {
                getCombat().setTarget(mob);
                break;
            }
        }

        if (specialDelta == 0 && stage < 3) {
            SpecialAttacks[][] attacks = {
                    {
                            SpecialAttacks.GRASPING_CLAWS,
                            SpecialAttacks.FLOWER_POWER,
                            SpecialAttacks.HUSKS,
                            SpecialAttacks.GRASPING_CLAWS,
                            SpecialAttacks.FLOWER_POWER,
                            SpecialAttacks.HUSKS,
                            SpecialAttacks.GRASPING_CLAWS,
                            SpecialAttacks.FLOWER_POWER,
                            SpecialAttacks.HUSKS,
                            SpecialAttacks.GRASPING_CLAWS,
                            SpecialAttacks.FLOWER_POWER,
                            SpecialAttacks.HUSKS,
                    },
                    {
                            SpecialAttacks.GRASPING_CLAWS,
                            SpecialAttacks.CURSE,
//							SpecialAttacks.PARASITES,
                            SpecialAttacks.GRASPING_CLAWS,
                            SpecialAttacks.CURSE,
//							SpecialAttacks.PARASITES,
                            SpecialAttacks.GRASPING_CLAWS,
                            SpecialAttacks.CURSE,
//							SpecialAttacks.PARASITES,
                    },
                    {
                            SpecialAttacks.GRASPING_CLAWS,
                            SpecialAttacks.GRASPING_CLAWS,
                            SpecialAttacks.GRASPING_CLAWS,
//							SpecialAttacks.SURGE,
                            SpecialAttacks.SPORES,
                            SpecialAttacks.SPORES,
                            SpecialAttacks.SPORES
                    }};
            SpecialAttacks nextAttack = attacks[stage][Misc.random(attacks[stage].length - 1)];
//			nextAttack = SpecialAttacks.SPORES;
            if (nextAttack.teleportOption != NO_TELEPORT) {
                int diffX = 0, diffY = 0;
                if (nextAttack.teleportOption == EDGE) {
                    setFlyDirection(0);
                    switch (getFlyDirection()) {
                        case 0:
                            diffY = -10;
                            break;
                        case 1:
                            diffX = 10;
                            break;
                        case 2:
                            diffY = -10;
                            break;
                        case 3:
                            diffX = -10;
                            break;
                    }
                }
                Position dest = getSpawnPosition().translate(diffX, diffY, 0);
                teleport(dest);
            }
            set("next_attack", nextAttack);
            getCombat().delayAttack(12);
        }
        if (specialDelta == -4) {
            getCombat().setSpecial(remove("next_attack"));
            getCombat().attack();
        }
        if (--specialDelta == -10) {
            getCombat().setSpecial(null);
            specialDelta = 60;
        }
        super.process();
    }

    public ArrayList<Entity> getPossibleTargets() {
        return getPossibleTargets(14, true, false);
    }

    public ArrayList<Entity> getPossibleTargets(int ratio, boolean players, boolean npcs) {
        ArrayList<Entity> possibleTargets = new ArrayList<Entity>();
        if (players) {
            for (Player player : World.players) {
                if (player == null || player.getCombat().isDead() || player.getPosition().distance(this.getCentrePosition()) > ratio) {
                    continue;
                }
                possibleTargets.add(player);
            }
        }
        if (npcs) {
            for (NPC npc : World.npcs) {
                if (npc == null || npc == this || npc.getCombat().isDead() || npc.getCentrePosition().distance(this.getCentrePosition()) > ratio) {
                    continue;
                }
                possibleTargets.add(npc);
            }
        }
        return possibleTargets;
    }

    public boolean isAttackable() {
        return stage > -1 && getId() < 9430 && stageDelta == -1;
    }

    private void teleport(Position dest) {
        animate(8607);
        getCombat().reset();
        addEvent(event -> {
            event.delay(1);
            getMovement().teleport(dest);
            event.delay(1);
            transform(stageDelta == -1 ? 9425 + getStage() : 9431);
            animate(8609);
        });
    }

    public void toggleShield() {
        shield = !shield;
        if (getPosition().getRegion().players.size() <= 1) {
            getCombat().getStat(StatType.Hitpoints).alter(shield ? 800 : 2400 - (800 * stage));
            for (TotemPlugin totem : totems) {
                totem.setChargeable(!isShield());
            }
        } else {
            getCombat().getStat(StatType.Hitpoints).alter(shield ? 800 + (200 * getPosition().getRegion().players.size()) : 2400 - (800 * stage));
            for (TotemPlugin totem : totems) {
                totem.setChargeable(!isShield());
            }
        }
    }

    public boolean isShield() {
        return shield;
    }

    public void setShield(boolean shield) {
        this.shield = shield;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public TotemPlugin[] getTotems() {
        return totems;
    }

    public void setTotems(TotemPlugin[] totems) {
        this.totems = totems;
    }

    public int getFlowerRotary() {
        return flowerRotary;
    }

    public void setFlowerRotary(int flowerRotary) {
        this.flowerRotary = flowerRotary;
    }

    public int getFlyDirection() {
        return flyDirection;
    }

    public void setFlyDirection(int flyDirection) {
        this.flyDirection = flyDirection;
    }

    public int getSleepwalkerCount() {
        return sleepwalkerCount;
    }

    public void setSleepwalkerCount(int sleepwalkerCount) {
        this.sleepwalkerCount = sleepwalkerCount;
    }

    public int getParasiteDelta() {
        return parasiteDelta;
    }

    public void setParasiteDelta(int parasiteDelta) {
        this.parasiteDelta = parasiteDelta;
    }

    public Position getBase() {
        return base;
    }

    public void initCombat() {
        combat = new NightmareCombat(this);
        combat.init(this, getDef().combatInfo);
    }
}

package io.ruin.model.activities.bosses.nightmare;


import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.spells.modern.CrumbleUndead;
import io.ruin.model.stat.StatType;

public class TotemPlugin extends NPC {

    private Nightmare nightmare;

    private boolean chargeable, charged;

    private int hitDelta = -1;
    private final int spawnId;

    public TotemPlugin(int id, Position spawn) {
        super(id);
        spawnId = id;
        spawn(spawn);
        lock(LockType.FULL_CANT_ATTACK);
        getCombat().getStat(StatType.Hitpoints).alter(1);
        skipMovementCheck = true;
    }

    @Override
    public void process() {
        super.process();
        if (hitDelta > 0 && --hitDelta == 0) {
            hitDelta = -1;
            Hit dmg = new Hit().fixedDamage(800);
            dmg.attacker = this;
            nightmare.hit(dmg);
        }
        npc.hitListener = new HitListener().postDefend(hit -> {
                hit.damage = npc.getHp();
        });
        if (getId() != spawnId) {
            transform(getId());
        }
    }

    @Override
    public int hit(Hit... hits) {
        if (charged) return 0;
        for (Hit hit : hits) {
            hit.type = HitType.HEAL;
            if (hit.attacker != null && hit.attacker.isPlayer()) {
                    hit = new Hit().fixedDamage(hit.minDamage * 100);
                    hit.type = HitType.HEAL;
            }
        }
        int rt = super.hit(hits);
        getCombat().getStat(StatType.Hitpoints).alter(getCombat().getStat(StatType.Hitpoints).currentLevel + (rt * 2));
        if (getCombat().getStat(StatType.Hitpoints).currentLevel >= getCombat().getStat(StatType.Hitpoints).fixedLevel) {
            transform(spawnId + 2);
            charged = true;
            chargeable = false;
            boolean all = true;
            for (TotemPlugin t : nightmare.getTotems()) {
                if (!t.charged) {
                    all = false;
                }
            }
            if (all) {
                for (TotemPlugin t : nightmare.getTotems()) {
                    t.charged = false;
                    t.getCombat().getStat(StatType.Hitpoints).alter(1);
                    Projectile p = new Projectile(1768, 130, 110, 30, 56, 10, 10, 64);
                    p.send(t, nightmare);
                    t.transform(t.spawnId);
                    t.breakCombat();
                    t.hitDelta = 3;
                }
                for (Player player : getPosition().getRegion().players) {
                    player.sendMessage("<col=ff0000>All four totems are fully charged.");
//					player.getWidgetManager().close(Widget.NIGHTMARE_TOTEM_HEALTH);
                }
            }
        }
        return rt;
    }

    public void breakCombat() {
        for (Player player : getPosition().getRegion().players) {
            if (player.getCombat().getTarget() == this) {
                player.getCombat().reset();
            }
        }
    }

    @Override
    public boolean isMovementBlocked(boolean message, boolean ignoreFreeze) {
        return true;
    }

    public Nightmare getNightmare() {
        return nightmare;
    }

    public void setNightmare(Nightmare nightmare) {
        this.nightmare = nightmare;
    }

    public boolean isChargeable() {
        return chargeable;
    }

    public void setChargeable(boolean chargeable) {
        this.chargeable = chargeable;
        actions = new NPCAction[5];
        for (int i = 0; i < 5; i++) {
            actions[i] = (player, npc) -> {
                player.getCombat().setTarget(npc);
                player.face(npc);
            };
        }
        if (chargeable) {
            transform(spawnId + 1);
        }
    }
}

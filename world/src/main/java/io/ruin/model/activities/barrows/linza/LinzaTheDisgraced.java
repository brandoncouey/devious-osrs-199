package io.ruin.model.activities.barrows.linza;

import io.ruin.api.utils.Random;
import io.ruin.cache.NPCDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.stat.StatType;

public class LinzaTheDisgraced extends NPCCombat {

    private static final Position SPAWN_POSITION = new Position(1364, 10265, 0);
    private final Projectile FIRE_WAVE = new Projectile(156, 43, 31, 51, 56, 10, 16, 64);


    private enum Form {
        VERAC(15093),
        TORAG(15092),
        KARIL(15091),
        GUTHAN(15090),
        AHRIM(15089),
        DHAROK(15088),
        ;

        Form(int npcId) {
            this.npcId = npcId;
        }

        private final int npcId;
    }

    private LinzaTheDisgraced.Form currentForm = LinzaTheDisgraced.Form.VERAC;


    static {
        for (int id : new int[]{15088, 15089, 15090, 15091, 15092, 15093})
            NPCDef.get(id).ignoreOccupiedTiles = true;
    }

    private void postDamage(Hit hit) {
        double hpRatio = (double) npc.getHp() / npc.getMaxHp();
        if (hpRatio == 0)
            return;
        if ((currentForm == Form.VERAC && hpRatio < 0.85)
                || (currentForm == Form.TORAG && hpRatio < 0.65)
                || (currentForm == Form.KARIL && hpRatio < 0.55)
                || (currentForm == Form.GUTHAN && hpRatio < 0.45)
                || (currentForm == Form.AHRIM && hpRatio < 0.35)
                || (currentForm == Form.DHAROK && hpRatio < 0.25)) {
            nextForm();
        }
    }

    private void postDefend(Hit hit) {
        if (currentForm != Form.DHAROK) {
            hit.damage *= 0.25;
        }
    }

    private void nextForm() {
        if (currentForm == LinzaTheDisgraced.Form.DHAROK)
            return;
        LinzaTheDisgraced.Form nextForm = LinzaTheDisgraced.Form.values()[currentForm.ordinal()];
        npc.addEvent(event -> {
            npc.lock();
            currentForm = nextForm;
            npc.transform(nextForm.npcId);
            npc.face(target);
            npc.unlock();
        });
    }

    @Override
    public void init() {
        npc.hitListener = new HitListener().postDamage(this::postDamage).postDefend(this::postDefend);
//        npc.deathEndListener = (entity, killer, killHit) -> onDeath();
        npc.attackBounds = new Bounds(npc.getPosition(), 20);
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (npc.isLocked())
            return false;
        if (!withinDistance(6))
            return false;
        specialAttack();
        return true;
    }

    private void ahrimAttack() {
        if (currentForm == Form.AHRIM) { // 2 attacks
            npc.graphics(155, 92, 0);
            npc.publicSound(162, 1, 0);
            npc.animate(727);
            Hit hit = projectileAttack(FIRE_WAVE, 1167, AttackStyle.MAGIC, info.max_damage);
            if (Random.rollDie(4)) {
                target.player.getStats().get(StatType.Strength).drain(5);
                target.graphics(400);
            }
        }
    }

    private void dharokAttack() {
        if (currentForm == Form.DHAROK) { // 2 attacks
            Hit hit = basicAttack();
            if (Random.rollDie(4))
                npc.animate(2067);
            hit.boostDamage((npc.getMaxHp() - npc.getHp()) * 0.01);
        }
    }

    private void karilAttack() {
        if (currentForm == Form.KARIL) { // 2 attacks
            projectileAttack(Projectile.BOLT, 2075, AttackStyle.RANGED, info.max_damage);
            if (Random.rollDie(4)) {
                target.graphics(401, 100, 0);
                target.player.getStats().get(StatType.Magic).drain(5);
            }
        }
    }

    private void veracAttack() {
        if (currentForm == Form.VERAC) { // 2 attacks
            basicAttack(2062, info.attack_style, 15).ignorePrayer();
        }
    }

    private void toragAttack() {
        if (currentForm == Form.TORAG) { // 2 attacks
            if (Random.rollDie(4)) {
                npc.animate(2068);
                target.graphics(399, 0, 0);
                target.player.getMovement().drainEnergy(20);
            }
        }
    }

    private void guthanAttack() {
        if (currentForm == Form.GUTHAN) { // 2 attacks
            npc.animate(2080);
            Hit hit = new Hit(npc, info.attack_style).randDamage(info.max_damage);
            hit.postDamage(t -> {
                if (hit.damage > 0 && Random.rollDie(4, 1)) {
                    t.graphics(398);
                    npc.incrementHp(hit.damage);
                }
            });
            target.hit(hit);
        }
    }


    private void specialAttack() {
        switch (currentForm) {
            case AHRIM:
                ahrimAttack();
                break;
            case VERAC:
                veracAttack();
                break;
            case DHAROK:
                dharokAttack();
                break;
            case KARIL:
                karilAttack();
                break;
            case TORAG:
                toragAttack();
                break;
            case GUTHAN:
                guthanAttack();
                break;
        }
    }
}
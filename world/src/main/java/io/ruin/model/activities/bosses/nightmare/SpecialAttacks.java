package io.ruin.model.activities.bosses.nightmare;

import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.utility.Misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public enum SpecialAttacks {

    GRASPING_CLAWS(8598, Nightmare.NO_TELEPORT) {
        @Override
        public void run(Nightmare nm) {
            ArrayList<Position> tiles = new ArrayList<>();
            for (int x = 23; x < 41; x++) {
                l:
                for (int y = 6; y < 24; y++) {
                    Position pos = nm.getBase().translated(x, y, 0);
                    for (NPC n : World.npcs) {
                        if (n.getCentrePosition().distance(pos) < (n.getSize() > 1 ? 2 : 1)) {
                            continue l;
                        }
                    }
                    if (Misc.random(10) < 3) {
                        tiles.add(pos);
                    }
                }
            }
            for (Position pos : tiles) {
                World.sendGraphics(1767, 0, 0, pos.getX(), pos.getY(), pos.getZ());
            }
            nm.addEvent(event -> {
                event.delay(2);
                for (Player p : World.players) {
                    for (Position t : tiles) {
                        if (p.getPosition().equals(t)) {
                            p.hit(new Hit().randDamage(50));
                        }
                    }
                }
            });
        }

    },

    SLEEPWALKERS(-1, Nightmare.NO_TELEPORT) {
        @Override
        public void run(Nightmare nm) {
            for (Player player : nm.getPosition().getRegion().players) {
                player.sendMessage("<col=ff0000>The Nightmare begins to charge up a devastating attack.");
            }
            nm.addEvent(event -> {
                event.delay(1);
                int count = nm.getPosition().getRegion().players.size();
                ArrayList<Position> spots = nm.getSleepwalkerPositions();
                Collections.shuffle(spots);
                for (int i = 0; i < count; i++) {
                    int id = 9446;
                    Sleepwalker sw = new Sleepwalker(id);
                    sw.spawn(spots.get(i).getX(), spots.get(i).getY(), spots.get(i).getZ());
                    sw.setNm(nm);
                }
                event.delay(2);
                nm.animate(8604);
                event.delay(1);
                for (Player player : nm.getPosition().getRegion().players) {
                    player.graphics(1782);
                }
                event.delay(8);
                for (Player player : nm.getPosition().getRegion().players) {
                    player.hit(new Hit().fixedDamage(nm.getSleepwalkerCount() * 5));
                }
                nm.transform(9425 + nm.getStage());
                nm.setSleepwalkerCount(0);
            });
        }

    },

    HUSKS(8599, Nightmare.NO_TELEPORT) {
        @Override
        public void run(Nightmare nm) {
            int size = nm.getPosition().getRegion().players.size() > 1 ? nm.getPosition().getRegion().players.size() / 2 : nm.getPosition().getRegion().players.size();
            ArrayList<Entity> targets = nm.getPossibleTargets(64, true, false);
            Collections.shuffle(targets);
            for (int i = 0; i < size; i++) {
                Player target = (Player) targets.get(i);
                int rX = ((target.getPosition().getRegion().id >> 8) << 6), rY = ((target.getPosition().getRegion().id & 0xFF) << 6);
                Position[] pos = null;
                if (target.getPosition().getX() - rX >= 23 || (target.getPosition().getX() - rX <= 41) || target.getPosition().getY() - rY >= 6 || (target.getPosition().getY() - rY <= 24)) {
                    if (target.getPosition().getY() - rY >= 6 || (target.getPosition().getY() - rY <= 24)) {
                        pos = new Position[]{target.getPosition().translated(-1, 0, 0), target.getPosition().translated(1, 0, 0)};
                    } else {
                        pos = new Position[]{target.getPosition().translated(0, 1, 0), target.getPosition().translated(0, -1, 0)};
                    }
                } else {
                    if (Misc.random(10) > 5) {
                        pos = new Position[]{target.getPosition().translated(-1, 0, 0), target.getPosition().translated(1, 0, 0)};
                    } else {
                        pos = new Position[]{target.getPosition().translated(0, 1, 0), target.getPosition().translated(0, -1, 0)};
                    }
                }
                if (pos != null) {
                    Husk husk = new Husk(9466, target, nm);
                    husk.spawn(pos[0]);
                    Husk husk2 = new Husk(9467, target, nm);
                    husk2.spawn(pos[1]);
                }
            }
        }

    },

    FLOWER_POWER(8601, Nightmare.CENTER) {
        @Override
        public void run(Nightmare nm) {
            if (nm.getFlowerRotary() != -1) {
                return;
            }
            int rand = Misc.random(FlowerRotary.values().length - 1);
            for (Player p : nm.getPosition().getRegion().players) {
                p.sendMessage("<col=ff0000>The Nightmare splits the area into segments!");
            }
            nm.setFlowerRotary(rand);
            FlowerRotary pattern = FlowerRotary.values()[rand];
            Position center = nm.getBase().translated(32, 15, 0);
            ArrayList<GameObject> flowers = new ArrayList<>();
            for (int i = 0; i < 11; i++) {
                GameObject lightFlower = new GameObject(37743, center.translated(0, pattern.getLight()[1] * i, 0), 10, 0);
                GameObject lightFlower2 = new GameObject(37743, center.translated(pattern.getLight()[0] * i, 0, 0), 10, 0);
                GameObject darkFlower = new GameObject(37740, center.translated(pattern.getDark()[0] * i, 0, 0), 10, 0);
                GameObject darkFlower2 = new GameObject(37740, center.translated(0, pattern.getDark()[1] * i, 0), 10, 0);
                flowers.add(lightFlower);
                flowers.add(lightFlower2);
                flowers.add(darkFlower);
                flowers.add(darkFlower2);
                lightFlower.spawn();
                lightFlower2.spawn();
                darkFlower.spawn();
                darkFlower2.spawn();
            }
            nm.addEvent(event -> {
                event.delay(1);
                for (GameObject flower : flowers) {
                    flower.animate(flower.getDef().animationID + 1);
                }
                event.delay(1);
                for (GameObject flower : flowers) {
                    flower.setId(flower.id + 1);
                }
                event.delay(6);
                for (GameObject flower : flowers) {
                    flower.animate(flower.getDef().animationID + 1);
                }
                event.delay(1);
                for (GameObject flower : flowers) {
                    flower.setId(flower.id + 1);
                }
                event.delay(10);
                for (GameObject flower : flowers) {
                    flower.animate(flower.getDef().animationID + 1);
                }
                for (Player p : nm.getPosition().getRegion().players) {
                    if (!pattern.safe(center, p.getPosition())) {
                        p.sendMessage("You failed to make it back to the safe area.");
                        p.hit(new Hit().randDamage(50));
                    }
                }
                event.delay(1);
                for (GameObject flower : flowers) {
                    flower.remove();
                }
                nm.setFlowerRotary(-1);
            });

        }

    },

    CURSE(8600, Nightmare.NO_TELEPORT) {
        @Override
        public void run(Nightmare nm) {
//			for (Player p : nm.getPosition().getRegion().players) {
//				p.getWidgetManager().open(Widget.NIGHTMARE_CURSE_OVERLAY);
//			}
            nm.addEvent(event -> {
                event.delay(2);
                for (Player p : nm.getPosition().getRegion().players) {
                    if (p.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES)) {
                        p.getPrayer().deactivate(Prayer.PROTECT_FROM_MISSILES);
                        p.getPrayer().toggle(Prayer.PROTECT_FROM_MELEE);
                    } else if (p.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE)) {
                        p.getPrayer().deactivate(Prayer.PROTECT_FROM_MELEE);
                        p.getPrayer().toggle(Prayer.PROTECT_FROM_MAGIC);
                    } else if (p.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC)) {
                        p.getPrayer().deactivate(Prayer.PROTECT_FROM_MAGIC);
                        p.getPrayer().toggle(Prayer.PROTECT_FROM_MISSILES);
                    }
                    p.set("nightmare_curse", System.currentTimeMillis() + 30000);
                    p.sendMessage("<col=ff0000>The Nightmare has cursed you, shuffling your prayers!");
                }
                event.delay(1);
//				for (Player p : nm.getPosition().getRegion().player) {
//					p.getWidgetManager().close(Widget.NIGHTMARE_CURSE_OVERLAY);
//				}
            });
        }

    },

    PARASITES(8606, Nightmare.NO_TELEPORT) {
        @Override
        public void run(Nightmare nm) {
            for (Entity victim : nm.getPossibleTargets()) {
                Player p = (Player) victim;
                if (Misc.random(6) == 3) continue;
                Projectile pr = new Projectile(1770, 110, 90, 30, 56, 10, 10, 64);
                pr.send(nm, victim);
                p.sendMessage("<col=ff0000>The Nightmare has impregnated you with a deadly parasite!");
                Config.IMPREGNANTED.set(p, 1);
                p.set("nightmare_babydaddy", true);
            }
            final List<Parasite> parasites = new ArrayList<Parasite>();
            nm.addEvent(event -> {
                event.delay(28);
                for (Entity victim : nm.getPossibleTargets()) {
                    if (victim.get("nightmare_babydaddy") != null) {
                        Player p = (Player) victim;
                        p.sendMessage("<col=ff0000>The parasite bursts out of you, fully grown!");
                        Config.IMPREGNANTED.set(p, 0);
                        p.graphics(1779);
                    }
                }
                event.delay(2);
                for (Entity victim : nm.getPossibleTargets()) {
                    if (victim.get("nightmare_babydaddy") != null) {
                        victim.graphics(1765);
                        Parasite parasite = (Parasite) new Parasite(Misc.random(5) == 3 ? 9469 : 9468).spawn(victim.getPosition().copy());
                        parasites.add(parasite);
                        victim.remove("nightmare_babydaddy");
                    }
                }
                event.delay(3);
                for (Parasite parasite : parasites) {
                    parasite.unlock();
                    parasite.getCombat().setTarget(nm);
                    parasite.face(nm);
                }
            });
        }
    },


    SURGE(8597, Nightmare.EDGE) {
        @Override
        public void run(Nightmare nm) {
//			ForceMovement forceMovement = ForceMovement.create(0, 10, 0, 18, 33, 60, WalkingQueue.NORTH);
//			forceMovement.setDestination(nm.getPosition().translated(0, 18, 0));
//			forceMovement.setDelay(3); //this needs to be set as a param
//			World.getWorld().submit(new Tickable(1) {
//
//				@Override
//				public void execute() {
//					nm.playAnimation(Animation.create(8597));
//					nm.setForceWalk(forceMovement, true);
//					nm.getUpdateFlags().flag(UpdateFlag.FORCE_MOVEMENT);
//					nm.face(forceMovement.getDestination());
//					this.stop();
//				}
//			});
        }

    },

    SPORES(8599, Nightmare.NO_TELEPORT) {
        @Override
        public void run(Nightmare nm) {
            for (Player player : nm.getPosition().getRegion().players) {
                player.sendMessage("<col=ff0000>The Nightmare summons some infectious spores!");
            }
            int[][] spores = {{32, 23}, {37, 20}, {40, 15}, {37, 10}, {32, 7}, {27, 10}, {24, 15}, {28, 15}, {32, 18}, {36, 15}, {32, 12}};
            for (int i = 0; i < spores.length; i++) {
                Spore spore = new Spore(nm.getBase().translated(spores[i][0], spores[i][1], 0));
                nm.addEvent(event -> {
                    event.delay(1);
                    spore.animate(spore.getDef().animationID + 1);
                    event.delay(3);
                    spore.setId(37739);
                    //spore.setId(37739, nm.getPossibleTargets()); PROPER ONE JUST NEED TO FIX THE ERROR
                });
                spore.spawn();
            }
        }

    };

    int animation;

    int teleportOption;

    SpecialAttacks(int animation, int teleportOption) {
        this.animation = animation;
        this.teleportOption = teleportOption;
    }

    public void run(Nightmare nm) {

    }

}


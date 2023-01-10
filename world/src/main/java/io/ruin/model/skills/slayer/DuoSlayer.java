package io.ruin.model.skills.slayer;

import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;

public class DuoSlayer {

    public static void findPartner(Player player) {
        if (player.isLocked()) {
            player.sendMessage("Please finish what you are doing first.");
            return;
        }

        //Invalid duo partner? Let's reset the partner
        if (player.isDuoSlayerPartner() && player.getDuoSlayerPartnerName() == null)
            player.setDuoSlayerPartner(false);

        //Valid duo partner & valid duo partner name? No need to do anything.
        if (player.isDuoSlayerPartner() && !player.getDuoSlayerPartnerName().isEmpty() && player.getDuoSlayerPartnerName() != null) {
            player.dialogue(
                    new OptionsDialogue(player.getDuoSlayerPartnerName() + " is currently your Duo Slayer partner.",
                            new Option("Leave Duo Slayer", plr -> {
                                Player partner = World.getPlayer(plr.getDuoSlayerPartnerUid(), false);
                                plr.setDuoSlayerHost(false);
                                plr.setDuoSlayerPartner(false);
                                plr.setDuoSlayerPartnerUid(-1);
                                plr.setDuoSlayerPartnerName(null);
                                plr.duoSlayerTaskRemaining = 0;
                                plr.duoSlayerTaskName = null;
                                plr.duoSlayerTaskId = -1;
                                partner.setDuoSlayerHost(false);
                                partner.setDuoSlayerPartner(false);
                                partner.setDuoSlayerPartnerUid(-1);
                                partner.setDuoSlayerPartnerName(null);
                                partner.duoSlayerTaskRemaining = 0;
                                partner.duoSlayerTaskName = null;
                                partner.duoSlayerTaskId = -1;
                                plr.sendMessage(Color.DARK_RED, "You have left the duo slayer team.");
                                if (partner.getUserId() > 0) {
                                    partner.sendMessage(Color.DARK_RED, plr.getName() + " has left the duo slayer team.");
                                }
                            }),
                            new Option("Nevermind")
                    )
            );
            return;
        }

        Position currentLocation = player.getPosition();
        if (!currentLocation.inBounds(new Bounds(3107, 3512, 3113, 3517, 0))) {
            player.sendMessage(Color.DARK_RED, "You need to be within the slayer graveyard at home to do this!");
            return;
        }

        //No partner? Time to find a new partner
        if (!player.isDuoSlayerPartner()) {
            player.stringInput("Enter the player's username.", partnerToFind -> {
                Player potentialPartner = World.getPlayer(partnerToFind);
                if (potentialPartner != null) {
                    int potentialPartnerId = potentialPartner.getUserId();
                    potentialPartner = World.getPlayer(potentialPartnerId, true);
                    if (potentialPartner.isOnline()) {
                        if (potentialPartner.isDuoSlayerPartner() && potentialPartner.getDuoSlayerPartnerName().equalsIgnoreCase(player.getName())) {
                            player.sendMessage(Color.DARK_RED, "That player already has a duo slayer partner!");
                        }
                        Position partnersLocation = potentialPartner.getPosition();
                        if (!partnersLocation.inBounds(new Bounds(3107, 3512, 3113, 3517, 0))) {
                            player.sendMessage(Color.DARK_RED, potentialPartner.getName() + " needs to be within the slayer graveyard at home to do this!");
                            return;
                        }
                        potentialPartner.dialogue(
                                new OptionsDialogue(player.getName() + " is requesting you to join them in Duo Slayer.",
                                        new Option("Accept", potential -> {
                                            acceptDuoSlayer(player, potential);
                                        }),
                                        new Option("Decline", potential -> {
                                            player.sendMessage(Color.DARK_RED, potential.getName() + " has declined to be your slayer partner.");
                                        })
                                )
                        );
                    }
                }
            });
        }
    }

    public static void acceptDuoSlayer(Player player, Player partner) {
        player.setDuoSlayerPartner(true);
        partner.setDuoSlayerPartner(true);

        player.setDuoSlayerPartnerName(partner.getName());
        partner.setDuoSlayerPartnerName(player.getName());

        player.setDuoSlayerHost(true);
        partner.setDuoSlayerHost(false);

        player.setDuoSlayerPartnerUid(partner.getUserId());
        partner.setDuoSlayerPartnerUid(player.getUserId());

        player.sendMessage(Color.DARK_GREEN, partner.getName() + " is now your slayer partner.");
        partner.sendMessage(Color.DARK_GREEN, player.getName() + " is now your slayer partner.");
    }

    public static void findPartner(Player player, Player other) {
        if (player.isLocked()) {
            player.sendMessage("Please finish what you are doing first.");
            return;
        }

        //Invalid duo partner? Let's reset the partner
        if (player.isDuoSlayerPartner() && player.getDuoSlayerPartnerName() == null)
            player.setDuoSlayerPartner(false);

        //Valid duo partner & valid duo partner name? No need to do anything.
        if (player.isDuoSlayerPartner() && !player.getDuoSlayerPartnerName().isEmpty() && player.getDuoSlayerPartnerName() != null) {
            player.sendMessage(Color.DARK_RED, "You already have " + player.getDuoSlayerPartnerName() + " as a slayer partner!");
            player.dialogue(
                    new OptionsDialogue(player.getDuoSlayerPartnerName() + " is currently your Duo Slayer partner.",
                            new Option("Leave Duo Slayer", plr -> {
                                Player partner = World.getPlayer(plr.getDuoSlayerPartnerUid(), false);
                                plr.setDuoSlayerHost(false);
                                plr.setDuoSlayerPartner(false);
                                plr.setDuoSlayerPartnerUid(-1);
                                plr.setDuoSlayerPartnerName(null);
                                plr.duoSlayerTaskRemaining = 0;
                                plr.duoSlayerTaskName = null;
                                plr.duoSlayerTaskId = -1;
                                partner.setDuoSlayerHost(false);
                                partner.setDuoSlayerPartner(false);
                                partner.setDuoSlayerPartnerUid(-1);
                                partner.setDuoSlayerPartnerName(null);
                                partner.duoSlayerTaskRemaining = 0;
                                partner.duoSlayerTaskName = null;
                                partner.duoSlayerTaskId = -1;
                            }),
                            new Option("Nevermind")
                    )
            );
            return;
        }

        Position currentLocation = player.getPosition();
        if (!currentLocation.inBounds(new Bounds(3107, 3512, 3113, 3517, 0))) {
            player.sendMessage(Color.DARK_RED, "You need to be within the slayer graveyard at home to do this!");
            return;
        }

        //No partner? Time to find a new partner
        if (!player.isDuoSlayerPartner()) {
            Player potentialPartner = other;
            if (potentialPartner != null) {
                int potentialPartnerId = potentialPartner.getUserId();
                potentialPartner = World.getPlayer(potentialPartnerId, true);
                if (potentialPartner.isOnline()) {
                    if (potentialPartner.isDuoSlayerPartner() && potentialPartner.getDuoSlayerPartnerName().equalsIgnoreCase(player.getName())) {
                        player.sendMessage(Color.DARK_RED, "That player already has a duo slayer partner!");
                    }
                    Position partnersLocation = potentialPartner.getPosition();
                    if (!partnersLocation.inBounds(new Bounds(3107, 3512, 3113, 3517, 0))) {
                        player.sendMessage(Color.DARK_RED, potentialPartner.getName() + " needs to be within the slayer graveyard at home to do this!");
                        return;
                    }
                    potentialPartner.dialogue(
                            new OptionsDialogue(player.getName() + " is requesting you to join them in Duo Slayer.",
                                    new Option("Accept", potential -> {
                                        acceptDuoSlayer(player, potential);
                                    }),
                                    new Option("Decline", potential -> {
                                        player.sendMessage(Color.DARK_RED, potential.getName() + " has declined to be your slayer partner.");
                                    })
                            )
                    );
                }
            }
        }
    }
}

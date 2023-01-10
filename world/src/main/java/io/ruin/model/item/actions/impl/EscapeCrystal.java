package io.ruin.model.item.actions.impl;

import io.ruin.model.activities.raids.tob.party.PartyStatus;
import io.ruin.model.activities.raids.tob.party.TheatreParty;
import io.ruin.model.activities.raids.tob.party.TheatrePartyManager;
import io.ruin.model.item.actions.ItemAction;

public class EscapeCrystal {

    static {
        ItemAction.registerInventory(25961, "activate", (player, item) -> {
            TheatreParty tobparty = TheatrePartyManager.instance().getPartyForPlayer(player.getUserId()).get();
            player.getMovement().startTeleport(e -> {
                if (player.theatreOfBloodStage == 6) {
                    player.sendMessage("There is no need to use this. As you've completed the TOB Run.");
                    return;
                }
                if (player.getUserId() == tobparty.getLeaderId() && tobparty.getUsers().size() > 1) {
                    tobparty.forPlayers(p -> {
                        p.getMovement().teleport(3665, 3219, 0);
                        p.tobDeaths = 0;
                        p.sendMessage("Everyone has died! The raid has ended.");
                        TheatreParty.updatePartyStatus(p, PartyStatus.NO_PARTY);
                        p.theatreOfBloodStage = 0;
                        player.deathEndListener = null;
                        p.theatreroom = "";
                        player.getCombat().setDead(false);
                        player.unlock();
                        if (p.getInventory().contains(25961))
                            p.getInventory().findItem(25961).remove();
                        p.tobreward = false;
                    });
                    TheatrePartyManager.instance().deregister(tobparty);
                } else {
                    player.getMovement().teleport(3665, 3219, 0);
                    TheatreParty.updatePartyStatus(player, PartyStatus.NO_PARTY);
                    player.theatreOfBloodStage = 0;
                    player.theatreroom = "";
                    player.getInventory().findItem(25961).remove();
                    player.tobreward = false;
                    player.getCombat().setDead(false);
                    player.unlock();
                }
                tobparty.leave(player.getUserId(), false);
            });
        });
    }
}
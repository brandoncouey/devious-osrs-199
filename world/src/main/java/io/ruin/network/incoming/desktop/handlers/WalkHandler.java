package io.ruin.network.incoming.desktop.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.World;
import io.ruin.model.activities.legacytournament.TournamentViewingOrb;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.IdHolder;

@IdHolder(ids = {57, 66})
public class WalkHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        if (player.isLocked() || player.isStunned()) {
            /* is our player new to the game? */
            if (player.inTutorial) {
                return;
            }
            /* is our player currently transformed? */ // TODO  Inspect the backlashes but we shouldnt be unmorphing if locked.
            /*if (player.getAppearance().getNpcId() != -1)
                TransformationRing.unmorph(player);
            else
                return;*/
            /*if they're viewing an orb, reset viewing orb */
            if (player.usingTournamentOrb) {
                TournamentViewingOrb.reset(player);
                return;
            }

            // Return always if locked why let them move?
            return;
        }

        if (player.emoteDelay.isDelayed()) {
            player.resetAnimation();
            player.emoteDelay.reset();
        }

        player.resetActions(true, true, true);

        int pressedKeys = in.readUnsignedByteA(); // key 81 pressed ? 2 : key 82 pressed ? 1 : 0;
        int x = in.readLEShort();
        int y = in.readLEShortA();

        if (player.isAdmin() || World.isDev()) {
            NPC npc = player.get("CONTROLLING_NPC");
            if (npc != null) {
                if (pressedKeys == 2) {
                    npc.getMovement().teleport(x, y, player.getHeight());
                } else {
                    npc.getRouteFinder().routeAbsolute(x, y);
                }
                return;
            } else if (pressedKeys == 2) {
                int z = player.getHeight();
                player.getMovement().teleport(x, y, z);
                player.sendFilteredMessage("<col=cc0000>::tele: " + x + "," + y + "," + z);
                return;
            }
        }
        player.getMovement().setCtrlRun(pressedKeys == 1);
        player.getRouteFinder().routeAbsolute(x, y, true);
    }

}
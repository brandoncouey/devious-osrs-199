package io.ruin.model.entity.player;

import io.ruin.model.activities.raids.xeric.party.RecruitingBoard;
import io.ruin.model.inter.staffmoderation.StaffModerationInterface;
import io.ruin.model.map.object.actions.impl.edgeville.Christmas;
import io.ruin.model.map.route.routes.TargetRoute;

import java.util.function.BiConsumer;

public enum PlayerAction {

    //GAMBLE("Gamble", false, (p1, p2) -> {
    //  p1.face(p2);
    //  TargetRoute.set(p1, p2, () -> {
    //      if(p1.getGambleRequestCooldown() > 0) {
    //          p1.sendFilteredMessage("You need to wait " + (p1.getGambleRequestCooldown() * 0.6) + " more seconds to challenge again.");
    //          return;
    //      }
    //      p1.setGambleRequestCooldown(50);
    //      p1.faceNone(true);
    //     GambleManager.proposeGambling(p1, p2);
    //  });
    //  }),

    ATTACK("Attack", true, (p1, p2) -> {
        p1.face(p2);
        p1.getCombat().setTarget(p2);
    }),
    FOLLOW("Follow", false, (p1, p2) -> {
        p1.face(p2);
        p1.getMovement().following = p2;
    }),
    MODERATE("Moderate", false, StaffModerationInterface::openStaffModeration),
    TRADE("Trade with", false, (p1, p2) -> {
        p1.face(p2);
        TargetRoute.set(p1, p2, () -> {
            p1.getTrade().request(p2);
            p1.faceNone(true);
        });
    }),
    CHALLENGE("Challenge", false, (p1, p2) -> {
        p1.face(p2);
        TargetRoute.set(p1, p2, () -> {
            p1.getDuel().request(p2);
            p1.faceNone(true);
        });
    }),
    PELT("Pelt", true, (p1, p2) -> {
        p1.face(p2);
        Christmas.throwSnow(p1, p2);
    }),
    FIGHT("Fight", true, (p1, p2) -> {
        p1.face(p2);
        p1.getCombat().setTarget(p2);
    }),
    GIM_Invite("Invite-GIM", false, (p1, p2) -> {
        p1.face(p2);
        TargetRoute.set(p1, p2, () -> {
            p1.getGroupIronman().invite(p2);
        });
        p1.faceNone(true);
    }),
    INVITE("Invite", false, (p1, p2) -> {
        p1.face(p2);
        TargetRoute.set(p1, p2, () -> {
            RecruitingBoard.invite(p1, p2);
            p1.faceNone(true);
        });
    });

    public final String name;

    public final boolean top;

    public final BiConsumer<Player, Player> consumer;

    PlayerAction(String name, boolean top, BiConsumer<Player, Player> consumer) {
        this.name = name;
        this.top = top;
        this.consumer = consumer;
    }

}
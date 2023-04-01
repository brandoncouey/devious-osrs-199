package io.ruin.model.activities.edgeville;


import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;


public class DeviousObjects {

    public static int GILDED_ADVENTURE_LOG = 31922;

    public static final int WELL = 14750;//50007

    static {

        ObjectAction.register(31922, 1, (player, object) -> {
            player.getCollectionLog().openCollectionLog(player);
        });
        //Moslee harmless dungeon entrance
        ObjectAction.register(3650, 1, (player, object) -> {
            player.lock(LockType.FULL_NULLIFY_DAMAGE); //keep lock outside of event!
            player.startEvent(e -> {
                player.getPacketSender().fadeOut();
                e.delay(1);
                player.getMovement().teleport(new Position(3748, 9374, 0));
                player.getPacketSender().clearFade();
                player.getPacketSender().fadeIn();
                player.unlock();
            });
        });
        //Inside Mosle harmless dungeon exit
        ObjectAction.register(6702, 1, (player, object) -> {
            player.lock(LockType.FULL_NULLIFY_DAMAGE); //keep lock outside of event!
            player.startEvent(e -> {
                player.getPacketSender().fadeOut();
                e.delay(1);
                player.getMovement().teleport(new Position(3749, 2973, 0));
                player.getPacketSender().clearFade();
                player.getPacketSender().fadeIn();
                player.unlock();
            });
        });
        //Fremmy Slayer Dungeon Entrance
        ObjectAction.register(2123, 1, (player, object) -> {
            player.lock(LockType.FULL_NULLIFY_DAMAGE); //keep lock outside of event!
            player.startEvent(e -> {
                player.getPacketSender().fadeOut();
                e.delay(1);
                player.getMovement().teleport(new Position(2808, 10002, 0));
                player.getPacketSender().clearFade();
                player.getPacketSender().fadeIn();
                player.unlock();
            });
        });
        //Fremmy Slayer Dungeon Exit
        ObjectAction.register(2141, 1, (player, object) -> {
            player.lock(LockType.FULL_NULLIFY_DAMAGE); //keep lock outside of event!
            player.startEvent(e -> {
                player.getPacketSender().fadeOut();
                e.delay(1);
                player.getMovement().teleport(new Position(2796, 3615, 0));
                player.getPacketSender().clearFade();
                player.getPacketSender().fadeIn();
                player.unlock();
            });
        });


    }
}


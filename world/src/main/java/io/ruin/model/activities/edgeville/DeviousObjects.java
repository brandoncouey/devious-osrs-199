package io.ruin.model.activities.edgeville;


import io.ruin.model.map.object.actions.ObjectAction;


public class DeviousObjects {

    public static int GILDED_ADVENTURE_LOG = 31922;

    public static final int WELL = 14750;//50007

    static {

        ObjectAction.register(31922, 1, (player, object) -> {
            player.getCollectionLog().openCollectionLog(player);
            return;
        });
        // ObjectAction.register(GILDED_ADVENTURE_LOG, 1, (player, object) -> {
        //.getCollectionLog().openCollectionLog(player);
        //       //return;
        //  });

    }
}


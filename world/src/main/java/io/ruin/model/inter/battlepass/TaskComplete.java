package io.ruin.model.inter.battlepass;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.SeasonPass.Achievements;
import io.ruin.model.inter.InterfaceType;

public class TaskComplete {

    /**
     * This is just an example! We can easily ad to this for the drop down interface for more completed tasks
     */

    public static void testComplete(Player player){
        player.startEvent(event -> {
            player.openInterface(InterfaceType.MAIN, 660);
            player.getPacketSender().sendString(660, 6, "Task completed");
            player.getPacketSender().sendString(660, 8, "Cut 10 Magic Logs!");
            player.sendMessage("You have completed the task" + "<col=FF0000> Cut 10 Magic Logs!");
            event.delay(2); // Can definitely be done way better lmao
            player.closeInterface(InterfaceType.MAIN);
        });
    }

    public static void showCompletion(Player player) {
        player.openInterface(InterfaceType.MAIN, 660);
        if (Achievements.Cut10MagicLogs.isFinished(player)) {
            player.getPacketSender().sendString(660, 8, "<col=0fd60f>Task completed" + "Cut 10 Magic Logs!");
            player.sendMessage("You have completed the task" + "<col=FF0000> Cut 10 Magic Logs!");
        } else {
            player.getPacketSender().sendString(660, 8, "<col=FF0000>Task completed");
        }
        if (Achievements.Cut10MapleLogs.isFinished(player)) {
            player.getPacketSender().sendString(660, 8, "<col=0fd60f>Task completed" + "Cut 10 Maple Logs!");
            player.sendMessage("You have completed the task" + "<col=FF0000> Cut 10 Maple Logs!");
        } else {
            player.getPacketSender().sendString(660, 8, "<col=FF0000>Task completed");
        }
    }
}

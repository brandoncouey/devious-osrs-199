package io.ruin.model.content;

import io.ruin.model.entity.player.Player;
import lombok.Data;

@Data
public class IronmanGroupRequest {

    private final Player sender;
    private final Player reciever;

    public void accept() {
        reciever.getGroupIronman().setLeader(sender.getName());
        sender.getGroupIronman().getTeamates().add(reciever.getName());
        reciever.getGroupIronman().setTeamates(sender.getGroupIronman().getTeamates());
        reciever.sendMessage("<col=107900>You've accepted " + sender.getName() + "'s invite!");
        sender.sendMessage("<col=107900>" + reciever.getName() + " has accepted your group invite!");
        reciever.getGroupIronman().setRequest(null);
        GIMRepository.update(sender);
        reciever.getGroupIronman().saveData.get().players.add(sender);
        sender.getGroupIronman().saveData.get().players.add(reciever);
    }

}

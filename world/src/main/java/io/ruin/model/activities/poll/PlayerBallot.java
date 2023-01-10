package io.ruin.model.activities.poll;

import io.ruin.model.entity.player.Player;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class PlayerBallot {

    public final Player player;
    public Map<Integer, Integer> answers;
    public Map<Integer, String> chosenAnswers;

    public PlayerBallot(Player player) {
        this.player = player;
        this.answers = new HashMap<>();
        this.chosenAnswers = new HashMap<>();
    }
}

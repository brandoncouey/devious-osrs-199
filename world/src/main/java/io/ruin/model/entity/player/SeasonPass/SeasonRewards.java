package io.ruin.model.entity.player.SeasonPass;

public class SeasonRewards {
    public int level;
    boolean isItem;
    public int id;
    public int amount;
    public String Effect;

    SeasonRewards(int level, boolean isItem, int id, int amount, String Effect){
        this.level = level;
        this.isItem = isItem;
        this.id = id;
        this.amount = amount;
        this.Effect = Effect;
    }

    public static final SeasonRewards[] rewards = new SeasonRewards[]{
            new SeasonRewards(1,true, 994, 1, "None"),
            new SeasonRewards(2,true, 994, 1, "None"),
            new SeasonRewards(3,true, 994, 1, "None"),
            new SeasonRewards(4,true, 994, 1, "None"),
            new SeasonRewards(5,true, 994, 1, "None"),
            new SeasonRewards(6,true, 994, 1, "None"),
            new SeasonRewards(7,true, 994, 1, "None"),
            new SeasonRewards(8,true, 994, 1, "None"),
            new SeasonRewards(9,true, 994, 1, "None")
    };

        /*Ideas for rewards
        Whip
        Exp lamps
        Clue caskets
        Mystery boxes
            Create boss mystery boxes so you eg can get raid mystery box

        In my opinion, make first battlepass pretty grindy and give raids 2 rewards at the end, eg everyone who completes the full battlepass gets a
            Scythe or even just the rapier,
            and we make it pretty hard to achive but still possible and people will always hype battlepasses for being great!

        Fashion scape!
        3rd age mboxes


     */
}

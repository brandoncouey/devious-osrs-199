package io.ruin.model.clan;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;


@Data
@RequiredArgsConstructor
public class ClanMember implements Serializable {


    private static final long serialVersionUID = -1203833687881050886L;

    @Getter
    @RequiredArgsConstructor
    public enum Ranks {
        RECRUIT(2583),
        CORPORAL(2584),
        SERGEANT(2585),
        LIEUTENANT(2586),
        CAPTAIN(2587),
        GENERAL(2588),
        LEADER(2589)

        ;

        private final int spriteId;

        public int getSpriteGroupId() {
            return 33 + this.ordinal();
        }
    }

    private final String username;
    private final Ranks rank;

}

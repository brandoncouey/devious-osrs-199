package io.ruin.model.item.actions.impl.pets;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetData {

    public static final int[] LEVELS_FOR_XP = new int[] { 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000, 11000, 12000, 13000, 14000, 15000 };

    @Expose
    private int experience;

    public int getLevel() {
        for (int i = LEVELS_FOR_XP.length - 1; i >= 0; i--) {
            if (experience >= LEVELS_FOR_XP[i]) {
                return i + 2;
            }
        }
        return 1;
    }

}

package io.ruin.data.impl.items;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroundItemSpawn {

    @Expose
    public int id;
    @Expose
    public int amount = 1;
    @Expose
    public int x, y, z;
    @Expose
    public String world;
    @Expose
    public String itemName;
    @Expose
    public int respawnSeconds = 60;
}

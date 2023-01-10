package io.ruin.model.inter.presets;

import com.google.gson.annotations.Expose;
import io.ruin.model.skills.magic.SpellBook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Preset {
    @Expose
    private final int presetId;
    @Expose
    private PresetItem[] equipment;
    @Expose
    private PresetItem[] inventory;
    @Expose
    private String name;
    @Expose
    private SpellBook spellBook;
    @Expose
    private boolean locked;
    @Expose
    private int hitpointsLevel = 1;
    @Expose
    private int attackLevel = 1;
    @Expose
    private int defenceLevel = 1;
    @Expose
    private int strengthLevel = 1;
    @Expose
    private int rangedLevel = 1;
    @Expose
    private int magicLevel = 1;
    @Expose
    private int prayerLevel = 1;

    public Preset(int presetId, PresetItem[] equipment, PresetItem[] inventory, String name, SpellBook spellBook, boolean locked) {
        this.presetId = presetId;
        this.equipment = equipment;
        this.inventory = inventory;
        this.name = name;
        this.spellBook = spellBook;
        this.locked = locked;
    }
}

package io.github.thatpreston.mermod.item.modifier;

import net.minecraft.nbt.CompoundTag;

public class NecklaceModifier {
    private final String id;
    private final String type;
    private final int tooltipColor;
    private final boolean foil;
    public NecklaceModifier(String id, String type, int tooltipColor, boolean foil) {
        this.id = id;
        this.type = type;
        this.tooltipColor = tooltipColor;
        this.foil = foil;
    }
    public void add(CompoundTag tag) {
        tag.putString(type, id);
    }
    public boolean remove(CompoundTag tag) {
        if(isAdded(tag)) {
            tag.remove(type);
            return true;
        }
        return false;
    }
    public boolean canAdd(CompoundTag tag) {
        return !tag.contains(type);
    }
    public boolean isAdded(CompoundTag tag) {
        return tag.getString(type).equals(id);
    }
    public String getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public int getTooltipColor() {
        return tooltipColor;
    }
    public boolean isFoil() {
        return foil;
    }
}
package io.github.thatpreston.mermod.item.modifier;

import net.minecraft.nbt.CompoundTag;

public class TailModifier extends NecklaceModifier {
    private final String texture;
    private final int model;
    public TailModifier(String id, int tooltipColor, String texture, int model) {
        super(id, "tail", tooltipColor, false);
        this.texture = texture;
        this.model = model;
    }
    @Override
    public void add(CompoundTag tag) {
        super.add(tag);
        tag.putString("texture", texture);
        tag.putInt("model", model);
    }
    @Override
    public boolean remove(CompoundTag tag) {
        boolean removed = super.remove(tag);
        if(removed) {
            tag.remove("texture");
            tag.remove("model");
        }
        return removed;
    }
    public String getTexture() {
        return texture;
    }
    public int getModel() {
        return model;
    }
}
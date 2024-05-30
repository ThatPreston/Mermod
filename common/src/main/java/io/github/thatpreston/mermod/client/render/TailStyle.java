package io.github.thatpreston.mermod.client.render;

import io.github.thatpreston.mermod.Mermod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public record TailStyle(ResourceLocation texture, int model, int tailColor, boolean hasBra, int braColor, boolean hasGradient, int gradientColor, boolean hasGlint, boolean permanent) {
    public static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(Mermod.MOD_ID, "textures/tail/tail.png");
    private static final HashMap<String, ResourceLocation> TAIL_LOCATION_CACHE = new HashMap<>();
    public static TailStyle fromNecklace(ItemStack necklace) {
        CompoundTag tag = necklace.getOrCreateTagElement("necklace_modifiers");
        int model = tag.getInt("model");
        int tailColor = Mermod.getItemColor(necklace);
        boolean hasBra = tag.contains("bra");
        int braColor = hasBra ? tag.getInt("bra_color") : 16777215;
        boolean hasGradient = tag.contains("gradient");
        int gradientColor = tag.getInt("gradient_color");
        String textureName = tag.getString("texture");
        ResourceLocation texture = textureName.isEmpty() ? DEFAULT_TEXTURE : getTailLocation(textureName, tailColor != 16777215 || braColor != 16777215 || hasGradient);
        return new TailStyle(texture, model, tailColor, hasBra, braColor, hasGradient, gradientColor, tag.contains("glint"), tag.contains("permanent"));
    }
    private static ResourceLocation getTailLocation(String name, boolean colorable) {
        String string = "textures/tail/" + name + (colorable ? "_colorable" : "") + ".png";
        return TAIL_LOCATION_CACHE.computeIfAbsent(string, path -> new ResourceLocation(Mermod.MOD_ID, path));
    }
}
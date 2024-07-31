package io.github.thatpreston.mermod.forge.compat.origins;

import io.github.edwinmindcraft.apoli.api.IDynamicFeatureConfiguration;
import io.github.thatpreston.mermod.client.render.TailStyle;
import net.minecraft.resources.ResourceLocation;

public class TailPower implements IDynamicFeatureConfiguration {
    private final TailStyle tailStyle;
    public TailPower(ResourceLocation texture, int model, int tailColor, boolean hasBra, int braColor, boolean hasGradient, int gradientColor, boolean hasGlint, boolean permanent) {
        this.tailStyle = new TailStyle(texture, model, tailColor, hasBra, braColor, hasGradient, gradientColor, hasGlint, permanent);
    }
    public TailStyle getTailStyle() {
        return tailStyle;
    }
}
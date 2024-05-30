package io.github.thatpreston.mermod.fabric.compat.origins;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.thatpreston.mermod.client.render.TailStyle;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class TailPower extends Power {
    private final TailStyle tailStyle;
    public TailPower(PowerType<?> type, LivingEntity entity, ResourceLocation texture, int model, int tailColor, boolean hasBra, int braColor, boolean hasGradient, int gradientColor, boolean hasGlint, boolean permanent) {
        super(type, entity);
        this.tailStyle = new TailStyle(texture, model, tailColor, hasBra, braColor, hasGradient, gradientColor, hasGlint, permanent);
    }
    public TailStyle getTailStyle() {
        return tailStyle;
    }
}
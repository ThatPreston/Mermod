package io.github.thatpreston.mermod.forge.compat.origins;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import io.github.thatpreston.mermod.client.render.TailStyle;
import net.minecraft.resources.ResourceLocation;

public class TailPowerFactory extends PowerFactory<TailPower> {
    public static final Codec<TailPower> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.optionalFieldOf("texture", TailStyle.DEFAULT_TEXTURE).forGetter(power -> power.getTailStyle().texture()),
            Codec.INT.optionalFieldOf("model", 0).forGetter(power -> power.getTailStyle().model()),
            Codec.INT.optionalFieldOf("tailColor", 16777215).forGetter(power -> power.getTailStyle().tailColor()),
            Codec.BOOL.optionalFieldOf("hasBra", false).forGetter(power -> power.getTailStyle().hasBra()),
            Codec.INT.optionalFieldOf("braColor", 16777215).forGetter(power -> power.getTailStyle().braColor()),
            Codec.BOOL.optionalFieldOf("hasGradient", false).forGetter(power -> power.getTailStyle().hasGradient()),
            Codec.INT.optionalFieldOf("gradientColor", 16777215).forGetter(power -> power.getTailStyle().gradientColor()),
            Codec.BOOL.optionalFieldOf("hasGlint", false).forGetter(power -> power.getTailStyle().hasGlint()),
            Codec.BOOL.optionalFieldOf("permanent", false).forGetter(power -> power.getTailStyle().permanent())
    ).apply(instance, TailPower::new));
    public TailPowerFactory() {
        super(CODEC);
    }
}
package io.github.thatpreston.mermod.fabric.compat.origins;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.thatpreston.mermod.client.render.TailStyle;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class OriginsCompat {
    private static final ResourceLocation TAIL_STYLE = new ResourceLocation("mermod", "tail_style");
    public static TailStyle getTailStyle(Player player) {
        List<TailPower> powers = PowerHolderComponent.getPowers(player, TailPower.class);
        if(powers != null && powers.size() > 0) {
            return powers.get(0).getTailStyle();
        }
        return null;
    }
    public static boolean hasTailStyle(Player player) {
        return PowerHolderComponent.hasPower(player, TailPower.class);
    }
    public static void registerPowerFactory() {
        SerializableData serializableData = new SerializableData();
        serializableData.add("texture", SerializableDataTypes.IDENTIFIER, TailStyle.DEFAULT_TEXTURE);
        serializableData.add("model", SerializableDataTypes.INT, 0);
        serializableData.add("tailColor", SerializableDataTypes.INT, 16777215);
        serializableData.add("hasBra", SerializableDataTypes.BOOLEAN, false);
        serializableData.add("braColor", SerializableDataTypes.INT, 16777215);
        serializableData.add("hasGradient", SerializableDataTypes.BOOLEAN, false);
        serializableData.add("gradientColor", SerializableDataTypes.INT, 16777215);
        serializableData.add("hasGlint", SerializableDataTypes.BOOLEAN, false);
        serializableData.add("permanent", SerializableDataTypes.BOOLEAN, false);
        PowerFactory<TailPower> serializer = new PowerFactory<TailPower>(TAIL_STYLE, serializableData, data -> (type, entity) -> new TailPower(type, entity, data.getId("texture"), data.getInt("model"), data.getInt("tailColor"), data.getBoolean("hasBra"), data.getInt("braColor"), data.getBoolean("hasGradient"), data.getInt("gradientColor"), data.getBoolean("hasGlint"), data.getBoolean("permanent"))).allowCondition();
        Registry.register(ApoliRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
    }
}
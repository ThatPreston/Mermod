package io.github.thatpreston.mermod.forge.compat.origins;

import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import io.github.edwinmindcraft.apoli.api.registry.ApoliRegistries;
import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.client.render.TailStyle;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class OriginsCompat {
    public static final DeferredRegister<PowerFactory<?>> POWER_FACTORIES = DeferredRegister.create(ApoliRegistries.POWER_FACTORY_KEY, Mermod.MOD_ID);
    public static final RegistryObject<TailPowerFactory> TAIL_STYLE_FACTORY = POWER_FACTORIES.register("tail_style", TailPowerFactory::new);
    public static TailStyle getTailStyle(Player player) {
        List<Holder<ConfiguredPower<TailPower, TailPowerFactory>>> powers = IPowerContainer.getPowers(player, TAIL_STYLE_FACTORY.get());
        if(powers != null && !powers.isEmpty()) {
            TailPower power = powers.get(0).get().getConfiguration();
            return power.getTailStyle();
        }
        return null;
    }
    public static boolean hasTailPower(Player player) {
        return IPowerContainer.hasPower(player, TAIL_STYLE_FACTORY.get());
    }
    public static void registerPowerFactory(IEventBus eventBus) {
        POWER_FACTORIES.register(eventBus);
    }
}
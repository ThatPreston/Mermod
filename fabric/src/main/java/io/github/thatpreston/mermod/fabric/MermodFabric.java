package io.github.thatpreston.mermod.fabric;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.config.MermodConfig;
import net.fabricmc.api.ModInitializer;
import net.neoforged.fml.config.ModConfig;

public class MermodFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Mermod.init();
        Mermod.commonSetup();
        Mermod.registerCauldronInteractions();
        NeoForgeConfigRegistry.INSTANCE.register(Mermod.MOD_ID, ModConfig.Type.SERVER, MermodConfig.SERVER_SPEC);
        NeoForgeConfigRegistry.INSTANCE.register(Mermod.MOD_ID, ModConfig.Type.CLIENT, MermodConfig.CLIENT_SPEC);
    }
}
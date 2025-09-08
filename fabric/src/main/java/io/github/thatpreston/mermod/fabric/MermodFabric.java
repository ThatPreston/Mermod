package io.github.thatpreston.mermod.fabric;

import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
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
        ConfigRegistry.INSTANCE.register(Mermod.MOD_ID, ModConfig.Type.SERVER, MermodConfig.SERVER_SPEC);
        ConfigRegistry.INSTANCE.register(Mermod.MOD_ID, ModConfig.Type.CLIENT, MermodConfig.CLIENT_SPEC);
    }
}
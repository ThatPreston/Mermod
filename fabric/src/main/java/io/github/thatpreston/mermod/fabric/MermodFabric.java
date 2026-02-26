package io.github.thatpreston.mermod.fabric;

import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.config.MermodConfig;
import io.github.thatpreston.mermod.fabric.compat.TrinketsCompat;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.neoforged.fml.config.ModConfig;

public class MermodFabric implements ModInitializer {
    public static boolean trinketsLoaded;
    @Override
    public void onInitialize() {
        FabricLoader loader = FabricLoader.getInstance();
        trinketsLoaded = loader.isModLoaded("trinkets");
        Mermod.init();
        Mermod.commonSetup();
        Mermod.registerCauldronInteractions();
        if(trinketsLoaded) {
            TrinketsCompat.registerTrinket();
        }
        ConfigRegistry.INSTANCE.register(Mermod.MOD_ID, ModConfig.Type.SERVER, MermodConfig.SERVER_SPEC);
        ConfigRegistry.INSTANCE.register(Mermod.MOD_ID, ModConfig.Type.CLIENT, MermodConfig.CLIENT_SPEC);
    }
}
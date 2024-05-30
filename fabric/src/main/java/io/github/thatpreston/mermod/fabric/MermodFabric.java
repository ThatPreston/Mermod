package io.github.thatpreston.mermod.fabric;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.config.MermodConfig;
import io.github.thatpreston.mermod.fabric.compat.TrinketsCompat;
import io.github.thatpreston.mermod.fabric.compat.origins.OriginsCompat;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.neoforged.fml.config.ModConfig;

public class MermodFabric implements ModInitializer {
    public static boolean trinketsLoaded;
    public static boolean originsLoaded;
    @Override
    public void onInitialize() {
        Mermod.init();
        FabricLoader loader = FabricLoader.getInstance();
        trinketsLoaded = loader.isModLoaded("trinkets");
        originsLoaded = loader.isModLoaded("origins");
        if(trinketsLoaded) {
            TrinketsCompat.init();
        }
        if(originsLoaded) {
            OriginsCompat.registerPowerFactory();
        }
        NeoForgeConfigRegistry.INSTANCE.register(Mermod.MOD_ID, ModConfig.Type.SERVER, MermodConfig.SERVER_SPEC);
        NeoForgeConfigRegistry.INSTANCE.register(Mermod.MOD_ID, ModConfig.Type.CLIENT, MermodConfig.CLIENT_SPEC);
        Mermod.registerCauldronInteractions();
    }
}
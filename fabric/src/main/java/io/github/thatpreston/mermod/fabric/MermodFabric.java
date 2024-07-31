package io.github.thatpreston.mermod.fabric;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.config.MermodConfig;
import io.github.thatpreston.mermod.fabric.compat.TrinketsCompat;
import io.github.thatpreston.mermod.fabric.compat.origins.OriginsCompat;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraftforge.fml.config.ModConfig;

public class MermodFabric implements ModInitializer {
    public static boolean trinketsLoaded;
    @Override
    public void onInitialize() {
        Mermod.init();
        FabricLoader loader = FabricLoader.getInstance();
        trinketsLoaded = loader.isModLoaded("trinkets");
        if(trinketsLoaded) {
            TrinketsCompat.init();
        }
        if(Mermod.originsLoaded) {
            OriginsCompat.registerPowerFactory();
        }
        ForgeConfigRegistry.INSTANCE.register(Mermod.MOD_ID, ModConfig.Type.SERVER, MermodConfig.SERVER_SPEC);
        ForgeConfigRegistry.INSTANCE.register(Mermod.MOD_ID, ModConfig.Type.CLIENT, MermodConfig.CLIENT_SPEC);
        Mermod.registerCauldronInteractions();
    }
}
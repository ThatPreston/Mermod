package io.github.thatpreston.mermod.forge;

import dev.architectury.platform.forge.EventBuses;
import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.config.MermodConfig;
import io.github.thatpreston.mermod.forge.compat.CuriosCompat;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Mermod.MOD_ID)
public class MermodForge {
    public static boolean curiosLoaded;
    public MermodForge() {
        curiosLoaded = ModList.get().isLoaded("curios");
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Mermod.MOD_ID, eventBus);
        eventBus.addListener(this::commonSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, MermodConfig.SERVER_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, MermodConfig.CLIENT_SPEC);
        Mermod.init();
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(Mermod::registerCauldronInteractions);
        if(curiosLoaded) {
            CuriosCompat.registerCurio();
        }
    }
}
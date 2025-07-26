package io.github.thatpreston.mermod.neoforge;

import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.config.MermodConfig;
import io.github.thatpreston.mermod.neoforge.compat.CuriosCompat;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Mermod.MOD_ID)
public class MermodNeoForge {
    public static boolean curiosLoaded;
    public MermodNeoForge(IEventBus eventBus, ModContainer container) {
        curiosLoaded = ModList.get().isLoaded("curios");
        Mermod.init();
        eventBus.addListener(this::commonSetup);
        container.registerConfig(ModConfig.Type.SERVER, MermodConfig.SERVER_SPEC);
        container.registerConfig(ModConfig.Type.CLIENT, MermodConfig.CLIENT_SPEC);
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
        Mermod.commonSetup();
        if(curiosLoaded) {
            CuriosCompat.register();
        }
        event.enqueueWork(Mermod::registerCauldronInteractions);
    }
}
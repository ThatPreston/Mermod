package io.github.thatpreston.mermod.neoforge;

import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.client.render.TailRenderLayer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod.EventBusSubscriber(modid = Mermod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MermodNeoForgeClient {
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {}
    @SubscribeEvent
    public static void addLayer(final EntityRenderersEvent.AddLayers event) {
        for(PlayerSkin.Model skin : event.getSkins()) {
            LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer = event.getSkin(skin);
            if(renderer != null) {
                renderer.addLayer(new TailRenderLayer<>(renderer, event.getEntityModels()));
            }
        }
    }
}
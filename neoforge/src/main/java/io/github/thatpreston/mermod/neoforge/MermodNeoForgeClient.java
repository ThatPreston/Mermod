package io.github.thatpreston.mermod.neoforge;

import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.MermodClient;
import io.github.thatpreston.mermod.client.render.MermodRenderTypes;
import io.github.thatpreston.mermod.client.render.TailRenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;

@EventBusSubscriber(modid = Mermod.MOD_ID, value = Dist.CLIENT)
public class MermodNeoForgeClient {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        MermodClient.clientSetup();
    }
    @SubscribeEvent
    public static void addLayer(EntityRenderersEvent.AddLayers event) {
        for(PlayerSkin.Model skin : event.getSkins()) {
            if(event.getSkin(skin) instanceof PlayerRenderer renderer) {
                renderer.addLayer(new TailRenderLayer(renderer, event.getEntityModels()));
            }
        }
    }
    @SubscribeEvent
    public static void registerRenderPipelines(RegisterRenderPipelinesEvent event) {
        event.registerPipeline(MermodRenderTypes.ARMOR_TRANSLUCENT_CULL_PIPELINE);
        event.registerPipeline(MermodRenderTypes.GLINT_CULL_PIPELINE);
    }
}
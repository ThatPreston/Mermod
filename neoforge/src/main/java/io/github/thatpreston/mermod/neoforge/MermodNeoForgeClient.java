package io.github.thatpreston.mermod.neoforge;

import dev.architectury.platform.Platform;
import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.MermodClient;
import io.github.thatpreston.mermod.client.render.MermodRenderTypes;
import io.github.thatpreston.mermod.client.render.TailRenderLayer;
import net.irisshaders.iris.pipeline.IrisPipelines;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.world.entity.player.PlayerModelType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;

@Mod(value = Mermod.MOD_ID, dist = Dist.CLIENT)
public class MermodNeoForgeClient {
    public MermodNeoForgeClient(IEventBus eventBus) {
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::addLayers);
        eventBus.addListener(this::registerRenderPipelines);
    }
    public void clientSetup(FMLClientSetupEvent event) {
        MermodClient.clientSetup();
        if(Platform.isModLoaded("iris")) {
            IrisPipelines.copyPipeline(RenderPipelines.ARMOR_TRANSLUCENT, MermodRenderTypes.ARMOR_TRANSLUCENT_CULL_PIPELINE);
            IrisPipelines.copyPipeline(RenderPipelines.GLINT, MermodRenderTypes.GLINT_CULL_PIPELINE);
        }
    }
    public void addLayers(EntityRenderersEvent.AddLayers event) {
        for(PlayerModelType playerModelType : event.getSkins()) {
            if(event.getPlayerRenderer(playerModelType) instanceof AvatarRenderer<?> renderer) {
                renderer.addLayer(new TailRenderLayer(renderer, event.getEntityModels()));
            }
        }
    }
    public void registerRenderPipelines(RegisterRenderPipelinesEvent event) {
        event.registerPipeline(MermodRenderTypes.ARMOR_TRANSLUCENT_CULL_PIPELINE);
        event.registerPipeline(MermodRenderTypes.GLINT_CULL_PIPELINE);
    }
}
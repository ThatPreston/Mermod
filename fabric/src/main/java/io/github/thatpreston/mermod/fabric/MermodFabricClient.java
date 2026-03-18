package io.github.thatpreston.mermod.fabric;

import io.github.thatpreston.mermod.MermodClient;
import io.github.thatpreston.mermod.client.render.MermodRenderTypes;
import io.github.thatpreston.mermod.client.render.TailRenderLayer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.irisshaders.iris.pipeline.IrisPipelines;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;

public class MermodFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MermodClient.init();
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((type, renderer, registrationHelper, context) -> {
            if(renderer instanceof AvatarRenderer<?> avatarRenderer) {
                registrationHelper.register(new TailRenderLayer(avatarRenderer, context.getModelSet()));
            }
        });
        RenderPipelines.register(MermodRenderTypes.ARMOR_TRANSLUCENT_CULL_PIPELINE);
        RenderPipelines.register(MermodRenderTypes.GLINT_CULL_PIPELINE);
        if(MermodClient.irisLoaded) {
            IrisPipelines.copyPipeline(RenderPipelines.ARMOR_TRANSLUCENT, MermodRenderTypes.ARMOR_TRANSLUCENT_CULL_PIPELINE);
            IrisPipelines.copyPipeline(RenderPipelines.GLINT, MermodRenderTypes.GLINT_CULL_PIPELINE);
        }
    }
}
package io.github.thatpreston.mermod.fabric;

import io.github.thatpreston.mermod.MermodClient;
import io.github.thatpreston.mermod.client.render.TailRenderLayer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;

public class MermodFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MermodClient.init();
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((type, renderer, registrationHelper, context) -> {
            if(renderer instanceof PlayerRenderer) {
                registrationHelper.register(new TailRenderLayer(renderer, context.getModelSet()));
            }
        });
    }
}
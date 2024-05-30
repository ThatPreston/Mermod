package io.github.thatpreston.mermod.forge;

import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.client.render.TailRenderLayer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Mermod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MermodForgeClient {
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {}
    @SubscribeEvent
    public static void addLayer(final EntityRenderersEvent.AddLayers event) {
        for(PlayerSkin.Model skin : event.getSkins()) {
            LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer = event.getPlayerSkin(skin);
            if(renderer != null) {
                renderer.addLayer(new TailRenderLayer<>(renderer, event.getEntityModels()));
            }
        }
    }
}
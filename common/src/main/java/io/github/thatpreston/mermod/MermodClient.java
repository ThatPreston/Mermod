package io.github.thatpreston.mermod;

import dev.architectury.platform.Platform;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import io.github.thatpreston.mermod.client.render.TailStyle;
import io.github.thatpreston.mermod.client.render.model.TailLayerDefinitions;
import io.github.thatpreston.mermod.compat.MermodClothConfigScreen;
import io.github.thatpreston.mermod.compat.MermodFiguraAPI;
import io.github.thatpreston.mermod.registry.RegistryHandler;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class MermodClient {
    public static final List<ModelLayerLocation> TAIL_MODEL_LAYERS = new ArrayList<>();
    public static final ModelLayerLocation DEFAULT_TAIL_LAYER = createTailModelLayer("default_tail");
    public static final ModelLayerLocation H2O_TAIL_LAYER = createTailModelLayer("h2o_tail");
    public static final ModelLayerLocation SIREN_TAIL_LAYER = createTailModelLayer("siren_tail");
    public static void init() {
        registerConfigScreen();
        ColorHandlerRegistry.registerItemColors((stack, index) -> index > 0 ? Mermod.getItemColor(stack) : -1, RegistryHandler.SEA_NECKLACE::get);
        ColorHandlerRegistry.registerItemColors((stack, index) -> Mermod.getItemColor(stack), RegistryHandler.MERMAID_BRA_MODIFIER::get, RegistryHandler.TAIL_GRADIENT_MODIFIER::get);
        EntityModelLayerRegistry.register(DEFAULT_TAIL_LAYER, () -> TailLayerDefinitions.getDefault(false));
        EntityModelLayerRegistry.register(H2O_TAIL_LAYER, () -> TailLayerDefinitions.getDefault(true));
        EntityModelLayerRegistry.register(SIREN_TAIL_LAYER, TailLayerDefinitions::getSiren);
    }
    private static ModelLayerLocation createTailModelLayer(String name) {
        ModelLayerLocation location = new ModelLayerLocation(new ResourceLocation(Mermod.MOD_ID, name), "main");
        TAIL_MODEL_LAYERS.add(location);
        return location;
    }
    private static void registerConfigScreen() {
        if(!Platform.isFabric() && Platform.isModLoaded("cloth_config")) {
            Platform.getMod(Mermod.MOD_ID).registerConfigurationScreen(MermodClothConfigScreen::build);
        }
    }
    public static boolean shouldTryRenderingTail(Player player) {
        if(!player.isInvisible()) {
            return !Mermod.figuraLoaded || MermodFiguraAPI.isTailVisible(player.getUUID());
        }
        return false;
    }
    public static TailStyle getRenderedTailStyle(Player player) {
        if(shouldTryRenderingTail(player)) {
            TailStyle style = Mermod.getTailStyle(player);
            if(style != null && (player.isInWater() || style.permanent())) {
                return style;
            }
        }
        return null;
    }
    public static boolean shouldRenderTail(Player player) {
        if(player.isInWater()) {
            return shouldTryRenderingTail(player) && Mermod.hasTailStyle(player);
        }
        return getRenderedTailStyle(player) != null;
    }
}
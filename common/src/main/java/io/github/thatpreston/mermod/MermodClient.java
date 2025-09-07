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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

import java.util.ArrayList;
import java.util.List;

public class MermodClient {
    public static final List<ModelLayerLocation> TAIL_MODEL_LAYERS = new ArrayList<>();
    public static final ModelLayerLocation DEFAULT_TAIL_LAYER = createTailModelLayer("default_tail");
    public static final ModelLayerLocation H2O_TAIL_LAYER = createTailModelLayer("h2o_tail");
    public static final ModelLayerLocation SIREN_TAIL_LAYER = createTailModelLayer("siren_tail");
    public static void init() {
        registerConfigScreen();
        ColorHandlerRegistry.registerItemColors((stack, index) -> index > 0 ? DyedItemColor.getOrDefault(stack, -1) : -1, RegistryHandler.SEA_NECKLACE::get);
        ColorHandlerRegistry.registerItemColors((stack, index) -> DyedItemColor.getOrDefault(stack, -1), RegistryHandler.MERMAID_BRA_MODIFIER::get, RegistryHandler.TAIL_GRADIENT_MODIFIER::get);
        EntityModelLayerRegistry.register(DEFAULT_TAIL_LAYER, () -> TailLayerDefinitions.getDefault(false));
        EntityModelLayerRegistry.register(H2O_TAIL_LAYER, () -> TailLayerDefinitions.getDefault(true));
        EntityModelLayerRegistry.register(SIREN_TAIL_LAYER, TailLayerDefinitions::getSiren);
    }
    private static ModelLayerLocation createTailModelLayer(String name) {
        ModelLayerLocation location = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Mermod.MOD_ID, name), "main");
        TAIL_MODEL_LAYERS.add(location);
        return location;
    }
    private static void registerConfigScreen() {
        if(!Platform.isFabric() && Platform.isModLoaded("cloth_config")) {
            Platform.getMod(Mermod.MOD_ID).registerConfigurationScreen(MermodClothConfigScreen::build);
        }
    }
    public static TailStyle getTailStyle(Player player) {
        ItemStack necklace = Mermod.getNecklace(player);
        if(!necklace.isEmpty()) {
            return TailStyle.fromNecklace(necklace);
        }
        return MermodPlatform.getTailStyle(player);
    }
    public static boolean hasTailStyle(Player player) {
        ItemStack necklace = Mermod.getNecklace(player);
        return !necklace.isEmpty() || MermodPlatform.hasTailStyle(player);
    }
    public static boolean shouldTryRenderingTail(Player player) {
        if(!player.isInvisible()) {
            return !Mermod.figuraLoaded || MermodFiguraAPI.isTailVisible(player.getUUID());
        }
        return false;
    }
    public static TailStyle getRenderedTailStyle(Player player) {
        if(shouldTryRenderingTail(player)) {
            TailStyle style = getTailStyle(player);
            if(style != null && (player.isInWater() || style.permanent())) {
                return style;
            }
        }
        return null;
    }
    public static boolean shouldRenderTail(Player player) {
        if(player.isInWater()) {
            return shouldTryRenderingTail(player) && hasTailStyle(player);
        }
        return getRenderedTailStyle(player) != null;
    }
}
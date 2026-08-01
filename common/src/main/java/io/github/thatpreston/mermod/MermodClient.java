package io.github.thatpreston.mermod;

import dev.architectury.platform.Platform;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import io.github.thatpreston.mermod.client.render.TailStyle;
import io.github.thatpreston.mermod.client.render.model.TailLayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MermodClient {
    public static final List<ModelLayerLocation> TAIL_MODEL_LAYERS = new ArrayList<>();
    public static final ModelLayerLocation DEFAULT_TAIL_LAYER = createTailModelLayer("default_tail");
    public static final ModelLayerLocation H2O_TAIL_LAYER = createTailModelLayer("h2o_tail");
    public static final ModelLayerLocation SIREN_TAIL_LAYER = createTailModelLayer("siren_tail");
    public static boolean irisLoaded;
    public static void init() {
        irisLoaded = Platform.isModLoaded("iris");
        EntityModelLayerRegistry.register(DEFAULT_TAIL_LAYER, TailLayerDefinitions::getDefault);
        EntityModelLayerRegistry.register(H2O_TAIL_LAYER, TailLayerDefinitions::getDefaultDorsalFins);
        EntityModelLayerRegistry.register(SIREN_TAIL_LAYER, TailLayerDefinitions::getSiren);
    }
    private static ModelLayerLocation createTailModelLayer(String name) {
        ModelLayerLocation location = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Mermod.MOD_ID, name), "main");
        TAIL_MODEL_LAYERS.add(location);
        return location;
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
            return true;
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
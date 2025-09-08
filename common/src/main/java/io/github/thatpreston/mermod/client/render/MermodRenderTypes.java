package io.github.thatpreston.mermod.client.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import io.github.thatpreston.mermod.Mermod;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.TriState;

import java.util.function.Function;

import static net.minecraft.client.renderer.RenderStateShard.*;

public class MermodRenderTypes {
    public static final RenderPipeline ARMOR_TRANSLUCENT_CULL_PIPELINE;
    private static final Function<ResourceLocation, RenderType> ARMOR_TRANSLUCENT_CULL;
    public static RenderType armorTranslucentCull(ResourceLocation location) {
        return ARMOR_TRANSLUCENT_CULL.apply(location);
    }
    static {
        ARMOR_TRANSLUCENT_CULL_PIPELINE = RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET).withLocation(ResourceLocation.fromNamespaceAndPath(Mermod.MOD_ID, "pipeline/armor_translucent_cull")).withShaderDefine("ALPHA_CUTOUT", 0.1F).withShaderDefine("NO_OVERLAY").withBlend(BlendFunction.TRANSLUCENT).withCull(true).build();
        ARMOR_TRANSLUCENT_CULL = Util.memoize((location) -> {
            RenderType.CompositeState state = RenderType.CompositeState.builder().setTextureState(new RenderStateShard.TextureStateShard(location, TriState.FALSE, false)).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).setLayeringState(VIEW_OFFSET_Z_LAYERING).createCompositeState(true);
            return RenderType.create("armor_translucent_cull", 1536, true, true, ARMOR_TRANSLUCENT_CULL_PIPELINE, state);
        });
    }
}
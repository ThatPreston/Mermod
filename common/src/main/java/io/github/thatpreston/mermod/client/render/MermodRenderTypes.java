package io.github.thatpreston.mermod.client.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import io.github.thatpreston.mermod.Mermod;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

import static net.minecraft.client.renderer.RenderStateShard.*;

public class MermodRenderTypes {
    public static final RenderPipeline ARMOR_TRANSLUCENT_CULL_PIPELINE;
    public static final RenderPipeline GLINT_CULL_PIPELINE;
    private static final Function<ResourceLocation, RenderType> ARMOR_TRANSLUCENT_CULL;
    private static final RenderType ARMOR_ENTITY_GLINT_CULL;
    public static RenderType armorTranslucentCull(ResourceLocation location) {
        return ARMOR_TRANSLUCENT_CULL.apply(location);
    }
    public static RenderType armorEntityGlintCull() {
        return ARMOR_ENTITY_GLINT_CULL;
    }
    static {
        ARMOR_TRANSLUCENT_CULL_PIPELINE = RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET).withLocation(ResourceLocation.fromNamespaceAndPath(Mermod.MOD_ID, "pipeline/armor_translucent_cull")).withShaderDefine("ALPHA_CUTOUT", 0.1F).withShaderDefine("NO_OVERLAY").withBlend(BlendFunction.TRANSLUCENT).withCull(true).build();
        GLINT_CULL_PIPELINE = RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET, RenderPipelines.FOG_SNIPPET, RenderPipelines.GLOBALS_SNIPPET).withLocation(ResourceLocation.fromNamespaceAndPath(Mermod.MOD_ID, "pipeline/glint_cull")).withVertexShader("core/glint").withFragmentShader("core/glint").withSampler("Sampler0").withDepthWrite(false).withCull(true).withDepthTestFunction(DepthTestFunction.EQUAL_DEPTH_TEST).withBlend(BlendFunction.GLINT).withVertexFormat(DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS).build();
        ARMOR_TRANSLUCENT_CULL = Util.memoize((location) -> {
            RenderType.CompositeState state = RenderType.CompositeState.builder().setTextureState(new RenderStateShard.TextureStateShard(location, false)).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).setLayeringState(VIEW_OFFSET_Z_LAYERING).createCompositeState(true);
            return RenderType.create("armor_translucent_cull", 1536, true, true, ARMOR_TRANSLUCENT_CULL_PIPELINE, state);
        });
        ARMOR_ENTITY_GLINT_CULL = RenderType.create("armor_entity_glint_cull", 1536, GLINT_CULL_PIPELINE, RenderType.CompositeState.builder().setTextureState(new RenderStateShard.TextureStateShard(ItemRenderer.ENCHANTED_GLINT_ARMOR, false)).setTexturingState(ARMOR_ENTITY_GLINT_TEXTURING).setLayeringState(VIEW_OFFSET_Z_LAYERING).createCompositeState(false));
    }
}
package io.github.thatpreston.mermod.client.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import io.github.thatpreston.mermod.Mermod;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.renderer.rendertype.*;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

import static net.minecraft.client.renderer.RenderPipelines.*;

public class MermodRenderTypes {
    public static final RenderPipeline ARMOR_TRANSLUCENT_CULL_PIPELINE;
    private static final Function<Identifier, RenderType> ARMOR_TRANSLUCENT_CULL;
    public static final RenderPipeline GLINT_CULL_PIPELINE;
    private static final RenderType ARMOR_ENTITY_GLINT_CULL;
    public static RenderType armorTranslucentCull(Identifier identifier) {
        return ARMOR_TRANSLUCENT_CULL.apply(identifier);
    }
    public static RenderType armorEntityGlintCull() {
        return ARMOR_ENTITY_GLINT_CULL;
    }
    static {
        ARMOR_TRANSLUCENT_CULL_PIPELINE = RenderPipeline.builder(ENTITY_SNIPPET).withLocation(Identifier.fromNamespaceAndPath(Mermod.MOD_ID, "pipeline/armor_translucent_cull")).withShaderDefine("ALPHA_CUTOUT", 0.1F).withShaderDefine("NO_OVERLAY").withShaderDefine("PER_FACE_LIGHTING").withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT)).withCull(true).build();
        ARMOR_TRANSLUCENT_CULL = Util.memoize((identifier) -> {
            RenderSetup renderSetup = RenderSetup.builder(ARMOR_TRANSLUCENT_CULL_PIPELINE).withTexture("Sampler0", identifier).useLightmap().useOverlay().setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).affectsCrumbling().sortOnUpload().setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE).createRenderSetup();
            return RenderType.create("armor_translucent_cull", renderSetup);
        });
        GLINT_CULL_PIPELINE = RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET, FOG_SNIPPET, GLOBALS_SNIPPET).withLocation(Identifier.fromNamespaceAndPath(Mermod.MOD_ID, "pipeline/glint_cull")).withVertexShader("core/glint").withFragmentShader("core/glint").withSampler("Sampler0").withCull(true).withColorTargetState(new ColorTargetState(BlendFunction.GLINT)).withVertexFormat(DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS).withDepthStencilState(new DepthStencilState(CompareOp.EQUAL, false)).build();
        ARMOR_ENTITY_GLINT_CULL = RenderType.create("armor_entity_glint_cull", RenderSetup.builder(RenderPipelines.GLINT).withTexture("Sampler0", ItemFeatureRenderer.ENCHANTED_GLINT_ARMOR).setTextureTransform(TextureTransform.ARMOR_ENTITY_GLINT_TEXTURING).setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).createRenderSetup());
    }
}
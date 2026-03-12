package io.github.thatpreston.mermod.client.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import io.github.thatpreston.mermod.Mermod;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.rendertype.*;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

import static net.minecraft.client.renderer.RenderPipelines.*;

public class MermodRenderTypes {
    public static final RenderPipeline ARMOR_TRANSLUCENT_CULL_PIPELINE;
    public static final RenderPipeline GLINT_CULL_PIPELINE;
    private static final Function<Identifier, RenderType> ARMOR_TRANSLUCENT_CULL;
    private static final RenderType ARMOR_ENTITY_GLINT_CULL;
    public static RenderType armorTranslucentCull(Identifier identifier) {
        return ARMOR_TRANSLUCENT_CULL.apply(identifier);
    }
    public static RenderType armorEntityGlintCull() {
        return ARMOR_ENTITY_GLINT_CULL;
    }
    static {
        ARMOR_TRANSLUCENT_CULL_PIPELINE = RenderPipeline.builder(ENTITY_SNIPPET).withLocation(Identifier.fromNamespaceAndPath(Mermod.MOD_ID, "pipeline/armor_translucent_cull")).withShaderDefine("ALPHA_CUTOUT", 0.1F).withShaderDefine("NO_OVERLAY").withShaderDefine("PER_FACE_LIGHTING").withBlend(BlendFunction.TRANSLUCENT).withCull(true).build();
        GLINT_CULL_PIPELINE = RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET, FOG_SNIPPET, GLOBALS_SNIPPET).withLocation(Identifier.fromNamespaceAndPath(Mermod.MOD_ID, "pipeline/glint_cull")).withVertexShader("core/glint").withFragmentShader("core/glint").withSampler("Sampler0").withDepthWrite(false).withCull(true).withDepthTestFunction(DepthTestFunction.EQUAL_DEPTH_TEST).withBlend(BlendFunction.GLINT).withVertexFormat(DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS).build();
        ARMOR_TRANSLUCENT_CULL = Util.memoize((identifier) -> {
            RenderSetup renderSetup = RenderSetup.builder(ARMOR_TRANSLUCENT_CULL_PIPELINE).withTexture("Sampler0", identifier).useLightmap().useOverlay().setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).affectsCrumbling().sortOnUpload().setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE).createRenderSetup();
            return RenderType.create("armor_translucent_cull", renderSetup);
        });
        ARMOR_ENTITY_GLINT_CULL = RenderType.create("armor_entity_glint_cull", RenderSetup.builder(GLINT_CULL_PIPELINE).withTexture("Sampler0", ItemRenderer.ENCHANTED_GLINT_ARMOR).setTextureTransform(TextureTransform.ARMOR_ENTITY_GLINT_TEXTURING).setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).createRenderSetup());
    }
}
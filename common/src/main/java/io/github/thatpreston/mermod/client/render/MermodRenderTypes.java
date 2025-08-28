package io.github.thatpreston.mermod.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.TriState;

import java.util.function.Function;

public class MermodRenderTypes extends RenderType {
    private static final Function<ResourceLocation, RenderType> ARMOR_TRANSLUCENT_CULL;
    public MermodRenderTypes(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }
    public static RenderType armorTranslucentCull(ResourceLocation location) {
        return ARMOR_TRANSLUCENT_CULL.apply(location);
    }
    static {
        ARMOR_TRANSLUCENT_CULL = Util.memoize((location) -> {
            CompositeState state = RenderType.CompositeState.builder().setShaderState(RENDERTYPE_ARMOR_TRANSLUCENT_SHADER).setTextureState(new RenderStateShard.TextureStateShard(location, TriState.FALSE, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(CULL).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).setLayeringState(VIEW_OFFSET_Z_LAYERING).createCompositeState(true);
            return create("armor_translucent_cull", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, true, state);
        });
    }
}
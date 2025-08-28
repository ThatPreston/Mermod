package io.github.thatpreston.mermod.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.thatpreston.mermod.MermodClient;
import io.github.thatpreston.mermod.client.render.model.TailModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;

import java.util.ArrayList;
import java.util.List;

public class TailRenderLayer extends RenderLayer<PlayerRenderState, PlayerModel> {
    private final List<TailModel> models = new ArrayList<>();
    public TailRenderLayer(PlayerRenderer parent, EntityModelSet set) {
        super(parent);
        for(ModelLayerLocation layer : MermodClient.TAIL_MODEL_LAYERS) {
            models.add(new TailModel(set.bakeLayer(layer)));
        }
    }
    @Override
    public void render(PoseStack stack, MultiBufferSource source, int light, PlayerRenderState state, float limbSwing, float limbSwingAmount) {
        PlayerRenderStateExtension extension = (PlayerRenderStateExtension)state;
        TailStyle style = extension.getTailStyle();
        if(style != null) {
            TailModel model = getModel(style.model());
            if(model != null) {
                stack.pushPose();
                model.setupAnim(state);
                model.copyFrom(this.getParentModel());
                VertexConsumer consumer = ItemRenderer.getArmorFoilBuffer(source, MermodRenderTypes.armorTranslucentCull(style.texture()), style.hasGlint());
                model.render(stack, consumer, light, OverlayTexture.NO_OVERLAY, style);
                stack.popPose();
            }
        }
    }
    private TailModel getModel(int model) {
        if(model >= 0 && model < models.size()) {
            return models.get(model);
        }
        return null;
    }
}
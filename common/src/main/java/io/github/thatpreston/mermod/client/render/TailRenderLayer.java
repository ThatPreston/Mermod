package io.github.thatpreston.mermod.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.thatpreston.mermod.MermodClient;
import io.github.thatpreston.mermod.client.render.model.TailModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;

import java.util.ArrayList;
import java.util.List;

public class TailRenderLayer extends RenderLayer<AvatarRenderState, PlayerModel> {
    private final List<TailModel> models = new ArrayList<>();
    public TailRenderLayer(RenderLayerParent<AvatarRenderState, PlayerModel> parent, EntityModelSet set) {
        super(parent);
        for(ModelLayerLocation layer : MermodClient.TAIL_MODEL_LAYERS) {
            models.add(new TailModel(set.bakeLayer(layer)));
        }
    }
    @Override
    public void submit(PoseStack stack, SubmitNodeCollector collector, int light, AvatarRenderState state, float yRot, float xRot) {
        AvatarRenderStateExtension extension = (AvatarRenderStateExtension)state;
        TailStyle style = extension.mermod$getTailStyle();
        if(style != null) {
            TailModel model = getModel(style.model());
            if(model != null) {
                stack.pushPose();
                this.getParentModel().body.translateAndRotate(stack);
                model.setupAnim(state);
                model.submit(stack, collector, light, OverlayTexture.NO_OVERLAY, style, MermodRenderTypes.armorTranslucentCull(style.texture()), state.outlineColor);
                if(style.hasGlint()) {
                    model.submit(stack, collector.order(1), light, OverlayTexture.NO_OVERLAY, style, MermodRenderTypes.armorEntityGlintCull(), state.outlineColor);
                }
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
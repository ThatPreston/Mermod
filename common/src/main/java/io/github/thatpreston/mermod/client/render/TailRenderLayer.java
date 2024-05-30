package io.github.thatpreston.mermod.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.thatpreston.mermod.MermodClient;
import io.github.thatpreston.mermod.client.render.model.TailModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class TailRenderLayer<T extends Player, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private final List<TailModel> models = new ArrayList<>();
    public TailRenderLayer(RenderLayerParent<T, M> parent, EntityModelSet set) {
        super(parent);
        for(ModelLayerLocation layer : MermodClient.TAIL_MODEL_LAYERS) {
            models.add(new TailModel(set.bakeLayer(layer)));
        }
    }
    @Override
    public void render(PoseStack stack, MultiBufferSource source, int light, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float age, float yaw, float pitch) {
        TailStyle style = MermodClient.getRenderedTailStyle(entity);
        if(style != null) {
            TailModel model = getModel(style.model());
            stack.pushPose();
            VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(source, RenderType.entityTranslucentCull(style.texture()), false, style.hasGlint());
            model.copyFrom(this.getParentModel());
            model.setupAnim(entity, limbSwing, limbSwingAmount, age, yaw, pitch);
            model.render(stack, consumer, light, OverlayTexture.NO_OVERLAY, style);
            stack.popPose();
        }
    }
    private TailModel getModel(int model) {
        if(model >= 0 && model < models.size()) {
            return models.get(model);
        }
        return models.get(0);
    }
}
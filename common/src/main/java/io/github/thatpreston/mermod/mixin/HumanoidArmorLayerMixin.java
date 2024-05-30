package io.github.thatpreston.mermod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.thatpreston.mermod.MermodClient;
import io.github.thatpreston.mermod.client.render.TailStyle;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.world.entity.EquipmentSlot.*;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
    public HumanoidArmorLayerMixin(RenderLayerParent<T, M> parent) {
        super(parent);
    }
    @Inject(method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;)V", at = @At("HEAD"), cancellable = true)
    private void onRenderArmorPiece(PoseStack stack, MultiBufferSource source, T entity, EquipmentSlot slot, int i, A model, CallbackInfo info) {
        if(entity instanceof Player player) {
            TailStyle style = MermodClient.getRenderedTailStyle(player);
            if(style != null) {
                boolean hasBra = style.hasBra();
                if(slot == LEGS || slot == FEET || (hasBra && slot == CHEST)) {
                    info.cancel();
                }
            }
        }
    }
}
package io.github.thatpreston.mermod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.thatpreston.mermod.MermodClient;
import io.github.thatpreston.mermod.client.render.model.TailModel;
import io.github.thatpreston.mermod.config.MermodConfig;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> model, float shadowRadius) {
        super(context, model, shadowRadius);
    }
    @Inject(method = "setupRotations(Lnet/minecraft/client/player/AbstractClientPlayer;Lcom/mojang/blaze3d/vertex/PoseStack;FFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;setupRotations(Lnet/minecraft/world/entity/LivingEntity;Lcom/mojang/blaze3d/vertex/PoseStack;FFF)V", shift = At.Shift.AFTER, ordinal = 1), cancellable = true)
    private void onSetupRotations(AbstractClientPlayer player, PoseStack stack, float age, float yaw, float partialTicks, CallbackInfo info) {
        if(MermodConfig.getReplaceSwimAnimation()) {
            if(MermodClient.shouldRenderTail(player)) {
                float swimAmount = player.getSwimAmount(partialTicks);
                float swimAngle = player.isInWater() ? -90 - player.getViewXRot(partialTicks) : -90;
                if(player.isInWater()) {
                    swimAngle += TailModel.getAngleWithOffset(age, -0.1F, 0.035F, 10) + 6;
                }
                stack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(swimAmount, 0, swimAngle)));
                if(player.isVisuallySwimming()) {
                    stack.translate(0, -1, 0.3F);
                }
                info.cancel();
            }
        }
    }
    @Inject(method = "setModelProperties(Lnet/minecraft/client/player/AbstractClientPlayer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;isCrouching()Z", shift = At.Shift.AFTER))
    private void onSetModelProperties(AbstractClientPlayer player, CallbackInfo info) {
        if(MermodClient.shouldRenderTail(player)) {
            PlayerModel<AbstractClientPlayer> model = this.getModel();
            model.rightLeg.visible = false;
            model.rightPants.visible = false;
            model.leftLeg.visible = false;
            model.leftPants.visible = false;
        }
    }
}
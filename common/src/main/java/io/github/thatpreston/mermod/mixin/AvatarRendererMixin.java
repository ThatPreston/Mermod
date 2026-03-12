package io.github.thatpreston.mermod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.thatpreston.mermod.MermodClient;
import io.github.thatpreston.mermod.client.render.AvatarRenderStateExtension;
import io.github.thatpreston.mermod.client.render.model.TailModel;
import io.github.thatpreston.mermod.config.MermodConfig;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public abstract class AvatarRendererMixin<A extends Avatar & ClientAvatarEntity> extends LivingEntityRenderer<A, AvatarRenderState, PlayerModel> {
    public AvatarRendererMixin(EntityRendererProvider.Context context, PlayerModel model, float shadowRadius) {
        super(context, model, shadowRadius);
    }
    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
    private void onExtractRenderState(A avatar, AvatarRenderState state, float partialTicks, CallbackInfo info) {
        AvatarRenderStateExtension extension = (AvatarRenderStateExtension)state;
        if(avatar instanceof Player player) {
            extension.mermod$setTailStyle(MermodClient.getRenderedTailStyle(player));
            extension.mermod$setOnGround(avatar.onGround());
        }
    }
    @Inject(method = "setupRotations(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;setupRotations(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;FF)V", shift = At.Shift.AFTER, ordinal = 1), cancellable = true)
    private void onSetupRotations(AvatarRenderState state, PoseStack stack, float f, float g, CallbackInfo info) {
        if(MermodConfig.shouldReplaceSwimAnimation()) {
            if(state instanceof AvatarRenderStateExtension extension && extension.mermod$getTailStyle() != null) {
                float swimAngle = state.isInWater ? -90 - state.xRot : -90;
                if(state.isInWater) {
                    float pos = state.ageInTicks * 0.2F + state.walkAnimationPos * 0.8F;
                    swimAngle += TailModel.getWaveHeight(pos, -0.1F, 0.035F, 10) + 6;
                }
                stack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(state.swimAmount, 0, swimAngle)));
                if(state.isVisuallySwimming) {
                    stack.translate(0, -1, 0.3F);
                }
                info.cancel();
            }
        }
    }
}
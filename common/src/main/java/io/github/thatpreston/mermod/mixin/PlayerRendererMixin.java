package io.github.thatpreston.mermod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.thatpreston.mermod.MermodClient;
import io.github.thatpreston.mermod.client.render.PlayerRenderStateExtension;
import io.github.thatpreston.mermod.client.render.model.TailModel;
import io.github.thatpreston.mermod.config.MermodConfig;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerRenderState, PlayerModel> {
    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel model, float shadowRadius) {
        super(context, model, shadowRadius);
    }
    @Inject(method = "extractRenderState(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;F)V", at = @At("TAIL"))
    private void onExtractRenderState(AbstractClientPlayer player, PlayerRenderState state, float partialTicks, CallbackInfo info) {
        PlayerRenderStateExtension extension = (PlayerRenderStateExtension)state;
        extension.setTailStyle(MermodClient.getRenderedTailStyle(player));
        extension.setOnGround(player.onGround());
    }
    @Inject(method = "setupRotations(Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;setupRotations(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;FF)V", shift = At.Shift.AFTER, ordinal = 1), cancellable = true)
    private void onSetupRotations(PlayerRenderState state, PoseStack stack, float f, float g, CallbackInfo info) {
        if(MermodConfig.shouldReplaceSwimAnimation()) {
            if(state instanceof PlayerRenderStateExtension extension && extension.getTailStyle() != null) {
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
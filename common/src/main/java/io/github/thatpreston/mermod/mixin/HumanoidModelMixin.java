package io.github.thatpreston.mermod.mixin;

import io.github.thatpreston.mermod.client.render.AvatarRenderStateExtension;
import io.github.thatpreston.mermod.client.render.model.TailModel;
import io.github.thatpreston.mermod.config.MermodConfig;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends HumanoidRenderState> extends EntityModel<T> implements ArmedModel, HeadedModel {
    @Shadow @Final public ModelPart rightArm;
    @Shadow @Final public ModelPart leftArm;
    @Shadow @Final public ModelPart head;
    public HumanoidModelMixin(ModelPart root) {
        super(root);
    }
    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At(value = "CONSTANT", args = "floatValue=26.0F", ordinal = 0), cancellable = true)
    private void onSetupAnim(T state, CallbackInfo info) {
        if(MermodConfig.shouldReplaceSwimAnimation()) {
            if(state instanceof AvatarRenderStateExtension extension && extension.mermod$getTailStyle() != null && state.isInWater) {
                float swimAmount = state.swimAmount;
                float right = state.attackArm == HumanoidArm.RIGHT && state.attackTime > 0 ? 0 : swimAmount;
                float left = state.attackArm == HumanoidArm.LEFT && state.attackTime > 0 ? 0 : swimAmount;
                if(!state.isUsingItem) {
                    float pos = state.ageInTicks * 0.2F + state.walkAnimationPos * 0.8F;
                    float x = (-Mth.PI * 0.9F) + TailModel.getWaveHeight(pos, 0.7F, 0.035F, Mth.PI / 10);
                    float z = Mth.PI / 10;
                    this.rightArm.xRot = Mth.lerp(right, this.rightArm.xRot, x);
                    this.leftArm.xRot = Mth.lerp(left, this.leftArm.xRot, x);
                    this.rightArm.zRot = Mth.lerp(right, this.rightArm.zRot, z);
                    this.leftArm.zRot = Mth.lerp(left, this.leftArm.zRot, -z);
                }
                this.head.xRot = Mth.rotLerpRad(swimAmount, state.xRot * 0.017453292F, -Mth.PI / 3);
                this.head.yRot *= Mth.lerp(swimAmount, 1, 0.5F);
                info.cancel();
            }
        }
    }
}
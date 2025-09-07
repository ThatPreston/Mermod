package io.github.thatpreston.mermod.mixin;

import io.github.thatpreston.mermod.MermodClient;
import io.github.thatpreston.mermod.client.render.model.TailModel;
import io.github.thatpreston.mermod.config.MermodConfig;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity> extends AgeableListModel<T> implements ArmedModel, HeadedModel {
    @Shadow @Final public ModelPart head;
    @Shadow @Final public ModelPart hat;
    @Shadow @Final public ModelPart rightArm;
    @Shadow @Final public ModelPart leftArm;
    @Shadow public float swimAmount;
    @Shadow protected abstract HumanoidArm getAttackArm(T entity);
    @Shadow
    protected abstract float rotlerpRad(float f, float g, float h);
    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;getAttackArm(Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/entity/HumanoidArm;", shift = At.Shift.AFTER), cancellable = true)
    private void onSetupAnim(T entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, CallbackInfo info) {
        if(MermodConfig.shouldReplaceSwimAnimation() && entity.isInWater()) {
            if(entity instanceof Player player && MermodClient.shouldRenderTail(player)) {
                HumanoidArm arm = this.getAttackArm(entity);
                float right = arm == HumanoidArm.RIGHT && this.attackTime > 0 ? 0 : this.swimAmount;
                float left = arm == HumanoidArm.LEFT && this.attackTime > 0 ? 0 : this.swimAmount;
                if(!entity.isUsingItem()) {
                    float pos = age * 0.2F + limbSwing * 0.8F;
                    float x = (-Mth.PI * 0.9F) + TailModel.getWaveHeight(pos, 0.7F, 0.035F, Mth.PI / 10);
                    float z = Mth.PI / 10;
                    this.rightArm.xRot = Mth.lerp(right, this.rightArm.xRot, x);
                    this.leftArm.xRot = Mth.lerp(left, this.leftArm.xRot, x);
                    this.rightArm.zRot = Mth.lerp(right, this.rightArm.zRot, z);
                    this.leftArm.zRot = Mth.lerp(left, this.leftArm.zRot, -z);
                }
                this.head.xRot = this.rotlerpRad(swimAmount, pitch * 0.017453292F, -Mth.PI / 3);
                this.head.yRot *= Mth.lerp(swimAmount, 1, 0.5F);
                this.hat.copyFrom(this.head);
                info.cancel();
            }
        }
    }
}
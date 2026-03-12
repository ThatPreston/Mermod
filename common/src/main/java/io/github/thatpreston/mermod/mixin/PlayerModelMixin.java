package io.github.thatpreston.mermod.mixin;

import io.github.thatpreston.mermod.client.render.AvatarRenderStateExtension;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public abstract class PlayerModelMixin extends HumanoidModel<AvatarRenderState> {
    @Shadow @Final public ModelPart rightPants;
    @Shadow @Final public ModelPart leftPants;
    public PlayerModelMixin(ModelPart root) {
        super(root);
    }
    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V"))
    private void onSetupAnim(AvatarRenderState state, CallbackInfo info) {
        if(state instanceof AvatarRenderStateExtension extension && extension.mermod$getTailStyle() != null) {
            this.rightLeg.visible = false;
            this.leftLeg.visible = false;
            this.rightPants.visible = false;
            this.leftPants.visible = false;
        }
    }
}
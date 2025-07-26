package io.github.thatpreston.mermod.mixin;

import io.github.thatpreston.mermod.client.render.PlayerRenderStateExtension;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public abstract class PlayerModelMixin extends HumanoidModel<PlayerRenderState> {
    @Shadow @Final public ModelPart rightPants;
    @Shadow @Final public ModelPart leftPants;
    public PlayerModelMixin(ModelPart root) {
        super(root);
    }
    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V"))
    private void onSetupAnim(PlayerRenderState state, CallbackInfo info) {
        if(state instanceof PlayerRenderStateExtension extension && extension.getTailStyle() != null) {
            this.rightLeg.visible = false;
            this.leftLeg.visible = false;
            this.rightPants.visible = false;
            this.leftPants.visible = false;
        }
    }
}
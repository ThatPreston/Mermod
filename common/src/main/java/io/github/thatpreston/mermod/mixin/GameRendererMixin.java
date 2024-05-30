package io.github.thatpreston.mermod.mixin;

import io.github.thatpreston.mermod.config.MermodConfig;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "getNightVisionScale(Lnet/minecraft/world/entity/LivingEntity;F)F", at = @At("HEAD"), cancellable = true)
    private static void onGetNightVisionScale(LivingEntity entity, float f, CallbackInfoReturnable<Float> info) {
        if(entity.hasEffect(MobEffects.NIGHT_VISION) && MermodConfig.getNightVisionFlashingFix()) {
            info.setReturnValue(1.0F);
        }
    }
}
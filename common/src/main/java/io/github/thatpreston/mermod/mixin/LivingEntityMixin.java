package io.github.thatpreston.mermod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.config.MermodConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @ModifyExpressionValue(method = "travel(Lnet/minecraft/world/phys/Vec3;)V", at = @At(value = "CONSTANT", args = "floatValue=0.02F", ordinal = 0))
    private float modifyFloat(float original) {
        if((Object)this instanceof Player player) {
            if(!Mermod.getNecklace(player).isEmpty()) {
                return original * (float)MermodConfig.getSwimSpeedMultiplier();
            }
        }
        return original;
    }
}
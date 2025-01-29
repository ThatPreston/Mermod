package io.github.thatpreston.mermod.fabric.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.thatpreston.mermod.Mermod;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @ModifyExpressionValue(method = "getDestroySpeed(Lnet/minecraft/world/level/block/state/BlockState;)F", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeInstance;getValue()D"))
    private double getSubmergedMiningSpeed(double original) {
        if((Object)this instanceof Player player) {
            if(!Mermod.getNecklace(player).isEmpty()) {
                return Math.max(1, original);
            }
        }
        return original;
    }
}
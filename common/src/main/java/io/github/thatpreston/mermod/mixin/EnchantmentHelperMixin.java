package io.github.thatpreston.mermod.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.config.MermodConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    @ModifyReturnValue(method = "hasAquaAffinity(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("RETURN"))
    private static boolean hasNecklaceAquaAffinity(boolean original, LivingEntity entity) {
        if(!original && MermodConfig.getAquaAffinity() && entity instanceof Player player) {
            ItemStack necklace = Mermod.getNecklace(player);
            return !necklace.isEmpty();
        }
        return original;
    }
}
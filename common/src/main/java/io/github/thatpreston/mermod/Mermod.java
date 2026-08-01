package io.github.thatpreston.mermod;

import io.github.thatpreston.mermod.config.MermodConfig;
import io.github.thatpreston.mermod.registry.RegistryHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Mermod {
    public static final String MOD_ID = "mermod";
    public static void init() {
        RegistryHandler.register();
    }
    public static ItemStack getNecklaceFromAccessorySlot(Player player) {
        return MermodPlatform.getNecklaceFromAccessorySlot(player);
    }
    public static ItemStack getNecklace(Player player) {
        ItemStack accessorySlotNecklace = getNecklaceFromAccessorySlot(player);
        if(!accessorySlotNecklace.isEmpty()) {
            return accessorySlotNecklace;
        }
        ItemStack chestSlotNecklace = player.getItemBySlot(EquipmentSlot.CHEST);
        if(chestSlotNecklace.is(RegistryHandler.SEA_NECKLACE.get())) {
            return chestSlotNecklace;
        }
        return ItemStack.EMPTY;
    }
    public static void addEffects(LivingEntity entity) {
        if(entity.isInWater() && entity instanceof ServerPlayer player) {
            if(MermodConfig.isWaterBreathingEnabled()) {
                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 250, 0, true, false));
            }
            if(MermodConfig.isNightVisionEnabled()) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 250, 0, true, false));
            }
        }
    }
}
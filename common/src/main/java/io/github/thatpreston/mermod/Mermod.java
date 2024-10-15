package io.github.thatpreston.mermod;

import dev.architectury.platform.Platform;
import io.github.thatpreston.mermod.client.render.TailStyle;
import io.github.thatpreston.mermod.config.MermodConfig;
import io.github.thatpreston.mermod.registry.RegistryHandler;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class Mermod {
    public static boolean figuraLoaded;
    public static final String MOD_ID = "mermod";
    public static void init() {
        RegistryHandler.register();
        figuraLoaded = Platform.isModLoaded("figura");
    }
    public static void registerCauldronInteractions() {
        Map<Item, CauldronInteraction> map = CauldronInteraction.WATER.map();
        map.put(RegistryHandler.SEA_NECKLACE.get(), CauldronInteraction.DYED_ITEM);
        map.put(RegistryHandler.MERMAID_BRA_MODIFIER.get(), CauldronInteraction.DYED_ITEM);
        map.put(RegistryHandler.TAIL_GRADIENT_MODIFIER.get(), CauldronInteraction.DYED_ITEM);
    }
    public static ItemStack getNecklace(Player player) {
        ItemStack necklace = player.getItemBySlot(EquipmentSlot.CHEST);
        if(necklace.is(RegistryHandler.SEA_NECKLACE.get())) {
            return necklace;
        }
        return MermodPlatform.getNecklaceFromAccessorySlot(player);
    }
    public static TailStyle getTailStyle(Player player) {
        ItemStack necklace = getNecklace(player);
        if(!necklace.isEmpty()) {
            return TailStyle.fromNecklace(necklace);
        }
        return MermodPlatform.getTailStyle(player);
    }
    public static boolean hasTailStyle(Player player) {
        ItemStack necklace = getNecklace(player);
        return !necklace.isEmpty() || MermodPlatform.hasTailStyle(player);
    }
    public static int getItemColor(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("display");
        return tag != null && tag.contains("color", 99) ? tag.getInt("color") : 16777215;
    }
    public static void addEffects(LivingEntity entity) {
        if(entity.isInWater()) {
            if(MermodConfig.getWaterBreathing() && !entity.hasEffect(MobEffects.WATER_BREATHING)) {
                entity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200, 0, true, false));
            }
            if(MermodConfig.getNightVision() && !entity.hasEffect(MobEffects.NIGHT_VISION)) {
                entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 0, true, false));
            }
        }
    }
}
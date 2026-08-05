package io.github.thatpreston.mermod;

import io.github.thatpreston.mermod.config.MermodConfig;
import io.github.thatpreston.mermod.registry.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class Mermod {
    public static final String MOD_ID = "mermod";
    public static void init() {
        RegistryHandler.register();
    }
    public static void registerCauldronInteractions() {
        Map<Item, CauldronInteraction> map = CauldronInteraction.WATER.map();
        map.put(RegistryHandler.SEA_NECKLACE.get(), Mermod::cleanDyedItem);
        map.put(RegistryHandler.MERMAID_BRA_MODIFIER.get(), Mermod::cleanDyedItem);
        map.put(RegistryHandler.TAIL_GRADIENT_MODIFIER.get(), Mermod::cleanDyedItem);
    }
    private static InteractionResult cleanDyedItem(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
        if(!stack.is(ItemTags.DYEABLE) || !stack.has(DataComponents.DYED_COLOR)) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        } else {
            if(!level.isClientSide()) {
                stack.remove(DataComponents.DYED_COLOR);
                player.awardStat(Stats.CLEAN_ARMOR);
                LayeredCauldronBlock.lowerFillLevel(state, level, pos);
            }
            return InteractionResult.SUCCESS;
        }
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
        if(chestSlotNecklace.is(RegistryHandler.SEA_NECKLACE_TAG)) {
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
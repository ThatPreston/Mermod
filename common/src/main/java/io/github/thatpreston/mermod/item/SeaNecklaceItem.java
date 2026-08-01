package io.github.thatpreston.mermod.item;

import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.item.modifier.NecklaceModifier;
import io.github.thatpreston.mermod.item.modifier.NecklaceModifierItem;
import io.github.thatpreston.mermod.item.modifier.NecklaceModifiers;
import io.github.thatpreston.mermod.registry.RegistryHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SeaNecklaceItem extends Item {
    private static final Component MODIFIERS_TOOLTIP = Component.translatable("tooltip.mermod.modifiers").withStyle(ChatFormatting.GRAY);
    private static final Component REMOVE_MODIFIERS_TOOLTIP = Component.translatable("tooltip.mermod.remove_modifiers").withStyle(ChatFormatting.GRAY);
    public SeaNecklaceItem(Item.Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if(player.isCrouching()) {
            ItemStack necklace = player.getItemInHand(hand);
            ItemStack stack = removeModifier(necklace);
            if(!stack.isEmpty()) {
                if(!player.addItem(stack)) {
                    player.drop(stack, false);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(level, player, hand);
    }
    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
        if(entity instanceof LivingEntity livingEntity && slot == EquipmentSlot.CHEST) {
            Mermod.addEffects(livingEntity);
        }
    }
    public static void addModifiers(ItemStack necklace, List<ItemStack> list) {
        NecklaceModifiers modifiers = necklace.getOrDefault(RegistryHandler.NECKLACE_MODIFIERS_COMPONENT_TYPE.get(), NecklaceModifiers.EMPTY).copy();
        for(ItemStack stack : list) {
            NecklaceModifierItem item = (NecklaceModifierItem)stack.getItem();
            NecklaceModifier modifier = item.getModifier();
            if(stack.has(DataComponents.DYED_COLOR)) {
                DyedItemColor dyedItemColor = stack.get(DataComponents.DYED_COLOR);
                modifiers.add(item.getType(), modifier.withColor(dyedItemColor != null ? dyedItemColor.rgb() : 16777215));
            } else {
                modifiers.add(item.getType(), modifier);
            }
        }
        necklace.set(RegistryHandler.NECKLACE_MODIFIERS_COMPONENT_TYPE.get(), modifiers);
    }
    public static boolean canAddModifiers(ItemStack necklace, List<ItemStack> list) {
        NecklaceModifiers modifiers = necklace.getOrDefault(RegistryHandler.NECKLACE_MODIFIERS_COMPONENT_TYPE.get(), NecklaceModifiers.EMPTY);
        ArrayList<String> addedTypes = new ArrayList<>();
        for(ItemStack stack : list) {
            NecklaceModifierItem item = (NecklaceModifierItem)stack.getItem();
            String type = item.getType();
            if(addedTypes.contains(type) || modifiers.contains(type)) {
                return false;
            }
            addedTypes.add(type);
        }
        return true;
    }
    public static ItemStack removeModifier(ItemStack necklace) {
        NecklaceModifiers modifiers = necklace.get(RegistryHandler.NECKLACE_MODIFIERS_COMPONENT_TYPE.get());
        if(modifiers != null) {
            for(NecklaceModifierItem item : NecklaceModifierItem.MODIFIERS) {
                if(modifiers.canRemove(item)) {
                    NecklaceModifiers copy = modifiers.copy();
                    ItemStack stack = copy.remove(item);
                    necklace.set(RegistryHandler.NECKLACE_MODIFIERS_COMPONENT_TYPE.get(), copy);
                    if(!stack.isEmpty()) {
                        return stack;
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> consumer, TooltipFlag flag) {
        NecklaceModifiers component = stack.get(RegistryHandler.NECKLACE_MODIFIERS_COMPONENT_TYPE.get());
        if(component != null) {
            Map<String, NecklaceModifier> modifiers = component.modifiers();
            if(!modifiers.isEmpty()) {
                consumer.accept(CommonComponents.EMPTY);
                consumer.accept(MODIFIERS_TOOLTIP);
                for(NecklaceModifier modifier : modifiers.values()) {
                    consumer.accept(Component.literal(" ").append(Component.translatable("item.mermod." + modifier.id() + "_modifier").withStyle(Style.EMPTY.withColor(modifier.color()))));
                }
                consumer.accept(REMOVE_MODIFIERS_TOOLTIP);
            }
        }
    }
}
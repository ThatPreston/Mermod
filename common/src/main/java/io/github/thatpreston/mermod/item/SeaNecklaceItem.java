package io.github.thatpreston.mermod.item;

import dev.architectury.extensions.ItemExtension;
import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.item.modifier.DyeableNecklaceModifierItem;
import io.github.thatpreston.mermod.item.modifier.NecklaceModifier;
import io.github.thatpreston.mermod.item.modifier.NecklaceModifierItem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SeaNecklaceItem extends Item implements DyeableLeatherItem, ItemExtension {
    private static final Component MODIFIERS_TOOLTIP = Component.translatable("tooltip.mermod.modifiers").withStyle(ChatFormatting.GRAY);
    private static final Component REMOVE_MODIFIERS_TOOLTIP = Component.translatable("tooltip.mermod.remove_modifiers").withStyle(ChatFormatting.GRAY);
    public SeaNecklaceItem() {
        super(new Item.Properties().arch$tab(CreativeModeTabs.TOOLS_AND_UTILITIES).stacksTo(1));
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack necklace = player.getItemInHand(hand);
        if(player.isCrouching()) {
            ItemStack stack = removeModifier(necklace);
            if(!stack.isEmpty()) {
                if(!player.addItem(stack)) {
                    player.drop(stack, false);
                }
                return InteractionResultHolder.sidedSuccess(necklace, level.isClientSide());
            }
        }
        return InteractionResultHolder.pass(necklace);
    }
    @Override
    public EquipmentSlot getCustomEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.CHEST;
    }
    @Override
    public void tickArmor(ItemStack stack, Player player) {
        Mermod.addEffects(player);
    }
    public static void addModifiers(ItemStack necklace, List<ItemStack> modifiers) {
        for(ItemStack stack : modifiers) {
            NecklaceModifierItem item = (NecklaceModifierItem)stack.getItem();
            item.addModifier(necklace, stack);
        }
    }
    public static boolean canAddModifiers(ItemStack necklace, List<ItemStack> modifiers) {
        ArrayList<String> addedTypes = new ArrayList<>();
        for(ItemStack stack : modifiers) {
            NecklaceModifierItem item = (NecklaceModifierItem)stack.getItem();
            String type = item.getModifier().getType();
            if(addedTypes.contains(type) || !item.canAddModifier(necklace)) {
                return false;
            }
            addedTypes.add(type);
        }
        return true;
    }
    public static ItemStack removeModifier(ItemStack necklace) {
        for(NecklaceModifierItem item : NecklaceModifierItem.MODIFIERS) {
            ItemStack stack = item.removeModifier(necklace);
            if(!stack.isEmpty()) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
    @Override
    public void appendHoverText(ItemStack necklace, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        CompoundTag tag = necklace.getOrCreateTagElement("necklace_modifiers");
        List<Component> modifierComponents = new ArrayList<>();
        for(NecklaceModifierItem item : NecklaceModifierItem.MODIFIERS) {
            NecklaceModifier modifier = item.getModifier();
            if(modifier.isAdded(tag)) {
                int color = item instanceof DyeableNecklaceModifierItem ? tag.getInt(modifier.getType() + "_color") : modifier.getTooltipColor();
                modifierComponents.add(Component.literal(" ").append(Component.translatable("item.mermod." + modifier.getId() + "_modifier").withStyle(Style.EMPTY.withColor(color))));
            }
        }
        if(!modifierComponents.isEmpty()) {
            list.add(CommonComponents.EMPTY);
            list.add(MODIFIERS_TOOLTIP);
            list.addAll(modifierComponents);
            list.add(REMOVE_MODIFIERS_TOOLTIP);
        }
    }
}
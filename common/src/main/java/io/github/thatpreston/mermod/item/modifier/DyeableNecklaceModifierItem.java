package io.github.thatpreston.mermod.item.modifier;

import io.github.thatpreston.mermod.Mermod;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DyeableNecklaceModifierItem extends NecklaceModifierItem implements DyeableLeatherItem {
    public DyeableNecklaceModifierItem(NecklaceModifier modifier) {
        super(modifier);
    }
    @Override
    public CompoundTag addModifier(ItemStack necklace, ItemStack stack) {
        CompoundTag tag = super.addModifier(necklace, stack);
        tag.putInt(modifier.getType() + "_color", Mermod.getItemColor(stack));
        return tag;
    }
    @Override
    public ItemStack removeModifier(ItemStack necklace) {
        CompoundTag tag = necklace.getOrCreateTagElement("necklace_modifiers");
        if(modifier.remove(tag)) {
            ItemStack stack = createModifier(tag);
            tag.remove(modifier.getType() + "_color");
            return stack;
        }
        return ItemStack.EMPTY;
    }
    @Override
    public ItemStack createModifier(CompoundTag tag) {
        ItemStack stack = super.createModifier(tag);
        stack.getOrCreateTagElement("display").putInt("color", tag.getInt(modifier.getType() + "_color"));
        return stack;
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("item.mermod.dyeable_necklace_modifier.tooltip").withStyle(ChatFormatting.GRAY));
    }
}
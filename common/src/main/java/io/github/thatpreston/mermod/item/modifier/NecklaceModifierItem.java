package io.github.thatpreston.mermod.item.modifier;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NecklaceModifierItem extends Item {
    public static final ArrayList<NecklaceModifierItem> MODIFIERS = new ArrayList<>();
    public final NecklaceModifier modifier;
    public NecklaceModifierItem(NecklaceModifier modifier) {
        super(new Item.Properties().arch$tab(CreativeModeTabs.INGREDIENTS));
        this.modifier = modifier;
        MODIFIERS.add(this);
    }
    public CompoundTag addModifier(ItemStack necklace, ItemStack stack) {
        CompoundTag tag = necklace.getOrCreateTagElement("necklace_modifiers");
        modifier.add(tag);
        return tag;
    }
    public ItemStack removeModifier(ItemStack necklace) {
        CompoundTag tag = necklace.getOrCreateTagElement("necklace_modifiers");
        return modifier.remove(tag) ? createModifier(tag) : ItemStack.EMPTY;
    }
    public boolean canAddModifier(ItemStack necklace) {
        CompoundTag tag = necklace.getOrCreateTagElement("necklace_modifiers");
        return modifier.canAdd(tag);
    }
    public ItemStack createModifier(CompoundTag tag) {
        return new ItemStack(this);
    }
    public NecklaceModifier getModifier() {
        return modifier;
    }
    @Override
    public boolean isFoil(ItemStack stack) {
        return modifier.isFoil();
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("item.mermod.necklace_modifier.tooltip").withStyle(ChatFormatting.GRAY));
    }
}
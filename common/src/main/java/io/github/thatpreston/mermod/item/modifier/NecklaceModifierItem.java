package io.github.thatpreston.mermod.item.modifier;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;
import java.util.List;

public class NecklaceModifierItem extends Item {
    private static final Component MODIFIER_TOOLTIP = Component.translatable("tooltip.mermod.modifier").withStyle(ChatFormatting.GRAY);
    private static final Component DYEABLE_MODIFIER_TOOLTIP = Component.translatable("tooltip.mermod.dyeable_modifier").withStyle(ChatFormatting.GRAY);
    public static final List<NecklaceModifierItem> MODIFIERS = new ArrayList<>();
    private final String type;
    private final NecklaceModifier modifier;
    public NecklaceModifierItem(String type, NecklaceModifier modifier) {
        super(new Item.Properties().arch$tab(CreativeModeTabs.INGREDIENTS));
        this.type = type;
        this.modifier = modifier;
        MODIFIERS.add(this);
    }
    public String getType() {
        return type;
    }
    public NecklaceModifier getModifier() {
        return modifier;
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(stack.is(ItemTags.DYEABLE) ? DYEABLE_MODIFIER_TOOLTIP : MODIFIER_TOOLTIP);
    }
}
package io.github.thatpreston.mermod.item.modifier;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NecklaceModifierItem extends Item {
    private static final Component MODIFIER_TOOLTIP = Component.translatable("tooltip.mermod.modifier").withStyle(ChatFormatting.GRAY);
    private static final Component DYEABLE_MODIFIER_TOOLTIP = Component.translatable("tooltip.mermod.dyeable_modifier").withStyle(ChatFormatting.GRAY);
    public static final List<NecklaceModifierItem> MODIFIERS = new ArrayList<>();
    private final String type;
    private final NecklaceModifier modifier;
    public NecklaceModifierItem(Item.Properties properties, String type, NecklaceModifier modifier) {
        super(properties);
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
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> consumer, TooltipFlag flag) {
        consumer.accept(stack.is(ItemTags.DYEABLE) ? DYEABLE_MODIFIER_TOOLTIP : MODIFIER_TOOLTIP);
    }
}
package io.github.thatpreston.mermod.item;

import dev.architectury.extensions.ItemExtension;
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
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SeaNecklaceItem extends Item implements ItemExtension {
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
    public static void addModifiers(ItemStack necklace, List<ItemStack> list) {
        NecklaceModifiers modifiers = necklace.getOrDefault(RegistryHandler.NECKLACE_MODIFIERS.get(), NecklaceModifiers.EMPTY).copy();
        for(ItemStack stack : list) {
            NecklaceModifierItem item = (NecklaceModifierItem)stack.getItem();
            NecklaceModifier modifier = item.getModifier();
            if(stack.is(ItemTags.DYEABLE)) {
                DyedItemColor dyedItemColor = stack.get(DataComponents.DYED_COLOR);
                modifiers.add(item.getType(), modifier.withColor(dyedItemColor != null ? dyedItemColor.rgb() : 16777215));
            } else {
                modifiers.add(item.getType(), modifier);
            }
        }
        necklace.set(RegistryHandler.NECKLACE_MODIFIERS.get(), modifiers);
    }
    public static boolean canAddModifiers(ItemStack necklace, List<ItemStack> list) {
        NecklaceModifiers modifiers = necklace.getOrDefault(RegistryHandler.NECKLACE_MODIFIERS.get(), NecklaceModifiers.EMPTY);
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
        NecklaceModifiers modifiers = necklace.get(RegistryHandler.NECKLACE_MODIFIERS.get());
        if(modifiers != null) {
            for(NecklaceModifierItem item : NecklaceModifierItem.MODIFIERS) {
                if(modifiers.canRemove(item)) {
                    NecklaceModifiers copy = modifiers.copy();
                    ItemStack stack = copy.remove(item);
                    necklace.set(RegistryHandler.NECKLACE_MODIFIERS.get(), copy);
                    if(!stack.isEmpty()) {
                        return stack;
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        NecklaceModifiers component = stack.get(RegistryHandler.NECKLACE_MODIFIERS.get());
        if(component != null) {
            Map<String, NecklaceModifier> modifiers = component.modifiers();
            if(!modifiers.isEmpty()) {
                list.add(CommonComponents.EMPTY);
                list.add(MODIFIERS_TOOLTIP);
                for(NecklaceModifier modifier : modifiers.values()) {
                    list.add(Component.literal(" ").append(Component.translatable("item.mermod." + modifier.id() + "_modifier").withStyle(Style.EMPTY.withColor(modifier.color()))));
                }
                list.add(REMOVE_MODIFIERS_TOOLTIP);
            }
        }
    }
}
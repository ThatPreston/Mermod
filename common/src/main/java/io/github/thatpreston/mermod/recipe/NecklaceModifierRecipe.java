package io.github.thatpreston.mermod.recipe;

import io.github.thatpreston.mermod.item.SeaNecklaceItem;
import io.github.thatpreston.mermod.item.modifier.NecklaceModifierItem;
import io.github.thatpreston.mermod.registry.RegistryHandler;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class NecklaceModifierRecipe extends CustomRecipe {
    public NecklaceModifierRecipe(ResourceLocation location, CraftingBookCategory category) {
        super(location, category);
    }
    @Override
    public boolean matches(CraftingContainer container, Level level) {
        ItemStack necklace = null;
        List<ItemStack> modifiers = new ArrayList<>();
        for(int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if(!stack.isEmpty()) {
                if(stack.getItem() instanceof SeaNecklaceItem) {
                    if(necklace != null) {
                        return false;
                    }
                    necklace = stack;
                } else {
                    if(!(stack.getItem() instanceof NecklaceModifierItem)) {
                        return false;
                    }
                    modifiers.add(stack);
                }
            }
        }
        return necklace != null && !modifiers.isEmpty() && SeaNecklaceItem.canAddModifiers(necklace, modifiers);
    }
    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess access) {
        List<ItemStack> modifiers = new ArrayList<>();
        ItemStack necklace = null;
        for(int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if(!stack.isEmpty()) {
                if(stack.getItem() instanceof SeaNecklaceItem) {
                    if(necklace != null) {
                        return ItemStack.EMPTY;
                    }
                    necklace = stack.copy();
                } else {
                    if(!(stack.getItem() instanceof NecklaceModifierItem)) {
                        return ItemStack.EMPTY;
                    }
                    modifiers.add(stack);
                }
            }
        }
        if(necklace != null && !modifiers.isEmpty()) {
            SeaNecklaceItem.addModifiers(necklace, modifiers);
            return necklace;
        }
        return ItemStack.EMPTY;
    }
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }
    @Override
    public RecipeSerializer<?> getSerializer() {
        return RegistryHandler.NECKLACE_MODIFIER_SERIALIZER.get();
    }
}
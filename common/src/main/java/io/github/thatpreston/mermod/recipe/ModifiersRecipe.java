package io.github.thatpreston.mermod.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.thatpreston.mermod.item.SeaNecklaceItem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class ModifiersRecipe extends NormalCraftingRecipe {
    public static final MapCodec<ModifiersRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec((i) -> i.group(CommonInfo.MAP_CODEC.forGetter((o) -> o.commonInfo), CraftingBookInfo.MAP_CODEC.forGetter((o) -> o.bookInfo), Ingredient.CODEC.fieldOf("target").forGetter((o) -> o.target), Ingredient.CODEC.fieldOf("modifier").forGetter((o) -> o.modifier), ItemStackTemplate.CODEC.fieldOf("result").forGetter((o) -> o.result)).apply(i, ModifiersRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ModifiersRecipe> STREAM_CODEC;
    public static final RecipeSerializer<ModifiersRecipe> SERIALIZER;
    private final Ingredient target;
    private final Ingredient modifier;
    private final ItemStackTemplate result;
    public ModifiersRecipe(Recipe.CommonInfo commonInfo, CraftingRecipe.CraftingBookInfo bookInfo, Ingredient target, Ingredient modifier, ItemStackTemplate result) {
        super(commonInfo, bookInfo);
        this.target = target;
        this.modifier = modifier;
        this.result = result;
    }
    public boolean matches(final CraftingInput input, final Level level) {
        if(input.ingredientCount() < 2) {
            return false;
        } else {
            List<ItemStack> modifiers = new ArrayList<>();
            ItemStack targetStack = ItemStack.EMPTY;
            for(int slot = 0; slot < input.size(); slot++) {
                ItemStack stack = input.getItem(slot);
                if(!stack.isEmpty()) {
                    if(this.target.test(stack)) {
                        if(!targetStack.isEmpty()) {
                            return false;
                        }
                        targetStack = stack;
                    } else {
                        if(!this.modifier.test(stack)) {
                            return false;
                        }
                        modifiers.add(stack);
                    }
                }
            }
            return !modifiers.isEmpty() && !targetStack.isEmpty() && SeaNecklaceItem.canAddModifiers(targetStack, modifiers);
        }
    }
    public ItemStack assemble(final CraftingInput input) {
        List<ItemStack> modifiers = new ArrayList<>();
        ItemStack targetStack = ItemStack.EMPTY;
        for(int slot = 0; slot < input.size(); slot++) {
            ItemStack stack = input.getItem(slot);
            if(!stack.isEmpty()) {
                if(this.target.test(stack)) {
                    if(!targetStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    targetStack = stack;
                } else {
                    if(!this.modifier.test(stack)) {
                        return ItemStack.EMPTY;
                    }
                    modifiers.add(stack);
                }
            }
        }
        if(!targetStack.isEmpty() && !modifiers.isEmpty()) {
            ItemStack result = TransmuteRecipe.createWithOriginalComponents(this.result, targetStack);
            SeaNecklaceItem.addModifiers(result, modifiers);
            return result;
        } else {
            return ItemStack.EMPTY;
        }
    }
    @Override
    public RecipeSerializer<ModifiersRecipe> getSerializer() {
        return SERIALIZER;
    }
    @Override
    protected PlacementInfo createPlacementInfo() {
        return PlacementInfo.create(List.of(this.target, this.modifier));
    }
    static {
        STREAM_CODEC = StreamCodec.composite(CommonInfo.STREAM_CODEC, (o) -> o.commonInfo, CraftingBookInfo.STREAM_CODEC, (o) -> o.bookInfo, Ingredient.CONTENTS_STREAM_CODEC, (o) -> o.target, Ingredient.CONTENTS_STREAM_CODEC, (o) -> o.modifier, ItemStackTemplate.STREAM_CODEC, (o) -> o.result, ModifiersRecipe::new);
        SERIALIZER = new RecipeSerializer(MAP_CODEC, STREAM_CODEC);
    }
}
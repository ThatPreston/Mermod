package io.github.thatpreston.mermod.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.item.SeaNecklaceItem;
import io.github.thatpreston.mermod.item.modifier.DyeableNecklaceModifierItem;
import io.github.thatpreston.mermod.item.modifier.NecklaceModifier;
import io.github.thatpreston.mermod.item.modifier.NecklaceModifierItem;
import io.github.thatpreston.mermod.item.modifier.TailModifier;
import io.github.thatpreston.mermod.recipe.NecklaceModifierRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

import java.util.function.Supplier;

public class RegistryHandler {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Mermod.MOD_ID, Registries.ITEM);
    public static final RegistrySupplier<Item> SEA_NECKLACE = ITEMS.register("sea_necklace", SeaNecklaceItem::new);
    public static final RegistrySupplier<Item> SEA_CRYSTAL = ITEMS.register("sea_crystal", () -> new Item(new Item.Properties().arch$tab(CreativeModeTabs.INGREDIENTS)));
    public static final RegistrySupplier<Item> MERMAID_BRA_MODIFIER = registerModifier(new NecklaceModifier("mermaid_bra", "bra", 0, false));
    public static final RegistrySupplier<Item> GLOWING_PEARL_MODIFIER = registerModifier(new NecklaceModifier("glowing_pearl", "glint", 16777060, true));
    public static final RegistrySupplier<Item> TAIL_GRADIENT_MODIFIER = registerModifier(new NecklaceModifier("tail_gradient", "gradient", 0, false));
    public static final RegistrySupplier<Item> TAIL_MOISTURIZER_MODIFIER = registerModifier(new NecklaceModifier("tail_moisturizer", "permanent", 15723519, true));
    public static final RegistrySupplier<Item> MOON_ROCK_MODIFIER = registerModifier(new TailModifier("moon_rock", 16755968, "h2o", 1));
    public static final RegistrySupplier<Item> URSULA_SHELL_MODIFIER = registerModifier(new TailModifier("ursula_shell", 16768000, "ariel", 0));
    public static final RegistrySupplier<Item> DEEP_SEA_CRYSTAL_MODIFIER = registerModifier(new TailModifier("deep_sea_crystal", 4608611, "siren", 2));
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(Mermod.MOD_ID, Registries.RECIPE_SERIALIZER);
    public static final RegistrySupplier<RecipeSerializer<NecklaceModifierRecipe>> NECKLACE_MODIFIER_SERIALIZER = RECIPES.register("crafting_special_necklace_modifier", () -> new SimpleCraftingRecipeSerializer<>(NecklaceModifierRecipe::new));
    public static void register() {
        ITEMS.register();
        RECIPES.register();
    }
    private static RegistrySupplier<Item> registerModifier(NecklaceModifier modifier) {
        Supplier<Item> supplier = modifier.getTooltipColor() == 0 ? () -> new DyeableNecklaceModifierItem(modifier) : () -> new NecklaceModifierItem(modifier);
        return ITEMS.register(modifier.getId() + "_modifier", supplier);
    }
}
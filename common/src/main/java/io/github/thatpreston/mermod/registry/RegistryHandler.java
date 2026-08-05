package io.github.thatpreston.mermod.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.item.modifier.NecklaceModifiers;
import io.github.thatpreston.mermod.item.SeaNecklaceItem;
import io.github.thatpreston.mermod.item.modifier.NecklaceModifier;
import io.github.thatpreston.mermod.item.modifier.NecklaceModifierItem;
import io.github.thatpreston.mermod.recipe.ModifiersRecipe;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class RegistryHandler {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Mermod.MOD_ID, Registries.ITEM);
    public static final RegistrySupplier<Item> SEA_NECKLACE = registerItem("sea_necklace", key -> new SeaNecklaceItem(new Item.Properties().setId(key).arch$tab(CreativeModeTabs.TOOLS_AND_UTILITIES).stacksTo(1).equippable(EquipmentSlot.CHEST)));
    public static final RegistrySupplier<Item> SEA_CRYSTAL = registerItem("sea_crystal", key -> new Item(new Item.Properties().setId(key).arch$tab(CreativeModeTabs.INGREDIENTS)));
    public static final RegistrySupplier<Item> MERMAID_BRA_MODIFIER = registerModifierItem("bra", new NecklaceModifier("mermaid_bra", 0));
    public static final RegistrySupplier<Item> GLOWING_PEARL_MODIFIER = registerModifierItem("glint", new NecklaceModifier("glowing_pearl", 16777060));
    public static final RegistrySupplier<Item> TAIL_GRADIENT_MODIFIER = registerModifierItem("gradient", new NecklaceModifier("tail_gradient", 0));
    public static final RegistrySupplier<Item> TAIL_MOISTURIZER_MODIFIER = registerModifierItem("permanent", new NecklaceModifier("tail_moisturizer", 15723519));
    public static final RegistrySupplier<Item> MOON_ROCK_MODIFIER = registerModifierItem("tail", new NecklaceModifier("moon_rock", 16755968, "h2o", 1));
    public static final RegistrySupplier<Item> URSULA_SHELL_MODIFIER = registerModifierItem("tail", new NecklaceModifier("ursula_shell", 16768000, "ariel", 0));
    public static final RegistrySupplier<Item> DEEP_SEA_CRYSTAL_MODIFIER = registerModifierItem("tail", new NecklaceModifier("deep_sea_crystal", 4608611, "siren", 2));
    public static final TagKey<Item> SEA_NECKLACE_TAG = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Mermod.MOD_ID, "sea_necklace"));
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Mermod.MOD_ID, Registries.RECIPE_SERIALIZER);
    public static final RegistrySupplier<RecipeSerializer<ModifiersRecipe>> MODIFIERS_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_special_modifiers", () -> new CustomRecipe.Serializer<>(ModifiersRecipe::new));
    public static final DeferredRegister<DataComponentType<?>> COMPONENT_TYPES = DeferredRegister.create(Mermod.MOD_ID, Registries.DATA_COMPONENT_TYPE);
    public static final RegistrySupplier<DataComponentType<NecklaceModifiers>> NECKLACE_MODIFIERS_COMPONENT_TYPE = registerComponentType("necklace_modifiers", builder -> builder.persistent(NecklaceModifiers.CODEC).networkSynchronized(NecklaceModifiers.STREAM_CODEC));
    private static RegistrySupplier<Item> registerItem(String id, Function<ResourceKey<Item>, Item> function) {
        Identifier identifier = Identifier.fromNamespaceAndPath(Mermod.MOD_ID, id);
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, identifier);
        return ITEMS.register(identifier, () -> function.apply(key));
    }
    private static RegistrySupplier<Item> registerModifierItem(String type, NecklaceModifier modifier) {
        return registerItem(modifier.id() + "_modifier", key -> new NecklaceModifierItem(new Item.Properties().setId(key).arch$tab(CreativeModeTabs.INGREDIENTS), type, modifier));
    }
    public static <T> RegistrySupplier<DataComponentType<T>> registerComponentType(String id, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return COMPONENT_TYPES.register(id, () -> builder.apply(DataComponentType.builder()).build());
    }
    public static void register() {
        ITEMS.register();
        RECIPE_SERIALIZERS.register();
        COMPONENT_TYPES.register();
    }
}
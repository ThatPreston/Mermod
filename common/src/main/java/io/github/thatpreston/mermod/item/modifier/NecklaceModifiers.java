package io.github.thatpreston.mermod.item.modifier;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

import java.util.Map;

public record NecklaceModifiers(Map<String, NecklaceModifier> modifiers) {
    public static final NecklaceModifiers EMPTY = new NecklaceModifiers(Map.of());
    public static final Codec<NecklaceModifiers> CODEC;
    private static final StreamCodec<ByteBuf, Map<String, NecklaceModifier>> MODIFIERS_STREAM_CODEC;
    public static final StreamCodec<ByteBuf, NecklaceModifiers> STREAM_CODEC;
    public NecklaceModifiers copy() {
        return new NecklaceModifiers(new Object2ObjectOpenHashMap<>(modifiers));
    }
    public void add(String type, NecklaceModifier modifier) {
        modifiers.put(type, modifier);
    }
    public ItemStack remove(NecklaceModifierItem item) {
        NecklaceModifier modifier = modifiers.remove(item.getType());
        if(modifier != null) {
            ItemStack stack = item.getDefaultInstance();
            if(stack.is(ItemTags.DYEABLE) && modifier.color() != 16777215) {
                stack.set(DataComponents.DYED_COLOR, new DyedItemColor(modifier.color()));
            }
            return stack;
        }
        return ItemStack.EMPTY;
    }
    public boolean canRemove(NecklaceModifierItem item) {
        NecklaceModifier modifier = modifiers.get(item.getType());
        return modifier != null && modifier.is(item.getModifier());
    }
    public NecklaceModifier get(String type) {
        return modifiers.get(type);
    }
    public boolean contains(String type) {
        return modifiers.containsKey(type);
    }
    static {
        CODEC = Codec.unboundedMap(Codec.STRING, NecklaceModifier.CODEC).xmap(NecklaceModifiers::new, NecklaceModifiers::modifiers);
        MODIFIERS_STREAM_CODEC = ByteBufCodecs.map(Object2ObjectOpenHashMap::new, ByteBufCodecs.STRING_UTF8, NecklaceModifier.STREAM_CODEC);
        STREAM_CODEC = MODIFIERS_STREAM_CODEC.map(NecklaceModifiers::new, NecklaceModifiers::modifiers);
    }
}
package io.github.thatpreston.mermod.fabric.compat;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.registry.RegistryHandler;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TrinketsCompat {
    public static ItemStack getNecklace(Player player) {
        AtomicReference<ItemStack> necklace = new AtomicReference<>(ItemStack.EMPTY);
        TrinketsApi.getTrinketComponent(player).ifPresent(trinketComponent -> {
            List<Tuple<SlotReference, ItemStack>> list = trinketComponent.getEquipped(RegistryHandler.SEA_NECKLACE.get());
            if(list.size() > 0) {
                necklace.set(list.get(0).getB());
            }
        });
        return necklace.get();
    }
    public static void init() {
        TrinketsApi.registerTrinket(RegistryHandler.SEA_NECKLACE.get(), new Trinket() {
            @Override
            public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
                Mermod.addEffects(entity);
            }
        });
    }
}
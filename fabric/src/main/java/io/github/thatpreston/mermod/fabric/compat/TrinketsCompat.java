package io.github.thatpreston.mermod.fabric.compat;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.registry.RegistryHandler;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public class TrinketsCompat {
    public static ItemStack getNecklace(Player player) {
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(player);
        if(component.isPresent()) {
            List<Tuple<SlotReference, ItemStack>> list = component.get().getEquipped(RegistryHandler.SEA_NECKLACE.get());
            if(!list.isEmpty()) {
                return list.getFirst().getB();
            }
        }
        return ItemStack.EMPTY;
    }
    public static void registerTrinket() {
        TrinketsApi.registerTrinket(RegistryHandler.SEA_NECKLACE.get(), new Trinket() {
            @Override
            public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
                Mermod.addEffects(entity);
            }
        });
    }
}
package io.github.thatpreston.mermod.fabric.compat;

import eu.pb4.trinkets.api.TrinketAttachment;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.TrinketsApi;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.registry.RegistryHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TrinketsCompat {
    public static ItemStack getNecklace(Player player) {
        TrinketAttachment attachment = TrinketsApi.getAttachment(player);
        if(attachment != null) {
            List<TrinketSlotAccess> list = attachment.equipped(RegistryHandler.SEA_NECKLACE.get(), true);
            if(!list.isEmpty()) {
                return list.getFirst().get();
            }
        }
        return ItemStack.EMPTY;
    }
    public static void registerTrinket() {
        TrinketCallback.setCallback(RegistryHandler.SEA_NECKLACE.get(), new TrinketCallback() {
            @Override
            public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
                Mermod.addEffects(entity);
            }
        });
    }
}
package io.github.thatpreston.mermod.compat;

import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.registry.RegistryHandler;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.core.AccessoryRegistry;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AccessoriesCompat {
    public static ItemStack getNecklace(Player player) {
        AccessoriesCapability capability = AccessoriesCapability.get(player);
        if(capability != null) {
            SlotEntryReference reference = capability.getFirstEquipped(RegistryHandler.SEA_NECKLACE.get());
            if(reference != null) {
                return reference.stack();
            }
        }
        return ItemStack.EMPTY;
    }
    public static void register() {
        AccessoryRegistry.register(RegistryHandler.SEA_NECKLACE.get(), new Accessory() {
            @Override
            public void tick(ItemStack stack, SlotReference reference) {
                Mermod.addEffects(reference.entity());
            }
        });
    }
    public static void registerRenderer() {
        AccessoriesRendererRegistry.bindItemToRenderer(RegistryHandler.SEA_NECKLACE.get(), AccessoriesRendererRegistry.NO_RENDERER_ID);
    }
}
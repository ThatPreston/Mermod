package io.github.thatpreston.mermod.forge.compat;

import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.registry.RegistryHandler;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class CuriosCompat {
    public static ItemStack getNecklace(Player player) {
        AtomicReference<ItemStack> necklace = new AtomicReference<>(ItemStack.EMPTY);
        CuriosApi.getCuriosInventory(player).ifPresent(inventory -> {
            Optional<SlotResult> result = inventory.findFirstCurio(RegistryHandler.SEA_NECKLACE.get());
            result.ifPresent(slotResult -> necklace.set(slotResult.stack()));
        });
        return necklace.get();
    }
    public static void registerCurio() {
        CuriosApi.registerCurio(RegistryHandler.SEA_NECKLACE.get(), new ICurioItem() {
            @Override
            public void curioTick(SlotContext context, ItemStack stack) {
                Mermod.addEffects(context.entity());
            }
            @Override
            public boolean isEnderMask(SlotContext context, EnderMan entity, ItemStack stack) {
                return false;
            }
        });
    }
}
package io.github.thatpreston.mermod.neoforge.compat;

import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.registry.RegistryHandler;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

public class CuriosCompat {
    public static ItemStack getNecklace(Player player) {
        ICuriosItemHandler handler = CuriosApi.getCuriosInventoryOrNull(player);
        if(handler != null) {
            Optional<SlotResult> result = handler.findFirstCurio(RegistryHandler.SEA_NECKLACE.get());
            if(result.isPresent()) {
                return result.get().stack();
            }
        }
        return ItemStack.EMPTY;
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
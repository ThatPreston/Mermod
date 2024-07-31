package io.github.thatpreston.mermod.fabric;

import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.client.render.TailStyle;
import io.github.thatpreston.mermod.fabric.compat.origins.OriginsCompat;
import io.github.thatpreston.mermod.fabric.compat.TrinketsCompat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MermodPlatformImpl {
    public static ItemStack getNecklaceFromAccessorySlot(Player player) {
        if(MermodFabric.trinketsLoaded) {
            return TrinketsCompat.getNecklace(player);
        }
        return ItemStack.EMPTY;
    }
    public static TailStyle getTailStyle(Player player) {
        if(Mermod.originsLoaded) {
            return OriginsCompat.getTailStyle(player);
        }
        return null;
    }
    public static boolean hasTailStyle(Player player) {
        if(Mermod.originsLoaded) {
            return OriginsCompat.hasTailPower(player);
        }
        return false;
    }
}
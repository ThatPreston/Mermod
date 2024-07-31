package io.github.thatpreston.mermod.forge;

import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.client.render.TailStyle;
import io.github.thatpreston.mermod.forge.compat.CuriosCompat;
import io.github.thatpreston.mermod.forge.compat.origins.OriginsCompat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MermodPlatformImpl {
    public static ItemStack getNecklaceFromAccessorySlot(Player player) {
        return MermodForge.curiosLoaded ? CuriosCompat.getNecklace(player) : ItemStack.EMPTY;
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
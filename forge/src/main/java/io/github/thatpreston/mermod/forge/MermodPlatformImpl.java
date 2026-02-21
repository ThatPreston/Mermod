package io.github.thatpreston.mermod.forge;

import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.client.render.TailStyle;
import io.github.thatpreston.mermod.compat.origins.OriginsCompat;
import io.github.thatpreston.mermod.forge.compat.CuriosCompat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MermodPlatformImpl {
    public static ItemStack getNecklaceFromAccessorySlot(Player player) {
        return MermodForge.curiosLoaded ? CuriosCompat.getNecklace(player) : ItemStack.EMPTY;
    }
    public static TailStyle getTailStyle(Player player) {
        return Mermod.originsLoaded ? OriginsCompat.getTailStyle(player) : null;
    }
    public static boolean hasTailStyle(Player player) {
        return Mermod.originsLoaded && OriginsCompat.hasTailPower(player);
    }
}
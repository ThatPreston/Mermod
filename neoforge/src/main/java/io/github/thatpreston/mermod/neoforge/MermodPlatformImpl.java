package io.github.thatpreston.mermod.neoforge;

import io.github.thatpreston.mermod.client.render.TailStyle;
import io.github.thatpreston.mermod.neoforge.compat.CuriosCompat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

public class MermodPlatformImpl {
    public static ItemStack getNecklaceFromAccessorySlot(Player player) {
        return MermodNeoForge.curiosLoaded ? CuriosCompat.getNecklace(player) : ItemStack.EMPTY;
    }
    public static TailStyle getTailStyle(Player player) {
        return null;
    }
    public static boolean hasTailStyle(Player player) {
        return false;
    }
    public static boolean isModLoaded(String id) {
        return ModList.get().isLoaded(id);
    }
}
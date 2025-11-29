package io.github.thatpreston.mermod.neoforge;

import io.github.thatpreston.mermod.client.render.TailStyle;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MermodPlatformImpl {
    public static ItemStack getNecklaceFromAccessorySlot(Player player) {
        return ItemStack.EMPTY;
    }
    public static TailStyle getTailStyle(Player player) {
        return null;
    }
    public static boolean hasTailStyle(Player player) {
        return false;
    }
}
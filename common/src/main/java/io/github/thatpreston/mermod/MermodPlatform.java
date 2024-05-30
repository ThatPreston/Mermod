package io.github.thatpreston.mermod;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.thatpreston.mermod.client.render.TailStyle;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MermodPlatform {
    @ExpectPlatform
    public static ItemStack getNecklaceFromAccessorySlot(Player player) {
        throw new AssertionError();
    }
    @ExpectPlatform
    public static TailStyle getTailStyle(Player player) {
        throw new AssertionError();
    }
    @ExpectPlatform
    public static boolean hasTailStyle(Player player) {
        throw new AssertionError();
    }
}
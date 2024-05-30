package io.github.thatpreston.mermod.fabric.mixin;

import io.github.thatpreston.mermod.mixin.MermodMixinConfigPlugin;
import net.fabricmc.loader.api.FabricLoader;

public class FabricMixinConfigPlugin extends MermodMixinConfigPlugin {
    @Override
    public boolean isModLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }
}
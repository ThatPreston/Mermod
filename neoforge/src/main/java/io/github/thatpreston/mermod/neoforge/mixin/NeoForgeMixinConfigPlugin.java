package io.github.thatpreston.mermod.neoforge.mixin;

import io.github.thatpreston.mermod.mixin.MermodMixinConfigPlugin;
import net.neoforged.fml.loading.LoadingModList;

public class NeoForgeMixinConfigPlugin extends MermodMixinConfigPlugin {
    @Override
    public boolean isModLoaded(String id) {
        return LoadingModList.get().getModFileById(id) != null;
    }
}
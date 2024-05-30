package io.github.thatpreston.mermod.forge.mixin;

import io.github.thatpreston.mermod.mixin.MermodMixinConfigPlugin;
import net.minecraftforge.fml.loading.LoadingModList;

public class ForgeMixinConfigPlugin extends MermodMixinConfigPlugin {
    @Override
    public boolean isModLoaded(String id) {
        return LoadingModList.get().getModFileById(id) != null;
    }
}
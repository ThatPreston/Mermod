package io.github.thatpreston.mermod.mixin;

import io.github.thatpreston.mermod.compat.MermodFiguraAPI;
import org.figuramc.figura.avatar.Avatar;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(Avatar.class)
public abstract class AvatarMixin {
    @Shadow @Final public UUID owner;
    @Inject(method = "clean()V", at = @At("HEAD"), remap = false)
    private void onClean(CallbackInfo info) {
        MermodFiguraAPI.TAIL_VISIBLE.remove(this.owner);
    }
}
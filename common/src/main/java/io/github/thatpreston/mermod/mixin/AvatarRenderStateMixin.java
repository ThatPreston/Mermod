package io.github.thatpreston.mermod.mixin;

import io.github.thatpreston.mermod.client.render.TailStyle;
import io.github.thatpreston.mermod.client.render.AvatarRenderStateExtension;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AvatarRenderState.class)
public abstract class AvatarRenderStateMixin implements AvatarRenderStateExtension {
    private TailStyle mermod$tailStyle;
    private boolean mermod$onGround;
    @Override
    public TailStyle mermod$getTailStyle() {
        return mermod$tailStyle;
    }
    @Override
    public void mermod$setTailStyle(TailStyle style) {
        this.mermod$tailStyle = style;
    }
    @Override
    public boolean mermod$isOnGround() {
        return mermod$onGround;
    }
    @Override
    public void mermod$setOnGround(boolean onGround) {
        this.mermod$onGround = onGround;
    }
}
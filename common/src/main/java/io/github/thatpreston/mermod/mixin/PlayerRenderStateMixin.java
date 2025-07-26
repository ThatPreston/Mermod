package io.github.thatpreston.mermod.mixin;

import io.github.thatpreston.mermod.client.render.TailStyle;
import io.github.thatpreston.mermod.client.render.PlayerRenderStateExtension;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerRenderState.class)
public abstract class PlayerRenderStateMixin implements PlayerRenderStateExtension {
    private TailStyle tailStyle;
    private boolean onGround;
    @Override
    public TailStyle getTailStyle() {
        return tailStyle;
    }
    @Override
    public void setTailStyle(TailStyle style) {
        this.tailStyle = style;
    }
    @Override
    public boolean isOnGround() {
        return onGround;
    }
    @Override
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}
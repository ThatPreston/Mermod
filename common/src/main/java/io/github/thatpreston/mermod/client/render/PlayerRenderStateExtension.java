package io.github.thatpreston.mermod.client.render;

public interface PlayerRenderStateExtension {
    TailStyle getTailStyle();
    void setTailStyle(TailStyle style);
    boolean isOnGround();
    void setOnGround(boolean onGround);
}
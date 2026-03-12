package io.github.thatpreston.mermod.client.render;

public interface AvatarRenderStateExtension {
    TailStyle mermod$getTailStyle();
    void mermod$setTailStyle(TailStyle style);
    boolean mermod$isOnGround();
    void mermod$setOnGround(boolean onGround);
}
package io.github.thatpreston.mermod.client.render.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.thatpreston.mermod.client.render.TailStyle;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class TailModel extends ListModel<Player> {
    private final ModelPart main;
    private final ModelPart bra;
    private final ModelPart waist;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart tail4;
    private final ModelPart tail5;
    private final ModelPart tail6;
    private final ModelPart tail7;
    private final ModelPart fin;
    public TailModel(ModelPart root) {
        main = root.getChild("main");
        bra = main.getChild("bra");
        waist = main.getChild("waist");
        tail1 = waist.getChild("tail1");
        tail2 = tail1.getChild("tail2");
        tail3 = tail2.getChild("tail3");
        tail4 = tail3.getChild("tail4");
        tail5 = tail4.getChild("tail5");
        tail6 = tail5.getChild("tail6");
        tail7 = tail6.getChild("tail7");
        fin = tail7.getChild("fin");
    }
    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of();
    }
    public void copyFrom(HumanoidModel<?> model) {
        main.copyFrom(model.body);
    }
    public static float getAngle(float x, float speed, float scale) {
        return Mth.sin(Mth.TWO_PI * x * speed) * scale;
    }
    public static float getAngleWithOffset(float x, float offset, float speed, float scale) {
        return Mth.sin(Mth.TWO_PI * (x + offset * (1 / speed)) * speed) * scale;
    }
    @Override
    public void setupAnim(Player player, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch) {
        boolean crouching = player.isCrouching();
        boolean swimming = player.isSwimming() || (player.isInWater() && !player.onGround() && !crouching);
        if(swimming) {
            float speed = 0.035F;
            float scale = Mth.PI / 12;
            tail1.xRot = getAngle(age, speed, scale);
            tail2.xRot = getAngleWithOffset(age, -0.1F, speed, scale);
            tail3.xRot = getAngleWithOffset(age, -0.2F, speed, scale);
            tail4.xRot = getAngleWithOffset(age, -0.3F, speed, scale);
            tail5.xRot = getAngleWithOffset(age, -0.4F, speed, scale);
            tail6.xRot = getAngleWithOffset(age, -0.5F, speed, scale);
            tail7.xRot = getAngleWithOffset(age, -0.6F, speed, scale);
            fin.xRot = tail7.xRot * 2;
        } else if(player.isVisuallySwimming() || player.getVehicle() != null) {
            tail1.xRot = tail2.xRot = tail3.xRot = tail4.xRot = tail5.xRot = tail6.xRot = tail7.xRot = fin.xRot = 0;
        } else {
            float angle = crouching ? Mth.PI / 16 : Mth.PI / 14;
            tail1.xRot = tail2.xRot = tail3.xRot = tail4.xRot = tail5.xRot = tail6.xRot = tail7.xRot = fin.xRot = angle;
        }
    }
    public void render(PoseStack stack, VertexConsumer consumer, int light, int overlay, TailStyle style) {
        float tailRed = (style.tailColor() >> 16 & 255) / 255.0F;
        float tailGreen = (style.tailColor() >> 8 & 255) / 255.0F;
        float tailBlue = (style.tailColor() & 255) / 255.0F;
        float braRed = (style.braColor() >> 16 & 255) / 255.0F;
        float braGreen = (style.braColor() >> 8 & 255) / 255.0F;
        float braBlue = (style.braColor() & 255) / 255.0F;
        float gradientRed = (style.gradientColor() >> 16 & 255) / 255.0F;
        float gradientGreen = (style.gradientColor() >> 8 & 255) / 255.0F;
        float gradientBlue = (style.gradientColor() & 255) / 255.0F;
        this.main.translateAndRotate(stack);
        if(style.hasBra()) {
            this.bra.render(stack, consumer, light, overlay, braRed, braGreen, braBlue, 1);
        }
        AtomicInteger index = new AtomicInteger();
        this.waist.visit(stack, (entry, path, cuboidIndex, cuboid) -> {
            float red = tailRed;
            float green = tailGreen;
            float blue = tailBlue;
            if(style.hasGradient()) {
                if(path.endsWith("fin") || path.endsWith("sidefins")) {
                    red = gradientRed;
                    green = gradientGreen;
                    blue = gradientBlue;
                } else {
                    float alpha = index.get() / 8.0F;
                    red = Mth.lerp(alpha, red, gradientRed);
                    green = Mth.lerp(alpha, green, gradientGreen);
                    blue = Mth.lerp(alpha, blue, gradientBlue);
                    index.getAndIncrement();
                }
            }
            cuboid.compile(entry, consumer, light, overlay, red, green, blue, 1);
        });
    }
}
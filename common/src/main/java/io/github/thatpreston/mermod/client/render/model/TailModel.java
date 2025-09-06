package io.github.thatpreston.mermod.client.render.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.thatpreston.mermod.client.render.TailStyle;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

public class TailModel extends ListModel<Player> {
    private static final BiConsumer<ModelPart, Float> SET_X = (part, value) -> part.xRot = value;
    private static final BiConsumer<ModelPart, Float> SET_Z = (part, value) -> part.zRot = value;
    private static final BiConsumer<ModelPart, Float> ADD_X = (part, value) -> part.xRot += value;
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
    private final ModelPart[] tailParts;
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
        tailParts = new ModelPart[]{tail1, tail2, tail3, tail4, tail5, tail6, tail7};
    }
    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of();
    }
    public void copyFrom(HumanoidModel<?> model) {
        main.copyFrom(model.body);
    }
    public static float getWaveHeight(float pos, float speed, float scale) {
        return Mth.sin(Mth.TWO_PI * pos * speed) * scale;
    }
    public static float getWaveHeight(float pos, float offset, float speed, float scale) {
        return Mth.sin(Mth.TWO_PI * (pos * speed + offset)) * scale;
    }
    private void animateWave(float pos, float speed, float scale, BiConsumer<ModelPart, Float> consumer) {
        for(int index = 0; index < tailParts.length; index++) {
            consumer.accept(tailParts[index], getWaveHeight(pos, -0.1F * index, speed, scale));
        }
    }
    @Override
    public void setupAnim(Player player, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch) {}
    public void setupAnim(Player player, float limbSwing, float limbSwingAmount, float partialTicks, float age, float yaw, float pitch) {
        for(ModelPart part : tailParts) {
            part.resetPose();
        }
        float swimAmount = player.getSwimAmount(partialTicks);
        if(player.isPassenger()) {
            tail1.xRot = -Mth.PI / 2;
            tail2.xRot = tail3.xRot = tail4.xRot = tail5.xRot = tail6.xRot = tail7.xRot = fin.xRot = Mth.PI / 90;
            animateWave(age, 0.01F, Mth.PI / 60, ADD_X);
            fin.xRot = Mth.PI / 60;
        } else if(player.onGround()) {
            float walkScale = Mth.PI / 24 * limbSwingAmount * (1 - swimAmount);
            tail1.xRot = tail2.xRot = tail3.xRot = tail4.xRot = tail5.xRot = tail6.xRot = tail7.xRot = fin.xRot = player.isCrouching() ? Mth.PI / 16 : Mth.PI / 14;
            animateWave(limbSwing, 0.06F, walkScale, SET_Z);
        } else {
            float idlePos = age * 0.2F + limbSwing * 0.6F;
            if(!player.isFallFlying()) {
                tail1.xRot = tail2.xRot = tail3.xRot = tail4.xRot = tail5.xRot = Mth.PI / 16;
                tail6.xRot = Mth.PI / 32;
            }
            animateWave(idlePos, 0.07F, Mth.PI / 20, ADD_X);
            fin.xRot = tail7.xRot * 2;
        }
        if(swimAmount > 0) {
            if(player.isInWater()) {
                float swimPos = age * 0.2F + limbSwing * 0.8F;
                animateWave(swimPos, 0.035F, Mth.PI / 12, (part, value) -> part.xRot = Mth.lerp(swimAmount, part.xRot, value));
                fin.xRot = Mth.lerp(swimAmount, fin.xRot, tail7.xRot * 2);
            } else {
                for(ModelPart part : tailParts) {
                    part.xRot = Mth.lerp(swimAmount, part.xRot, 0);
                }
                fin.xRot = Mth.lerp(swimAmount, fin.xRot, 0);
                animateWave(limbSwing, 0.06F, Mth.PI / 32 * limbSwingAmount, SET_Z);
            }
        }
    }
    public void render(PoseStack stack, VertexConsumer consumer, int light, int overlay, TailStyle style) {
        int tailColor = style.tailColor();
        int gradientColor = style.gradientColor();
        this.main.translateAndRotate(stack);
        if(style.hasBra()) {
            this.bra.render(stack, consumer, light, overlay, style.braColor());
        }
        AtomicInteger index = new AtomicInteger();
        this.waist.visit(stack, (entry, path, cuboidIndex, cuboid) -> {
            int i = index.get();
            int color = tailColor;
            if(style.hasGradient()) {
                if(path.endsWith("fin") || path.endsWith("sidefins") || i > 7) {
                    color = gradientColor;
                } else {
                    float alpha = i / 8.0F;
                    color = FastColor.ARGB32.lerp(alpha, tailColor, gradientColor);
                    index.getAndIncrement();
                }
            }
            cuboid.compile(entry, consumer, light, overlay, color);
        });
    }
}
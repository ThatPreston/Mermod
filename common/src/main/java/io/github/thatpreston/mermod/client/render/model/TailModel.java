package io.github.thatpreston.mermod.client.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.thatpreston.mermod.client.render.AvatarRenderStateExtension;
import io.github.thatpreston.mermod.client.render.TailStyle;
import io.github.thatpreston.mermod.mixin.ModelPartAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;

import java.util.List;
import java.util.function.BiConsumer;

public class TailModel extends EntityModel<AvatarRenderState> {
    private static final BiConsumer<ModelPart, Float> SET_X = (part, value) -> part.xRot = value;
    private static final BiConsumer<ModelPart, Float> SET_Z = (part, value) -> part.zRot = value;
    private static final BiConsumer<ModelPart, Float> ADD_X = (part, value) -> part.xRot += value;
    public final ModelPart body;
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
    private final ModelPart[] parts;
    private final ModelPart[] tailParts;
    public TailModel(ModelPart root) {
        super(root);
        body = root.getChild("body");
        bra = body.getChild("bra");
        waist = body.getChild("waist");
        tail1 = waist.getChild("tail1");
        tail2 = tail1.getChild("tail2");
        tail3 = tail2.getChild("tail3");
        tail4 = tail3.getChild("tail4");
        tail5 = tail4.getChild("tail5");
        tail6 = tail5.getChild("tail6");
        tail7 = tail6.getChild("tail7");
        fin = tail7.getChild("fin");
        parts = new ModelPart[]{waist, tail1, tail2, tail3, tail4, tail5, tail6, tail7, fin};
        tailParts = new ModelPart[]{tail1, tail2, tail3, tail4, tail5, tail6, tail7};
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
    public void setupAnim(AvatarRenderState state) {
        super.setupAnim(state);
        AvatarRenderStateExtension extension = (AvatarRenderStateExtension)state;
        float swimAmount = state.swimAmount;
        if(state.isPassenger) {
            tail1.xRot = -Mth.PI / 2;
            tail2.xRot = tail3.xRot = tail4.xRot = tail5.xRot = tail6.xRot = tail7.xRot = fin.xRot = Mth.PI / 90;
            animateWave(state.ageInTicks, 0.01F, Mth.PI / 60, ADD_X);
            fin.xRot = Mth.PI / 60;
        } else if(extension.mermod$isOnGround()) {
            float walkScale = Mth.PI / 24 * state.walkAnimationSpeed * (1 - swimAmount);
            tail1.xRot = tail2.xRot = tail3.xRot = tail4.xRot = tail5.xRot = tail6.xRot = tail7.xRot = fin.xRot = state.isCrouching ? Mth.PI / 16 : Mth.PI / 14;
            animateWave(state.walkAnimationPos, 0.06F, walkScale, SET_Z);
        } else {
            float idlePos = state.ageInTicks * 0.2F + state.walkAnimationPos * 0.6F;
            if(!state.isFallFlying) {
                tail1.xRot = tail2.xRot = tail3.xRot = tail4.xRot = tail5.xRot = Mth.PI / 16;
                tail6.xRot = Mth.PI / 32;
            }
            animateWave(idlePos, 0.07F, Mth.PI / 20, ADD_X);
            fin.xRot = tail7.xRot * 2;
        }
        if(swimAmount > 0) {
            if(state.isInWater) {
                float swimPos = state.ageInTicks * 0.2F + state.walkAnimationPos * 0.8F;
                animateWave(swimPos, 0.035F, Mth.PI / 12, (part, value) -> part.xRot = Mth.lerp(swimAmount, part.xRot, value));
                fin.xRot = Mth.lerp(swimAmount, fin.xRot, tail7.xRot * 2);
            } else {
                for(ModelPart part : tailParts) {
                    part.xRot = Mth.lerp(swimAmount, part.xRot, 0);
                }
                fin.xRot = Mth.lerp(swimAmount, fin.xRot, 0);
                animateWave(state.walkAnimationPos, 0.06F, Mth.PI / 32 * state.walkAnimationSpeed, SET_Z);
            }
        }
    }
    public void submit(PoseStack stack, OrderedSubmitNodeCollector collector, int light, int overlay, TailStyle style, RenderType renderType, int outlineColor) {
        if(style.hasBra()) {
            collector.submitModelPart(this.bra, stack, renderType, light, overlay, null, false, false, style.braColor(), null, outlineColor);
        }
        this.renderParts(this.parts, stack, collector, light, overlay, renderType, style, outlineColor);
    }
    private void renderParts(ModelPart[] parts, PoseStack stack, OrderedSubmitNodeCollector collector, int light, int overlay, RenderType renderType, TailStyle style, int outlineColor) {
        for(int partIndex = 0; partIndex < parts.length; partIndex++) {
            stack.pushPose();
            int primaryColor = style.hasGradient() ? ARGB.srgbLerp((float)partIndex / (parts.length - 1), style.tailColor(), style.gradientColor()) : style.tailColor();
            int secondaryColor = style.hasGradient() ? style.gradientColor() : style.tailColor();
            this.renderPart(parts[partIndex], stack, collector, light, overlay, renderType, primaryColor, secondaryColor, outlineColor);
        }
        for(int partIndex = 0; partIndex < parts.length; partIndex++) {
            stack.popPose();
        }
    }
    private void renderPart(ModelPart part, PoseStack stack, OrderedSubmitNodeCollector collector, int light, int overlay, RenderType renderType, int primaryColor, int secondaryColor, int outlineColor) {
        part.translateAndRotate(stack);
        List<ModelPart.Cube> cubes = ((ModelPartAccessor)(Object)part).getCubes();
        for(int cubeIndex = 0; cubeIndex < cubes.size(); cubeIndex++) {
            int color = cubeIndex == 0 ? primaryColor : secondaryColor;
            ModelPart.Cube cube = cubes.get(cubeIndex);
            collector.submitCustomGeometry(stack, renderType, (pose, consumer) -> {
                cube.compile(pose, consumer, light, overlay, color);
                if(outlineColor != 0 && (renderType.outline().isPresent() || renderType.isOutline())) {
                    OutlineBufferSource outlineBufferSource = Minecraft.getInstance().renderBuffers().outlineBufferSource();
                    outlineBufferSource.setColor(outlineColor);
                    cube.compile(pose, outlineBufferSource.getBuffer(renderType), light, overlay, color);
                }
            });
        }
    }
}
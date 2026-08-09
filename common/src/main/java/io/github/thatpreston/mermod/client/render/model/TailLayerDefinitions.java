package io.github.thatpreston.mermod.client.render.model;

import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class TailLayerDefinitions {
    public static LayerDefinition getDefault() {
        return new TailBuilder().addSegments(
                new TailBuilder.TailSegment("waist", 12).addBox(-4, 0, -2, 8, 12, 4, 24, 0, CubeDeformation.NONE)
                        .addSideFins(4, 8, 5, 4, 24, 40),
                new TailBuilder.TailSegment("tail1").addBody(8, 4, 4, 0, 0)
                        .addSideFins(4, 0, 5, 4, 24, 44),
                new TailBuilder.TailSegment("tail2").addBody(7.5F, 3, 3.5F, 0, 8)
                        .addSideFins(3, 0, 6, 3, 24, 48),
                new TailBuilder.TailSegment("tail3").addBody(7, 2, 3, 0, 15)
                        .addSideFins(3, 0, 6, 2, 24, 51),
                new TailBuilder.TailSegment("tail4").addBody(6.5F, 2, 2.5F, 0, 20)
                        .addSideFins(3, 0, 6, 2, 24, 53),
                new TailBuilder.TailSegment("tail5").addBody(6, 2, 2, 0, 25)
                        .addSideFins(3, 0, 6, 2, 24, 55),
                new TailBuilder.TailSegment("tail6").addBody(5.5F, 2, 1.5F, 0, 29)
                        .addSideFins(2, 0, 7, 2, 24, 57),
                new TailBuilder.TailSegment("tail7").addBody(5, 2, 1, 0, 33)
                        .addSideFins(2, 0, 7, 5, 24, 59),
                new TailBuilder.TailSegment("fin").addQuad(-11.5F, 0, 0, 23, 24, 0, 40, false)
        ).build(48, 64);
    }
    public static LayerDefinition getDefaultDorsalFins() {
        return new TailBuilder().addSegments(
                new TailBuilder.TailSegment("waist", 12).addBox(-4, 0, -2, 8, 12, 4, 24, 0, CubeDeformation.NONE)
                        .addSideFins(4, 8, 5, 4, 24, 40)
                        .addBox(-0.5F, 10, 2, 1, 2, 1, 32, 40, new CubeDeformation(-0.1F, 0, 0)),
                new TailBuilder.TailSegment("tail1").addBody(8, 4, 4, 0, 0)
                        .addSideFins(4, 0, 5, 4, 24, 44)
                        .addBox(-0.5F, 0, 2, 1, 4, 2, 32, 43, CubeDeformation.NONE),
                new TailBuilder.TailSegment("tail2").addBody(7.5F, 3, 3.5F, 0, 8)
                        .addSideFins(3, 0, 6, 3, 24, 48)
                        .addBox(-0.5F, 0, 1.5F, 1, 3, 2, 32, 49, new CubeDeformation(-0.1F, 0, -0.25F)),
                new TailBuilder.TailSegment("tail3").addBody(7, 2, 3, 0, 15)
                        .addSideFins(3, 0, 6, 2, 24, 51)
                        .addBox(-0.5F, 0, 1.5F, 1, 2, 1, 32, 54, new CubeDeformation(-0.2F, 0, 0)),
                new TailBuilder.TailSegment("tail4").addBody(6.5F, 2, 2.5F, 0, 20)
                        .addSideFins(3, 0, 6, 2, 24, 53)
                        .addBox(-0.5F, 0, 1, 1, 2, 2, 38, 40, new CubeDeformation(-0.1F, 0, -0.25F)),
                new TailBuilder.TailSegment("tail5").addBody(6, 2, 2, 0, 25)
                        .addSideFins(3, 0, 6, 2, 24, 55)
                        .addBox(-0.5F, 0, 0.75F, 1, 2, 2, 38, 44, new CubeDeformation(0, 0, -0.25F)),
                new TailBuilder.TailSegment("tail6").addBody(5.5F, 2, 1.5F, 0, 29)
                        .addSideFins(2, 0, 7, 2, 24, 57)
                        .addBox(-0.5F, 0, 0.75F, 1, 2, 1, 38, 48, new CubeDeformation(-0.1F, 0, 0)),
                new TailBuilder.TailSegment("tail7").addBody(5, 2, 1, 0, 33)
                        .addSideFins(2, 0, 7, 5, 24, 59),
                new TailBuilder.TailSegment("fin").addQuad(-11.5F, 0, 0, 23, 24, 0, 40, false)
        ).build(48, 64);
    }
    public static LayerDefinition getSiren() {
        return new TailBuilder().addSegments(
                new TailBuilder.TailSegment("waist", 12).addBox(-4, 0, -2, 8, 12, 4, 24, 0, CubeDeformation.NONE)
                        .addSideFins(4, 8, 5, 4, 24, 40),
                new TailBuilder.TailSegment("tail1").addBody(8, 3, 4, 0, 0)
                        .addSideFins(4, 0, 5, 3, 24, 44),
                new TailBuilder.TailSegment("tail2").addBody(7, 3, 3.5F, 0, 7)
                        .addSideFins(3, 0, 6, 3, 24, 47),
                new TailBuilder.TailSegment("tail3").addBody(6, 3, 3, 0, 14)
                        .addSideFins(3, 0, 6, 3, 24, 50),
                new TailBuilder.TailSegment("tail4").addBody(5, 3, 2.5F, 0, 20)
                        .addSideFins(2, 0, 7, 3, 24, 53),
                new TailBuilder.TailSegment("tail5").addBody(4, 3, 2, 0, 26)
                        .addSideFins(2, 0, 7, 3, 24, 56),
                new TailBuilder.TailSegment("tail6").addBody(3.5F, 3, 1.5F, 0, 31)
                        .addSideFins(1.5F, 0, 8, 3, 32, 40),
                new TailBuilder.TailSegment("tail7").addBody(3, 3, 1, 0, 36)
                        .addSideFins(1.5F, 0, 8, 6, 32, 43),
                new TailBuilder.TailSegment("fin").addQuad(-11.5F, 0, 0, 23, 24, 0, 40, false)
        ).build(48, 64);
    }
}
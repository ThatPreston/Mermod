package io.github.thatpreston.mermod.client.render.model;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.core.Direction;

import java.util.EnumSet;
import java.util.Set;

public class TailLayerDefinitions {
    private static final Set<Direction> FRONT_ONLY = EnumSet.of(Direction.NORTH);
    private static final Set<Direction> BACK_ONLY = EnumSet.of(Direction.SOUTH);
    private static final CubeDeformation TAIL_DEFORMATION = new CubeDeformation(-0.25F, 0, -0.25F);
    private static final CubeDeformation TAIL_DEFORMATION_Z = new CubeDeformation(0, 0, -0.25F);
    private static final CubeDeformation DORSAL_FIN_DEFORMATION = new CubeDeformation(-0.1F, 0, 0);
    public static LayerDefinition getDefault(boolean addDorsalFins) {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition main = meshDefinition.getRoot().addOrReplaceChild("main", CubeListBuilder.create().addBox(-4, 0, -2, 8, 12, 4), PartPose.ZERO);
        main.addOrReplaceChild("bra", CubeListBuilder.create().texOffs(24, 16).addBox(-4, 0, -2, 8, 12, 4, new CubeDeformation(0.05F)), PartPose.ZERO);
        PartDefinition waist = main.addOrReplaceChild("waist", CubeListBuilder.create().texOffs(24, 0).addBox(-4, 0, -2, 8, 12, 4, new CubeDeformation(0.1F)), PartPose.ZERO);
        PartDefinition tail1 = waist.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 0).addBox(-4, 0, -2, 8, 4, 4), PartPose.offset(0, 12, 0));
        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 8).addBox(-4, 0, -2, 8, 3, 4, TAIL_DEFORMATION), PartPose.offset(0, 4, 0));
        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 15).addBox(-3.5F, 0, -1.5F, 7, 2, 3), PartPose.offset(0, 3, 0));
        PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(0, 20).addBox(-3.5F, 0, -1.5F, 7, 2, 3, TAIL_DEFORMATION), PartPose.offset(0, 2, 0));
        PartDefinition tail5 = tail4.addOrReplaceChild("tail5", CubeListBuilder.create().texOffs(0, 25).addBox(-3, 0, -1, 6, 2, 2), PartPose.offset(0, 2, 0));
        PartDefinition tail6 = tail5.addOrReplaceChild("tail6", CubeListBuilder.create().texOffs(0, 29).addBox(-3, 0, -1, 6, 2, 2, TAIL_DEFORMATION), PartPose.offset(0, 2, 0));
        PartDefinition tail7 = tail6.addOrReplaceChild("tail7", CubeListBuilder.create().texOffs(0, 33).addBox(-2.5F, 0, -0.5F, 5, 2, 1), PartPose.offset(0, 2, 0));
        tail7.addOrReplaceChild("fin", addQuad(CubeListBuilder.create(), -11.5F, 0, 0, 23, 24, 0, 40, false), PartPose.offset(0, 2, 0));
        addSideFins(waist, 4, 8, 5, 4, 24, 40);
        addSideFins(tail1, 4, 0, 5, 4, 24, 44);
        addSideFins(tail2, 3, 0, 6, 3, 24, 48);
        addSideFins(tail3, 3, 0, 6, 2, 24, 51);
        addSideFins(tail4, 3, 0, 6, 2, 24, 53);
        addSideFins(tail5, 3, 0, 6, 2, 24, 55);
        addSideFins(tail6, 2, 0, 7, 2, 24, 57);
        addSideFins(tail7, 2, 0, 7, 5, 24, 59);
        if(addDorsalFins) {
            waist.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(32, 40).addBox(-0.5F, 10, 2, 1, 2, 1, DORSAL_FIN_DEFORMATION), PartPose.ZERO);
            tail1.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(32, 43).addBox(-0.5F, 0, 2, 1, 4, 2), PartPose.ZERO);
            tail2.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(32, 49).addBox(-0.5F, 0, 1.5F, 1, 3, 2, DORSAL_FIN_DEFORMATION.extend(0, 0, -0.25F)), PartPose.ZERO);
            tail3.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(32, 54).addBox(-0.5F, 0, 1.5F, 1, 2, 1, DORSAL_FIN_DEFORMATION.extend(-0.1F, 0, 0)), PartPose.ZERO);
            tail4.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(38, 40).addBox(-0.5F, 0, 1, 1, 2, 2, DORSAL_FIN_DEFORMATION.extend(0, 0, -0.25F)), PartPose.ZERO);
            tail5.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(38, 44).addBox(-0.5F, 0, 0.75F, 1, 2, 2, new CubeDeformation(0, 0, -0.25F)), PartPose.ZERO);
            tail6.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(38, 48).addBox(-0.5F, 0, 0.75F, 1, 2, 1, DORSAL_FIN_DEFORMATION), PartPose.ZERO);
        }
        return LayerDefinition.create(meshDefinition, 48, 64);
    }
    public static LayerDefinition getSiren() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition main = meshDefinition.getRoot().addOrReplaceChild("main", CubeListBuilder.create().addBox(-4, 0, -2, 8, 12, 4), PartPose.ZERO);
        main.addOrReplaceChild("bra", CubeListBuilder.create().texOffs(24, 16).addBox(-4, 0, -2, 8, 12, 4, new CubeDeformation(0.05F)), PartPose.ZERO);
        PartDefinition waist = main.addOrReplaceChild("waist", CubeListBuilder.create().texOffs(24, 0).addBox(-4, 0, -2, 8, 12, 4, new CubeDeformation(0.1F)), PartPose.ZERO);
        PartDefinition tail1 = waist.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 0).addBox(-4, 0, -2, 8, 3, 4), PartPose.offset(0, 12, 0));
        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 7).addBox(-3.5F, 0, -2, 7, 3, 4, TAIL_DEFORMATION_Z), PartPose.offset(0, 3, 0));
        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 14).addBox(-3, 0, -1.5F, 6, 3, 3), PartPose.offset(0, 3, 0));
        PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(0, 20).addBox(-2.5F, 0, -1.5F, 5, 3, 3, TAIL_DEFORMATION_Z), PartPose.offset(0, 3, 0));
        PartDefinition tail5 = tail4.addOrReplaceChild("tail5", CubeListBuilder.create().texOffs(0, 26).addBox(-2, 0, -1, 4, 3, 2), PartPose.offset(0, 3, 0));
        PartDefinition tail6 = tail5.addOrReplaceChild("tail6", CubeListBuilder.create().texOffs(0, 31).addBox(-2, 0, -1, 4, 3, 2, TAIL_DEFORMATION), PartPose.offset(0, 3, 0));
        PartDefinition tail7 = tail6.addOrReplaceChild("tail7", CubeListBuilder.create().texOffs(0, 36).addBox(-1.5F, 0, -0.5F, 3, 3, 1), PartPose.offset(0, 3, 0));
        tail7.addOrReplaceChild("fin", addQuad(CubeListBuilder.create(), -11.5F, 0, 0, 23, 24, 0, 40, false), PartPose.offset(0, 3, 0));
        addSideFins(waist, 4, 8, 5, 4, 24, 40);
        addSideFins(tail1, 4, 0, 5, 3, 24, 44);
        addSideFins(tail2, 3, 0, 6, 3, 24, 47);
        addSideFins(tail3, 3, 0, 6, 3, 24, 50);
        addSideFins(tail4, 2, 0, 7, 3, 24, 53);
        addSideFins(tail5, 2, 0, 7, 3, 24, 56);
        addSideFins(tail6, 1.5F, 0, 8, 3, 32, 40);
        addSideFins(tail7, 1.5F, 0, 8, 6, 32, 43);
        return LayerDefinition.create(meshDefinition, 48, 64);
    }
    private static CubeListBuilder addQuad(CubeListBuilder builder, float x, float y, float z, float width, float height, int u, int v, boolean mirror) {
        builder.mirror(mirror).texOffs(u, v).addBox(x, y, z, width, height, 0, FRONT_ONLY);
        builder.mirror(!mirror).texOffs((int)(u - width), v).addBox(x, y, z, width, height, 0, BACK_ONLY);
        return builder;
    }
    private static void addSideFins(PartDefinition part, float x, float y, float width, float height, int u, int v) {
        CubeListBuilder builder = CubeListBuilder.create();
        addQuad(builder, x, y, 0, width, height, u, v, true);
        addQuad(builder, -width - x, y, 0, width, height, u, v, false);
        part.addOrReplaceChild("sidefins", builder, PartPose.ZERO);
    }
}
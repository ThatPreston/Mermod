package io.github.thatpreston.mermod.client.render.model;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

import java.util.*;

public class TailBuilder {
    private final List<TailSegment> segments = new ArrayList<>();
    private final MeshDefinition mesh;
    private final PartDefinition body;
    public TailBuilder() {
        this.mesh = new MeshDefinition();
        this.body = this.mesh.getRoot().addOrReplaceChild("body", CubeListBuilder.create().addBox(-4, 0, -2, 8, 12, 4), PartPose.ZERO);
        body.addOrReplaceChild("bra", CubeListBuilder.create().texOffs(24, 16).addBox(-4, 0, -2, 8, 12, 4), PartPose.ZERO);
    }
    public TailBuilder addSegment(TailSegment segment) {
        segments.add(segment);
        return this;
    }
    public TailBuilder addSegments(TailSegment... segments) {
        Collections.addAll(this.segments, segments);
        return this;
    }
    public LayerDefinition build(int x, int y) {
        PartDefinition lastSegment = this.body;
        float offsetY = 0;
        for(TailSegment segment : segments) {
            lastSegment = lastSegment.addOrReplaceChild(segment.name, segment.cubeListBuilder, PartPose.offset(0, offsetY, 0));
            offsetY = segment.offsetY;
        }
        return LayerDefinition.create(this.mesh, x, y);
    }
    public static class TailSegment {
        private static final Set<Direction> FRONT_ONLY = EnumSet.of(Direction.NORTH);
        private static final Set<Direction> BACK_ONLY = EnumSet.of(Direction.SOUTH);
        private final String name;
        private final CubeListBuilder cubeListBuilder;
        private float offsetY;
        public TailSegment(String name) {
            this.name = name;
            this.cubeListBuilder = CubeListBuilder.create();
        }
        public TailSegment(String name, float offsetY) {
            this(name);
            this.offsetY = offsetY;
        }
        public TailSegment addBody(float width, float height, float depth, int u, int v) {
            int actualWidth = Mth.ceil(width);
            int actualDepth = Mth.ceil(depth);
            CubeDeformation deformation = new CubeDeformation((width - actualWidth) / 2.0F, 0, (depth - actualDepth) / 2.0F);
            this.offsetY = height;
            return this.addBox(-actualWidth / 2.0F, 0, -actualDepth / 2.0F, actualWidth, height, actualDepth, u, v, deformation);
        }
        public TailSegment addBox(float x, float y, float z, float width, float height, float depth, int u, int v, CubeDeformation deformation) {
            this.cubeListBuilder.mirror(false).texOffs(u, v).addBox(x, y, z, width, height, depth, deformation);
            return this;
        }
        public TailSegment addQuad(float x, float y, float z, float width, float height, int u, int v, boolean mirror) {
            this.cubeListBuilder.mirror(mirror).texOffs(u, v).addBox(x, y, z, width, height, 0, FRONT_ONLY);
            this.cubeListBuilder.mirror(!mirror).texOffs((int)(u - width), v).addBox(x, y, z, width, height, 0, BACK_ONLY);
            return this;
        }
        public TailSegment addSideFins(float x, float y, float width, float height, int u, int v) {
            this.addQuad(x, y, 0, width, height, u, v, true);
            this.addQuad(-width - x, y, 0, width, height, u, v, false);
            return this;
        }
    }
}
// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.world.World;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenSouthronStatue extends LOTRWorldGenSouthronStructure
{
    public LOTRWorldGenSouthronStatue(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected boolean canUseRedBricks() {
        return false;
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        if (this.isUmbar()) {
            super.brick2Block = LOTRMod.brick6;
            super.brick2Meta = 6;
            super.brick2SlabBlock = LOTRMod.slabSingle13;
            super.brick2SlabMeta = 2;
            super.brick2StairBlock = LOTRMod.stairsUmbarBrick;
            super.brick2WallBlock = LOTRMod.wall5;
            super.brick2WallMeta = 0;
            super.brick2CarvedBlock = LOTRMod.brick6;
            super.brick2CarvedMeta = 8;
            super.pillar2Block = LOTRMod.pillar2;
            super.pillar2Meta = 10;
        }
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -5; i2 <= 5; ++i2) {
                for (int k2 = -5; k2 <= 5; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -5; i2 <= 5; ++i2) {
            for (int k2 = -5; k2 <= 5; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 != 5 || k3 != 5) {
                    for (int j3 = 1; j3 <= 10; ++j3) {
                        this.setAir(world, i2, j3, k2);
                    }
                }
            }
        }
        this.loadStrScan("southron_statue_base");
        this.assocStatueBlocks();
        this.generateStrScan(world, random, 0, 0, 0);
        final String statue = this.getRandomStatueStrscan(random);
        this.loadStrScan(statue);
        this.assocStatueBlocks();
        this.generateStrScan(world, random, 0, 4, 0);
        return true;
    }
    
    protected String getRandomStatueStrscan(final Random random) {
        final String[] statues = { "mumak", "bird", "snake" };
        final String str = "southron_statue_" + statues[random.nextInt(statues.length)];
        return str;
    }
    
    private void assocStatueBlocks() {
        this.associateBlockMetaAlias("STONE", super.stoneBlock, super.stoneMeta);
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", super.brickSlabBlock, super.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", super.brickSlabBlock, super.brickSlabMeta | 0x8);
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", super.brickWallBlock, super.brickWallMeta);
        this.associateBlockMetaAlias("PILLAR", super.pillarBlock, super.pillarMeta);
        this.associateBlockMetaAlias("BRICK2", super.brick2Block, super.brick2Meta);
        this.associateBlockMetaAlias("BRICK2_SLAB", super.brick2SlabBlock, super.brick2SlabMeta);
        this.associateBlockMetaAlias("BRICK2_SLAB_INV", super.brick2SlabBlock, super.brick2SlabMeta | 0x8);
        this.associateBlockAlias("BRICK2_STAIR", super.brick2StairBlock);
        this.associateBlockMetaAlias("BRICK2_WALL", super.brick2WallBlock, super.brick2WallMeta);
        this.associateBlockMetaAlias("BRICK2_CARVED", super.brick2CarvedBlock, super.brick2CarvedMeta);
        this.associateBlockMetaAlias("PILLAR2", super.pillar2Block, super.pillar2Meta);
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;

public abstract class LOTRWorldGenMoredainHut extends LOTRWorldGenStructureBase2
{
    protected Block clayBlock;
    protected int clayMeta;
    protected Block stainedClayBlock;
    protected int stainedClayMeta;
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brickSlabBlock;
    protected int brickSlabMeta;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block plankSlabBlock;
    protected int plankSlabMeta;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block thatchBlock;
    protected int thatchMeta;
    protected Block thatchSlabBlock;
    protected int thatchSlabMeta;
    
    public LOTRWorldGenMoredainHut(final boolean flag) {
        super(flag);
        this.clayBlock = Blocks.hardened_clay;
        this.clayMeta = 0;
        this.stainedClayBlock = Blocks.stained_hardened_clay;
        this.stainedClayMeta = 1;
        this.brickBlock = LOTRMod.brick3;
        this.brickMeta = 10;
        this.brickSlabBlock = LOTRMod.slabSingle7;
        this.brickSlabMeta = 0;
        this.plankBlock = Blocks.planks;
        this.plankMeta = 4;
        this.plankSlabBlock = (Block)Blocks.wooden_slab;
        this.plankSlabMeta = 4;
        this.fenceBlock = Blocks.fence;
        this.fenceMeta = 4;
        this.thatchBlock = LOTRMod.thatch;
        this.thatchMeta = 0;
        this.thatchSlabBlock = LOTRMod.slabSingleThatch;
        this.thatchSlabMeta = 0;
    }
    
    protected abstract int getOffset();
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, this.getOffset());
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int range = this.getOffset(), i2 = -range; i2 <= range; ++i2) {
                for (int k2 = -range; k2 <= range; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2);
                    final Block block = this.getBlock(world, i2, j2 - 1, k2);
                    if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.sand && block != Blocks.stone) {
                        return false;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (maxHeight - minHeight > 5) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    protected void layFoundation(final World world, final int i, final int k) {
        for (int j = 0; (j == 0 || !this.isOpaque(world, i, j, k)) && this.getY(j) >= 0; --j) {
            this.setBlockAndMetadata(world, i, j, k, this.clayBlock, this.clayMeta);
            this.setGrassToDirt(world, i, j - 1, k);
        }
    }
    
    protected void dropFence(final World world, final int i, int j, final int k) {
        while (true) {
            this.setBlockAndMetadata(world, i, j, k, this.fenceBlock, this.fenceMeta);
            if (this.isOpaque(world, i, j - 1, k)) {
                break;
            }
            --j;
        }
    }
}

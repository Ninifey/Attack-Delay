// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGulfTownWall extends LOTRWorldGenGulfStructure
{
    private boolean isTall;
    
    public LOTRWorldGenGulfTownWall(final boolean flag) {
        super(flag);
        this.isTall = false;
    }
    
    public void setTall() {
        this.isTall = true;
    }
    
    @Override
    protected boolean canUseRedBrick() {
        return false;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            final int i2 = 0;
            final int k2 = 0;
            final int j2 = this.getTopBlock(world, i2, k2) - 1;
            if (!this.isSurface(world, i2, j2, k2)) {
                return false;
            }
        }
        for (int j3 = 1; (j3 >= 0 || !this.isOpaque(world, 0, j3, 0)) && this.getY(j3) >= 0; --j3) {
            if (random.nextBoolean()) {
                this.setBlockAndMetadata(world, 0, j3, 0, Blocks.sandstone, 0);
            }
            else {
                this.setBlockAndMetadata(world, 0, j3, 0, super.brickBlock, super.brickMeta);
            }
            this.setGrassToDirt(world, 0, j3 - 1, 0);
        }
        for (int j3 = 2; j3 <= 4; ++j3) {
            if (random.nextBoolean()) {
                this.setBlockAndMetadata(world, 0, j3, 0, Blocks.sandstone, 0);
            }
            else {
                this.setBlockAndMetadata(world, 0, j3, 0, super.brickBlock, super.brickMeta);
            }
        }
        if (this.isTall) {
            for (int j3 = 5; j3 <= 6; ++j3) {
                this.setBlockAndMetadata(world, 0, j3, 0, super.boneWallBlock, super.boneWallMeta);
            }
            this.setBlockAndMetadata(world, 0, 7, 0, super.boneBlock, super.boneMeta);
            this.placeWallBanner(world, 0, 7, 0, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        }
        else {
            this.setBlockAndMetadata(world, 0, 5, 0, super.fenceBlock, super.fenceMeta);
        }
        return true;
    }
}

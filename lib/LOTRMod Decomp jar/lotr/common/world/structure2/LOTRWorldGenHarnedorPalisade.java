// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenHarnedorPalisade extends LOTRWorldGenHarnedorStructure
{
    private boolean isTall;
    
    public LOTRWorldGenHarnedorPalisade(final boolean flag) {
        super(flag);
        this.isTall = false;
    }
    
    public void setTall() {
        this.isTall = true;
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        final int randomWood = random.nextInt(3);
        if (randomWood == 0) {
            super.woodBlock = LOTRMod.wood4;
            super.woodMeta = 2;
        }
        else if (randomWood == 1) {
            super.woodBlock = Blocks.log;
            super.woodMeta = 0;
        }
        else if (randomWood == 2) {
            super.woodBlock = LOTRMod.wood6;
            super.woodMeta = 3;
        }
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
        int height = 3 + random.nextInt(2);
        if (this.isTall) {
            height += 4;
        }
        if (this.isRuined()) {
            height = Math.max(1, height - 2);
        }
        for (int j3 = height; (j3 >= 0 || !this.isOpaque(world, 0, j3, 0)) && this.getY(j3) >= 0; --j3) {
            this.setBlockAndMetadata(world, 0, j3, 0, super.woodBlock, super.woodMeta);
            this.setGrassToDirt(world, 0, j3 - 1, 0);
        }
        if (this.isTall || random.nextInt(5) == 0) {
            this.setBlockAndMetadata(world, 0, height + 1, 0, super.fenceBlock, super.fenceMeta);
            this.placeSkull(world, random, 0, height + 2, 0);
        }
        if (!this.isRuined() && this.isTall) {
            this.placeWallBanner(world, 0, height, 0, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
        }
        return true;
    }
}

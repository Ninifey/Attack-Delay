// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.item.LOTRItemBanner;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGulfTotem extends LOTRWorldGenGulfStructure
{
    public LOTRWorldGenGulfTotem(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 2);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -3; i2 <= 3; ++i2) {
                for (int k2 = -3; k2 <= 3; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        this.loadStrScan("gulf_totem");
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockMetaAlias("FLAG", super.flagBlock, super.flagMeta);
        this.associateBlockMetaAlias("BONE", super.boneBlock, super.boneMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeWallBanner(world, 0, 6, -5, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, 0, 6, 5, LOTRItemBanner.BannerType.HARAD_GULF, 0);
        this.placeWallBanner(world, -6, 8, 0, LOTRItemBanner.BannerType.HARAD_GULF, 3);
        this.placeWallBanner(world, 6, 8, 0, LOTRItemBanner.BannerType.HARAD_GULF, 1);
        return true;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.LOTRMod;

public class LOTRWorldGenBurntHouse extends LOTRWorldGenRuinedHouse
{
    public LOTRWorldGenBurntHouse(final boolean flag) {
        super(flag);
        super.woodBlock = LOTRMod.wood;
        super.woodMeta = 3;
        super.plankBlock = LOTRMod.planks;
        super.plankMeta = 3;
        super.fenceBlock = LOTRMod.fence;
        super.fenceMeta = 3;
        super.stairBlock = LOTRMod.stairsCharred;
        super.stoneBlock = LOTRMod.scorchedStone;
        super.stoneMeta = 0;
        super.stoneVariantBlock = LOTRMod.scorchedStone;
        super.stoneVariantMeta = 0;
    }
}

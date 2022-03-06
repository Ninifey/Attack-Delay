// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.LOTRMod;

public class LOTRWorldGenRottenHouse extends LOTRWorldGenRuinedHouse
{
    public LOTRWorldGenRottenHouse(final boolean flag) {
        super(flag);
        super.woodBlock = LOTRMod.rottenLog;
        super.woodMeta = 0;
        super.plankBlock = LOTRMod.planksRotten;
        super.plankMeta = 0;
        super.fenceBlock = LOTRMod.fenceRotten;
        super.fenceMeta = 0;
        super.stairBlock = LOTRMod.stairsRotten;
    }
}

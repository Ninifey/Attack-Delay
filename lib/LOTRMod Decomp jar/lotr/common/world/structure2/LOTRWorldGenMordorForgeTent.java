// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenMordorForgeTent extends LOTRWorldGenMordorTent
{
    public LOTRWorldGenMordorForgeTent(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.tentBlock = LOTRMod.brick;
        super.tentMeta = 0;
        super.fenceBlock = LOTRMod.wall;
        super.fenceMeta = 1;
        super.hasOrcForge = true;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenUrukForgeTent extends LOTRWorldGenUrukTent
{
    public LOTRWorldGenUrukForgeTent(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.tentBlock = LOTRMod.brick2;
        super.tentMeta = 7;
        super.fenceBlock = LOTRMod.wall2;
        super.fenceMeta = 7;
        super.hasOrcForge = true;
    }
}

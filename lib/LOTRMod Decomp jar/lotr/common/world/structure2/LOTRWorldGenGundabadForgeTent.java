// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.init.Blocks;
import java.util.Random;

public class LOTRWorldGenGundabadForgeTent extends LOTRWorldGenGundabadTent
{
    public LOTRWorldGenGundabadForgeTent(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.tentBlock = Blocks.cobblestone;
        super.tentMeta = 0;
        super.fenceBlock = Blocks.cobblestone_wall;
        super.fenceMeta = 0;
        super.hasOrcForge = true;
    }
}

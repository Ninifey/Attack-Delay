// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenHarnedorPalisadeRuined extends LOTRWorldGenHarnedorPalisade
{
    public LOTRWorldGenHarnedorPalisadeRuined(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected boolean isRuined() {
        return true;
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        if (random.nextBoolean()) {
            super.woodBlock = LOTRMod.wood;
            super.woodMeta = 3;
        }
    }
}

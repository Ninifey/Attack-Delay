// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import java.util.Random;

public class LOTRWorldGenUmbarStatue extends LOTRWorldGenSouthronStatue
{
    public LOTRWorldGenUmbarStatue(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected boolean isUmbar() {
        return true;
    }
    
    @Override
    protected String getRandomStatueStrscan(final Random random) {
        final String[] statues = { "pillar", "snake", "pharazon" };
        final String str = "umbar_statue_" + statues[random.nextInt(statues.length)];
        return str;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.LOTREntityNPCRespawner;
import java.util.Random;
import net.minecraft.world.World;

public abstract class LOTRWorldGenNPCRespawner extends LOTRWorldGenStructureBase2
{
    public LOTRWorldGenNPCRespawner(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        final LOTREntityNPCRespawner spawner = new LOTREntityNPCRespawner(world);
        this.setupRespawner(spawner);
        this.placeNPCRespawner(spawner, world, 0, 1, 0);
        return true;
    }
    
    public abstract void setupRespawner(final LOTREntityNPCRespawner p0);
}

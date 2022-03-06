// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityUmbarWarrior;
import lotr.common.entity.npc.LOTREntityUmbarArcher;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenUmbarTower extends LOTRWorldGenSouthronTower
{
    public LOTRWorldGenUmbarTower(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected boolean isUmbar() {
        return true;
    }
    
    @Override
    protected LOTREntityNearHaradrimBase createWarrior(final World world, final Random random) {
        return (random.nextInt(3) == 0) ? new LOTREntityUmbarArcher(world) : new LOTREntityUmbarWarrior(world);
    }
    
    @Override
    protected void setSpawnClasses(final LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClasses(LOTREntityUmbarWarrior.class, LOTREntityUmbarArcher.class);
    }
}

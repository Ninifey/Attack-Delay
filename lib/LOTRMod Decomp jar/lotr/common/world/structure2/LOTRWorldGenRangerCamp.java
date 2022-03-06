// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityRangerNorth;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityRangerNorthCaptain;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenRangerCamp extends LOTRWorldGenCampBase
{
    public LOTRWorldGenRangerCamp(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.tableBlock = LOTRMod.commandTable;
    }
    
    @Override
    protected LOTRWorldGenStructureBase2 createTent(final boolean flag, final Random random) {
        return new LOTRWorldGenRangerTent(false);
    }
    
    @Override
    protected LOTREntityNPC getCampCaptain(final World world, final Random random) {
        return new LOTREntityRangerNorthCaptain(world);
    }
    
    @Override
    protected void placeNPCRespawner(final World world, final Random random, final int i, final int j, final int k) {
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClass(LOTREntityRangerNorth.class);
        respawner.setCheckRanges(24, -12, 12, 12);
        respawner.setSpawnRanges(8, -4, 4, 16);
        this.placeNPCRespawner(respawner, world, i, j, k);
    }
}

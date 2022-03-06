// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityGundabadOrcArcher;
import lotr.common.entity.npc.LOTREntityGundabadOrc;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityGundabadOrcMercenaryCaptain;
import lotr.common.entity.npc.LOTREntityGundabadOrcTrader;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenGundabadCamp extends LOTRWorldGenCampBase
{
    public LOTRWorldGenGundabadCamp(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.tableBlock = LOTRMod.commandTable;
        super.fenceBlock = LOTRMod.fence;
        super.fenceMeta = 3;
        super.fenceGateBlock = LOTRMod.fenceGateCharred;
        super.hasOrcTorches = true;
        super.hasSkulls = true;
    }
    
    @Override
    protected LOTRWorldGenStructureBase2 createTent(final boolean flag, final Random random) {
        if (random.nextInt(6) == 0) {
            return new LOTRWorldGenGundabadForgeTent(false);
        }
        return new LOTRWorldGenGundabadTent(false);
    }
    
    @Override
    protected LOTREntityNPC getCampCaptain(final World world, final Random random) {
        return random.nextBoolean() ? new LOTREntityGundabadOrcTrader(world) : new LOTREntityGundabadOrcMercenaryCaptain(world);
    }
    
    @Override
    protected void placeNPCRespawner(final World world, final Random random, final int i, final int j, final int k) {
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityGundabadOrc.class, LOTREntityGundabadOrcArcher.class);
        respawner.setCheckRanges(24, -12, 12, 12);
        respawner.setSpawnRanges(8, -4, 4, 16);
        this.placeNPCRespawner(respawner, world, i, j, k);
    }
}

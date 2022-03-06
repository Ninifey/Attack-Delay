// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityDolGuldurOrcArcher;
import lotr.common.entity.npc.LOTREntityDolGuldurOrc;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityDolGuldurOrcTrader;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenDolGuldurCamp extends LOTRWorldGenCampBase
{
    public LOTRWorldGenDolGuldurCamp(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.tableBlock = LOTRMod.commandTable;
        super.brickBlock = LOTRMod.brick2;
        super.brickMeta = 8;
        super.brickSlabBlock = LOTRMod.slabSingle4;
        super.brickSlabMeta = 5;
        super.fenceBlock = LOTRMod.fence;
        super.fenceMeta = 3;
        super.fenceGateBlock = LOTRMod.fenceGateCharred;
        super.hasOrcTorches = true;
        super.hasSkulls = true;
    }
    
    @Override
    protected LOTRWorldGenStructureBase2 createTent(final boolean flag, final Random random) {
        if (random.nextInt(6) == 0) {
            return new LOTRWorldGenDolGuldurForgeTent(false);
        }
        return new LOTRWorldGenDolGuldurTent(false);
    }
    
    @Override
    protected LOTREntityNPC getCampCaptain(final World world, final Random random) {
        if (random.nextBoolean()) {
            return new LOTREntityDolGuldurOrcTrader(world);
        }
        return null;
    }
    
    @Override
    protected void placeNPCRespawner(final World world, final Random random, final int i, final int j, final int k) {
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityDolGuldurOrc.class, LOTREntityDolGuldurOrcArcher.class);
        respawner.setCheckRanges(24, -12, 12, 12);
        respawner.setSpawnRanges(8, -4, 4, 16);
        this.placeNPCRespawner(respawner, world, i, j, k);
    }
}

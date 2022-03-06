// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityMordorOrcArcher;
import lotr.common.entity.npc.LOTREntityMordorOrc;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityMordorOrcTrader;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenMordorCamp extends LOTRWorldGenCampBase
{
    public LOTRWorldGenMordorCamp(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.tableBlock = LOTRMod.commandTable;
        super.brickBlock = LOTRMod.brick;
        super.brickMeta = 0;
        super.brickSlabBlock = LOTRMod.slabSingle;
        super.brickSlabMeta = 1;
        super.fenceBlock = LOTRMod.fence;
        super.fenceMeta = 3;
        super.fenceGateBlock = LOTRMod.fenceGateCharred;
        super.farmBaseBlock = LOTRMod.rock;
        super.farmBaseMeta = 0;
        super.farmCropBlock = LOTRMod.morgulShroom;
        super.farmCropMeta = 0;
        super.hasOrcTorches = true;
        super.hasSkulls = true;
    }
    
    @Override
    protected LOTRWorldGenStructureBase2 createTent(final boolean flag, final Random random) {
        if (random.nextInt(6) == 0) {
            return new LOTRWorldGenMordorForgeTent(false);
        }
        return new LOTRWorldGenMordorTent(false);
    }
    
    @Override
    protected LOTREntityNPC getCampCaptain(final World world, final Random random) {
        if (random.nextBoolean()) {
            return new LOTREntityMordorOrcTrader(world);
        }
        return null;
    }
    
    @Override
    protected void placeNPCRespawner(final World world, final Random random, final int i, final int j, final int k) {
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityMordorOrc.class, LOTREntityMordorOrcArcher.class);
        respawner.setCheckRanges(24, -12, 12, 12);
        respawner.setSpawnRanges(8, -4, 4, 16);
        this.placeNPCRespawner(respawner, world, i, j, k);
    }
}

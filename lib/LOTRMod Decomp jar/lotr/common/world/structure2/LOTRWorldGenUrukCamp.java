// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityUrukHaiCrossbower;
import lotr.common.entity.npc.LOTREntityUrukHai;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityUrukHaiMercenaryCaptain;
import lotr.common.entity.npc.LOTREntityUrukHaiTrader;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenUrukCamp extends LOTRWorldGenCampBase
{
    public LOTRWorldGenUrukCamp(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.tableBlock = LOTRMod.commandTable;
        super.brickBlock = LOTRMod.brick2;
        super.brickMeta = 7;
        super.brickSlabBlock = LOTRMod.slabSingle4;
        super.brickSlabMeta = 4;
        super.fenceBlock = LOTRMod.fence;
        super.fenceMeta = 3;
        super.fenceGateBlock = LOTRMod.fenceGateCharred;
        super.hasOrcTorches = true;
        super.hasSkulls = true;
    }
    
    @Override
    protected LOTRWorldGenStructureBase2 createTent(final boolean flag, final Random random) {
        if (random.nextInt(6) == 0) {
            return new LOTRWorldGenUrukForgeTent(false);
        }
        return new LOTRWorldGenUrukTent(false);
    }
    
    @Override
    protected LOTREntityNPC getCampCaptain(final World world, final Random random) {
        return random.nextBoolean() ? new LOTREntityUrukHaiTrader(world) : new LOTREntityUrukHaiMercenaryCaptain(world);
    }
    
    @Override
    protected void placeNPCRespawner(final World world, final Random random, final int i, final int j, final int k) {
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityUrukHai.class, LOTREntityUrukHaiCrossbower.class);
        respawner.setCheckRanges(24, -12, 12, 12);
        respawner.setSpawnRanges(8, -4, 4, 16);
        this.placeNPCRespawner(respawner, world, i, j, k);
    }
}

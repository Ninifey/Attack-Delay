// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityAngmarOrcArcher;
import lotr.common.entity.npc.LOTREntityAngmarOrc;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityAngmarOrcTrader;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenAngmarCamp extends LOTRWorldGenCampBase
{
    public LOTRWorldGenAngmarCamp(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.tableBlock = LOTRMod.commandTable;
        super.brickBlock = LOTRMod.brick2;
        super.brickMeta = 0;
        super.brickSlabBlock = LOTRMod.slabSingle3;
        super.brickSlabMeta = 3;
        super.fenceBlock = LOTRMod.fence;
        super.fenceMeta = 3;
        super.fenceGateBlock = LOTRMod.fenceGateCharred;
        super.hasOrcTorches = true;
        super.hasSkulls = true;
    }
    
    @Override
    protected LOTRWorldGenStructureBase2 createTent(final boolean flag, final Random random) {
        if (random.nextInt(6) == 0) {
            return new LOTRWorldGenAngmarForgeTent(false);
        }
        return new LOTRWorldGenAngmarTent(false);
    }
    
    @Override
    protected LOTREntityNPC getCampCaptain(final World world, final Random random) {
        if (random.nextBoolean()) {
            return new LOTREntityAngmarOrcTrader(world);
        }
        return null;
    }
    
    @Override
    protected void placeNPCRespawner(final World world, final Random random, final int i, final int j, final int k) {
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityAngmarOrc.class, LOTREntityAngmarOrcArcher.class);
        respawner.setCheckRanges(24, -12, 12, 12);
        respawner.setSpawnRanges(8, -4, 4, 16);
        this.placeNPCRespawner(respawner, world, i, j, k);
    }
}

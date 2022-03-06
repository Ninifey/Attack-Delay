// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityMoredainMercenary;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenMoredainMercCamp extends LOTRWorldGenCampBase
{
    public LOTRWorldGenMoredainMercCamp(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.tableBlock = LOTRMod.commandTable;
        super.brickBlock = LOTRMod.planks2;
        super.brickMeta = 2;
        super.brickSlabBlock = LOTRMod.woodSlabSingle3;
        super.brickSlabMeta = 2;
        super.fenceBlock = LOTRMod.fence2;
        super.fenceMeta = 2;
        super.fenceGateBlock = LOTRMod.fenceGateCedar;
    }
    
    @Override
    protected LOTRWorldGenStructureBase2 createTent(final boolean flag, final Random random) {
        return new LOTRWorldGenMoredainMercTent(false);
    }
    
    @Override
    protected LOTREntityNPC getCampCaptain(final World world, final Random random) {
        return null;
    }
    
    @Override
    protected void placeNPCRespawner(final World world, final Random random, final int i, final int j, final int k) {
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClass(LOTREntityMoredainMercenary.class);
        respawner.setCheckRanges(24, -12, 12, 10);
        respawner.setSpawnRanges(8, -4, 4, 16);
        this.placeNPCRespawner(respawner, world, i, j, k);
        for (int mercs = 2 + random.nextInt(5), l = 0; l < mercs; ++l) {
            final LOTREntityMoredainMercenary merc = new LOTREntityMoredainMercenary(world);
            this.spawnNPCAndSetHome(merc, world, 0, 1, 0, 16);
        }
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        if (super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            for (int dummies = 1 + random.nextInt(3), l = 0; l < dummies; ++l) {
                for (int att = 0; att < 8; ++att) {
                    final int r = MathHelper.getRandomIntegerInRange(random, 8, 15);
                    final float ang = random.nextFloat() * 3.1415927f * 2.0f;
                    final int i2 = (int)(r * MathHelper.cos(ang));
                    final int k2 = (int)(r * MathHelper.sin(ang));
                    final int j2 = this.getTopBlock(world, i2, k2);
                    final int rot = random.nextInt(4);
                    if (this.generateSubstructureWithRestrictionFlag(new LOTRWorldGenMoredainMercDummy(super.notifyChanges), world, random, i2, j2, k2, rot, true)) {
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }
}

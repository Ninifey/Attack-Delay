// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityHaradSlave;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenCorsairCampCage extends LOTRWorldGenCorsairStructure
{
    public LOTRWorldGenCorsairCampCage(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 2);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -1; i2 <= 2; ++i2) {
                for (int k2 = -1; k2 <= 2; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -1; i2 <= 2; ++i2) {
            for (int k2 = -1; k2 <= 2; ++k2) {
                for (int j2 = 1; j2 <= 4; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        this.loadStrScan("corsair_camp_cage");
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        for (int slaves = 1 + random.nextInt(3), l = 0; l < slaves; ++l) {
            final LOTREntityHaradSlave slave = new LOTREntityHaradSlave(world);
            this.spawnNPCAndSetHome(slave, world, 0, 1, 0, 4);
        }
        return true;
    }
}

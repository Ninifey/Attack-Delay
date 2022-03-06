// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import java.util.List;
import net.minecraft.world.biome.BiomeGenBase;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.MathHelper;
import net.minecraft.util.AxisAlignedBB;
import lotr.common.entity.npc.LOTREntityEnt;
import lotr.common.world.biome.LOTRBiomeGenFangorn;
import lotr.common.LOTRMod;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityCorruptMallorn extends TileEntity
{
    public void updateEntity() {
        super.updateEntity();
        final Random rand = super.worldObj.rand;
        if (!super.worldObj.isClient && LOTRMod.canSpawnMobs(super.worldObj) && rand.nextInt(40) == 0) {
            final BiomeGenBase biome = super.worldObj.getBiomeGenForCoords(super.xCoord, super.zCoord);
            if (biome instanceof LOTRBiomeGenFangorn) {
                final int checkRange = 24;
                final int spawnRange = 20;
                final List nearbyEnts = super.worldObj.getEntitiesWithinAABB((Class)LOTREntityEnt.class, AxisAlignedBB.getBoundingBox((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, (double)(super.xCoord + 1), (double)(super.yCoord + 1), (double)(super.zCoord + 1)).expand((double)checkRange, (double)checkRange, (double)checkRange));
                if (nearbyEnts.isEmpty()) {
                    final LOTREntityEnt ent = new LOTREntityEnt(super.worldObj);
                    for (int l = 0; l < 16; ++l) {
                        final int i = super.xCoord + MathHelper.getRandomIntegerInRange(rand, -spawnRange, spawnRange);
                        final int j = super.yCoord + MathHelper.getRandomIntegerInRange(rand, -spawnRange, spawnRange);
                        final int k = super.zCoord + MathHelper.getRandomIntegerInRange(rand, -spawnRange, spawnRange);
                        if (super.worldObj.getBlock(i, j - 1, k).isNormalCube() && !super.worldObj.getBlock(i, j, k).isNormalCube() && !super.worldObj.getBlock(i, j + 1, k).isNormalCube()) {
                            ent.setLocationAndAngles(i + 0.5, (double)j, k + 0.5, rand.nextFloat() * 360.0f, 0.0f);
                            ent.liftSpawnRestrictions = false;
                            if (ent.getCanSpawnHere()) {
                                ent.onSpawnWithEgg(null);
                                ent.isNPCPersistent = false;
                                super.worldObj.spawnEntityInWorld((Entity)ent);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}

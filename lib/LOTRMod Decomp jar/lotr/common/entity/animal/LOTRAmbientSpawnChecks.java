// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.Entity;
import java.util.List;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import java.util.Arrays;
import net.minecraft.util.MathHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;

public class LOTRAmbientSpawnChecks
{
    public static boolean canSpawn(final EntityLiving entity, final int xzRange, final int yRange, final int attempts, final int required, final Material... materials) {
        final World world = ((Entity)entity).worldObj;
        final Random rand = entity.getRNG();
        final int i = MathHelper.floor_double(((Entity)entity).posX);
        final int j = MathHelper.floor_double(((Entity)entity).posY);
        final int k = MathHelper.floor_double(((Entity)entity).posZ);
        final Block below = world.getBlock(i, j - 1, k);
        if (below == world.getBiomeGenForCoords(i, k).topBlock) {
            final int light = world.getBlockLightValue(i, j, k);
            if ((j >= 62 && light >= rand.nextInt(8)) || light >= 8) {
                final List<Material> validMaterials = Arrays.asList(materials);
                int counted = 0;
                for (int l = 0; l < attempts; ++l) {
                    final int i2 = i + rand.nextInt(xzRange) - rand.nextInt(xzRange);
                    final int k2 = k + rand.nextInt(xzRange) - rand.nextInt(xzRange);
                    final int j2 = j + rand.nextInt(yRange) - rand.nextInt(yRange);
                    if (world.blockExists(i2, j2, k2)) {
                        final Block block = world.getBlock(i2, j2, k2);
                        if (validMaterials.contains(block.getMaterial()) && ++counted > required) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}

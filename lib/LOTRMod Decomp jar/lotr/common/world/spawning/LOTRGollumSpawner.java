// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.spawning;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRLevelData;
import net.minecraft.world.World;

public class LOTRGollumSpawner
{
    public static void performSpawning(final World world) {
        if (LOTRLevelData.gollumSpawned()) {
            return;
        }
        if (world.getTotalWorldTime() % 20L == 0L) {
            final LOTRWaypoint home = LOTRWaypoint.HIGH_PASS;
            final int x = home.getXCoord();
            final int z = home.getZCoord();
            final int homeRange = 128;
            final int i = MathHelper.getRandomIntegerInRange(world.rand, x - homeRange, x + homeRange);
            final int j = MathHelper.getRandomIntegerInRange(world.rand, 16, 32);
            final int k = MathHelper.getRandomIntegerInRange(world.rand, z - homeRange, z + homeRange);
            final int checkRange = 16;
            if (world.checkChunksExist(i - checkRange, j - checkRange, k - checkRange, i + checkRange, j + checkRange, k + checkRange)) {
                AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1));
                aabb = aabb.expand((double)checkRange, (double)checkRange, (double)checkRange);
                if (world.getEntitiesWithinAABB((Class)EntityPlayer.class, aabb).isEmpty()) {
                    final Block block = world.getBlock(i, j, k);
                    final Block below = world.getBlock(i, j - 1, k);
                    final Block above = world.getBlock(i, j + 1, k);
                    if (below.isNormalCube() && !block.isNormalCube() && !above.isNormalCube()) {
                        final LOTREntityGollum gollum = new LOTREntityGollum(world);
                        gollum.setLocationAndAngles(i + 0.5, (double)j, k + 0.5, 0.0f, 0.0f);
                        if (gollum.getCanSpawnHere()) {
                            gollum.onSpawnWithEgg(null);
                            world.spawnEntityInWorld((Entity)gollum);
                            LOTRLevelData.setGollumSpawned(true);
                        }
                    }
                }
            }
        }
    }
}

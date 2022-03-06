// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.block.Block;
import lotr.common.entity.npc.LOTREntityHarnedorWarrior;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenNearHaradDesertCamp extends LOTRWorldGenStructureBase2
{
    public LOTRWorldGenNearHaradDesertCamp(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        int highestHeight = 0;
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int k2 = -1; k2 <= 1; ++k2) {
                final int j2 = this.getTopBlock(world, i2, k2);
                final Block block = this.getBlock(world, i2, j2 - 1, k2);
                if (block != Blocks.sand && block != Blocks.dirt && block != Blocks.grass) {
                    return false;
                }
                if (j2 > highestHeight) {
                    highestHeight = j2;
                }
            }
        }
        boolean flag = false;
        int x = -2 + random.nextInt(5);
        int z = 4 + random.nextInt(4);
        int y = this.getTopBlock(world, x, z);
        flag = (new LOTRWorldGenNearHaradTent(super.notifyChanges).generateWithSetRotation(world, random, this.getX(x, z), this.getY(y), this.getZ(x, z), this.getRotationMode()) || flag);
        x = -2 + random.nextInt(5);
        z = -4 - random.nextInt(4);
        y = this.getTopBlock(world, x, z);
        flag = (new LOTRWorldGenNearHaradTent(super.notifyChanges).generateWithSetRotation(world, random, this.getX(x, z), this.getY(y), this.getZ(x, z), (this.getRotationMode() + 2) % 4) || flag);
        if (!flag) {
            return false;
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = -1; k3 <= 1; ++k3) {
                for (int j3 = highestHeight - 1; !this.isOpaque(world, i3, j3, k3) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, Blocks.sandstone, 0);
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
            }
            this.setBlockAndMetadata(world, i3, highestHeight, -1, LOTRMod.stairsNearHaradBrick, 2);
            this.setBlockAndMetadata(world, i3, highestHeight, 1, LOTRMod.stairsNearHaradBrick, 3);
        }
        this.setBlockAndMetadata(world, -1, highestHeight, 0, LOTRMod.stairsNearHaradBrick, 1);
        this.setBlockAndMetadata(world, 1, highestHeight, 0, LOTRMod.stairsNearHaradBrick, 0);
        this.setBlockAndMetadata(world, 0, highestHeight, 0, Blocks.water, 0);
        for (int camels = 1 + random.nextInt(4), l = 0; l < camels; ++l) {
            final LOTREntityCamel camel = new LOTREntityCamel(world);
            final int camelX = random.nextBoolean() ? (-3 - random.nextInt(3)) : (3 + random.nextInt(3));
            final int camelZ = -3 + random.nextInt(7);
            final int camelY = this.getTopBlock(world, camelX, camelZ);
            if (this.getBlock(world, camelX, camelY - 1, camelZ) == Blocks.sand && this.getBlock(world, camelX, camelY, camelZ) == Blocks.air) {
                if (this.getBlock(world, camelX, camelY + 1, camelZ) == Blocks.air) {
                    this.setBlockAndMetadata(world, camelX, camelY, camelZ, LOTRMod.fence2, 2);
                    this.setBlockAndMetadata(world, camelX, camelY + 1, camelZ, LOTRMod.fence2, 2);
                    this.spawnNPCAndSetHome((EntityCreature)camel, world, camelX, camelY, camelZ, 0);
                    camel.detachHome();
                    camel.saddleMountForWorldGen();
                    if (random.nextBoolean()) {
                        camel.setChestedForWorldGen();
                    }
                    final EntityLeashKnot leash = EntityLeashKnot.func_110129_a(world, this.getX(camelX, camelZ), this.getY(camelY), this.getZ(camelX, camelZ));
                    camel.setLeashedToEntity((Entity)leash, true);
                }
            }
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClass(LOTREntityHarnedorWarrior.class);
        respawner.setCheckRanges(20, -12, 12, 4);
        respawner.setSpawnRanges(8, -4, 4, 16);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }
}

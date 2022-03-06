// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import lotr.common.world.map.LOTRFixedStructures;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.entity.passive.EntityOcelot;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.entity.npc.LOTREntityTroll;
import lotr.common.world.feature.LOTRWorldGenFangornTrees;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import java.util.Random;

public class LOTRWorldGenMarshHut extends LOTRWorldGenStructureBase
{
    private static Random generateRand;
    
    public LOTRWorldGenMarshHut() {
        super(false);
    }
    
    public boolean generate(final World world, final Random random, final int i, int j, final int k) {
        --j;
        final int radius = 8;
        final int radiusPlusOne = radius + 1;
        final int wallThresholdMin = radius * radius;
        final int wallThresholdMax = radiusPlusOne * radiusPlusOne;
        final Block plankBlock = LOTRMod.planks2;
        final int plankMeta = 9;
        final Block doorBlock = LOTRMod.doorWillow;
        for (int i2 = i - radiusPlusOne; i2 <= i + radiusPlusOne; ++i2) {
            for (int k2 = k - radiusPlusOne; k2 <= k + radiusPlusOne; ++k2) {
                final int i3 = i2 - i;
                final int k3 = k2 - k;
                final int distSq = i3 * i3 + k3 * k3;
                if (distSq < wallThresholdMax) {
                    for (int j2 = j; (j2 == j || !LOTRMod.isOpaque(world, i2, j2, k2)) && j2 >= 0; --j2) {
                        this.func_150516_a(world, i2, j2, k2, Blocks.dirt, 1);
                        this.setGrassToDirt(world, i2, j2 - 1, k2);
                    }
                    for (int j2 = j + 1; j2 <= j + 6; ++j2) {
                        if (distSq >= wallThresholdMin) {
                            this.func_150516_a(world, i2, j2, k2, plankBlock, plankMeta);
                        }
                        else {
                            this.func_150516_a(world, i2, j2, k2, Blocks.air, 0);
                        }
                    }
                }
            }
        }
        final int roofThresholdMax = (radiusPlusOne + 2) * (radiusPlusOne + 2);
        for (int i4 = i - radiusPlusOne - 2; i4 <= i + radiusPlusOne + 2; ++i4) {
            for (int k4 = k - radiusPlusOne - 2; k4 <= k + radiusPlusOne + 2; ++k4) {
                for (int j3 = j + 6; j3 <= j + 10; ++j3) {
                    final int i5 = i4 - i;
                    final int k5 = k4 - k;
                    final int j4 = j3 - (j + 4);
                    final int distSq2 = i5 * i5 + k5 * k5 + j4 * j4;
                    if (distSq2 + j4 * j4 < wallThresholdMax) {
                        final boolean grass = !LOTRMod.isOpaque(world, i4, j3 + 1, k4);
                        this.func_150516_a(world, i4, j3, k4, (Block)(grass ? Blocks.grass : Blocks.dirt), 0);
                        this.setGrassToDirt(world, i4, j3 - 1, k4);
                    }
                }
            }
        }
        this.func_150516_a(world, i - (radius - 1), j + 3, k, Blocks.torch, 1);
        this.func_150516_a(world, i + (radius - 1), j + 3, k, Blocks.torch, 2);
        this.func_150516_a(world, i, j + 3, k - (radius - 1), Blocks.torch, 3);
        this.func_150516_a(world, i, j + 3, k + (radius - 1), Blocks.torch, 4);
        this.func_150516_a(world, i, j + 1, k - radius, doorBlock, 1);
        this.func_150516_a(world, i, j + 2, k - radius, doorBlock, 8);
        new LOTRWorldGenFangornTrees(false, Blocks.log, 0, (Block)Blocks.leaves, 0).disableRestrictions().generate(world, random, i, j + 11, k);
        final LOTREntityTroll troll = new LOTREntityTroll(world);
        troll.setLocationAndAngles(i + 0.5, (double)(j + 1), k + 0.5, 0.0f, 0.0f);
        troll.isNPCPersistent = true;
        troll.onSpawnWithEgg(null);
        troll.trollImmuneToSun = true;
        troll.isPassive = true;
        troll.familyInfo.setName(new StringBuilder().append('S').append('h').append('r').append('e').append('k').toString());
        troll.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0E8);
        troll.setHealth(troll.getMaxHealth());
        world.spawnEntityInWorld((Entity)troll);
        final LOTREntityTroll troll2 = new LOTREntityTroll(world);
        troll2.setLocationAndAngles(i + 0.5, (double)(j + 1), k + 0.5, 0.0f, 0.0f);
        troll2.isNPCPersistent = true;
        troll2.onSpawnWithEgg(null);
        troll2.trollImmuneToSun = true;
        troll2.isPassive = true;
        troll2.familyInfo.setName(new StringBuilder().append('D').append('r').append('e').append('k').toString());
        troll2.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0E8);
        troll2.setHealth(troll2.getMaxHealth());
        world.spawnEntityInWorld((Entity)troll2);
        final LOTREntityHorse horse = new LOTREntityHorse(world);
        horse.setLocationAndAngles(i + 0.5, (double)(j + 1), k + 0.5, 0.0f, 0.0f);
        horse.setHorseType(1);
        horse.setMountable(false);
        horse.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0E8);
        horse.setHealth(horse.getMaxHealth());
        world.spawnEntityInWorld((Entity)horse);
        final EntityOcelot cat = new EntityOcelot(world);
        cat.setLocationAndAngles(i + 0.5, (double)(j + 1), k + 0.5, 0.0f, 0.0f);
        cat.setTamed(true);
        cat.setTameSkin(2);
        cat.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0E8);
        cat.setHealth(cat.getMaxHealth());
        world.spawnEntityInWorld((Entity)cat);
        this.func_150516_a(world, i, j + 2, k + (radius - 1), Blocks.wall_sign, 2);
        TileEntity tileentity = world.getTileEntity(i, j + 2, k + (radius - 1));
        if (tileentity instanceof TileEntitySign) {
            final TileEntitySign sign = (TileEntitySign)tileentity;
            sign.field_145915_a[0] = "Check yourself";
            sign.field_145915_a[1] = "before you";
            sign.field_145915_a[2] = troll.familyInfo.getName() + " yourself";
        }
        this.func_150516_a(world, i, j + 1, k + (radius - 1), Blocks.wall_sign, 2);
        tileentity = world.getTileEntity(i, j + 1, k + (radius - 1));
        if (tileentity instanceof TileEntitySign) {
            final TileEntitySign sign = (TileEntitySign)tileentity;
            sign.field_145915_a[0] = troll.familyInfo.getName().toUpperCase();
            sign.field_145915_a[1] = "IS";
            sign.field_145915_a[2] = troll2.familyInfo.getName().toUpperCase();
        }
        return true;
    }
    
    public static boolean generatesAt(final World world, final int i, final int k) {
        return LOTRFixedStructures.generatesAtMapImageCoords(i, k, 1406, 1122);
    }
    
    static {
        LOTRWorldGenMarshHut.generateRand = new Random();
    }
}

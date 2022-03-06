// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import java.util.Iterator;
import java.util.List;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.util.MathHelper;
import java.util.ArrayList;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenHaradRuinedFort extends LOTRWorldGenStructureBase2
{
    public LOTRWorldGenHaradRuinedFort(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -7; i2 <= 12; ++i2) {
                for (int k2 = -3; k2 <= 4; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (j2 < -4) {
                        return false;
                    }
                }
            }
        }
        if (super.usingPlayer == null) {
            super.originY -= 4 + random.nextInt(5);
        }
        for (int i2 = -7; i2 <= 12; ++i2) {
            for (int k2 = -3; k2 <= 4; ++k2) {
                for (int j2 = -2; !this.isOpaque(world, i2, j2, k2) && this.getY(j2) >= 0; --j2) {
                    if (random.nextInt(4) == 0) {
                        this.setBlockAndMetadata(world, i2, j2, k2, LOTRMod.brick3, 11);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, j2, k2, LOTRMod.brick, 15);
                    }
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
            }
        }
        this.loadStrScan("harad_ruined_fort");
        this.addBlockMetaAliasOption("BRICK", 3, LOTRMod.brick, 15);
        this.addBlockMetaAliasOption("BRICK", 1, LOTRMod.brick3, 11);
        this.addBlockMetaAliasOption("BRICK_SLAB", 3, LOTRMod.slabSingle4, 0);
        this.addBlockMetaAliasOption("BRICK_SLAB", 1, LOTRMod.slabSingle7, 1);
        this.addBlockMetaAliasOption("BRICK_SLAB_INV", 3, LOTRMod.slabSingle4, 8);
        this.addBlockMetaAliasOption("BRICK_SLAB_INV", 1, LOTRMod.slabSingle7, 9);
        this.addBlockAliasOption("BRICK_STAIR", 3, LOTRMod.stairsNearHaradBrick);
        this.addBlockAliasOption("BRICK_STAIR", 1, LOTRMod.stairsNearHaradBrickCracked);
        this.addBlockMetaAliasOption("BRICK_WALL", 3, LOTRMod.wall, 15);
        this.addBlockMetaAliasOption("BRICK_WALL", 1, LOTRMod.wall3, 3);
        this.addBlockMetaAliasOption("PILLAR", 4, LOTRMod.pillar, 5);
        this.generateStrScan(world, random, 0, 1, 0);
        final List<int[]> chestCoords = new ArrayList<int[]>();
        chestCoords.add(new int[] { 3, 1, -2 });
        chestCoords.add(new int[] { 0, 1, -2 });
        chestCoords.add(new int[] { -3, 1, -2 });
        chestCoords.add(new int[] { -6, 1, -2 });
        chestCoords.add(new int[] { 8, 1, 0 });
        chestCoords.add(new int[] { 10, 1, 1 });
        chestCoords.add(new int[] { 11, 1, 3 });
        chestCoords.add(new int[] { 8, 1, 3 });
        chestCoords.add(new int[] { 6, 1, 3 });
        chestCoords.add(new int[] { 3, 1, 3 });
        chestCoords.add(new int[] { 0, 1, 3 });
        chestCoords.add(new int[] { -3, 1, 3 });
        chestCoords.add(new int[] { -6, 1, 3 });
        chestCoords.add(new int[] { 6, 2, -2 });
        chestCoords.add(new int[] { 6, 2, 0 });
        chestCoords.add(new int[] { 6, 6, -2 });
        chestCoords.add(new int[] { -6, 6, -2 });
        chestCoords.add(new int[] { -1, 6, -1 });
        chestCoords.add(new int[] { 8, 6, 0 });
        chestCoords.add(new int[] { 10, 6, 1 });
        chestCoords.add(new int[] { 0, 6, 1 });
        chestCoords.add(new int[] { -2, 6, 1 });
        chestCoords.add(new int[] { -6, 6, 1 });
        chestCoords.add(new int[] { 8, 6, 3 });
        chestCoords.add(new int[] { 0, 6, 3 });
        chestCoords.add(new int[] { -2, 6, 3 });
        chestCoords.add(new int[] { -6, 6, 3 });
        final int chests = 2 + random.nextInt(4);
        while (chestCoords.size() > chests) {
            chestCoords.remove(random.nextInt(chestCoords.size()));
        }
        for (final int[] coords : chestCoords) {
            this.placeChest(world, random, coords[0], coords[1], coords[2], LOTRMod.chestBasket, MathHelper.getRandomIntegerInRange(random, 2, 4), LOTRChestContents.NEAR_HARAD_PYRAMID);
        }
        return true;
    }
}

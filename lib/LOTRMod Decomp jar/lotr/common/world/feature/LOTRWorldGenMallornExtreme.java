// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.Block;
import lotr.common.world.structure2.LOTRWorldGenElfHouse;
import lotr.common.world.structure.LOTRWorldGenElfLordHouse;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import lotr.common.LOTRMod;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenMallornExtreme extends WorldGenAbstractTree
{
    private static int HEIGHT_MIN;
    private static int HEIGHT_MAX;
    private static int BOUGH_ANGLE_INTERVAL_MIN;
    private static int BOUGH_ANGLE_INTERVAL_MAX;
    private static int BOUGH_LENGTH_MIN;
    private static int BOUGH_LENGTH_MAX;
    private static float BOUGH_THICKNESS_FACTOR;
    private static float BOUGH_BASE_HEIGHT_MIN;
    private static float BOUGH_BASE_HEIGHT_MAX;
    private static int BOUGH_HEIGHT_MIN;
    private static int BOUGH_HEIGHT_MAX;
    private static int BRANCH_LENGTH_MIN;
    private static int BRANCH_LENGTH_MAX;
    private static int BRANCH_HEIGHT_MIN;
    private static int BRANCH_HEIGHT_MAX;
    public static float HOUSE_HEIGHT_MIN;
    public static float HOUSE_HEIGHT_MAX;
    private static float HOUSE_CHANCE;
    private static float HOUSE_ELFLORD_CHANCE;
    private boolean notify;
    private boolean saplingGrowth;
    
    public LOTRWorldGenMallornExtreme(final boolean flag) {
        super(flag);
        this.saplingGrowth = false;
        this.notify = flag;
    }
    
    public LOTRWorldGenMallornExtreme setSaplingGrowth() {
        this.saplingGrowth = true;
        return this;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        return this.generateAndReturnHeight(world, random, i, j, k, false) > 0;
    }
    
    public int generateAndReturnHeight(final World world, final Random random, final int i, final int j, final int k, final boolean forceGeneration) {
        final int height = MathHelper.getRandomIntegerInRange(random, LOTRWorldGenMallornExtreme.HEIGHT_MIN, LOTRWorldGenMallornExtreme.HEIGHT_MAX);
        final int trunkWidth = 2;
        boolean flag = true;
        if ((j < 1 || j + height + 5 > 256) && !forceGeneration) {
            return 0;
        }
        for (int j2 = j; j2 <= j + 1 + height; ++j2) {
            int range = trunkWidth;
            if (j2 == j) {
                range = 0;
            }
            if (j2 >= j + 1 + height - 2) {
                range = trunkWidth + 1;
            }
            for (int i2 = i - range; i2 <= i + range && flag; ++i2) {
                for (int k2 = k - range; k2 <= k + range && flag; ++k2) {
                    if (j2 >= 0 && j2 < 256) {
                        final Block block = world.getBlock(i2, j2, k2);
                        if (!forceGeneration && !this.isReplaceable(world, i2, j2, k2) && block != LOTRMod.quenditeGrass) {
                            flag = false;
                        }
                    }
                    else if (!forceGeneration) {
                        flag = false;
                    }
                }
            }
        }
        if (!flag && !forceGeneration) {
            return 0;
        }
        if (!forceGeneration) {
            for (int i3 = i - trunkWidth; i3 <= i + trunkWidth; ++i3) {
                for (int k3 = k - trunkWidth; k3 <= k + trunkWidth; ++k3) {
                    final Block block2 = world.getBlock(i3, j - 1, k3);
                    boolean correctBlock = false;
                    if (this.saplingGrowth) {
                        if (block2 == LOTRMod.quenditeGrass) {
                            correctBlock = true;
                        }
                    }
                    else if (block2.canSustainPlant((IBlockAccess)world, i3, j - 1, k3, ForgeDirection.UP, (IPlantable)LOTRMod.sapling)) {
                        correctBlock = true;
                    }
                    if (!correctBlock) {
                        return 0;
                    }
                }
            }
        }
        for (int i3 = i - trunkWidth; i3 <= i + trunkWidth; ++i3) {
            for (int k3 = k - trunkWidth; k3 <= k + trunkWidth; ++k3) {
                if (!forceGeneration) {
                    world.getBlock(i3, j - 1, k3).onPlantGrow(world, i3, j - 1, k3, i3, j, k3);
                }
                for (int j3 = 0; j3 < height; ++j3) {
                    final Block block3 = world.getBlock(i3, j + j3, k3);
                    if (block3.isReplaceable((IBlockAccess)world, i3, j + j3, k3) || block3.isLeaves((IBlockAccess)world, i3, j + j3, k3)) {
                        this.func_150516_a(world, i3, j + j3, k3, LOTRMod.wood, 1);
                    }
                }
            }
        }
        int angle = 0;
        while (angle < 360) {
            angle += MathHelper.getRandomIntegerInRange(random, LOTRWorldGenMallornExtreme.BOUGH_ANGLE_INTERVAL_MIN, LOTRWorldGenMallornExtreme.BOUGH_ANGLE_INTERVAL_MAX);
            final float angleR = (float)Math.toRadians(angle);
            final float sin = MathHelper.sin(angleR);
            final float cos = MathHelper.cos(angleR);
            final int boughLength = MathHelper.getRandomIntegerInRange(random, LOTRWorldGenMallornExtreme.BOUGH_LENGTH_MIN, LOTRWorldGenMallornExtreme.BOUGH_LENGTH_MAX);
            final int boughThickness = Math.round(boughLength * LOTRWorldGenMallornExtreme.BOUGH_THICKNESS_FACTOR);
            final int boughBaseHeight = j + MathHelper.floor_double((double)(height * MathHelper.randomFloatClamp(random, LOTRWorldGenMallornExtreme.BOUGH_BASE_HEIGHT_MIN, LOTRWorldGenMallornExtreme.BOUGH_BASE_HEIGHT_MAX)));
            final int boughHeight = MathHelper.getRandomIntegerInRange(random, LOTRWorldGenMallornExtreme.BOUGH_HEIGHT_MIN, LOTRWorldGenMallornExtreme.BOUGH_HEIGHT_MAX);
            for (int l = 0; l < boughLength; ++l) {
                final int i4 = i + Math.round(sin * l);
                final int k4 = k + Math.round(cos * l);
                final int j4 = boughBaseHeight + Math.round(l / (float)boughLength * boughHeight);
                for (int range2 = boughThickness - Math.round(l / (float)boughLength * boughThickness * 0.5f), i5 = i4 - range2; i5 <= i4 + range2; ++i5) {
                    for (int j5 = j4 - range2; j5 <= j4 + range2; ++j5) {
                        for (int k5 = k4 - range2; k5 <= k4 + range2; ++k5) {
                            final Block block4 = world.getBlock(i5, j5, k5);
                            if (block4.isReplaceable((IBlockAccess)world, i5, j5, k5) || block4.isLeaves((IBlockAccess)world, i5, j5, k5)) {
                                this.func_150516_a(world, i5, j5, k5, LOTRMod.wood, 13);
                            }
                        }
                    }
                }
                if (l == boughLength - 1) {
                    for (int branches = MathHelper.getRandomIntegerInRange(random, 8, 16), b = 0; b < branches; ++b) {
                        final float branch_angle = random.nextFloat() * 2.0f * 3.1415927f;
                        final float branch_sin = MathHelper.sin(branch_angle);
                        final float branch_cos = MathHelper.cos(branch_angle);
                        final int branchLength = MathHelper.getRandomIntegerInRange(random, LOTRWorldGenMallornExtreme.BRANCH_LENGTH_MIN, LOTRWorldGenMallornExtreme.BRANCH_LENGTH_MAX);
                        final int branchHeight = MathHelper.getRandomIntegerInRange(random, LOTRWorldGenMallornExtreme.BRANCH_HEIGHT_MIN, LOTRWorldGenMallornExtreme.BRANCH_HEIGHT_MAX);
                        for (int b2 = 0; b2 < branchLength; ++b2) {
                            final int i6 = i4 + Math.round(branch_sin * b2);
                            final int k6 = k4 + Math.round(branch_cos * b2);
                            final int j6 = j4 + Math.round(b2 / (float)branchLength * branchHeight);
                            final Block block5 = world.getBlock(i6, j6, k6);
                            if (block5.isReplaceable((IBlockAccess)world, i6, j6, k6) || block5.isLeaves((IBlockAccess)world, i6, j6, k6)) {
                                this.func_150516_a(world, i6, j6, k6, LOTRMod.wood, 13);
                            }
                            if (b2 == branchLength - 1) {
                                this.spawnLeafCluster(world, random, i6, j6, k6, 3);
                            }
                        }
                    }
                }
            }
        }
        if (trunkWidth > 0) {
            for (int j7 = j + (int)(height * LOTRWorldGenMallornExtreme.BOUGH_BASE_HEIGHT_MIN); j7 > j + (int)(height * 0.67f); j7 -= 1 + random.nextInt(3)) {
                for (int branches2 = 1 + random.nextInt(5), b3 = 0; b3 < branches2; ++b3) {
                    final float branchAngle = random.nextFloat() * 3.1415927f * 2.0f;
                    int i7 = i + (int)(1.5f + MathHelper.cos(branchAngle) * 4.0f);
                    int k7 = k + (int)(1.5f + MathHelper.sin(branchAngle) * 4.0f);
                    int j8 = j7;
                    for (int length = MathHelper.getRandomIntegerInRange(random, 10, 20), m = 0; m < length; ++m) {
                        i7 = i + (int)(1.5f + MathHelper.cos(branchAngle) * m);
                        k7 = k + (int)(1.5f + MathHelper.sin(branchAngle) * m);
                        j8 = j7 - 3 + m / 2;
                        if (!this.isReplaceable(world, i7, j8, k7)) {
                            break;
                        }
                        this.func_150516_a(world, i7, j8, k7, LOTRMod.wood, 13);
                    }
                    this.spawnLeafLayer(world, random, i7, j8 + 1, k7, 2);
                    this.spawnLeafLayer(world, random, i7, j8, k7, 3);
                    this.spawnLeafLayer(world, random, i7, j8 - 1, k7, 1);
                }
            }
        }
        if (trunkWidth > 0) {
            for (int roots = MathHelper.getRandomIntegerInRange(random, 6, 10), l2 = 0; l2 < roots; ++l2) {
                int i8 = i;
                int j9 = j + 1 + random.nextInt(5);
                int k8 = k;
                int xDirection = 0;
                int zDirection = 0;
                final int rootLength = 1 + random.nextInt(4);
                if (random.nextBoolean()) {
                    if (random.nextBoolean()) {
                        i8 -= trunkWidth + 1;
                        xDirection = -1;
                    }
                    else {
                        i8 += trunkWidth + 1;
                        xDirection = 1;
                    }
                    k8 -= trunkWidth + 1;
                    k8 += random.nextInt(trunkWidth * 2 + 2);
                }
                else {
                    if (random.nextBoolean()) {
                        k8 -= trunkWidth + 1;
                        zDirection = -1;
                    }
                    else {
                        k8 += trunkWidth + 1;
                        zDirection = 1;
                    }
                    i8 -= trunkWidth + 1;
                    i8 += random.nextInt(trunkWidth * 2 + 2);
                }
                for (int l3 = 0; l3 < rootLength; ++l3) {
                    int rootBlocks = 0;
                    for (int j10 = j9; !LOTRMod.isOpaque(world, i8, j10, k8); --j10) {
                        this.func_150516_a(world, i8, j10, k8, LOTRMod.wood, 13);
                        world.getBlock(i8, j10 - 1, k8).onPlantGrow(world, i8, j10 - 1, k8, i8, j10, k8);
                        if (++rootBlocks > 5) {
                            break;
                        }
                    }
                    --j9;
                    if (random.nextBoolean()) {
                        if (xDirection == -1) {
                            --i8;
                        }
                        else if (xDirection == 1) {
                            ++i8;
                        }
                        else if (zDirection == -1) {
                            --k8;
                        }
                        else if (zDirection == 1) {
                            ++k8;
                        }
                    }
                }
            }
        }
        if (!this.saplingGrowth && !this.notify && !forceGeneration && random.nextFloat() < LOTRWorldGenMallornExtreme.HOUSE_CHANCE) {
            final int houseHeight = MathHelper.floor_double((double)(height * MathHelper.randomFloatClamp(random, LOTRWorldGenMallornExtreme.HOUSE_HEIGHT_MIN, LOTRWorldGenMallornExtreme.HOUSE_HEIGHT_MAX)));
            final boolean isElfLordTree = random.nextFloat() < LOTRWorldGenMallornExtreme.HOUSE_ELFLORD_CHANCE;
            boolean spawnedElfLord = false;
            if (isElfLordTree) {
                final LOTRWorldGenElfLordHouse house = new LOTRWorldGenElfLordHouse(true);
                house.restrictions = false;
                spawnedElfLord = house.generate(world, random, i, j + houseHeight, k);
            }
            if (!isElfLordTree || !spawnedElfLord) {
                final LOTRWorldGenElfHouse house2 = new LOTRWorldGenElfHouse(true);
                house2.restrictions = false;
                house2.generate(world, random, i, j + houseHeight, k);
            }
        }
        return height;
    }
    
    private void spawnLeafCluster(final World world, final Random random, final int i, final int j, final int k, final int leafRange) {
        final int leafRangeSq = leafRange * leafRange;
        final int leafRangeSqLess = (int)((leafRange - 0.5) * (leafRange - 0.5));
        for (int i2 = i - leafRange; i2 <= i + leafRange; ++i2) {
            for (int j2 = j - leafRange; j2 <= j + leafRange; ++j2) {
                for (int k2 = k - leafRange; k2 <= k + leafRange; ++k2) {
                    final int i3 = i2 - i;
                    final int j3 = j2 - j;
                    final int k3 = k2 - k;
                    final int dist = i3 * i3 + j3 * j3 + k3 * k3;
                    if (dist < leafRangeSqLess || (dist < leafRangeSq && random.nextInt(3) == 0)) {
                        final Block block = world.getBlock(i2, j2, k2);
                        if (block.isReplaceable((IBlockAccess)world, i2, j2, k2) || block.isLeaves((IBlockAccess)world, i2, j2, k2)) {
                            this.func_150516_a(world, i2, j2, k2, LOTRMod.leaves, 1);
                        }
                    }
                }
            }
        }
    }
    
    private void spawnLeafLayer(final World world, final Random random, final int i, final int j, final int k, final int leafRange) {
        final int leafRangeSq = leafRange * leafRange;
        for (int i2 = i - leafRange; i2 <= i + leafRange; ++i2) {
            for (int k2 = k - leafRange; k2 <= k + leafRange; ++k2) {
                final int i3 = i2 - i;
                final int k3 = k2 - k;
                final int dist = i3 * i3 + k3 * k3;
                if (dist <= leafRangeSq) {
                    final Block block = world.getBlock(i2, j, k2);
                    if (block.isReplaceable((IBlockAccess)world, i2, j, k2) || block.isLeaves((IBlockAccess)world, i2, j, k2)) {
                        this.func_150516_a(world, i2, j, k2, LOTRMod.leaves, 1);
                    }
                }
            }
        }
    }
    
    static {
        LOTRWorldGenMallornExtreme.HEIGHT_MIN = 35;
        LOTRWorldGenMallornExtreme.HEIGHT_MAX = 70;
        LOTRWorldGenMallornExtreme.BOUGH_ANGLE_INTERVAL_MIN = 10;
        LOTRWorldGenMallornExtreme.BOUGH_ANGLE_INTERVAL_MAX = 30;
        LOTRWorldGenMallornExtreme.BOUGH_LENGTH_MIN = 15;
        LOTRWorldGenMallornExtreme.BOUGH_LENGTH_MAX = 25;
        LOTRWorldGenMallornExtreme.BOUGH_THICKNESS_FACTOR = 0.03f;
        LOTRWorldGenMallornExtreme.BOUGH_BASE_HEIGHT_MIN = 0.9f;
        LOTRWorldGenMallornExtreme.BOUGH_BASE_HEIGHT_MAX = 1.0f;
        LOTRWorldGenMallornExtreme.BOUGH_HEIGHT_MIN = 7;
        LOTRWorldGenMallornExtreme.BOUGH_HEIGHT_MAX = 10;
        LOTRWorldGenMallornExtreme.BRANCH_LENGTH_MIN = 8;
        LOTRWorldGenMallornExtreme.BRANCH_LENGTH_MAX = 10;
        LOTRWorldGenMallornExtreme.BRANCH_HEIGHT_MIN = 6;
        LOTRWorldGenMallornExtreme.BRANCH_HEIGHT_MAX = 8;
        LOTRWorldGenMallornExtreme.HOUSE_HEIGHT_MIN = 0.4f;
        LOTRWorldGenMallornExtreme.HOUSE_HEIGHT_MAX = 0.7f;
        LOTRWorldGenMallornExtreme.HOUSE_CHANCE = 0.7f;
        LOTRWorldGenMallornExtreme.HOUSE_ELFLORD_CHANCE = 0.15f;
    }
}

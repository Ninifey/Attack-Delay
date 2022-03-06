// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import lotr.common.LOTRMod;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenPalm extends WorldGenAbstractTree
{
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    private boolean hasDates;
    private int minHeight;
    private int maxHeight;
    
    public LOTRWorldGenPalm(final boolean flag, final Block b, final int m, final Block b1, final int m1) {
        super(flag);
        this.hasDates = false;
        this.minHeight = 5;
        this.maxHeight = 8;
        this.woodBlock = b;
        this.woodMeta = m;
        this.leafBlock = b1;
        this.leafMeta = m1;
    }
    
    public LOTRWorldGenPalm setMinMaxHeight(final int min, final int max) {
        this.minHeight = min;
        this.maxHeight = max;
        return this;
    }
    
    public LOTRWorldGenPalm setDates() {
        this.hasDates = true;
        return this;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        if (j < 1 || j + height + 2 > 256) {
            return false;
        }
        if (!this.isReplaceable(world, i, j, k)) {
            return false;
        }
        final Block below = world.getBlock(i, j - 1, k);
        if (!below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling)) {
            return false;
        }
        for (int l = 1; l < height + 2; ++l) {
            for (int i2 = i - 1; i2 <= i + 1; ++i2) {
                for (int k2 = k - 1; k2 <= k + 1; ++k2) {
                    if (!this.isReplaceable(world, i2, j + l, k2)) {
                        return false;
                    }
                }
            }
        }
        final float trunkAngle = 6.2831855f * random.nextFloat();
        final float trunkSin = MathHelper.sin(trunkAngle);
        final float trunkCos = MathHelper.cos(trunkAngle);
        int trunkX = i;
        int trunkZ = k;
        int trunkSwitches = 0;
        final int trunkSwitchesMax = MathHelper.getRandomIntegerInRange(random, 0, 3);
        for (int m = 0; m < height; ++m) {
            this.func_150516_a(world, trunkX, j + m, trunkZ, this.woodBlock, this.woodMeta);
            if (this.hasDates && m == height - 3) {
                for (int d = 0; d < 4; ++d) {
                    final ForgeDirection dir = ForgeDirection.getOrientation(d + 2);
                    this.func_150516_a(world, trunkX + dir.getOpposite().offsetX, j + m, trunkZ + dir.getOpposite().offsetZ, LOTRMod.dateBlock, d);
                }
            }
            if (m > height / 3 && m < height - 1 && trunkSwitches < trunkSwitchesMax && random.nextBoolean()) {
                ++trunkSwitches;
                if (Math.abs(trunkCos) >= MathHelper.getRandomDoubleInRange(random, 0.25, 0.5)) {
                    trunkX += (int)Math.signum(trunkCos);
                }
                if (Math.abs(trunkSin) >= MathHelper.getRandomDoubleInRange(random, 0.25, 0.5)) {
                    trunkZ += (int)Math.signum(trunkSin);
                }
            }
        }
        int leafAngle = 0;
        while (leafAngle < 360) {
            leafAngle += 15 + random.nextInt(15);
            final float angleR = (float)Math.toRadians(leafAngle);
            final float sin = MathHelper.sin(angleR);
            final float cos = MathHelper.cos(angleR);
            final float angleY = random.nextFloat() * (float)Math.toRadians(30.0);
            final float cosY = MathHelper.cos(angleY);
            final float sinY = MathHelper.sin(angleY);
            int i3 = trunkX;
            int j2 = j + height - 1;
            int k3 = trunkZ;
            final int jStart = j2;
            for (int branchLength = 5, l2 = 1; l2 <= branchLength; ++l2) {
                if (Math.floor(sinY * l2) != Math.floor(sinY * (l2 - 1))) {
                    j2 += (int)Math.signum(sinY);
                }
                else {
                    double dCos = Math.floor(Math.abs(cos * l2)) - Math.floor(Math.abs(cos * (l2 - 1)));
                    double dSin = Math.floor(Math.abs(sin * l2)) - Math.floor(Math.abs(sin * (l2 - 1)));
                    dCos = Math.abs(dCos);
                    dSin = Math.abs(dSin);
                    final boolean cosOrSin = (dCos == dSin) ? random.nextBoolean() : (dCos > dSin);
                    if (cosOrSin) {
                        i3 += (int)Math.signum(cos);
                    }
                    else {
                        k3 += (int)Math.signum(sin);
                    }
                }
                final boolean wood = l2 == 1;
                final Block block = world.getBlock(i3, j2, k3);
                final boolean replacingWood = block.isWood((IBlockAccess)world, i3, j2, k3);
                if (!block.isReplaceable((IBlockAccess)world, i3, j2, k3) && !block.isLeaves((IBlockAccess)world, i3, j2, k3) && !replacingWood) {
                    break;
                }
                if (wood) {
                    this.func_150516_a(world, i3, j2, k3, this.woodBlock, this.woodMeta);
                }
                else if (!replacingWood) {
                    this.func_150516_a(world, i3, j2, k3, this.leafBlock, this.leafMeta);
                }
                if (l2 >= 5) {
                    break;
                }
            }
        }
        world.getBlock(i, j - 1, k).onPlantGrow(world, i, j - 1, k, i, j, k);
        return true;
    }
}

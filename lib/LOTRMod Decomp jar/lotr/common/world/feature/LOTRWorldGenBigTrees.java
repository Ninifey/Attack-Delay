// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import java.util.Random;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenBigTrees extends WorldGenAbstractTree
{
    private static final byte[] otherCoordPairs;
    private Random rand;
    private World worldObj;
    private int[] basePos;
    private int heightLimit;
    private int height;
    private double heightAttenuation;
    private double branchDensity;
    private double branchSlope;
    private double scaleWidth;
    private double leafDensity;
    private int heightLimitLimit;
    private int leafDistanceLimit;
    private int[][] leafNodes;
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    
    public LOTRWorldGenBigTrees(final boolean flag, final Block block, final int i, final Block block1, final int j) {
        super(flag);
        this.rand = new Random();
        this.basePos = new int[] { 0, 0, 0 };
        this.heightAttenuation = 0.618;
        this.branchDensity = 1.0;
        this.branchSlope = 0.381;
        this.scaleWidth = 1.0;
        this.leafDensity = 1.0;
        this.heightLimitLimit = 12;
        this.leafDistanceLimit = 4;
        this.woodBlock = block;
        this.woodMeta = i;
        this.leafBlock = block1;
        this.leafMeta = i;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        this.worldObj = world;
        final long l = random.nextLong();
        this.rand.setSeed(l);
        this.basePos[0] = i;
        this.basePos[1] = j;
        this.basePos[2] = k;
        if (this.heightLimit == 0) {
            this.heightLimit = 5 + this.rand.nextInt(this.heightLimitLimit);
        }
        if (!this.validTreeLocation()) {
            return false;
        }
        this.generateLeafNodeList();
        this.generateLeaves();
        this.generateTrunk();
        this.generateLeafNodeBases();
        return true;
    }
    
    private void generateLeafNodeList() {
        this.height = (int)(this.heightLimit * this.heightAttenuation);
        if (this.height >= this.heightLimit) {
            this.height = this.heightLimit - 1;
        }
        int i = (int)(1.382 + Math.pow(this.leafDensity * this.heightLimit / 13.0, 2.0));
        if (i < 1) {
            i = 1;
        }
        final int[][] aint = new int[i * this.heightLimit][4];
        int j = this.basePos[1] + this.heightLimit - this.leafDistanceLimit;
        int k = 1;
        final int l = this.basePos[1] + this.height;
        int i2 = j - this.basePos[1];
        aint[0][0] = this.basePos[0];
        aint[0][1] = j;
        aint[0][2] = this.basePos[2];
        aint[0][3] = l;
        --j;
        while (i2 >= 0) {
            int j2 = 0;
            final float f = this.layerSize(i2);
            if (f < 0.0f) {
                --j;
                --i2;
            }
            else {
                final double d0 = 0.5;
                while (j2 < i) {
                    final double d2 = this.scaleWidth * f * (this.rand.nextFloat() + 0.328);
                    final double d3 = this.rand.nextFloat() * 2.0 * 3.141592653589793;
                    final int k2 = MathHelper.floor_double(d2 * Math.sin(d3) + this.basePos[0] + d0);
                    final int l2 = MathHelper.floor_double(d2 * Math.cos(d3) + this.basePos[2] + d0);
                    final int[] aint2 = { k2, j, l2 };
                    final int[] aint3 = { k2, j + this.leafDistanceLimit, l2 };
                    if (this.checkBlockLine(aint2, aint3) == -1) {
                        final int[] aint4 = { this.basePos[0], this.basePos[1], this.basePos[2] };
                        final double d4 = Math.sqrt(Math.pow(Math.abs(this.basePos[0] - aint2[0]), 2.0) + Math.pow(Math.abs(this.basePos[2] - aint2[2]), 2.0));
                        final double d5 = d4 * this.branchSlope;
                        if (aint2[1] - d5 > l) {
                            aint4[1] = l;
                        }
                        else {
                            aint4[1] = (int)(aint2[1] - d5);
                        }
                        if (this.checkBlockLine(aint4, aint2) == -1) {
                            aint[k][0] = k2;
                            aint[k][1] = j;
                            aint[k][2] = l2;
                            aint[k][3] = aint4[1];
                            ++k;
                        }
                    }
                    ++j2;
                }
                --j;
                --i2;
            }
        }
        System.arraycopy(aint, 0, this.leafNodes = new int[k][4], 0, k);
    }
    
    private void genTreeLayer(final int par1, final int par2, final int par3, final float par4, final byte par5, final Block par6, final int meta) {
        final int i1 = (int)(par4 + 0.618);
        final byte b1 = LOTRWorldGenBigTrees.otherCoordPairs[par5];
        final byte b2 = LOTRWorldGenBigTrees.otherCoordPairs[par5 + 3];
        final int[] aint = { par1, par2, par3 };
        final int[] aint2 = { 0, 0, 0 };
        int j1 = -i1;
        int k1 = -i1;
        aint2[par5] = aint[par5];
        while (j1 <= i1) {
            aint2[b1] = aint[b1] + j1;
            for (k1 = -i1; k1 <= i1; ++k1) {
                final double d0 = Math.pow(Math.abs(j1) + 0.5, 2.0) + Math.pow(Math.abs(k1) + 0.5, 2.0);
                if (d0 <= par4 * par4) {
                    aint2[b2] = aint[b2] + k1;
                    final Block block = this.worldObj.getBlock(aint2[0], aint2[1], aint2[2]);
                    if (block.getMaterial() == Material.air || block.isLeaves((IBlockAccess)this.worldObj, aint2[0], aint2[1], aint2[2])) {
                        this.func_150516_a(this.worldObj, aint2[0], aint2[1], aint2[2], par6, meta);
                    }
                }
            }
            ++j1;
        }
    }
    
    private float layerSize(final int par1) {
        if (par1 < (float)this.heightLimit * 0.3) {
            return -1.618f;
        }
        final float f = this.heightLimit / 2.0f;
        final float f2 = this.heightLimit / 2.0f - par1;
        float f3;
        if (f2 == 0.0f) {
            f3 = f;
        }
        else if (Math.abs(f2) >= f) {
            f3 = 0.0f;
        }
        else {
            f3 = (float)Math.sqrt(Math.pow(Math.abs(f), 2.0) - Math.pow(Math.abs(f2), 2.0));
        }
        f3 *= 0.5f;
        return f3;
    }
    
    private float leafSize(final int par1) {
        return (par1 >= 0 && par1 < this.leafDistanceLimit) ? ((par1 != 0 && par1 != this.leafDistanceLimit - 1) ? 3.0f : 2.0f) : -1.0f;
    }
    
    private void generateLeafNode(final int i, final int j, final int k) {
        for (int j2 = j, j3 = j + this.leafDistanceLimit; j2 < j3; ++j2) {
            final float f = this.leafSize(j2 - j);
            this.genTreeLayer(i, j2, k, f, (byte)1, this.leafBlock, this.leafMeta);
        }
    }
    
    private void placeBlockLine(final int[] par1ArrayOfInteger, final int[] par2ArrayOfInteger, final Block block, final int meta) {
        final int[] aint2 = { 0, 0, 0 };
        byte b0 = 0;
        byte b2 = 0;
        while (b0 < 3) {
            aint2[b0] = par2ArrayOfInteger[b0] - par1ArrayOfInteger[b0];
            if (Math.abs(aint2[b0]) > Math.abs(aint2[b2])) {
                b2 = b0;
            }
            ++b0;
        }
        if (aint2[b2] != 0) {
            final byte b3 = LOTRWorldGenBigTrees.otherCoordPairs[b2];
            final byte b4 = LOTRWorldGenBigTrees.otherCoordPairs[b2 + 3];
            byte b5;
            if (aint2[b2] > 0) {
                b5 = 1;
            }
            else {
                b5 = -1;
            }
            final double d0 = aint2[b3] / (double)aint2[b2];
            final double d2 = aint2[b4] / (double)aint2[b2];
            final int[] aint3 = { 0, 0, 0 };
            for (int j = 0, k = aint2[b2] + b5; j != k; j += b5) {
                aint3[b2] = MathHelper.floor_double(par1ArrayOfInteger[b2] + j + 0.5);
                aint3[b3] = MathHelper.floor_double(par1ArrayOfInteger[b3] + j * d0 + 0.5);
                aint3[b4] = MathHelper.floor_double(par1ArrayOfInteger[b4] + j * d2 + 0.5);
                byte b6 = 0;
                final int l = Math.abs(aint3[0] - par1ArrayOfInteger[0]);
                final int i1 = Math.abs(aint3[2] - par1ArrayOfInteger[2]);
                final int j2 = Math.max(l, i1);
                if (j2 > 0) {
                    if (l == j2) {
                        b6 = 4;
                    }
                    else if (i1 == j2) {
                        b6 = 8;
                    }
                }
                this.func_150516_a(this.worldObj, aint3[0], aint3[1], aint3[2], block, meta | b6);
            }
        }
    }
    
    private void generateLeaves() {
        for (int i = 0, j = this.leafNodes.length; i < j; ++i) {
            final int k = this.leafNodes[i][0];
            final int l = this.leafNodes[i][1];
            final int i2 = this.leafNodes[i][2];
            this.generateLeafNode(k, l, i2);
        }
    }
    
    private boolean leafNodeNeedsBase(final int par1) {
        return par1 >= this.heightLimit * 0.2;
    }
    
    private void generateTrunk() {
        final int i = this.basePos[0];
        final int j = this.basePos[1];
        final int j2 = this.basePos[1] + this.height;
        final int k = this.basePos[2];
        final int[] aint = { i, j, k };
        final int[] aint2 = { i, j2, k };
        this.placeBlockLine(aint, aint2, this.woodBlock, this.woodMeta);
        this.worldObj.getBlock(i, j - 1, k).onPlantGrow(this.worldObj, i, j - 1, k, i, j, k);
    }
    
    private void generateLeafNodeBases() {
        int i = 0;
        final int j = this.leafNodes.length;
        final int[] aint = { this.basePos[0], this.basePos[1], this.basePos[2] };
        while (i < j) {
            final int[] aint2 = this.leafNodes[i];
            final int[] aint3 = { aint2[0], aint2[1], aint2[2] };
            aint[1] = aint2[3];
            final int k = aint[1] - this.basePos[1];
            if (this.leafNodeNeedsBase(k)) {
                this.placeBlockLine(aint, aint3, this.woodBlock, this.woodMeta);
            }
            ++i;
        }
    }
    
    private int checkBlockLine(final int[] par1ArrayOfInteger, final int[] par2ArrayOfInteger) {
        final int[] aint2 = { 0, 0, 0 };
        byte b0 = 0;
        byte b2 = 0;
        while (b0 < 3) {
            aint2[b0] = par2ArrayOfInteger[b0] - par1ArrayOfInteger[b0];
            if (Math.abs(aint2[b0]) > Math.abs(aint2[b2])) {
                b2 = b0;
            }
            ++b0;
        }
        if (aint2[b2] == 0) {
            return -1;
        }
        final byte b3 = LOTRWorldGenBigTrees.otherCoordPairs[b2];
        final byte b4 = LOTRWorldGenBigTrees.otherCoordPairs[b2 + 3];
        byte b5;
        if (aint2[b2] > 0) {
            b5 = 1;
        }
        else {
            b5 = -1;
        }
        final double d0 = aint2[b3] / (double)aint2[b2];
        final double d2 = aint2[b4] / (double)aint2[b2];
        final int[] aint3 = { 0, 0, 0 };
        int i;
        int j;
        for (i = 0, j = aint2[b2] + b5; i != j; i += b5) {
            aint3[b2] = par1ArrayOfInteger[b2] + i;
            aint3[b3] = MathHelper.floor_double(par1ArrayOfInteger[b3] + i * d0);
            aint3[b4] = MathHelper.floor_double(par1ArrayOfInteger[b4] + i * d2);
            final Block block = this.worldObj.getBlock(aint3[0], aint3[1], aint3[2]);
            if (block.getMaterial() != Material.air && !block.isLeaves((IBlockAccess)this.worldObj, aint3[0], aint3[1], aint3[2])) {
                break;
            }
        }
        return (i == j) ? -1 : Math.abs(i);
    }
    
    private boolean validTreeLocation() {
        final int[] aint = { this.basePos[0], this.basePos[1], this.basePos[2] };
        final int[] aint2 = { this.basePos[0], this.basePos[1] + this.heightLimit - 1, this.basePos[2] };
        final Block block = this.worldObj.getBlock(this.basePos[0], this.basePos[1] - 1, this.basePos[2]);
        if (!block.canSustainPlant((IBlockAccess)this.worldObj, this.basePos[0], this.basePos[1] - 1, this.basePos[2], ForgeDirection.UP, (IPlantable)Blocks.sapling)) {
            return false;
        }
        final int j = this.checkBlockLine(aint, aint2);
        if (j == -1) {
            return true;
        }
        if (j < 6) {
            return false;
        }
        this.heightLimit = j;
        return true;
    }
    
    static {
        otherCoordPairs = new byte[] { 2, 0, 0, 1, 2, 1 };
    }
}

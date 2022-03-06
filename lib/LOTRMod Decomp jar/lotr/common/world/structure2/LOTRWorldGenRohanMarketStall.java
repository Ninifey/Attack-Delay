// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityRohanOrcharder;
import com.google.common.math.IntMath;
import lotr.common.entity.npc.LOTREntityRohanBaker;
import lotr.common.entity.npc.LOTREntityRohanFishmonger;
import lotr.common.entity.npc.LOTREntityRohanButcher;
import lotr.common.entity.npc.LOTREntityRohanBrewer;
import lotr.common.entity.npc.LOTREntityRohanBuilder;
import lotr.common.entity.npc.LOTREntityRohanLumberman;
import lotr.common.entity.npc.LOTREntityRohanFarmer;
import lotr.common.entity.npc.LOTREntityRohanBlacksmith;
import lotr.common.entity.npc.LOTREntityRohanMan;
import net.minecraft.entity.EntityCreature;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import java.util.Random;

public abstract class LOTRWorldGenRohanMarketStall extends LOTRWorldGenRohanStructure
{
    private static Class[] allStallTypes;
    
    public static LOTRWorldGenStructureBase2 getRandomStall(final Random random, final boolean flag) {
        try {
            final Class cls = LOTRWorldGenRohanMarketStall.allStallTypes[random.nextInt(LOTRWorldGenRohanMarketStall.allStallTypes.length)];
            return cls.getConstructor(Boolean.TYPE).newInstance(flag);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public LOTRWorldGenRohanMarketStall(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 3);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -2; i2 <= 2; ++i2) {
                for (int k2 = -2; k2 <= 2; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i2, j3, k2, LOTRMod.dirtPath, 0);
                    this.setGrassToDirt(world, i2, j3 - 1, k2);
                }
                for (int j3 = 1; j3 <= 4; ++j3) {
                    this.setAir(world, i2, j3, k2);
                }
                if (i3 == 2 && k3 == 2) {
                    if (k2 < 0) {
                        for (int j3 = 1; j3 <= 4; ++j3) {
                            this.setBlockAndMetadata(world, i2, j3, k2, super.fenceBlock, super.fenceMeta);
                        }
                    }
                    else {
                        for (int j3 = 1; j3 <= 3; ++j3) {
                            this.setBlockAndMetadata(world, i2, j3, k2, super.fenceBlock, super.fenceMeta);
                        }
                    }
                }
                else {
                    int j4 = 4;
                    if (k2 == 2 || (k2 == 1 && i3 == 2)) {
                        j4 = 3;
                    }
                    this.generateRoof(world, random, i2, j4, k2);
                }
            }
        }
        this.setBlockAndMetadata(world, -1, 1, -2, super.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 1, -2, super.plankStairBlock, 6);
        this.setBlockAndMetadata(world, 1, 1, -2, super.plankStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 1, 2, super.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 1, 2, super.plankStairBlock, 7);
        this.setBlockAndMetadata(world, 1, 1, 2, super.plankStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 1, -1, super.plankStairBlock, 7);
        this.setBlockAndMetadata(world, 2, 1, 0, super.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 2, 1, 1, super.plankStairBlock, 6);
        this.setBlockAndMetadata(world, -2, 1, -1, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -2, 1, 0, super.fenceGateBlock, 1);
        this.setBlockAndMetadata(world, -2, 1, 1, super.plankBlock, super.plankMeta);
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 1, 1, super.plank2StairBlock, 6);
            this.setBlockAndMetadata(world, i2, 3, 1, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        }
        for (int k4 = -1; k4 <= 0; ++k4) {
            this.setBlockAndMetadata(world, -2, 3, k4, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            this.setBlockAndMetadata(world, 2, 3, k4, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        }
        this.setBlockAndMetadata(world, 1, 1, -1, (Block)Blocks.chest, 3);
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 3, -2, super.fenceBlock, super.fenceMeta);
        }
        final LOTREntityRohanMan trader = this.createTrader(world);
        this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
        return true;
    }
    
    protected abstract void generateRoof(final World p0, final Random p1, final int p2, final int p3, final int p4);
    
    protected abstract LOTREntityRohanMan createTrader(final World p0);
    
    static {
        LOTRWorldGenRohanMarketStall.allStallTypes = new Class[] { Blacksmith.class, Farmer.class, Lumber.class, Builder.class, Brewer.class, Butcher.class, Fish.class, Baker.class, Orcharder.class };
    }
    
    public static class Blacksmith extends LOTRWorldGenRohanMarketStall
    {
        public Blacksmith(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected void generateRoof(final World world, final Random random, final int i1, final int j1, final int k1) {
            final int i2 = Math.abs(i1);
            final int k2 = Math.abs(k1);
            if (i2 + k2 >= 3) {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 7);
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 15);
            }
        }
        
        @Override
        protected LOTREntityRohanMan createTrader(final World world) {
            return new LOTREntityRohanBlacksmith(world);
        }
    }
    
    public static class Farmer extends LOTRWorldGenRohanMarketStall
    {
        public Farmer(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected void generateRoof(final World world, final Random random, final int i1, final int j1, final int k1) {
            if (random.nextInt(3) == 0) {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 0);
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 8);
            }
        }
        
        @Override
        protected LOTREntityRohanMan createTrader(final World world) {
            return new LOTREntityRohanFarmer(world);
        }
    }
    
    public static class Lumber extends LOTRWorldGenRohanMarketStall
    {
        public Lumber(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected void generateRoof(final World world, final Random random, final int i1, final int j1, final int k1) {
            final int i2 = Math.abs(i1);
            final int k2 = Math.abs(k1);
            if (i2 + k2 >= 3) {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 13);
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
            }
        }
        
        @Override
        protected LOTREntityRohanMan createTrader(final World world) {
            return new LOTREntityRohanLumberman(world);
        }
    }
    
    public static class Builder extends LOTRWorldGenRohanMarketStall
    {
        public Builder(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected void generateRoof(final World world, final Random random, final int i1, final int j1, final int k1) {
            final int i2 = Math.abs(i1);
            final int k2 = Math.abs(k1);
            if (k2 % 2 == 0 && i2 % 2 == k2 / 2 % 2) {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 7);
            }
        }
        
        @Override
        protected LOTREntityRohanMan createTrader(final World world) {
            return new LOTREntityRohanBuilder(world);
        }
    }
    
    public static class Brewer extends LOTRWorldGenRohanMarketStall
    {
        public Brewer(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected void generateRoof(final World world, final Random random, final int i1, final int j1, final int k1) {
            final int i2 = Math.abs(i1);
            final int k2 = Math.abs(k1);
            if (i2 % 2 == 1) {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 4);
            }
        }
        
        @Override
        protected LOTREntityRohanMan createTrader(final World world) {
            return new LOTREntityRohanBrewer(world);
        }
    }
    
    public static class Butcher extends LOTRWorldGenRohanMarketStall
    {
        public Butcher(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected void generateRoof(final World world, final Random random, final int i1, final int j1, final int k1) {
            if (random.nextInt(3) == 0) {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 14);
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 6);
            }
        }
        
        @Override
        protected LOTREntityRohanMan createTrader(final World world) {
            return new LOTREntityRohanButcher(world);
        }
    }
    
    public static class Fish extends LOTRWorldGenRohanMarketStall
    {
        public Fish(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected void generateRoof(final World world, final Random random, final int i1, final int j1, final int k1) {
            final int i2 = Math.abs(i1);
            final int k2 = Math.abs(k1);
            if (k2 % 2 == 1) {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 11);
            }
            else if (i2 % 2 == k2 / 2 % 2) {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 0);
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 3);
            }
        }
        
        @Override
        protected LOTREntityRohanMan createTrader(final World world) {
            return new LOTREntityRohanFishmonger(world);
        }
    }
    
    public static class Baker extends LOTRWorldGenRohanMarketStall
    {
        public Baker(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected void generateRoof(final World world, final Random random, final int i1, final int j1, final int k1) {
            final int i2 = Math.abs(i1);
            final int k2 = Math.abs(k1);
            if (i2 % 2 == 0) {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 1);
            }
        }
        
        @Override
        protected LOTREntityRohanMan createTrader(final World world) {
            return new LOTREntityRohanBaker(world);
        }
    }
    
    public static class Orcharder extends LOTRWorldGenRohanMarketStall
    {
        public Orcharder(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected void generateRoof(final World world, final Random random, final int i1, final int j1, final int k1) {
            final int i2 = Math.abs(i1);
            final int k2 = Math.abs(k1);
            if (IntMath.mod(i2 + k2, 2) == 0) {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 14);
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 5);
            }
        }
        
        @Override
        protected LOTREntityRohanMan createTrader(final World world) {
            return new LOTREntityRohanOrcharder(world);
        }
    }
}

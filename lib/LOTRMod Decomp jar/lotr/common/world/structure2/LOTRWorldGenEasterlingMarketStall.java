// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityEasterlingGoldsmith;
import lotr.common.entity.npc.LOTREntityEasterlingFarmer;
import lotr.common.entity.npc.LOTREntityEasterlingHunter;
import lotr.common.entity.npc.LOTREntityEasterlingBaker;
import lotr.common.entity.npc.LOTREntityEasterlingFishmonger;
import lotr.common.entity.npc.LOTREntityEasterlingBrewer;
import lotr.common.entity.npc.LOTREntityEasterlingButcher;
import lotr.common.entity.npc.LOTREntityEasterlingMason;
import lotr.common.entity.npc.LOTREntityEasterlingLumberman;
import com.google.common.math.IntMath;
import lotr.common.entity.npc.LOTREntityEasterlingBlacksmith;
import lotr.common.entity.npc.LOTREntityEasterling;
import net.minecraft.entity.EntityCreature;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import java.util.Random;

public abstract class LOTRWorldGenEasterlingMarketStall extends LOTRWorldGenEasterlingStructure
{
    private static Class[] allStallTypes;
    
    public static LOTRWorldGenStructureBase2 getRandomStall(final Random random, final boolean flag) {
        try {
            final Class cls = LOTRWorldGenEasterlingMarketStall.allStallTypes[random.nextInt(LOTRWorldGenEasterlingMarketStall.allStallTypes.length)];
            return cls.getConstructor(Boolean.TYPE).newInstance(flag);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public LOTRWorldGenEasterlingMarketStall(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 3);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -2; i2 <= 2; ++i2) {
                for (int k2 = -2; k2 <= 2; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (maxHeight - minHeight > 5) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            for (int k3 = -2; k3 <= 2; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i3, j2 - 1, k3);
                }
                for (int j2 = 1; j2 <= 4; ++j2) {
                    this.setAir(world, i3, j2, k3);
                }
                if (i4 == 2 && k4 == 2) {
                    for (int j2 = 1; j2 <= 3; ++j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                    }
                }
                else if (i4 == 2 || k4 == 2) {
                    this.setBlockAndMetadata(world, i3, 3, k3, LOTRMod.reedBars, 0);
                }
                this.generateRoof(world, random, i3, 4, k3);
            }
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, -2, super.plankStairBlock, 6);
            this.setBlockAndMetadata(world, i3, 1, 2, super.plankStairBlock, 7);
        }
        for (int k5 = -1; k5 <= 1; ++k5) {
            this.setBlockAndMetadata(world, -2, 1, k5, super.plankStairBlock, 5);
            this.setBlockAndMetadata(world, 2, 1, k5, super.plankStairBlock, 4);
        }
        this.setBlockAndMetadata(world, -2, 1, 0, super.fenceGateBlock, 1);
        this.setBlockAndMetadata(world, -1, 1, 1, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 1, 1, 1, (Block)Blocks.chest, 2);
        final LOTREntityEasterling trader = this.createTrader(world);
        this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
        return true;
    }
    
    protected abstract void generateRoof(final World p0, final Random p1, final int p2, final int p3, final int p4);
    
    protected abstract LOTREntityEasterling createTrader(final World p0);
    
    static {
        LOTRWorldGenEasterlingMarketStall.allStallTypes = new Class[] { Blacksmith.class, Lumber.class, Mason.class, Butcher.class, Brewer.class, Fish.class, Baker.class, Hunter.class, Farmer.class, Gold.class };
    }
    
    public static class Blacksmith extends LOTRWorldGenEasterlingMarketStall
    {
        public Blacksmith(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected void generateRoof(final World world, final Random random, final int i1, final int j1, final int k1) {
            final int i2 = Math.abs(i1);
            final int k2 = Math.abs(k1);
            if (i2 == k2) {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 15);
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 7);
            }
        }
        
        @Override
        protected LOTREntityEasterling createTrader(final World world) {
            return new LOTREntityEasterlingBlacksmith(world);
        }
    }
    
    public static class Lumber extends LOTRWorldGenEasterlingMarketStall
    {
        public Lumber(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected void generateRoof(final World world, final Random random, final int i1, final int j1, final int k1) {
            final int i2 = Math.abs(i1);
            final int k2 = Math.abs(k1);
            if ((i2 == 2 || k2 == 2) && IntMath.mod(i2 + k2, 2) == 0) {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 13);
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
            }
        }
        
        @Override
        protected LOTREntityEasterling createTrader(final World world) {
            return new LOTREntityEasterlingLumberman(world);
        }
    }
    
    public static class Mason extends LOTRWorldGenEasterlingMarketStall
    {
        public Mason(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected void generateRoof(final World world, final Random random, final int i1, final int j1, final int k1) {
            final int i2 = Math.abs(i1);
            final int k2 = Math.abs(k1);
            if (i2 == 2 || k2 == 2) {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 7);
            }
            else if (i2 == 1 || k2 == 1) {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 14);
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 7);
            }
        }
        
        @Override
        protected LOTREntityEasterling createTrader(final World world) {
            return new LOTREntityEasterlingMason(world);
        }
    }
    
    public static class Butcher extends LOTRWorldGenEasterlingMarketStall
    {
        public Butcher(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected void generateRoof(final World world, final Random random, final int i1, final int j1, final int k1) {
            final int i2 = Math.abs(i1);
            final int k2 = Math.abs(k1);
            if (i2 == 2 || k2 == 2) {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 6);
            }
            else if (i2 == 1 || k2 == 1) {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 14);
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 0);
            }
        }
        
        @Override
        protected LOTREntityEasterling createTrader(final World world) {
            return new LOTREntityEasterlingButcher(world);
        }
    }
    
    public static class Brewer extends LOTRWorldGenEasterlingMarketStall
    {
        public Brewer(final boolean flag) {
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
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 0);
            }
        }
        
        @Override
        protected LOTREntityEasterling createTrader(final World world) {
            return new LOTREntityEasterlingBrewer(world);
        }
    }
    
    public static class Fish extends LOTRWorldGenEasterlingMarketStall
    {
        public Fish(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected void generateRoof(final World world, final Random random, final int i1, final int j1, final int k1) {
            final int i2 = Math.abs(i1);
            final int k2 = Math.abs(k1);
            if (i2 % 2 == 0) {
                if (k2 == 2) {
                    this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 0);
                }
                else {
                    this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 3);
                }
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 11);
            }
        }
        
        @Override
        protected LOTREntityEasterling createTrader(final World world) {
            return new LOTREntityEasterlingFishmonger(world);
        }
    }
    
    public static class Baker extends LOTRWorldGenEasterlingMarketStall
    {
        public Baker(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected void generateRoof(final World world, final Random random, final int i1, final int j1, final int k1) {
            final int i2 = Math.abs(i1);
            final int k2 = Math.abs(k1);
            if (k2 % 2 == 0) {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 1);
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
            }
        }
        
        @Override
        protected LOTREntityEasterling createTrader(final World world) {
            return new LOTREntityEasterlingBaker(world);
        }
    }
    
    public static class Hunter extends LOTRWorldGenEasterlingMarketStall
    {
        public Hunter(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected void generateRoof(final World world, final Random random, final int i1, final int j1, final int k1) {
            if (IntMath.mod(i1, 2) == 0 && IntMath.mod(k1, 2) == 0) {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 15);
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
            }
        }
        
        @Override
        protected LOTREntityEasterling createTrader(final World world) {
            return new LOTREntityEasterlingHunter(world);
        }
    }
    
    public static class Farmer extends LOTRWorldGenEasterlingMarketStall
    {
        public Farmer(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected void generateRoof(final World world, final Random random, final int i1, final int j1, final int k1) {
            final int i2 = Math.abs(i1);
            final int k2 = Math.abs(k1);
            if (IntMath.mod(i2 + k2, 2) == 0) {
                if (Integer.signum(i1) != -Integer.signum(k1) && i2 + k2 == 2) {
                    this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 4);
                }
                else {
                    this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 13);
                }
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
            }
        }
        
        @Override
        protected LOTREntityEasterling createTrader(final World world) {
            return new LOTREntityEasterlingFarmer(world);
        }
    }
    
    public static class Gold extends LOTRWorldGenEasterlingMarketStall
    {
        public Gold(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected void generateRoof(final World world, final Random random, final int i1, final int j1, final int k1) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 4);
        }
        
        @Override
        protected LOTREntityEasterling createTrader(final World world) {
            return new LOTREntityEasterlingGoldsmith(world);
        }
    }
}

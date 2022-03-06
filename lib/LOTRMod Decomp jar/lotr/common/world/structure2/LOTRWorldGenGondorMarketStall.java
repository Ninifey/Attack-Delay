// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityGondorBaker;
import lotr.common.entity.npc.LOTREntityGondorBlacksmith;
import lotr.common.entity.npc.LOTREntityGondorFarmer;
import lotr.common.entity.npc.LOTREntityGondorFishmonger;
import lotr.common.entity.npc.LOTREntityGondorButcher;
import lotr.common.entity.npc.LOTREntityGondorFlorist;
import lotr.common.entity.npc.LOTREntityGondorBrewer;
import lotr.common.entity.npc.LOTREntityGondorMason;
import lotr.common.entity.npc.LOTREntityGondorLumberman;
import lotr.common.entity.npc.LOTREntityGondorGreengrocer;
import com.google.common.math.IntMath;
import lotr.common.entity.npc.LOTREntityGondorMan;
import net.minecraft.entity.EntityCreature;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import java.util.Random;

public abstract class LOTRWorldGenGondorMarketStall extends LOTRWorldGenGondorStructure
{
    private static Class[] allStallTypes;
    
    public static LOTRWorldGenStructureBase2 getRandomStall(final Random random, final boolean flag) {
        try {
            final Class cls = LOTRWorldGenGondorMarketStall.allStallTypes[random.nextInt(LOTRWorldGenGondorMarketStall.allStallTypes.length)];
            return cls.getConstructor(Boolean.TYPE).newInstance(flag);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public LOTRWorldGenGondorMarketStall(final boolean flag) {
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
                        this.setBlockAndMetadata(world, i3, j2, k3, super.fenceBlock, super.fenceMeta);
                    }
                }
                else if (i4 == 2 || k4 == 2) {
                    this.setBlockAndMetadata(world, i3, 1, k3, super.plankBlock, super.plankMeta);
                    this.setBlockAndMetadata(world, i3, 3, k3, super.fenceBlock, super.fenceMeta);
                }
                this.generateRoof(world, random, i3, 4, k3);
            }
        }
        this.setBlockAndMetadata(world, -2, 1, 0, super.fenceGateBlock, 1);
        this.setBlockAndMetadata(world, -1, 1, -1, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 1, 1, -1, (Block)Blocks.chest, 3);
        final LOTREntityGondorMan trader = this.createTrader(world);
        this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
        return true;
    }
    
    protected abstract void generateRoof(final World p0, final Random p1, final int p2, final int p3, final int p4);
    
    protected abstract LOTREntityGondorMan createTrader(final World p0);
    
    static {
        LOTRWorldGenGondorMarketStall.allStallTypes = new Class[] { Greengrocer.class, Lumber.class, Mason.class, Brewer.class, Flowers.class, Butcher.class, Fish.class, Farmer.class, Blacksmith.class, Baker.class };
    }
    
    public static class Greengrocer extends LOTRWorldGenGondorMarketStall
    {
        public Greengrocer(final boolean flag) {
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
        protected LOTREntityGondorMan createTrader(final World world) {
            return new LOTREntityGondorGreengrocer(world);
        }
    }
    
    public static class Lumber extends LOTRWorldGenGondorMarketStall
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
        protected LOTREntityGondorMan createTrader(final World world) {
            return new LOTREntityGondorLumberman(world);
        }
    }
    
    public static class Mason extends LOTRWorldGenGondorMarketStall
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
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 8);
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 7);
            }
        }
        
        @Override
        protected LOTREntityGondorMan createTrader(final World world) {
            return new LOTREntityGondorMason(world);
        }
    }
    
    public static class Brewer extends LOTRWorldGenGondorMarketStall
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
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 4);
            }
        }
        
        @Override
        protected LOTREntityGondorMan createTrader(final World world) {
            return new LOTREntityGondorBrewer(world);
        }
    }
    
    public static class Flowers extends LOTRWorldGenGondorMarketStall
    {
        public Flowers(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected void generateRoof(final World world, final Random random, final int i1, final int j1, final int k1) {
            final int i2 = Math.abs(i1);
            final int k2 = Math.abs(k1);
            if (i2 == k2) {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 4);
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 13);
            }
        }
        
        @Override
        protected LOTREntityGondorMan createTrader(final World world) {
            return new LOTREntityGondorFlorist(world);
        }
    }
    
    public static class Butcher extends LOTRWorldGenGondorMarketStall
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
        protected LOTREntityGondorMan createTrader(final World world) {
            return new LOTREntityGondorButcher(world);
        }
    }
    
    public static class Fish extends LOTRWorldGenGondorMarketStall
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
        protected LOTREntityGondorMan createTrader(final World world) {
            return new LOTREntityGondorFishmonger(world);
        }
    }
    
    public static class Farmer extends LOTRWorldGenGondorMarketStall
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
        protected LOTREntityGondorMan createTrader(final World world) {
            return new LOTREntityGondorFarmer(world);
        }
    }
    
    public static class Blacksmith extends LOTRWorldGenGondorMarketStall
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
        protected LOTREntityGondorMan createTrader(final World world) {
            return new LOTREntityGondorBlacksmith(world);
        }
    }
    
    public static class Baker extends LOTRWorldGenGondorMarketStall
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
        protected LOTREntityGondorMan createTrader(final World world) {
            return new LOTREntityGondorBaker(world);
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.World;

public abstract class LOTRWorldGenStoneRuin extends LOTRWorldGenStructureBase2
{
    private int minWidth;
    private int maxWidth;
    
    private LOTRWorldGenStoneRuin(final int i, final int j) {
        super(false);
        this.minWidth = i;
        this.maxWidth = j;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        final int width = MathHelper.getRandomIntegerInRange(random, this.minWidth, this.maxWidth);
        boolean generateColumn = random.nextInt(3) > 0;
        if (generateColumn) {
            int minHeight = 0;
            int maxHeight = 0;
            final int columnX = 0 - width / 2;
            final int columnZ = 0 - width / 2;
            Label_0187: {
                if (super.restrictions) {
                    for (int i2 = columnX; i2 < columnX + width; ++i2) {
                        for (int k2 = columnZ; k2 < columnZ + width; ++k2) {
                            final int j2 = this.getTopBlock(world, i2, k2);
                            if (j2 < minHeight) {
                                minHeight = j2;
                            }
                            if (j2 > maxHeight) {
                                maxHeight = j2;
                            }
                            if (maxHeight - minHeight > 8) {
                                generateColumn = false;
                                break Label_0187;
                            }
                            if (!this.isSurface(world, i2, j2 - 1, k2)) {
                                generateColumn = false;
                                break Label_0187;
                            }
                        }
                    }
                }
            }
            if (generateColumn) {
                final int baseHeight = 4 + random.nextInt(4) + random.nextInt(width * 3);
                for (int i3 = columnX; i3 < columnX + width; ++i3) {
                    for (int k3 = columnZ; k3 < columnZ + width; ++k3) {
                        int j3;
                        for (int height = j3 = (int)(baseHeight * (1.0f + random.nextFloat())); j3 >= minHeight; --j3) {
                            this.placeRandomBrick(world, random, i3, j3, k3);
                            this.setGrassToDirt(world, i3, j3 - 1, k3);
                        }
                    }
                }
            }
        }
        final int radius = width * 2;
        for (int ruinParts = 2 + random.nextInt(4) + random.nextInt(width * 3), l = 0; l < ruinParts; ++l) {
            final int i4 = MathHelper.getRandomIntegerInRange(random, -radius * 2, radius * 2);
            final int k4 = MathHelper.getRandomIntegerInRange(random, -radius * 2, radius * 2);
            final int j4 = this.getTopBlock(world, i4, k4);
            if (!super.restrictions || this.isSurface(world, i4, j4 - 1, k4)) {
                final int randomFeature = random.nextInt(4);
                boolean flag = true;
                if (randomFeature == 0) {
                    if (!this.isOpaque(world, i4, j4, k4)) {
                        this.placeRandomSlab(world, random, i4, j4, k4);
                    }
                }
                else {
                    for (int j5 = j4; j5 < j4 + randomFeature && flag; flag = !this.isOpaque(world, i4, j5, k4), ++j5) {}
                    if (flag) {
                        for (int j5 = j4; j5 < j4 + randomFeature; ++j5) {
                            this.placeRandomBrick(world, random, i4, j5, k4);
                        }
                    }
                }
                if (flag) {
                    this.setGrassToDirt(world, i4, j4 - 1, k4);
                }
            }
        }
        return true;
    }
    
    protected abstract void placeRandomBrick(final World p0, final Random p1, final int p2, final int p3, final int p4);
    
    protected abstract void placeRandomSlab(final World p0, final Random p1, final int p2, final int p3, final int p4);
    
    public static class STONE extends LOTRWorldGenStoneRuin
    {
        public STONE(final int i, final int j) {
            super(i, j, null);
        }
        
        @Override
        protected void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
            final int l = random.nextInt(3);
            switch (l) {
                case 0: {
                    this.setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 0);
                    break;
                }
                case 1: {
                    this.setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 1);
                    break;
                }
                case 2: {
                    this.setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 2);
                    break;
                }
            }
        }
        
        @Override
        protected void placeRandomSlab(final World world, final Random random, final int i, final int j, final int k) {
            if (random.nextInt(4) == 0) {
                this.setBlockAndMetadata(world, i, j, k, (Block)Blocks.stone_slab, 0);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, (Block)Blocks.stone_slab, 5);
            }
        }
    }
    
    public static class ANGMAR extends LOTRWorldGenStoneRuin
    {
        public ANGMAR(final int i, final int j) {
            super(i, j, null);
        }
        
        @Override
        protected void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
            final int l = random.nextInt(2);
            switch (l) {
                case 0: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 0);
                    break;
                }
                case 1: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 1);
                    break;
                }
            }
        }
        
        @Override
        protected void placeRandomSlab(final World world, final Random random, final int i, final int j, final int k) {
            if (random.nextInt(4) == 0) {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle3, 4);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle3, 3);
            }
        }
    }
    
    public static class ARNOR extends LOTRWorldGenStoneRuin
    {
        public ARNOR(final int i, final int j) {
            super(i, j, null);
        }
        
        @Override
        protected void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
            final int l = random.nextInt(3);
            switch (l) {
                case 0: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 3);
                    break;
                }
                case 1: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 4);
                    break;
                }
                case 2: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 5);
                    break;
                }
            }
        }
        
        @Override
        protected void placeRandomSlab(final World world, final Random random, final int i, final int j, final int k) {
            if (random.nextInt(4) == 0) {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle4, 2 + random.nextInt(2));
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle4, 1);
            }
        }
    }
    
    public static class DOL_GULDUR extends LOTRWorldGenStoneRuin
    {
        public DOL_GULDUR(final int i, final int j) {
            super(i, j, null);
        }
        
        @Override
        protected void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
            final int l = random.nextInt(2);
            switch (l) {
                case 0: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 8);
                    break;
                }
                case 1: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 9);
                    break;
                }
            }
        }
        
        @Override
        protected void placeRandomSlab(final World world, final Random random, final int i, final int j, final int k) {
            if (random.nextInt(4) == 0) {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle4, 6);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle4, 5);
            }
        }
    }
    
    public static class DORWINION extends LOTRWorldGenStoneRuin
    {
        public DORWINION(final int i, final int j) {
            super(i, j, null);
        }
        
        @Override
        protected void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick5, 2);
        }
        
        @Override
        protected void placeRandomSlab(final World world, final Random random, final int i, final int j, final int k) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle9, 7);
        }
    }
    
    public static class DWARVEN extends LOTRWorldGenStoneRuin
    {
        public DWARVEN(final int i, final int j) {
            super(i, j, null);
        }
        
        @Override
        protected void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
            final int l = random.nextInt(2);
            switch (l) {
                case 0: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick, 6);
                    break;
                }
                case 1: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick4, 5);
                    break;
                }
            }
        }
        
        @Override
        protected void placeRandomSlab(final World world, final Random random, final int i, final int j, final int k) {
            if (random.nextInt(4) == 0) {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle7, 6);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle, 7);
            }
        }
    }
    
    public static class GALADHRIM extends LOTRWorldGenStoneRuin
    {
        public GALADHRIM(final int i, final int j) {
            super(i, j, null);
        }
        
        @Override
        protected void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
            final int l = random.nextInt(3);
            switch (l) {
                case 0: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick, 11);
                    break;
                }
                case 1: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick, 12);
                    break;
                }
                case 2: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick, 13);
                    break;
                }
            }
        }
        
        @Override
        protected void placeRandomSlab(final World world, final Random random, final int i, final int j, final int k) {
            if (random.nextInt(4) == 0) {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle2, 6 + random.nextInt(2));
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle2, 3 + random.nextInt(3));
            }
        }
    }
    
    public static class HIGH_ELVEN extends LOTRWorldGenStoneRuin
    {
        public HIGH_ELVEN(final int i, final int j) {
            super(i, j, null);
        }
        
        @Override
        protected void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
            final int l = random.nextInt(3);
            switch (l) {
                case 0: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick3, 2);
                    break;
                }
                case 1: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick3, 3);
                    break;
                }
                case 2: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick3, 4);
                    break;
                }
            }
        }
        
        @Override
        protected void placeRandomSlab(final World world, final Random random, final int i, final int j, final int k) {
            if (random.nextInt(4) == 0) {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle6, 0 + random.nextInt(2));
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle5, 5 + random.nextInt(3));
            }
        }
    }
    
    public static class MORDOR extends LOTRWorldGenStoneRuin
    {
        public MORDOR(final int i, final int j) {
            super(i, j, null);
        }
        
        @Override
        protected void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
            final int l = random.nextInt(2);
            switch (l) {
                case 0: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick, 0);
                    break;
                }
                case 1: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick, 7);
                    break;
                }
            }
        }
        
        @Override
        protected void placeRandomSlab(final World world, final Random random, final int i, final int j, final int k) {
            if (random.nextInt(4) == 0) {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle, 1);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle2, 2);
            }
        }
    }
    
    public static class NEAR_HARAD extends LOTRWorldGenStoneRuin
    {
        public NEAR_HARAD(final int i, final int j) {
            super(i, j, null);
        }
        
        @Override
        protected void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
            final int l = random.nextInt(2);
            switch (l) {
                case 0: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick, 15);
                    break;
                }
                case 1: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick3, 11);
                    break;
                }
            }
        }
        
        @Override
        protected void placeRandomSlab(final World world, final Random random, final int i, final int j, final int k) {
            if (random.nextInt(4) == 0) {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle4, 0);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle7, 1);
            }
        }
    }
    
    public static class UMBAR extends LOTRWorldGenStoneRuin
    {
        public UMBAR(final int i, final int j) {
            super(i, j, null);
        }
        
        @Override
        protected void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
            final int l = random.nextInt(2);
            switch (l) {
                case 0: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick6, 6);
                    break;
                }
                case 1: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick6, 7);
                    break;
                }
            }
        }
        
        @Override
        protected void placeRandomSlab(final World world, final Random random, final int i, final int j, final int k) {
            if (random.nextInt(4) == 0) {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle13, 2);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle13, 3);
            }
        }
    }
    
    public static class NUMENOR extends LOTRWorldGenStoneRuin
    {
        public NUMENOR(final int i, final int j) {
            super(i, j, null);
        }
        
        @Override
        protected void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 11);
        }
        
        @Override
        protected void placeRandomSlab(final World world, final Random random, final int i, final int j, final int k) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle5, 3);
        }
    }
    
    public static class RHUN extends LOTRWorldGenStoneRuin
    {
        public RHUN(final int i, final int j) {
            super(i, j, null);
        }
        
        @Override
        protected void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
            final int l = random.nextInt(3);
            switch (l) {
                case 0: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick5, 11);
                    break;
                }
                case 1: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick5, 13);
                    break;
                }
                case 2: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick5, 14);
                    break;
                }
            }
        }
        
        @Override
        protected void placeRandomSlab(final World world, final Random random, final int i, final int j, final int k) {
            if (random.nextInt(4) == 0) {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle12, 4);
            }
            else {
                final int l = random.nextInt(3);
                switch (l) {
                    case 0: {
                        this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle12, 0);
                        break;
                    }
                    case 1: {
                        this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle12, 1);
                        break;
                    }
                    case 2: {
                        this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle12, 2);
                        break;
                    }
                }
            }
        }
    }
    
    public static class TAUREDAIN extends LOTRWorldGenStoneRuin
    {
        public TAUREDAIN(final int i, final int j) {
            super(i, j, null);
        }
        
        @Override
        protected void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
            final int l = random.nextInt(3);
            switch (l) {
                case 0: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick4, 0);
                    break;
                }
                case 1: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick4, 1);
                    break;
                }
                case 2: {
                    this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick4, 2);
                    break;
                }
            }
        }
        
        @Override
        protected void placeRandomSlab(final World world, final Random random, final int i, final int j, final int k) {
            if (random.nextInt(4) == 0) {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle8, 5);
            }
            else {
                final int l = random.nextInt(3);
                switch (l) {
                    case 0: {
                        this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle8, 0);
                        break;
                    }
                    case 1: {
                        this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle8, 1);
                        break;
                    }
                    case 2: {
                        this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle8, 2);
                        break;
                    }
                }
            }
        }
    }
}

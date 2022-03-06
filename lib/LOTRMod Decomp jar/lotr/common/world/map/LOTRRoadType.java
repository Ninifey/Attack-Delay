// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import net.minecraft.world.biome.BiomeGenBase;
import java.util.Random;

public abstract class LOTRRoadType
{
    public static final LOTRRoadType PATH;
    public static final LOTRRoadType COBBLESTONE;
    public static final LOTRRoadType DIRT;
    public static final LOTRRoadType GALADHRIM;
    public static final LOTRRoadType GALADHRIM_RUINED;
    public static final LOTRRoadType HIGH_ELVEN;
    public static final LOTRRoadType HIGH_ELVEN_RUINED;
    public static final LOTRRoadType WOOD_ELVEN;
    public static final LOTRRoadType WOOD_ELVEN_RUINED;
    public static final LOTRRoadType ARNOR;
    public static final LOTRRoadType GONDOR;
    public static final LOTRRoadType GONDOR_RUINED;
    public static final LOTRRoadType DOL_AMROTH;
    public static final LOTRRoadType ROHAN;
    public static final LOTRRoadType DWARVEN;
    public static final LOTRRoadType DALE;
    public static final LOTRRoadType HARAD;
    public static final LOTRRoadType HARAD_PATH;
    public static final LOTRRoadType HARAD_TOWN;
    public static final LOTRRoadType UMBAR;
    public static final LOTRRoadType GULF_HARAD;
    public static final LOTRRoadType TAUREDAIN;
    public static final LOTRRoadType MORDOR;
    public static final LOTRRoadType DORWINION;
    public static final LOTRRoadType RHUN;
    
    private LOTRRoadType() {
    }
    
    public abstract RoadBlock getBlock(final Random p0, final BiomeGenBase p1, final boolean p2, final boolean p3);
    
    public float getRepair() {
        return 1.0f;
    }
    
    public boolean hasFlowers() {
        return false;
    }
    
    public LOTRRoadType setRepair(final float f) {
        final LOTRRoadType baseRoad = this;
        return new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                return baseRoad.getBlock(rand, biome, top, slab);
            }
            
            @Override
            public float getRepair() {
                return f;
            }
            
            @Override
            public boolean hasFlowers() {
                return baseRoad.hasFlowers();
            }
        };
    }
    
    public LOTRRoadType setHasFlowers(final boolean flag) {
        final LOTRRoadType baseRoad = this;
        return new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                return baseRoad.getBlock(rand, biome, top, slab);
            }
            
            @Override
            public float getRepair() {
                return baseRoad.getRepair();
            }
            
            @Override
            public boolean hasFlowers() {
                return flag;
            }
        };
    }
    
    static {
        PATH = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    final float f = rand.nextFloat();
                    if (f < 0.5f) {
                        return new RoadBlock(LOTRMod.slabSingleDirt, 1);
                    }
                    if (f < 0.8f) {
                        return new RoadBlock(LOTRMod.slabSingleDirt, 0);
                    }
                    return new RoadBlock(LOTRMod.slabSingleGravel, 0);
                }
                else {
                    if (!top) {
                        return new RoadBlock(LOTRMod.dirtPath, 0);
                    }
                    final float f = rand.nextFloat();
                    if (f < 0.5f) {
                        return new RoadBlock(LOTRMod.dirtPath, 0);
                    }
                    if (f < 0.8f) {
                        return new RoadBlock(Blocks.dirt, 1);
                    }
                    return new RoadBlock(Blocks.gravel, 0);
                }
            }
        };
        COBBLESTONE = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    return new RoadBlock((Block)Blocks.stone_slab, 3);
                }
                return new RoadBlock(Blocks.cobblestone, 0);
            }
        };
        DIRT = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    return new RoadBlock(LOTRMod.slabSingleDirt, 0);
                }
                return new RoadBlock(Blocks.dirt, 1);
            }
        };
        GALADHRIM = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    return new RoadBlock(LOTRMod.slabSingle2, 3);
                }
                return new RoadBlock(LOTRMod.brick, 11);
            }
        };
        GALADHRIM_RUINED = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    if (rand.nextInt(4) == 0) {
                        return rand.nextBoolean() ? new RoadBlock(LOTRMod.slabSingle2, 4) : new RoadBlock(LOTRMod.slabSingle2, 5);
                    }
                    return new RoadBlock(LOTRMod.slabSingle2, 3);
                }
                else {
                    if (rand.nextInt(4) == 0) {
                        return rand.nextBoolean() ? new RoadBlock(LOTRMod.brick, 12) : new RoadBlock(LOTRMod.brick, 13);
                    }
                    return new RoadBlock(LOTRMod.brick, 11);
                }
            }
        };
        HIGH_ELVEN = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    return new RoadBlock(LOTRMod.slabSingle5, 5);
                }
                return new RoadBlock(LOTRMod.brick3, 2);
            }
        };
        HIGH_ELVEN_RUINED = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    if (rand.nextInt(4) == 0) {
                        return rand.nextBoolean() ? new RoadBlock(LOTRMod.slabSingle5, 6) : new RoadBlock(LOTRMod.slabSingle5, 7);
                    }
                    return new RoadBlock(LOTRMod.slabSingle5, 5);
                }
                else {
                    if (rand.nextInt(4) == 0) {
                        return rand.nextBoolean() ? new RoadBlock(LOTRMod.brick3, 3) : new RoadBlock(LOTRMod.brick3, 4);
                    }
                    return new RoadBlock(LOTRMod.brick3, 2);
                }
            }
        };
        WOOD_ELVEN = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    return new RoadBlock(LOTRMod.slabSingle6, 2);
                }
                return new RoadBlock(LOTRMod.brick3, 5);
            }
        };
        WOOD_ELVEN_RUINED = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    if (rand.nextInt(4) == 0) {
                        return rand.nextBoolean() ? new RoadBlock(LOTRMod.slabSingle6, 3) : new RoadBlock(LOTRMod.slabSingle6, 4);
                    }
                    return new RoadBlock(LOTRMod.slabSingle6, 2);
                }
                else {
                    if (rand.nextInt(4) == 0) {
                        return rand.nextBoolean() ? new RoadBlock(LOTRMod.brick3, 6) : new RoadBlock(LOTRMod.brick3, 7);
                    }
                    return new RoadBlock(LOTRMod.brick3, 5);
                }
            }
        };
        ARNOR = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    if (rand.nextInt(4) == 0) {
                        return rand.nextBoolean() ? new RoadBlock(LOTRMod.slabSingle4, 2) : new RoadBlock(LOTRMod.slabSingle4, 3);
                    }
                    return new RoadBlock(LOTRMod.slabSingle4, 1);
                }
                else {
                    if (rand.nextInt(4) == 0) {
                        return rand.nextBoolean() ? new RoadBlock(LOTRMod.brick2, 4) : new RoadBlock(LOTRMod.brick2, 5);
                    }
                    return new RoadBlock(LOTRMod.brick2, 3);
                }
            }
        };
        GONDOR = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    return new RoadBlock(LOTRMod.slabSingle, 3);
                }
                return new RoadBlock(LOTRMod.brick, 1);
            }
        };
        GONDOR_RUINED = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    if (rand.nextInt(8) == 0) {
                        return new RoadBlock(LOTRMod.slabSingle, 2);
                    }
                    if (rand.nextInt(8) == 0) {
                        return rand.nextBoolean() ? new RoadBlock(LOTRMod.slabSingle, 4) : new RoadBlock(LOTRMod.slabSingle, 5);
                    }
                    return new RoadBlock(LOTRMod.slabSingle, 3);
                }
                else {
                    if (rand.nextInt(8) == 0) {
                        return new RoadBlock(LOTRMod.slabDouble, 2);
                    }
                    if (rand.nextInt(8) == 0) {
                        return rand.nextBoolean() ? new RoadBlock(LOTRMod.brick, 2) : new RoadBlock(LOTRMod.brick, 3);
                    }
                    return new RoadBlock(LOTRMod.brick, 1);
                }
            }
        };
        DOL_AMROTH = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    return new RoadBlock(LOTRMod.slabSingle6, 7);
                }
                return new RoadBlock(LOTRMod.brick3, 9);
            }
        };
        ROHAN = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    return new RoadBlock(LOTRMod.slabSingle, 6);
                }
                return new RoadBlock(LOTRMod.brick, 4);
            }
        };
        DWARVEN = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    return new RoadBlock(LOTRMod.slabSingle, 7);
                }
                return new RoadBlock(LOTRMod.brick, 6);
            }
        };
        DALE = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    return new RoadBlock(LOTRMod.slabSingle9, 6);
                }
                return new RoadBlock(LOTRMod.brick5, 1);
            }
        };
        HARAD = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    return new RoadBlock(LOTRMod.slabSingle4, 0);
                }
                return new RoadBlock(LOTRMod.brick, 15);
            }
        };
        HARAD_PATH = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    final float f = rand.nextFloat();
                    if (f < 0.33f) {
                        if (rand.nextInt(4) == 0) {
                            return new RoadBlock(LOTRMod.slabSingle7, 1);
                        }
                        return new RoadBlock(LOTRMod.slabSingle4, 0);
                    }
                    else {
                        if (f < 0.67f) {
                            return new RoadBlock(LOTRMod.slabSingleSand, 0);
                        }
                        return new RoadBlock(LOTRMod.slabSingleDirt, 1);
                    }
                }
                else {
                    final float f = rand.nextFloat();
                    if (f < 0.33f) {
                        if (rand.nextInt(4) == 0) {
                            return new RoadBlock(LOTRMod.brick3, 11);
                        }
                        return new RoadBlock(LOTRMod.brick, 15);
                    }
                    else {
                        if (f < 0.67f) {
                            return top ? new RoadBlock((Block)Blocks.sand, 0) : new RoadBlock(Blocks.sandstone, 0);
                        }
                        return new RoadBlock(LOTRMod.dirtPath, 0);
                    }
                }
            }
        };
        HARAD_TOWN = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    final float f = rand.nextFloat();
                    if (f < 0.17f) {
                        return new RoadBlock(LOTRMod.slabSingleDirt, 0);
                    }
                    if (f < 0.33f) {
                        return new RoadBlock(LOTRMod.slabSingleDirt, 1);
                    }
                    if (f < 0.5f) {
                        return new RoadBlock(LOTRMod.slabSingleSand, 0);
                    }
                    if (f < 0.67f) {
                        return new RoadBlock(LOTRMod.slabSingle4, 0);
                    }
                    if (f < 0.83f) {
                        return new RoadBlock(LOTRMod.slabSingle7, 1);
                    }
                    return new RoadBlock(LOTRMod.slabSingle4, 7);
                }
                else {
                    final float f = rand.nextFloat();
                    if (f < 0.17f) {
                        return new RoadBlock(Blocks.dirt, 1);
                    }
                    if (f < 0.33f) {
                        return new RoadBlock(LOTRMod.dirtPath, 0);
                    }
                    if (f < 0.5f) {
                        return top ? new RoadBlock((Block)Blocks.sand, 0) : new RoadBlock(Blocks.sandstone, 0);
                    }
                    if (f < 0.67f) {
                        return new RoadBlock(LOTRMod.brick, 15);
                    }
                    if (f < 0.83f) {
                        return new RoadBlock(LOTRMod.brick3, 11);
                    }
                    return new RoadBlock(LOTRMod.pillar, 5);
                }
            }
        };
        UMBAR = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    return new RoadBlock(LOTRMod.slabSingle13, 2);
                }
                return new RoadBlock(LOTRMod.brick6, 6);
            }
        };
        GULF_HARAD = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    final float f = rand.nextFloat();
                    if (f < 0.25f) {
                        return new RoadBlock(LOTRMod.slabSingleDirt, 0);
                    }
                    if (f < 0.5f) {
                        return new RoadBlock(LOTRMod.slabSingleSand, 1);
                    }
                    if (f < 0.75f) {
                        return new RoadBlock(LOTRMod.slabSingle7, 2);
                    }
                    return new RoadBlock(LOTRMod.slabSingle7, 3);
                }
                else {
                    final float f = rand.nextFloat();
                    if (f < 0.25f) {
                        return new RoadBlock(Blocks.dirt, 1);
                    }
                    if (f < 0.5f) {
                        return top ? new RoadBlock((Block)Blocks.sand, 1) : new RoadBlock(LOTRMod.redSandstone, 0);
                    }
                    if (f < 0.75f) {
                        return new RoadBlock(LOTRMod.brick3, 13);
                    }
                    return new RoadBlock(LOTRMod.brick3, 14);
                }
            }
        };
        TAUREDAIN = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    if (rand.nextInt(4) != 0) {
                        return new RoadBlock(LOTRMod.slabSingle8, 0);
                    }
                    if (rand.nextBoolean()) {
                        return new RoadBlock(LOTRMod.slabSingle8, 1);
                    }
                    return new RoadBlock(LOTRMod.slabSingle8, 2);
                }
                else {
                    if (rand.nextInt(4) != 0) {
                        return new RoadBlock(LOTRMod.brick4, 0);
                    }
                    if (rand.nextBoolean()) {
                        return new RoadBlock(LOTRMod.brick4, 1);
                    }
                    return new RoadBlock(LOTRMod.brick4, 2);
                }
            }
        };
        MORDOR = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    return new RoadBlock(LOTRMod.slabSingleDirt, 3);
                }
                return new RoadBlock(LOTRMod.mordorDirt, 0);
            }
        };
        DORWINION = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    return new RoadBlock(LOTRMod.slabSingle9, 7);
                }
                return new RoadBlock(LOTRMod.brick5, 2);
            }
        };
        RHUN = new LOTRRoadType() {
            @Override
            public RoadBlock getBlock(final Random rand, final BiomeGenBase biome, final boolean top, final boolean slab) {
                if (slab) {
                    if (rand.nextInt(8) == 0) {
                        return rand.nextBoolean() ? new RoadBlock(LOTRMod.slabSingle12, 1) : new RoadBlock(LOTRMod.slabSingle12, 2);
                    }
                    return new RoadBlock(LOTRMod.slabSingle12, 0);
                }
                else {
                    if (rand.nextInt(8) == 0) {
                        return rand.nextBoolean() ? new RoadBlock(LOTRMod.brick5, 13) : new RoadBlock(LOTRMod.brick5, 14);
                    }
                    return new RoadBlock(LOTRMod.brick5, 11);
                }
            }
        };
    }
    
    public abstract static class BridgeType
    {
        public static final BridgeType DEFAULT;
        public static final BridgeType MIRKWOOD;
        public static final BridgeType CHARRED;
        
        private BridgeType() {
        }
        
        public abstract RoadBlock getBlock(final Random p0, final boolean p1);
        
        public abstract RoadBlock getEdge(final Random p0);
        
        public abstract RoadBlock getFence(final Random p0);
        
        static {
            DEFAULT = new BridgeType() {
                @Override
                public RoadBlock getBlock(final Random rand, final boolean slab) {
                    if (slab) {
                        return new RoadBlock((Block)Blocks.wooden_slab, 0);
                    }
                    return new RoadBlock(Blocks.planks, 0);
                }
                
                @Override
                public RoadBlock getEdge(final Random rand) {
                    return new RoadBlock(LOTRMod.woodBeamV1, 0);
                }
                
                @Override
                public RoadBlock getFence(final Random rand) {
                    return new RoadBlock(Blocks.fence, 0);
                }
            };
            MIRKWOOD = new BridgeType() {
                @Override
                public RoadBlock getBlock(final Random rand, final boolean slab) {
                    if (slab) {
                        return new RoadBlock(LOTRMod.woodSlabSingle, 2);
                    }
                    return new RoadBlock(LOTRMod.planks, 2);
                }
                
                @Override
                public RoadBlock getEdge(final Random rand) {
                    return new RoadBlock(LOTRMod.woodBeam1, 2);
                }
                
                @Override
                public RoadBlock getFence(final Random rand) {
                    return new RoadBlock(LOTRMod.fence, 2);
                }
            };
            CHARRED = new BridgeType() {
                @Override
                public RoadBlock getBlock(final Random rand, final boolean slab) {
                    if (slab) {
                        return new RoadBlock(LOTRMod.woodSlabSingle, 3);
                    }
                    return new RoadBlock(LOTRMod.planks, 3);
                }
                
                @Override
                public RoadBlock getEdge(final Random rand) {
                    return new RoadBlock(LOTRMod.woodBeam1, 3);
                }
                
                @Override
                public RoadBlock getFence(final Random rand) {
                    return new RoadBlock(LOTRMod.fence, 3);
                }
            };
        }
    }
    
    public static class RoadBlock
    {
        public Block block;
        public final int meta;
        
        public RoadBlock(final Block b, final int i) {
            this.block = b;
            this.meta = i;
        }
    }
}

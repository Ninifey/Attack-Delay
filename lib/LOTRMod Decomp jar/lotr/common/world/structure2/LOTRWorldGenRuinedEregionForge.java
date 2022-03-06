// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.entity.npc.LOTREntityElf;
import net.minecraft.world.World;
import lotr.common.LOTRMod;

public class LOTRWorldGenRuinedEregionForge extends LOTRWorldGenHighElvenForge
{
    public LOTRWorldGenRuinedEregionForge(final boolean flag) {
        super(flag);
        super.ruined = true;
        super.roofBlock = LOTRMod.clayTileDyed;
        super.roofMeta = 8;
        super.roofStairBlock = LOTRMod.stairsClayTileDyedLightGray;
    }
    
    @Override
    protected LOTREntityElf getElf(final World world) {
        return null;
    }
    
    @Override
    protected void placeBrick(final World world, final int i, final int j, final int k, final Random random) {
        if (random.nextInt(12) == 0) {
            return;
        }
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
    protected void placePillar(final World world, final int i, final int j, final int k, final Random random) {
        if (random.nextInt(12) == 0) {
            return;
        }
        if (random.nextInt(3) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.pillar, 11);
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.pillar, 10);
        }
    }
    
    @Override
    protected void placeSlab(final World world, final int i, final int j, final int k, final boolean flag, final Random random) {
        if (random.nextInt(12) == 0) {
            return;
        }
        final int l = random.nextInt(3);
        switch (l) {
            case 0: {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle5, 0x5 | (flag ? 8 : 0));
                break;
            }
            case 1: {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle5, 0x6 | (flag ? 8 : 0));
                break;
            }
            case 2: {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle5, 0x7 | (flag ? 8 : 0));
                break;
            }
        }
    }
    
    @Override
    protected void placeWall(final World world, final int i, final int j, final int k, final Random random) {
        if (random.nextInt(12) == 0) {
            return;
        }
        final int l = random.nextInt(3);
        switch (l) {
            case 0: {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.wall2, 11);
                break;
            }
            case 1: {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.wall2, 12);
                break;
            }
            case 2: {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.wall2, 13);
                break;
            }
        }
    }
    
    @Override
    protected void placeStairs(final World world, final int i, final int j, final int k, final int meta, final Random random) {
        if (random.nextInt(12) == 0) {
            return;
        }
        final int l = random.nextInt(3);
        switch (l) {
            case 0: {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsHighElvenBrick, meta);
                break;
            }
            case 1: {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsHighElvenBrickMossy, meta);
                break;
            }
            case 2: {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsHighElvenBrickCracked, meta);
                break;
            }
        }
    }
    
    @Override
    protected void placeRoof(final World world, final int i, final int j, final int k, final Random random) {
        if (random.nextInt(12) == 0) {
            return;
        }
        super.placeRoof(world, i, j, k, random);
    }
    
    @Override
    protected void placeRoofStairs(final World world, final int i, final int j, final int k, final int meta, final Random random) {
        if (random.nextInt(12) == 0) {
            return;
        }
        super.placeRoofStairs(world, i, j, k, meta, random);
    }
}

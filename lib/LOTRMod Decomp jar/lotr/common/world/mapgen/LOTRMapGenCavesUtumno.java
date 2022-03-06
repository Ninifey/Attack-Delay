// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.mapgen;

import net.minecraft.init.Blocks;
import lotr.common.world.LOTRUtumnoLevel;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;

public class LOTRMapGenCavesUtumno extends LOTRMapGenCaves
{
    @Override
    protected int caveRarity() {
        return 3;
    }
    
    @Override
    protected int getCaveGenerationHeight() {
        return super.rand.nextInt(super.rand.nextInt(240) + 8);
    }
    
    @Override
    protected void digBlock(final Block[] blockArray, final int index, final int xzIndex, final int i, final int j, final int k, final int chunkX, final int chunkZ, final LOTRBiome biome, final boolean foundTop) {
        if (j < LOTRUtumnoLevel.forY(0).getLowestCorridorFloor() || j > LOTRUtumnoLevel.forY(255).getHighestCorridorRoof()) {
            return;
        }
        for (int l = 0; l < LOTRUtumnoLevel.values().length - 1; ++l) {
            final LOTRUtumnoLevel levelUpper = LOTRUtumnoLevel.values()[l];
            final LOTRUtumnoLevel levelLower = LOTRUtumnoLevel.values()[l + 1];
            if (j > levelLower.getHighestCorridorRoof() && j < levelUpper.getLowestCorridorFloor()) {
                return;
            }
        }
        blockArray[index] = Blocks.air;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.block.Block;

public class LOTRBiomeGenLake extends LOTRBiome
{
    public LOTRBiomeGenLake(final int i, final boolean major) {
        super(i, major);
        this.setMinMaxHeight(-0.5f, 0.2f);
        super.spawnableCreatureList.clear();
        super.spawnableLOTRAmbientList.clear();
        super.npcSpawnList.clear();
        super.decorator.sandPerChunk = 0;
    }
    
    public LOTRBiomeGenLake setLakeBlock(final Block block) {
        super.topBlock = block;
        super.fillerBlock = block;
        return this;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.SEA.getSubregion("lake");
    }
    
    @Override
    public boolean getEnableRiver() {
        return false;
    }
}

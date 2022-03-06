// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntitySeagull;

public class LOTRBiomeGenBeach extends LOTRBiomeGenOcean
{
    public LOTRBiomeGenBeach(final int i, final boolean major) {
        super(i, major);
        this.setMinMaxHeight(0.1f, 0.0f);
        this.setTemperatureRainfall(0.8f, 0.4f);
        super.spawnableCreatureList.clear();
        super.spawnableWaterCreatureList.clear();
        super.spawnableLOTRAmbientList.clear();
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntitySeagull.class, 20, 4, 4));
    }
    
    public LOTRBiomeGenBeach setBeachBlock(final Block block, final int meta) {
        super.topBlock = block;
        super.topBlockMeta = meta;
        super.fillerBlock = block;
        super.fillerBlockMeta = meta;
        return this;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.passive.EntityWolf;

public class LOTRBiomeGenLoneLandsHills extends LOTRBiomeGenLoneLands
{
    public LOTRBiomeGenLoneLandsHills(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityWolf.class, 10, 4, 8));
        this.clearBiomeVariants();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
        super.decorator.treesPerChunk = 1;
    }
}

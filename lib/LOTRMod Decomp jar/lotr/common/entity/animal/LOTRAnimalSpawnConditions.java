// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.biome.LOTRBiome;

public interface LOTRAnimalSpawnConditions
{
    boolean canWorldGenSpawnAt(final int p0, final int p1, final int p2, final LOTRBiome p3, final LOTRBiomeVariant p4);
}

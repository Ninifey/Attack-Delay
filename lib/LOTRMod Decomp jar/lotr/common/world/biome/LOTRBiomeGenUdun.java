// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.structure2.LOTRWorldGenMordorWargPit;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.structure2.LOTRWorldGenMordorCamp;

public class LOTRBiomeGenUdun extends LOTRBiomeGenMordor
{
    public LOTRBiomeGenUdun(final int i, final boolean major) {
        super(i, major);
        super.biomeColors.setSky(6837327);
        super.biomeColors.setClouds(4797229);
        super.biomeColors.setFog(4996410);
        super.decorator.addRandomStructure(new LOTRWorldGenMordorCamp(false), 20);
        super.decorator.addRandomStructure(new LOTRWorldGenMordorWargPit(false), 100);
    }
}

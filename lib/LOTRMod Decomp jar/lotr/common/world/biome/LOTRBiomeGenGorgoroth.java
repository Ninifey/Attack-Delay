// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

public class LOTRBiomeGenGorgoroth extends LOTRBiomeGenMordor
{
    public LOTRBiomeGenGorgoroth(final int i, final boolean major) {
        super(i, major);
        super.enableMordorBoulders = false;
        super.decorator.grassPerChunk = 0;
        super.biomeColors.setSky(5843484);
    }
}

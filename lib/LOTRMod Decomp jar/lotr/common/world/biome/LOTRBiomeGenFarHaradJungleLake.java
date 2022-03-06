// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

public class LOTRBiomeGenFarHaradJungleLake extends LOTRBiomeGenFarHaradJungle
{
    public LOTRBiomeGenFarHaradJungleLake(final int i, final boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        super.decorator.sandPerChunk = 0;
    }
    
    @Override
    public boolean getEnableRiver() {
        return false;
    }
}

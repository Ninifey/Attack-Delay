// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

public class LOTRBiomeGenNurnen extends LOTRBiomeGenNurn
{
    public LOTRBiomeGenNurnen(final int i, final boolean major) {
        super(i, major);
        super.npcSpawnList.clear();
        this.clearBiomeVariants();
    }
    
    @Override
    public boolean getEnableRiver() {
        return false;
    }
}

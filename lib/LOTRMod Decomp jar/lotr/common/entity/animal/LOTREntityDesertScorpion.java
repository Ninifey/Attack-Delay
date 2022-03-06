// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import lotr.common.world.biome.LOTRBiomeGenNearHarad;

public class LOTREntityDesertScorpion extends LOTREntityScorpion implements LOTRBiomeGenNearHarad.ImmuneToHeat
{
    public boolean pyramidSpawned;
    
    public LOTREntityDesertScorpion(final World world) {
        super(world);
        this.pyramidSpawned = false;
        ((Entity)this).isImmuneToFire = true;
    }
    
    @Override
    protected int getRandomScorpionScale() {
        return ((Entity)this).rand.nextInt(2);
    }
    
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && (super.spawningFromSpawner || this.pyramidSpawned || ((Entity)this).posY < 60.0 || ((Entity)this).rand.nextInt(500) == 0);
    }
    
    public boolean isValidLightLevel() {
        return super.spawningFromSpawner || this.pyramidSpawned || super.isValidLightLevel();
    }
}

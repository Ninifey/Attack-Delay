// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTREntityJungleScorpion extends LOTREntityScorpion
{
    public LOTREntityJungleScorpion(final World world) {
        super(world);
    }
    
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && (super.spawningFromSpawner || ((Entity)this).posY < 60.0 || ((Entity)this).rand.nextInt(100) == 0);
    }
}

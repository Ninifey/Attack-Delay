// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.Entity;
import lotr.common.entity.LOTREntities;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.entity.passive.EntityAnimal;

public abstract class LOTREntityAnimalMF extends EntityAnimal
{
    public LOTREntityAnimalMF(final World world) {
        super(world);
    }
    
    public abstract Class getAnimalMFBaseClass();
    
    public abstract boolean isMale();
    
    public boolean canMateWith(final EntityAnimal mate) {
        final LOTREntityAnimalMF mfMate = (LOTREntityAnimalMF)mate;
        if (mate == this) {
            return false;
        }
        if (this.getAnimalMFBaseClass().equals(mfMate.getAnimalMFBaseClass()) && this.isInLove() && mate.isInLove()) {
            final boolean thisMale = this.isMale();
            final boolean otherMale = mfMate.isMale();
            return thisMale != otherMale;
        }
        return false;
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID((Entity)this));
    }
}

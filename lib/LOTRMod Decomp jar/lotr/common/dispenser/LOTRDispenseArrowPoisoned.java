// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.dispenser;

import net.minecraft.entity.projectile.EntityArrow;
import lotr.common.entity.item.LOTREntityArrowPoisoned;
import net.minecraft.entity.IProjectile;
import net.minecraft.dispenser.IPosition;
import net.minecraft.world.World;
import net.minecraft.dispenser.BehaviorProjectileDispense;

public class LOTRDispenseArrowPoisoned extends BehaviorProjectileDispense
{
    protected IProjectile getProjectileEntity(final World world, final IPosition iposition) {
        final EntityArrow arrow = new LOTREntityArrowPoisoned(world, iposition.getX(), iposition.getY(), iposition.getZ());
        arrow.canBePickedUp = 1;
        return (IProjectile)arrow;
    }
}

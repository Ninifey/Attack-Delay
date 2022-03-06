// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityPebble;
import net.minecraft.entity.IProjectile;
import net.minecraft.dispenser.IPosition;
import net.minecraft.world.World;
import net.minecraft.dispenser.BehaviorProjectileDispense;

public class LOTRDispensePebble extends BehaviorProjectileDispense
{
    protected IProjectile getProjectileEntity(final World world, final IPosition position) {
        return (IProjectile)new LOTREntityPebble(world, position.getX(), position.getY(), position.getZ());
    }
}

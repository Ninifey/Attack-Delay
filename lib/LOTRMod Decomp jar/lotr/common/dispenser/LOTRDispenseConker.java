// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityConker;
import net.minecraft.entity.IProjectile;
import net.minecraft.dispenser.IPosition;
import net.minecraft.world.World;
import net.minecraft.dispenser.BehaviorProjectileDispense;

public class LOTRDispenseConker extends BehaviorProjectileDispense
{
    protected IProjectile getProjectileEntity(final World world, final IPosition position) {
        return (IProjectile)new LOTREntityConker(world, position.getX(), position.getY(), position.getZ());
    }
}

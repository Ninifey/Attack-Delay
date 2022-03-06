// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityThrownTermite;
import net.minecraft.entity.IProjectile;
import net.minecraft.dispenser.IPosition;
import net.minecraft.world.World;
import net.minecraft.dispenser.BehaviorProjectileDispense;

public class LOTRDispenseTermite extends BehaviorProjectileDispense
{
    protected IProjectile getProjectileEntity(final World world, final IPosition position) {
        return (IProjectile)new LOTREntityThrownTermite(world, position.getX(), position.getY(), position.getZ());
    }
}

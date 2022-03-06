// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityMysteryWeb;
import net.minecraft.entity.IProjectile;
import net.minecraft.dispenser.IPosition;
import net.minecraft.world.World;
import net.minecraft.dispenser.BehaviorProjectileDispense;

public class LOTRDispenseMysteryWeb extends BehaviorProjectileDispense
{
    protected IProjectile getProjectileEntity(final World world, final IPosition position) {
        return (IProjectile)new LOTREntityMysteryWeb(world, position.getX(), position.getY(), position.getZ());
    }
}

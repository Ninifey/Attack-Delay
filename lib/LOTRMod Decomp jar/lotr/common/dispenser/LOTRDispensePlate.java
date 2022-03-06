// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityPlate;
import net.minecraft.entity.IProjectile;
import net.minecraft.dispenser.IPosition;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.dispenser.BehaviorProjectileDispense;

public class LOTRDispensePlate extends BehaviorProjectileDispense
{
    private Block plateBlock;
    
    public LOTRDispensePlate(final Block block) {
        this.plateBlock = block;
    }
    
    protected IProjectile getProjectileEntity(final World world, final IPosition position) {
        return (IProjectile)new LOTREntityPlate(world, this.plateBlock, position.getX(), position.getY(), position.getZ());
    }
}

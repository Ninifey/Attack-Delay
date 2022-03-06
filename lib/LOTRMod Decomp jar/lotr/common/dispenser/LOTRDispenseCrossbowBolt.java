// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.dispenser;

import net.minecraft.dispenser.IBlockSource;
import lotr.common.entity.projectile.LOTREntityCrossbowBolt;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.IProjectile;
import net.minecraft.dispenser.IPosition;
import net.minecraft.world.World;
import net.minecraft.item.Item;
import net.minecraft.dispenser.BehaviorProjectileDispense;

public class LOTRDispenseCrossbowBolt extends BehaviorProjectileDispense
{
    private Item theBoltItem;
    
    public LOTRDispenseCrossbowBolt(final Item item) {
        this.theBoltItem = item;
    }
    
    protected IProjectile getProjectileEntity(final World world, final IPosition iposition) {
        final ItemStack itemstack = new ItemStack(this.theBoltItem);
        final LOTREntityCrossbowBolt bolt = new LOTREntityCrossbowBolt(world, itemstack, iposition.getX(), iposition.getY(), iposition.getZ());
        bolt.canBePickedUp = 1;
        return (IProjectile)bolt;
    }
    
    protected void playDispenseSound(final IBlockSource source) {
        source.getWorld().playSoundEffect((double)source.getXInt(), (double)source.getYInt(), (double)source.getZInt(), "lotr:item.crossbow", 1.0f, 1.2f);
    }
}

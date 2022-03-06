// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.dispenser;

import net.minecraft.dispenser.IBlockSource;
import lotr.common.entity.projectile.LOTREntityDart;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.IProjectile;
import net.minecraft.dispenser.IPosition;
import net.minecraft.world.World;
import lotr.common.item.LOTRItemDart;
import net.minecraft.dispenser.BehaviorProjectileDispense;

public class LOTRDispenseDart extends BehaviorProjectileDispense
{
    private LOTRItemDart theDartItem;
    
    public LOTRDispenseDart(final LOTRItemDart item) {
        this.theDartItem = item;
    }
    
    protected IProjectile getProjectileEntity(final World world, final IPosition iposition) {
        final ItemStack itemstack = new ItemStack((Item)this.theDartItem);
        final LOTREntityDart dart = this.theDartItem.createDart(world, itemstack, iposition.getX(), iposition.getY(), iposition.getZ());
        dart.canBePickedUp = 1;
        return (IProjectile)dart;
    }
    
    protected float func_82500_b() {
        return 1.5f;
    }
    
    protected void playDispenseSound(final IBlockSource source) {
        source.getWorld().playSoundEffect((double)source.getXInt(), (double)source.getYInt(), (double)source.getZInt(), "lotr:item.dart", 1.0f, 1.2f);
    }
}

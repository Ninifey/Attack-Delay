// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.dispenser;

import net.minecraft.util.EnumFacing;
import net.minecraft.dispenser.IPosition;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import lotr.common.entity.projectile.LOTREntitySpear;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.ItemStack;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;

public class LOTRDispenseSpear extends BehaviorDefaultDispenseItem
{
    public ItemStack dispenseStack(final IBlockSource dispenser, final ItemStack itemstack) {
        final World world = dispenser.getWorld();
        final IPosition iposition = BlockDispenser.func_149939_a(dispenser);
        final EnumFacing enumfacing = BlockDispenser.func_149937_b(dispenser.getBlockMetadata());
        final LOTREntitySpear spear = new LOTREntitySpear(world, itemstack.copy(), iposition.getX(), iposition.getY(), iposition.getZ());
        spear.setThrowableHeading(enumfacing.getFrontOffsetX(), enumfacing.getFrontOffsetY() + 0.1f, enumfacing.getFrontOffsetZ(), 1.1f, 6.0f);
        spear.canBePickedUp = 1;
        world.spawnEntityInWorld((Entity)spear);
        itemstack.splitStack(1);
        return itemstack;
    }
    
    protected void playDispenseSound(final IBlockSource dispenser) {
        dispenser.getWorld().playAuxSFX(1002, dispenser.getXInt(), dispenser.getYInt(), dispenser.getZInt(), 0);
    }
}

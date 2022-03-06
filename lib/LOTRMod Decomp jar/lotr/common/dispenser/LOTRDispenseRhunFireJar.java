// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.dispenser;

import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.Block;
import lotr.common.block.LOTRBlockRhunFireJar;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.ItemStack;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;

public class LOTRDispenseRhunFireJar extends BehaviorDefaultDispenseItem
{
    private final BehaviorDefaultDispenseItem dispenseDefault;
    
    public LOTRDispenseRhunFireJar() {
        this.dispenseDefault = new BehaviorDefaultDispenseItem();
    }
    
    protected ItemStack dispenseStack(final IBlockSource dispenser, final ItemStack itemstack) {
        final EnumFacing enumfacing = BlockDispenser.func_149937_b(dispenser.getBlockMetadata());
        final World world = dispenser.getWorld();
        final int i = dispenser.getXInt() + enumfacing.getFrontOffsetX();
        final int j = dispenser.getYInt() + enumfacing.getFrontOffsetY();
        final int k = dispenser.getZInt() + enumfacing.getFrontOffsetZ();
        if (world.getBlock(i, j, k).isReplaceable((IBlockAccess)world, i, j, k)) {
            LOTRBlockRhunFireJar.explodeOnAdded = false;
            world.setBlock(i, j, k, Block.getBlockFromItem(itemstack.getItem()), itemstack.getItemDamage(), 3);
            LOTRBlockRhunFireJar.explodeOnAdded = true;
            --itemstack.stackSize;
            return itemstack;
        }
        return this.dispenseDefault.dispense(dispenser, itemstack);
    }
}

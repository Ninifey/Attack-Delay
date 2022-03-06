// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.dispenser;

import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.item.LOTREntityOrcBomb;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.ItemStack;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;

public class LOTRDispenseOrcBomb extends BehaviorDefaultDispenseItem
{
    protected ItemStack dispenseStack(final IBlockSource dispenser, final ItemStack itemstack) {
        final EnumFacing enumfacing = BlockDispenser.func_149937_b(dispenser.getBlockMetadata());
        final World world = dispenser.getWorld();
        final int i = dispenser.getXInt() + enumfacing.getFrontOffsetX();
        final int j = dispenser.getYInt() + enumfacing.getFrontOffsetY();
        final int k = dispenser.getZInt() + enumfacing.getFrontOffsetZ();
        final LOTREntityOrcBomb lotrEntityOrcBomb;
        final LOTREntityOrcBomb bomb = lotrEntityOrcBomb = new LOTREntityOrcBomb(world, i + 0.5f, j + 0.5f, k + 0.5f, null);
        lotrEntityOrcBomb.fuse += itemstack.getItemDamage() * 10;
        bomb.setBombStrengthLevel(itemstack.getItemDamage());
        bomb.droppedByPlayer = true;
        world.spawnEntityInWorld((Entity)bomb);
        --itemstack.stackSize;
        return itemstack;
    }
}

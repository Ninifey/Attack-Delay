// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.dispenser;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityLiving;
import lotr.common.item.LOTRItemSpawnEgg;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.ItemStack;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;

public class LOTRDispenseSpawnEgg extends BehaviorDefaultDispenseItem
{
    public ItemStack dispenseStack(final IBlockSource dispenser, final ItemStack itemstack) {
        final EnumFacing enumfacing = BlockDispenser.func_149937_b(dispenser.getBlockMetadata());
        final double d = dispenser.getX() + enumfacing.getFrontOffsetX();
        final double d2 = dispenser.getYInt() + 0.2;
        final double d3 = dispenser.getZ() + enumfacing.getFrontOffsetZ();
        final Entity entity = LOTRItemSpawnEgg.spawnCreature(dispenser.getWorld(), itemstack.getItemDamage(), d, d2, d3);
        if (entity instanceof EntityLiving && itemstack.hasDisplayName()) {
            ((EntityLiving)entity).setCustomNameTag(itemstack.getDisplayName());
        }
        if (entity instanceof LOTREntityNPC) {
            ((LOTREntityNPC)entity).isNPCPersistent = true;
        }
        itemstack.splitStack(1);
        return itemstack;
    }
}

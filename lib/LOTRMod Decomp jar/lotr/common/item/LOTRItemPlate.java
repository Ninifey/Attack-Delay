// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.projectile.LOTREntityPlate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import lotr.common.dispenser.LOTRDispensePlate;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;
import lotr.common.block.LOTRBlockPlate;
import net.minecraft.block.Block;
import net.minecraft.item.ItemReed;

public class LOTRItemPlate extends ItemReed
{
    public Block plateBlock;
    
    public LOTRItemPlate(final Block block) {
        super(block);
        this.plateBlock = block;
        ((LOTRBlockPlate)this.plateBlock).setPlateItem((Item)this);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabFood);
        BlockDispenser.dispenseBehaviorRegistry.putObject((Object)this, (Object)new LOTRDispensePlate(this.plateBlock));
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        final LOTREntityPlate plate = new LOTREntityPlate(world, this.plateBlock, (EntityLivingBase)entityplayer);
        world.playSoundAtEntity((Entity)entityplayer, "random.bow", 1.0f, 1.0f / (Item.itemRand.nextFloat() * 0.4f + 1.2f) + 0.25f);
        if (!world.isClient) {
            world.spawnEntityInWorld((Entity)plate);
        }
        if (!entityplayer.capabilities.isCreativeMode) {
            --itemstack.stackSize;
        }
        return itemstack;
    }
    
    public boolean isValidArmor(final ItemStack itemstack, final int armorType, final Entity entity) {
        return armorType == 0;
    }
}

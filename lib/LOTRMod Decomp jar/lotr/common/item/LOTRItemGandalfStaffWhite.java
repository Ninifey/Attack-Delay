// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.item.EnumAction;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketWeaponFX;
import lotr.common.entity.projectile.LOTREntityGandalfFireball;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;

public class LOTRItemGandalfStaffWhite extends LOTRItemSword implements LOTRStoryItem
{
    public LOTRItemGandalfStaffWhite() {
        super(LOTRMaterial.HIGH_ELVEN);
        this.setMaxDamage(1500);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabStory);
        super.lotrWeaponDamage = 8.0f;
    }
    
    public ItemStack onEaten(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        entityplayer.swingItem();
        itemstack.damageItem(2, (EntityLivingBase)entityplayer);
        world.playSoundAtEntity((Entity)entityplayer, "mob.ghast.fireball", 2.0f, (Item.itemRand.nextFloat() - Item.itemRand.nextFloat()) * 0.2f + 1.0f);
        if (!world.isClient) {
            world.spawnEntityInWorld((Entity)new LOTREntityGandalfFireball(world, (EntityLivingBase)entityplayer));
            final LOTRPacketWeaponFX packet = new LOTRPacketWeaponFX(LOTRPacketWeaponFX.Type.STAFF_GANDALF_WHITE, (Entity)entityplayer);
            LOTRPacketHandler.networkWrapper.sendToAllAround((IMessage)packet, LOTRPacketHandler.nearEntity((Entity)entityplayer, 64.0));
        }
        return itemstack;
    }
    
    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return 40;
    }
    
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.bow;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        return itemstack;
    }
}

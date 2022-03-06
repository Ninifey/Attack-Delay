// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.EnumAction;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.projectile.LOTREntitySpear;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import lotr.common.dispenser.LOTRDispenseSpear;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;

public class LOTRItemSpear extends LOTRItemSword
{
    public LOTRItemSpear(final LOTRMaterial material) {
        this(material.toToolMaterial());
    }
    
    public LOTRItemSpear(final Item.ToolMaterial material) {
        super(material);
        --super.lotrWeaponDamage;
        BlockDispenser.dispenseBehaviorRegistry.putObject((Object)this, (Object)new LOTRDispenseSpear());
    }
    
    public void onPlayerStoppedUsing(final ItemStack itemstack, final World world, final EntityPlayer entityplayer, final int i) {
        if (entityplayer.getHeldItem() != itemstack) {
            return;
        }
        final int useTick = this.getMaxItemUseDuration(itemstack) - i;
        float charge = useTick / (float)this.getMaxDrawTime();
        if (charge < 0.1f) {
            return;
        }
        charge = (charge * charge + charge * 2.0f) / 3.0f;
        charge = Math.min(charge, 1.0f);
        final LOTREntitySpear spear = new LOTREntitySpear(world, (EntityLivingBase)entityplayer, itemstack.copy(), charge * 2.0f);
        if (charge >= 1.0f) {
            spear.setIsCritical(true);
        }
        final int fireAspect = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) + LOTREnchantmentHelper.calcFireAspect(itemstack);
        if (fireAspect > 0) {
            spear.setFire(100);
        }
        for (final LOTREnchantment ench : LOTREnchantment.allEnchantments) {
            if (ench.applyToProjectile() && LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
                LOTREnchantmentHelper.setProjectileEnchantment(spear, ench);
            }
        }
        if (entityplayer.capabilities.isCreativeMode) {
            spear.canBePickedUp = 2;
        }
        world.playSoundAtEntity((Entity)entityplayer, "random.bow", 1.0f, 1.0f / (Item.itemRand.nextFloat() * 0.4f + 1.2f) + charge * 0.5f);
        if (!world.isClient) {
            world.spawnEntityInWorld((Entity)spear);
        }
        if (!entityplayer.capabilities.isCreativeMode) {
            --itemstack.stackSize;
            if (itemstack.stackSize <= 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, (ItemStack)null);
            }
        }
    }
    
    public int getMaxDrawTime() {
        return 20;
    }
    
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.bow;
    }
    
    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return 72000;
    }
    
    public float getRangedDamageMultiplier(final ItemStack itemstack, final Entity shooter, final Entity hit) {
        float damage = this.getLOTRWeaponDamage();
        if (shooter instanceof EntityLivingBase && hit instanceof EntityLivingBase) {
            damage += EnchantmentHelper.getEnchantmentModifierLiving((EntityLivingBase)shooter, (EntityLivingBase)hit);
        }
        else {
            damage += EnchantmentHelper.func_152377_a(itemstack, EnumCreatureAttribute.UNDEFINED);
        }
        return damage * 0.7f;
    }
}

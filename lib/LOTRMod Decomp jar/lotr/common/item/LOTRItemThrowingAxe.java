// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.entity.EnumCreatureAttribute;
import lotr.common.recipe.LOTRRecipes;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import lotr.common.dispenser.LOTRDispenseThrowingAxe;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemThrowingAxe extends Item
{
    private Item.ToolMaterial axeMaterial;
    
    public LOTRItemThrowingAxe(final LOTRMaterial material) {
        this(material.toToolMaterial());
    }
    
    public LOTRItemThrowingAxe(final Item.ToolMaterial material) {
        this.axeMaterial = material;
        this.setMaxStackSize(1);
        this.setMaxDamage(material.getMaxUses());
        this.setFull3D();
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabCombat);
        BlockDispenser.dispenseBehaviorRegistry.putObject((Object)this, (Object)new LOTRDispenseThrowingAxe());
    }
    
    public Item.ToolMaterial getAxeMaterial() {
        return this.axeMaterial;
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        final LOTREntityThrowingAxe axe = new LOTREntityThrowingAxe(world, (EntityLivingBase)entityplayer, itemstack.copy(), 2.0f);
        axe.setIsCritical(true);
        final int fireAspect = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) + LOTREnchantmentHelper.calcFireAspect(itemstack);
        if (fireAspect > 0) {
            axe.setFire(100);
        }
        for (final LOTREnchantment ench : LOTREnchantment.allEnchantments) {
            if (ench.applyToProjectile() && LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
                LOTREnchantmentHelper.setProjectileEnchantment(axe, ench);
            }
        }
        if (entityplayer.capabilities.isCreativeMode) {
            axe.canBePickedUp = 2;
        }
        world.playSoundAtEntity((Entity)entityplayer, "random.bow", 1.0f, 1.0f / (Item.itemRand.nextFloat() * 0.4f + 1.2f) + 0.25f);
        if (!world.isClient) {
            world.spawnEntityInWorld((Entity)axe);
        }
        if (!entityplayer.capabilities.isCreativeMode) {
            --itemstack.stackSize;
        }
        return itemstack;
    }
    
    public boolean getIsRepairable(final ItemStack itemstack, final ItemStack repairItem) {
        return LOTRRecipes.checkItemEquals(this.axeMaterial.getRepairItemStack(), repairItem) || super.getIsRepairable(itemstack, repairItem);
    }
    
    public float getRangedDamageMultiplier(final ItemStack itemstack, final Entity shooter, final Entity hit) {
        float damage = this.axeMaterial.getDamageVsEntity() + 4.0f;
        if (shooter instanceof EntityLivingBase && hit instanceof EntityLivingBase) {
            damage += EnchantmentHelper.getEnchantmentModifierLiving((EntityLivingBase)shooter, (EntityLivingBase)hit);
        }
        else {
            damage += EnchantmentHelper.func_152377_a(itemstack, EnumCreatureAttribute.UNDEFINED);
        }
        return damage * 0.5f;
    }
}

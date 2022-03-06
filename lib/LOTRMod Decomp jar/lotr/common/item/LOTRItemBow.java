// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.Arrays;
import lotr.client.render.item.LOTRRenderBow;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import java.util.Iterator;
import lotr.common.enchant.LOTREnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.item.LOTREntityArrowPoisoned;
import lotr.common.LOTRMod;
import net.minecraft.init.Items;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;

public class LOTRItemBow extends ItemBow
{
    private Item.ToolMaterial bowMaterial;
    public final double arrowDamageFactor;
    private int bowPullTime;
    public static final float MIN_BOW_DRAW_AMOUNT = 0.65f;
    @SideOnly(Side.CLIENT)
    private IIcon[] bowPullIcons;
    
    public LOTRItemBow(final LOTRMaterial material) {
        this(material.toToolMaterial(), 1.0);
    }
    
    public LOTRItemBow(final LOTRMaterial material, final double d) {
        this(material.toToolMaterial(), d);
    }
    
    public LOTRItemBow(final Item.ToolMaterial material) {
        this(material, 1.0);
    }
    
    public LOTRItemBow(final Item.ToolMaterial material, final double d) {
        this.bowMaterial = material;
        this.setMaxDamage((int)(material.getMaxUses() * 1.5f));
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabCombat);
        this.arrowDamageFactor = d;
        this.bowPullTime = 20;
    }
    
    public LOTRItemBow setDrawTime(final int i) {
        this.bowPullTime = i;
        return this;
    }
    
    public void onPlayerStoppedUsing(final ItemStack itemstack, final World world, final EntityPlayer entityplayer, final int i) {
        int useTick = this.getMaxItemUseDuration(itemstack) - i;
        final ArrowLooseEvent event = new ArrowLooseEvent(entityplayer, itemstack, useTick);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            return;
        }
        useTick = event.charge;
        ItemStack arrowItem = null;
        final int arrowSlot = this.getInvArrowSlot(entityplayer);
        if (arrowSlot >= 0) {
            arrowItem = entityplayer.inventory.mainInventory[arrowSlot];
        }
        final boolean shouldConsume = this.shouldConsumeArrow(itemstack, entityplayer);
        if (arrowItem == null && !shouldConsume) {
            arrowItem = new ItemStack(Items.arrow);
        }
        if (arrowItem != null) {
            float charge = useTick / (float)this.getMaxDrawTime();
            if (charge < 0.65f) {
                return;
            }
            charge = (charge * charge + charge * 2.0f) / 3.0f;
            charge = Math.min(charge, 1.0f);
            EntityArrow arrow;
            if (arrowItem.getItem() == LOTRMod.arrowPoisoned) {
                arrow = new LOTREntityArrowPoisoned(world, (EntityLivingBase)entityplayer, charge * 2.0f * getLaunchSpeedFactor(itemstack));
            }
            else {
                arrow = new EntityArrow(world, (EntityLivingBase)entityplayer, charge * 2.0f * getLaunchSpeedFactor(itemstack));
            }
            if (arrow.getDamage() < 1.0) {
                arrow.setDamage(1.0);
            }
            if (charge >= 1.0f) {
                arrow.setIsCritical(true);
            }
            applyBowModifiers(arrow, itemstack);
            itemstack.damageItem(1, (EntityLivingBase)entityplayer);
            world.playSoundAtEntity((Entity)entityplayer, "random.bow", 1.0f, 1.0f / (Item.itemRand.nextFloat() * 0.4f + 1.2f) + charge * 0.5f);
            if (!shouldConsume) {
                arrow.canBePickedUp = 2;
            }
            else if (arrowSlot >= 0) {
                final ItemStack itemStack = arrowItem;
                --itemStack.stackSize;
                if (arrowItem.stackSize <= 0) {
                    entityplayer.inventory.mainInventory[arrowSlot] = null;
                }
            }
            if (!world.isClient) {
                world.spawnEntityInWorld((Entity)arrow);
            }
        }
    }
    
    public static float getLaunchSpeedFactor(final ItemStack itemstack) {
        float f = 1.0f;
        if (itemstack != null) {
            if (itemstack.getItem() instanceof LOTRItemBow) {
                f *= (float)((LOTRItemBow)itemstack.getItem()).arrowDamageFactor;
            }
            f *= LOTREnchantmentHelper.calcRangedDamageFactor(itemstack);
        }
        return f;
    }
    
    public static void applyBowModifiers(final EntityArrow arrow, final ItemStack itemstack) {
        final int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);
        if (power > 0) {
            arrow.setDamage(arrow.getDamage() + power * 0.5 + 0.5);
        }
        int punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);
        punch += LOTREnchantmentHelper.calcRangedKnockback(itemstack);
        if (punch > 0) {
            arrow.setKnockbackStrength(punch);
        }
        final int fireAspect = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) + LOTREnchantmentHelper.calcFireAspect(itemstack);
        if (fireAspect > 0) {
            arrow.setFire(100);
        }
        for (final LOTREnchantment ench : LOTREnchantment.allEnchantments) {
            if (ench.applyToProjectile() && LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
                LOTREnchantmentHelper.setProjectileEnchantment((Entity)arrow, ench);
            }
        }
    }
    
    public int getMaxDrawTime() {
        return this.bowPullTime;
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        final ArrowNockEvent event = new ArrowNockEvent(entityplayer, itemstack);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            return event.result;
        }
        if (!this.shouldConsumeArrow(itemstack, entityplayer) || this.getInvArrowSlot(entityplayer) >= 0) {
            entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        }
        return itemstack;
    }
    
    private boolean shouldConsumeArrow(final ItemStack itemstack, final EntityPlayer entityplayer) {
        return !entityplayer.capabilities.isCreativeMode && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) == 0;
    }
    
    private int getInvArrowSlot(final EntityPlayer entityplayer) {
        for (int slot = 0; slot < entityplayer.inventory.mainInventory.length; ++slot) {
            final ItemStack invItem = entityplayer.inventory.mainInventory[slot];
            if (invItem != null && (invItem.getItem() == Items.arrow || invItem.getItem() == LOTRMod.arrowPoisoned)) {
                return slot;
            }
        }
        return -1;
    }
    
    public int getItemEnchantability() {
        return 1 + this.bowMaterial.getEnchantability() / 5;
    }
    
    public boolean getIsRepairable(final ItemStack itemstack, final ItemStack repairItem) {
        return repairItem.getItem() == Items.string || super.getIsRepairable(itemstack, repairItem);
    }
    
    public BowState getBowState(final EntityLivingBase entity, final ItemStack usingItem, final int useRemaining) {
        if (entity instanceof EntityPlayer && usingItem != null && usingItem.getItem() == this) {
            final int ticksInUse = usingItem.getMaxItemUseDuration() - useRemaining;
            final double useAmount = ticksInUse / (double)this.bowPullTime;
            if (useAmount >= 0.9) {
                return BowState.PULL_2;
            }
            if (useAmount > 0.65) {
                return BowState.PULL_1;
            }
            if (useAmount > 0.0) {
                return BowState.PULL_0;
            }
        }
        return BowState.HELD;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final ItemStack itemstack, final int renderPass, final EntityPlayer entityplayer, final ItemStack usingItem, final int useRemaining) {
        final BowState bowState = this.getBowState((EntityLivingBase)entityplayer, usingItem, useRemaining);
        if (bowState == BowState.PULL_0) {
            return this.bowPullIcons[0];
        }
        if (bowState == BowState.PULL_1) {
            return this.bowPullIcons[1];
        }
        if (bowState == BowState.PULL_2) {
            return this.bowPullIcons[2];
        }
        return ((Item)this).itemIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        ((Item)this).itemIcon = iconregister.registerIcon(this.getIconString());
        this.bowPullIcons = new IIcon[3];
        final IItemRenderer bowRenderer = MinecraftForgeClient.getItemRenderer(new ItemStack((Item)this), IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON);
        if (bowRenderer instanceof LOTRRenderBow && ((LOTRRenderBow)bowRenderer).isLargeBow()) {
            Arrays.fill(this.bowPullIcons, ((Item)this).itemIcon);
        }
        else {
            this.bowPullIcons[0] = iconregister.registerIcon(this.getIconString() + "_" + BowState.PULL_0.iconName);
            this.bowPullIcons[1] = iconregister.registerIcon(this.getIconString() + "_" + BowState.PULL_1.iconName);
            this.bowPullIcons[2] = iconregister.registerIcon(this.getIconString() + "_" + BowState.PULL_2.iconName);
        }
    }
    
    public enum BowState
    {
        HELD(""), 
        PULL_0("pull_0"), 
        PULL_1("pull_1"), 
        PULL_2("pull_2");
        
        public final String iconName;
        
        private BowState(final String s) {
            this.iconName = s;
        }
    }
}

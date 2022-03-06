// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.recipe.LOTRRecipes;
import java.util.List;
import net.minecraft.util.StatCollector;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.LOTRMod;
import java.util.Iterator;
import lotr.common.enchant.LOTREnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.projectile.LOTREntityCrossbowBolt;
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

public class LOTRItemCrossbow extends ItemBow
{
    public final double boltDamageFactor;
    private Item.ToolMaterial crossbowMaterial;
    @SideOnly(Side.CLIENT)
    private IIcon[] crossbowPullIcons;
    
    public LOTRItemCrossbow(final LOTRMaterial material) {
        this(material.toToolMaterial());
    }
    
    public LOTRItemCrossbow(final Item.ToolMaterial material) {
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabCombat);
        this.crossbowMaterial = material;
        this.setMaxDamage((int)(this.crossbowMaterial.getMaxUses() * 1.25f));
        this.setMaxStackSize(1);
        this.boltDamageFactor = 1.0f + Math.max(0.0f, (this.crossbowMaterial.getDamageVsEntity() - 2.0f) * 0.1f);
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (isLoaded(itemstack)) {
            final ItemStack boltItem = getLoaded(itemstack);
            if (boltItem != null) {
                final float charge = 1.0f;
                final ItemStack shotBolt = boltItem.copy();
                shotBolt.stackSize = 1;
                final LOTREntityCrossbowBolt bolt = new LOTREntityCrossbowBolt(world, (EntityLivingBase)entityplayer, shotBolt, charge * 2.0f * getCrossbowLaunchSpeedFactor(itemstack));
                if (bolt.boltDamageFactor < 1.0) {
                    bolt.boltDamageFactor = 1.0;
                }
                if (charge >= 1.0f) {
                    bolt.setIsCritical(true);
                }
                applyCrossbowModifiers(bolt, itemstack);
                if (!this.shouldConsumeBolt(itemstack, entityplayer)) {
                    bolt.canBePickedUp = 2;
                }
                if (!world.isClient) {
                    world.spawnEntityInWorld((Entity)bolt);
                }
                world.playSoundAtEntity((Entity)entityplayer, "lotr:item.crossbow", 1.0f, 1.0f / (Item.itemRand.nextFloat() * 0.4f + 1.2f) + charge * 0.5f);
                itemstack.damageItem(1, (EntityLivingBase)entityplayer);
                if (!world.isClient) {
                    this.setLoaded(itemstack, null);
                }
            }
        }
        else if (!this.shouldConsumeBolt(itemstack, entityplayer) || this.getInvBoltSlot(entityplayer) >= 0) {
            entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        }
        return itemstack;
    }
    
    public static float getCrossbowLaunchSpeedFactor(final ItemStack itemstack) {
        float f = 1.0f;
        if (itemstack != null) {
            if (itemstack.getItem() instanceof LOTRItemCrossbow) {
                f *= (float)((LOTRItemCrossbow)itemstack.getItem()).boltDamageFactor;
            }
            f *= LOTREnchantmentHelper.calcRangedDamageFactor(itemstack);
        }
        return f;
    }
    
    public static void applyCrossbowModifiers(final LOTREntityCrossbowBolt bolt, final ItemStack itemstack) {
        final int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);
        if (power > 0) {
            bolt.boltDamageFactor += power * 0.5 + 0.5;
        }
        int punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);
        punch += LOTREnchantmentHelper.calcRangedKnockback(itemstack);
        if (punch > 0) {
            bolt.knockbackStrength = punch;
        }
        final int fireAspect = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) + LOTREnchantmentHelper.calcFireAspect(itemstack);
        if (fireAspect > 0) {
            bolt.setFire(100);
        }
        for (final LOTREnchantment ench : LOTREnchantment.allEnchantments) {
            if (ench.applyToProjectile() && LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
                LOTREnchantmentHelper.setProjectileEnchantment(bolt, ench);
            }
        }
    }
    
    public void onUsingTick(final ItemStack itemstack, final EntityPlayer entityplayer, final int count) {
        final World world = ((Entity)entityplayer).worldObj;
        if (!world.isClient && !isLoaded(itemstack) && this.getMaxItemUseDuration(itemstack) - count == this.getMaxDrawTime()) {
            world.playSoundAtEntity((Entity)entityplayer, "lotr:item.crossbowLoad", 1.0f, 1.5f + world.rand.nextFloat() * 0.2f);
        }
    }
    
    public void onPlayerStoppedUsing(final ItemStack itemstack, final World world, final EntityPlayer entityplayer, final int useTick) {
        final int ticksInUse = this.getMaxItemUseDuration(itemstack) - useTick;
        if (ticksInUse >= this.getMaxDrawTime() && !isLoaded(itemstack)) {
            ItemStack boltItem = null;
            final int boltSlot = this.getInvBoltSlot(entityplayer);
            if (boltSlot >= 0) {
                boltItem = entityplayer.inventory.mainInventory[boltSlot];
            }
            final boolean shouldConsume = this.shouldConsumeBolt(itemstack, entityplayer);
            if (boltItem == null && !shouldConsume) {
                boltItem = new ItemStack(LOTRMod.crossbowBolt);
            }
            if (boltItem != null) {
                if (shouldConsume && boltSlot >= 0) {
                    final ItemStack itemStack = boltItem;
                    --itemStack.stackSize;
                    if (boltItem.stackSize <= 0) {
                        entityplayer.inventory.mainInventory[boltSlot] = null;
                    }
                }
                if (!world.isClient) {
                    this.setLoaded(itemstack, boltItem.copy());
                }
            }
            entityplayer.clearItemInUse();
        }
    }
    
    public int getMaxDrawTime() {
        return 50;
    }
    
    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return 72000;
    }
    
    public static boolean isLoaded(final ItemStack itemstack) {
        return getLoaded(itemstack) != null;
    }
    
    public static ItemStack getLoaded(final ItemStack itemstack) {
        if (itemstack != null && itemstack.getItem() instanceof LOTRItemCrossbow) {
            final NBTTagCompound nbt = itemstack.getTagCompound();
            if (nbt == null) {
                return null;
            }
            if (nbt.hasKey("LOTRCrossbowAmmo")) {
                final NBTTagCompound ammoData = nbt.getCompoundTag("LOTRCrossbowAmmo");
                return ItemStack.loadItemStackFromNBT(ammoData);
            }
            if (nbt.hasKey("LOTRCrossbowLoaded")) {
                return new ItemStack(LOTRMod.crossbowBolt);
            }
        }
        return null;
    }
    
    private void setLoaded(final ItemStack itemstack, final ItemStack ammo) {
        if (itemstack != null && itemstack.getItem() instanceof LOTRItemCrossbow) {
            NBTTagCompound nbt = itemstack.getTagCompound();
            if (nbt == null) {
                nbt = new NBTTagCompound();
                itemstack.setTagCompound(nbt);
            }
            if (ammo != null) {
                final NBTTagCompound ammoData = new NBTTagCompound();
                ammo.writeToNBT(ammoData);
                nbt.setTag("LOTRCrossbowAmmo", (NBTBase)ammoData);
            }
            else {
                nbt.removeTag("LOTRCrossbowAmmo");
            }
            if (nbt.hasKey("LOTRCrossbowLoaded")) {
                nbt.removeTag("LOTRCrossbowLoaded");
            }
        }
    }
    
    private boolean shouldConsumeBolt(final ItemStack itemstack, final EntityPlayer entityplayer) {
        return !entityplayer.capabilities.isCreativeMode && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) == 0;
    }
    
    private int getInvBoltSlot(final EntityPlayer entityplayer) {
        for (int slot = 0; slot < entityplayer.inventory.mainInventory.length; ++slot) {
            final ItemStack invItem = entityplayer.inventory.mainInventory[slot];
            if (invItem != null && invItem.getItem() instanceof LOTRItemCrossbowBolt) {
                return slot;
            }
        }
        return -1;
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        String name = super.getItemStackDisplayName(itemstack);
        if (isLoaded(itemstack)) {
            name = StatCollector.translateToLocalFormatted("item.lotr.crossbow.loaded", new Object[] { name });
        }
        return name;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        final ItemStack ammo = getLoaded(itemstack);
        if (ammo != null) {
            final String ammoName = ammo.getDisplayName();
            list.add(StatCollector.translateToLocalFormatted("item.lotr.crossbow.loadedItem", new Object[] { ammoName }));
        }
    }
    
    public int getItemEnchantability() {
        return 1 + this.crossbowMaterial.getEnchantability() / 5;
    }
    
    public Item.ToolMaterial getCrossbowMaterial() {
        return this.crossbowMaterial;
    }
    
    public boolean getIsRepairable(final ItemStack itemstack, final ItemStack repairItem) {
        return LOTRRecipes.checkItemEquals(this.crossbowMaterial.getRepairItemStack(), repairItem) || super.getIsRepairable(itemstack, repairItem);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final ItemStack itemstack, final int renderPass, final EntityPlayer entityplayer, final ItemStack usingItem, final int useRemaining) {
        if (isLoaded(itemstack)) {
            return this.crossbowPullIcons[2];
        }
        if (usingItem != null && usingItem.getItem() == this) {
            final int ticksInUse = usingItem.getMaxItemUseDuration() - useRemaining;
            final double useAmount = ticksInUse / (double)this.getMaxDrawTime();
            if (useAmount >= 1.0) {
                return this.crossbowPullIcons[2];
            }
            if (useAmount > 0.5) {
                return this.crossbowPullIcons[1];
            }
            if (useAmount > 0.0) {
                return this.crossbowPullIcons[0];
            }
        }
        return ((Item)this).itemIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(final ItemStack itemstack) {
        if (isLoaded(itemstack)) {
            return this.crossbowPullIcons[2];
        }
        return ((Item)this).itemIcon;
    }
    
    public IIcon getIcon(final ItemStack itemstack, final int pass) {
        return this.getIconIndex(itemstack);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        ((Item)this).itemIcon = iconregister.registerIcon(this.getIconString());
        (this.crossbowPullIcons = new IIcon[3])[0] = iconregister.registerIcon(this.getIconString() + "_" + LOTRItemBow.BowState.PULL_0.iconName);
        this.crossbowPullIcons[1] = iconregister.registerIcon(this.getIconString() + "_" + LOTRItemBow.BowState.PULL_1.iconName);
        this.crossbowPullIcons[2] = iconregister.registerIcon(this.getIconString() + "_" + LOTRItemBow.BowState.PULL_2.iconName);
    }
}

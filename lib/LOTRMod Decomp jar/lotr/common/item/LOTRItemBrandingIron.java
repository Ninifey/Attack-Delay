// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.UUID;
import net.minecraft.util.StatCollector;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.util.ForgeDirection;
import lotr.common.tileentity.LOTRTileEntityHobbitOven;
import lotr.common.tileentity.LOTRTileEntityForgeBase;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.entity.Entity;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class LOTRItemBrandingIron extends Item
{
    @SideOnly(Side.CLIENT)
    private IIcon iconCool;
    @SideOnly(Side.CLIENT)
    private IIcon iconHot;
    private static final int HEAT_USES = 5;
    
    public LOTRItemBrandingIron() {
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabTools);
        this.setMaxStackSize(1);
        this.setMaxDamage(100);
        this.setFull3D();
    }
    
    private static boolean isHeated(final ItemStack itemstack) {
        return itemstack.hasTagCompound() && itemstack.getTagCompound().getBoolean("HotIron");
    }
    
    private static void setHeated(final ItemStack itemstack, final boolean flag) {
        if (!itemstack.hasTagCompound()) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setBoolean("HotIron", flag);
    }
    
    public static String trimAcceptableBrandName(String s) {
        s = StringUtils.trim(s);
        final int maxLength = 64;
        if (s.length() > maxLength) {
            s = s.substring(0, maxLength);
        }
        return s;
    }
    
    public static String getBrandName(final ItemStack itemstack) {
        if (itemstack.hasTagCompound()) {
            final String s = itemstack.getTagCompound().getString("BrandName");
            if (!StringUtils.isBlank((CharSequence)s)) {
                return s;
            }
        }
        return null;
    }
    
    public static boolean hasBrandName(final ItemStack itemstack) {
        return getBrandName(itemstack) != null;
    }
    
    public static void setBrandName(final ItemStack itemstack, final String s) {
        if (!itemstack.hasTagCompound()) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setString("BrandName", s);
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (!hasBrandName(itemstack)) {
            entityplayer.openGui((Object)LOTRMod.instance, 61, world, 0, 0, 0);
        }
        return itemstack;
    }
    
    public boolean itemInteractionForEntity(final ItemStack itemstack, final EntityPlayer entityplayer, final EntityLivingBase entity) {
        if (isHeated(itemstack) && hasBrandName(itemstack)) {
            final String brandName = getBrandName(itemstack);
            if (entity instanceof EntityLiving) {
                final EntityLiving entityliving = (EntityLiving)entity;
                boolean acceptableEntity = false;
                if (entityliving instanceof EntityAnimal) {
                    acceptableEntity = true;
                }
                else if (entityliving instanceof LOTREntityNPC && ((LOTREntityNPC)entityliving).canRenameNPC()) {
                    acceptableEntity = true;
                }
                if (acceptableEntity && !entityliving.getCustomNameTag().equals(brandName)) {
                    entityliving.setCustomNameTag(brandName);
                    entityliving.func_110163_bv();
                    entityliving.playLivingSound();
                    entityliving.getJumpHelper().setJumping();
                    final World world = ((Entity)entityliving).worldObj;
                    world.playSoundAtEntity((Entity)entityliving, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
                    entityplayer.swingItem();
                    final int preDamage = itemstack.getItemDamage();
                    itemstack.damageItem(1, (EntityLivingBase)entityplayer);
                    final int newDamage = itemstack.getItemDamage();
                    if (preDamage / 5 != newDamage / 5) {
                        setHeated(itemstack, false);
                    }
                    if (!world.isClient) {
                        setBrandingPlayer((Entity)entityliving, entityplayer.getUniqueID());
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, final int i, final int j, final int k, final int side, final float f, final float f1, final float f2) {
        if (hasBrandName(itemstack) && !isHeated(itemstack)) {
            boolean isHotBlock = false;
            final TileEntity te = world.getTileEntity(i, j, k);
            if (te instanceof TileEntityFurnace && ((TileEntityFurnace)te).func_145950_i()) {
                isHotBlock = true;
            }
            else if (te instanceof LOTRTileEntityForgeBase && ((LOTRTileEntityForgeBase)te).isSmelting()) {
                isHotBlock = true;
            }
            else if (te instanceof LOTRTileEntityHobbitOven && ((LOTRTileEntityHobbitOven)te).isCooking()) {
                isHotBlock = true;
            }
            if (!isHotBlock) {
                final ForgeDirection dir = ForgeDirection.getOrientation(side);
                final Block block = world.getBlock(i + dir.offsetX, j + dir.offsetY, k + dir.offsetZ);
                if (block.getMaterial() == Material.fire) {
                    isHotBlock = true;
                }
            }
            if (isHotBlock) {
                setHeated(itemstack, true);
                return true;
            }
        }
        return false;
    }
    
    public boolean getIsRepairable(final ItemStack itemstack, final ItemStack repairItem) {
        return repairItem.getItem() == Items.iron_ingot;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(final ItemStack itemstack) {
        return this.getIcon(itemstack, 0);
    }
    
    public IIcon getIcon(final ItemStack itemstack, final int pass) {
        if (isHeated(itemstack)) {
            return this.iconHot;
        }
        return this.iconCool;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        this.iconCool = iconregister.registerIcon(this.getIconString());
        this.iconHot = iconregister.registerIcon(this.getIconString() + "_hot");
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        String name = super.getItemStackDisplayName(itemstack);
        if (hasBrandName(itemstack)) {
            final String brandName = getBrandName(itemstack);
            name = StatCollector.translateToLocalFormatted("item.lotr.brandingIron.named", new Object[] { name, brandName });
        }
        else {
            name = StatCollector.translateToLocalFormatted("item.lotr.brandingIron.unnamed", new Object[] { name });
        }
        return name;
    }
    
    public static UUID getBrandingPlayer(final Entity entity) {
        final NBTTagCompound nbt = entity.getEntityData();
        if (nbt.hasKey("LOTRBrander")) {
            final String s = nbt.getString("LOTRBrander");
            return UUID.fromString(s);
        }
        return null;
    }
    
    public static void setBrandingPlayer(final Entity entity, final UUID player) {
        final String s = player.toString();
        entity.getEntityData().setString("LOTRBrander", s);
    }
}

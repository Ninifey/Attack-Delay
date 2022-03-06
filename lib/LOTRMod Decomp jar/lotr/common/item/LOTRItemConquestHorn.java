// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.List;
import net.minecraft.util.StatCollector;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.EnumAction;
import lotr.common.entity.LOTREntityInvasionSpawner;
import lotr.common.fac.LOTRFaction;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.Entity;
import lotr.common.LOTRBannerProtection;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.LOTRLevelData;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import lotr.common.LOTRDimension;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class LOTRItemConquestHorn extends Item
{
    @SideOnly(Side.CLIENT)
    private IIcon baseIcon;
    @SideOnly(Side.CLIENT)
    private IIcon overlayIcon;
    
    public LOTRItemConquestHorn() {
        this.setMaxStackSize(1);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabCombat);
    }
    
    public static LOTRInvasions getInvasionType(final ItemStack itemstack) {
        LOTRInvasions invasionType = null;
        if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("InvasionType")) {
            final String s = itemstack.getTagCompound().getString("InvasionType");
            invasionType = LOTRInvasions.forName(s);
        }
        if (invasionType == null && itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("HornFaction")) {
            final String s = itemstack.getTagCompound().getString("HornFaction");
            invasionType = LOTRInvasions.forName(s);
        }
        if (invasionType == null) {
            invasionType = LOTRInvasions.HOBBIT;
        }
        return invasionType;
    }
    
    public static void setInvasionType(final ItemStack itemstack, final LOTRInvasions type) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setString("InvasionType", type.codeName());
    }
    
    public static ItemStack createHorn(final LOTRInvasions type) {
        final ItemStack itemstack = new ItemStack(LOTRMod.conquestHorn);
        setInvasionType(itemstack, type);
        return itemstack;
    }
    
    private boolean canUseHorn(final ItemStack itemstack, final World world, final EntityPlayer entityplayer, final boolean sendMessage) {
        if (LOTRDimension.getCurrentDimension(world) == LOTRDimension.UTUMNO) {
            if (sendMessage && !world.isClient) {
                entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("chat.lotr.conquestHornProtected", new Object[0]));
            }
            return false;
        }
        final LOTRInvasions invasionType = getInvasionType(itemstack);
        final LOTRFaction invasionFaction = invasionType.invasionFaction;
        final float alignmentRequired = 1500.0f;
        if (LOTRLevelData.getData(entityplayer).getAlignment(invasionFaction) < alignmentRequired) {
            if (sendMessage && !world.isClient) {
                LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, alignmentRequired, invasionType.invasionFaction);
            }
            return false;
        }
        boolean blocked = false;
        if (LOTRBannerProtection.isProtectedByBanner(world, (Entity)entityplayer, LOTRBannerProtection.forFaction(invasionFaction), false)) {
            blocked = true;
        }
        if (LOTREntityNPCRespawner.isSpawnBlocked((Entity)entityplayer, invasionFaction)) {
            blocked = true;
        }
        if (blocked) {
            if (sendMessage && !world.isClient) {
                entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("chat.lotr.conquestHornProtected", new Object[] { invasionFaction.factionName() }));
            }
            return false;
        }
        return true;
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        final boolean canUse = this.canUseHorn(itemstack, world, entityplayer, false);
        entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        return itemstack;
    }
    
    public ItemStack onEaten(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        final LOTRInvasions invasionType = getInvasionType(itemstack);
        if (this.canUseHorn(itemstack, world, entityplayer, true)) {
            if (!world.isClient) {
                final LOTREntityInvasionSpawner invasion = new LOTREntityInvasionSpawner(world);
                invasion.setInvasionType(invasionType);
                invasion.spawnsPersistent = true;
                invasion.setLocationAndAngles(((Entity)entityplayer).posX, ((Entity)entityplayer).posY + 3.0, ((Entity)entityplayer).posZ, 0.0f, 0.0f);
                world.spawnEntityInWorld((Entity)invasion);
                invasion.announceInvasionToEnemies();
                invasion.announceInvasionTo(entityplayer);
            }
            if (!entityplayer.capabilities.isCreativeMode) {
                --itemstack.stackSize;
            }
            return itemstack;
        }
        return itemstack;
    }
    
    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return 40;
    }
    
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.bow;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        this.baseIcon = iconregister.registerIcon(this.getIconString() + "_base");
        this.overlayIcon = iconregister.registerIcon(this.getIconString() + "_overlay");
    }
    
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(final int i, final int pass) {
        return (pass > 0) ? this.overlayIcon : this.baseIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(final ItemStack itemstack, final int pass) {
        if (pass == 0) {
            final LOTRFaction faction = getInvasionType(itemstack).invasionFaction;
            return faction.getFactionColor();
        }
        return 16777215;
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        final LOTRInvasions type = getInvasionType(itemstack);
        if (type != null) {
            return StatCollector.translateToLocal(type.codeNameHorn());
        }
        return super.getItemStackDisplayName(itemstack);
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        final LOTRInvasions type = getInvasionType(itemstack);
        list.add(type.invasionName());
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (final LOTRInvasions type : LOTRInvasions.values()) {
            final ItemStack itemstack = new ItemStack(item);
            setInvasionType(itemstack, type);
            list.add(itemstack);
        }
    }
}

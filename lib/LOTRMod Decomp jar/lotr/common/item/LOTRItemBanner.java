// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.Block;
import lotr.common.entity.item.LOTREntityBannerWall;
import net.minecraft.util.Direction;
import net.minecraft.init.Blocks;
import lotr.common.LOTRAchievement;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import lotr.common.entity.item.LOTREntityBanner;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRBannerProtection;
import lotr.common.LOTRConfig;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.util.StatCollector;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class LOTRItemBanner extends Item
{
    @SideOnly(Side.CLIENT)
    private IIcon[] bannerIcons;
    
    public LOTRItemBanner() {
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
        this.setMaxStackSize(16);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setFull3D();
    }
    
    public static BannerType getBannerType(final ItemStack itemstack) {
        if (itemstack.getItem() instanceof LOTRItemBanner) {
            return getBannerType(itemstack.getItemDamage());
        }
        return null;
    }
    
    public static BannerType getBannerType(final int i) {
        return BannerType.forID(i);
    }
    
    public static NBTTagCompound getProtectionData(final ItemStack itemstack) {
        if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("LOTRBannerData")) {
            final NBTTagCompound data = itemstack.getTagCompound().getCompoundTag("LOTRBannerData");
            return data;
        }
        return null;
    }
    
    public static void setProtectionData(final ItemStack itemstack, final NBTTagCompound data) {
        if (data == null) {
            if (itemstack.getTagCompound() != null) {
                itemstack.getTagCompound().removeTag("LOTRBannerData");
            }
        }
        else {
            if (itemstack.getTagCompound() == null) {
                itemstack.setTagCompound(new NBTTagCompound());
            }
            itemstack.getTagCompound().setTag("LOTRBannerData", (NBTBase)data);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i) {
        if (i >= this.bannerIcons.length) {
            i = 0;
        }
        return this.bannerIcons[i];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        this.bannerIcons = new IIcon[BannerType.bannerTypes.size()];
        for (int i = 0; i < this.bannerIcons.length; ++i) {
            this.bannerIcons[i] = iconregister.registerIcon(this.getIconString() + "_" + BannerType.bannerTypes.get(i).bannerName);
        }
    }
    
    public String getUnlocalizedName(final ItemStack itemstack) {
        return super.getUnlocalizedName() + "." + getBannerType(itemstack).bannerName;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        final NBTTagCompound protectData = getProtectionData(itemstack);
        if (protectData != null) {
            list.add(StatCollector.translateToLocal("item.lotr.banner.protect"));
        }
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, final int i, int j, final int k, int side, final float f, final float f1, final float f2) {
        final BannerType bannerType = getBannerType(itemstack);
        final NBTTagCompound protectData = getProtectionData(itemstack);
        if (world.getBlock(i, j, k).isReplaceable((IBlockAccess)world, i, j, k)) {
            side = 1;
        }
        else if (side == 1) {
            ++j;
        }
        if (side == 0) {
            return false;
        }
        if (side == 1) {
            if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
                return false;
            }
            final Block block = world.getBlock(i, j - 1, k);
            final int meta = world.getBlockMetadata(i, j - 1, k);
            if (block.isSideSolid((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP)) {
                if (LOTRConfig.allowBannerProtection && !entityplayer.capabilities.isCreativeMode) {
                    final int protectRange = LOTRBannerProtection.getProtectionRange(block, meta);
                    if (protectRange > 0) {
                        final LOTRFaction faction = bannerType.faction;
                        if (LOTRLevelData.getData(entityplayer).getAlignment(faction) < 1.0f) {
                            if (!world.isClient) {
                                LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 1.0f, faction);
                            }
                            return false;
                        }
                        if (!world.isClient && LOTRBannerProtection.isProtectedByBanner(world, i, j, k, LOTRBannerProtection.forPlayer(entityplayer), false, protectRange)) {
                            entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("chat.lotr.alreadyProtected", new Object[0]));
                            return false;
                        }
                    }
                }
                if (!world.isClient) {
                    final LOTREntityBanner banner = new LOTREntityBanner(world);
                    banner.setLocationAndAngles(i + 0.5, (double)j, k + 0.5, 90.0f * (MathHelper.floor_double(((Entity)entityplayer).rotationYaw * 4.0f / 360.0f + 0.5) & 0x3), 0.0f);
                    if (world.checkNoEntityCollision(banner.boundingBox) && world.getCollidingBoundingBoxes((Entity)banner, banner.boundingBox).size() == 0 && !world.isAnyLiquid(banner.boundingBox)) {
                        banner.setBannerType(bannerType);
                        if (protectData != null) {
                            banner.readProtectionFromNBT(protectData);
                        }
                        banner.setPlacingPlayer(entityplayer);
                        world.spawnEntityInWorld((Entity)banner);
                        if (banner.isProtectingTerritory()) {
                            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.bannerProtect);
                        }
                        world.playSoundAtEntity((Entity)banner, Blocks.planks.stepSound.func_150496_b(), (Blocks.planks.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.planks.stepSound.getFrequency() * 0.8f);
                        --itemstack.stackSize;
                        return true;
                    }
                    banner.setDead();
                }
            }
        }
        else {
            if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
                return false;
            }
            if (!world.isClient) {
                final int l = Direction.facingToDirection[side];
                final LOTREntityBannerWall banner2 = new LOTREntityBannerWall(world, i, j, k, l);
                if (banner2.onValidSurface()) {
                    banner2.setBannerType(bannerType);
                    if (protectData != null) {
                        banner2.setProtectData((NBTTagCompound)protectData.copy());
                    }
                    world.spawnEntityInWorld((Entity)banner2);
                    world.playSoundAtEntity((Entity)banner2, Blocks.planks.stepSound.func_150496_b(), (Blocks.planks.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.planks.stepSound.getFrequency() * 0.8f);
                    --itemstack.stackSize;
                    return true;
                }
                banner2.setDead();
            }
        }
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (final BannerType type : BannerType.bannerTypes) {
            list.add(new ItemStack(item, 1, type.bannerID));
        }
    }
    
    public enum BannerType
    {
        GONDOR(0, "gondor", LOTRFaction.GONDOR), 
        ROHAN(1, "rohan", LOTRFaction.ROHAN), 
        MORDOR(2, "mordor", LOTRFaction.MORDOR), 
        GALADHRIM(3, "lothlorien", LOTRFaction.GALADHRIM), 
        WOOD_ELF(4, "mirkwood", LOTRFaction.WOOD_ELF), 
        DUNLAND(5, "dunland", LOTRFaction.DUNLAND), 
        ISENGARD(6, "isengard", LOTRFaction.URUK_HAI), 
        DWARF(7, "durin", LOTRFaction.DWARF), 
        ANGMAR(8, "angmar", LOTRFaction.ANGMAR), 
        NEAR_HARAD(9, "nearHarad", LOTRFaction.NEAR_HARAD), 
        HIGH_ELF(10, "highElf", LOTRFaction.HIGH_ELF), 
        BLUE_MOUNTAINS(11, "blueMountains", LOTRFaction.BLUE_MOUNTAINS), 
        RANGER_NORTH(12, "ranger", LOTRFaction.RANGER_NORTH), 
        DOL_GULDUR(13, "dolGuldur", LOTRFaction.DOL_GULDUR), 
        GUNDABAD(14, "gundabad", LOTRFaction.GUNDABAD), 
        HALF_TROLL(15, "halfTroll", LOTRFaction.HALF_TROLL), 
        DOL_AMROTH(16, "dolAmroth", LOTRFaction.GONDOR), 
        MOREDAIN(17, "moredain", LOTRFaction.MOREDAIN), 
        TAUREDAIN(18, "tauredain", LOTRFaction.TAUREDAIN), 
        DALE(19, "dale", LOTRFaction.DALE), 
        DORWINION(20, "dorwinion", LOTRFaction.DORWINION), 
        HOBBIT(21, "hobbit", LOTRFaction.HOBBIT), 
        ANORIEN(22, "anorien", LOTRFaction.GONDOR), 
        ITHILIEN(23, "ithilien", LOTRFaction.GONDOR), 
        LOSSARNACH(24, "lossarnach", LOTRFaction.GONDOR), 
        LEBENNIN(25, "lebennin", LOTRFaction.GONDOR), 
        PELARGIR(26, "pelargir", LOTRFaction.GONDOR), 
        BLACKROOT_VALE(27, "blackrootVale", LOTRFaction.GONDOR), 
        PINNATH_GELIN(28, "pinnathGelin", LOTRFaction.GONDOR), 
        MINAS_MORGUL(29, "minasMorgul", LOTRFaction.MORDOR), 
        BLACK_URUK(30, "blackUruk", LOTRFaction.MORDOR), 
        GONDOR_STEWARD(31, "gondorSteward", LOTRFaction.GONDOR), 
        NAN_UNGOL(32, "nanUngol", LOTRFaction.MORDOR), 
        RHUDAUR(33, "rhudaur", LOTRFaction.ANGMAR), 
        LAMEDON(34, "lamedon", LOTRFaction.GONDOR), 
        RHUN(35, "rhun", LOTRFaction.RHUN), 
        RIVENDELL(36, "rivendell", LOTRFaction.HIGH_ELF), 
        ESGAROTH(37, "esgaroth", LOTRFaction.DALE), 
        UMBAR(38, "umbar", LOTRFaction.NEAR_HARAD), 
        HARAD_NOMAD(39, "haradNomad", LOTRFaction.NEAR_HARAD), 
        HARAD_GULF(40, "haradGulf", LOTRFaction.NEAR_HARAD);
        
        public static List<BannerType> bannerTypes;
        private static Map<Integer, BannerType> bannerForID;
        public final int bannerID;
        public final String bannerName;
        public final LOTRFaction faction;
        
        private BannerType(final int i, final String s, final LOTRFaction f) {
            this.bannerID = i;
            this.bannerName = s;
            this.faction = f;
            this.faction.factionBanners.add(this);
        }
        
        public static BannerType forID(final int ID) {
            return BannerType.bannerForID.get(ID);
        }
        
        static {
            BannerType.bannerTypes = new ArrayList<BannerType>();
            BannerType.bannerForID = new HashMap<Integer, BannerType>();
            for (final BannerType t : values()) {
                BannerType.bannerTypes.add(t);
                BannerType.bannerForID.put(t.bannerID, t);
            }
        }
    }
}

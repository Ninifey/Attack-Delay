// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.ArrayList;
import net.minecraft.block.Block;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.Entity;
import lotr.common.entity.npc.LOTREntityHuorn;
import lotr.common.entity.npc.LOTREntityTree;
import lotr.common.block.LOTRBlockSaplingBase;
import net.minecraft.block.BlockSapling;
import lotr.common.fac.LOTRAlignmentValues;
import net.minecraft.init.Items;
import lotr.common.LOTRAchievement;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import lotr.common.fac.LOTRFaction;
import lotr.common.LOTRLevelData;
import net.minecraft.world.World;
import net.minecraft.item.EnumAction;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class LOTRItemEntDraught extends Item
{
    @SideOnly(Side.CLIENT)
    private IIcon[] draughtIcons;
    public static DraughtInfo[] draughtTypes;
    
    public LOTRItemEntDraught() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabFood);
    }
    
    private DraughtInfo getDraughtInfo(final ItemStack itemstack) {
        int i = itemstack.getItemDamage();
        if (i >= LOTRItemEntDraught.draughtTypes.length) {
            i = 0;
        }
        return LOTRItemEntDraught.draughtTypes[i];
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i) {
        if (i >= this.draughtIcons.length) {
            i = 0;
        }
        return this.draughtIcons[i];
    }
    
    public String getUnlocalizedName(final ItemStack itemstack) {
        return super.getUnlocalizedName() + "." + itemstack.getItemDamage();
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        this.draughtIcons = new IIcon[LOTRItemEntDraught.draughtTypes.length];
        for (int i = 0; i < LOTRItemEntDraught.draughtTypes.length; ++i) {
            this.draughtIcons[i] = iconregister.registerIcon(this.getIconString() + "_" + LOTRItemEntDraught.draughtTypes[i].name);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < LOTRItemEntDraught.draughtTypes.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        LOTRItemMug.addPotionEffectsToTooltip(itemstack, entityplayer, list, flag, this.getDraughtInfo(itemstack).effects);
    }
    
    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return 32;
    }
    
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.drink;
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (this.canPlayerDrink(entityplayer, itemstack)) {
            entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        }
        return itemstack;
    }
    
    public boolean canPlayerDrink(final EntityPlayer entityplayer, final ItemStack itemstack) {
        return !this.getDraughtInfo(itemstack).effects.isEmpty() || entityplayer.canEat(true);
    }
    
    public ItemStack onEaten(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.FANGORN) < 0.0f) {
            if (!world.isClient) {
                entityplayer.addPotionEffect(new PotionEffect(Potion.poison.id, 100));
            }
        }
        else {
            if (entityplayer.canEat(false)) {
                entityplayer.getFoodStats().addStats(this.getDraughtInfo(itemstack).heal, this.getDraughtInfo(itemstack).saturation);
            }
            if (!world.isClient) {
                final List effects = this.getDraughtInfo(itemstack).effects;
                for (int i = 0; i < effects.size(); ++i) {
                    final PotionEffect effect = effects.get(i);
                    entityplayer.addPotionEffect(new PotionEffect(effect.getPotionID(), effect.getDuration()));
                }
            }
            if (!world.isClient && entityplayer.getCurrentEquippedItem() == itemstack) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkEntDraught);
            }
        }
        return entityplayer.capabilities.isCreativeMode ? itemstack : new ItemStack(Items.bowl);
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, final int i, final int j, final int k, final int side, final float f, final float f1, final float f2) {
        if (this.getDraughtInfo(itemstack).name.equals("gold")) {
            if (LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.FANGORN) < 500.0f) {
                if (!world.isClient) {
                    LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 500.0f, LOTRFaction.FANGORN);
                }
                return false;
            }
            final Block block = world.getBlock(i, j, k);
            int meta = world.getBlockMetadata(i, j, k);
            if (block instanceof BlockSapling || block instanceof LOTRBlockSaplingBase) {
                meta &= 0x7;
                for (int huornType = 0; huornType < LOTREntityTree.TYPES.length; ++huornType) {
                    if (block == LOTREntityTree.SAPLING_BLOCKS[huornType] && meta == LOTREntityTree.SAPLING_META[huornType]) {
                        final LOTREntityHuorn huorn = new LOTREntityHuorn(world);
                        huorn.setTreeType(huornType);
                        huorn.isNPCPersistent = true;
                        huorn.liftSpawnRestrictions = true;
                        huorn.setLocationAndAngles(i + 0.5, (double)j, k + 0.5, 0.0f, 0.0f);
                        if (huorn.getCanSpawnHere()) {
                            if (!world.isClient) {
                                world.spawnEntityInWorld((Entity)huorn);
                                huorn.initCreatureForHire(null);
                                huorn.hiredNPCInfo.isActive = true;
                                huorn.hiredNPCInfo.alignmentRequiredToCommand = 500.0f;
                                huorn.hiredNPCInfo.setHiringPlayer(entityplayer);
                                huorn.hiredNPCInfo.setTask(LOTRHiredNPCInfo.Task.WARRIOR);
                                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.summonHuorn);
                            }
                            for (int l = 0; l < 24; ++l) {
                                final double d = i + 0.5 - world.rand.nextDouble() * 2.0 + world.rand.nextDouble() * 2.0;
                                final double d2 = j + world.rand.nextDouble() * 4.0;
                                final double d3 = k + 0.5 - world.rand.nextDouble() * 2.0 + world.rand.nextDouble() * 2.0;
                                world.spawnParticle("happyVillager", d, d2, d3, 0.0, 0.0, 0.0);
                            }
                            if (!entityplayer.capabilities.isCreativeMode) {
                                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.bowl));
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    static {
        LOTRItemEntDraught.draughtTypes = new DraughtInfo[] { new DraughtInfo("green", 0, 0.0f).addEffect(Potion.moveSpeed.id, 120).addEffect(Potion.digSpeed.id, 120).addEffect(Potion.damageBoost.id, 120), new DraughtInfo("brown", 20, 3.0f), new DraughtInfo("gold", 0, 0.0f), new DraughtInfo("yellow", 0, 0.0f).addEffect(Potion.regeneration.id, 60), new DraughtInfo("red", 0, 0.0f).addEffect(Potion.fireResistance.id, 180), new DraughtInfo("silver", 0, 0.0f).addEffect(Potion.nightVision.id, 180), new DraughtInfo("blue", 0, 0.0f).addEffect(Potion.waterBreathing.id, 180) };
    }
    
    public static class DraughtInfo
    {
        public String name;
        public int heal;
        public float saturation;
        public List effects;
        
        public DraughtInfo(final String s, final int i, final float f) {
            this.effects = new ArrayList();
            this.name = s;
            this.heal = i;
            this.saturation = f;
        }
        
        public DraughtInfo addEffect(final int i, final int j) {
            this.effects.add(new PotionEffect(i, j * 20));
            return this;
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.entity.EntityLivingBase;
import java.util.Iterator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.util.IIcon;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.Entity;
import net.minecraft.block.Block;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.LOTREntities;
import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;
import lotr.common.dispenser.LOTRDispenseSpawnEgg;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemSpawnEgg extends Item
{
    public LOTRItemSpawnEgg() {
        this.setHasSubtypes(true);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabSpawn);
        BlockDispenser.dispenseBehaviorRegistry.putObject((Object)this, (Object)new LOTRDispenseSpawnEgg());
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        String itemName = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
        final String entityName = LOTREntities.getStringFromID(itemstack.getItemDamage());
        if (entityName != null) {
            itemName = itemName + " " + StatCollector.translateToLocal("entity." + entityName + ".name");
        }
        return itemName;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        final String entityName = LOTREntities.getStringFromID(itemstack.getItemDamage());
        if (entityName != null) {
            list.add(entityName);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(final ItemStack itemstack, final int i) {
        final LOTREntities.SpawnEggInfo info = LOTREntities.spawnEggs.get(itemstack.getItemDamage());
        return (info != null) ? ((i == 0) ? info.primaryColor : info.secondaryColor) : 16777215;
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, int i, int j, int k, final int l, final float f, final float f1, final float f2) {
        if (world.isClient) {
            return true;
        }
        final Block block = world.getBlock(i, j, k);
        i += Facing.offsetsXForSide[l];
        j += Facing.offsetsYForSide[l];
        k += Facing.offsetsZForSide[l];
        double d = 0.0;
        if (l == 1 && block != null && block.getRenderType() == 11) {
            d = 0.5;
        }
        final Entity entity = spawnCreature(world, itemstack.getItemDamage(), i + 0.5, j + d, k + 0.5);
        if (entity != null) {
            if (entity instanceof EntityLiving && itemstack.hasDisplayName()) {
                ((EntityLiving)entity).setCustomNameTag(itemstack.getDisplayName());
            }
            if (entity instanceof LOTREntityNPC) {
                ((LOTREntityNPC)entity).isNPCPersistent = true;
                ((LOTREntityNPC)entity).onArtificalSpawn();
            }
            if (!entityplayer.capabilities.isCreativeMode) {
                --itemstack.stackSize;
            }
        }
        return true;
    }
    
    public static Entity spawnCreature(final World world, final int id, final double d, final double d1, final double d2) {
        if (!LOTREntities.spawnEggs.containsKey(id)) {
            return null;
        }
        final String entityName = LOTREntities.getStringFromID(id);
        final Entity entity = EntityList.createEntityByName(entityName, world);
        if (entity instanceof EntityLiving) {
            final EntityLiving entityliving = (EntityLiving)entity;
            entityliving.setLocationAndAngles(d, d1, d2, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0f), 0.0f);
            ((EntityLivingBase)entityliving).rotationYawHead = ((Entity)entityliving).rotationYaw;
            ((EntityLivingBase)entityliving).renderYawOffset = ((Entity)entityliving).rotationYaw;
            entityliving.onSpawnWithEgg((IEntityLivingData)null);
            world.spawnEntityInWorld((Entity)entityliving);
            entityliving.playLivingSound();
        }
        return entity;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(final int i, final int j) {
        return Items.spawn_egg.getIconFromDamageForRenderPass(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (final LOTREntities.SpawnEggInfo info : LOTREntities.spawnEggs.values()) {
            list.add(new ItemStack(item, 1, info.spawnedID));
        }
    }
}

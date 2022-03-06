// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.Facing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import lotr.common.LOTRLevelData;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.world.structure.LOTRStructures;
import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class LOTRItemStructureSpawner extends Item
{
    @SideOnly(Side.CLIENT)
    private IIcon iconBase;
    @SideOnly(Side.CLIENT)
    private IIcon iconOverlay;
    @SideOnly(Side.CLIENT)
    private IIcon iconVillageBase;
    @SideOnly(Side.CLIENT)
    private IIcon iconVillageOverlay;
    public static int lastStructureSpawnTick;
    
    public LOTRItemStructureSpawner() {
        this.setHasSubtypes(true);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabSpawn);
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        String s = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
        final String structureName = LOTRStructures.getNameFromID(itemstack.getItemDamage());
        if (structureName != null) {
            s = s + " " + StatCollector.translateToLocal("lotr.structure." + structureName + ".name");
        }
        return s;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        this.iconBase = iconregister.registerIcon(this.getIconString() + "_base");
        this.iconOverlay = iconregister.registerIcon(this.getIconString() + "_overlay");
        this.iconVillageBase = iconregister.registerIcon(this.getIconString() + "_village_base");
        this.iconVillageOverlay = iconregister.registerIcon(this.getIconString() + "_village_overlay");
    }
    
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(final int i, final int pass) {
        final LOTRStructures.StructureColorInfo info = LOTRStructures.structureItemSpawners.get(i);
        if (info == null) {
            return this.iconBase;
        }
        if (info.isVillage) {
            if (pass == 0) {
                return this.iconVillageBase;
            }
            return this.iconVillageOverlay;
        }
        else {
            if (pass == 0) {
                return this.iconBase;
            }
            return this.iconOverlay;
        }
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(final ItemStack itemstack, final int pass) {
        final LOTRStructures.StructureColorInfo info = LOTRStructures.structureItemSpawners.get(itemstack.getItemDamage());
        if (info == null) {
            return 16777215;
        }
        if (pass == 0) {
            return info.colorBackground;
        }
        return info.colorForeground;
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, int i, int j, int k, final int side, final float f, final float f1, final float f2) {
        if (world.isClient) {
            return true;
        }
        if (LOTRLevelData.structuresBanned()) {
            entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("chat.lotr.spawnStructure.disabled", new Object[0]));
            return false;
        }
        if (LOTRLevelData.isPlayerBannedForStructures(entityplayer)) {
            entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("chat.lotr.spawnStructure.banned", new Object[0]));
            return false;
        }
        if (LOTRItemStructureSpawner.lastStructureSpawnTick > 0) {
            entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("chat.lotr.spawnStructure.wait", new Object[] { LOTRItemStructureSpawner.lastStructureSpawnTick / 20.0 }));
            return false;
        }
        i += Facing.offsetsXForSide[side];
        j += Facing.offsetsYForSide[side];
        k += Facing.offsetsZForSide[side];
        if (this.spawnStructure(entityplayer, world, itemstack.getItemDamage(), i, j, k) && !entityplayer.capabilities.isCreativeMode) {
            --itemstack.stackSize;
        }
        return true;
    }
    
    private boolean spawnStructure(final EntityPlayer entityplayer, final World world, final int id, final int i, final int j, final int k) {
        if (!LOTRStructures.structureItemSpawners.containsKey(id)) {
            return false;
        }
        final LOTRStructures.IStructureProvider strProvider = LOTRStructures.getStructureForID(id);
        if (strProvider != null) {
            final boolean generated = strProvider.generateStructure(world, entityplayer, i, j, k);
            if (generated) {
                LOTRItemStructureSpawner.lastStructureSpawnTick = 20;
                world.playSoundAtEntity((Entity)entityplayer, "lotr:item.structureSpawner", 1.0f, 1.0f);
            }
            return generated;
        }
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (final LOTRStructures.StructureColorInfo info : LOTRStructures.structureItemSpawners.values()) {
            if (!info.isHidden) {
                list.add(new ItemStack(item, 1, info.spawnedID));
            }
        }
    }
    
    static {
        LOTRItemStructureSpawner.lastStructureSpawnTick = 0;
    }
}

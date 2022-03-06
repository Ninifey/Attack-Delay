// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.tileentity.TileEntity;
import lotr.common.tileentity.LOTRTileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import lotr.common.entity.LOTREntities;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class LOTRItemMobSpawner extends ItemBlock
{
    public LOTRItemMobSpawner(final Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    public int getMetadata(final int i) {
        return 0;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (final LOTREntities.SpawnEggInfo info : LOTREntities.spawnEggs.values()) {
            list.add(new ItemStack(item, 1, info.spawnedID));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        final String entityName = LOTREntities.getStringFromID(itemstack.getItemDamage());
        list.add(entityName);
    }
    
    public boolean placeBlockAt(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, final int i, final int j, final int k, final int side, final float f, final float f1, final float f2, final int metadata) {
        if (super.placeBlockAt(itemstack, entityplayer, world, i, j, k, side, f, f1, f2, metadata)) {
            final TileEntity tileentity = world.getTileEntity(i, j, k);
            if (tileentity != null && tileentity instanceof LOTRTileEntityMobSpawner) {
                ((LOTRTileEntityMobSpawner)tileentity).setEntityClassID(itemstack.getItemDamage());
                ((LOTRTileEntityMobSpawner)tileentity).spawnsPersistentNPCs = true;
            }
            return true;
        }
        return false;
    }
}

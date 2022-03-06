// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.MathHelper;
import java.util.ArrayList;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;

public class LOTRBlockRemains extends Block
{
    public LOTRBlockRemains() {
        super((Material)LOTRMaterialRemains.remains);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
        this.setHardness(3.0f);
        this.setStepSound(Block.soundTypeGravel);
    }
    
    public ArrayList getDrops(final World world, final int i, final int j, final int k, final int metadata, final int fortune) {
        final ArrayList drops = new ArrayList();
        int dropCount = MathHelper.getRandomIntegerInRange(world.rand, 1, 3) + world.rand.nextInt(1 + fortune * 2);
        if (world.rand.nextBoolean()) {
            ++dropCount;
        }
        final IInventory dropInv = (IInventory)new InventoryBasic("remains", true, dropCount * 5);
        LOTRChestContents.fillInventory(dropInv, world.rand, LOTRChestContents.MARSH_REMAINS, dropCount);
        for (int l = 0; l < dropInv.getSizeInventory(); ++l) {
            final ItemStack drop = dropInv.getStackInSlot(l);
            if (drop != null) {
                drops.add(drop);
            }
        }
        return drops;
    }
    
    public void harvestBlock(final World world, final EntityPlayer entityplayer, final int i, final int j, final int k, final int l) {
        super.harvestBlock(world, entityplayer, i, j, k, l);
        if (!world.isClient) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.mineRemains);
        }
    }
}

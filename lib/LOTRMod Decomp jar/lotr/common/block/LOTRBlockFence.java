// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;

public class LOTRBlockFence extends BlockFence
{
    private Block plankBlock;
    
    public LOTRBlockFence(final Block planks) {
        super("", Material.wood);
        this.setHardness(2.0f);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeWood);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
        this.plankBlock = planks;
    }
    
    public boolean canPlaceTorchOnTop(final World world, final int i, final int j, final int k) {
        return true;
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    public int getRenderType() {
        if (LOTRMod.proxy.isClient()) {
            return LOTRMod.proxy.getFenceRenderID();
        }
        return super.getRenderType();
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return this.plankBlock.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        final List plankTypes = new ArrayList();
        this.plankBlock.getSubBlocks(Item.getItemFromBlock(this.plankBlock), this.plankBlock.getCreativeTabToDisplayOn(), plankTypes);
        for (int j = 0; j < plankTypes.size(); ++j) {
            final Object obj = plankTypes.get(j);
            if (obj instanceof ItemStack) {
                final int meta = ((ItemStack)obj).getItemDamage();
                list.add(new ItemStack((Block)this, 1, meta));
            }
        }
    }
}

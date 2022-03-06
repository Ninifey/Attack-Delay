// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;
import net.minecraft.item.Item;
import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockDoor;

public class LOTRBlockDoor extends BlockDoor
{
    public LOTRBlockDoor() {
        this(Material.wood);
        this.setStepSound(Block.soundTypeWood);
        this.setHardness(3.0f);
    }
    
    public LOTRBlockDoor(final Material material) {
        super(material);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabUtil);
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return ((i & 0x8) != 0x0) ? null : Item.getItemFromBlock((Block)this);
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(final World world, final int i, final int j, final int k) {
        return Item.getItemFromBlock((Block)this);
    }
    
    @SideOnly(Side.CLIENT)
    public String getItemIconName() {
        return this.getTextureName();
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getDoorRenderID();
    }
}

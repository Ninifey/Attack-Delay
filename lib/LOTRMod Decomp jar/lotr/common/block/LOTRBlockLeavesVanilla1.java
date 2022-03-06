// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import java.util.Random;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.block.BlockOldLeaf;

public class LOTRBlockLeavesVanilla1 extends LOTRBlockLeavesBase
{
    public LOTRBlockLeavesVanilla1() {
        super(true, "lotr:leavesV1");
        this.setLeafNames("oak", "spruce", "birch", "jungle");
        this.setSeasonal(true, false, true, false);
    }
    
    @Override
    public String[] func_150125_e() {
        return BlockOldLeaf.field_150131_O;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderColor(final int i) {
        final int meta = i & 0x3;
        if (meta == 0) {
            return ColorizerFoliage.getFoliageColorBasic();
        }
        return super.getRenderColor(i);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public int colorMultiplier(final IBlockAccess world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k) & 0x3;
        if (meta == 0) {
            return LOTRBlockLeavesBase.getBiomeLeafColor(world, i, j, k);
        }
        return super.colorMultiplier(world, i, j, k);
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return Item.getItemFromBlock(Blocks.sapling);
    }
    
    @Override
    protected int getSaplingChance(final int meta) {
        if (meta == 3) {
            return 30;
        }
        return super.getSaplingChance(meta);
    }
}

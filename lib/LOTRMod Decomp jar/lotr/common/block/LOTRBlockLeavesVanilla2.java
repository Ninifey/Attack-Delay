// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import java.util.Random;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.block.BlockNewLeaf;

public class LOTRBlockLeavesVanilla2 extends LOTRBlockLeavesBase
{
    public LOTRBlockLeavesVanilla2() {
        super(true, "lotr:leavesV2");
        this.setLeafNames("acacia", "darkOak");
        this.setSeasonal(false, true);
    }
    
    @Override
    public String[] func_150125_e() {
        return BlockNewLeaf.field_150133_O;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderColor(final int i) {
        final int meta = i & 0x3;
        if (meta == 0 || meta == 1) {
            return ColorizerFoliage.getFoliageColorBasic();
        }
        return super.getRenderColor(i);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public int colorMultiplier(final IBlockAccess world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k) & 0x3;
        if (meta == 0 || meta == 1) {
            return LOTRBlockLeavesBase.getBiomeLeafColor(world, i, j, k);
        }
        return super.colorMultiplier(world, i, j, k);
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return Item.getItemFromBlock(Blocks.sapling);
    }
    
    public int damageDropped(final int i) {
        return super.damageDropped(i) + 4;
    }
    
    @Override
    protected int getSaplingChance(final int meta) {
        if (meta == 1) {
            return 12;
        }
        return super.getSaplingChance(meta);
    }
    
    public int getDamageValue(final World world, final int i, final int j, final int k) {
        return super.damageDropped(world.getBlockMetadata(i, j, k));
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.world.IBlockAccess;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRBlockLeaves extends LOTRBlockLeavesBase
{
    public LOTRBlockLeaves() {
        this.setLeafNames("shirePine", "mallorn", "mirkOak", "mirkOakRed");
        this.setSeasonal(false, true, false, false);
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        super.randomDisplayTick(world, i, j, k, random);
        String s = null;
        final int metadata = world.getBlockMetadata(i, j, k) & 0x3;
        if (metadata == 1 && random.nextInt(75) == 0) {
            s = "leafGold";
        }
        else if (metadata == 2 && random.nextInt(250) == 0) {
            s = "leafMirk";
        }
        else if (metadata == 3 && random.nextInt(40) == 0) {
            s = "leafRed";
        }
        if (s != null) {
            final double d = i + random.nextFloat();
            final double d2 = j - 0.05;
            final double d3 = k + random.nextFloat();
            final double d4 = -0.1 + random.nextFloat() * 0.2f;
            final double d5 = -0.03 - random.nextFloat() * 0.02f;
            final double d6 = -0.1 + random.nextFloat() * 0.2f;
            LOTRMod.proxy.spawnParticle(s, d, d2, d3, d4, d5, d6);
        }
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return Item.getItemFromBlock(LOTRMod.sapling);
    }
    
    @Override
    protected void addSpecialLeafDrops(final ArrayList drops, final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        if ((meta & 0x3) == 0x1) {
            final int nutChance = this.calcFortuneModifiedDropChance(100, fortune);
            if (world.rand.nextInt(nutChance) == 0) {
                drops.add(new ItemStack(LOTRMod.mallornNut));
            }
        }
    }
    
    public int getLightOpacity(final IBlockAccess world, final int i, final int j, final int k) {
        final int l = world.getBlockMetadata(i, j, k) & 0x3;
        if (l == 2 || l == 3) {
            return 255;
        }
        return super.getLightOpacity(world, i, j, k);
    }
}

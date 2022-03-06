// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import java.util.ArrayList;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import java.util.Random;

public class LOTRBlockLeaves7 extends LOTRBlockLeavesBase
{
    public LOTRBlockLeaves7() {
        this.setLeafNames("aspen", "greenOak", "lairelosse", "almond");
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return Item.getItemFromBlock(LOTRMod.sapling7);
    }
    
    @Override
    protected void addSpecialLeafDrops(final ArrayList drops, final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        if ((meta & 0x3) == 0x3) {
            final int fruitChance = this.calcFortuneModifiedDropChance(12, fortune);
            if (world.rand.nextInt(fruitChance) == 0) {
                drops.add(new ItemStack(LOTRMod.almond));
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        super.randomDisplayTick(world, i, j, k, random);
        String s = null;
        final int metadata = world.getBlockMetadata(i, j, k) & 0x3;
        if (metadata == 1 && random.nextInt(150) == 0) {
            s = "leafGreen";
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
}

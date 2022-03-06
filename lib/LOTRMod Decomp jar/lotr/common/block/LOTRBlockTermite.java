// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import java.util.Random;
import net.minecraft.entity.Entity;
import lotr.common.entity.animal.LOTREntityTermite;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;

public class LOTRBlockTermite extends Block
{
    public LOTRBlockTermite() {
        super(Material.ground);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
        this.setHardness(0.5f);
        this.setResistance(3.0f);
    }
    
    public void onBlockDestroyedByPlayer(final World world, final int i, final int j, final int k, final int meta) {
        if (!world.isClient && meta == 0 && world.rand.nextBoolean()) {
            for (int termites = 1 + world.rand.nextInt(3), l = 0; l < termites; ++l) {
                this.spawnTermite(world, i, j, k);
            }
        }
    }
    
    public void onBlockExploded(final World world, final int i, final int j, final int k, final Explosion explosion) {
        final int meta = world.getBlockMetadata(i, j, k);
        if (!world.isClient && meta == 0 && world.rand.nextBoolean()) {
            this.spawnTermite(world, i, j, k);
        }
        super.onBlockExploded(world, i, j, k, explosion);
    }
    
    private void spawnTermite(final World world, final int i, final int j, final int k) {
        final LOTREntityTermite termite = new LOTREntityTermite(world);
        termite.setLocationAndAngles(i + 0.5, (double)j, k + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
        world.spawnEntityInWorld((Entity)termite);
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    public int quantityDropped(final int meta, final int fortune, final Random random) {
        return (meta == 1) ? 1 : 0;
    }
    
    protected ItemStack createStackedBlock(final int i) {
        return new ItemStack((Block)this, 1, 1);
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i <= 1; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class LOTRBlockDolGuldurTable extends LOTRBlockCraftingTable
{
    @SideOnly(Side.CLIENT)
    private IIcon[] tableIcons;
    
    public LOTRBlockDolGuldurTable() {
        super(Material.rock, LOTRFaction.DOL_GULDUR, 30);
        this.setStepSound(Block.soundTypeStone);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (i == 1) {
            return this.tableIcons[1];
        }
        if (i == 0) {
            return LOTRMod.brick2.getIcon(0, 8);
        }
        return this.tableIcons[0];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        (this.tableIcons = new IIcon[2])[0] = iconregister.registerIcon(this.getTextureName() + "_side");
        this.tableIcons[1] = iconregister.registerIcon(this.getTextureName() + "_top");
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        if (random.nextInt(20) == 0) {
            for (int l = 0; l < 16; ++l) {
                final double d = i + 0.25 + random.nextFloat() * 0.5f;
                final double d2 = j + 1.0;
                final double d3 = k + 0.25 + random.nextFloat() * 0.5f;
                final double d4 = -0.05 + random.nextFloat() * 0.1;
                final double d5 = 0.1 + random.nextFloat() * 0.1;
                final double d6 = -0.05 + random.nextFloat() * 0.1;
                LOTRMod.proxy.spawnParticle("morgulPortal", d, d2, d3, d4, d5, d6);
            }
        }
    }
}

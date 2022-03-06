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

public class LOTRBlockMorgulTable extends LOTRBlockCraftingTable
{
    @SideOnly(Side.CLIENT)
    private IIcon[] tableIcons;
    
    public LOTRBlockMorgulTable() {
        super(Material.rock, LOTRFaction.MORDOR, 1);
        this.setStepSound(Block.soundTypeStone);
        this.setLightLevel(0.5f);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (i == 1) {
            return this.tableIcons[1];
        }
        if (i == 0) {
            return LOTRMod.rock.getIcon(2, 0);
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
        for (int l = 0; l < 2; ++l) {
            final double d = i + 0.25 + random.nextFloat() * 0.5f;
            final double d2 = j + 1.0;
            final double d3 = k + 0.25 + random.nextFloat() * 0.5f;
            world.spawnParticle("flame", d, d2, d3, 0.0, 0.0, 0.0);
        }
    }
}

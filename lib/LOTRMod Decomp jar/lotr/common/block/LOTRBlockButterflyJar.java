// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.entity.animal.LOTREntityButterfly;
import net.minecraft.entity.Entity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class LOTRBlockButterflyJar extends LOTRBlockAnimalJar
{
    @SideOnly(Side.CLIENT)
    private IIcon glassIcon;
    @SideOnly(Side.CLIENT)
    private IIcon lidIcon;
    
    public LOTRBlockButterflyJar() {
        super(Material.glass);
        this.setBlockBounds(0.1875f, 0.0f, 0.1875f, 0.8125f, 0.75f, 0.8125f);
        this.setHardness(0.0f);
        this.setStepSound(Block.soundTypeGlass);
    }
    
    @Override
    public boolean canCapture(final Entity entity) {
        return entity instanceof LOTREntityButterfly;
    }
    
    @Override
    public float getJarEntityHeight() {
        return 0.25f;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (i == -1) {
            return this.lidIcon;
        }
        return this.glassIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.glassIcon = iconregister.registerIcon(this.getTextureName() + "_glass");
        this.lidIcon = iconregister.registerIcon(this.getTextureName() + "_lid");
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 1;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getButterflyJarRenderID();
    }
}

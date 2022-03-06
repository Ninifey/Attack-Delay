// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import lotr.common.LOTRMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import lotr.common.entity.animal.LOTREntityBird;
import net.minecraft.entity.Entity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class LOTRBlockBirdCage extends LOTRBlockAnimalJar
{
    @SideOnly(Side.CLIENT)
    private IIcon[] sideIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] topIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] baseIcons;
    private String[] cageTypes;
    
    public LOTRBlockBirdCage() {
        super(Material.glass);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.setHardness(0.5f);
        this.setStepSound(Block.soundTypeMetal);
        this.setCageTypes("bronze", "iron", "silver", "gold");
    }
    
    protected void setCageTypes(final String... s) {
        this.cageTypes = s;
    }
    
    @Override
    public boolean canCapture(final Entity entity) {
        return entity instanceof LOTREntityBird;
    }
    
    @Override
    public float getJarEntityHeight() {
        return 0.5f;
    }
    
    @Override
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        return true;
    }
    
    public static boolean isSameBirdCage(final IBlockAccess world, final int i, final int j, final int k, final int i1, final int j1, final int k1) {
        final Block block = world.getBlock(i, j, k);
        final int meta = world.getBlockMetadata(i, j, k);
        final Block block2 = world.getBlock(i1, j1, k1);
        final int meta2 = world.getBlockMetadata(i1, j1, k1);
        return block instanceof LOTRBlockBirdCage && block == block2 && meta == meta2;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        if (j >= this.cageTypes.length) {
            j = 0;
        }
        if (i == 0 || i == 1) {
            return this.topIcons[j];
        }
        if (i == -1) {
            return this.baseIcons[j];
        }
        return this.sideIcons[j];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.sideIcons = new IIcon[this.cageTypes.length];
        this.topIcons = new IIcon[this.cageTypes.length];
        this.baseIcons = new IIcon[this.cageTypes.length];
        for (int i = 0; i < this.cageTypes.length; ++i) {
            this.sideIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.cageTypes[i] + "_side");
            this.topIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.cageTypes[i] + "_top");
            this.baseIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.cageTypes[i] + "_base");
        }
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getBirdCageRenderID();
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < this.cageTypes.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}

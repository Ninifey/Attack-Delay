// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import lotr.common.LOTRDamage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class LOTRBlockTallGrass extends LOTRBlockGrass
{
    @SideOnly(Side.CLIENT)
    private IIcon[] grassIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] overlayIcons;
    public static String[] grassNames;
    public static boolean[] grassOverlay;
    
    public void onEntityCollidedWithBlock(final World world, final int i, final int j, final int k, final Entity entity) {
        final int meta = world.getBlockMetadata(i, j, k);
        if ((meta == 3 && entity.isSprinting()) || (meta == 4 && entity instanceof EntityPlayer)) {
            boolean bootsLegs = false;
            if (entity instanceof EntityLivingBase) {
                final EntityLivingBase living = (EntityLivingBase)entity;
                if (living.getEquipmentInSlot(1) != null && living.getEquipmentInSlot(2) != null) {
                    bootsLegs = true;
                }
            }
            if (!bootsLegs) {
                entity.attackEntityFrom(LOTRDamage.plantHurt, 0.25f);
            }
        }
    }
    
    @Override
    public ArrayList getDrops(final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        if (meta == 3) {
            final ArrayList thistles = new ArrayList();
            thistles.add(new ItemStack((Block)this, 1, 3));
            return thistles;
        }
        return super.getDrops(world, i, j, k, meta, fortune);
    }
    
    @SideOnly(Side.CLIENT)
    public int getBlockColor() {
        return Blocks.tallgrass.getBlockColor();
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderColor(final int meta) {
        return this.getBlockColor();
    }
    
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(final IBlockAccess world, final int i, final int j, final int k) {
        return world.getBiomeGenForCoords(i, k).getBiomeGrassColor(i, j, k);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        if (j >= LOTRBlockTallGrass.grassNames.length) {
            j = 0;
        }
        if (i == -1) {
            return this.overlayIcons[j];
        }
        return this.grassIcons[j];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.grassIcons = new IIcon[LOTRBlockTallGrass.grassNames.length];
        this.overlayIcons = new IIcon[LOTRBlockTallGrass.grassNames.length];
        for (int i = 0; i < LOTRBlockTallGrass.grassNames.length; ++i) {
            this.grassIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + LOTRBlockTallGrass.grassNames[i]);
            if (LOTRBlockTallGrass.grassOverlay[i]) {
                this.overlayIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + LOTRBlockTallGrass.grassNames[i] + "_overlay");
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int j = 0; j < LOTRBlockTallGrass.grassNames.length; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }
    
    static {
        LOTRBlockTallGrass.grassNames = new String[] { "short", "flower", "wheat", "thistle", "nettle", "fernsprout" };
        LOTRBlockTallGrass.grassOverlay = new boolean[] { false, true, true, true, false, false };
    }
}

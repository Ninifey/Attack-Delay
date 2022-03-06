// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.tileentity.LOTRTileEntityGulduril;
import net.minecraft.tileentity.TileEntity;
import lotr.common.LOTRMod;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import lotr.client.render.LOTRConnectedTextures;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class LOTRBlockOreStorage extends LOTRBlockOreStorageBase implements LOTRConnectedBlock
{
    @SideOnly(Side.CLIENT)
    private IIcon orcSteelSideIcon;
    @SideOnly(Side.CLIENT)
    private IIcon morgulSteelSideIcon;
    
    public LOTRBlockOreStorage() {
        this.setOreStorageNames("copper", "tin", "bronze", "silver", "mithril", "orcSteel", "quendite", "dwarfSteel", "galvorn", "urukSteel", "naurite", "gulduril", "morgulSteel", "sulfur", "saltpeter", "blueDwarfSteel");
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(final IIconRegister iconregister) {
        super.oreStorageIcons = new IIcon[super.oreStorageNames.length];
        for (int i = 0; i < super.oreStorageNames.length; ++i) {
            if (i == 4) {
                LOTRConnectedTextures.registerConnectedIcons(iconregister, this, i, false);
            }
            else {
                super.oreStorageIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + super.oreStorageNames[i]);
            }
        }
        this.orcSteelSideIcon = iconregister.registerIcon(this.getTextureName() + "_orcSteel_side");
        this.morgulSteelSideIcon = iconregister.registerIcon(this.getTextureName() + "_morgulSteel_side");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        final int meta = world.getBlockMetadata(i, j, k);
        if (meta == 4) {
            return LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
        }
        return super.getIcon(world, i, j, k, side);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final int i, final int j) {
        if (j == 4) {
            return LOTRConnectedTextures.getConnectedIconItem(this, j);
        }
        if (j == 5 && i > 1) {
            return this.orcSteelSideIcon;
        }
        if (j == 12 && i > 1) {
            return this.morgulSteelSideIcon;
        }
        return super.getIcon(i, j);
    }
    
    @Override
    public String getConnectedName(final int meta) {
        return super.textureName + "_" + super.oreStorageNames[meta];
    }
    
    @Override
    public boolean areBlocksConnected(final IBlockAccess world, final int i, final int j, final int k, final int i1, final int j1, final int k1) {
        return Checks.matchBlockAndMeta(this, world, i, j, k, i1, j1, k1);
    }
    
    public boolean isFireSource(final World world, final int i, final int j, final int k, final ForgeDirection side) {
        return world.getBlockMetadata(i, j, k) == 13 && side == ForgeDirection.UP;
    }
    
    public int getLightValue(final IBlockAccess world, final int i, final int j, final int k) {
        if (world.getBlockMetadata(i, j, k) == 6) {
            return LOTRMod.oreQuendite.getLightValue();
        }
        if (world.getBlockMetadata(i, j, k) == 10) {
            return LOTRMod.oreNaurite.getLightValue();
        }
        if (world.getBlockMetadata(i, j, k) == 11) {
            return LOTRMod.oreGulduril.getLightValue();
        }
        return 0;
    }
    
    public boolean hasTileEntity(final int metadata) {
        return metadata == 11;
    }
    
    public TileEntity createTileEntity(final World world, final int metadata) {
        if (this.hasTileEntity(metadata)) {
            return new LOTRTileEntityGulduril();
        }
        return null;
    }
}

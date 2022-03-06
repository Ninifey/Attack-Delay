// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import lotr.common.world.map.LOTRConquestGrid;
import lotr.common.LOTRSquadrons;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityCommandTable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.BlockContainer;

public class LOTRBlockCommandTable extends BlockContainer
{
    @SideOnly(Side.CLIENT)
    private IIcon topIcon;
    @SideOnly(Side.CLIENT)
    private IIcon sideIcon;
    
    public LOTRBlockCommandTable() {
        super(Material.iron);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabUtil);
        this.setHardness(2.5f);
        this.setStepSound(Block.soundTypeMetal);
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityCommandTable();
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getCommandTableRenderID();
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        if (entityplayer.isSneaking()) {
            final LOTRTileEntityCommandTable table = (LOTRTileEntityCommandTable)world.getTileEntity(i, j, k);
            if (table != null) {
                if (!world.isClient) {
                    table.toggleZoomExp();
                }
                return true;
            }
        }
        final ItemStack itemstack = entityplayer.getCurrentEquippedItem();
        if (itemstack != null && itemstack.getItem() instanceof LOTRSquadrons.SquadronItem) {
            entityplayer.openGui((Object)LOTRMod.instance, 33, world, 0, 0, 0);
            if (!world.isClient) {
                world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, ((Block)this).stepSound.getDigResourcePath(), (((Block)this).stepSound.getVolume() + 1.0f) / 2.0f, ((Block)this).stepSound.getFrequency() * 0.5f);
            }
            return true;
        }
        if (LOTRConquestGrid.conquestEnabled(world)) {
            entityplayer.openGui((Object)LOTRMod.instance, 60, world, 0, 0, 0);
            if (!world.isClient) {
                world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, ((Block)this).stepSound.getDigResourcePath(), (((Block)this).stepSound.getVolume() + 1.0f) / 2.0f, ((Block)this).stepSound.getFrequency() * 0.5f);
            }
            return true;
        }
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (i == 1 || i == 0) {
            return this.topIcon;
        }
        return this.sideIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.sideIcon = iconregister.registerIcon(this.getTextureName() + "_side");
        this.topIcon = iconregister.registerIcon(this.getTextureName() + "_top");
    }
}

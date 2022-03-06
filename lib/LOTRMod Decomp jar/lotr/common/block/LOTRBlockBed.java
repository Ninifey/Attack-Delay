// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import java.util.Random;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.Direction;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.block.BlockBed;

public class LOTRBlockBed extends BlockBed
{
    public Item bedItem;
    private Block bedBottomBlock;
    private int bedBottomMetadata;
    @SideOnly(Side.CLIENT)
    private IIcon[] bedIconsEnd;
    @SideOnly(Side.CLIENT)
    private IIcon[] bedIconsSide;
    @SideOnly(Side.CLIENT)
    private IIcon[] bedIconsTop;
    
    public LOTRBlockBed(final Block block, final int k) {
        this.bedBottomBlock = block;
        this.bedBottomMetadata = k;
        this.setHardness(0.2f);
        this.setStepSound(Block.soundTypeWood);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (i == 0) {
            return this.bedBottomBlock.getIcon(0, this.bedBottomMetadata);
        }
        final int k = func_149895_l(j);
        final int l = Direction.bedDirection[k][i];
        final int i2 = func_149975_b(j) ? 1 : 0;
        return ((i2 != 1 || l != 2) && (i2 != 0 || l != 3)) ? ((l != 5 && l != 4) ? this.bedIconsTop[i2] : this.bedIconsSide[i2]) : this.bedIconsEnd[i2];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.bedIconsTop = new IIcon[] { iconregister.registerIcon(this.getTextureName() + "_feet_top"), iconregister.registerIcon(this.getTextureName() + "_head_top") };
        this.bedIconsEnd = new IIcon[] { iconregister.registerIcon(this.getTextureName() + "_feet_end"), iconregister.registerIcon(this.getTextureName() + "_head_end") };
        this.bedIconsSide = new IIcon[] { iconregister.registerIcon(this.getTextureName() + "_feet_side"), iconregister.registerIcon(this.getTextureName() + "_head_side") };
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return func_149975_b(i) ? null : this.bedItem;
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(final World world, final int i, final int j, final int k) {
        return this.bedItem;
    }
    
    public boolean isBed(final IBlockAccess world, final int i, final int j, final int k, final EntityLivingBase entity) {
        return true;
    }
}

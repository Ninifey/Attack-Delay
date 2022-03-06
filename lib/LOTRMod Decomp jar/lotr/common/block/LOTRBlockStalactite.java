// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.util.DamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.block.material.Material;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Random;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;

public class LOTRBlockStalactite extends Block
{
    private Block modelBlock;
    private int modelMeta;
    
    public LOTRBlockStalactite(final Block block, final int meta) {
        super(block.getMaterial());
        this.modelBlock = block;
        this.modelMeta = meta;
        this.setStepSound(this.modelBlock.stepSound);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
        this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 1.0f, 0.75f);
    }
    
    public float getBlockHardness(final World world, final int i, final int j, final int k) {
        return this.modelBlock.getBlockHardness(world, i, j, k);
    }
    
    public float getExplosionResistance(final Entity entity) {
        return this.modelBlock.getExplosionResistance(entity);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return this.modelBlock.getIcon(i, this.modelMeta);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
    
    public int quantityDropped(final Random random) {
        return this.modelBlock.quantityDropped(random);
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    public boolean canSilkHarvest(final World world, final EntityPlayer entityplayer, final int i, final int j, final int k, final int meta) {
        return true;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getStalactiteRenderID();
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        final int metadata = world.getBlockMetadata(i, j, k);
        if (metadata == 0) {
            return world.getBlock(i, j + 1, k).isSideSolid((IBlockAccess)world, i, j + 1, k, ForgeDirection.DOWN);
        }
        return metadata == 1 && world.getBlock(i, j - 1, k).isSideSolid((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP);
    }
    
    public boolean canReplace(final World world, final int i, final int j, final int k, final int side, final ItemStack itemstack) {
        final int metadata = itemstack.getItemDamage();
        if (metadata == 0) {
            return world.getBlock(i, j + 1, k).isSideSolid((IBlockAccess)world, i, j + 1, k, ForgeDirection.DOWN);
        }
        return metadata == 1 && world.getBlock(i, j - 1, k).isSideSolid((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP);
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        if (!this.canBlockStay(world, i, j, k)) {
            this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int j = 0; j <= 1; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        if (random.nextInt(50) == 0 && world.getBlockMetadata(i, j, k) == 0) {
            final Block above = world.getBlock(i, j + 1, k);
            if (above.isOpaqueCube() && above.getMaterial() == Material.rock) {
                world.spawnParticle("dripWater", i + 0.6, (double)j, k + 0.6, 0.0, 0.0, 0.0);
            }
        }
    }
    
    public void onFallenUpon(final World world, final int i, final int j, final int k, final Entity entity, final float fallDistance) {
        if (entity instanceof EntityLivingBase && world.getBlockMetadata(i, j, k) == 1) {
            final int damage = (int)(fallDistance * 2.0f) + 1;
            entity.attackEntityFrom(DamageSource.fall, (float)damage);
        }
    }
}

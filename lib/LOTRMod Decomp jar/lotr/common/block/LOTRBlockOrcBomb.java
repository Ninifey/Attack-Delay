// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.item.Item;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import lotr.common.entity.item.LOTREntityOrcBomb;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.IBlockAccess;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class LOTRBlockOrcBomb extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon[] orcBombIcons;
    
    public LOTRBlockOrcBomb() {
        super(Material.iron);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabCombat);
        this.setHardness(3.0f);
        this.setResistance(0.0f);
        this.setStepSound(Block.soundTypeMetal);
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderColor(final int i) {
        final int strength = getBombStrengthLevel(i);
        if (strength == 1) {
            return 11974326;
        }
        if (strength == 2) {
            return 7829367;
        }
        return 16777215;
    }
    
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(final IBlockAccess world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        final int strength = getBombStrengthLevel(meta);
        if (strength == 1) {
            return 11974326;
        }
        if (strength == 2) {
            return 7829367;
        }
        return 16777215;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        final boolean isFire = isFireBomb(j);
        if (i == -1) {
            return this.orcBombIcons[2];
        }
        if (i == 1) {
            return isFire ? this.orcBombIcons[4] : this.orcBombIcons[1];
        }
        return isFire ? this.orcBombIcons[3] : this.orcBombIcons[0];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        (this.orcBombIcons = new IIcon[5])[0] = iconregister.registerIcon(this.getTextureName() + "_side");
        this.orcBombIcons[1] = iconregister.registerIcon(this.getTextureName() + "_top");
        this.orcBombIcons[2] = iconregister.registerIcon(this.getTextureName() + "_handle");
        this.orcBombIcons[3] = iconregister.registerIcon(this.getTextureName() + "_fire_side");
        this.orcBombIcons[4] = iconregister.registerIcon(this.getTextureName() + "_fire_top");
    }
    
    public static int getBombStrengthLevel(final int meta) {
        return meta & 0x7;
    }
    
    public static boolean isFireBomb(final int meta) {
        return (meta & 0x8) != 0x0;
    }
    
    public void onBlockAdded(final World world, final int i, final int j, final int k) {
        super.onBlockAdded(world, i, j, k);
        if (world.isBlockIndirectlyGettingPowered(i, j, k)) {
            this.onBlockDestroyedByPlayer(world, i, j, k, -1);
            world.setBlockToAir(i, j, k);
        }
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        if (block.getMaterial() != Material.air && block.canProvidePower() && world.isBlockIndirectlyGettingPowered(i, j, k)) {
            this.onBlockDestroyedByPlayer(world, i, j, k, -1);
            world.setBlockToAir(i, j, k);
        }
    }
    
    public void onBlockExploded(final World world, final int i, final int j, final int k, final Explosion explosion) {
        if (!world.isClient) {
            final int meta = world.getBlockMetadata(i, j, k);
            final LOTREntityOrcBomb bomb = new LOTREntityOrcBomb(world, i + 0.5f, j + 0.5f, k + 0.5f, explosion.getExplosivePlacedBy());
            bomb.setBombStrengthLevel(meta);
            bomb.setFuseFromExplosion();
            bomb.droppedByPlayer = true;
            world.spawnEntityInWorld((Entity)bomb);
        }
        super.onBlockExploded(world, i, j, k, explosion);
    }
    
    public void onBlockDestroyedByPlayer(final World world, final int i, final int j, final int k, int meta) {
        if (!world.isClient && meta == -1) {
            meta = world.getBlockMetadata(i, j, k);
            final LOTREntityOrcBomb bomb = new LOTREntityOrcBomb(world, i + 0.5f, j + 0.5f, k + 0.5f, null);
            bomb.setBombStrengthLevel(meta);
            bomb.droppedByPlayer = true;
            world.spawnEntityInWorld((Entity)bomb);
            world.playSoundAtEntity((Entity)bomb, "game.tnt.primed", 1.0f, 1.0f);
        }
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int l, final float f, final float f1, final float f2) {
        if (entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() == LOTRMod.orcTorchItem) {
            this.onBlockDestroyedByPlayer(world, i, j, k, -1);
            world.setBlockToAir(i, j, k);
            return true;
        }
        return false;
    }
    
    public boolean canDropFromExplosion(final Explosion explosion) {
        return false;
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getOrcBombRenderID();
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i <= 1; ++i) {
            for (int j = 0; j <= 2; ++j) {
                list.add(new ItemStack(item, 1, j + i * 8));
            }
        }
    }
}

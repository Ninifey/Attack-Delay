// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.block.material.MaterialLogic;
import net.minecraft.block.material.MapColor;
import net.minecraft.world.Explosion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.Entity;
import lotr.common.entity.LOTREntityFallingFireJar;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.BlockFalling;

public class LOTRBlockRhunFireJar extends BlockFalling
{
    public static int renderingStage;
    public static final int renderBase = 1;
    public static final int renderNeck = 2;
    public static final int renderLid = 3;
    public static final int renderCap = 4;
    public static final int renderCrown = 5;
    public static final int renderHandle = 6;
    @SideOnly(Side.CLIENT)
    private IIcon iconBaseSide;
    @SideOnly(Side.CLIENT)
    private IIcon iconBaseTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconBaseBottom;
    @SideOnly(Side.CLIENT)
    private IIcon iconNeckSide;
    @SideOnly(Side.CLIENT)
    private IIcon iconLidSide;
    @SideOnly(Side.CLIENT)
    private IIcon iconLidTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconLidBottom;
    @SideOnly(Side.CLIENT)
    private IIcon iconCapSide;
    @SideOnly(Side.CLIENT)
    private IIcon iconCapTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconCapBottom;
    @SideOnly(Side.CLIENT)
    private IIcon iconCrownSide;
    @SideOnly(Side.CLIENT)
    private IIcon iconHandleSide;
    public static boolean explodeOnAdded;
    private static Material materialFireJar;
    
    public LOTRBlockRhunFireJar() {
        super(LOTRBlockRhunFireJar.materialFireJar);
        this.setTickRandomly(true);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabCombat);
        this.setBlockBounds(0.125f, 0.0f, 0.125f, 0.875f, 1.0f, 0.875f);
        this.setHardness(0.5f);
        this.setStepSound(Block.soundTypeStone);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.iconBaseSide = iconregister.registerIcon(this.getTextureName() + "_base_side");
        this.iconBaseTop = iconregister.registerIcon(this.getTextureName() + "_base_top");
        this.iconBaseBottom = iconregister.registerIcon(this.getTextureName() + "_base_bottom");
        this.iconNeckSide = iconregister.registerIcon(this.getTextureName() + "_neck_side");
        this.iconLidSide = iconregister.registerIcon(this.getTextureName() + "_lid_side");
        this.iconLidTop = iconregister.registerIcon(this.getTextureName() + "_lid_top");
        this.iconLidBottom = iconregister.registerIcon(this.getTextureName() + "_lid_bottom");
        this.iconCapSide = iconregister.registerIcon(this.getTextureName() + "_cap_side");
        this.iconCapTop = iconregister.registerIcon(this.getTextureName() + "_cap_top");
        this.iconCapBottom = iconregister.registerIcon(this.getTextureName() + "_cap_bottom");
        this.iconCrownSide = iconregister.registerIcon(this.getTextureName() + "_crown_side");
        this.iconHandleSide = iconregister.registerIcon(this.getTextureName() + "_handle_side");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (LOTRBlockRhunFireJar.renderingStage == 1) {
            return (i == 0) ? this.iconBaseBottom : ((i == 1) ? this.iconBaseTop : this.iconBaseSide);
        }
        if (LOTRBlockRhunFireJar.renderingStage == 2) {
            return this.iconNeckSide;
        }
        if (LOTRBlockRhunFireJar.renderingStage == 3) {
            return (i == 0) ? this.iconLidBottom : ((i == 1) ? this.iconLidTop : this.iconLidSide);
        }
        if (LOTRBlockRhunFireJar.renderingStage == 4) {
            return (i == 0) ? this.iconCapBottom : ((i == 1) ? this.iconCapTop : this.iconCapSide);
        }
        if (LOTRBlockRhunFireJar.renderingStage == 5) {
            return this.iconCrownSide;
        }
        if (LOTRBlockRhunFireJar.renderingStage == 6) {
            return this.iconHandleSide;
        }
        return LOTRMod.brick5.getIcon(i, 11);
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getRhunFireJarRenderID();
    }
    
    public void onBlockAdded(final World world, final int i, final int j, final int k) {
        if (world.isBlockIndirectlyGettingPowered(i, j, k)) {
            if (LOTRBlockRhunFireJar.explodeOnAdded) {
                this.explode(world, i, j, k);
            }
        }
        else {
            super.onBlockAdded(world, i, j, k);
        }
    }
    
    public void updateTick(final World world, final int i, final int j, final int k, final Random random) {
        if (LOTRMod.doFireTick(world)) {
            boolean foundFire = false;
            for (int l = 0; l < 12; ++l) {
                final int range = 1 + random.nextInt(4);
                final int i2 = i + MathHelper.getRandomIntegerInRange(random, -range, range);
                final int j2 = j + MathHelper.getRandomIntegerInRange(random, -range, range);
                final int k2 = k + MathHelper.getRandomIntegerInRange(random, -range, range);
                final Block block = world.getBlock(i2, j2, k2);
                final Material material = block.getMaterial();
                if (material == Material.fire || material == Material.lava) {
                    foundFire = true;
                    break;
                }
            }
            if (foundFire) {
                this.explode(world, i, j, k);
            }
        }
        if (world.getBlock(i, j, k) == this && !world.isClient && BlockFalling.func_149831_e(world, i, j - 1, k) && j >= 0) {
            final int b0 = 32;
            if (world.checkChunksExist(i - b0, j - b0, k - b0, i + b0, j + b0, k + b0)) {
                final EntityFallingBlock falling = new LOTREntityFallingFireJar(world, i + 0.5, j + 0.5, k + 0.5, (Block)this, world.getBlockMetadata(i, j, k));
                this.func_149829_a(falling);
                world.spawnEntityInWorld((Entity)falling);
            }
        }
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        super.onNeighborBlockChange(world, i, j, k, block);
        if (world.getBlock(i, j, k) == this) {
            if (world.isBlockIndirectlyGettingPowered(i, j, k)) {
                this.explode(world, i, j, k);
            }
            else if (!world.isClient) {
                this.updateTick(world, i, j, k, world.rand);
            }
        }
    }
    
    public void explode(final World world, final int i, final int j, final int k) {
        if (!world.isClient) {
            world.createExplosion((Entity)null, (double)i, (double)j, (double)k, 2.0f, false);
            world.setBlockToAir(i, j, k);
            final int range = 2;
            for (int l = 0; l < 64; ++l) {
                final int i2 = i + MathHelper.getRandomIntegerInRange(world.rand, -range, range);
                final int j2 = j + MathHelper.getRandomIntegerInRange(world.rand, -range, range);
                final int k2 = k + MathHelper.getRandomIntegerInRange(world.rand, -range, range);
                final Block block = world.getBlock(i2, j2, k2);
                if ((block.isAir((IBlockAccess)world, i2, j2, k2) || block.isReplaceable((IBlockAccess)world, i2, j2, k2)) && !block.getMaterial().isLiquid()) {
                    world.setBlock(i2, j2, k2, LOTRMod.rhunFire, 0, 3);
                }
            }
        }
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        final ItemStack itemstack = entityplayer.getCurrentEquippedItem();
        if (itemstack != null && itemstack.getItem() instanceof ItemFlintAndSteel) {
            this.explode(world, i, j, k);
            return true;
        }
        return false;
    }
    
    public void onEntityCollidedWithBlock(final World world, final int i, final int j, final int k, final Entity entity) {
        final double speed = Math.sqrt(entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ);
        if (speed >= MathHelper.getRandomDoubleInRange(world.rand, 0.3, 0.8)) {
            this.explode(world, i, j, k);
        }
    }
    
    public void onBlockExploded(final World world, final int i, final int j, final int k, final Explosion explosion) {
        this.explode(world, i, j, k);
        super.onBlockExploded(world, i, j, k, explosion);
    }
    
    public void func_149828_a(final World world, final int i, final int j, final int k, final int meta) {
        this.explode(world, i, j, k);
    }
    
    static {
        LOTRBlockRhunFireJar.renderingStage = 0;
        LOTRBlockRhunFireJar.explodeOnAdded = true;
        LOTRBlockRhunFireJar.materialFireJar = (Material)new MaterialLogic(MapColor.field_151665_m);
    }
}

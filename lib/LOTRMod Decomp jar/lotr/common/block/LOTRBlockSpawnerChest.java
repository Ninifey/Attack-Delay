// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.Item;
import java.util.Random;
import net.minecraft.entity.effect.EntityLightningBolt;
import lotr.common.entity.animal.LOTREntityDesertScorpion;
import lotr.common.entity.npc.LOTREntityHaradPyramidWraith;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import lotr.common.tileentity.LOTRTileEntitySpawnerChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;

public class LOTRBlockSpawnerChest extends BlockChest
{
    private static boolean dropChestItems;
    public final Block chestModel;
    
    public LOTRBlockSpawnerChest(final Block block) {
        super(0);
        this.chestModel = block;
        this.setStepSound(block.stepSound);
        this.setcreativeTab((CreativeTabs)null);
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return (TileEntity)new LOTRTileEntitySpawnerChest();
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return this.chestModel.getIcon(i, j);
    }
    
    public int getRenderType() {
        return this.chestModel.getRenderType();
    }
    
    public float getBlockHardness(final World world, final int i, final int j, final int k) {
        return this.chestModel.getBlockHardness(world, i, j, k);
    }
    
    public float getExplosionResistance(final Entity entity, final World world, final int i, final int j, final int k, final double explosionX, final double explosionY, final double explosionZ) {
        return this.chestModel.getExplosionResistance(entity, world, i, j, k, explosionX, explosionY, explosionZ);
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        this.spawnEntity(world, i, j, k);
        LOTRBlockSpawnerChest.dropChestItems = false;
        if (!world.isClient) {
            final ItemStack[] chestInv = new ItemStack[27];
            final TileEntity tileentity = world.getTileEntity(i, j, k);
            if (tileentity instanceof IInventory) {
                for (int l = 0; l < chestInv.length; ++l) {
                    chestInv[l] = ((IInventory)tileentity).getStackInSlot(l);
                }
            }
            world.setBlock(i, j, k, this.chestModel, world.getBlockMetadata(i, j, k), 3);
            for (int l = 0; l < 27; ++l) {
                ((IInventory)world.getTileEntity(i, j, k)).setInventorySlotContents(l, chestInv[l]);
            }
        }
        return LOTRBlockSpawnerChest.dropChestItems = true;
    }
    
    public void breakBlock(final World world, final int i, final int j, final int k, final Block block, final int meta) {
        if (LOTRBlockSpawnerChest.dropChestItems) {
            this.spawnEntity(world, i, j, k);
            super.breakBlock(world, i, j, k, block, meta);
        }
        else {
            world.removeTileEntity(i, j, k);
        }
    }
    
    private void spawnEntity(final World world, final int i, final int j, final int k) {
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (!(tileentity instanceof LOTRTileEntitySpawnerChest)) {
            return;
        }
        final Entity entity = ((LOTRTileEntitySpawnerChest)tileentity).createMob();
        if (!(entity instanceof EntityLiving)) {
            return;
        }
        final EntityLiving entityliving = (EntityLiving)entity;
        entityliving.setLocationAndAngles(i + 0.5, (double)(j + 1), k + 0.5, 0.0f, 0.0f);
        entityliving.spawnExplosionParticle();
        if (!world.isClient) {
            entityliving.onSpawnWithEgg((IEntityLivingData)null);
            if (entityliving instanceof LOTREntityNPC) {
                ((LOTREntityNPC)entityliving).isNPCPersistent = true;
            }
            world.spawnEntityInWorld((Entity)entityliving);
            world.playSoundAtEntity((Entity)entityliving, "lotr:wraith.spawn", 1.0f, 0.7f + world.rand.nextFloat() * 0.6f);
            if (entityliving instanceof LOTREntityHaradPyramidWraith) {
                for (int l = 0; l < 4; ++l) {
                    final LOTREntityDesertScorpion desertScorpion = new LOTREntityDesertScorpion(world);
                    desertScorpion.setSpawningFromMobSpawner(true);
                    final double d = ((Entity)entityliving).posX - world.rand.nextDouble() * 3.0 + world.rand.nextDouble() * 3.0;
                    final double d2 = ((Entity)entityliving).posY;
                    final double d3 = ((Entity)entityliving).posZ - world.rand.nextDouble() * 3.0 + world.rand.nextDouble() * 3.0;
                    desertScorpion.setLocationAndAngles(d, d2, d3, world.rand.nextFloat() * 360.0f, 0.0f);
                    if (desertScorpion.getCanSpawnHere()) {
                        world.spawnEntityInWorld((Entity)desertScorpion);
                        desertScorpion.setSpawningFromMobSpawner(false);
                    }
                }
                world.addWeatherEffect((Entity)new EntityLightningBolt(world, ((Entity)entityliving).posX, ((Entity)entityliving).posY, ((Entity)entityliving).posZ));
            }
        }
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return Item.getItemFromBlock(this.chestModel);
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(final World world, final int i, final int j, final int k) {
        return Item.getItemFromBlock(this.chestModel);
    }
    
    static {
        LOTRBlockSpawnerChest.dropChestItems = true;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import net.minecraft.tileentity.TileEntitySkull;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.item.LOTREntityBannerWall;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.item.LOTRItemBanner;
import lotr.common.tileentity.LOTRTileEntityArmorStand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import lotr.common.block.LOTRBlockFlowerPot;
import lotr.common.block.LOTRBlockMug;
import lotr.common.recipe.LOTRBrewingRecipes;
import lotr.common.item.LOTRItemMug;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.item.ItemStack;
import lotr.common.tileentity.LOTRTileEntityPlate;
import lotr.common.LOTRFoods;
import java.util.Random;
import lotr.common.tileentity.LOTRTileEntitySpawnerChest;
import net.minecraft.tileentity.TileEntity;
import lotr.common.entity.LOTREntities;
import lotr.common.tileentity.LOTRTileEntityMobSpawner;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.util.MathHelper;
import lotr.common.world.structure2.LOTRStructureTimelapse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class LOTRWorldGenStructureBase extends WorldGenerator
{
    public boolean restrictions;
    public EntityPlayer usingPlayer;
    protected boolean notifyChanges;
    public LOTRStructureTimelapse.ThreadTimelapse threadTimelapse;
    
    public LOTRWorldGenStructureBase(final boolean flag) {
        super(flag);
        this.restrictions = true;
        this.usingPlayer = null;
        this.notifyChanges = flag;
    }
    
    protected int usingPlayerRotation() {
        return MathHelper.floor_double(((Entity)this.usingPlayer).rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
    }
    
    protected void func_150516_a(final World world, final int i, final int j, final int k, final Block block, final int meta) {
        super.func_150516_a(world, i, j, k, block, meta);
        if (block != Blocks.air && this.threadTimelapse != null) {
            this.threadTimelapse.onBlockSet();
        }
    }
    
    protected void setBlockMetadata(final World world, final int i, final int j, final int k, final int meta) {
        world.setBlockMetadata(i, j, k, meta, this.notifyChanges ? 3 : 2);
    }
    
    protected void placeOrcTorch(final World world, final int i, final int j, final int k) {
        this.func_150516_a(world, i, j, k, LOTRMod.orcTorch, 0);
        this.func_150516_a(world, i, j + 1, k, LOTRMod.orcTorch, 1);
    }
    
    protected void placeMobSpawner(final World world, final int i, final int j, final int k, final Class entityClass) {
        this.func_150516_a(world, i, j, k, LOTRMod.mobSpawner, 0);
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity instanceof LOTRTileEntityMobSpawner) {
            ((LOTRTileEntityMobSpawner)tileentity).setEntityClassID(LOTREntities.getEntityIDFromClass(entityClass));
        }
    }
    
    protected void placeSpawnerChest(final World world, final int i, final int j, final int k, final Block block, final int meta, final Class entityClass) {
        this.func_150516_a(world, i, j, k, block, 0);
        this.setBlockMetadata(world, i, j, k, meta);
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity instanceof LOTRTileEntitySpawnerChest) {
            ((LOTRTileEntitySpawnerChest)tileentity).setMobID(entityClass);
        }
    }
    
    protected void placePlate(final World world, final Random random, final int i, final int j, final int k, final Block plateBlock, final LOTRFoods foodList) {
        this.placePlate_do(world, random, i, j, k, plateBlock, foodList, false);
    }
    
    protected void placePlateWithCertainty(final World world, final Random random, final int i, final int j, final int k, final Block plateBlock, final LOTRFoods foodList) {
        this.placePlate_do(world, random, i, j, k, plateBlock, foodList, true);
    }
    
    private void placePlate_do(final World world, final Random random, final int i, final int j, final int k, final Block plateBlock, final LOTRFoods foodList, final boolean certain) {
        if (!certain && random.nextBoolean()) {
            return;
        }
        this.func_150516_a(world, i, j, k, plateBlock, 0);
        if (certain || random.nextBoolean()) {
            final TileEntity tileentity = world.getTileEntity(i, j, k);
            if (tileentity != null && tileentity instanceof LOTRTileEntityPlate) {
                final LOTRTileEntityPlate plate = (LOTRTileEntityPlate)tileentity;
                final ItemStack food = foodList.getRandomFoodForPlate(random);
                if (random.nextInt(4) == 0) {
                    final ItemStack itemStack = food;
                    itemStack.stackSize += 1 + random.nextInt(3);
                }
                plate.setFoodItem(food);
            }
        }
    }
    
    protected void placeBarrel(final World world, final Random random, final int i, final int j, final int k, final int meta, final LOTRFoods foodList) {
        this.placeBarrel(world, random, i, j, k, meta, foodList.getRandomBrewableDrink(random));
    }
    
    protected void placeBarrel(final World world, final Random random, final int i, final int j, final int k, final int meta, ItemStack drink) {
        this.func_150516_a(world, i, j, k, LOTRMod.barrel, meta);
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity instanceof LOTRTileEntityBarrel) {
            final LOTRTileEntityBarrel barrel = (LOTRTileEntityBarrel)tileentity;
            barrel.barrelMode = 2;
            drink = drink.copy();
            LOTRItemMug.setStrengthMeta(drink, MathHelper.getRandomIntegerInRange(random, 1, 3));
            LOTRItemMug.setVessel(drink, LOTRItemMug.Vessel.MUG, true);
            drink.stackSize = MathHelper.getRandomIntegerInRange(random, LOTRBrewingRecipes.BARREL_CAPACITY / 2, LOTRBrewingRecipes.BARREL_CAPACITY);
            barrel.setInventorySlotContents(9, drink);
        }
    }
    
    protected void placeMug(final World world, final Random random, final int i, final int j, final int k, final int meta, final LOTRFoods foodList) {
        this.placeMug(world, random, i, j, k, meta, foodList.getRandomPlaceableDrink(random), foodList);
    }
    
    protected void placeMug(final World world, final Random random, final int i, final int j, final int k, final int meta, final ItemStack drink, final LOTRFoods foodList) {
        this.placeMug(world, random, i, j, k, meta, drink, foodList.getPlaceableDrinkVessels());
    }
    
    protected void placeMug(final World world, final Random random, final int i, final int j, final int k, final int meta, ItemStack drink, final LOTRItemMug.Vessel[] vesselTypes) {
        final LOTRItemMug.Vessel vessel = vesselTypes[random.nextInt(vesselTypes.length)];
        this.func_150516_a(world, i, j, k, vessel.getBlock(), meta);
        if (random.nextInt(3) != 0) {
            drink = drink.copy();
            drink.stackSize = 1;
            if (drink.getItem() instanceof LOTRItemMug && ((LOTRItemMug)drink.getItem()).isBrewable) {
                LOTRItemMug.setStrengthMeta(drink, MathHelper.getRandomIntegerInRange(random, 1, 3));
            }
            LOTRItemMug.setVessel(drink, vessel, true);
            LOTRBlockMug.setMugItem(world, i, j, k, drink, vessel);
        }
    }
    
    protected void placeFlowerPot(final World world, final int i, final int j, final int k, final ItemStack itemstack) {
        this.func_150516_a(world, i, j, k, LOTRMod.flowerPot, 0);
        LOTRBlockFlowerPot.setPlant(world, i, j, k, itemstack);
    }
    
    protected void spawnItemFrame(final World world, final int i, final int j, final int k, final int direction, final ItemStack itemstack) {
        final EntityItemFrame frame = new EntityItemFrame(world, i, j, k, direction);
        frame.setDisplayedItem(itemstack);
        world.spawnEntityInWorld((Entity)frame);
    }
    
    protected void placeArmorStand(final World world, final int i, final int j, final int k, final int direction, final ItemStack[] armor) {
        this.func_150516_a(world, i, j, k, LOTRMod.armorStand, direction);
        this.func_150516_a(world, i, j + 1, k, LOTRMod.armorStand, direction | 0x4);
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity != null && tileentity instanceof LOTRTileEntityArmorStand) {
            final LOTRTileEntityArmorStand armorStand = (LOTRTileEntityArmorStand)tileentity;
            for (int l = 0; l < armor.length; ++l) {
                final ItemStack armorPart = armor[l];
                if (armorPart == null) {
                    armorStand.setInventorySlotContents(l, null);
                }
                else {
                    armorStand.setInventorySlotContents(l, armor[l].copy());
                }
            }
        }
    }
    
    protected void placeBanner(final World world, final int i, final int j, final int k, final int direction, final LOTRItemBanner.BannerType type) {
        final LOTREntityBanner banner = new LOTREntityBanner(world);
        banner.setLocationAndAngles(i + 0.5, (double)j, k + 0.5, direction * 90.0f, 0.0f);
        banner.setBannerType(type);
        world.spawnEntityInWorld((Entity)banner);
    }
    
    protected void placeWallBanner(final World world, final int i, final int j, final int k, final int direction, final LOTRItemBanner.BannerType type) {
        final LOTREntityBannerWall banner = new LOTREntityBannerWall(world, i, j, k, direction);
        banner.setBannerType(type);
        world.spawnEntityInWorld((Entity)banner);
    }
    
    protected void placeNPCRespawner(final LOTREntityNPCRespawner entity, final World world, final int i, final int j, final int k) {
        entity.setLocationAndAngles(i + 0.5, (double)j, k + 0.5, 0.0f, 0.0f);
        world.spawnEntityInWorld((Entity)entity);
    }
    
    protected void setGrassToDirt(final World world, final int i, final int j, final int k) {
        world.getBlock(i, j, k).onPlantGrow(world, i, j, k, i, j, k);
    }
    
    protected void setAir(final World world, final int i, final int j, final int k) {
        this.func_150516_a(world, i, j, k, Blocks.air, 0);
    }
    
    protected void placeSkull(final World world, final Random random, final int i, final int j, final int k) {
        this.func_150516_a(world, i, j, k, Blocks.skull, 1);
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity != null && tileentity instanceof TileEntitySkull) {
            final TileEntitySkull skull = (TileEntitySkull)tileentity;
            skull.func_145903_a(random.nextInt(16));
        }
    }
}

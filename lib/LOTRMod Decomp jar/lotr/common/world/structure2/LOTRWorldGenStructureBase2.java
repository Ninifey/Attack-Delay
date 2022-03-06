// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import java.util.Collection;
import net.minecraft.util.WeightedRandom;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.BlockSlab;
import java.util.Iterator;
import lotr.common.util.LOTRLog;
import lotr.common.entity.item.LOTREntityRugBase;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.EntityLeashKnot;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityCreature;
import lotr.common.tileentity.LOTRTileEntityDwarvenDoor;
import lotr.common.block.LOTRBlockGateDwarvenIthildin;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.tileentity.LOTRTileEntityAnimalJar;
import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import lotr.common.entity.item.LOTREntityBannerWall;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.item.LOTRItemBanner;
import lotr.common.tileentity.LOTRTileEntityWeaponRack;
import lotr.common.tileentity.LOTRTileEntityArmorStand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.block.LOTRBlockFlowerPot;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.item.Item;
import lotr.common.tileentity.LOTRTileEntityKebabStand;
import lotr.common.recipe.LOTRBrewingRecipes;
import lotr.common.item.LOTRItemMug;
import net.minecraft.util.MathHelper;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import lotr.common.tileentity.LOTRTileEntityPlate;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRFoods;
import lotr.common.tileentity.LOTRTileEntitySpawnerChest;
import lotr.common.entity.LOTREntities;
import lotr.common.tileentity.LOTRTileEntityMobSpawner;
import lotr.common.LOTRMod;
import net.minecraft.inventory.IInventory;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.material.Material;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockLever;
import lotr.common.block.LOTRBlockGate;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockBed;
import lotr.common.block.LOTRBlockKebabStand;
import lotr.common.block.LOTRBlockBarrel;
import net.minecraft.block.BlockLadder;
import lotr.common.block.LOTRBlockWeaponRack;
import lotr.common.block.LOTRBlockArmorStand;
import net.minecraft.util.Direction;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockTripWireHook;
import lotr.common.block.LOTRBlockMug;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.init.Blocks;
import lotr.common.block.LOTRBlockForgeBase;
import lotr.common.block.LOTRBlockHobbitOven;
import net.minecraft.block.BlockFurnace;
import lotr.common.block.LOTRBlockSpawnerChest;
import lotr.common.block.LOTRBlockChest;
import net.minecraft.block.BlockChest;
import net.minecraft.block.Block;
import lotr.common.world.structure.LOTRStructures;
import java.util.Random;
import net.minecraft.world.World;
import java.util.HashMap;
import java.util.Map;
import lotr.common.world.structure2.scan.LOTRStructureScan;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import lotr.common.world.village.LOTRVillageGen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class LOTRWorldGenStructureBase2 extends WorldGenerator
{
    public boolean restrictions;
    protected boolean notifyChanges;
    public EntityPlayer usingPlayer;
    public boolean shouldFindSurface;
    public LOTRVillageGen.AbstractInstance villageInstance;
    public LOTRStructureTimelapse.ThreadTimelapse threadTimelapse;
    protected int originX;
    protected int originY;
    protected int originZ;
    private int rotationMode;
    private StructureBoundingBox sbb;
    private LOTRStructureScan currentStrScan;
    private Map<String, BlockAliasPool> scanAliases;
    private Map<String, Float> scanAliasChances;
    
    public LOTRWorldGenStructureBase2(final boolean flag) {
        super(flag);
        this.restrictions = true;
        this.usingPlayer = null;
        this.shouldFindSurface = false;
        this.scanAliases = new HashMap<String, BlockAliasPool>();
        this.scanAliasChances = new HashMap<String, Float>();
        this.notifyChanges = flag;
    }
    
    public final boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        return this.generateWithSetRotation(world, random, i, j, k, random.nextInt(4));
    }
    
    public abstract boolean generateWithSetRotation(final World p0, final Random p1, final int p2, final int p3, final int p4, final int p5);
    
    protected void setupRandomBlocks(final Random random) {
    }
    
    public int usingPlayerRotation() {
        return LOTRStructures.getRotationFromPlayer(this.usingPlayer);
    }
    
    public int getRotationMode() {
        return this.rotationMode;
    }
    
    protected void setOriginAndRotation(final World world, final int i, final int j, final int k, final int rotation, final int shift) {
        this.setOriginAndRotation(world, i, j, k, rotation, shift, 0);
    }
    
    protected void setOriginAndRotation(final World world, int i, int j, int k, final int rotation, final int shift, final int shiftX) {
        --j;
        this.rotationMode = rotation;
        switch (this.getRotationMode()) {
            case 0: {
                k += shift;
                i += shiftX;
                break;
            }
            case 1: {
                i -= shift;
                k += shiftX;
                break;
            }
            case 2: {
                k -= shift;
                i -= shiftX;
                break;
            }
            case 3: {
                i += shift;
                k -= shiftX;
                break;
            }
        }
        this.originX = i;
        this.originY = j;
        this.originZ = k;
        if (this.shouldFindSurface) {
            this.shouldFindSurface = false;
            this.findSurface(world, -shiftX, -shift);
        }
    }
    
    protected void findSurface(final World world, final int i, final int k) {
        for (int j = 8; this.getY(j) >= 0; --j) {
            if (this.isSurface(world, i, j, k)) {
                this.originY = this.getY(j);
                break;
            }
        }
    }
    
    public void setStructureBB(final StructureBoundingBox box) {
        this.sbb = box;
    }
    
    public boolean hasSBB() {
        return this.sbb != null;
    }
    
    private boolean isInSBB(final int i, final int j, final int k) {
        return this.sbb == null || this.sbb.isVecInside(i, j, k);
    }
    
    protected void setBlockAndMetadata(final World world, int i, int j, int k, final Block block, int meta) {
        final int i2 = i;
        final int k2 = k;
        i = this.getX(i2, k2);
        k = this.getZ(i2, k2);
        j = this.getY(j);
        if (!this.isInSBB(i, j, k)) {
            return;
        }
        meta = this.rotateMeta(block, meta);
        super.func_150516_a(world, i, j, k, block, meta);
        if (meta != 0 && (block instanceof BlockChest || block instanceof LOTRBlockChest || block instanceof LOTRBlockSpawnerChest || block instanceof BlockFurnace || block instanceof LOTRBlockHobbitOven || block instanceof LOTRBlockForgeBase)) {
            world.setBlockMetadata(i, j, k, meta, this.notifyChanges ? 3 : 2);
        }
        if (block != Blocks.air && this.threadTimelapse != null) {
            this.threadTimelapse.onBlockSet();
        }
    }
    
    protected int rotateMeta(final Block block, final int meta) {
        if (block instanceof BlockRotatedPillar) {
            final int i = meta & 0x3;
            int j = meta & 0xC;
            if (j == 0) {
                return meta;
            }
            if (this.rotationMode == 0 || this.rotationMode == 2) {
                return meta;
            }
            if (j == 4) {
                j = 8;
            }
            else if (j == 8) {
                j = 4;
            }
            return j | i;
        }
        else {
            if (block instanceof BlockStairs) {
                int i = meta & 0x3;
                final int j = meta & 0x4;
                for (int l = 0; l < this.rotationMode; ++l) {
                    if (i == 2) {
                        i = 1;
                    }
                    else if (i == 1) {
                        i = 3;
                    }
                    else if (i == 3) {
                        i = 0;
                    }
                    else if (i == 0) {
                        i = 2;
                    }
                }
                return j | i;
            }
            if (block instanceof LOTRBlockMug || block instanceof BlockTripWireHook || block instanceof BlockAnvil) {
                int i = meta;
                for (int k = 0; k < this.rotationMode; ++k) {
                    i = Direction.enderEyeMetaToDirection[i];
                }
                return i;
            }
            if (block instanceof LOTRBlockArmorStand) {
                int i = meta & 0x3;
                final int j = meta & 0x4;
                for (int l = 0; l < this.rotationMode; ++l) {
                    i = Direction.enderEyeMetaToDirection[i];
                }
                return j | i;
            }
            if (block instanceof LOTRBlockWeaponRack) {
                int i = meta & 0x3;
                final int j = meta & 0x4;
                for (int l = 0; l < this.rotationMode; ++l) {
                    i = Direction.enderEyeMetaToDirection[i];
                }
                return j | i;
            }
            if (block == Blocks.wall_sign || block instanceof BlockLadder || block instanceof BlockFurnace || block instanceof BlockChest || block instanceof LOTRBlockChest || block instanceof LOTRBlockBarrel || block instanceof LOTRBlockHobbitOven || block instanceof LOTRBlockForgeBase || block instanceof LOTRBlockKebabStand) {
                if (meta == 0 && (block instanceof BlockFurnace || block instanceof BlockChest || block instanceof LOTRBlockChest || block instanceof LOTRBlockHobbitOven || block instanceof LOTRBlockForgeBase)) {
                    return meta;
                }
                int i = meta;
                for (int k = 0; k < this.rotationMode; ++k) {
                    i = Direction.facingToDirection[i];
                    i = Direction.enderEyeMetaToDirection[i];
                    i = Direction.directionToFacing[i];
                }
                return i;
            }
            else {
                if (block == Blocks.standing_sign) {
                    int i = meta;
                    i += this.rotationMode * 4;
                    i &= 0xF;
                    return i;
                }
                if (block instanceof BlockBed) {
                    int i = meta;
                    final boolean flag = meta >= 8;
                    if (flag) {
                        i -= 8;
                    }
                    for (int l = 0; l < this.rotationMode; ++l) {
                        i = Direction.enderEyeMetaToDirection[i];
                    }
                    if (flag) {
                        i += 8;
                    }
                    return i;
                }
                if (block instanceof BlockTorch) {
                    if (meta == 5) {
                        return 5;
                    }
                    int i = meta;
                    for (int k = 0; k < this.rotationMode; ++k) {
                        if (i == 4) {
                            i = 1;
                        }
                        else if (i == 1) {
                            i = 3;
                        }
                        else if (i == 3) {
                            i = 2;
                        }
                        else if (i == 2) {
                            i = 4;
                        }
                    }
                    return i;
                }
                else if (block instanceof BlockDoor) {
                    if ((meta & 0x8) != 0x0) {
                        return meta;
                    }
                    int i = meta & 0x3;
                    final int j = meta & 0x4;
                    for (int l = 0; l < this.rotationMode; ++l) {
                        i = Direction.enderEyeMetaToDirection[i];
                    }
                    return j | i;
                }
                else {
                    if (block instanceof BlockTrapDoor) {
                        int i = meta & 0x3;
                        final int j = meta & 0x4;
                        final int m = meta & 0x8;
                        for (int l2 = 0; l2 < this.rotationMode; ++l2) {
                            if (i == 0) {
                                i = 3;
                            }
                            else if (i == 1) {
                                i = 2;
                            }
                            else if (i == 2) {
                                i = 0;
                            }
                            else if (i == 3) {
                                i = 1;
                            }
                        }
                        return m | j | i;
                    }
                    if (block instanceof BlockFenceGate) {
                        int i = meta & 0x3;
                        final int j = meta & 0x4;
                        for (int l = 0; l < this.rotationMode; ++l) {
                            i = Direction.enderEyeMetaToDirection[i];
                        }
                        return j | i;
                    }
                    if (block instanceof BlockPumpkin) {
                        int i = meta;
                        for (int k = 0; k < this.rotationMode; ++k) {
                            i = Direction.enderEyeMetaToDirection[i];
                        }
                        return i;
                    }
                    if (block instanceof BlockSkull) {
                        if (meta < 2) {
                            return meta;
                        }
                        int i = Direction.facingToDirection[meta];
                        for (int k = 0; k < this.rotationMode; ++k) {
                            i = Direction.enderEyeMetaToDirection[i];
                        }
                        return Direction.directionToFacing[i];
                    }
                    else {
                        if (block instanceof LOTRBlockGate) {
                            int i = meta & 0x7;
                            final int j = meta & 0x8;
                            if (i != 0 && i != 1) {
                                for (int l = 0; l < this.rotationMode; ++l) {
                                    i = Direction.facingToDirection[i];
                                    i = Direction.enderEyeMetaToDirection[i];
                                    i = Direction.directionToFacing[i];
                                }
                            }
                            return j | i;
                        }
                        if (block instanceof BlockLever) {
                            int i = meta & 0x7;
                            final int j = meta & 0x8;
                            if (i == 0 || i == 7) {
                                for (int l = 0; l < this.rotationMode; ++l) {
                                    i = ((i == 0) ? 7 : 0);
                                }
                            }
                            else if (i == 5 || i == 6) {
                                for (int l = 0; l < this.rotationMode; ++l) {
                                    i = ((i == 5) ? 6 : 5);
                                }
                            }
                            else {
                                for (int l = 0; l < this.rotationMode; ++l) {
                                    if (i == 4) {
                                        i = 1;
                                    }
                                    else if (i == 1) {
                                        i = 3;
                                    }
                                    else if (i == 3) {
                                        i = 2;
                                    }
                                    else if (i == 2) {
                                        i = 4;
                                    }
                                }
                            }
                            return j | i;
                        }
                        if (block instanceof BlockButton) {
                            int i = meta;
                            final int j = meta & 0x8;
                            for (int l = 0; l < this.rotationMode; ++l) {
                                if (i == 4) {
                                    i = 1;
                                }
                                else if (i == 1) {
                                    i = 3;
                                }
                                else if (i == 3) {
                                    i = 2;
                                }
                                else if (i == 2) {
                                    i = 4;
                                }
                            }
                            return j | i;
                        }
                        return meta;
                    }
                }
            }
        }
    }
    
    protected Block getBlock(final World world, int i, int j, int k) {
        final int i2 = i;
        final int k2 = k;
        i = this.getX(i2, k2);
        k = this.getZ(i2, k2);
        j = this.getY(j);
        if (!this.isInSBB(i, j, k)) {
            return Blocks.air;
        }
        return world.getBlock(i, j, k);
    }
    
    protected int getMeta(final World world, int i, int j, int k) {
        final int i2 = i;
        final int k2 = k;
        i = this.getX(i2, k2);
        k = this.getZ(i2, k2);
        j = this.getY(j);
        if (!this.isInSBB(i, j, k)) {
            return 0;
        }
        return world.getBlockMetadata(i, j, k);
    }
    
    protected int getTopBlock(final World world, int i, int k) {
        final int i2 = i;
        final int k2 = k;
        i = this.getX(i2, k2);
        k = this.getZ(i2, k2);
        if (!this.isInSBB(i, 0, k)) {
            return 0;
        }
        return world.getTopSolidOrLiquidBlock(i, k) - this.originY;
    }
    
    protected BiomeGenBase getBiome(final World world, int i, int k) {
        final int i2 = i;
        final int k2 = k;
        i = this.getX(i2, k2);
        k = this.getZ(i2, k2);
        if (!this.isInSBB(i, 0, k)) {
            return null;
        }
        return world.getBiomeGenForCoords(i, k);
    }
    
    protected boolean isAir(final World world, final int i, final int j, final int k) {
        return this.getBlock(world, i, j, k).getMaterial() == Material.air;
    }
    
    protected boolean isOpaque(final World world, final int i, final int j, final int k) {
        return this.getBlock(world, i, j, k).isOpaqueCube();
    }
    
    protected boolean isReplaceable(final World world, final int i, final int j, final int k) {
        return this.getBlock(world, i, j, k).isReplaceable((IBlockAccess)world, this.getX(i, k), this.getY(j), this.getZ(i, k));
    }
    
    protected boolean isSideSolid(final World world, final int i, final int j, final int k, final ForgeDirection side) {
        return this.getBlock(world, i, j, k).isSideSolid((IBlockAccess)world, this.getX(i, k), this.getY(j), this.getZ(i, k), side);
    }
    
    protected TileEntity getTileEntity(final World world, int i, int j, int k) {
        final int i2 = i;
        final int k2 = k;
        i = this.getX(i2, k2);
        k = this.getZ(i2, k2);
        j = this.getY(j);
        if (!this.isInSBB(i, j, k)) {
            return null;
        }
        return world.getTileEntity(i, j, k);
    }
    
    protected void placeChest(final World world, final Random random, final int i, final int j, final int k, final int meta, final LOTRChestContents contents) {
        this.placeChest(world, random, i, j, k, meta, contents, -1);
    }
    
    protected void placeChest(final World world, final Random random, final int i, final int j, final int k, final int meta, final LOTRChestContents contents, final int amount) {
        this.placeChest(world, random, i, j, k, (Block)Blocks.chest, meta, contents, amount);
    }
    
    protected void placeChest(final World world, final Random random, final int i, final int j, final int k, final Block chest, final int meta, final LOTRChestContents contents) {
        this.placeChest(world, random, i, j, k, chest, meta, contents, -1);
    }
    
    protected void placeChest(final World world, final Random random, final int i, final int j, final int k, final Block chest, final int meta, final LOTRChestContents contents, final int amount) {
        this.setBlockAndMetadata(world, i, j, k, chest, meta);
        this.fillChest(world, random, i, j, k, contents, amount);
    }
    
    protected void fillChest(final World world, final Random random, int i, int j, int k, final LOTRChestContents contents, final int amount) {
        final int i2 = i;
        final int k2 = k;
        i = this.getX(i2, k2);
        k = this.getZ(i2, k2);
        j = this.getY(j);
        if (!this.isInSBB(i, j, k)) {
            return;
        }
        LOTRChestContents.fillChest(world, random, i, j, k, contents, amount);
    }
    
    protected void putInventoryInChest(final World world, final int i, final int j, final int k, final IInventory inv) {
        final TileEntity tileentity = this.getTileEntity(world, i, j, k);
        if (tileentity instanceof IInventory) {
            final IInventory blockInv = (IInventory)tileentity;
            for (int l = 0; l < blockInv.getSizeInventory(); ++l) {
                if (l >= inv.getSizeInventory()) {
                    break;
                }
                blockInv.setInventorySlotContents(l, inv.getStackInSlot(l));
            }
        }
    }
    
    protected void placeOrcTorch(final World world, final int i, final int j, final int k) {
        this.setBlockAndMetadata(world, i, j, k, LOTRMod.orcTorch, 0);
        this.setBlockAndMetadata(world, i, j + 1, k, LOTRMod.orcTorch, 1);
    }
    
    protected void placeMobSpawner(final World world, final int i, final int j, final int k, final Class entityClass) {
        this.setBlockAndMetadata(world, i, j, k, LOTRMod.mobSpawner, 0);
        final TileEntity tileentity = this.getTileEntity(world, i, j, k);
        if (tileentity instanceof LOTRTileEntityMobSpawner) {
            ((LOTRTileEntityMobSpawner)tileentity).setEntityClassID(LOTREntities.getEntityIDFromClass(entityClass));
        }
    }
    
    protected void placeSpawnerChest(final World world, final int i, final int j, final int k, final Block block, final int meta, final Class entityClass) {
        this.placeSpawnerChest(world, null, i, j, k, block, meta, entityClass, null);
    }
    
    protected void placeSpawnerChest(final World world, final Random random, final int i, final int j, final int k, final Block block, final int meta, final Class entityClass, final LOTRChestContents contents) {
        this.placeSpawnerChest(world, random, i, j, k, block, meta, entityClass, contents, -1);
    }
    
    protected void placeSpawnerChest(final World world, final Random random, final int i, final int j, final int k, final Block block, final int meta, final Class entityClass, final LOTRChestContents contents, final int amount) {
        this.setBlockAndMetadata(world, i, j, k, block, meta);
        final TileEntity tileentity = this.getTileEntity(world, i, j, k);
        if (tileentity instanceof LOTRTileEntitySpawnerChest) {
            ((LOTRTileEntitySpawnerChest)tileentity).setMobID(entityClass);
        }
        if (contents != null) {
            this.fillChest(world, random, i, j, k, contents, amount);
        }
    }
    
    protected void placePlate(final World world, final Random random, final int i, final int j, final int k, final Block plateBlock, final LOTRFoods foodList) {
        this.placePlate_list(world, random, i, j, k, plateBlock, foodList, false);
    }
    
    protected void placePlateWithCertainty(final World world, final Random random, final int i, final int j, final int k, final Block plateBlock, final LOTRFoods foodList) {
        this.placePlate_list(world, random, i, j, k, plateBlock, foodList, true);
    }
    
    protected void placePlate_list(final World world, final Random random, final int i, final int j, final int k, final Block plateBlock, final LOTRFoods foodList, final boolean certain) {
        final ItemStack food = foodList.getRandomFoodForPlate(random);
        if (random.nextInt(4) == 0) {
            final ItemStack itemStack = food;
            itemStack.stackSize += 1 + random.nextInt(3);
        }
        this.placePlate_item(world, random, i, j, k, plateBlock, food, certain);
    }
    
    protected void placePlate_item(final World world, final Random random, final int i, final int j, final int k, final Block plateBlock, final ItemStack foodItem, final boolean certain) {
        this.placePlate_do(world, random, i, j, k, plateBlock, foodItem, certain);
    }
    
    private void placePlate_do(final World world, final Random random, final int i, final int j, final int k, final Block plateBlock, final ItemStack foodItem, final boolean certain) {
        if (!certain && random.nextBoolean()) {
            return;
        }
        this.setBlockAndMetadata(world, i, j, k, plateBlock, 0);
        if (certain || random.nextBoolean()) {
            final TileEntity tileentity = this.getTileEntity(world, i, j, k);
            if (tileentity instanceof LOTRTileEntityPlate) {
                final LOTRTileEntityPlate plate = (LOTRTileEntityPlate)tileentity;
                plate.setFoodItem(foodItem);
            }
        }
    }
    
    protected void placeBarrel(final World world, final Random random, final int i, final int j, final int k, final int meta, final LOTRFoods foodList) {
        this.placeBarrel(world, random, i, j, k, meta, foodList.getRandomBrewableDrink(random));
    }
    
    protected void placeBarrel(final World world, final Random random, final int i, final int j, final int k, final int meta, ItemStack drink) {
        this.setBlockAndMetadata(world, i, j, k, LOTRMod.barrel, meta);
        final TileEntity tileentity = this.getTileEntity(world, i, j, k);
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
    
    protected void placeMug(final World world, final Random random, int i, int j, int k, final int meta, ItemStack drink, final LOTRItemMug.Vessel[] vesselTypes) {
        final LOTRItemMug.Vessel vessel = vesselTypes[random.nextInt(vesselTypes.length)];
        this.setBlockAndMetadata(world, i, j, k, vessel.getBlock(), meta);
        if (random.nextInt(3) != 0) {
            final int i2 = i;
            final int k2 = k;
            i = this.getX(i2, k2);
            k = this.getZ(i2, k2);
            j = this.getY(j);
            if (!this.isInSBB(i, j, k)) {
                return;
            }
            drink = drink.copy();
            drink.stackSize = 1;
            if (drink.getItem() instanceof LOTRItemMug && ((LOTRItemMug)drink.getItem()).isBrewable) {
                LOTRItemMug.setStrengthMeta(drink, MathHelper.getRandomIntegerInRange(random, 1, 3));
            }
            LOTRItemMug.setVessel(drink, vessel, true);
            LOTRBlockMug.setMugItem(world, i, j, k, drink, vessel);
        }
    }
    
    protected void placeKebabStand(final World world, final Random random, final int i, final int j, final int k, final Block block, final int meta) {
        this.setBlockAndMetadata(world, i, j, k, block, meta);
        final TileEntity tileentity = this.getTileEntity(world, i, j, k);
        if (tileentity instanceof LOTRTileEntityKebabStand) {
            final LOTRTileEntityKebabStand stand = (LOTRTileEntityKebabStand)tileentity;
            final int kebab = MathHelper.getRandomIntegerInRange(random, 1, 8);
            stand.generateCookedKebab(kebab);
        }
    }
    
    protected void plantFlower(final World world, final Random random, final int i, final int j, final int k) {
        final ItemStack itemstack = this.getRandomFlower(world, random);
        this.setBlockAndMetadata(world, i, j, k, Block.getBlockFromItem(itemstack.getItem()), itemstack.getItemDamage());
    }
    
    protected void placeFlowerPot(final World world, int i, int j, int k, final ItemStack itemstack) {
        final boolean vanilla = itemstack == null || itemstack.getItem() == Item.getItemFromBlock(Blocks.cactus);
        if (vanilla) {
            this.setBlockAndMetadata(world, i, j, k, Blocks.flower_pot, 0);
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.flowerPot, 0);
        }
        final int i2 = i;
        final int k2 = k;
        i = this.getX(i2, k2);
        k = this.getZ(i2, k2);
        j = this.getY(j);
        if (!this.isInSBB(i, j, k)) {
            return;
        }
        if (itemstack != null) {
            if (vanilla) {
                final TileEntity te = world.getTileEntity(i, j, k);
                if (te instanceof TileEntityFlowerPot) {
                    final TileEntityFlowerPot pot = (TileEntityFlowerPot)te;
                    pot.func_145964_a(itemstack.getItem(), itemstack.getItemDamage());
                    pot.onInventoryChanged();
                }
            }
            else {
                LOTRBlockFlowerPot.setPlant(world, i, j, k, itemstack);
            }
        }
    }
    
    protected ItemStack getRandomFlower(final World world, final Random random) {
        final BiomeGenBase biome = this.getBiome(world, 0, 0);
        if (biome instanceof LOTRBiome) {
            final BiomeGenBase.FlowerEntry fe = ((LOTRBiome)biome).getRandomFlower(random);
            return new ItemStack(fe.block, 1, fe.metadata);
        }
        if (random.nextBoolean()) {
            return new ItemStack((Block)Blocks.yellow_flower, 0);
        }
        return new ItemStack((Block)Blocks.red_flower, 0);
    }
    
    protected ItemStack getRandomTallGrass(final World world, final Random random) {
        final BiomeGenBase biome = this.getBiome(world, 0, 0);
        if (biome instanceof LOTRBiome) {
            final LOTRBiome.GrassBlockAndMeta gbm = ((LOTRBiome)biome).getRandomGrass(random);
            return new ItemStack(gbm.block, 1, gbm.meta);
        }
        return new ItemStack((Block)Blocks.tallgrass, 1, 1);
    }
    
    protected void plantTallGrass(final World world, final Random random, final int i, final int j, final int k) {
        final ItemStack itemstack = this.getRandomTallGrass(world, random);
        this.setBlockAndMetadata(world, i, j, k, Block.getBlockFromItem(itemstack.getItem()), itemstack.getItemDamage());
    }
    
    protected void spawnItemFrame(final World world, int i, int j, int k, int direction, final ItemStack itemstack) {
        final int i2 = i;
        final int k2 = k;
        i = this.getX(i2, k2);
        k = this.getZ(i2, k2);
        j = this.getY(j);
        if (!this.isInSBB(i, j, k)) {
            return;
        }
        for (int l = 0; l < this.rotationMode; ++l) {
            direction = Direction.enderEyeMetaToDirection[direction];
        }
        final EntityItemFrame frame = new EntityItemFrame(world, i, j, k, direction);
        frame.setDisplayedItem(itemstack);
        world.spawnEntityInWorld((Entity)frame);
    }
    
    protected void placeArmorStand(final World world, final int i, final int j, final int k, final int direction, final ItemStack[] armor) {
        this.setBlockAndMetadata(world, i, j, k, LOTRMod.armorStand, direction);
        this.setBlockAndMetadata(world, i, j + 1, k, LOTRMod.armorStand, direction | 0x4);
        final TileEntity tileentity = this.getTileEntity(world, i, j, k);
        if (tileentity instanceof LOTRTileEntityArmorStand) {
            final LOTRTileEntityArmorStand armorStand = (LOTRTileEntityArmorStand)tileentity;
            if (armor != null) {
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
    }
    
    protected void placeWeaponRack(final World world, final int i, final int j, final int k, final int meta, final ItemStack weapon) {
        this.setBlockAndMetadata(world, i, j, k, LOTRMod.weaponRack, meta);
        final TileEntity tileentity = this.getTileEntity(world, i, j, k);
        if (tileentity instanceof LOTRTileEntityWeaponRack) {
            final LOTRTileEntityWeaponRack weaponRack = (LOTRTileEntityWeaponRack)tileentity;
            if (weapon != null) {
                weaponRack.setWeaponItem(weapon.copy());
            }
        }
    }
    
    protected void placeBanner(final World world, final int i, final int j, final int k, final LOTRItemBanner.BannerType bt, final int direction) {
        this.placeBanner(world, i, j, k, bt, direction, false, 0);
    }
    
    protected void placeBanner(final World world, int i, int j, int k, final LOTRItemBanner.BannerType bt, int direction, final boolean protection, final int r) {
        final int i2 = i;
        final int k2 = k;
        i = this.getX(i2, k2);
        k = this.getZ(i2, k2);
        j = this.getY(j);
        if (!this.isInSBB(i, j, k)) {
            return;
        }
        for (int l = 0; l < this.rotationMode; ++l) {
            direction = Direction.enderEyeMetaToDirection[direction];
        }
        final LOTREntityBanner banner = new LOTREntityBanner(world);
        banner.setLocationAndAngles(i + 0.5, (double)j, k + 0.5, direction * 90.0f, 0.0f);
        banner.setBannerType(bt);
        if (protection) {
            banner.setStructureProtection(true);
            banner.setSelfProtection(false);
        }
        if (r > 0) {
            if (r > 64) {
                throw new RuntimeException("WARNING: Banner protection range " + r + " is too large!");
            }
            banner.setCustomRange(r);
        }
        world.spawnEntityInWorld((Entity)banner);
    }
    
    protected void placeWallBanner(final World world, int i, int j, int k, final LOTRItemBanner.BannerType bt, int direction) {
        final int i2 = i;
        final int k2 = k;
        i = this.getX(i2, k2);
        k = this.getZ(i2, k2);
        j = this.getY(j);
        if (!this.isInSBB(i, j, k)) {
            return;
        }
        for (int l = 0; l < this.rotationMode; ++l) {
            direction = Direction.enderEyeMetaToDirection[direction];
        }
        final LOTREntityBannerWall banner = new LOTREntityBannerWall(world, i, j, k, direction);
        banner.setBannerType(bt);
        world.spawnEntityInWorld((Entity)banner);
    }
    
    protected void setGrassToDirt(final World world, int i, int j, int k) {
        final int i2 = i;
        final int k2 = k;
        i = this.getX(i2, k2);
        k = this.getZ(i2, k2);
        j = this.getY(j);
        if (!this.isInSBB(i, j, k)) {
            return;
        }
        world.getBlock(i, j, k).onPlantGrow(world, i, j, k, i, j, k);
    }
    
    protected void setBiomeTop(final World world, final int i, final int j, final int k) {
        final BiomeGenBase biome = this.getBiome(world, i, k);
        final Block topBlock = biome.topBlock;
        int topMeta = 0;
        if (biome instanceof LOTRBiome) {
            topMeta = ((LOTRBiome)biome).topBlockMeta;
        }
        this.setBlockAndMetadata(world, i, j, k, topBlock, topMeta);
    }
    
    protected void setBiomeFiller(final World world, final int i, final int j, final int k) {
        final BiomeGenBase biome = this.getBiome(world, i, k);
        final Block fillerBlock = biome.fillerBlock;
        int fillerMeta = 0;
        if (biome instanceof LOTRBiome) {
            fillerMeta = ((LOTRBiome)biome).fillerBlockMeta;
        }
        this.setBlockAndMetadata(world, i, j, k, fillerBlock, fillerMeta);
    }
    
    protected void setAir(final World world, final int i, final int j, final int k) {
        this.setBlockAndMetadata(world, i, j, k, Blocks.air, 0);
    }
    
    protected void placeSkull(final World world, final Random random, final int i, final int j, final int k) {
        this.placeSkull(world, i, j, k, random.nextInt(16));
    }
    
    protected void placeSkull(final World world, final int i, final int j, final int k, int dir) {
        this.setBlockAndMetadata(world, i, j, k, Blocks.skull, 1);
        final TileEntity tileentity = this.getTileEntity(world, i, j, k);
        if (tileentity instanceof TileEntitySkull) {
            final TileEntitySkull skull = (TileEntitySkull)tileentity;
            dir += this.rotationMode * 4;
            dir %= 16;
            skull.func_145903_a(dir);
        }
    }
    
    protected void placeSign(final World world, final int i, final int j, final int k, final Block block, final int meta, final String[] text) {
        this.setBlockAndMetadata(world, i, j, k, block, meta);
        final TileEntity te = this.getTileEntity(world, i, j, k);
        if (te instanceof TileEntitySign) {
            final TileEntitySign sign = (TileEntitySign)te;
            for (int l = 0; l < sign.field_145915_a.length; ++l) {
                sign.field_145915_a[l] = text[l];
            }
        }
    }
    
    protected void placeAnimalJar(final World world, final int i, final int j, final int k, final Block block, final int meta, final EntityLiving creature) {
        this.setBlockAndMetadata(world, i, j, k, block, meta);
        final TileEntity te = this.getTileEntity(world, i, j, k);
        if (te instanceof LOTRTileEntityAnimalJar) {
            final LOTRTileEntityAnimalJar jar = (LOTRTileEntityAnimalJar)te;
            final NBTTagCompound nbt = new NBTTagCompound();
            if (creature != null) {
                final int i2 = this.getX(i, k);
                final int j2 = this.getY(j);
                final int k2 = this.getZ(i, k);
                creature.setPosition(i2 + 0.5, (double)j2, k2 + 0.5);
                creature.onSpawnWithEgg((IEntityLivingData)null);
                if (creature.writeToNBTOptional(nbt)) {
                    jar.setEntityData(nbt);
                }
            }
        }
    }
    
    protected void placeIthildinDoor(final World world, final int i, final int j, final int k, final Block block, final int meta, final LOTRBlockGateDwarvenIthildin.DoorSize doorSize) {
        final int i2 = this.getX(i, k);
        final int j2 = this.getY(j);
        final int k2 = this.getZ(i, k);
        final int xzFactorX = (meta == 2) ? -1 : ((meta == 3) ? 1 : 0);
        final int xzFactorZ = (meta == 4) ? 1 : ((meta == 5) ? -1 : 0);
        for (int y = 0; y < doorSize.height; ++y) {
            for (int xz = 0; xz < doorSize.width; ++xz) {
                final int i3 = i + xz * xzFactorX;
                final int j3 = j + y;
                final int k3 = k + xz * xzFactorZ;
                this.setBlockAndMetadata(world, i3, j3, k3, block, meta);
                final LOTRTileEntityDwarvenDoor door = (LOTRTileEntityDwarvenDoor)this.getTileEntity(world, i3, j3, k3);
                if (door != null) {
                    door.setDoorSizeAndPos(doorSize, xz, y);
                    door.setDoorBasePos(i2, j2, k2);
                }
            }
        }
    }
    
    protected void spawnNPCAndSetHome(final EntityCreature entity, final World world, int i, int j, int k, final int homeDistance) {
        final int i2 = i;
        final int k2 = k;
        i = this.getX(i2, k2);
        k = this.getZ(i2, k2);
        j = this.getY(j);
        if (!this.isInSBB(i, j, k)) {
            return;
        }
        entity.setLocationAndAngles(i + 0.5, (double)j, k + 0.5, 0.0f, 0.0f);
        entity.onSpawnWithEgg((IEntityLivingData)null);
        if (entity instanceof LOTREntityNPC) {
            ((LOTREntityNPC)entity).isNPCPersistent = true;
        }
        world.spawnEntityInWorld((Entity)entity);
        entity.setHomeArea(i, j, k, homeDistance);
    }
    
    protected void leashEntityTo(final EntityCreature entity, final World world, int i, int j, int k) {
        final int i2 = i;
        final int k2 = k;
        i = this.getX(i2, k2);
        k = this.getZ(i2, k2);
        j = this.getY(j);
        if (!this.isInSBB(i, j, k)) {
            return;
        }
        final EntityLeashKnot leash = EntityLeashKnot.func_110129_a(world, i, j, k);
        entity.setLeashedToEntity((Entity)leash, true);
    }
    
    protected void placeNPCRespawner(final LOTREntityNPCRespawner entity, final World world, int i, int j, int k) {
        final int i2 = i;
        final int k2 = k;
        i = this.getX(i2, k2);
        k = this.getZ(i2, k2);
        j = this.getY(j);
        if (!this.isInSBB(i, j, k)) {
            return;
        }
        entity.setLocationAndAngles(i + 0.5, (double)j, k + 0.5, 0.0f, 0.0f);
        world.spawnEntityInWorld((Entity)entity);
    }
    
    protected void placeRug(final LOTREntityRugBase rug, final World world, int i, int j, int k, final float rotation) {
        final int i2 = i;
        final int k2 = k;
        i = this.getX(i2, k2);
        k = this.getZ(i2, k2);
        j = this.getY(j);
        if (!this.isInSBB(i, j, k)) {
            return;
        }
        float f = rotation;
        switch (this.rotationMode) {
            case 0: {
                f += 0.0f;
                break;
            }
            case 1: {
                f += 270.0f;
                break;
            }
            case 2: {
                f += 180.0f;
                break;
            }
            case 3: {
                f += 90.0f;
                break;
            }
        }
        f %= 360.0f;
        rug.setLocationAndAngles(i + 0.5, (double)j, k + 0.5, f, 0.0f);
        world.spawnEntityInWorld((Entity)rug);
    }
    
    protected boolean generateSubstructure(final LOTRWorldGenStructureBase2 str, final World world, final Random random, final int i, final int j, final int k, final int r) {
        return this.generateSubstructureWithRestrictionFlag(str, world, random, i, j, k, r, this.restrictions);
    }
    
    protected boolean generateSubstructureWithRestrictionFlag(final LOTRWorldGenStructureBase2 str, final World world, final Random random, int i, int j, int k, int r, final boolean isRestrict) {
        final int i2 = i;
        final int k2 = k;
        i = this.getX(i2, k2);
        k = this.getZ(i2, k2);
        j = this.getY(j);
        r += this.rotationMode;
        r %= 4;
        str.restrictions = isRestrict;
        str.usingPlayer = this.usingPlayer;
        str.villageInstance = this.villageInstance;
        str.threadTimelapse = this.threadTimelapse;
        str.setStructureBB(this.sbb);
        return str.generateWithSetRotation(world, random, i, j, k, r);
    }
    
    protected void loadStrScan(final String name) {
        this.currentStrScan = LOTRStructureScan.getScanByName(name);
        if (this.currentStrScan == null) {
            LOTRLog.logger.error("LOTR: Structure Scan for name " + name + " does not exist!!!");
        }
        this.scanAliases.clear();
    }
    
    protected void associateBlockAlias(final String alias, final Block block) {
        this.addBlockAliasOption(alias, 1, block);
    }
    
    protected void addBlockAliasOption(final String alias, final int weight, final Block block) {
        this.addBlockMetaAliasOption(alias, weight, block, -1);
    }
    
    protected void associateBlockMetaAlias(final String alias, final Block block, final int meta) {
        this.addBlockMetaAliasOption(alias, 1, block, meta);
    }
    
    protected void addBlockMetaAliasOption(final String alias, final int weight, final Block block, final int meta) {
        BlockAliasPool pool = this.scanAliases.get(alias);
        if (pool == null) {
            pool = new BlockAliasPool();
            this.scanAliases.put(alias, pool);
        }
        pool.addEntry(1, block, meta);
    }
    
    protected void setBlockAliasChance(final String alias, final float chance) {
        this.scanAliasChances.put(alias, chance);
    }
    
    protected void clearScanAlias(final String alias) {
        this.scanAliases.remove(alias);
        this.scanAliasChances.remove(alias);
    }
    
    protected void generateStrScan(final World world, final Random random, final int i, final int j, final int k) {
        for (int pass = 0; pass <= 1; ++pass) {
            for (final LOTRStructureScan.ScanStepBase step : this.currentStrScan.scanSteps) {
                final int i2 = i - step.x;
                int j2 = j + step.y;
                final int k2 = k + step.z;
                Block aliasBlock = null;
                int aliasMeta = -1;
                if (step.hasAlias()) {
                    final String alias = step.getAlias();
                    final BlockAliasPool pool = this.scanAliases.get(alias);
                    if (pool == null) {
                        throw new IllegalArgumentException("No block associated to alias " + alias + " !");
                    }
                    final BlockAliasPool.BlockMetaEntry e = pool.getEntry(random);
                    aliasBlock = e.block;
                    aliasMeta = e.meta;
                    if (this.scanAliasChances.containsKey(alias)) {
                        final float chance = this.scanAliasChances.get(alias);
                        if (random.nextFloat() >= chance) {
                            continue;
                        }
                    }
                }
                final Block block = step.getBlock(aliasBlock);
                final int meta = step.getMeta(aliasMeta);
                boolean inThisPass = false;
                if (block.getMaterial().isOpaque() || block == Blocks.air) {
                    inThisPass = (pass == 0);
                }
                else {
                    inThisPass = (pass == 1);
                }
                if (inThisPass) {
                    if (step.findLowest) {
                        while (this.getY(j2) > 0 && !this.getBlock(world, i2, j2 - 1, k2).getMaterial().blocksMovement()) {
                            --j2;
                        }
                    }
                    if (step instanceof LOTRStructureScan.ScanStepSkull) {
                        this.placeSkull(world, random, i2, j2, k2);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, j2, k2, block, meta);
                        if ((step.findLowest || j2 <= 1) && block.isOpaqueCube()) {
                            this.setGrassToDirt(world, i2, j2 - 1, k2);
                        }
                        if (!step.fillDown) {
                            continue;
                        }
                        for (int j3 = j2 - 1; !this.isOpaque(world, i2, j3, k2) && this.getY(j3) >= 0; --j3) {
                            this.setBlockAndMetadata(world, i2, j3, k2, block, meta);
                            if (block.isOpaqueCube()) {
                                this.setGrassToDirt(world, i2, j3 - 1, k2);
                            }
                        }
                    }
                }
            }
        }
        this.currentStrScan = null;
        this.scanAliases.clear();
    }
    
    protected int getX(final int x, final int z) {
        switch (this.rotationMode) {
            case 0: {
                return this.originX - x;
            }
            case 1: {
                return this.originX - z;
            }
            case 2: {
                return this.originX + x;
            }
            case 3: {
                return this.originX + z;
            }
            default: {
                return this.originX;
            }
        }
    }
    
    protected int getZ(final int x, final int z) {
        switch (this.rotationMode) {
            case 0: {
                return this.originZ + z;
            }
            case 1: {
                return this.originZ - x;
            }
            case 2: {
                return this.originZ - z;
            }
            case 3: {
                return this.originZ + x;
            }
            default: {
                return this.originZ;
            }
        }
    }
    
    protected int getY(final int y) {
        return this.originY + y;
    }
    
    protected final boolean isSurface(final World world, int i, int j, int k) {
        final int i2 = i;
        final int k2 = k;
        i = this.getX(i2, k2);
        k = this.getZ(i2, k2);
        j = this.getY(j);
        return isSurfaceStatic(world, i, j, k) || (this.villageInstance != null && this.villageInstance.isVillageSurface(world, i, j, k));
    }
    
    public static boolean isSurfaceStatic(final World world, final int i, final int j, final int k) {
        final Block block = world.getBlock(i, j, k);
        final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
        if (block instanceof BlockSlab && !block.isOpaqueCube()) {
            return isSurfaceStatic(world, i, j - 1, k);
        }
        final Block above = world.getBlock(i, j + 1, k);
        return !above.getMaterial().isLiquid() && (block == biome.topBlock || block == biome.fillerBlock || (block == Blocks.grass || block == Blocks.dirt || block == Blocks.gravel || block == LOTRMod.dirtPath) || (block == LOTRMod.mudGrass || block == LOTRMod.mud) || (block == Blocks.sand || block == LOTRMod.whiteSand) || (block == LOTRMod.mordorDirt || block == LOTRMod.mordorGravel));
    }
    
    private static class BlockAliasPool
    {
        private List<BlockMetaEntry> entries;
        private int totalWeight;
        
        private BlockAliasPool() {
            this.entries = new ArrayList<BlockMetaEntry>();
        }
        
        public void addEntry(final int w, final Block b, final int m) {
            this.entries.add(new BlockMetaEntry(w, b, m));
            this.totalWeight = WeightedRandom.getTotalWeight((Collection)this.entries);
        }
        
        public BlockMetaEntry getEntry(final Random random) {
            return (BlockMetaEntry)WeightedRandom.getRandomItem(random, (Collection)this.entries, this.totalWeight);
        }
        
        private static class BlockMetaEntry extends WeightedRandom.Item
        {
            public final Block block;
            public final int meta;
            
            public BlockMetaEntry(final int w, final Block b, final int m) {
                super(w);
                this.block = b;
                this.meta = m;
            }
        }
    }
}

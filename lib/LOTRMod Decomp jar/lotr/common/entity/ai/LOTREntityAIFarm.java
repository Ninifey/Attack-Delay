// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.EntityHanging;
import net.minecraft.item.ItemHoe;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.util.ForgeDirection;
import java.util.List;
import java.util.Random;
import net.minecraft.tileentity.TileEntity;
import java.util.Iterator;
import net.minecraft.item.ItemDye;
import net.minecraft.tileentity.TileEntityChest;
import java.util.Collection;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockCorn;
import net.minecraft.block.BlockStem;
import lotr.common.LOTRReflection;
import net.minecraft.block.BlockCrops;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.EnumPlantType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;
import net.minecraft.world.IBlockAccess;
import lotr.common.block.LOTRBlockGrapevine;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.FakePlayerFactory;
import java.util.UUID;
import com.mojang.authlib.GameProfile;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTRFarmhand;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIFarm extends EntityAIBase
{
    private static final int HOEING = 0;
    private static final int PLANTING = 1;
    private static final int HARVESTING = 2;
    private static final int DEPOSITING = 3;
    private static final int BONEMEALING = 4;
    private static final int COLLECTING = 5;
    private static final int DEPOSIT_THRESHOLD = 16;
    private static final int COLLECT_THRESHOLD = 16;
    private static final int MIN_CHEST_RANGE = 24;
    private LOTREntityNPC theEntity;
    private LOTRFarmhand theEntityFarmer;
    private World theWorld;
    private double moveSpeed;
    private float farmingEfficiency;
    private int action;
    private int targetX;
    private int targetY;
    private int targetZ;
    private int pathingTick;
    private int rePathDelay;
    private boolean harvestingSolidBlock;
    private FakePlayer fakePlayer;
    
    public LOTREntityAIFarm(final LOTRFarmhand npc, final double d, final float f) {
        this.action = -1;
        this.theEntity = (LOTREntityNPC)npc;
        this.theEntityFarmer = npc;
        this.theWorld = ((Entity)this.theEntity).worldObj;
        this.moveSpeed = d;
        this.setMutexBits(1);
        if (this.theWorld instanceof WorldServer) {
            this.fakePlayer = FakePlayerFactory.get((WorldServer)this.theWorld, new GameProfile((UUID)null, "LOTRFarming"));
        }
        this.farmingEfficiency = f;
    }
    
    public boolean shouldExecute() {
        if (this.theEntity.hiredNPCInfo.isActive && !this.theEntity.hiredNPCInfo.isGuardMode()) {
            return false;
        }
        this.setAppropriateHomeRange(-1);
        if (this.theEntity.hasHome() && !this.theEntity.isWithinHomeDistanceCurrentPosition()) {
            return false;
        }
        if (this.theEntity.getRNG().nextFloat() < this.farmingEfficiency * 0.1f) {
            if (this.canDoDepositing()) {
                final ChunkCoordinates depositTarget = this.findTarget(3);
                if (depositTarget != null) {
                    this.targetX = depositTarget.posX;
                    this.targetY = depositTarget.posY;
                    this.targetZ = depositTarget.posZ;
                    this.action = 3;
                    return true;
                }
            }
            if (this.canDoHoeing()) {
                final ChunkCoordinates hoeTarget = this.findTarget(0);
                if (hoeTarget != null) {
                    this.targetX = hoeTarget.posX;
                    this.targetY = hoeTarget.posY;
                    this.targetZ = hoeTarget.posZ;
                    this.action = 0;
                    return true;
                }
            }
            if (this.canDoPlanting()) {
                final ChunkCoordinates plantTarget = this.findTarget(1);
                if (plantTarget != null) {
                    this.targetX = plantTarget.posX;
                    this.targetY = plantTarget.posY;
                    this.targetZ = plantTarget.posZ;
                    this.action = 1;
                    return true;
                }
            }
            if (this.canDoHarvesting()) {
                final ChunkCoordinates harvestTarget = this.findTarget(2);
                if (harvestTarget != null) {
                    this.targetX = harvestTarget.posX;
                    this.targetY = harvestTarget.posY;
                    this.targetZ = harvestTarget.posZ;
                    this.action = 2;
                    return true;
                }
            }
            if (this.canDoBonemealing()) {
                final ChunkCoordinates bonemealTarget = this.findTarget(4);
                if (bonemealTarget != null) {
                    this.targetX = bonemealTarget.posX;
                    this.targetY = bonemealTarget.posY;
                    this.targetZ = bonemealTarget.posZ;
                    this.action = 4;
                    return true;
                }
            }
            if (this.canDoCollecting()) {
                final ChunkCoordinates collectTarget = this.findTarget(5);
                if (collectTarget != null) {
                    this.targetX = collectTarget.posX;
                    this.targetY = collectTarget.posY;
                    this.targetZ = collectTarget.posZ;
                    this.action = 5;
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isFarmingGrapes() {
        final IPlantable seed = this.getSeedsToPlant();
        return seed.getPlant((IBlockAccess)this.theWorld, -1, -1, -1) instanceof LOTRBlockGrapevine;
    }
    
    private boolean canDoHoeing() {
        return true;
    }
    
    private boolean canDoPlanting() {
        if (this.theEntity.hiredNPCInfo.isActive) {
            final ItemStack invSeeds = this.getInventorySeeds();
            return invSeeds != null && invSeeds.stackSize > 1;
        }
        return true;
    }
    
    private boolean canDoHarvesting() {
        return this.theEntity.hiredNPCInfo.isActive && this.getInventorySeeds() != null && this.hasSpaceForCrops() && this.getCropForSeed(this.getSeedsToPlant()) != null;
    }
    
    private boolean canDoDepositing() {
        if (this.theEntity.hiredNPCInfo.isActive) {
            for (int l = 1; l <= 2; ++l) {
                final ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
                if (itemstack != null && itemstack.stackSize >= 16) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean canDoBonemealing() {
        if (this.theEntity.hiredNPCInfo.isActive) {
            final ItemStack invBmeal = this.getInventoryBonemeal();
            return invBmeal != null;
        }
        return false;
    }
    
    private boolean canDoCollecting() {
        if (this.theEntity.hiredNPCInfo.isActive) {
            final ItemStack seeds = this.getInventorySeeds();
            if (seeds != null && seeds.stackSize <= 16) {
                return true;
            }
            final ItemStack bonemeal = this.getInventoryBonemeal();
            if (bonemeal == null || (bonemeal != null && bonemeal.stackSize <= 16)) {
                return true;
            }
        }
        return false;
    }
    
    private ItemStack getInventorySeeds() {
        if (this.theEntity.hiredNPCInfo.getHiredInventory() == null) {
            return null;
        }
        final ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(0);
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (item instanceof IPlantable) {
                final IPlantable iplantable = (IPlantable)item;
                if (iplantable.getPlantType((IBlockAccess)this.theWorld, -1, -1, -1) == EnumPlantType.Crop) {
                    return itemstack;
                }
            }
        }
        return null;
    }
    
    private IPlantable getSeedsToPlant() {
        if (this.theEntity.hiredNPCInfo.isActive) {
            final ItemStack invSeeds = this.getInventorySeeds();
            if (invSeeds != null) {
                return (IPlantable)invSeeds.getItem();
            }
        }
        return this.theEntityFarmer.getUnhiredSeeds();
    }
    
    private boolean hasSpaceForCrops() {
        if (this.theEntity.hiredNPCInfo.getHiredInventory() == null) {
            return false;
        }
        for (int l = 1; l <= 2; ++l) {
            final ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
            if (itemstack == null || (itemstack.stackSize < itemstack.getMaxStackSize() && itemstack.isItemEqual(this.getCropForSeed(this.getSeedsToPlant())))) {
                return true;
            }
        }
        return false;
    }
    
    private ItemStack getInventoryBonemeal() {
        if (this.theEntity.hiredNPCInfo.getHiredInventory() == null) {
            return null;
        }
        final ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(3);
        if (itemstack != null && itemstack.getItem() == Items.dye && itemstack.getItemDamage() == 15) {
            return itemstack;
        }
        return null;
    }
    
    private ItemStack getCropForSeed(final IPlantable seed) {
        final Block block = seed.getPlant((IBlockAccess)this.theWorld, -1, -1, -1);
        if (block instanceof BlockCrops) {
            return new ItemStack(LOTRReflection.getCropItem((BlockCrops)block));
        }
        if (block instanceof BlockStem) {
            return new ItemStack(LOTRReflection.getStemFruitBlock((BlockStem)block).getItemDropped(0, this.theWorld.rand, 0), 1, 0);
        }
        if (block instanceof LOTRBlockCorn) {
            return new ItemStack(LOTRMod.corn);
        }
        if (block instanceof LOTRBlockGrapevine) {
            return new ItemStack(((LOTRBlockGrapevine)block).getGrapeItem());
        }
        return null;
    }
    
    public void startExecuting() {
        super.startExecuting();
        this.setAppropriateHomeRange(this.action);
    }
    
    private void setAppropriateHomeRange(final int targetAction) {
        if (this.theEntity.hiredNPCInfo.isActive) {
            int hRange = this.theEntity.hiredNPCInfo.getGuardRange();
            final ChunkCoordinates home = this.theEntity.getHomePosition();
            if ((targetAction == 3 || targetAction == 5) && hRange < 24) {
                hRange = 24;
            }
            this.theEntity.setHomeArea(home.posX, home.posY, home.posZ, hRange);
        }
    }
    
    public boolean continueExecuting() {
        if (this.theEntity.hiredNPCInfo.isActive && !this.theEntity.hiredNPCInfo.isGuardMode()) {
            return false;
        }
        if (this.theEntity.getNavigator().noPath()) {
            return false;
        }
        if (this.pathingTick < 200) {
            if (this.action == 0) {
                return this.canDoHoeing() && this.isSuitableForHoeing(this.targetX, this.targetY, this.targetZ);
            }
            if (this.action == 1) {
                return this.canDoPlanting() && this.isSuitableForPlanting(this.targetX, this.targetY, this.targetZ);
            }
            if (this.action == 2) {
                return this.canDoHarvesting() && this.isSuitableForHarvesting(this.targetX, this.targetY, this.targetZ);
            }
            if (this.action == 3) {
                return this.canDoDepositing() && this.isSuitableForDepositing(this.targetX, this.targetY, this.targetZ);
            }
            if (this.action == 4) {
                return this.canDoBonemealing() && this.isSuitableForBonemealing(this.targetX, this.targetY, this.targetZ);
            }
            if (this.action == 5) {
                return this.canDoCollecting() && this.isSuitableForCollecting(this.targetX, this.targetY, this.targetZ);
            }
        }
        return false;
    }
    
    public void resetTask() {
        this.pathingTick = 0;
        this.rePathDelay = 0;
        this.action = -1;
        this.harvestingSolidBlock = false;
        this.setAppropriateHomeRange(this.action);
    }
    
    public void updateTask() {
        boolean canDoAction = false;
        final ChunkCoordinates walkTarget = this.getWalkTarget(this.targetX, this.targetY, this.targetZ, this.action);
        final double distSq = this.theEntity.getDistanceSq(walkTarget.posX + 0.5, (double)walkTarget.posY, walkTarget.posZ + 0.5);
        if (this.action == 0 || this.action == 1) {
            final int i = MathHelper.floor_double(((Entity)this.theEntity).posX);
            final int j = MathHelper.floor_double(((Entity)this.theEntity).boundingBox.minY);
            final int k = MathHelper.floor_double(((Entity)this.theEntity).posZ);
            canDoAction = (i == walkTarget.posX && j == walkTarget.posY && k == walkTarget.posZ);
        }
        else {
            canDoAction = (distSq < 9.0);
        }
        if (!canDoAction) {
            this.theEntity.getLookHelper().setLookPosition(this.targetX + 0.5, this.targetY + 0.5, this.targetZ + 0.5, 10.0f, (float)this.theEntity.getVerticalFaceSpeed());
            --this.rePathDelay;
            if (this.rePathDelay <= 0) {
                this.rePathDelay = 10;
                this.theEntity.getNavigator().tryMoveToXYZ(walkTarget.posX + 0.5, (double)walkTarget.posY, walkTarget.posZ + 0.5, this.moveSpeed);
            }
            ++this.pathingTick;
        }
        else if (this.action == 0 && this.isSuitableForHoeing(this.targetX, this.targetY, this.targetZ)) {
            this.theEntity.swingItem();
            if (this.isReplaceable(this.targetX, this.targetY + 1, this.targetZ)) {
                this.theWorld.setBlockToAir(this.targetX, this.targetY + 1, this.targetZ);
            }
            Items.iron_hoe.onItemUse(new ItemStack(Items.iron_hoe), (EntityPlayer)this.fakePlayer, this.theWorld, this.targetX, this.targetY, this.targetZ, 1, 0.5f, 0.5f, 0.5f);
        }
        else if (this.action == 1 && this.isSuitableForPlanting(this.targetX, this.targetY, this.targetZ)) {
            this.theEntity.swingItem();
            final IPlantable seed = this.getSeedsToPlant();
            final Block plant = seed.getPlant((IBlockAccess)this.theWorld, this.targetX, this.targetY, this.targetZ);
            final int meta = seed.getPlantMetadata((IBlockAccess)this.theWorld, this.targetX, this.targetY, this.targetZ);
            this.theWorld.setBlock(this.targetX, this.targetY, this.targetZ, plant, meta, 3);
            if (this.theEntity.hiredNPCInfo.isActive) {
                this.theEntity.hiredNPCInfo.getHiredInventory().decrStackSize(0, 1);
            }
        }
        else if (this.action == 2 && this.isSuitableForHarvesting(this.targetX, this.targetY, this.targetZ)) {
            this.theEntity.swingItem();
            final Block block = this.theWorld.getBlock(this.targetX, this.targetY, this.targetZ);
            final ArrayList drops = new ArrayList();
            if (block instanceof LOTRBlockCorn) {
                for (int j2 = 0; j2 <= LOTRBlockCorn.MAX_GROW_HEIGHT - 1; ++j2) {
                    final int j3 = this.targetY + j2;
                    if (this.theWorld.getBlock(this.targetX, j3, this.targetZ) == block && LOTRBlockCorn.hasCorn(this.theWorld, this.targetX, j3, this.targetZ)) {
                        final int meta2 = this.theWorld.getBlockMetadata(this.targetX, j3, this.targetZ);
                        drops.addAll(((LOTRBlockCorn)block).getCornDrops(this.theWorld, this.targetX, j3, this.targetZ, meta2));
                        LOTRBlockCorn.setHasCorn(this.theWorld, this.targetX, j3, this.targetZ, false);
                    }
                }
            }
            else if (block instanceof LOTRBlockGrapevine) {
                final int meta = this.theWorld.getBlockMetadata(this.targetX, this.targetY, this.targetZ);
                drops.addAll(block.getDrops(this.theWorld, this.targetX, this.targetY, this.targetZ, meta, 0));
                block.removedByPlayer(this.theWorld, (EntityPlayer)this.fakePlayer, this.targetX, this.targetY, this.targetZ, true);
            }
            else {
                final int meta = this.theWorld.getBlockMetadata(this.targetX, this.targetY, this.targetZ);
                drops.addAll(block.getDrops(this.theWorld, this.targetX, this.targetY, this.targetZ, meta, 0));
                this.theWorld.setBlockToAir(this.targetX, this.targetY, this.targetZ);
            }
            final Block.SoundType stepSound = block.stepSound;
            this.theWorld.playSoundEffect(this.targetX + 0.5, this.targetY + 0.5, this.targetZ + 0.5, stepSound.getDigResourcePath(), (stepSound.getVolume() + 1.0f) / 2.0f, stepSound.getFrequency() * 0.8f);
            final ItemStack seedItem = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(0);
            final ItemStack cropItem = this.getCropForSeed(this.getSeedsToPlant());
            boolean addedOneCropSeed = false;
            for (final Object obj : drops) {
                final ItemStack drop = (ItemStack)obj;
                if (drop.isItemEqual(cropItem)) {
                    if (drop.isItemEqual(seedItem) && !addedOneCropSeed) {
                        addedOneCropSeed = true;
                        final ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(0);
                        if (itemstack.stackSize + drop.stackSize <= itemstack.getMaxStackSize()) {
                            final ItemStack itemStack = itemstack;
                            ++itemStack.stackSize;
                            this.theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(0, itemstack);
                            continue;
                        }
                    }
                    for (int l = 1; l <= 2; ++l) {
                        final ItemStack itemstack2 = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
                        if (itemstack2 == null) {
                            this.theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(l, drop);
                            break;
                        }
                        if (itemstack2.stackSize + drop.stackSize <= itemstack2.getMaxStackSize() && itemstack2.isItemEqual(cropItem)) {
                            final ItemStack itemStack2 = itemstack2;
                            ++itemStack2.stackSize;
                            this.theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(l, itemstack2);
                            break;
                        }
                    }
                }
                else {
                    if (!drop.isItemEqual(seedItem)) {
                        continue;
                    }
                    final ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(0);
                    if (itemstack.stackSize + drop.stackSize > itemstack.getMaxStackSize()) {
                        continue;
                    }
                    final ItemStack itemStack3 = itemstack;
                    ++itemStack3.stackSize;
                    this.theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(0, itemstack);
                }
            }
        }
        else if (this.action == 3 && this.isSuitableForDepositing(this.targetX, this.targetY, this.targetZ)) {
            this.theEntity.swingItem();
            final TileEntity te = this.theWorld.getTileEntity(this.targetX, this.targetY, this.targetZ);
            if (te instanceof TileEntityChest) {
                final TileEntityChest chest = (TileEntityChest)te;
                for (int m = 1; m <= 2; ++m) {
                    final ItemStack itemstack3 = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(m);
                    if (itemstack3 != null) {
                        for (int slot = 0; slot < chest.getSizeInventory(); ++slot) {
                            ItemStack chestItem = chest.getStackInSlot(slot);
                            if (chestItem == null || (chestItem.isItemEqual(itemstack3) && ItemStack.areItemStackTagsEqual(chestItem, itemstack3) && chestItem.stackSize < chestItem.getMaxStackSize())) {
                                if (chestItem == null) {
                                    chestItem = itemstack3.copy();
                                    chestItem.stackSize = 0;
                                }
                                while (itemstack3.stackSize > 0 && chestItem.stackSize < chestItem.getMaxStackSize()) {
                                    final ItemStack itemStack4 = chestItem;
                                    ++itemStack4.stackSize;
                                    final ItemStack itemStack5 = itemstack3;
                                    --itemStack5.stackSize;
                                }
                                chest.setInventorySlotContents(slot, chestItem);
                                if (itemstack3.stackSize <= 0) {
                                    this.theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(m, (ItemStack)null);
                                    break;
                                }
                            }
                        }
                    }
                }
                this.theWorld.playSoundEffect(this.targetX + 0.5, this.targetY + 0.5, this.targetZ + 0.5, "random.chestclosed", 0.5f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f);
            }
        }
        else if (this.action == 4 && this.isSuitableForBonemealing(this.targetX, this.targetY, this.targetZ)) {
            this.theEntity.swingItem();
            ItemStack bonemeal = this.getInventoryBonemeal();
            if (ItemDye.applyBonemeal(this.getInventoryBonemeal(), this.theWorld, this.targetX, this.targetY, this.targetZ, (EntityPlayer)this.fakePlayer)) {
                this.theWorld.playAuxSFX(2005, this.targetX, this.targetY, this.targetZ, 0);
            }
            if (bonemeal.stackSize <= 0) {
                bonemeal = null;
            }
            this.theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(3, bonemeal);
        }
        else if (this.action == 5 && this.isSuitableForCollecting(this.targetX, this.targetY, this.targetZ)) {
            this.theEntity.swingItem();
            final TileEntity te = this.theWorld.getTileEntity(this.targetX, this.targetY, this.targetZ);
            if (te instanceof TileEntityChest) {
                final TileEntityChest chest = (TileEntityChest)te;
                final int[] array;
                final int[] invSlots = array = new int[] { 0, 3 };
                for (final int l2 : array) {
                    ItemStack itemstack4 = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l2);
                    if (itemstack4 == null && l2 == 3) {
                        itemstack4 = new ItemStack(Items.dye, 0, 15);
                    }
                    if (itemstack4 != null) {
                        for (int slot2 = 0; slot2 < chest.getSizeInventory(); ++slot2) {
                            ItemStack chestItem2 = chest.getStackInSlot(slot2);
                            if (chestItem2 != null && chestItem2.isItemEqual(itemstack4) && ItemStack.areItemStackTagsEqual(chestItem2, itemstack4) && chestItem2.stackSize > 0) {
                                while (itemstack4.stackSize < itemstack4.getMaxStackSize() && chestItem2.stackSize > 0) {
                                    final ItemStack itemStack6 = chestItem2;
                                    --itemStack6.stackSize;
                                    final ItemStack itemStack7 = itemstack4;
                                    ++itemStack7.stackSize;
                                }
                                if (itemstack4.stackSize <= 0) {
                                    itemstack4 = null;
                                }
                                if (chestItem2.stackSize <= 0) {
                                    chestItem2 = null;
                                }
                                this.theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(l2, itemstack4);
                                chest.setInventorySlotContents(slot2, chestItem2);
                                if (itemstack4.stackSize >= itemstack4.getMaxStackSize()) {
                                    break;
                                }
                            }
                        }
                    }
                }
                this.theWorld.playSoundEffect(this.targetX + 0.5, this.targetY + 0.5, this.targetZ + 0.5, "random.chestopen", 0.5f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f);
            }
        }
    }
    
    private ChunkCoordinates findTarget(final int targetAction) {
        this.setAppropriateHomeRange(targetAction);
        final Random random = this.theEntity.getRNG();
        for (int l = 0; l < 32; ++l) {
            int i = MathHelper.floor_double(((Entity)this.theEntity).posX) + MathHelper.getRandomIntegerInRange(random, -8, 8);
            int j = MathHelper.floor_double(((Entity)this.theEntity).boundingBox.minY) + MathHelper.getRandomIntegerInRange(random, -4, 4);
            int k = MathHelper.floor_double(((Entity)this.theEntity).posZ) + MathHelper.getRandomIntegerInRange(random, -8, 8);
            boolean suitable = false;
            if (targetAction == 0) {
                suitable = this.isSuitableForHoeing(i, j, k);
            }
            else if (targetAction == 1) {
                suitable = this.isSuitableForPlanting(i, j, k);
            }
            else if (targetAction == 2) {
                suitable = this.isSuitableForHarvesting(i, j, k);
            }
            else if (targetAction == 3 || targetAction == 5) {
                final List<TileEntityChest> chests = new ArrayList<TileEntityChest>();
                for (final Object obj : this.theWorld.field_147482_g) {
                    final TileEntity te = (TileEntity)obj;
                    if (te instanceof TileEntityChest) {
                        final TileEntityChest chest = (TileEntityChest)te;
                        if (!this.theEntity.isWithinHomeDistance(((TileEntity)chest).xCoord, ((TileEntity)chest).yCoord, ((TileEntity)chest).zCoord)) {
                            continue;
                        }
                        chests.add(chest);
                    }
                }
                if (!chests.isEmpty()) {
                    final TileEntityChest chest2 = chests.get(random.nextInt(chests.size()));
                    i = ((TileEntity)chest2).xCoord;
                    j = ((TileEntity)chest2).yCoord;
                    k = ((TileEntity)chest2).zCoord;
                    if (targetAction == 3) {
                        suitable = this.isSuitableForDepositing(i, j, k);
                    }
                    else if (targetAction == 5) {
                        suitable = this.isSuitableForCollecting(i, j, k);
                    }
                }
                else {
                    suitable = false;
                }
            }
            else if (targetAction == 4) {
                suitable = this.isSuitableForBonemealing(i, j, k);
            }
            if (suitable && !this.theEntity.isWithinHomeDistance(i, j, k)) {
                suitable = false;
            }
            if (suitable) {
                final ChunkCoordinates walkTarget = this.getWalkTarget(i, j, k, targetAction);
                if (this.theEntity.getNavigator().getPathToXYZ((double)walkTarget.posX, (double)walkTarget.posY, (double)walkTarget.posZ) == null) {
                    suitable = false;
                }
            }
            if (suitable) {
                return new ChunkCoordinates(i, j, k);
            }
        }
        return null;
    }
    
    private ChunkCoordinates getWalkTarget(final int i, final int j, final int k, final int targetAction) {
        if (targetAction == 0) {
            if (this.isReplaceable(i, j + 1, k)) {
                return new ChunkCoordinates(i, j + 1, k);
            }
            return this.getAdjacentSolidOpenWalkTarget(i, j + 1, k);
        }
        else if (targetAction == 1 || targetAction == 2 || targetAction == 4) {
            if (this.harvestingSolidBlock) {
                return new ChunkCoordinates(i, j + 1, k);
            }
            if (this.isFarmingGrapes()) {
                int groundY = j;
                for (int j2 = 1; j2 <= 2; ++j2) {
                    if (!(this.theWorld.getBlock(i, j - j2 - 1, k) instanceof LOTRBlockGrapevine)) {
                        groundY = j - j2 - 1;
                        break;
                    }
                }
                return this.getAdjacentSolidOpenWalkTarget(i, groundY + 1, k);
            }
            return new ChunkCoordinates(i, j, k);
        }
        else {
            if (targetAction == 3 || targetAction == 5) {
                return this.getAdjacentSolidOpenWalkTarget(i, j, k);
            }
            return new ChunkCoordinates(i, j, k);
        }
    }
    
    private boolean isSolidOpenWalkTarget(final int i, final int j, final int k) {
        final Block below = this.theWorld.getBlock(i, j - 1, k);
        if (below.isOpaqueCube() || below.canSustainPlant((IBlockAccess)this.theWorld, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.wheat)) {
            final List bounds = new ArrayList();
            final AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 2), (double)(k + 1));
            for (int j2 = j; j2 <= j + 1; ++j2) {
                this.theWorld.getBlock(i, j2, k).addCollisionBoxesToList(this.theWorld, i, j2, k, aabb, bounds, (Entity)this.theEntity);
            }
            if (bounds.isEmpty()) {
                return this.theEntity.getNavigator().getPathToXYZ((double)i, (double)j, (double)k) != null;
            }
        }
        return false;
    }
    
    private ChunkCoordinates getAdjacentSolidOpenWalkTarget(final int i, final int j, final int k) {
        final List<ChunkCoordinates> possibleCoords = new ArrayList<ChunkCoordinates>();
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int k2 = -1; k2 <= 1; ++k2) {
                final int i3 = i + i2;
                final int k3 = k + k2;
                for (int j2 = 1; j2 >= -1; --j2) {
                    final int j3 = j + j2;
                    if (this.isSolidOpenWalkTarget(i3, j3, k3)) {
                        possibleCoords.add(new ChunkCoordinates(i3, j3, k3));
                    }
                }
            }
        }
        if (!possibleCoords.isEmpty()) {
            return possibleCoords.get(0);
        }
        return new ChunkCoordinates(i, j, k);
    }
    
    private boolean isSuitableForHoeing(final int i, final int j, final int k) {
        this.harvestingSolidBlock = false;
        final Block block = this.theWorld.getBlock(i, j, k);
        final boolean isGrassDirt = block.canSustainPlant((IBlockAccess)this.theWorld, i, j, k, ForgeDirection.UP, (IPlantable)Blocks.tallgrass);
        final boolean isFarmland = block.canSustainPlant((IBlockAccess)this.theWorld, i, j, k, ForgeDirection.UP, (IPlantable)Blocks.wheat);
        if (!isGrassDirt || isFarmland || (!this.isReplaceable(i, j + 1, k) && this.theWorld.getBlock(i, j + 1, k) != LOTRMod.grapevine)) {
            return false;
        }
        final Block below = this.theWorld.getBlock(i, j - 1, k);
        if (below == Blocks.sand) {
            return false;
        }
        boolean waterNearby = false;
    Label_0199:
        for (int range = 4, i2 = i - range; i2 <= i + range; ++i2) {
            for (int k2 = k - range; k2 <= k + range; ++k2) {
                if (this.theWorld.getBlock(i2, j, k2).getMaterial() == Material.water) {
                    waterNearby = true;
                    break Label_0199;
                }
            }
        }
        return waterNearby;
    }
    
    private boolean isSuitableForPlanting(final int i, final int j, final int k) {
        this.harvestingSolidBlock = false;
        if (this.isFarmingGrapes()) {
            return this.theWorld.getBlock(i, j, k) == LOTRMod.grapevine && LOTRBlockGrapevine.canPlantGrapesAt(this.theWorld, i, j, k, this.getSeedsToPlant());
        }
        return this.theWorld.getBlock(i, j - 1, k).isFertile(this.theWorld, i, j - 1, k) && this.isReplaceable(i, j, k);
    }
    
    private boolean isSuitableForHarvesting(final int i, final int j, final int k) {
        this.harvestingSolidBlock = false;
        final IPlantable seed = this.getSeedsToPlant();
        final Block plantBlock = seed.getPlant((IBlockAccess)this.theWorld, i, j, k);
        if (plantBlock instanceof BlockCrops) {
            this.harvestingSolidBlock = false;
            return this.theWorld.getBlock(i, j, k) == plantBlock && this.theWorld.getBlockMetadata(i, j, k) >= 7;
        }
        if (plantBlock instanceof BlockStem) {
            this.harvestingSolidBlock = true;
            return this.theWorld.getBlock(i, j, k) == LOTRReflection.getStemFruitBlock((BlockStem)plantBlock);
        }
        if (plantBlock instanceof LOTRBlockCorn) {
            this.harvestingSolidBlock = false;
            if (this.theWorld.getBlock(i, j, k) == plantBlock) {
                for (int j2 = 0; j2 <= LOTRBlockCorn.MAX_GROW_HEIGHT - 1; ++j2) {
                    final int j3 = j + j2;
                    if (this.theWorld.getBlock(i, j3, k) == plantBlock && LOTRBlockCorn.hasCorn(this.theWorld, i, j3, k)) {
                        return true;
                    }
                }
            }
        }
        else if (plantBlock instanceof LOTRBlockGrapevine) {
            this.harvestingSolidBlock = false;
            return this.theWorld.getBlock(i, j, k) == seed.getPlant((IBlockAccess)this.theWorld, i, j, k) && this.theWorld.getBlockMetadata(i, j, k) >= 7;
        }
        return false;
    }
    
    private boolean isSuitableForDepositing(final int i, final int j, final int k) {
        this.harvestingSolidBlock = false;
        final TileEntityChest chest = this.getSuitableChest(i, j, k);
        if (chest != null) {
            for (int l = 1; l <= 2; ++l) {
                final ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
                if (itemstack != null) {
                    for (int slot = 0; slot < chest.getSizeInventory(); ++slot) {
                        final ItemStack chestItem = chest.getStackInSlot(slot);
                        if (chestItem == null || (chestItem.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(chestItem, itemstack) && chestItem.stackSize < chestItem.getMaxStackSize())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean isSuitableForBonemealing(final int i, final int j, final int k) {
        this.harvestingSolidBlock = false;
        final IPlantable seed = this.getSeedsToPlant();
        final Block plantBlock = seed.getPlant((IBlockAccess)this.theWorld, i, j, k);
        if (plantBlock instanceof IGrowable && this.theWorld.getBlock(i, j, k) == plantBlock) {
            final IGrowable growableBlock = (IGrowable)plantBlock;
            if (growableBlock.func_149851_a(this.theWorld, i, j, k, this.theWorld.isClient)) {
                this.harvestingSolidBlock = plantBlock.isOpaqueCube();
                return true;
            }
        }
        return false;
    }
    
    private boolean isSuitableForCollecting(final int i, final int j, final int k) {
        this.harvestingSolidBlock = false;
        final TileEntityChest chest = this.getSuitableChest(i, j, k);
        if (chest != null) {
            final int[] array;
            final int[] invSlots = array = new int[] { 0, 3 };
            for (final int l : array) {
                ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
                if (itemstack == null && l == 3) {
                    itemstack = new ItemStack(Items.dye, 0, 15);
                }
                if (itemstack != null && itemstack.stackSize <= 16) {
                    for (int slot = 0; slot < chest.getSizeInventory(); ++slot) {
                        final ItemStack chestItem = chest.getStackInSlot(slot);
                        if (chestItem != null && chestItem.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(chestItem, itemstack) && chestItem.stackSize > 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private TileEntityChest getSuitableChest(final int i, final int j, final int k) {
        final Block block = this.theWorld.getBlock(i, j, k);
        final int meta = this.theWorld.getBlockMetadata(i, j, k);
        TileEntityChest suitableChest = null;
        if (block.hasTileEntity(meta)) {
            final TileEntity te = this.theWorld.getTileEntity(i, j, k);
            if (te instanceof TileEntityChest) {
                final TileEntityChest chest = (TileEntityChest)te;
                boolean flag = false;
                if (this.isChestSuitable(chest)) {
                    flag = true;
                }
                else if (chest.field_145991_k != null && this.isChestSuitable(chest.field_145991_k)) {
                    flag = true;
                }
                else if (chest.field_145990_j != null && this.isChestSuitable(chest.field_145990_j)) {
                    flag = true;
                }
                else if (chest.field_145992_i != null && this.isChestSuitable(chest.field_145992_i)) {
                    flag = true;
                }
                else if (chest.field_145988_l != null && this.isChestSuitable(chest.field_145988_l)) {
                    flag = true;
                }
                if (flag) {
                    suitableChest = chest;
                }
            }
        }
        return suitableChest;
    }
    
    private boolean isChestSuitable(final TileEntityChest chest) {
        final int i = ((TileEntity)chest).xCoord;
        final int j = ((TileEntity)chest).yCoord;
        final int k = ((TileEntity)chest).zCoord;
        final AxisAlignedBB chestBB = AxisAlignedBB.getBoundingBox((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1));
        final List entities = this.theWorld.getEntitiesWithinAABB((Class)EntityItemFrame.class, chestBB.expand(2.0, 2.0, 2.0));
        for (final Object obj : entities) {
            final EntityItemFrame frame = (EntityItemFrame)obj;
            if (((EntityHanging)frame).field_146063_b == i && ((EntityHanging)frame).field_146064_c == j && ((EntityHanging)frame).field_146062_d == k) {
                final ItemStack frameItem = frame.getDisplayedItem();
                if (frameItem != null && frameItem.getItem() instanceof ItemHoe) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    private boolean isReplaceable(final int i, final int j, final int k) {
        final Block block = this.theWorld.getBlock(i, j, k);
        return !block.getMaterial().isLiquid() && block.isReplaceable((IBlockAccess)this.theWorld, i, j, k);
    }
}

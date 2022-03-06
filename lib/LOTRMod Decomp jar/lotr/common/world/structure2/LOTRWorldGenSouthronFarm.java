// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntitySouthronFarmer;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.entity.npc.LOTREntityHaradSlave;
import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.item.Item;
import net.minecraft.block.Block;

public class LOTRWorldGenSouthronFarm extends LOTRWorldGenSouthronStructure
{
    private Block crop1Block;
    private Item seed1;
    private Block crop2Block;
    private Item seed2;
    
    public LOTRWorldGenSouthronFarm(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        if (random.nextBoolean()) {
            this.crop1Block = Blocks.wheat;
            this.seed1 = Items.wheat_seeds;
        }
        else {
            final int randomCrop = random.nextInt(4);
            if (randomCrop == 0) {
                this.crop1Block = Blocks.carrots;
                this.seed1 = Items.carrot;
            }
            else if (randomCrop == 1) {
                this.crop1Block = Blocks.potatoes;
                this.seed1 = Items.potato;
            }
            else if (randomCrop == 2) {
                this.crop1Block = LOTRMod.lettuceCrop;
                this.seed1 = LOTRMod.lettuce;
            }
            else if (randomCrop == 3) {
                this.crop1Block = LOTRMod.turnipCrop;
                this.seed1 = LOTRMod.turnip;
            }
        }
        if (random.nextBoolean()) {
            this.crop2Block = Blocks.wheat;
            this.seed2 = Items.wheat_seeds;
        }
        else {
            final int randomCrop = random.nextInt(4);
            if (randomCrop == 0) {
                this.crop2Block = Blocks.carrots;
                this.seed2 = Items.carrot;
            }
            else if (randomCrop == 1) {
                this.crop2Block = Blocks.potatoes;
                this.seed2 = Items.potato;
            }
            else if (randomCrop == 2) {
                this.crop2Block = LOTRMod.lettuceCrop;
                this.seed2 = LOTRMod.lettuce;
            }
            else if (randomCrop == 3) {
                this.crop2Block = LOTRMod.turnipCrop;
                this.seed2 = LOTRMod.turnip;
            }
        }
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -4; i2 <= 4; ++i2) {
                for (int k2 = -6; k2 <= 6; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (maxHeight - minHeight > 6) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = -6; k3 <= 6; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (i4 != 4 || k4 != 6) {
                    for (int j2 = -1; !this.isOpaque(world, i3, j2, k3) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.stoneBlock, super.stoneMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    for (int j2 = 1; j2 <= 4; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
            }
        }
        this.loadStrScan("southron_farm");
        this.associateBlockMetaAlias("STONE", super.stoneBlock, super.stoneMeta);
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", super.brickSlabBlock, super.brickSlabMeta);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", super.fenceGateBlock);
        this.associateBlockAlias("CROP1", this.crop1Block);
        this.associateBlockAlias("CROP2", this.crop2Block);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeSkull(world, random, -4, 4, 0);
        this.placeSkull(world, random, 4, 4, 0);
        if (random.nextInt(4) == 0) {
            final LOTREntityNearHaradrimBase farmer = this.createFarmer(world);
            this.spawnNPCAndSetHome(farmer, world, 0, 1, 1, 8);
        }
        final LOTREntityHaradSlave farmhand1 = new LOTREntityHaradSlave(world);
        farmhand1.seedsItem = this.seed1;
        this.spawnNPCAndSetHome(farmhand1, world, -2, 1, 0, 8);
        final LOTREntityHaradSlave farmhand2 = new LOTREntityHaradSlave(world);
        farmhand2.seedsItem = this.seed2;
        this.spawnNPCAndSetHome(farmhand2, world, 2, 1, 0, 8);
        return true;
    }
    
    protected LOTREntityNearHaradrimBase createFarmer(final World world) {
        return new LOTREntitySouthronFarmer(world);
    }
}

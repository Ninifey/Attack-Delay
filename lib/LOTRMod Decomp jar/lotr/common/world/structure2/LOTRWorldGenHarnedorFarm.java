// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityHarnedorFarmhand;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityHarnedorFarmer;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.item.Item;
import net.minecraft.block.Block;

public class LOTRWorldGenHarnedorFarm extends LOTRWorldGenHarnedorStructure
{
    private Block crop1Block;
    private Item seed1;
    private Block crop2Block;
    private Item seed2;
    
    public LOTRWorldGenHarnedorFarm(final boolean flag) {
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
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -4; i2 <= 4; ++i2) {
                for (int k2 = -4; k2 <= 4; ++k2) {
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
            for (int k3 = -4; k3 <= 4; ++k3) {
                for (int j3 = -1; !this.isOpaque(world, i3, j3, k3) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, Blocks.dirt, 0);
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
                for (int j3 = 1; j3 <= 4; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
            }
        }
        this.loadStrScan("harnedor_farm");
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", super.fenceGateBlock);
        this.associateBlockMetaAlias("ROOF", super.roofBlock, super.roofMeta);
        this.associateBlockAlias("CROP1", this.crop1Block);
        this.associateBlockAlias("CROP2", this.crop2Block);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeSkull(world, random, 0, 4, 0);
        for (final int i4 : new int[] { -2, 2 }) {
            int j2 = 0;
            for (int step = 0; step < 6; ++step) {
                final int k4 = -5 - step;
                if (this.isOpaque(world, i4, j2 + 1, k4)) {
                    this.setAir(world, i4, j2 + 1, k4);
                    this.setAir(world, i4, j2 + 2, k4);
                    this.setAir(world, i4, j2 + 3, k4);
                    this.setBlockAndMetadata(world, i4, j2, k4, (Block)Blocks.grass, 0);
                    this.setGrassToDirt(world, i4, j2 - 1, k4);
                    for (int j4 = j2 - 1; !this.isOpaque(world, i4, j4, k4) && this.getY(j4) >= 0; --j4) {
                        this.setBlockAndMetadata(world, i4, j4, k4, Blocks.dirt, 0);
                        this.setGrassToDirt(world, i4, j4 - 1, k4);
                    }
                    ++j2;
                }
                else {
                    if (this.isOpaque(world, i4, j2, k4)) {
                        break;
                    }
                    this.setAir(world, i4, j2 + 1, k4);
                    this.setAir(world, i4, j2 + 2, k4);
                    this.setAir(world, i4, j2 + 3, k4);
                    this.setBlockAndMetadata(world, i4, j2, k4, (Block)Blocks.grass, 0);
                    this.setGrassToDirt(world, i4, j2 - 1, k4);
                    for (int j4 = j2 - 1; !this.isOpaque(world, i4, j4, k4) && this.getY(j4) >= 0; --j4) {
                        this.setBlockAndMetadata(world, i4, j4, k4, Blocks.dirt, 0);
                        this.setGrassToDirt(world, i4, j4 - 1, k4);
                    }
                    --j2;
                }
            }
        }
        if (random.nextInt(4) == 0) {
            final LOTREntityHarnedorFarmer farmer = new LOTREntityHarnedorFarmer(world);
            this.spawnNPCAndSetHome(farmer, world, 0, 1, 1, 8);
        }
        final LOTREntityHarnedorFarmhand farmhand1 = new LOTREntityHarnedorFarmhand(world);
        farmhand1.seedsItem = this.seed1;
        this.spawnNPCAndSetHome(farmhand1, world, -2, 1, 0, 8);
        final LOTREntityHarnedorFarmhand farmhand2 = new LOTREntityHarnedorFarmhand(world);
        farmhand2.seedsItem = this.seed2;
        this.spawnNPCAndSetHome(farmhand2, world, 2, 1, 0, 8);
        return true;
    }
}

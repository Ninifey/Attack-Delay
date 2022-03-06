// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityTauredainFarmhand;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityTauredainFarmer;
import lotr.common.LOTRMod;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.item.Item;
import net.minecraft.block.Block;

public class LOTRWorldGenTauredainVillageFarm extends LOTRWorldGenTauredainHouse
{
    private Block cropBlock;
    private int cropMeta;
    private Item seedItem;
    private boolean melon;
    
    public LOTRWorldGenTauredainVillageFarm(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected int getOffset() {
        return 4;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            return false;
        }
        final int randomCrop = random.nextInt(8);
        if (randomCrop == 0 || randomCrop == 1) {
            this.cropBlock = Blocks.potatoes;
            this.cropMeta = 7;
            this.seedItem = Items.potato;
            this.melon = false;
        }
        else if (randomCrop == 2 || randomCrop == 3) {
            this.cropBlock = LOTRMod.cornStalk;
            this.cropMeta = 0;
            this.seedItem = Item.getItemFromBlock(LOTRMod.cornStalk);
            this.melon = false;
        }
        else if (randomCrop == 4) {
            this.cropBlock = Blocks.wheat;
            this.cropMeta = 7;
            this.seedItem = Items.wheat_seeds;
            this.melon = false;
        }
        else if (randomCrop == 5) {
            this.cropBlock = Blocks.carrots;
            this.cropMeta = 7;
            this.seedItem = Items.carrot;
            this.melon = false;
        }
        else if (randomCrop == 6 || randomCrop == 7) {
            this.cropBlock = Blocks.melon_stem;
            this.cropMeta = 7;
            this.seedItem = Items.melon_seeds;
            this.melon = true;
        }
        for (int i2 = -4; i2 <= 4; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                for (int j2 = 1; j2 <= 4; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        this.loadStrScan("taurethrim_farm");
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", super.brickWallBlock, super.brickWallMeta);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", super.fenceGateBlock);
        this.associateBlockMetaAlias("CROP", this.cropBlock, this.cropMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        if (this.melon) {
            for (int k3 = -2; k3 <= 2; ++k3) {
                this.setBlockAndMetadata(world, 0, 1, k3, super.brickBlock, super.brickMeta);
            }
            for (int i2 = -1; i2 <= 1; ++i2) {
                this.setBlockAndMetadata(world, i2, 0, 0, Blocks.stained_hardened_clay, 12);
                this.setBlockAndMetadata(world, i2, 1, 0, Blocks.water, 0);
                this.setAir(world, i2, 2, 0);
            }
            for (final int k4 : new int[] { -1, 1 }) {
                for (int i3 = -3; i3 <= 3; ++i3) {
                    if (i3 != 0) {
                        this.setBlockAndMetadata(world, i3, 0, k4, (Block)Blocks.sand, 0);
                        this.setBlockAndMetadata(world, i3, 1, k4, LOTRMod.mudGrass, 0);
                    }
                }
            }
        }
        if (random.nextInt(3) == 0) {
            final LOTREntityTauredainFarmer farmer = new LOTREntityTauredainFarmer(world);
            this.spawnNPCAndSetHome(farmer, world, 0, 2, 1, 4);
        }
        final LOTREntityTauredainFarmhand farmhand1 = new LOTREntityTauredainFarmhand(world);
        farmhand1.seedsItem = this.seedItem;
        this.spawnNPCAndSetHome(farmhand1, world, -2, 2, 0, 6);
        final LOTREntityTauredainFarmhand farmhand2 = new LOTREntityTauredainFarmhand(world);
        farmhand2.seedsItem = this.seedItem;
        this.spawnNPCAndSetHome(farmhand2, world, 2, 2, 0, 6);
        return true;
    }
}

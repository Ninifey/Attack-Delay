// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityTauredainSmith;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainSmithy extends LOTRWorldGenTauredainHouse
{
    public LOTRWorldGenTauredainSmithy(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected int getOffset() {
        return 6;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -5; i2 <= 5; ++i2) {
                for (int k2 = -5; k2 <= 7; ++k2) {
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
                    if (maxHeight - minHeight > 8) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            for (int k3 = -5; k3 <= 7; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (i4 <= 4 && k3 >= -2 && k3 <= 5) {
                    for (int j2 = -4; j2 <= 0; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
                if (i4 <= 2 && k3 == 6) {
                    for (int j2 = -4; j2 <= -1; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
                if (i4 <= 5 && k4 <= 5) {
                    for (int j2 = 1; j2 <= 8; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
                if (i4 <= 3 && k3 >= 1 && k3 <= 7) {
                    for (int j2 = 1; j2 <= 8; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
            }
        }
        this.loadStrScan("taurethrim_smithy");
        this.associateBlockMetaAlias("STONEBRICK", LOTRMod.brick4, 0);
        this.associateBlockAlias("STONEBRICK_STAIR", LOTRMod.stairsTauredainBrick);
        this.associateBlockMetaAlias("STONEBRICK_WALL", LOTRMod.wall4, 0);
        this.associateBlockMetaAlias("OBBRICK", LOTRMod.brick4, 4);
        this.associateBlockMetaAlias("OBBRICK_SLAB", LOTRMod.slabSingle8, 4);
        this.associateBlockAlias("OBBRICK_STAIR", LOTRMod.stairsTauredainBrickObsidian);
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", super.brickWallBlock, super.brickWallMeta);
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
        this.associateBlockMetaAlias("WOOD|4", super.woodBlock, super.woodMeta | 0x4);
        this.associateBlockMetaAlias("WOOD|8", super.woodBlock, super.woodMeta | 0x8);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", super.fenceGateBlock);
        this.addBlockMetaAliasOption("FLOOR", 10, Blocks.stained_hardened_clay, 7);
        this.addBlockMetaAliasOption("FLOOR", 10, LOTRMod.mud, 0);
        this.associateBlockMetaAlias("WALL", Blocks.stained_hardened_clay, 12);
        this.associateBlockMetaAlias("ROOF", super.thatchBlock, super.thatchMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", super.thatchSlabBlock, super.thatchSlabMeta);
        this.associateBlockAlias("ROOF_STAIR", super.thatchStairBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        this.setBlockAndMetadata(world, 0, 5, 5, super.bedBlock, 1);
        this.setBlockAndMetadata(world, 1, 5, 5, super.bedBlock, 9);
        this.placeChest(world, random, 2, 5, 4, LOTRMod.chestBasket, 5, LOTRChestContents.TAUREDAIN_HOUSE);
        this.placeTauredainFlowerPot(world, 2, 6, 5, random);
        this.placePlateWithCertainty(world, random, 2, 6, 3, LOTRMod.woodPlateBlock, LOTRFoods.TAUREDAIN);
        this.placeTauredainTorch(world, -4, 2, -4);
        this.placeTauredainTorch(world, 4, 2, -4);
        this.placeWeaponRack(world, -3, -2, 2, 5, this.getRandomTaurethrimWeapon(random));
        this.placeArmorStand(world, 3, -3, 2, 1, new ItemStack[] { null, new ItemStack(LOTRMod.bodyTauredain), null, null });
        final LOTREntityTauredainSmith smith = new LOTREntityTauredainSmith(world);
        this.spawnNPCAndSetHome(smith, world, 0, -3, 3, 12);
        return true;
    }
    
    protected ItemStack getRandomTaurethrimWeapon(final Random random) {
        final ItemStack[] items = { new ItemStack(LOTRMod.swordTauredain), new ItemStack(LOTRMod.daggerTauredain), new ItemStack(LOTRMod.spearTauredain), new ItemStack(LOTRMod.pikeTauredain), new ItemStack(LOTRMod.hammerTauredain), new ItemStack(LOTRMod.battleaxeTauredain) };
        return items[random.nextInt(items.length)].copy();
    }
}

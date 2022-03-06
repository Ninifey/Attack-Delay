// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityHarnedhrim;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityHarnedorBlacksmith;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorSmithy extends LOTRWorldGenHarnedorStructure
{
    public LOTRWorldGenHarnedorSmithy(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -12; i2 <= 8; ++i2) {
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
                    if (maxHeight - minHeight > 8) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -10; i3 <= 6; ++i3) {
            for (int k3 = -6; k3 <= 6; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if ((i3 >= -8 && i3 <= 4 && k4 == 4) || (i3 >= -10 && i3 <= 6 && k4 <= 3)) {
                    for (int j2 = -1; !this.isOpaque(world, i3, j2, k3) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.plank2Block, super.plank2Meta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    for (int j2 = 1; j2 <= 8; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
            }
        }
        this.loadStrScan("harnedor_smithy");
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", super.fenceGateBlock);
        this.associateBlockAlias("DOOR", super.doorBlock);
        this.associateBlockMetaAlias("PLANK2", super.plank2Block, super.plank2Meta);
        this.associateBlockMetaAlias("ROOF", super.roofBlock, super.roofMeta);
        this.generateStrScan(world, random, 0, 1, 0);
        this.placeWeaponRack(world, -1, 2, -1, 5, this.getRandomHarnedorWeapon(random));
        this.placeWeaponRack(world, -1, 2, 1, 5, this.getRandomHarnedorWeapon(random));
        this.placeArmorStand(world, 3, 1, 3, 2, null);
        if (random.nextBoolean()) {
            this.placeArmorStand(world, 0, 1, 3, 0, new ItemStack[] { new ItemStack(LOTRMod.helmetHarnedor), new ItemStack(LOTRMod.bodyHarnedor), new ItemStack(LOTRMod.legsHarnedor), new ItemStack(LOTRMod.bootsHarnedor) });
        }
        else {
            this.placeArmorStand(world, 0, 1, 3, 0, new ItemStack[] { null, new ItemStack(LOTRMod.bodyHarnedor), null, null });
        }
        this.placeChest(world, random, 5, 1, -2, LOTRMod.chestBasket, 5, LOTRChestContents.HARNEDOR_HOUSE);
        this.placeChest(world, random, -7, 1, 3, LOTRMod.chestBasket, 2, LOTRChestContents.HARNEDOR_HOUSE);
        this.placeBarrel(world, random, -3, 2, -1, 5, LOTRFoods.HARNEDOR_DRINK);
        this.placeMug(world, random, -3, 2, 0, 2, LOTRFoods.HARNEDOR_DRINK);
        this.placeMug(world, random, -9, 2, -2, 3, LOTRFoods.HARNEDOR_DRINK);
        this.placePlate(world, random, -5, 2, 3, LOTRMod.ceramicPlateBlock, LOTRFoods.HARNEDOR);
        this.placePlate(world, random, -3, 2, 3, LOTRMod.ceramicPlateBlock, LOTRFoods.HARNEDOR);
        this.placeFlowerPot(world, -4, 2, 3, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, -8, 1, 1, super.bedBlock, 3);
        this.setBlockAndMetadata(world, -9, 1, 1, super.bedBlock, 11);
        final LOTREntityHarnedhrim smith = new LOTREntityHarnedorBlacksmith(world);
        this.spawnNPCAndSetHome(smith, world, 0, 1, 0, 8);
        return true;
    }
}

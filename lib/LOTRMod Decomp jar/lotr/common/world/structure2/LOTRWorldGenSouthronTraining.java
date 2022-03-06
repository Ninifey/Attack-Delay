// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronTraining extends LOTRWorldGenSouthronStructure
{
    public LOTRWorldGenSouthronTraining(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -7; i2 <= 7; ++i2) {
                for (int k2 = -5; k2 <= 5; ++k2) {
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
        for (int i3 = -7; i3 <= 7; ++i3) {
            for (int k3 = -5; k3 <= 5; ++k3) {
                for (int j3 = 1; j3 <= 4; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
            }
        }
        this.loadStrScan("southron_training");
        this.associateBlockMetaAlias("STONE", super.stoneBlock, super.stoneMeta);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockMetaAlias("BEAM", super.woodBeamBlock, super.woodBeamMeta);
        this.addBlockMetaAliasOption("GROUND", 5, (Block)Blocks.sand, 0);
        this.addBlockMetaAliasOption("GROUND", 3, Blocks.dirt, 1);
        this.addBlockMetaAliasOption("GROUND", 1, (Block)Blocks.sand, 1);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeWeaponRack(world, -5, 2, -4, 2, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, 5, 2, -4, 2, this.getRandomHaradWeapon(random));
        this.placeSkull(world, 0, 3, 2, 0);
        this.placeSkull(world, -5, 3, 0, 12);
        this.placeSkull(world, 5, 3, 0, 4);
        return true;
    }
}

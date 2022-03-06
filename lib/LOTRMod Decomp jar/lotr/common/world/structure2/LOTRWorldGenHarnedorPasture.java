// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.animal.LOTREntityCamel;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.EntityCreature;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorPasture extends LOTRWorldGenHarnedorStructure
{
    public LOTRWorldGenHarnedorPasture(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -3; i2 <= 3; ++i2) {
                for (int k2 = -3; k2 <= 6; ++k2) {
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
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int k3 = -3; k3 <= 6; ++k3) {
                for (int j3 = -1; !this.isOpaque(world, i3, j3, k3) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, Blocks.dirt, 0);
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
                for (int j3 = 1; j3 <= 4; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
            }
        }
        this.loadStrScan("harnedor_pasture");
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", super.fenceGateBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        for (final int i4 : new int[] { -2, -1, 1, 2 }) {
            int j2 = 0;
            for (int step = 0; step < 6; ++step) {
                final int k4 = -4 - step;
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
        for (int animals = 2 + random.nextInt(4), l = 0; l < animals; ++l) {
            final EntityCreature animal = (EntityCreature)getRandomAnimal(world, random);
            this.spawnNPCAndSetHome(animal, world, 0, 1, 0, 0);
            animal.detachHome();
        }
        return true;
    }
    
    public static EntityAnimal getRandomAnimal(final World world, final Random random) {
        final int animal = random.nextInt(5);
        if (animal == 0) {
            return (EntityAnimal)new EntityCow(world);
        }
        if (animal == 1) {
            return (EntityAnimal)new EntityPig(world);
        }
        if (animal == 2) {
            return (EntityAnimal)new EntitySheep(world);
        }
        if (animal == 3) {
            return (EntityAnimal)new EntityChicken(world);
        }
        if (animal == 4) {
            return (EntityAnimal)new LOTREntityCamel(world);
        }
        return null;
    }
}

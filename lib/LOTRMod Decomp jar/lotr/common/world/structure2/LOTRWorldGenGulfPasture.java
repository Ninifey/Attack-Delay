// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGulfPasture extends LOTRWorldGenGulfStructure
{
    public LOTRWorldGenGulfPasture(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -5; i2 <= 5; ++i2) {
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
                    if (maxHeight - minHeight > 6) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            for (int k3 = -5; k3 <= 5; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (i4 != 5 || k4 != 5) {
                    for (int j2 = 1; j2 <= 6; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
            }
        }
        this.loadStrScan("gulf_pasture");
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", super.fenceGateBlock);
        this.associateBlockMetaAlias("FLAG", super.flagBlock, super.flagMeta);
        this.associateBlockMetaAlias("BONE", super.boneBlock, super.boneMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        for (int animals = 2 + random.nextInt(4), l = 0; l < animals; ++l) {
            final EntityCreature animal = (EntityCreature)LOTRWorldGenHarnedorPasture.getRandomAnimal(world, random);
            this.spawnNPCAndSetHome(animal, world, 0, 1, 0, 0);
            animal.detachHome();
        }
        return true;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenEriadorDowns extends LOTRBiomeGenEriador
{
    private WorldGenerator boulderGen;
    
    public LOTRBiomeGenEriadorDowns(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 4);
        this.clearBiomeVariants();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
        super.decorator.grassPerChunk = 5;
        super.decorator.doubleGrassPerChunk = 1;
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(24) == 0) {
            for (int l = 0; l < 3; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.25f;
    }
}

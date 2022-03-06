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

public class LOTRBiomeGenAnduinVale extends LOTRBiomeGenAnduin
{
    private WorldGenerator valeBoulders;
    
    public LOTRBiomeGenAnduinVale(final int i, final boolean major) {
        super(i, major);
        this.valeBoulders = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 5);
        this.clearBiomeVariants();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.2f);
        super.variantChance = 0.3f;
        super.decorator.setTreeCluster(10, 20);
        super.decorator.willowPerChunk = 2;
        super.decorator.flowersPerChunk = 5;
        super.decorator.doubleFlowersPerChunk = 2;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 3;
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(16) == 0) {
            for (int l = 0; l < 3; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.valeBoulders.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.25f;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.5f;
    }
}

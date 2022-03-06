// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.LOTRWorldGenHobbitTavern;
import lotr.common.world.structure2.LOTRWorldGenHobbitFarm;
import lotr.common.world.structure2.LOTRWorldGenHobbitWindmill;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenShireMoors extends LOTRBiomeGenShire
{
    private WorldGenerator boulderSmall;
    private WorldGenerator boulderLarge;
    
    public LOTRBiomeGenShireMoors(final int i, final boolean major) {
        super(i, major);
        this.boulderSmall = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 2);
        this.boulderLarge = new LOTRWorldGenBoulder(Blocks.stone, 0, 3, 5);
        this.clearBiomeVariants();
        super.variantChance = 0.2f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
        super.decorator.treesPerChunk = 0;
        super.decorator.flowersPerChunk = 16;
        super.decorator.doubleFlowersPerChunk = 0;
        super.decorator.grassPerChunk = 16;
        super.decorator.doubleGrassPerChunk = 1;
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 8000);
        super.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 2000);
        this.addFlower(LOTRMod.shireHeather, 0, 100);
        super.biomeColors.resetGrass();
        super.decorator.addRandomStructure(new LOTRWorldGenHobbitWindmill(false), 500);
        super.decorator.addRandomStructure(new LOTRWorldGenHobbitFarm(false), 1000);
        super.decorator.addRandomStructure(new LOTRWorldGenHobbitTavern(false), 200);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.SHIRE.getSubregion("moors");
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(8) == 0) {
            for (int l = 0; l < 4; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.boulderSmall.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
        if (random.nextInt(30) == 0) {
            for (int l = 0; l < 4; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.boulderLarge.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.1f;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.25f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return super.spawnCountMultiplier() * 2;
    }
}

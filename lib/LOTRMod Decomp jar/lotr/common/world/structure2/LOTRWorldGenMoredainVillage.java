// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.util.MathHelper;
import lotr.common.world.biome.LOTRBiomeGenFarHaradSavannah;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenMoredainVillage extends LOTRWorldGenStructureBase2
{
    private static int VILLAGE_SIZE;
    
    public LOTRWorldGenMoredainVillage(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, (super.usingPlayer != null) ? (LOTRWorldGenMoredainVillage.VILLAGE_SIZE + 1) : 0);
        if (super.restrictions) {
            boolean suitableSpawn = false;
            final BiomeGenBase biome = world.getBiomeGenForCoords(super.originX, super.originZ);
            if (biome instanceof LOTRBiomeGenFarHaradSavannah) {
                suitableSpawn = LOTRBiomeGenFarHaradSavannah.isBiomePopulated(super.originX, super.originY, super.originZ);
            }
            if (!suitableSpawn) {
                return false;
            }
        }
        final int huts = MathHelper.getRandomIntegerInRange(random, 3, 6);
        final int traderHuts = MathHelper.getRandomIntegerInRange(random, 0, 2);
        for (int chieftainHuts = MathHelper.getRandomIntegerInRange(random, 0, 1), l = 0; l < chieftainHuts; ++l) {
            final LOTRWorldGenStructureBase2 structure = new LOTRWorldGenMoredainHutChieftain(super.notifyChanges);
            this.attemptHutSpawn(structure, world, random);
        }
        for (int l = 0; l < huts; ++l) {
            final LOTRWorldGenStructureBase2 structure = new LOTRWorldGenMoredainHutVillage(super.notifyChanges);
            this.attemptHutSpawn(structure, world, random);
        }
        for (int l = 0; l < traderHuts; ++l) {
            final LOTRWorldGenStructureBase2 structure = new LOTRWorldGenMoredainHutTrader(super.notifyChanges);
            this.attemptHutSpawn(structure, world, random);
        }
        return true;
    }
    
    private boolean attemptHutSpawn(final LOTRWorldGenStructureBase2 structure, final World world, final Random random) {
        structure.restrictions = super.restrictions;
        structure.usingPlayer = super.usingPlayer;
        for (int l = 0; l < 16; ++l) {
            final int x = MathHelper.getRandomIntegerInRange(random, -LOTRWorldGenMoredainVillage.VILLAGE_SIZE, LOTRWorldGenMoredainVillage.VILLAGE_SIZE);
            final int z = MathHelper.getRandomIntegerInRange(random, -LOTRWorldGenMoredainVillage.VILLAGE_SIZE, LOTRWorldGenMoredainVillage.VILLAGE_SIZE);
            final int spawnX = this.getX(x, z);
            final int spawnZ = this.getZ(x, z);
            final int spawnY = this.getY(this.getTopBlock(world, x, z));
            final int rotation = random.nextInt(4);
            if (structure.generateWithSetRotation(world, random, spawnX, spawnY, spawnZ, rotation)) {
                return true;
            }
        }
        return false;
    }
    
    static {
        LOTRWorldGenMoredainVillage.VILLAGE_SIZE = 16;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenAngmarHillmanVillage extends LOTRWorldGenStructureBase2
{
    private static int VILLAGE_SIZE;
    
    public LOTRWorldGenAngmarHillmanVillage(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        if (super.restrictions) {
            final boolean suitableSpawn = true;
            final BiomeGenBase biome = world.getBiomeGenForCoords(super.originX, super.originZ);
            if (!suitableSpawn) {
                return false;
            }
        }
        final int houses = MathHelper.getRandomIntegerInRange(random, 3, 6);
        for (int chiefainHouses = MathHelper.getRandomIntegerInRange(random, 0, 1), l = 0; l < chiefainHouses; ++l) {
            final LOTRWorldGenStructureBase2 structure = new LOTRWorldGenAngmarHillmanChieftainHouse(super.notifyChanges);
            this.attemptHouseSpawn(structure, world, random);
        }
        for (int l = 0; l < houses; ++l) {
            final LOTRWorldGenStructureBase2 structure = new LOTRWorldGenAngmarHillmanHouse(super.notifyChanges);
            this.attemptHouseSpawn(structure, world, random);
        }
        return true;
    }
    
    private boolean attemptHouseSpawn(final LOTRWorldGenStructureBase2 structure, final World world, final Random random) {
        structure.restrictions = super.restrictions;
        structure.usingPlayer = super.usingPlayer;
        for (int l = 0; l < 16; ++l) {
            final int x = MathHelper.getRandomIntegerInRange(random, -LOTRWorldGenAngmarHillmanVillage.VILLAGE_SIZE, LOTRWorldGenAngmarHillmanVillage.VILLAGE_SIZE);
            final int z = MathHelper.getRandomIntegerInRange(random, -LOTRWorldGenAngmarHillmanVillage.VILLAGE_SIZE, LOTRWorldGenAngmarHillmanVillage.VILLAGE_SIZE);
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
        LOTRWorldGenAngmarHillmanVillage.VILLAGE_SIZE = 16;
    }
}

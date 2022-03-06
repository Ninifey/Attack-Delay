// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import java.util.ArrayList;
import java.util.Iterator;
import lotr.common.world.feature.LOTRWorldGenSkullPile;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import lotr.common.world.LOTRUtumnoLevel;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.LOTRMod;
import lotr.common.LOTRDimension;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.feature.LOTRWorldGenStalactites;
import java.util.List;

public class LOTRBiomeGenUtumno extends LOTRBiome
{
    private static List<LOTRBiome> utumnoBiomes;
    private LOTRWorldGenStalactites stalactiteGen;
    private LOTRWorldGenStalactites stalactiteIceGen;
    private LOTRWorldGenStalactites stalactiteObsidianGen;
    private LOTRBiomeSpawnList spawnableGuestList;
    
    public LOTRBiomeGenUtumno(final int i) {
        super(i, false, LOTRDimension.UTUMNO);
        this.stalactiteGen = new LOTRWorldGenStalactites(LOTRMod.stalactite);
        this.stalactiteIceGen = new LOTRWorldGenStalactites(LOTRMod.stalactiteIce);
        this.stalactiteObsidianGen = new LOTRWorldGenStalactites(LOTRMod.stalactiteObsidian);
        this.spawnableGuestList = new LOTRBiomeSpawnList(this);
        LOTRBiomeGenUtumno.utumnoBiomes.add(this);
        this.setDisableRain();
        super.spawnableCreatureList.clear();
        super.spawnableWaterCreatureList.clear();
        super.spawnableLOTRAmbientList.clear();
        super.spawnableCaveCreatureList.clear();
        super.npcSpawnList.clear();
        super.biomeColors.setGrass(0);
        super.biomeColors.setFoliage(0);
        super.biomeColors.setSky(0);
        super.biomeColors.setFoggy(true);
        super.biomeColors.setWater(0);
        final LOTRBiomeSpawnList.FactionContainer factionList = this.spawnableGuestList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = { null };
        final int n = 0;
        final LOTRBiomeSpawnList spawnableGuestList = this.spawnableGuestList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.UTUMNO_GUESTS, 10);
        factionList.add(lists);
    }
    
    @Override
    public LOTRBiomeSpawnList getNPCSpawnList(final World world, final Random random, final int i, final int j, final int k, final LOTRBiomeVariant variant) {
        if (random.nextInt(1000) == 0) {
            return this.spawnableGuestList;
        }
        return LOTRUtumnoLevel.forY(j).getNPCSpawnList();
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.UTUMNO.getSubregion("utumno");
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        this.generateHoles(world, random, i, k);
        this.generatePits(world, random, i, k);
        this.generateBridges(world, random, i, k);
        this.generateStairs(world, random, i, k);
        this.generatePillars(world, random, i, k);
        this.generatePortalBases(world, random, i, k);
        this.generateBars(world, random, i, k);
        this.generateStalactites(world, random, i, k);
        this.generateSkulls(world, random, i, k);
    }
    
    private void generateHoles(final World world, final Random random, final int i, final int k) {
        for (int l = 0; l < 8; ++l) {
            final int i2 = i + 8 + random.nextInt(16);
            final int k2 = k + 8 + random.nextInt(16);
            final int j1 = MathHelper.getRandomIntegerInRange(random, 20, 240);
            if (world.isAirBlock(i2, j1, k2)) {
                final LOTRUtumnoLevel level = LOTRUtumnoLevel.forY(j1);
                for (int j2 = j1; j2 >= level.corridorBaseLevels[0] - 1; --j2) {
                    if (world.isAirBlock(i2, j2, k2) && random.nextInt(10) == 0) {
                        break;
                    }
                    if (LOTRUtumnoLevel.forY(j2 - 1) != level) {
                        break;
                    }
                    for (int i3 = i2 - 1; i3 <= i2; ++i3) {
                        for (int k3 = k2 - 1; k3 <= k2; ++k3) {
                            world.setBlockToAir(i3, j2, k3);
                        }
                    }
                }
            }
        }
    }
    
    private void generatePits(final World world, final Random random, final int i, final int k) {
        if (random.nextInt(5) == 0) {
            final int i2 = i + 8 + random.nextInt(16);
            final int k2 = k + 8 + random.nextInt(16);
            final int j1 = MathHelper.getRandomIntegerInRange(random, 20, 220);
            if (world.isAirBlock(i2, j1, k2)) {
                final int radius = 8 + random.nextInt(30);
                final LOTRUtumnoLevel level = LOTRUtumnoLevel.forY(j1);
                final int yMin = Math.max(j1 - radius, level.baseLevel + 5);
                final int yMax = Math.min(j1 + radius, level.topLevel - 5);
                for (int i3 = i2 - radius; i3 <= i2 + radius; ++i3) {
                    for (int j2 = yMin; j2 <= yMax; ++j2) {
                        for (int k3 = k2 - radius; k3 <= k2 + radius; ++k3) {
                            final int i4 = Math.abs(i3 - i2);
                            final int j3 = Math.abs(j2 - j1);
                            final int k4 = Math.abs(k3 - k2);
                            final double dist = i4 * i4 + j3 * j3 + k4 * k4;
                            if (dist < (radius - 5) * (radius - 5)) {
                                world.setBlockToAir(i3, j2, k3);
                            }
                            else if (dist < radius * radius && random.nextInt(6) == 0) {
                                world.setBlockToAir(i3, j2, k3);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void generateBridges(final World world, final Random random, final int i, final int k) {
        for (int l = 0; l < 20; ++l) {
            final int i2 = i + 8 + random.nextInt(16);
            final int k2 = k + 8 + random.nextInt(16);
            final LOTRUtumnoLevel utumnoLevel = LOTRUtumnoLevel.values()[random.nextInt(LOTRUtumnoLevel.values().length)];
            for (int j1 = utumnoLevel.corridorBaseLevels[random.nextInt(utumnoLevel.corridorBaseLevels.length)] - 1, fuzz = 2, j2 = j1 - fuzz; j2 <= j1 + fuzz; ++j2) {
                final Block block = world.getBlock(i2, j2, k2);
                final int meta = world.getBlockMetadata(i2, j2, k2);
                if (block.isOpaqueCube() && world.isAirBlock(i2, j2 + 1, k2) && (world.isAirBlock(i2 - 1, j2, k2) || world.isAirBlock(i2 + 1, j2, k2) || world.isAirBlock(i2, j2, k2 - 1) || world.isAirBlock(i2, j2, k2 + 1))) {
                    int[] bridge = this.searchForBridge(world, i2, j2, k2, -1, 0);
                    if (bridge == null) {
                        bridge = this.searchForBridge(world, i2, j2, k2, 1, 0);
                        if (bridge == null) {
                            bridge = this.searchForBridge(world, i2, j2, k2, 0, -1);
                            if (bridge == null) {
                                bridge = this.searchForBridge(world, i2, j2, k2, 0, 1);
                            }
                        }
                    }
                    if (bridge != null) {
                        final int xRange = bridge[0];
                        final int zRange = bridge[1];
                        int startX = i2;
                        int endX = i2;
                        int startZ = k2;
                        int endZ = k2;
                        if (xRange >= 0) {
                            endX += xRange;
                        }
                        else {
                            startX -= -xRange;
                        }
                        if (zRange >= 0) {
                            endZ += zRange;
                        }
                        else {
                            startZ -= -zRange;
                        }
                        if (xRange == 0) {
                            final int xWidth = random.nextInt(3);
                            startX -= xWidth;
                            endX += xWidth;
                        }
                        if (zRange == 0) {
                            final int zWidth = random.nextInt(3);
                            startZ -= zWidth;
                            endZ += zWidth;
                        }
                        for (int x = startX; x <= endX; ++x) {
                            for (int z = startZ; z <= endZ; ++z) {
                                if (random.nextInt(8) != 0) {
                                    world.setBlock(x, j2, z, utumnoLevel.brickBlock, utumnoLevel.brickMeta, 2);
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
    
    private int[] searchForBridge(final World world, final int i, final int j, final int k, final int xDirection, final int zDirection) {
        final LOTRUtumnoLevel utumnoLevel = LOTRUtumnoLevel.forY(j);
        final int maxBridgeLength = 16;
        final int minBridgeLength = 2 + utumnoLevel.corridorWidth / 2;
        int foundAir = 0;
        int foundBrick = 0;
        int x = 0;
        int z = 0;
        while (Math.abs(x) < maxBridgeLength) {
            if (Math.abs(z) >= maxBridgeLength) {
                break;
            }
            if (xDirection == -1) {
                --x;
            }
            if (xDirection == 1) {
                ++x;
            }
            if (zDirection == -1) {
                --z;
            }
            if (zDirection == 1) {
                ++z;
            }
            final int i2 = i + x;
            final int k2 = k + z;
            if (foundAir == 0 && world.isAirBlock(i2, j, k2)) {
                if (xDirection == 0) {
                    foundAir = z;
                }
                else if (zDirection == 0) {
                    foundAir = x;
                }
            }
            if (foundAir == 0 || !world.getBlock(i2, j, k2).isOpaqueCube()) {
                continue;
            }
            if (xDirection == 0) {
                foundBrick = z;
                break;
            }
            if (zDirection == 0) {
                foundBrick = x;
                break;
            }
            break;
        }
        if (foundBrick != 0 && Math.abs(foundBrick - foundAir) >= minBridgeLength) {
            return new int[] { x, z };
        }
        return null;
    }
    
    private void generateStairs(final World world, final Random random, final int i, final int k) {
        for (int l = 0; l < 8; ++l) {
            final int i2 = i + 8 + random.nextInt(16);
            final int k2 = k + 8 + random.nextInt(16);
            final LOTRUtumnoLevel utumnoLevel = LOTRUtumnoLevel.values()[random.nextInt(LOTRUtumnoLevel.values().length)];
            for (int j1 = utumnoLevel.corridorBaseLevels[1 + random.nextInt(utumnoLevel.corridorBaseLevels.length - 1)] - 1, fuzz = 2, j2 = j1 - fuzz; j2 <= j1 + fuzz; ++j2) {
                if (world.getBlock(i2, j2, k2).isOpaqueCube() && world.isAirBlock(i2, j2 + 1, k2)) {
                    int xDirection = 0;
                    int zDirection = 0;
                    int stairMeta = 0;
                    if (random.nextBoolean()) {
                        xDirection = (random.nextBoolean() ? 1 : -1);
                        stairMeta = ((xDirection > 0) ? 1 : 0);
                    }
                    else {
                        zDirection = (random.nextBoolean() ? 1 : -1);
                        stairMeta = ((zDirection > 0) ? 3 : 2);
                    }
                    int stairX = i2;
                    int stairY = j2;
                    int stairZ = k2;
                    final int minStairRange = 6;
                    final int maxStairRange = 20;
                    final int stairWidth = 1 + random.nextInt(3);
                    final int stairHeight = stairWidth + 2;
                    int stair = 0;
                    do {
                        for (int w = 0; w < stairWidth; ++w) {
                            final int i3 = stairX + w * zDirection;
                            final int k3 = stairZ + w * xDirection;
                            world.setBlock(i3, stairY, k3, utumnoLevel.brickStairBlock, stairMeta, 2);
                            if (world.isAirBlock(i3, stairY - 1, k3)) {
                                world.setBlock(i3, stairY - 1, k3, utumnoLevel.brickStairBlock, (stairMeta ^ 0x1) | 0x4, 2);
                            }
                            for (int j3 = stairY + 1; j3 <= stairY + stairHeight; ++j3) {
                                world.setBlock(i3, j3, k3, Blocks.air);
                            }
                        }
                        if (++stair >= maxStairRange) {
                            break;
                        }
                        if (stair >= minStairRange && random.nextInt(10) == 0) {
                            break;
                        }
                        if (xDirection == -1) {
                            --stairX;
                        }
                        if (xDirection == 1) {
                            ++stairX;
                        }
                        if (zDirection == -1) {
                            --stairZ;
                        }
                        if (zDirection != 1) {
                            continue;
                        }
                        ++stairZ;
                    } while (--stairY > utumnoLevel.corridorBaseLevels[0]);
                    break;
                }
            }
        }
    }
    
    private void generatePillars(final World world, final Random random, final int i, final int k) {
        for (int l = 0; l < 40; ++l) {
            final int i2 = i + 8 + random.nextInt(16);
            final int k2 = k + 8 + random.nextInt(16);
            final LOTRUtumnoLevel utumnoLevel = LOTRUtumnoLevel.values()[random.nextInt(LOTRUtumnoLevel.values().length)];
            final int j1 = utumnoLevel.corridorBaseLevels[random.nextInt(utumnoLevel.corridorBaseLevels.length)];
            final int pillarHeight = MathHelper.getRandomIntegerInRange(random, 1, utumnoLevel.corridorHeight);
            for (int fuzz = 2, j2 = j1 - fuzz; j2 <= j1 + fuzz; ++j2) {
                if (!world.isAirBlock(i2, j2 - 1, k2)) {
                    boolean generated = false;
                    for (int j3 = j2; j3 <= j2 + pillarHeight && world.isAirBlock(i2, j3, k2); ++j3) {
                        world.setBlock(i2, j3, k2, utumnoLevel.pillarBlock, utumnoLevel.pillarMeta, 2);
                        generated = true;
                    }
                    if (generated) {
                        break;
                    }
                }
            }
        }
    }
    
    private void generatePortalBases(final World world, final Random random, final int i, final int k) {
        for (int l = 0; l < 1; ++l) {
            final int i2 = i + 8 + random.nextInt(16);
            final int k2 = k + 8 + random.nextInt(16);
            final float f = random.nextFloat();
            LOTRUtumnoLevel utumnoLevel;
            if (f < 0.15f) {
                utumnoLevel = LOTRUtumnoLevel.ICE;
            }
            else if (f < 0.5f) {
                utumnoLevel = LOTRUtumnoLevel.OBSIDIAN;
            }
            else {
                utumnoLevel = LOTRUtumnoLevel.FIRE;
            }
            for (int j1 = utumnoLevel.corridorBaseLevels[random.nextInt(utumnoLevel.corridorBaseLevels.length)], fuzz = 2, j2 = j1 - fuzz; j2 <= j1 + fuzz; ++j2) {
                if (world.isAirBlock(i2, j2, k2) && World.doesBlockHaveSolidTopSurface((IBlockAccess)world, i2, j2 - 1, k2)) {
                    world.setBlock(i2, j2, k2, LOTRMod.utumnoReturnPortalBase, 0, 2);
                    break;
                }
            }
        }
    }
    
    private void generateBars(final World world, final Random random, final int i, final int k) {
        for (int l = 0; l < 200; ++l) {
            final int i2 = i + 8 + random.nextInt(16);
            final int k2 = k + 8 + random.nextInt(16);
            final int j1 = MathHelper.getRandomIntegerInRange(random, 4, 250);
            if (world.getBlock(i2, j1, k2).isOpaqueCube()) {
                final int barWidth = 1 + random.nextInt(3);
                final int barHeight = 2 + random.nextInt(3);
                final boolean fire = random.nextInt(3) == 0;
                int facingX = 0;
                int facingZ = 0;
                if (random.nextBoolean()) {
                    facingX = (random.nextBoolean() ? -1 : 1);
                }
                else {
                    facingZ = (random.nextBoolean() ? -1 : 1);
                }
                boolean generate = true;
            Label_0311:
                for (int pass = 0; pass <= 1; ++pass) {
                    for (int xz = 0; xz < barWidth; ++xz) {
                        int y = -1;
                        while (y < barHeight + 1) {
                            final int i3 = i2 + xz * facingZ;
                            final int j2 = j1 + y;
                            final int k3 = k2 + xz * facingX;
                            boolean flag = true;
                            if (!world.getBlock(i3, j2, k3).isOpaqueCube()) {
                                flag = false;
                            }
                            if (y >= 0 && y < barHeight && !world.isAirBlock(i3 + facingX, j2, k3 + facingZ)) {
                                flag = false;
                            }
                            if (!flag) {
                                if (pass == 0) {
                                    generate = true;
                                    final int fx = facingX;
                                    final int fz = facingX = facingZ;
                                    facingZ = fx;
                                    continue Label_0311;
                                }
                                generate = false;
                                break Label_0311;
                            }
                            else {
                                ++y;
                            }
                        }
                    }
                }
                if (generate) {
                    for (int xz2 = 0; xz2 < barWidth; ++xz2) {
                        for (int y2 = 0; y2 < barHeight; ++y2) {
                            final int i4 = i2 + xz2 * facingZ;
                            final int j3 = j1 + y2;
                            final int k4 = k2 + xz2 * facingX;
                            world.setBlock(i4, j3, k4, LOTRMod.orcSteelBars, 0, 2);
                            if (fire && y2 == 0) {
                                final int i5 = i4 - facingX;
                                final int k5 = k4 - facingZ;
                                if (world.getBlock(i5, j3, k5).isOpaqueCube()) {
                                    world.setBlock(i5, j3 - 1, k5, LOTRMod.hearth, 0, 2);
                                    world.setBlock(i5, j3, k5, (Block)Blocks.fire, 0, 2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void generateStalactites(final World world, final Random random, final int i, final int k) {
        for (int l = 0; l < 2; ++l) {
            final int i2 = i + 8 + random.nextInt(16);
            final int k2 = k + 8 + random.nextInt(16);
            final int j1 = MathHelper.getRandomIntegerInRange(random, LOTRUtumnoLevel.ICE.baseLevel, LOTRUtumnoLevel.ICE.topLevel);
            if (random.nextBoolean()) {
                this.stalactiteGen.generate(world, random, i2, j1, k2);
            }
            else {
                this.stalactiteIceGen.generate(world, random, i2, j1, k2);
            }
        }
        for (int l = 0; l < 2; ++l) {
            final int i2 = i + 8 + random.nextInt(16);
            final int k2 = k + 8 + random.nextInt(16);
            final int j1 = MathHelper.getRandomIntegerInRange(random, LOTRUtumnoLevel.OBSIDIAN.baseLevel, LOTRUtumnoLevel.OBSIDIAN.topLevel);
            if (random.nextBoolean()) {
                this.stalactiteGen.generate(world, random, i2, j1, k2);
            }
            else {
                this.stalactiteObsidianGen.generate(world, random, i2, j1, k2);
            }
        }
        for (int l = 0; l < 2; ++l) {
            final int i2 = i + 8 + random.nextInt(16);
            final int k2 = k + 8 + random.nextInt(16);
            final int j1 = MathHelper.getRandomIntegerInRange(random, LOTRUtumnoLevel.FIRE.baseLevel, LOTRUtumnoLevel.FIRE.topLevel);
            this.stalactiteObsidianGen.generate(world, random, i2, j1, k2);
        }
    }
    
    private void generateSkulls(final World world, final Random random, final int i, final int k) {
        for (int l = 0; l < 4; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            final int j1 = MathHelper.getRandomIntegerInRange(random, 4, 250);
            new LOTRWorldGenSkullPile().generate(world, random, i2, j1, k2);
        }
    }
    
    @Override
    public boolean canSpawnHostilesInDay() {
        return true;
    }
    
    public static void updateFogColor(final int i, final int j, final int k) {
        final LOTRUtumnoLevel utumnoLevel = LOTRUtumnoLevel.forY(j);
        for (final LOTRBiome biome : LOTRBiomeGenUtumno.utumnoBiomes) {
            biome.biomeColors.setFog(utumnoLevel.fogColor);
        }
    }
    
    static {
        LOTRBiomeGenUtumno.utumnoBiomes = new ArrayList<LOTRBiome>();
    }
}

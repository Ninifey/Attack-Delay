// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import net.minecraftforge.common.util.ForgeDirection;
import lotr.common.LOTRFoods;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;

public abstract class LOTRWorldGenWargPitBase extends LOTRWorldGenStructureBase2
{
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brickSlabBlock;
    protected int brickSlabMeta;
    protected Block brickStairBlock;
    protected Block brickWallBlock;
    protected int brickWallMeta;
    protected Block pillarBlock;
    protected int pillarMeta;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block plankSlabBlock;
    protected int plankSlabMeta;
    protected Block plankStairBlock;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block beamBlock;
    protected int beamMeta;
    protected Block doorBlock;
    protected Block woolBlock;
    protected int woolMeta;
    protected Block carpetBlock;
    protected int carpetMeta;
    protected Block barsBlock;
    protected Block gateOrcBlock;
    protected Block gateMetalBlock;
    protected Block tableBlock;
    protected Block bedBlock;
    protected LOTRItemBanner.BannerType banner;
    protected LOTRChestContents chestContents;
    
    public LOTRWorldGenWargPitBase(final boolean flag) {
        super(flag);
    }
    
    protected abstract LOTREntityNPC getOrc(final World p0);
    
    protected abstract LOTREntityNPC getWarg(final World p0);
    
    protected abstract void setOrcSpawner(final LOTREntityNPCRespawner p0);
    
    protected abstract void setWargSpawner(final LOTREntityNPCRespawner p0);
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        this.plankBlock = LOTRMod.planks;
        this.plankMeta = 3;
        this.plankSlabBlock = LOTRMod.woodSlabSingle;
        this.plankSlabMeta = 3;
        this.plankStairBlock = LOTRMod.stairsCharred;
        this.fenceBlock = LOTRMod.fence;
        this.fenceMeta = 3;
        this.beamBlock = LOTRMod.woodBeam1;
        this.beamMeta = 3;
        this.doorBlock = LOTRMod.doorCharred;
        this.woolBlock = Blocks.wool;
        this.woolMeta = 12;
        this.carpetBlock = Blocks.carpet;
        this.carpetMeta = 12;
        this.barsBlock = LOTRMod.orcSteelBars;
        this.gateOrcBlock = LOTRMod.gateOrc;
        this.gateMetalBlock = LOTRMod.gateBronzeBars;
        this.bedBlock = LOTRMod.orcBed;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 8, -10);
        super.originY -= 4;
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -13; i2 <= 12; ++i2) {
                for (int k2 = -12; k2 <= 14; ++k2) {
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
                }
            }
            if (maxHeight - minHeight > 12) {
                return false;
            }
        }
        for (int radius = 8, i3 = -radius; i3 <= radius; ++i3) {
            for (int k3 = -radius; k3 <= radius; ++k3) {
                if (i3 * i3 + k3 * k3 < radius * radius) {
                    for (int j3 = 0; j3 <= 12; ++j3) {
                        this.setAir(world, i3, j3, k3);
                    }
                }
            }
        }
        for (int r2 = 12, i2 = -r2; i2 <= r2; ++i2) {
            for (int k2 = -r2; k2 <= r2; ++k2) {
                if (i2 * i2 + k2 * k2 < r2 * r2 && k2 >= -4 && i2 <= 4) {
                    for (int j2 = 0; j2 <= 12; ++j2) {
                        this.setAir(world, i2, j2, k2);
                    }
                }
            }
        }
        for (int i2 = -12; i2 <= -8; ++i2) {
            for (int k2 = -7; k2 <= -4; ++k2) {
                if (k2 == -7) {
                    if (i2 == -12) {
                        continue;
                    }
                    if (i2 == -8) {
                        continue;
                    }
                }
                for (int j2 = 5; j2 <= 12; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = 8; k2 <= 12; ++k2) {
                for (int j2 = 7; j2 <= 11; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int k2 = -11; k2 <= -6; ++k2) {
                for (int j2 = 0; j2 <= 3; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        for (int i2 = 6; i2 <= 11; ++i2) {
            for (int k2 = -1; k2 <= 1; ++k2) {
                for (int j2 = 0; j2 <= 3; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        this.loadStrScan("warg_pit");
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", this.brickSlabBlock, this.brickSlabMeta | 0x8);
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", this.brickWallBlock, this.brickWallMeta);
        this.associateBlockMetaAlias("PILLAR", this.pillarBlock, this.pillarMeta);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
        this.associateBlockMetaAlias("BEAM|4", this.beamBlock, this.beamMeta | 0x4);
        this.associateBlockMetaAlias("BEAM|8", this.beamBlock, this.beamMeta | 0x8);
        this.associateBlockAlias("DOOR", this.doorBlock);
        this.associateBlockMetaAlias("WOOL", this.woolBlock, this.woolMeta);
        this.associateBlockMetaAlias("CARPET", this.carpetBlock, this.carpetMeta);
        this.associateGroundBlocks();
        this.associateBlockMetaAlias("BARS", this.barsBlock, 0);
        this.associateBlockAlias("GATE_ORC", this.gateOrcBlock);
        this.associateBlockAlias("GATE_METAL", this.gateMetalBlock);
        this.associateBlockMetaAlias("TABLE", this.tableBlock, 0);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeWallBanner(world, -7, 5, 0, this.banner, 1);
        this.placeWallBanner(world, 7, 5, 0, this.banner, 3);
        this.placeWallBanner(world, 0, 5, -7, this.banner, 0);
        this.placeWallBanner(world, 0, 5, 7, this.banner, 2);
        this.placeOrcTorch(world, 2, 4, -5);
        this.placeOrcTorch(world, -2, 4, -5);
        this.placeOrcTorch(world, 5, 4, -2);
        this.placeOrcTorch(world, -5, 4, -2);
        this.placeOrcTorch(world, 5, 4, 2);
        this.placeOrcTorch(world, -5, 4, 2);
        this.placeOrcTorch(world, 2, 4, 5);
        this.placeOrcTorch(world, -2, 4, 5);
        this.placeOrcTorch(world, 1, 7, 8);
        this.placeOrcTorch(world, -1, 7, 8);
        this.placeOrcTorch(world, 4, 8, -4);
        this.placeOrcTorch(world, -4, 8, -4);
        this.placeOrcTorch(world, 4, 8, 4);
        this.placeOrcTorch(world, -4, 8, 4);
        this.placeOrcTorch(world, -8, 10, -4);
        this.placeOrcTorch(world, -12, 10, -4);
        this.placeChest(world, random, -7, 1, 0, 4, this.chestContents);
        this.placeChest(world, random, 1, 7, 12, 2, this.chestContents);
        this.setBlockAndMetadata(world, -2, 7, 9, this.bedBlock, 3);
        this.setBlockAndMetadata(world, -3, 7, 9, this.bedBlock, 11);
        this.setBlockAndMetadata(world, -2, 7, 11, this.bedBlock, 3);
        this.setBlockAndMetadata(world, -3, 7, 11, this.bedBlock, 11);
        this.placeBarrel(world, random, 3, 8, 11, 5, LOTRFoods.ORC_DRINK);
        this.placeMug(world, random, 3, 8, 10, 1, LOTRFoods.ORC_DRINK);
        this.placePlateWithCertainty(world, random, 3, 8, 9, LOTRMod.woodPlateBlock, LOTRFoods.ORC);
        final int maxStep = 12;
        for (int i4 = -1; i4 <= 1; ++i4) {
            for (int step = 0; step < 2; ++step) {
                final int j4 = 5 - step;
                final int k4 = -9 - step;
                if (this.isSideSolid(world, i4, j4, k4, ForgeDirection.UP)) {
                    break;
                }
                this.setBlockAndMetadata(world, i4, j4, k4, this.brickStairBlock, 2);
                this.setGrassToDirt(world, i4, j4 - 1, k4);
                for (int j5 = j4 - 1; !this.isSideSolid(world, i4, j5, k4, ForgeDirection.UP) && this.getY(j5) >= 0; --j5) {
                    this.setBlockAndMetadata(world, i4, j5, k4, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i4, j5 - 1, k4);
                }
            }
        }
        for (int i4 = -1; i4 <= 1; ++i4) {
            for (int step = 0; step < maxStep; ++step) {
                final int j4 = 3 - step;
                final int k4 = -13 - step;
                if (this.isOpaque(world, i4, j4, k4)) {
                    break;
                }
                this.setBlockAndMetadata(world, i4, j4, k4, this.brickStairBlock, 2);
                this.setGrassToDirt(world, i4, j4 - 1, k4);
                for (int j5 = j4 - 1; !this.isOpaque(world, i4, j5, k4) && this.getY(j5) >= 0; --j5) {
                    this.setBlockAndMetadata(world, i4, j5, k4, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i4, j5 - 1, k4);
                }
            }
        }
        for (int wargs = 2 + random.nextInt(5), l = 0; l < wargs; ++l) {
            final LOTREntityNPC warg = this.getWarg(world);
            this.spawnNPCAndSetHome(warg, world, 0, 1, 0, 8);
        }
        final LOTREntityNPC orc = this.getOrc(world);
        this.spawnNPCAndSetHome(orc, world, 0, 1, 0, 24);
        final LOTREntityNPCRespawner wargSpawner = new LOTREntityNPCRespawner(world);
        this.setWargSpawner(wargSpawner);
        wargSpawner.setCheckRanges(12, -8, 16, 8);
        wargSpawner.setSpawnRanges(4, -4, 4, 24);
        this.placeNPCRespawner(wargSpawner, world, 0, 0, 0);
        final LOTREntityNPCRespawner orcSpawner = new LOTREntityNPCRespawner(world);
        this.setOrcSpawner(orcSpawner);
        orcSpawner.setCheckRanges(32, -12, 20, 16);
        orcSpawner.setSpawnRanges(16, -4, 8, 16);
        this.placeNPCRespawner(orcSpawner, world, 0, 0, 0);
        return true;
    }
    
    protected void associateGroundBlocks() {
        this.addBlockMetaAliasOption("GROUND", 4, Blocks.dirt, 1);
        this.addBlockMetaAliasOption("GROUND", 4, LOTRMod.dirtPath, 0);
        this.addBlockMetaAliasOption("GROUND", 4, Blocks.gravel, 0);
        this.addBlockMetaAliasOption("GROUND", 4, Blocks.cobblestone, 0);
        this.addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingleDirt, 0);
        this.addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingleDirt, 1);
        this.addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingleGravel, 0);
        this.addBlockMetaAliasOption("GROUND_SLAB", 4, (Block)Blocks.stone_slab, 3);
        this.addBlockMetaAliasOption("GROUND_COVER", 1, LOTRMod.thatchFloor, 0);
        this.setBlockAliasChance("GROUND_COVER", 0.25f);
    }
    
    @Override
    protected void placeOrcTorch(final World world, final int i, final int j, final int k) {
        this.setBlockAndMetadata(world, i, j, k, LOTRMod.orcTorch, 0);
        this.setBlockAndMetadata(world, i, j + 1, k, LOTRMod.orcTorch, 1);
    }
}

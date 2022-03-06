// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.world.structure.LOTRChestContents;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import lotr.common.entity.npc.LOTREntityGundabadOrcMercenaryCaptain;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;

public class LOTRWorldGenRuinedDwarvenTower extends LOTRWorldGenDwarvenTower
{
    private boolean isGundabad;
    
    public LOTRWorldGenRuinedDwarvenTower(final boolean flag) {
        super(flag);
        super.ruined = true;
        super.glowBrickBlock = super.brickBlock;
        super.glowBrickMeta = super.brickMeta;
    }
    
    @Override
    protected LOTREntityNPC getCommanderNPC(final World world) {
        if (this.isGundabad) {
            return new LOTREntityGundabadOrcMercenaryCaptain(world);
        }
        return null;
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        if (random.nextInt(3) == 0) {
            super.plankBlock = LOTRMod.planks;
            super.plankMeta = 3;
            super.plankSlabBlock = LOTRMod.woodSlabSingle;
            super.plankSlabMeta = 3;
        }
        if (random.nextInt(4) == 0) {
            super.barsBlock = Blocks.air;
        }
        else {
            final int randomBars = random.nextInt(4);
            if (randomBars == 0) {
                super.barsBlock = LOTRMod.dwarfBars;
            }
            else if (randomBars == 1) {
                super.barsBlock = LOTRMod.orcSteelBars;
            }
            else if (randomBars == 2) {
                super.barsBlock = Blocks.iron_bars;
            }
            else if (randomBars == 3) {
                super.barsBlock = LOTRMod.bronzeBars;
            }
        }
        this.isGundabad = (random.nextInt(3) == 0);
        if (this.isGundabad) {
            super.gateBlock = LOTRMod.gateOrc;
            super.tableBlock = LOTRMod.gundabadTable;
            super.forgeBlock = LOTRMod.orcForge;
            super.bannerType = LOTRItemBanner.BannerType.GUNDABAD;
            super.chestContents = LOTRChestContents.GUNDABAD_TENT;
        }
        else {
            super.gateBlock = LOTRMod.gateDwarven;
            super.tableBlock = LOTRMod.dwarvenTable;
            super.forgeBlock = LOTRMod.dwarvenForge;
            super.bannerType = null;
            super.chestContents = LOTRChestContents.DWARVEN_TOWER;
        }
    }
    
    @Override
    protected void placeBrick(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(4) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick4, 5);
        }
        else {
            super.placeBrick(world, random, i, j, k);
        }
    }
    
    @Override
    protected void placeBrickSlab(final World world, final Random random, final int i, final int j, final int k, final boolean flip) {
        if (random.nextInt(4) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle7, 0x6 | (flip ? 8 : 0));
        }
        else {
            super.placeBrickSlab(world, random, i, j, k, flip);
        }
    }
    
    @Override
    protected void placeBrickStair(final World world, final Random random, final int i, final int j, final int k, final int meta) {
        if (random.nextInt(4) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsDwarvenBrickCracked, meta);
        }
        else {
            super.placeBrickStair(world, random, i, j, k, meta);
        }
    }
    
    @Override
    protected void placeBrickWall(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(4) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.wall4, 5);
        }
        else {
            super.placeBrickWall(world, random, i, j, k);
        }
    }
    
    @Override
    protected void placePillar(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(4) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.pillar2, 0);
        }
        else {
            super.placePillar(world, random, i, j, k);
        }
    }
}

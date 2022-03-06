// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.block.Block;
import java.util.Random;
import lotr.common.entity.npc.LOTREntityGaladhrimSmith;
import lotr.common.entity.npc.LOTREntityElf;
import net.minecraft.world.World;
import lotr.common.LOTRMod;

public class LOTRWorldGenGaladhrimForge extends LOTRWorldGenElvenForge
{
    public LOTRWorldGenGaladhrimForge(final boolean flag) {
        super(flag);
        super.brickBlock = LOTRMod.brick;
        super.brickMeta = 11;
        super.pillarBlock = LOTRMod.pillar;
        super.pillarMeta = 1;
        super.slabBlock = LOTRMod.slabSingle2;
        super.slabMeta = 3;
        super.carvedBrickBlock = LOTRMod.brick2;
        super.carvedBrickMeta = 15;
        super.wallBlock = LOTRMod.wall;
        super.wallMeta = 10;
        super.stairBlock = LOTRMod.stairsElvenBrick;
        super.torchBlock = LOTRMod.mallornTorchSilver;
        super.tableBlock = LOTRMod.elvenTable;
        super.barsBlock = LOTRMod.galadhrimBars;
        super.woodBarsBlock = LOTRMod.galadhrimWoodBars;
        super.roofBlock = LOTRMod.clayTileDyed;
        super.roofMeta = 4;
        super.roofStairBlock = LOTRMod.stairsClayTileDyedYellow;
        super.chestBlock = LOTRMod.chestMallorn;
    }
    
    @Override
    protected LOTREntityElf getElf(final World world) {
        return new LOTREntityGaladhrimSmith(world);
    }
    
    @Override
    protected Block getTorchBlock(final Random random) {
        return LOTRWorldGenElfHouse.getRandomTorch(random);
    }
}

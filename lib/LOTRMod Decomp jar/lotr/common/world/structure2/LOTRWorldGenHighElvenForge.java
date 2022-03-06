// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityHighElfSmith;
import lotr.common.entity.npc.LOTREntityElf;
import net.minecraft.world.World;
import lotr.common.LOTRMod;

public class LOTRWorldGenHighElvenForge extends LOTRWorldGenElvenForge
{
    public LOTRWorldGenHighElvenForge(final boolean flag) {
        super(flag);
        super.brickBlock = LOTRMod.brick3;
        super.brickMeta = 2;
        super.pillarBlock = LOTRMod.pillar;
        super.pillarMeta = 10;
        super.slabBlock = LOTRMod.slabSingle5;
        super.slabMeta = 5;
        super.carvedBrickBlock = LOTRMod.brick2;
        super.carvedBrickMeta = 13;
        super.wallBlock = LOTRMod.wall2;
        super.wallMeta = 11;
        super.stairBlock = LOTRMod.stairsHighElvenBrick;
        super.torchBlock = LOTRMod.highElvenTorch;
        super.tableBlock = LOTRMod.highElvenTable;
        super.barsBlock = LOTRMod.highElfBars;
        super.woodBarsBlock = LOTRMod.highElfWoodBars;
        super.roofBlock = LOTRMod.clayTileDyed;
        super.roofMeta = 3;
        super.roofStairBlock = LOTRMod.stairsClayTileDyedLightBlue;
    }
    
    @Override
    protected LOTREntityElf getElf(final World world) {
        return new LOTREntityHighElfSmith(world);
    }
}

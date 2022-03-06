// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityWoodElfSmith;
import lotr.common.entity.npc.LOTREntityElf;
import net.minecraft.world.World;
import lotr.common.LOTRMod;

public class LOTRWorldGenWoodElvenForge extends LOTRWorldGenElvenForge
{
    public LOTRWorldGenWoodElvenForge(final boolean flag) {
        super(flag);
        super.brickBlock = LOTRMod.brick3;
        super.brickMeta = 5;
        super.pillarBlock = LOTRMod.pillar;
        super.pillarMeta = 12;
        super.slabBlock = LOTRMod.slabSingle6;
        super.slabMeta = 2;
        super.carvedBrickBlock = LOTRMod.brick2;
        super.carvedBrickMeta = 14;
        super.wallBlock = LOTRMod.wall3;
        super.wallMeta = 0;
        super.stairBlock = LOTRMod.stairsWoodElvenBrick;
        super.torchBlock = LOTRMod.woodElvenTorch;
        super.tableBlock = LOTRMod.woodElvenTable;
        super.barsBlock = LOTRMod.woodElfBars;
        super.woodBarsBlock = LOTRMod.woodElfWoodBars;
        super.roofBlock = LOTRMod.clayTileDyed;
        super.roofMeta = 13;
        super.roofStairBlock = LOTRMod.stairsClayTileDyedGreen;
    }
    
    @Override
    protected LOTREntityElf getElf(final World world) {
        return new LOTREntityWoodElfSmith(world);
    }
}

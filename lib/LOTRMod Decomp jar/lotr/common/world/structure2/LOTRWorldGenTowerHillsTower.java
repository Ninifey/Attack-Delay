// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.LOTRMod;

public class LOTRWorldGenTowerHillsTower extends LOTRWorldGenHighElvenTower
{
    public LOTRWorldGenTowerHillsTower(final boolean flag) {
        super(flag);
        super.brickBlock = LOTRMod.brick4;
        super.brickMeta = 15;
        super.brickSlabBlock = LOTRMod.slabSingle9;
        super.brickSlabMeta = 0;
        super.brickStairBlock = LOTRMod.stairsChalkBrick;
        super.brickWallBlock = LOTRMod.wall3;
        super.brickWallMeta = 6;
        super.pillarBlock = LOTRMod.pillar2;
        super.pillarMeta = 1;
        super.floorBlock = LOTRMod.slabDouble8;
        super.floorMeta = 7;
        super.roofBlock = super.brickBlock;
        super.roofMeta = super.brickMeta;
        super.roofSlabBlock = super.brickSlabBlock;
        super.roofSlabMeta = super.brickSlabMeta;
        super.roofStairBlock = super.brickStairBlock;
    }
}

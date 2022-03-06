// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityMordorWarg;
import lotr.common.entity.npc.LOTREntityMordorOrc;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenMordorWargPit extends LOTRWorldGenWargPitBase
{
    public LOTRWorldGenMordorWargPit(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.brickBlock = LOTRMod.brick;
        super.brickMeta = 0;
        super.brickSlabBlock = LOTRMod.slabSingle;
        super.brickSlabMeta = 1;
        super.brickStairBlock = LOTRMod.stairsMordorBrick;
        super.brickWallBlock = LOTRMod.wall;
        super.brickWallMeta = 1;
        super.pillarBlock = LOTRMod.pillar;
        super.pillarMeta = 7;
        super.woolBlock = Blocks.wool;
        super.woolMeta = 12;
        super.carpetBlock = Blocks.carpet;
        super.carpetMeta = 12;
        super.gateMetalBlock = LOTRMod.gateIronBars;
        super.tableBlock = LOTRMod.morgulTable;
        super.banner = LOTRItemBanner.BannerType.MORDOR;
        super.chestContents = LOTRChestContents.ORC_TENT;
    }
    
    @Override
    protected LOTREntityNPC getOrc(final World world) {
        return new LOTREntityMordorOrc(world);
    }
    
    @Override
    protected LOTREntityNPC getWarg(final World world) {
        return new LOTREntityMordorWarg(world);
    }
    
    @Override
    protected void setOrcSpawner(final LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClass(LOTREntityMordorOrc.class);
    }
    
    @Override
    protected void setWargSpawner(final LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClass(LOTREntityMordorWarg.class);
    }
    
    @Override
    protected void associateGroundBlocks() {
        this.addBlockMetaAliasOption("GROUND", 4, LOTRMod.rock, 0);
        this.addBlockMetaAliasOption("GROUND", 4, LOTRMod.mordorDirt, 0);
        this.addBlockMetaAliasOption("GROUND", 4, LOTRMod.mordorGravel, 0);
        this.addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingle10, 7);
        this.addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingleDirt, 3);
        this.addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingleGravel, 1);
        this.addBlockMetaAliasOption("GROUND_COVER", 1, LOTRMod.mordorMoss, 0);
        this.setBlockAliasChance("GROUND_COVER", 0.25f);
    }
}

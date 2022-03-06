// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityMirkwoodSpider;
import lotr.common.entity.npc.LOTREntityDolGuldurOrc;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenDolGuldurSpiderPit extends LOTRWorldGenWargPitBase
{
    public LOTRWorldGenDolGuldurSpiderPit(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.brickBlock = LOTRMod.brick2;
        super.brickMeta = 8;
        super.brickSlabBlock = LOTRMod.slabSingle4;
        super.brickSlabMeta = 5;
        super.brickStairBlock = LOTRMod.stairsDolGuldurBrick;
        super.brickWallBlock = LOTRMod.wall2;
        super.brickWallMeta = 8;
        super.pillarBlock = super.beamBlock;
        super.pillarMeta = super.beamMeta;
        super.woolBlock = Blocks.wool;
        super.woolMeta = 15;
        super.carpetBlock = Blocks.carpet;
        super.carpetMeta = 15;
        super.gateMetalBlock = LOTRMod.gateIronBars;
        super.tableBlock = LOTRMod.dolGuldurTable;
        super.banner = LOTRItemBanner.BannerType.DOL_GULDUR;
        super.chestContents = LOTRChestContents.DOL_GULDUR_TENT;
    }
    
    @Override
    protected LOTREntityNPC getOrc(final World world) {
        return new LOTREntityDolGuldurOrc(world);
    }
    
    @Override
    protected LOTREntityNPC getWarg(final World world) {
        return new LOTREntityMirkwoodSpider(world);
    }
    
    @Override
    protected void setOrcSpawner(final LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClass(LOTREntityDolGuldurOrc.class);
    }
    
    @Override
    protected void setWargSpawner(final LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClass(LOTREntityMirkwoodSpider.class);
    }
    
    @Override
    protected void associateGroundBlocks() {
        super.associateGroundBlocks();
        this.clearScanAlias("GROUND_COVER");
        this.addBlockMetaAliasOption("GROUND_COVER", 1, LOTRMod.webUngoliant, 0);
        this.setBlockAliasChance("GROUND_COVER", 0.04f);
    }
}

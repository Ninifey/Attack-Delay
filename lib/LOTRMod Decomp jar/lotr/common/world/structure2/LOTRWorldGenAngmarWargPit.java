// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityAngmarWarg;
import lotr.common.entity.npc.LOTREntityAngmarOrc;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenAngmarWargPit extends LOTRWorldGenWargPitBase
{
    public LOTRWorldGenAngmarWargPit(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.brickBlock = LOTRMod.brick2;
        super.brickMeta = 0;
        super.brickSlabBlock = LOTRMod.slabSingle3;
        super.brickSlabMeta = 3;
        super.brickStairBlock = LOTRMod.stairsAngmarBrick;
        super.brickWallBlock = LOTRMod.wall2;
        super.brickWallMeta = 0;
        super.pillarBlock = LOTRMod.pillar2;
        super.pillarMeta = 4;
        super.woolBlock = Blocks.wool;
        super.woolMeta = 15;
        super.carpetBlock = Blocks.carpet;
        super.carpetMeta = 15;
        super.tableBlock = LOTRMod.angmarTable;
        super.banner = LOTRItemBanner.BannerType.ANGMAR;
        super.chestContents = LOTRChestContents.ANGMAR_TENT;
    }
    
    @Override
    protected LOTREntityNPC getOrc(final World world) {
        return new LOTREntityAngmarOrc(world);
    }
    
    @Override
    protected LOTREntityNPC getWarg(final World world) {
        return new LOTREntityAngmarWarg(world);
    }
    
    @Override
    protected void setOrcSpawner(final LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClass(LOTREntityAngmarOrc.class);
    }
    
    @Override
    protected void setWargSpawner(final LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClass(LOTREntityAngmarWarg.class);
    }
    
    @Override
    protected void associateGroundBlocks() {
        super.associateGroundBlocks();
        this.clearScanAlias("GROUND_COVER");
        this.addBlockMetaAliasOption("GROUND_COVER", 1, Blocks.snow_layer, 0);
        this.setBlockAliasChance("GROUND_COVER", 0.25f);
    }
}

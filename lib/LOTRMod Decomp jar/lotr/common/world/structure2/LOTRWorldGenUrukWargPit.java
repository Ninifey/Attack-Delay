// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityUrukWarg;
import lotr.common.entity.npc.LOTREntityIsengardSnaga;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenUrukWargPit extends LOTRWorldGenWargPitBase
{
    public LOTRWorldGenUrukWargPit(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.brickBlock = LOTRMod.brick2;
        super.brickMeta = 7;
        super.brickSlabBlock = LOTRMod.slabSingle4;
        super.brickSlabMeta = 4;
        super.brickStairBlock = LOTRMod.stairsUrukBrick;
        super.brickWallBlock = LOTRMod.wall2;
        super.brickWallMeta = 7;
        super.pillarBlock = super.beamBlock;
        super.pillarMeta = super.beamMeta;
        super.woolBlock = Blocks.wool;
        super.woolMeta = 12;
        super.carpetBlock = Blocks.carpet;
        super.carpetMeta = 12;
        super.barsBlock = LOTRMod.urukBars;
        super.gateOrcBlock = LOTRMod.gateUruk;
        super.tableBlock = LOTRMod.urukTable;
        super.banner = LOTRItemBanner.BannerType.ISENGARD;
        super.chestContents = LOTRChestContents.URUK_TENT;
    }
    
    @Override
    protected LOTREntityNPC getOrc(final World world) {
        return new LOTREntityIsengardSnaga(world);
    }
    
    @Override
    protected LOTREntityNPC getWarg(final World world) {
        return new LOTREntityUrukWarg(world);
    }
    
    @Override
    protected void setOrcSpawner(final LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClass(LOTREntityIsengardSnaga.class);
    }
    
    @Override
    protected void setWargSpawner(final LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClass(LOTREntityUrukWarg.class);
    }
}

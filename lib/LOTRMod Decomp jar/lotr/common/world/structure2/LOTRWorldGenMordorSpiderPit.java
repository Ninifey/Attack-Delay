// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityMordorSpider;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityOrc;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityMordorOrcSpiderKeeper;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenMordorSpiderPit extends LOTRWorldGenMordorWargPit
{
    public LOTRWorldGenMordorSpiderPit(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        if (super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            final LOTREntityOrc spiderKeeper = new LOTREntityMordorOrcSpiderKeeper(world);
            this.spawnNPCAndSetHome(spiderKeeper, world, 0, 1, 0, 8);
            return true;
        }
        return false;
    }
    
    @Override
    protected LOTREntityNPC getWarg(final World world) {
        return new LOTREntityMordorSpider(world);
    }
    
    @Override
    protected void setWargSpawner(final LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClass(LOTREntityMordorSpider.class);
    }
    
    @Override
    protected void associateGroundBlocks() {
        super.associateGroundBlocks();
        this.clearScanAlias("GROUND_COVER");
        this.addBlockMetaAliasOption("GROUND_COVER", 1, LOTRMod.webUngoliant, 0);
        this.setBlockAliasChance("GROUND_COVER", 0.04f);
    }
}

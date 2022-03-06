// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityRivendellSmith;
import lotr.common.entity.npc.LOTREntityElf;
import net.minecraft.world.World;
import lotr.common.LOTRMod;

public class LOTRWorldGenRivendellForge extends LOTRWorldGenHighElvenForge
{
    public LOTRWorldGenRivendellForge(final boolean flag) {
        super(flag);
        super.roofBlock = LOTRMod.clayTileDyed;
        super.roofMeta = 9;
        super.roofStairBlock = LOTRMod.stairsClayTileDyedCyan;
        super.tableBlock = LOTRMod.rivendellTable;
    }
    
    @Override
    protected LOTREntityElf getElf(final World world) {
        return new LOTREntityRivendellSmith(world);
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.world.structure.LOTRChestContents;
import lotr.common.entity.npc.LOTREntityBlueMountainsSmith;
import lotr.common.entity.npc.LOTREntityDwarf;
import net.minecraft.world.World;
import lotr.common.LOTRMod;

public class LOTRWorldGenBlueMountainsSmithy extends LOTRWorldGenDwarfSmithy
{
    public LOTRWorldGenBlueMountainsSmithy(final boolean flag) {
        super(flag);
        super.baseBrickBlock = LOTRMod.brick;
        super.baseBrickMeta = 14;
        super.carvedBrickBlock = LOTRMod.brick3;
        super.carvedBrickMeta = 0;
        super.pillarBlock = LOTRMod.pillar;
        super.pillarMeta = 3;
        super.tableBlock = LOTRMod.blueDwarvenTable;
        super.barsBlock = LOTRMod.blueDwarfBars;
    }
    
    @Override
    protected LOTREntityDwarf createSmith(final World world) {
        return new LOTREntityBlueMountainsSmith(world);
    }
    
    @Override
    protected LOTRChestContents getChestContents() {
        return LOTRChestContents.BLUE_MOUNTAINS_SMITHY;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;

public class LOTRWorldGenDolGuldurTent extends LOTRWorldGenTentBase
{
    public LOTRWorldGenDolGuldurTent(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        final int randomWool = random.nextInt(3);
        if (randomWool == 0) {
            super.tentBlock = Blocks.wool;
            super.tentMeta = 15;
        }
        else if (randomWool == 1) {
            super.tentBlock = Blocks.wool;
            super.tentMeta = 12;
        }
        else if (randomWool == 2) {
            super.tentBlock = Blocks.wool;
            super.tentMeta = 7;
        }
        super.fenceBlock = LOTRMod.fence;
        super.fenceMeta = 3;
        super.tableBlock = LOTRMod.dolGuldurTable;
        super.chestContents = LOTRChestContents.DOL_GULDUR_TENT;
        super.hasOrcTorches = true;
    }
}

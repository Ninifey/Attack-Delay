// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.entity.npc.LOTREntityRivendellLord;
import java.util.Random;
import lotr.common.entity.npc.LOTREntityRivendellElf;
import lotr.common.entity.npc.LOTREntityElf;
import net.minecraft.world.World;
import lotr.common.item.LOTRItemBanner;
import lotr.common.LOTRMod;

public class LOTRWorldGenRivendellHall extends LOTRWorldGenHighElvenHall
{
    public LOTRWorldGenRivendellHall(final boolean flag) {
        super(flag);
        super.tableBlock = LOTRMod.rivendellTable;
        super.bannerType = LOTRItemBanner.BannerType.RIVENDELL;
        super.chestContents = LOTRChestContents.RIVENDELL_HALL;
    }
    
    @Override
    protected LOTREntityElf createElf(final World world) {
        return new LOTREntityRivendellElf(world);
    }
    
    @Override
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        if (super.generate(world, random, i, j, k)) {
            final LOTREntityElf elfLord = new LOTREntityRivendellLord(world);
            elfLord.setLocationAndAngles((double)(i + 6), (double)(j + 6), (double)(k + 6), 0.0f, 0.0f);
            elfLord.spawnRidingHorse = false;
            elfLord.onSpawnWithEgg(null);
            elfLord.isNPCPersistent = true;
            world.spawnEntityInWorld((Entity)elfLord);
            elfLord.setHomeArea(i + 7, j + 3, k + 7, 16);
        }
        return false;
    }
}

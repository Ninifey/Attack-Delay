// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.item.LOTRItemBanner;
import lotr.common.LOTRMod;
import java.util.Random;
import lotr.common.entity.npc.LOTREntityRivendellElf;
import lotr.common.entity.npc.LOTREntityElf;
import net.minecraft.world.World;

public class LOTRWorldGenRivendellHouse extends LOTRWorldGenHighElfHouse
{
    public LOTRWorldGenRivendellHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected LOTREntityElf createElf(final World world) {
        return new LOTREntityRivendellElf(world);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.tableBlock = LOTRMod.rivendellTable;
        super.bannerType = LOTRItemBanner.BannerType.RIVENDELL;
        super.chestContents = LOTRChestContents.RIVENDELL_HALL;
    }
    
    @Override
    protected ItemStack getElfFramedItem(final Random random) {
        final ItemStack[] items = { new ItemStack(LOTRMod.helmetRivendell), new ItemStack(LOTRMod.bodyRivendell), new ItemStack(LOTRMod.legsRivendell), new ItemStack(LOTRMod.bootsRivendell), new ItemStack(LOTRMod.daggerRivendell), new ItemStack(LOTRMod.swordRivendell), new ItemStack(LOTRMod.spearRivendell), new ItemStack(LOTRMod.longspearRivendell), new ItemStack(LOTRMod.rivendellBow), new ItemStack(Items.arrow), new ItemStack(Items.feather), new ItemStack(LOTRMod.swanFeather), new ItemStack(LOTRMod.quenditeCrystal), new ItemStack(LOTRMod.goldRing), new ItemStack(LOTRMod.silverRing) };
        return items[random.nextInt(items.length)].copy();
    }
}

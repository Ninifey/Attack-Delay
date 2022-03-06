// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import lotr.common.entity.npc.LOTREntityBlueDwarf;
import lotr.common.entity.npc.LOTREntityDwarf;
import net.minecraft.world.World;

public class LOTRWorldGenBlueMountainsHouse extends LOTRWorldGenDwarfHouse
{
    public LOTRWorldGenBlueMountainsHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected LOTREntityDwarf createDwarf(final World world) {
        return new LOTREntityBlueDwarf(world);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.stoneBlock = Blocks.stone;
        super.stoneMeta = 0;
        super.fillerBlock = LOTRMod.rock;
        super.fillerMeta = 3;
        super.topBlock = LOTRMod.rock;
        super.topMeta = 3;
        super.brick2Block = LOTRMod.brick;
        super.brick2Meta = 14;
        super.pillarBlock = LOTRMod.pillar;
        super.pillarMeta = 3;
        super.chandelierBlock = LOTRMod.chandelier;
        super.chandelierMeta = 11;
        super.tableBlock = LOTRMod.blueDwarvenTable;
        super.barsBlock = LOTRMod.blueDwarfBars;
        super.larderContents = LOTRChestContents.BLUE_DWARF_HOUSE_LARDER;
        super.personalContents = LOTRChestContents.BLUE_MOUNTAINS_STRONGHOLD;
        super.plateFoods = LOTRFoods.BLUE_DWARF;
        super.drinkFoods = LOTRFoods.DWARF_DRINK;
    }
    
    @Override
    protected ItemStack getRandomWeaponItem(final Random random) {
        final ItemStack[] items = { new ItemStack(LOTRMod.swordBlueDwarven), new ItemStack(LOTRMod.daggerBlueDwarven), new ItemStack(LOTRMod.hammerBlueDwarven), new ItemStack(LOTRMod.battleaxeBlueDwarven), new ItemStack(LOTRMod.pickaxeBlueDwarven), new ItemStack(LOTRMod.mattockBlueDwarven), new ItemStack(LOTRMod.throwingAxeBlueDwarven), new ItemStack(LOTRMod.pikeBlueDwarven) };
        return items[random.nextInt(items.length)].copy();
    }
    
    @Override
    protected ItemStack getRandomOtherItem(final Random random) {
        final ItemStack[] items = { new ItemStack(LOTRMod.helmetBlueDwarven), new ItemStack(LOTRMod.bodyBlueDwarven), new ItemStack(LOTRMod.legsBlueDwarven), new ItemStack(LOTRMod.bootsBlueDwarven), new ItemStack(LOTRMod.blueDwarfSteel), new ItemStack(LOTRMod.bronze), new ItemStack(Items.iron_ingot), new ItemStack(LOTRMod.silver), new ItemStack(LOTRMod.silverNugget), new ItemStack(Items.gold_ingot), new ItemStack(Items.gold_nugget) };
        return items[random.nextInt(items.length)].copy();
    }
}

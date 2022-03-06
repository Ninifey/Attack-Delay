// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.item.LOTRItemHaradRobes;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTREntityEasterlingLevyman extends LOTREntityEasterling
{
    private static ItemStack[] levyWeapons;
    private static ItemStack[] levySpears;
    private static ItemStack[] levyBodies;
    private static ItemStack[] levyLegs;
    private static ItemStack[] levyBoots;
    private static final int[] kaftanColors;
    
    public LOTREntityEasterlingLevyman(final World world) {
        super(world);
        this.addTargetTasks(true);
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = ((Entity)this).rand.nextInt(LOTREntityEasterlingLevyman.levyWeapons.length);
        super.npcItemsInv.setMeleeWeapon(LOTREntityEasterlingLevyman.levyWeapons[i].copy());
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        if (((Entity)this).rand.nextInt(5) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            i = ((Entity)this).rand.nextInt(LOTREntityEasterlingLevyman.levySpears.length);
            super.npcItemsInv.setMeleeWeapon(LOTREntityEasterlingLevyman.levySpears[i].copy());
        }
        i = ((Entity)this).rand.nextInt(LOTREntityEasterlingLevyman.levyBoots.length);
        this.setCurrentItemOrArmor(1, LOTREntityEasterlingLevyman.levyBoots[i].copy());
        i = ((Entity)this).rand.nextInt(LOTREntityEasterlingLevyman.levyLegs.length);
        this.setCurrentItemOrArmor(2, LOTREntityEasterlingLevyman.levyLegs[i].copy());
        i = ((Entity)this).rand.nextInt(LOTREntityEasterlingLevyman.levyBodies.length);
        this.setCurrentItemOrArmor(3, LOTREntityEasterlingLevyman.levyBodies[i].copy());
        this.setCurrentItemOrArmor(4, (ItemStack)null);
        if (((Entity)this).rand.nextBoolean()) {
            final ItemStack kaftan = new ItemStack(LOTRMod.bodyKaftan);
            final int kaftanColor = LOTREntityEasterlingLevyman.kaftanColors[((Entity)this).rand.nextInt(LOTREntityEasterlingLevyman.kaftanColors.length)];
            LOTRItemHaradRobes.setRobesColor(kaftan, kaftanColor);
            this.setCurrentItemOrArmor(3, kaftan);
            if (((Entity)this).rand.nextBoolean()) {
                final ItemStack kaftanLegs = new ItemStack(LOTRMod.legsKaftan);
                LOTRItemHaradRobes.setRobesColor(kaftanLegs, kaftanColor);
                this.setCurrentItemOrArmor(2, kaftanLegs);
            }
        }
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "rhun/warrior/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "rhun/warrior/hired";
        }
        return "rhun/warrior/friendly";
    }
    
    static {
        LOTREntityEasterlingLevyman.levyWeapons = new ItemStack[] { new ItemStack(LOTRMod.daggerRhun), new ItemStack(LOTRMod.daggerRhunPoisoned), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerBronze), new ItemStack(LOTRMod.swordRhun), new ItemStack(LOTRMod.battleaxeRhun), new ItemStack(Items.iron_sword), new ItemStack(LOTRMod.swordBronze), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.battleaxeBronze), new ItemStack(LOTRMod.spearRhun), new ItemStack(LOTRMod.spearIron), new ItemStack(LOTRMod.spearBronze) };
        LOTREntityEasterlingLevyman.levySpears = new ItemStack[] { new ItemStack(LOTRMod.spearRhun), new ItemStack(LOTRMod.spearIron), new ItemStack(LOTRMod.spearBronze) };
        LOTREntityEasterlingLevyman.levyBodies = new ItemStack[] { new ItemStack((Item)Items.leather_chestplate), new ItemStack(LOTRMod.bodyBronze) };
        LOTREntityEasterlingLevyman.levyLegs = new ItemStack[] { new ItemStack((Item)Items.leather_leggings), new ItemStack(LOTRMod.legsBronze) };
        LOTREntityEasterlingLevyman.levyBoots = new ItemStack[] { new ItemStack((Item)Items.leather_boots), new ItemStack(LOTRMod.bootsBronze) };
        kaftanColors = new int[] { 14823729, 11862016, 5512477, 14196753, 11374145, 7366222 };
    }
}

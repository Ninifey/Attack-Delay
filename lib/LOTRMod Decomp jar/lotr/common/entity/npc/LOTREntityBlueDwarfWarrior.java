// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.LOTRShields;
import net.minecraft.world.World;

public class LOTREntityBlueDwarfWarrior extends LOTREntityBlueDwarf
{
    public LOTREntityBlueDwarfWarrior(final World world) {
        super(world);
        super.npcShield = LOTRShields.ALIGNMENT_BLUE_MOUNTAINS;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(7);
        if (i == 0) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordBlueDwarven));
        }
        else if (i == 1 || i == 2) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeBlueDwarven));
        }
        else if (i == 3 || i == 4) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerBlueDwarven));
        }
        else if (i == 5) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.mattockBlueDwarven));
        }
        else if (i == 6) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeBlueDwarven));
        }
        if (((Entity)this).rand.nextInt(6) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBlueDwarven));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsBlueDwarven));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsBlueDwarven));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyBlueDwarven));
        if (((Entity)this).rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetBlueDwarven));
        }
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
}

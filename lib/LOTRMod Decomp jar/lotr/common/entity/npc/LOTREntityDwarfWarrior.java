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

public class LOTREntityDwarfWarrior extends LOTREntityDwarf
{
    public LOTREntityDwarfWarrior(final World world) {
        super(world);
        super.npcShield = LOTRShields.ALIGNMENT_DWARF;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(7);
        if (i == 0) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordDwarven));
        }
        else if (i == 1 || i == 2) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeDwarven));
        }
        else if (i == 3 || i == 4) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerDwarven));
        }
        else if (i == 5) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.mattockDwarven));
        }
        else if (i == 6) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeDwarven));
        }
        if (((Entity)this).rand.nextInt(6) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearDwarven));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDwarven));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDwarven));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDwarven));
        if (((Entity)this).rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDwarven));
        }
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
}

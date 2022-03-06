// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityEasterlingWarrior extends LOTREntityEasterlingLevyman
{
    public LOTREntityEasterlingWarrior(final World world) {
        super(world);
        this.addTargetTasks(true);
        super.spawnRidingHorse = (((Entity)this).rand.nextInt(6) == 0);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(LOTREntityNPC.npcRangedAccuracy).setBaseValue(0.75);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(10);
        if (i == 0 || i == 1 || i == 2) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordRhun));
        }
        else if (i == 3 || i == 4 || i == 5) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeRhun));
        }
        else if (i == 6) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.polearmRhun));
        }
        else if (i == 7) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerRhun));
        }
        else if (i == 8) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerRhunPoisoned));
        }
        else if (i == 9) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeRhun));
        }
        if (((Entity)this).rand.nextInt(5) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearRhun));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRhun));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRhun));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRhun));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRhun));
        return data;
    }
}

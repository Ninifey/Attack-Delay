// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.LOTRShields;
import net.minecraft.world.World;

public class LOTREntityDunlendingWarrior extends LOTREntityDunlending
{
    public LOTREntityDunlendingWarrior(final World world) {
        super(world);
        super.npcShield = LOTRShields.ALIGNMENT_DUNLAND;
    }
    
    @Override
    public EntityAIBase getDunlendingAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.6, false);
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(7);
        if (i == 0) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_sword));
        }
        else if (i == 1) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordBronze));
        }
        else if (i == 2) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerIron));
        }
        else if (i == 3) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBronze));
        }
        else if (i == 4) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeIron));
        }
        else if (i == 5) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeBronze));
        }
        else if (i == 6) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeIron));
        }
        if (((Entity)this).rand.nextInt(5) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            if (((Entity)this).rand.nextBoolean()) {
                super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearIron));
            }
            else {
                super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBronze));
            }
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDunlending));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDunlending));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDunlending));
        if (((Entity)this).rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDunlending));
        }
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
}

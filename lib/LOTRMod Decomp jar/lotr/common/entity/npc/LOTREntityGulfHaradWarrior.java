// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.LOTRShields;
import net.minecraft.world.World;

public class LOTREntityGulfHaradWarrior extends LOTREntityGulfHaradrim
{
    public LOTREntityGulfHaradWarrior(final World world) {
        super(world);
        this.addTargetTasks(true);
        super.spawnRidingHorse = (((Entity)this).rand.nextInt(10) == 0);
        super.npcShield = LOTRShields.ALIGNMENT_GULF;
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(LOTREntityNPC.npcRangedAccuracy).setBaseValue(0.75);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if (((Entity)this).rand.nextInt(3) != 0) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordGulfHarad));
        }
        else {
            final int i = ((Entity)this).rand.nextInt(5);
            if (i == 0 || i == 1) {
                super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordHarad));
            }
            else if (i == 2) {
                super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHarad));
            }
            else if (i == 3) {
                super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHaradPoisoned));
            }
            else if (i == 4) {
                super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeHarad));
            }
        }
        if (((Entity)this).rand.nextInt(5) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHarad));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsGulfHarad));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsGulfHarad));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGulfHarad));
        if (((Entity)this).rand.nextInt(10) == 0) {
            this.setCurrentItemOrArmor(4, (ItemStack)null);
        }
        else {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetGulfHarad));
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
            return "nearHarad/gulf/warrior/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "nearHarad/gulf/warrior/hired";
        }
        return "nearHarad/gulf/warrior/friendly";
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.LOTRShields;
import net.minecraft.world.World;

public class LOTREntityDorwinionElfWarrior extends LOTREntityDorwinionElf
{
    public LOTREntityDorwinionElfWarrior(final World world) {
        super(world);
        ((EntityLiving)this).tasks.addTask(2, super.meleeAttackAI);
        super.npcShield = LOTRShields.ALIGNMENT_DORWINION_ELF;
    }
    
    @Override
    protected EntityAIBase createElfMeleeAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, false);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(2);
        if (i == 0) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordDorwinionElf));
            if (((Entity)this).rand.nextInt(5) == 0) {
                super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
                super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBladorthin));
            }
        }
        else if (i == 1) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBladorthin));
            super.npcItemsInv.setSpearBackup(null);
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDorwinionElf));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDorwinionElf));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDorwinionElf));
        if (((Entity)this).rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDorwinionElf));
        }
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 3.0f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "dorwinion/elfWarrior/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "dorwinion/elfWarrior/hired";
        }
        return "dorwinion/elfWarrior/friendly";
    }
}

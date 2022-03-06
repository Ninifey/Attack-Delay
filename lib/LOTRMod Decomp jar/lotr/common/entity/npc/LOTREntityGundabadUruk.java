// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.LOTRShields;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTREntityGundabadUruk extends LOTREntityGundabadOrc
{
    private static ItemStack[] urukWeapons;
    
    public LOTREntityGundabadUruk(final World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        super.isWeakOrc = false;
        super.npcShield = LOTRShields.ALIGNMENT_GUNDABAD;
    }
    
    @Override
    public EntityAIBase createOrcAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, false);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
        this.getEntityAttribute(LOTREntityNPC.npcRangedAccuracy).setBaseValue(0.75);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(LOTREntityGundabadUruk.urukWeapons.length);
        super.npcItemsInv.setMeleeWeapon(LOTREntityGundabadUruk.urukWeapons[i].copy());
        if (((Entity)this).rand.nextInt(6) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearGundabadUruk));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsGundabadUruk));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsGundabadUruk));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGundabadUruk));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetGundabadUruk));
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killGundabadUruk;
    }
    
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.75f;
    }
    
    static {
        LOTREntityGundabadUruk.urukWeapons = new ItemStack[] { new ItemStack(LOTRMod.swordGundabadUruk), new ItemStack(LOTRMod.battleaxeGundabadUruk), new ItemStack(LOTRMod.hammerGundabadUruk), new ItemStack(LOTRMod.daggerGundabadUruk), new ItemStack(LOTRMod.daggerGundabadUrukPoisoned), new ItemStack(LOTRMod.pikeGundabadUruk) };
    }
}

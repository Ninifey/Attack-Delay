// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.LOTRShields;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTREntityMoredainWarrior extends LOTREntityMoredain
{
    private static ItemStack[] weaponsMoredain;
    private static ItemStack[] weaponsIron;
    private static ItemStack[] weaponsBronze;
    
    public LOTREntityMoredainWarrior(final World world) {
        super(world);
        super.npcShield = LOTRShields.ALIGNMENT_MOREDAIN;
        super.spawnRidingHorse = (((Entity)this).rand.nextInt(10) == 0);
    }
    
    @Override
    protected EntityAIBase createHaradrimAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.7, true);
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if (((Entity)this).rand.nextInt(5) == 0) {
            if (((Entity)this).rand.nextBoolean()) {
                final int i = ((Entity)this).rand.nextInt(LOTREntityMoredainWarrior.weaponsIron.length);
                super.npcItemsInv.setMeleeWeapon(LOTREntityMoredainWarrior.weaponsIron[i].copy());
            }
            else {
                final int i = ((Entity)this).rand.nextInt(LOTREntityMoredainWarrior.weaponsBronze.length);
                super.npcItemsInv.setMeleeWeapon(LOTREntityMoredainWarrior.weaponsBronze[i].copy());
            }
        }
        else {
            final int i = ((Entity)this).rand.nextInt(LOTREntityMoredainWarrior.weaponsMoredain.length);
            super.npcItemsInv.setMeleeWeapon(LOTREntityMoredainWarrior.weaponsMoredain[i].copy());
        }
        if (((Entity)this).rand.nextInt(3) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearMoredain));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsMoredain));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsMoredain));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyMoredain));
        if (((Entity)this).rand.nextInt(3) == 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetMoredain));
        }
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    static {
        LOTREntityMoredainWarrior.weaponsMoredain = new ItemStack[] { new ItemStack(LOTRMod.battleaxeMoredain), new ItemStack(LOTRMod.battleaxeMoredain), new ItemStack(LOTRMod.daggerMoredain), new ItemStack(LOTRMod.daggerMoredainPoisoned), new ItemStack(LOTRMod.clubMoredain), new ItemStack(LOTRMod.clubMoredain), new ItemStack(LOTRMod.spearMoredain), new ItemStack(LOTRMod.spearMoredain), new ItemStack(LOTRMod.swordMoredain), new ItemStack(LOTRMod.swordMoredain) };
        LOTREntityMoredainWarrior.weaponsIron = new ItemStack[] { new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.spearNearHarad) };
        LOTREntityMoredainWarrior.weaponsBronze = new ItemStack[] { new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.spearHarad) };
    }
}

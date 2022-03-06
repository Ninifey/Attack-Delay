// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityRhino;
import lotr.common.LOTRShields;
import net.minecraft.world.World;

public class LOTREntityHalfTrollWarrior extends LOTREntityHalfTroll
{
    public LOTREntityHalfTrollWarrior(final World world) {
        super(world);
        super.npcShield = LOTRShields.ALIGNMENT_HALF_TROLL;
        super.spawnRidingHorse = (((Entity)this).rand.nextInt(12) == 0);
    }
    
    @Override
    public LOTRNPCMount createMountToRide() {
        final LOTREntityRhino rhino = new LOTREntityRhino(((Entity)this).worldObj);
        if (((Entity)this).rand.nextBoolean()) {
            rhino.setMountArmor(new ItemStack(LOTRMod.rhinoArmorHalfTroll));
        }
        return rhino;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.24);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(7);
        if (i == 0) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeHalfTroll));
        }
        else if (i == 1) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerHalfTroll));
        }
        else if (i == 2) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.maceHalfTroll));
        }
        else if (i == 3) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.scimitarHalfTroll));
        }
        else if (i == 4) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHalfTroll));
        }
        else if (i == 5) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHalfTrollPoisoned));
        }
        else if (i == 6) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeHalfTroll));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsHalfTroll));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsHalfTroll));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyHalfTroll));
        if (((Entity)this).rand.nextInt(4) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetHalfTroll));
        }
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
}

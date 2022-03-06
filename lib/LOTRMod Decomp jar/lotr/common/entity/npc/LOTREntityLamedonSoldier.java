// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.LOTRCapes;
import lotr.common.LOTRShields;
import net.minecraft.world.World;

public class LOTREntityLamedonSoldier extends LOTREntityGondorSoldier
{
    public LOTREntityLamedonSoldier(final World world) {
        super(world);
        super.spawnRidingHorse = (((Entity)this).rand.nextInt(6) == 0);
        super.npcShield = LOTRShields.ALIGNMENT_LAMEDON;
        super.npcCape = LOTRCapes.LAMEDON;
    }
    
    @Override
    public EntityAIBase createGondorAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, false);
    }
    
    @Override
    public LOTRNPCMount createMountToRide() {
        final LOTREntityHorse horse = (LOTREntityHorse)super.createMountToRide();
        horse.setMountArmor(new ItemStack(LOTRMod.horseArmorLamedon));
        return horse;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(3);
        if (i == 0) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordGondor));
        }
        else if (i == 1) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerGondor));
        }
        else if (i == 2) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeGondor));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsLamedon));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsLamedon));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyLamedon));
        if (((Entity)this).rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetLamedon));
        }
        else {
            this.setCurrentItemOrArmor(4, (ItemStack)null);
        }
        return data;
    }
    
    @Override
    protected void onAttackModeChange(final AttackMode mode, final boolean mounted) {
        if (mode == AttackMode.IDLE) {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getIdleItem());
        }
        else {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getMeleeWeapon());
        }
    }
}

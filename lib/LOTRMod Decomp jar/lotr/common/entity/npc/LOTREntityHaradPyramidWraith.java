// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class LOTREntityHaradPyramidWraith extends LOTREntitySkeletalWraith
{
    public LOTREntityHaradPyramidWraith(final World world) {
        super(world);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if (((Entity)this).rand.nextBoolean()) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerNearHaradPoisoned));
            super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
            this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsNearHarad));
            this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsNearHarad));
            this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyNearHarad));
        }
        else {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHaradPoisoned));
            super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
            this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsGulfHarad));
            this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsGulfHarad));
            this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGulfHarad));
        }
        return data;
    }
}

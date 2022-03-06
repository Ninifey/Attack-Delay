// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityTauredainPyramidWraith extends LOTREntitySkeletalWraith
{
    public LOTREntityTauredainPyramidWraith(final World world) {
        super(world);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(6);
        if (i == 0 || i == 1) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordTauredain));
        }
        else if (i == 2) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerTauredain));
        }
        else if (i == 3) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerTauredainPoisoned));
        }
        else if (i == 4) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerTauredain));
        }
        else if (i == 5) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeTauredain));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsTauredain));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsTauredain));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyTauredain));
        return data;
    }
}

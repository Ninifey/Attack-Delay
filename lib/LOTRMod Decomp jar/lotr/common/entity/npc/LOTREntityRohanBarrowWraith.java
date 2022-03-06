// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityRohanBarrowWraith extends LOTREntitySkeletalWraith
{
    public LOTREntityRohanBarrowWraith(final World world) {
        super(world);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordRohan));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRohanMarshal));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRohanMarshal));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRohanMarshal));
        return data;
    }
}

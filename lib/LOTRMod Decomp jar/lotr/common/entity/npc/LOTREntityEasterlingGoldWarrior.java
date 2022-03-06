// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.LOTRShields;
import net.minecraft.world.World;

public class LOTREntityEasterlingGoldWarrior extends LOTREntityEasterlingWarrior
{
    public LOTREntityEasterlingGoldWarrior(final World world) {
        super(world);
        super.npcShield = LOTRShields.ALIGNMENT_RHUN;
    }
    
    @Override
    public LOTRNPCMount createMountToRide() {
        final LOTREntityHorse horse = (LOTREntityHorse)super.createMountToRide();
        horse.setMountArmor(new ItemStack(LOTRMod.horseArmorRhunGold));
        return horse;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRhunGold));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRhunGold));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRhunGold));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRhunGold));
        return data;
    }
}
